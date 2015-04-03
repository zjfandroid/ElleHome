package EllE.Home.ControlService;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NodeDevInfo {
	
	public long mac;
	public InetAddress ip;
	public int port;
	
	List<ControlDevInfo> controlDevInfo;
	
	public NodeDevInfo(long mac,InetAddress ip,int port){
		this.mac = mac;
		this.ip = ip;
		this.port = port;
		controlDevInfo = new ArrayList<ControlDevInfo>();
	}
	
	public void freshNodeIp(InetAddress xip,int port){
		if(ip.hashCode()!=xip.hashCode()||this.port!=port){
			this.ip = xip;
			this.port = port;
		}
	}
	
	public void addControlDev(ControlDevInfo dev){
		controlDevInfo.add(dev);
	}
	
	public ControlDevInfo findControlDev(int controlid){
		ControlDevInfo tmp = null;
		for(int i=0;i<controlDevInfo.size();i++){
			if(controlDevInfo.get(i).controlid == controlid){
				tmp = controlDevInfo.get(i);
				return tmp;
			}
		}
		return tmp;
	}
	
}
