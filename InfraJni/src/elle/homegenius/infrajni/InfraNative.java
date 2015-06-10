package elle.homegenius.infrajni;

public class InfraNative {
	
	public static int AIR_TEMP_19			= 0x13;
	public static int AIR_TEMP_20			= 0x14;
	public static int AIR_TEMP_21			= 0x15;
	public static int AIR_TEMP_22			= 0x16;
	public static int AIR_TEMP_23			= 0x17;
	public static int AIR_TEMP_24			= 0x18;
	public static int AIR_TEMP_25			= 0x19;
	public static int AIR_TEMP_26			= 0x1a;
	public static int AIR_TEMP_27			= 0x1b;
	public static int AIR_TEMP_28			= 0x1c;
	public static int AIR_TEMP_29			= 0x1d;
	public static int AIR_TEMP_30			= 0x1e;

	public static int AIR_FLOW_RATE_AUTO		= 0x01;
	public static int AIR_FLOW_RATE_LOW			= 0x02;
	public static int AIR_FLOW_RATE_MID			= 0x03;
	public static int AIR_FLOW_RATE_HIGH		= 0x04;

	public static int AIR_FLOW_MANUAL_DOWN		= 0x03;
	public static int AIR_FLOW_MANUAL_MID		= 0x02;
	public static int AIR_FLOW_MANUAL_UP		= 0x01;

	public static int AIR_FLOW_AUTO_ON			= 0x01;
	public static int AIR_FLOW_AUTO_OFF			= 0x00;

	public static int AIR_ONOFF_ON				= 0x01;
	public static int AIR_ONOFF_OFF				= 0x00;

	public static int AIR_KEY_ONOFF				= 0x01;
	public static int AIR_KEY_MODEL				= 0x02;
	public static int AIR_KEY_FLOW				= 0x03;
	public static int AIR_KEY_MANUAL_FLOW		= 0x04;
	public static int AIR_KEY_AUTO_FLOW			= 0x05;
	public static int AIR_KEY_TEMP_ADD			= 0x06;
	public static int AIR_KEY_TEMP_REDUCE		= 0x07;

	public static int AIR_MODEL_AUTO			= 0x01;
	public static int AIR_MODEL_COLD			= 0x02;
	public static int AIR_MODEL_DRY				= 0x03;
	public static int AIR_MODEL_WIND			= 0x04;
	public static int AIR_MODEL_HOT				= 0x05;
	
	
	//电视
	public static int TvFunVolCut				= 0x01;
	public static int TvFunChPlus				= 0x02;
	public static int TvFunMenu					= 0x03;
	public static int TvFunChCut				= 0x04;
	public static int TvFunVolPlus				= 0x05;
	public static int TvFunPower				= 0x06;
	public static int TvFunMute					= 0x07;		//静音
	public static int TvFun1					= 0x08;
	public static int TvFun2					= 0x09;
	public static int TvFun3					= 0x0a;
	public static int TvFun4					= 0x0b;
	public static int TvFun5					= 0x0c;
	public static int TvFun6					= 0x0d;
	public static int TvFun7					= 0x0e;
	public static int TvFun8					= 0x0f;
	public static int TvFun9					= 0x10;
	public static int TvFunNum					= 0x11;
	public static int TvFun0					= 0x12;
	public static int TvFunTVAV					= 0x13;
	public static int TvFunBack					= 0x14;
	public static int TvFunSure					= 0x15;
	public static int TvFunUp					= 0x16;
	public static int TvFunLeft					= 0x17;
	public static int TvFunRight				= 0x18;
	public static int TvFunDown					= 0x19;

	public native void test();
	public native int testInt();
	
	public native String getOneBrandName(int id);
	public native int getBrandLen();
	
	//处理学习数据
	public native byte[] exchangeStudyData(byte[] data);
	
	//空调
	public native String getAirOneBrandNameById(int id,int language);
	public native int getAirAllBrandListLen();
	
	public native byte[] getAirCommandByBrand(int brand,int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	public native byte[] getAirCommandById(int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	public native int getAirBrandLenById(int id);
	
	
	//电视
	public native String getTvOneBrandNameById(int id,int language);
	public native int getTvAllBrandListLen();
	
	public native int getTvBrandLenById(int id);
	public native byte[] getTvCommand(int brand,int id,int fun);
	
	static {
		System.loadLibrary("InfraJni");
	}
	
}
