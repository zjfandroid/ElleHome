package elle.home.database;

public class OneInfraData {

	public String devname;
	public long mac;
	public byte type;
	public int funid;
	public byte[] data;
	
	public OneInfraData(){
		
	};
	
	public void setData(String devname,long mac,byte type,int funid,byte[] data){
		this.devname = devname;
		this.mac = mac;
		this.type = type;
		this.funid = funid;
		this.data = data;
	}
	
	
	
}
