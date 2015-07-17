package elle.fun.infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import elle.home.publicfun.DataExchange;

public class InfraThread extends Thread {

	String TAG = "InfraThread";
	
	public static final String Company = "vstarcam";
	public static final String TypeAir = "AirControl";
	public static final String TypeTv = "Tv";
	public static final String LanguageChinese = "Chinese";
	public static final String LanguageEnglish = "English";
	
	JSONObject json = new JSONObject();
	
	OnBrandListListener brandListener;
	OnDevListListener devListener;
	
	public void startGetBrandList(String company,String type,String language,OnBrandListListener brandListener){
		try {
			json.put("company",company);
			json.put("type", type);
			json.put("language", language);
			json.put("fun", "getBrandList");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.brandListener = brandListener;
		this.start();
	}
	
	public void startGetDevList(String company,String type,String brand,OnDevListListener devListener){
		try {
			json.put("company", company);
			json.put("type", type);
			json.put("fun", "getDevList");
			json.put("brand", brand);
		} catch (JSONException e) {
			e.printStackTrace();			
		}
		this.devListener = devListener;
		this.start();
	}
	
	@Override
	public void run() {
		super.run();
		try {
			Socket socket = new Socket("198.199.115.33",28888);
//			Socket socket = new Socket("192.168.2.61",28888);			
//			Socket socket = new Socket("192.168.30.113",28888);			
			if(socket!=null){
				
			}
			byte[] sendtmp = new byte[6+json.toString().getBytes().length];
			sendtmp[0] = 0x01;
			sendtmp[1] = 0x02;
			System.arraycopy(DataExchange.intToFourByte(6+json.toString().getBytes().length), 0, sendtmp, 2, 4);
			System.arraycopy(json.toString().getBytes(), 0, sendtmp, 6, json.toString().getBytes().length);
			
			OutputStream out = socket.getOutputStream();
			//Log.d(TAG,"byte len:"+sendtmp.length);
			out.write(sendtmp);
			out.flush();
			
			InputStream in = socket.getInputStream();
			byte[] frame = new byte[4096];
			int size = in.read(frame);
			int allsize = 0;
			int readlen = 0;
			if(size>6){
				if(frame[0]==0x01&&frame[1]==0x02){
					allsize = DataExchange.fourByteToInt(frame[2], frame[3], frame[4], frame[5]);
					byte[] tmp = new byte[allsize-6];
					readlen = size-6;
					System.arraycopy(frame, 6, tmp, 0, readlen);
					if(allsize != size){
						while((size = in.read(frame))!=-1){
							System.arraycopy(frame, 0, tmp, readlen, size);
							readlen = readlen + size;
							if(readlen == (allsize-6)){
								break;
							}
						}
					}
					//
					try {
						JSONObject result = new JSONObject(new String(tmp));
						//Log.d(TAG,"result:"+result.toString());
						if(json.getString("fun").equalsIgnoreCase("getBrandList")){
							int brandLen = Integer.valueOf(result.getString("BrandLen"));
							String data = result.getString("DataResult");
							String[] xx = data.split(",");
							ArrayList<String> datalist = new ArrayList<String>();
							//Log.d(TAG,"datalist len:"+xx.length);
							for(int i=0;i<xx.length;i++){
								datalist.add(xx[i]);
							}
							this.brandListener.recvBrandList(true, brandLen, datalist);
						}else if(json.getString("fun").equalsIgnoreCase("getDevList")){
							int devLen = Integer.valueOf(result.getString("DevLen"));
							String data = result.getString("DataResult");
							String[] xx = data.split(",");
							ArrayList<String> datalist = new ArrayList<String>();
							for(int i=0;i<xx.length;i++){
								datalist.add(xx[i]);
							}
							this.devListener.recvDevListListener(true, devLen, datalist);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
