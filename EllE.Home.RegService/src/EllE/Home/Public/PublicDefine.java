package EllE.Home.Public;


public class PublicDefine {
	
	public static final int LocalUdpPort = 5880;
	public static final int LocalFindPort = 5879;
	
	public static final byte FunCheck = (byte)0xff;
	
	public static final byte TypePublic = (byte)0x00;
	public static final byte PublicVerOrgin = (byte)0x00;

	public static final byte TypeLight = (byte)0x20;
	public static final byte LightVerOrgin = (byte)0x00;
	public static final byte FunLightOpen = (byte)0x01;
	public static final byte FunLightClose = (byte)0x02;
	public static final byte FunLightColor = (byte)0x03;
	public static final byte FunLightRandom = (byte)0x04;
	
	public static final byte TypePlug = (byte)0x10;
	public static final byte PlugVerOgrin = (byte)0x00;
	public static final byte FunPlugOn = (byte)0x01;
	public static final byte FunPlugOff = (byte)0x02;
	
	public static int seq;
	public static byte[] phonemac = new byte[4];
	
	public static void setPhoneMac(byte[] mac){
		System.arraycopy(mac, 0, phonemac, 0, 4);
	}
	
	public static byte[] getPhoneMac(){
		return phonemac;
	}
	
	public static int getSeq(){
		seq++;
		if(seq==0)
			seq++;
		return seq;
	}
	
	
	
}
