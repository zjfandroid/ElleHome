package elle.home.hal;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.net.NetworkInfo.State;

public class WiFiExchange {
	
	public String TAG = "WiFiExchange";
	
	Context context;
	private WifiManager wifiManager;
	OnWifiChange listener;
	
	boolean isConfigOk;
	
	boolean isRunNow;
	
	//当前网络的ssid的id编号
	int curWifiId;
	
	WifiThread thread;
	
	boolean isError;
	
	public WiFiExchange(Context context){
		isConfigOk = false;
		isRunNow = false;
		this.context = context;
		this.wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		
	}
	
	public void startConfig(String devSsid,String configSsid,String password){
		if(isRunNow)
			return;
		thread = new WifiThread();
		isRunNow = true;
		isError = false;
		thread.setSSid(devSsid,configSsid,password);
		thread.start();
	}
	
	public void backToRouter(String routerSsid,String devSsid){
		thread = new WifiThread();
		isRunNow = true;
		thread.setDirectSsid(routerSsid, devSsid);
		thread.start();
	}
	
	public void endConfig(){
		if(isRunNow){
			thread.stopThis();
			isRunNow = false;
		}
	}
	
	public class WifiThread extends Thread{

		String devssid;
		String configSsid;
		String password;
		String curSsid;
		
		boolean isDirect;
		
		boolean isStop;
		
		public void setSSid(String devssid,String configssid,String password){
			Log.d(TAG,"设备ssid:"+devssid+" 配置路由ssid:"+configssid+" 路由密码:"+password);
			this.devssid = devssid;
			this.configSsid = configssid;
			this.password = password;
			isStop = true;
			isDirect = false;
		}
		
		public void setDirectSsid(String routerSsid,String devSsid){
			isDirect = true;
			this.curSsid = routerSsid;
			this.devssid = devSsid;
		}
		
		public void stopThis(){
			Log.d(TAG,"退出wifi配置线程了");
			isStop = false;
		}
		
		/**
		 * 判断当前wifi的网络是否为
		 * */
		private int whatSsid(String devSsid){
			if(wifiManager.getConnectionInfo().getSSID().equals("\"" + devSsid + "\"")){
				Log.d(TAG,"现在手机连接到的是设备自己的热点");
				return 0;
			}
			Log.d(TAG,"现在手机连接到的是路由器的热点");
			return 1;	//连接在路由器上的
		}
		
		/**
		 *切换到某个dev的ap热点 
		 * */
		private boolean changeToDevAp(){
			//尝试直接切换到某个wifi
			wifiManager.disconnect();
			//判断是否有该配置，有则删除掉
			List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
			for (WifiConfiguration existingConfig : existingConfigs) {
	            if (existingConfig.SSID.equals("\"" + devssid + "\"")) {
	            	int tmpId = existingConfig.networkId;
	    			wifiManager.removeNetwork(tmpId);
	    			wifiManager.saveConfiguration();
	    			Log.d(TAG,"删除掉当前（设备）的网络配置："+existingConfig.SSID);
	            }
	        }
			
			//判断这个wifi是否为开放网络，如果为开放的，则无需密码，不开放则用密码登陆
			WifiConfiguration config ;
			if(isNeedPassword(devssid)){
				config = creatWifiInfo(devssid, "12345678", 3);
				Log.d(TAG,"设备ap需要密码");
			}else{
				config = creatWifiInfo(devssid, "", 1);
				Log.d(TAG,"设备ap不需要密码");
			}
			
			//建立一个wifi配置先
			
			int wcgID = wifiManager.addNetwork(config);
			int repeatCount = 0;
			//使能新的wifi配置
			while(wifiManager.enableNetwork(wcgID, true)==false){
				repeatCount++;
				Log.d(TAG,"尝试enable设备wifi次数:"+repeatCount);
				if(repeatCount>30){
					isStop = false;
					listener.onError("使能设备WiFi超时失败");
					return false;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(isStop==false){
					listener.onError("使能设备WiFi退出");
					return false;
				}
			}
			//保证连接上
			repeatCount = 0;
			ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			while(((android.net.NetworkInfo.State) ((ConnectivityManager) conManager).getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState())!= android.net.NetworkInfo.State.CONNECTED){
				repeatCount++;
				if(repeatCount>30){
					isStop = false;
					listener.onError("连接设备WiFi超时失败:"+repeatCount);
					return false;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(isStop==false){
					listener.onError("连接设备WiFi退出:"+repeatCount);
					return false;
				}
				Log.d(TAG,"还未连接上设备的ssid："+devssid+" count:"+repeatCount);
			}
			//判断wifi是否已经真的被改变
			WifiManager xwifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			while(!xwifiManager.getConnectionInfo().getSSID().equalsIgnoreCase("\"" +devssid+ "\"")){
				xwifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
				repeatCount++;
				if(repeatCount>30){
					isStop = false;
					listener.onError("获取AP提供的IP超时失败");
					return false;
				}
				Log.d(TAG,"配置wifi设备，当前的ssid还未改变，尝试加入设备wifi次数:"+repeatCount+" curside:"+xwifiManager.getConnectionInfo().getSSID());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(isStop==false){
					listener.onError("获取AP提供的IP退出");
					return false;
				}
			}
			listener.changeToDevAp();
			Log.d(TAG,"当前的ssid："+wifiManager.getConnectionInfo().getSSID());
			Log.d(TAG,"加入到配置的wifi:"+config.SSID+" ok");
			return true;
		}
		
		/**
		 * 判断要加入的网络的属性，加密或者不加密
		 * */
		private boolean isNeedPassword(String ssid){
			if(wifiManager.startScan()){
				List<ScanResult> list = wifiManager.getScanResults();
				if(list!=null){
					for(int i=0;i<list.size();i++){
						ScanResult result = list.get(i);
						Log.d(TAG,"ssid:"+result.SSID+" security:"+result.capabilities);
						if(ssid.equalsIgnoreCase(result.SSID)){
							if(result.capabilities.contains("WEP")||result.capabilities.contains("WPA")){
								return true;
							}else{
								return false;
							}
						}
					}
				}
			}
			return false;
		}
		
		/**
		 * 切换回原来的路由
		 * */
		private boolean changeBackToRouter(String backSsid,String xdevssid){
			
			//关闭当前的网络，然后删除相关信息，然后删除掉
			Log.d(TAG,"配置之前的ssid："+backSsid+" 切换回去之前的当前网络ssid:"+wifiManager.getConnectionInfo().getSSID());

			List<WifiConfiguration> xexistingConfigs = wifiManager.getConfiguredNetworks();
			for (WifiConfiguration existingConfig : xexistingConfigs) {
				//Log.d(TAG,"existingConfig:"+existingConfig.SSID);
	            if (existingConfig.SSID.equals(backSsid)) {
	            	int repeatCount = 0;
	            	while(wifiManager.enableNetwork(existingConfig.networkId,true)==false){
	            		repeatCount++;
	            		if(repeatCount>30){
	    					isStop = false;
	    					listener.onError("使能路由WiFi超时失败");
	    					return false;
	    				}
	    				Log.d(TAG,"配置成功后尝试加入设备wifi次数:"+repeatCount);
	    				try {
	    					Thread.sleep(1000);
	    				} catch (InterruptedException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    				if(isStop==false){
	    					listener.onError("使能路由WiFi退出");
	    					return false;
	    				}
	            	}
	            	
	            	WifiManager xwifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	    			while(!xwifiManager.getConnectionInfo().getSSID().equalsIgnoreCase(backSsid)){
	    				xwifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	    				repeatCount++;
	    				if(repeatCount>30){
	    					isStop = false;
	    					listener.onError("连接路由WiFi超时");
	    					return false;
	    				}
	    				Log.d(TAG,"切换回路由，当前的ssid还未改变，尝试切换回去路由次数:"+repeatCount+" curside:"+xwifiManager.getConnectionInfo().getSSID());
	    				try {
	    					Thread.sleep(1000);
	    				} catch (InterruptedException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    				if(isStop==false){
	    					listener.onError("连接路由WiFi退出");
	    					return false;
	    				}
	    			}
	            	Log.d(TAG,"切换回原来的wifi："+backSsid);
	            	break;
	            }
	            
	        }
			
			List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
			for (WifiConfiguration existingConfig : existingConfigs) {
	            if (existingConfig.SSID.equals("\"" + xdevssid + "\"")) {
	            	int tmpId = existingConfig.networkId;
	    			wifiManager.removeNetwork(tmpId);
	    			wifiManager.saveConfiguration();
	    			Log.d(TAG,"删除掉当前（设备）的网络配置："+existingConfig.SSID);
	            }
	        }
			
			Log.d(TAG,"切换回去结束");
			return true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			if(isDirect==false){
				listener.routerCloseNow();
				//连接方式
				int contype = 0;
				//记录下当前wifi的id编号
				curWifiId = wifiManager.getConnectionInfo().getNetworkId();
				curSsid = wifiManager.getConnectionInfo().getSSID();
				//先判断当前的网络是连接在设备上的还是路由上的
				contype = whatSsid(devssid);
				switch(contype){
				case 0:	//已经是设备了
					listener.changeToDevAp();
					break;
				default:
					if(changeToDevAp()==false){
						isError = true;
						Log.d(TAG,"标记出错");
					}
					break;
				}
				if(isError){
					
				}else{
					//等待配置ok
					int repeatCount = 0;
					while(!isConfigOk){
						Log.d(TAG,"等待配置ok");
						if(isStop==false){
							break;
						}
						repeatCount++;
						if(repeatCount>30){
							isStop = false;
						}
						try {
							this.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(contype!=0){
					//当最初连接是路由的话，就切换回去到原来的网络
					if(changeBackToRouter(curSsid,devssid)==false){
						isError = true;
					}
				}
			}else{
				changeBackToRouter(curSsid,devssid);
			}
			if(isError){
				
			}else{
				listener.changeBackToRouter();
			}
			
		}		
	}
	
	/**
	 * 查看以前是否配置过此wifi
	 * */
	public WifiConfiguration isExsists(String ssid){
		List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
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
        	wifiManager.removeNetwork(tempconfig.networkId);
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
	
	public void setConfigOk(){
		isConfigOk = true;
	}
	
	public void setListener(OnWifiChange listener){
		this.listener = listener;
	}
	
	public interface OnWifiChange{
		//切换到设备的热点去
		public void changeToDevAp();
		//切换回路由
		public void changeBackToRouter();
		//路由关闭了
		public void routerCloseNow();
		//错误码
		public void onError(String str);
		
	}
	
}
