package EllE.Home.ControlService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import EllE.Home.Public.DataExchange;
import EllE.Home.Public.PacketCheck;
import EllE.Home.RegService.CBsendToClient;
import EllE.Home.RegService.RegClient;
import EllE.Home.RegService.DevRegUdpService.RecvThread;
import EllE.Home.RegService.DevRegUdpService.SendThread;

public class DevControlUdpService {
	public static final String TAG = "DevControlService";
	
	public DatagramSocket UDPSOCKET;
	
	//发送队列表
	List<DatagramPacket> sendList;
	
	CoordinateService coordinate;
	
	SendThread sendThread;
	RecvThread recvThread;
	
	public DevControlUdpService(CoordinateService coordinate){
		if(BondPort(30002)==true){
			//System.out.println("启动udp成功");
			sendList = new ArrayList<DatagramPacket>();
			this.coordinate = coordinate;
		}
	}
	
	public void startService(){
		if(UDPSOCKET!=null){
			sendThread = new SendThread();
			recvThread = new RecvThread();
			
			sendThread.start();
			recvThread.start();
			
		}
	}
	
	private boolean BondPort(int port){
		try {
			UDPSOCKET = new DatagramSocket(port);
			return true;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized void addSendPacketToList(DatagramPacket packet){
		sendList.add(packet);
	}
	
	public synchronized DatagramPacket getSendPacketList(){
		DatagramPacket tmp = null;
		if(sendList.size()>0){
			tmp = sendList.get(0);
			sendList.remove(0);
		}
		return tmp;
	}
	public class RecvThread extends Thread{
		
		public boolean runFlag = true;
		
		void stopThis(){
			runFlag = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			byte[] data = new byte[4096];
			int datalen;
			runFlag = true;
			while(runFlag){				
				DatagramPacket packet = new DatagramPacket(data,data.length);
				try {
					UDPSOCKET.receive(packet);
					datalen = packet.getLength();
					if(datalen>0){
						byte[] xdata= new byte[datalen];
						System.arraycopy(data, 0, xdata, 0, datalen);
						//System.out.println("控制服务器ip:"+packet.getAddress()+" 控制服务器port:"+packet.getPort());
						//System.out.println("控制服务器收到数据："+DataExchange.byteArrayToHexString(xdata));
						PacketCheck packetcheck = new PacketCheck(xdata,datalen,packet.getAddress(),packet.getPort());
						if(packetcheck.check()==true){
							//System.out.println("控制服务器检查通过,回掉处理");
							coordinate.dealPacket(packetcheck);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		
		
	}
	
	public class SendThread extends Thread{
		
		public boolean runFlag = true;
		
		void stopThis(){
			runFlag = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			runFlag = true;
			while(runFlag){
				DatagramPacket sendpacket= getSendPacketList();
				if(sendpacket!=null){
					try {
						UDPSOCKET.send(sendpacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						this.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
			}			
		}
	}	
	
}
