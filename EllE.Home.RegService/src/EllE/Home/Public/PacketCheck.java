package EllE.Home.Public;

import java.net.InetAddress;


public class PacketCheck {
	
	public static final byte HEADH = (byte)0x55;
	public static final byte HEADL = (byte)0xaa;
	public static final byte PHONEHEADH = (byte)0x55;
	public static final byte PHONEHEADL = (byte)0x66;
	
	public static final int BASICLEN = 37;

	public byte[] data;
	int len;
	
	public InetAddress ip;
	public int port;
	
	public byte devfun;
	public byte devver;
	public byte devcode;
	public long mac;
	public int controlDevId;
	public int seq;
	
	public PacketCheck(byte[] data,int len,InetAddress ip,int port){
		this.data = data;
		this.len = len;
		this.ip = ip;
		this.port = port;
		mac = 0;
		controlDevId = 0;
	}
	
	public boolean check(){
		
		int tmp;
		
		if(data==null)
			return false;
		
		if(len<BASICLEN)
			return false;
		
		if(data[0]!=HEADH&&data[0]!=PHONEHEADH)
			return false;
		if(data[1]!=HEADL&&data[1]!=PHONEHEADL)
			return false;
		
		tmp = DataExchange.twoByteToInt(data[2], data[3]);
		if(tmp!=len)
			return false;
		
		mac = DataExchange.eightByteToLong(data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14]);
		controlDevId = DataExchange.fourByteToInt(data[23], data[24], data[25], data[26]);
		
		this.devfun = data[20];
		this.devver = data[19];
		this.devcode = data[18];
		this.seq = DataExchange.twoByteToInt(data[31], data[32]);
		
		return true;
	}
	
}
