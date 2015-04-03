package EllE.Home.RegService;

import java.net.InetAddress;



import EllE.Home.Public.BasicPacket;
import EllE.Home.Public.DataExchange;

public class RegPacket extends BasicPacket {

	public static final byte FunRegBack = (byte)0xfa;
	
	public byte[] xdata;
	
	public RegPacket(InetAddress ip, int port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
		xdata = new byte[6];
		
	}
	
	public void setControlIp(InetAddress ip,int port){
		if(ip!=null)
		{
			byte[] tmp;
			tmp = ip.getAddress();
			xdata[0] = tmp[0];
			xdata[1] = tmp[1];
			xdata[2] = tmp[2];
			xdata[3] = tmp[3];
			
			tmp = DataExchange.intToTwoByte(port);
			xdata[4] = tmp[0];
			xdata[5] = tmp[1];
			
		}
	}
	
	
	
	

}
