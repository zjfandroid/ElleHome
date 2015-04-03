package EllE.Home.RegService;

public class DevRegInfo {

	public long devmac;
	public String devip;
	public int devport;
	public String devlocation;
	
	public DevRegInfo(long devmac,String ip,int port){
		this.devmac = devmac;
		this.devip = ip;
		this.devport = port;
	}
	
	
}
