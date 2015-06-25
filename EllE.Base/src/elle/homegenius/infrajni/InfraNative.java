package elle.homegenius.infrajni;

public class InfraNative {
	
	public final static int AIR_TEMP_19			= 0x13;
	public final static int AIR_TEMP_20			= 0x14;
	public final static int AIR_TEMP_21			= 0x15;
	public final static int AIR_TEMP_22			= 0x16;
	public final static int AIR_TEMP_23			= 0x17;
	public final static int AIR_TEMP_24			= 0x18;
	public final static int AIR_TEMP_25			= 0x19;
	public final static int AIR_TEMP_26			= 0x1a;
	public final static int AIR_TEMP_27			= 0x1b;
	public final static int AIR_TEMP_28			= 0x1c;
	public final static int AIR_TEMP_29			= 0x1d;
	public final static int AIR_TEMP_30			= 0x1e;

	public final static int AIR_FLOW_RATE_AUTO		= 0x01;
	public final static int AIR_FLOW_RATE_LOW			= 0x02;
	public final static int AIR_FLOW_RATE_MID			= 0x03;
	public final static int AIR_FLOW_RATE_HIGH		= 0x04;

	public final static int AIR_FLOW_MANUAL_DOWN		= 0x03;
	public final static int AIR_FLOW_MANUAL_MID		= 0x02;
	public final static int AIR_FLOW_MANUAL_UP		= 0x01;

	public final static int AIR_FLOW_AUTO_ON			= 0x01;
	public final static int AIR_FLOW_AUTO_OFF			= 0x00;

	public final static int AIR_ONOFF_ON				= 0x01;
	public final static int AIR_ONOFF_OFF				= 0x00;

	public final static int AIR_KEY_ONOFF				= 0x01;
	public final static int AIR_KEY_MODEL				= 0x02;
	public final static int AIR_KEY_FLOW				= 0x03;
	public final static int AIR_KEY_MANUAL_FLOW		= 0x04;
	public final static int AIR_KEY_AUTO_FLOW			= 0x05;
	public final static int AIR_KEY_TEMP_ADD			= 0x06;
	public final static int AIR_KEY_TEMP_REDUCE		= 0x07;

	public final static int AIR_MODEL_AUTO			= 0x01;
	public final static int AIR_MODEL_COLD			= 0x02;
	public final static int AIR_MODEL_DRY				= 0x03;
	public final static int AIR_MODEL_WIND			= 0x04;
	public final static int AIR_MODEL_HOT				= 0x05;
	
	
	//电视
	public final static int TvFunVolCut				= 0x01;
	public final static int TvFunChPlus				= 0x02;
	public final static int TvFunMenu					= 0x03;
	public final static int TvFunChCut				= 0x04;
	public final static int TvFunVolPlus				= 0x05;
	public final static int TvFunPower				= 0x06;
	public final static int TvFunMute					= 0x07;		//静音
	public final static int TvFun1					= 0x08;
	public final static int TvFun2					= 0x09;
	public final static int TvFun3					= 0x0a;
	public final static int TvFun4					= 0x0b;
	public final static int TvFun5					= 0x0c;
	public final static int TvFun6					= 0x0d;
	public final static int TvFun7					= 0x0e;
	public final static int TvFun8					= 0x0f;
	public final static int TvFun9					= 0x10;
	public final static int TvFunNum					= 0x11;
	public final static int TvFun0					= 0x12;
	public final static int TvFunTVAV					= 0x13;
	public final static int TvFunBack					= 0x14;
	public final static int TvFunSure					= 0x15;
	public final static int TvFunUp					= 0x16;
	public final static int TvFunLeft					= 0x17;
	public final static int TvFunRight				= 0x18;
	public final static int TvFunDown					= 0x19;

	public native static void test();
	public native static int testInt();
	
	public native static String getOneBrandName(int id);
	public native static int getBrandLen();
	
	//处理学习数据
	public native static byte[] exchangeStudyData(byte[] data);
	
	//空调
	public native static String getAirOneBrandNameById(int id,int language);
	public native static int getAirAllBrandListLen();
	
	public native static byte[] getAirCommandByBrand(int brand,int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	public native static byte[] getAirCommandById(int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	public native static int getAirBrandLenById(int id);
	
	
	//电视
	public native static String getTvOneBrandNameById(int id,int language);
	public native static int getTvAllBrandListLen();
	
	public native static int getTvBrandLenById(int id);
	public native static byte[] getTvCommand(int brand,int id,int fun);
	
	static {
		System.loadLibrary("InfraJni");
	}
	
}
