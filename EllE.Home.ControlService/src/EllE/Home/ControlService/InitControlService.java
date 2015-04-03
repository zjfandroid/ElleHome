package EllE.Home.ControlService;

public class InitControlService {

	public static CoordinateService service;
	
	public static void main(String[] args){
		service = new CoordinateService();
		service.startService();
		System.out.println("启动控制服务器");
	}
	
}
