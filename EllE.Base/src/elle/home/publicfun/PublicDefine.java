package elle.home.publicfun;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Vibrator;

public class PublicDefine {
	
	public static final String CONFIG_SHAKE_ON = "shake_on";
	public static final String CONFIG_SHAKE_OFF = "shake_off";
	public static final String SHAKE_DAY = "SHAKE_DAY";
	public static final String SHAKE_NIGHT = "SHAKE_NIGHT";
	
	public static final int LocalUdpPort = 5880;
	public static final int LocalFindPort = 5879;
	
	public static final int RemoteRegServicePort = 30001;
	
	public static final int ConnectNull = 0;
	public static final int ConnectLocal = 1;
	public static final int ConnectRemote = 2;
	public static final int ConnectNullIcon = 3;
	
	public static final byte FunCheck = (byte)0xff;
	public static final byte FunCheckBack = (byte)0xfe;
	public static final byte FunReg = (byte)0xfb;
	public static final byte FunRegBack = (byte)0xfa;
	public static final byte FunReset = (byte)0xf7;
	public static final byte FunWiFiConfig = (byte)0xf0;
	
	public static final byte TypePublic = (byte)0x00;
	public static final byte PublicVerOrgin = (byte)0x00;
	
	public static final byte TypeNull = (byte)0x00;

	public static final byte TypeGateWay = (byte)0x01;
	public static final byte FunGetGateWayMac = (byte)0x04;
	public static final byte GateWayOrgin = (byte)0x01;
	public static final byte GateWayAllowIn = (byte)0x01;
	public static final byte GateWayBan = (byte)0x02;
	public static final byte GateWayRequestDevOut = (byte)0x03;
	public static final byte GateWayPostDevIn = (byte)0x04;
	public static final byte GateWayAllowDevIn = (byte)0x05;
	
	/*zigbee多路控制器*/
	public static final byte TypeController = (byte)0x11;
	public static final byte TypeFreeStickers = (byte)0x31;
	public static final byte CLEAR_FreeStickers = (byte)0x02;
	public static final byte VerControllers[] = {(byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04};
	public static final byte FunControllersSwitch[] = {(byte)0x10, (byte)0x20, (byte)0x30, (byte)0x40};
	public static final byte FunControllersTimeOn[] = {(byte)0x11, (byte)0x21, (byte)0x31, (byte)0x41};
	public static final byte FunControllersTimeOff[] = {(byte)0x12, (byte)0x22, (byte)0x32, (byte)0x42};
	
	public static final byte TypeLight = (byte)0x20;
	public static final byte TypeBigLight = (byte)0x21;
	public static final byte LightVerOrgin = (byte)0x00;
	public static final byte FunLightOpen = (byte)0x01;
	public static final byte FunLightClose = (byte)0x02;
	public static final byte FunLightColor = (byte)0x03;
	public static final byte FunLightRandom = (byte)0x04;
	public static final byte FunLightSleep = (byte)0x05;
	
	public static final byte TypePlug = (byte)0x10;
	public static final byte PlugVerOgrin = (byte)0x00;
	public static final byte FunPlugOn = (byte)0x01;
	public static final byte FunPlugOff = (byte)0x02;
	public static final byte FunPlugSetOpen = (byte)0x03;
	public static final byte FunPlugSetClose = (byte)0x04;
	
	public static final byte TypeInfra = (byte)0x30;
	public static final byte TypeInfraAir = (byte)0x31;
	public static final byte TypeInfraTv = (byte)0x32;
	public static final byte TypeInfraCamera = (byte)0x33;
	public static final byte InfraVerOgrin = (byte)0x00;
	public static final byte FunInfraStudy = (byte)0x01;
	public static final byte FunInfraQuit = (byte)0x02;
	public static final byte FunInfraStudyData = (byte)0x03;
	public static final byte FunInfraSendData = (byte)0x04;
	
	public static final byte TypeCurtain = (byte)0x50;
	public static final byte CurtainVerOgrin = (byte)0x00;
	public static final byte CurtainOn = (byte)0x01;
	public static final byte CurtainOff = (byte)0x02;
	public static final byte CurtainStop = (byte)0x03;
	public static final byte CurtainProgress = (byte)0x04;
	public static final byte CurtainReboot = (byte)0x05;
	
	public static int seq;
	public static byte[] phonemac = new byte[4];
	
	public static void setPhoneMac(byte[] mac){
		System.arraycopy(mac, 0, phonemac, 0, 4);
	}
	
	public static byte[] getPhoneMac(){
		return phonemac;
	}
	
	public static int getSeq(){
		seq = seq+1;
		if(seq==0)
			seq = seq+1;
		return seq;
	}
	
	public static final int ResideIconLocatHome = 0;	
	public static final int ResideIconLocatApartment = 1;
	public static final int ResideIconLocatBaby = 2;
	public static final int ResideIconLocatCar = 3;
	public static final int ResideIconLocatCookhouse = 4;
	public static final int ResideIconLocatDorm = 5;
	public static final int ResideIconLocatGarden = 6;
	public static final int ResideIconLocatHotel = 7;
	public static final int ResideIconLocatOffice = 8;
	
	public static final int ResideIconGoodNight = 0;
	public static final int ResideIconBackHome = 1;
	public static final int ResideIconDinner = 2;
	public static final int ResideIconLeaveHome = 3;
	public static final int ResideIconMovie = 4;
	public static final int ResideIconReading = 5;
	public static final int ResideIconSport = 6;
	public static final int ResideIconTalking = 7;
	public static final int ResideIconWorking = 8;
	
	/**
	 * 检查wifi的连接状态，是否连接了
	 * */
	public static boolean isWiFiConnect(Context context){
		
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
  
        if(wifi == State.CONNECTED || wifi == State.CONNECTING){  
        	return true;
        } 
		return false;
	}
	
    /**
     * 设置是否打开Wifi 
     * @param context
     * @param enabled
     */
	public static void toggleWiFi(Context context, boolean enabled) { 
       WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
       wifiManager.setWifiEnabled(enabled); 
   }
   
	public static final int ScenePlugOnIcon = 0;
	public static final int ScenePlugOffIcon = 1;
	public static final int ScenePlugNullIcon = 2;
	
	public static final int SceneLightOnIcon = 0;
	public static final int SceneLightOffIcon = 1;
	public static final int SceneLightFreeIcon = 2;
	public static final int SceneLightNullIcon = 3;
	public static final int SceneLightColor1 = 4;
	public static final int SceneLightColor2 = 5;
	public static final int SceneLightColor3 = 6;
	public static final int SceneLightColor4 = 7;
	public static final int SceneLightColor5 = 8;
	public static final int SceneLightColor6 = 9;
	public static final int SceneLightColor7 = 10;
	public static final int SceneLightColor8 = 11;
	
	public static final int SceneInfraNullIcon = 0;
	
	//public static final int SceneInfraAirWarmIcon = 0;
	//public static final int SceneInfraAirColdIcon = 1;
	public static final int SceneInfraAirCold16 = 0;
	public static final int SceneInfraAirCold24 = 1;
	public static final int SceneInfraAirCold30 = 2;
	public static final int SceneInfraAirWarm16 = 4;
	public static final int SceneInfraAirWarm24 = 5;
	public static final int SceneInfraAirWarm30 = 6;
	public static final int SceneInfraAirCloseIcon = 3;
	public static final int SceneInfraAirNullIcon = 7;
	
	public static void vibratorNormal(Context context){
		Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(50);
	}
	
}
