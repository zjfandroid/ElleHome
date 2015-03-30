package elle.home.hal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
	
	public String TAG = "WifiAdmin";
	private WifiManager mWifiManager;
	private WifiInfo mWifiInfo;
	private Context context;
	private List<ScanResult> mWifiList = null;
	private List<WifiConfiguration> mWifiConfiguration;
	private DhcpInfo dhcpInfo;
	private WifiLock mWifiLock;
	

	public WifiAdmin(Context context){
		this.context = context;
		this.mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		this.mWifiInfo = mWifiManager.getConnectionInfo();
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();
		//this.createWifiLock();
		
	}
	
	/**
	 * 得到配置信息
	 * */
	public List<WifiConfiguration> getConfiguration(){
		return mWifiConfiguration;
	}
	
	/**
	 * 指定配置好的网络
	 * */
	public void connectConfiguration(int index){
		if(index>mWifiConfiguration.size()){
			return ;
		}
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,true);
	}
	
	/**
	 * 加入到指定的网络
	 * */
	public boolean addNetWork(WifiConfiguration wcg){
		int wcgID = mWifiManager.addNetwork(wcg);
		boolean b = mWifiManager.enableNetwork(wcgID, true);
		Log.d(TAG,"enable network:"+b);
		return b;
	}
	
	/**
	 * 得到wifi连接信息
	 * */
	public WifiInfo getWifiInfo(){
		return this.mWifiInfo;
	}
	
	/**
	 * 得到扫描的结果
	 * */
	public List<ScanResult> getWifiList(){
		return mWifiManager.getScanResults();
	}
	
	public ArrayList<HashMap<String, String>> getWifiSsidList(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		if(mWifiManager.startScan()){
			mWifiList = mWifiManager.getScanResults();
			if(mWifiList!=null){
				for(int i=0;i<mWifiList.size();i++){
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("ssid", mWifiList.get(i).SSID);
					list.add(map);
				}
			}
		}
		return list;
	}
	
	public boolean addExsists(String ssid){
		List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + ssid + "\"")) {
            	mWifiManager.enableNetwork(existingConfig.networkId,true);
            	return true;
            }
        }
		return false;
	}
	
	/**
	 * 查看以前是否配置过此wifi
	 * */
	public WifiConfiguration isExsists(String ssid){
		List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + ssid + "\"")) {
                return existingConfig;
            }
        }
        return null;

	}
	
	/**
	 * 组包一个wifi配置信息
	 * */
	public WifiConfiguration creatWifiInfo(String ssid,String password,int type){
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";
        
        WifiConfiguration tempconfig = this.isExsists(ssid);
        if(tempconfig!=null){
        	mWifiManager.removeNetwork(tempconfig.networkId);
        }else{
        	Log.d(TAG,"isexsits is null");
        }
        
        if(type == 1){
        	Log.d(TAG,"type = 1");
        	config.wepKeys[0] = "";
        	config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        	config.wepTxKeyIndex = 0;
        }
        
        if(type == 2){
        	Log.d(TAG,"type = 2");
        	config.hiddenSSID = true;
        	config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        
        if(type == 3){
        	Log.d(TAG,"type = 3");
        	config.preSharedKey = "\"" + password + "\"";
        	 
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        
		return config;
	}
	
	/**
	 * wifi锁定
	 * */
	public void acquireWifiLock(){
		mWifiLock.acquire();
	}
	
	/**
	 * wifi解锁
	 * */
	public void releaseWifiLock(){
		mWifiLock.release();
	}
	
	public void createWifiLock(){
		mWifiLock = mWifiManager.createWifiLock("Test");
	}
	
}
