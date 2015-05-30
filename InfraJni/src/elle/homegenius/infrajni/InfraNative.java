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

	public native void test();
	public native int testInt();
	
	public native String getOneBrandName(int id);
	public native int getBrandLen();
	
	public native byte[] getAirCommandByBrand(int brand,int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	public native byte[] getAirCommandById(int id,int temp,int airflowRate,int manualFlow,int autoFlow,int onoff,int key,int model);	
	
	public native String getAirOneBrandNameById(int id,int language);
	public native int getAirBrandLenById(int id);
	public native int getAirAllBrandListLen();
	
	static {
		System.loadLibrary("InfraJni");
	}
	
}
