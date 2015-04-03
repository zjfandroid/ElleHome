package EllE.Home.ControlService;

import java.net.InetAddress;

public class ControlDevInfo {

	public int controlid;
	public InetAddress controlDevIp;
	public int controlDevPort;
	
	public ControlDevInfo(int id,InetAddress ip,int port){
		this.controlid = id;
		this.controlDevIp = ip;
		this.controlDevPort = port;
	}
	
	public void freshControlDev(InetAddress ip,int port){
		if(controlDevIp.hashCode()!=ip.hashCode()||controlDevPort!=port){
			this.controlDevIp = ip;
			this.controlDevPort = port;
		}
	}
	
}
