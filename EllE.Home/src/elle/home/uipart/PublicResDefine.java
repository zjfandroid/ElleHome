package elle.home.uipart;

import android.content.res.Resources;
import elle.home.app.smart.R;
import elle.home.publicfun.PublicDefine;

public class PublicResDefine {
	
	/**
	 * 由类型得到fragment中的设备图标
	 * */
	public static int getFragmentIconByType(byte type){
		switch(type){
		case PublicDefine.TypeLight:
			return R.drawable.icon_light_normal;
		case PublicDefine.TypePlug:
			return R.drawable.icon_plug_normal;
		case PublicDefine.TypeInfra:
			return R.drawable.icon_infra_normal;
		case PublicDefine.TypeInfraAir:
			return R.drawable.icon_infra_air_normal;
		case PublicDefine.TypeNull:
			return R.drawable.icon_null_normal;
		case PublicDefine.TypeInfraCamera:
			return R.drawable.icon_camera_normal;
		case PublicDefine.TypeController:
		case PublicDefine.TypeGateWay:
			return R.drawable.icon_gateway_normal;
		case PublicDefine.TypeCurtain:
			return R.drawable.curtain;
			
		}
		return R.drawable.icon_null_normal;
	}
	
	//小图标
	public static int getLittleIconByType(byte type){
		switch(type){
		case PublicDefine.TypeLight:
			return R.drawable.icon_little_light;
		case PublicDefine.TypePlug:
			return R.drawable.icon_little_plug;
		case PublicDefine.TypeInfra:
			return R.drawable.icon_little_infra;
		case PublicDefine.TypeInfraAir:
			return R.drawable.icon_little_air;
		case PublicDefine.TypeInfraCamera:
			return R.drawable.icon_camera_normal;
		case PublicDefine.TypeGateWay:
		case PublicDefine.TypeController:
			return R.drawable.icon_gateway_normal;
		}
		return R.drawable.icon_little_light;
	}
	
	/**
	 * 得到侧滑栏中界面图标的资源id
	 * */
	public static int getResideLocatIcon(int icon){
		switch(icon){
		case PublicDefine.ResideIconLocatHome:
			return R.drawable.reside_icon_home;
		case PublicDefine.ResideIconLocatOffice:
			return R.drawable.reside_icon_office;
		case PublicDefine.ResideIconLocatApartment:
			return R.drawable.reside_icon_apartment;
		case PublicDefine.ResideIconLocatHotel:
			return R.drawable.reside_icon_hotel;
		case PublicDefine.ResideIconLocatDorm:
			return R.drawable.reside_icon_dorm;
		case PublicDefine.ResideIconLocatCookhouse:
			return R.drawable.reside_icon_cookroom;
		case PublicDefine.ResideIconLocatGarden:
			return R.drawable.reside_icon_garden;
		case PublicDefine.ResideIconLocatBaby:
			return R.drawable.reside_icon_baby;
		case PublicDefine.ResideIconLocatCar:
			return R.drawable.reside_icon_car;
		}
		return R.drawable.reside_icon_home;
	}

	/**
	 * 得到侧滑界面中场景的图标
	 * */
	public static int getSceneResideLogo(int icon){
		switch(icon){
		case PublicDefine.ResideIconGoodNight:
			return R.drawable.reside_sense_goodnight;
		case PublicDefine.ResideIconBackHome:
			return R.drawable.scene_item_backhome;
		case PublicDefine.ResideIconDinner:
			return R.drawable.scene_item_dinner;
		case PublicDefine.ResideIconLeaveHome:
			return R.drawable.scene_item_leavehome;
		case PublicDefine.ResideIconMovie:
			return R.drawable.scene_item_movie;
		case PublicDefine.ResideIconReading:
			return R.drawable.scene_item_reading;
		case PublicDefine.ResideIconSport:
			return R.drawable.scene_item_sport;
		case PublicDefine.ResideIconTalking:
			return R.drawable.scene_item_talking;
		case PublicDefine.ResideIconWorking:
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
	 *根据类型和功能返回场景显示的功能图片 
	 * */
	public static int getIconByTypeFun(byte type,int funid){
		if(type == PublicDefine.TypeLight){
			switch(funid){
			case PublicDefine.SceneLightOnIcon:
				return R.drawable.scene_logo_on;
			case PublicDefine.SceneLightOffIcon:
				return R.drawable.scene_logo_off;
			case PublicDefine.SceneLightNullIcon:
				return R.drawable.scene_cancel;
			case PublicDefine.SceneLightFreeIcon:
				return R.drawable.scene_light_free;
			case PublicDefine.SceneLightColor1:
				return R.drawable.scene_light_color1;
			case PublicDefine.SceneLightColor2:
				return R.drawable.scene_light_color2;
			case PublicDefine.SceneLightColor3:
				return R.drawable.scene_light_color3;
			case PublicDefine.SceneLightColor4:
				return R.drawable.scene_light_color4;
			case PublicDefine.SceneLightColor5:
				return R.drawable.scene_light_color5;
			case PublicDefine.SceneLightColor6:
				return R.drawable.scene_light_color6;
			case PublicDefine.SceneLightColor7:
				return R.drawable.scene_light_color7;
			case PublicDefine.SceneLightColor8:
				return R.drawable.scene_light_color8;
			}
		}else if(type == PublicDefine.TypePlug){
			switch(funid){
			case PublicDefine.ScenePlugOnIcon:
				return R.drawable.scene_logo_on;
			case PublicDefine.ScenePlugOffIcon:
				return R.drawable.scene_logo_off;
			case PublicDefine.ScenePlugNullIcon:
				return R.drawable.scene_cancel;
			}
		}else if(type == PublicDefine.TypeInfra){
			switch(funid){
			case PublicDefine.SceneInfraNullIcon:
				return R.drawable.scene_cancel;
			}
		}else if(type == PublicDefine.TypeInfraAir){
			switch(funid){
			case PublicDefine.SceneInfraAirCold16:
				return R.drawable.scene_air_cold16;
			case PublicDefine.SceneInfraAirCold24:
				return R.drawable.scene_air_cold24;
			case PublicDefine.SceneInfraAirCold30:
				return R.drawable.scene_air_cold30;
			case PublicDefine.SceneInfraAirWarm16:
				return R.drawable.scene_air_warm16;
			case PublicDefine.SceneInfraAirWarm24:
				return R.drawable.scene_air_warm24;
			case PublicDefine.SceneInfraAirWarm30:
				return R.drawable.scene_air_warm30;
			case PublicDefine.SceneInfraAirCloseIcon:
				return R.drawable.scene_logo_off;
			case PublicDefine.SceneInfraAirNullIcon:
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
		case PublicDefine.TypeGateWay:
			return res.getString(R.string.type_gateway);
		}
		return res.getString(R.string.type_unknow_string);
	}
	
}
