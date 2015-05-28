package elle.home.hal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

public class GetSSID{
	private static final String SERVER_URL = "http://wifi.baohe:8080/webServices/get_first_data";
	
	private SSIDInfo info = new SSIDInfo();

	private OnSSIDListener mListener;
	
	public GetSSID(OnSSIDListener onSSIDListener) {
		this.mListener = onSSIDListener;
		getSSID();
	}
	
	
	class SSIDInfo {
		public String SSID;
		public String pass;
	}

	private void getSSID() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = SERVER_URL;
				String infor = getWebRet(url);
				
				if(null != infor){
					JSONObject jsonObject = null;
					
					try {
						jsonObject = JSONObject.parseObject(infor);
					} catch (Exception e) {
						jsonObject = null;
					}
					
					if(null != jsonObject){
						info.pass = jsonObject.getString("pass2");
						info.SSID = jsonObject.getString("ssid2");
						Log.d("nfo.pass=", info.pass);
						Log.d("nfo.ssid=", info.SSID);
						Log.d("nfo.=", jsonObject.toString());
						mListener.onSSIDGet(info.SSID, info.pass);
					}else{
						mListener.onFail("jsonObject is null");
					}
				}else{
					mListener.onFail("infor is null");
				}
			}
		}).start();
	}

	private String getWebRet(String strUrl) {
		try {
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			String webContent = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			webContent = sb.toString();
			return webContent;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public interface OnSSIDListener{
		void onSSIDGet(String ssid, String pass);
		void onFail(String info);
	}
}
