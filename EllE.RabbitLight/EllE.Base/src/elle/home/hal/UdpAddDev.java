package elle.home.hal;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import elle.home.publicfun.PublicDefine;

public class UdpAddDev extends UdpPublic {

	OnUdpRecv onUdpRecv;
	UdpAddDev self;
	public boolean isFindDev;
	public boolean isConfig;
	
	OnAddDev onlistener;
	
	String ssid;
	String password;
	
	public void setListener(OnAddDev a){
		this.onlistener = a;
	}
	
	private Timer timer = new Timer();
	//发送查询设备是否存在
	private TimerTask task = new TimerTask(){

		@Override
		public void run() {
			if(isFindDev==false){
				try {
					String check = "EllE.Home,\"ssid\",\"password\",\"1\"";
					//Log.d(TAG,"check");
					DatagramPacket packet1 = new DatagramPacket(check.getBytes(),check.getBytes().length,InetAddress.getByName("255.255.255.255"),PublicDefine.LocalFindPort);
					self.sendData(packet1);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(isConfig==false){
					if(ssid!=null){
						String check = "EllE.Home,\""+ssid+"\",\""+password+"\",\"3\"";
						DatagramPacket packet1;
						try {
							packet1 = new DatagramPacket(check.getBytes(),check.getBytes().length,InetAddress.getByName("255.255.255.255"),PublicDefine.LocalFindPort);
							self.sendData(packet1);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					timer.cancel();
				}
			}
		}};
	
	public UdpAddDev(){
		self = this;
		isFindDev = false;
		isConfig = false;
		onUdpRecv = new OnUdpRecv(){

			@Override
			public void onRecv(DatagramPacket packet) {
				// TODO Auto-generated method stub
				byte[] data = new byte[packet.getLength()];
				System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());
				String tmp = new String(data);
				Log.d(TAG,"recvpacket:"+tmp);
				if(tmp.equals("EllE.Home,\"ssid\",\"password\",\"2\"")){	//检查是否有这个设备
					isFindDev = true;
					if(onlistener!=null){
						onlistener.onGetBack();
					}
					
				}else if(tmp.equals("EllE.Home,\"ssid\",\"password\",\"4\"")){	//检查密码是否发送成功
					isConfig = true;
					if(onlistener!=null){
						onlistener.onConfigOk();
					}
				}else{
					
				}
				
			}
			
		};
		super.setListenerUdpRecv(onUdpRecv);
	}
	
	public void sendConfig(String ssid,String password){
		this.ssid = ssid;
		this.password = password;
		
	}
	
	public void startCheck(){
		super.startnow();
		timer.schedule(task, 100,300);
	}
	
	public void stopCheck(){
		timer.cancel();
		super.stopnow();
	}
	
	public interface OnAddDev{
		void onGetBack();
		void onConfigOk();
		
	}

}


