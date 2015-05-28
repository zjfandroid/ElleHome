package elle.home.hal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

public class UserData {

	public String TAG = "UserData";
	
	Context context;
	SharedPreferences sp;
	
	public UserData(Context context){
		this.context = context;
		sp = context.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}
	
	public void saveData(String key,String data){
		Editor et = sp.edit();
		et.putString(key, data);
		et.commit();
	}
	
	public String getData(String key){
		return sp.getString(key, "none");
	}
	
	public byte[] getUuid(){
		byte[] tmp = new byte[4];
		TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		String szImei = TelephonyMgr.getDeviceId(); 
		String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
		String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
		String m_szLongID = szImei + m_szAndroidID+ m_szWLANMAC ;
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(m!=null){
			m.update(m_szLongID.getBytes(),0,m_szLongID.length()); 
			byte p_md5Data[] = m.digest();
			for(int i=0;i<4;i++){
				tmp[i] = p_md5Data[i];
			}
		}
		Log.d(TAG,"得到mac地址:"+tmp[0]+" "+tmp[1]+" "+tmp[2]+" "+tmp[3]);
		
		return tmp;
	}
	
}
