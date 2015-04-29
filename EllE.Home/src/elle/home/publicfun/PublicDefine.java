package elle.home.publicfun;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import elle.home.app.R;

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
	public static final byte GateWayOrgin = (byte)0x00;
	public static final byte GateWayAllowIn = (byte)0x01;
	public static final byte GateWayBan = (byte)0x02;
	public static final byte GateWayRequestDevOut = (byte)0x03;
	public static final byte GateWayPostDevIn = (byte)0x04;
	public static final byte GateWayAllowDevIn = (byte)0x05;
	
	public static final byte TypeLight = (byte)0x20;
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
	
	/**
	 * 由类型得到fragment中的设备图标
	 * */
	public static int getFragmentIconByType(byte type){
		switch(type){
		case TypeLight:
			return R.drawable.icon_light_normal;
		case TypePlug:
			return R.drawable.icon_plug_normal;
		case TypeInfra:
			return R.drawable.icon_infra_normal;
		case TypeInfraAir:
			return R.drawable.icon_infra_air_normal;
		case TypeNull:
			return R.drawable.icon_null_normal;
		case TypeInfraCamera:
			return R.drawable.icon_camera_normal;
		}
		return R.drawable.icon_null_normal;
	}
	
	//小图标
	public static int getLittleIconByType(byte type){
		switch(type){
		case TypeLight:
			return R.drawable.icon_little_light;
		case TypePlug:
			return R.drawable.icon_little_plug;
		case TypeInfra:
			return R.drawable.icon_little_infra;
		case TypeInfraAir:
			return R.drawable.icon_little_air;
		case TypeInfraCamera:
			return R.drawable.icon_camera_normal;
		}
		return R.drawable.icon_little_light;
	}
	
	//得到介绍相应类型的介绍背景
	public static int getIntroduceBkByType(byte type){
		switch(type){
		case TypeLight:
			return R.drawable.introduct_light;
		case TypePlug:
			return R.drawable.introduce_plug;
		case TypeInfra:
			return R.drawable.introduce_infra;
		case TypeInfraAir:
			return R.drawable.introduce_air;
		}
		return R.drawable.common_bk;
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
	
	/**
	 * 得到侧滑栏中界面图标的资源id
	 * */
	public static int getResideLocatIcon(int icon){
		switch(icon){
		case ResideIconLocatHome:
			return R.drawable.reside_icon_home;
		case ResideIconLocatOffice:
			return R.drawable.reside_icon_office;
		case ResideIconLocatApartment:
			return R.drawable.reside_icon_apartment;
		case ResideIconLocatHotel:
			return R.drawable.reside_icon_hotel;
		case ResideIconLocatDorm:
			return R.drawable.reside_icon_dorm;
		case ResideIconLocatCookhouse:
			return R.drawable.reside_icon_cookroom;
		case ResideIconLocatGarden:
			return R.drawable.reside_icon_garden;
		case ResideIconLocatBaby:
			return R.drawable.reside_icon_baby;
		case ResideIconLocatCar:
			return R.drawable.reside_icon_car;
		}
		return R.drawable.reside_icon_home;
	}
	
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
	 * 得到侧滑界面中场景的图标
	 * */
	public static int getSceneResideLogo(int icon){
		switch(icon){
		case ResideIconGoodNight:
			return R.drawable.reside_sense_goodnight;
		case ResideIconBackHome:
			return R.drawable.scene_item_backhome;
		case ResideIconDinner:
			return R.drawable.scene_item_dinner;
		case ResideIconLeaveHome:
			return R.drawable.scene_item_leavehome;
		case ResideIconMovie:
			return R.drawable.scene_item_movie;
		case ResideIconReading:
			return R.drawable.scene_item_reading;
		case ResideIconSport:
			return R.drawable.scene_item_sport;
		case ResideIconTalking:
			return R.drawable.scene_item_talking;
		case ResideIconWorking:
			return R.drawable.scene_item_work;
		}
		return R.drawable.reside_sense_goodnight;
	}
	
	/**
	 * 得到场景管理的图片
	 * */
	public static int getManageSceneIcon(int a){
		return getSceneResideLogo(a);
	}

	/**
	 * 得到设备管理的设备的图片
	 * */
	public static int getManageDevIcon(byte type ){
		return getLittleIconByType(type);
	}
	
	/**
	 * 得到fragment中设备状态的图标
	 * **/
	public static int getConnectFragmentLogo(int status){
		int tmp = 0;
		switch(status){
		case PublicDefine.ConnectLocal:
			tmp = R.drawable.fragment_local;
			break;
		case PublicDefine.ConnectNull:
			tmp = R.drawable.fragment_offline;
			break;
		case PublicDefine.ConnectRemote:
			tmp = R.drawable.fragment_internet;
			break;
		case PublicDefine.ConnectNullIcon:
			tmp = R.drawable.fragment_nullicon;
		}
		return tmp;
	}
	
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
	
	/**
	 *根据类型和功能返回场景显示的功能图片 
	 * */
	public static int getIconByTypeFun(byte type,int funid){
		if(type == PublicDefine.TypeLight){
			switch(funid){
			case SceneLightOnIcon:
				return R.drawable.scene_logo_on;
			case SceneLightOffIcon:
				return R.drawable.scene_logo_off;
			case SceneLightNullIcon:
				return R.drawable.scene_cancel;
			case SceneLightFreeIcon:
				return R.drawable.scene_light_free;
			case SceneLightColor1:
				return R.drawable.scene_light_color1;
			case SceneLightColor2:
				return R.drawable.scene_light_color2;
			case SceneLightColor3:
				return R.drawable.scene_light_color3;
			case SceneLightColor4:
				return R.drawable.scene_light_color4;
			case SceneLightColor5:
				return R.drawable.scene_light_color5;
			case SceneLightColor6:
				return R.drawable.scene_light_color6;
			case SceneLightColor7:
				return R.drawable.scene_light_color7;
			case SceneLightColor8:
				return R.drawable.scene_light_color8;
			}
		}else if(type == PublicDefine.TypePlug){
			switch(funid){
			case ScenePlugOnIcon:
				return R.drawable.scene_logo_on;
			case ScenePlugOffIcon:
				return R.drawable.scene_logo_off;
			case ScenePlugNullIcon:
				return R.drawable.scene_cancel;
			}
		}else if(type == PublicDefine.TypeInfra){
			switch(funid){
			case SceneInfraNullIcon:
				return R.drawable.scene_cancel;
			}
		}else if(type == PublicDefine.TypeInfraAir){
			switch(funid){
			case SceneInfraAirCold16:
				return R.drawable.scene_air_cold16;
			case SceneInfraAirCold24:
				return R.drawable.scene_air_cold24;
			case SceneInfraAirCold30:
				return R.drawable.scene_air_cold30;
			case SceneInfraAirWarm16:
				return R.drawable.scene_air_warm16;
			case SceneInfraAirWarm24:
				return R.drawable.scene_air_warm24;
			case SceneInfraAirWarm30:
				return R.drawable.scene_air_warm30;
			case SceneInfraAirCloseIcon:
				return R.drawable.scene_logo_off;
			case SceneInfraAirNullIcon:
				return R.drawable.scene_cancel;
			}
		}
		return R.drawable.scene_cancel;
	}
	
	/**
	 * 根据类型返回文字
	 * */
	public static String getStringByType(Resources res,byte type){
		switch(type){
		case PublicDefine.TypeInfra:
			
			return res.getString(R.string.type_infra_string);
		case PublicDefine.TypeInfraAir:
			return res.getString(R.string.type_air_string);
		case PublicDefine.TypeInfraTv:
			return res.getString(R.string.type_tv_string);
		case PublicDefine.TypeLight:
			return res.getString(R.string.type_light_string);
		case PublicDefine.TypePlug:
			return res.getString(R.string.type_plug_string);
		}
		return res.getString(R.string.type_unknow_string);
	}
	
	public static void vibratorNormal(Context context){
		Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(50);
	}
	
	
}
