package EllE.Home.RegService;

public class InitService {

	
	public static DevRegUdpService service;
	
	public static void main(String[] args){
		service = new DevRegUdpService();
		service.startService();
	}
	
}
