package EllE.Home.RegService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import EllE.Home.Public.BasicPacket;
import EllE.Home.Public.DataExchange;
import EllE.Home.Public.PacketCheck;

public class DevRegUdpService {

	public static final String TAG = "DevRegUdpService";
	
	public DatagramSocket UDPSOCKET;
	
	//发送队列表
	List<BasicPacket> sendList;
	
	SendThread sendThread;
	RecvThread recvThread;
	
	public DevRegUdpService(){
		if(BondPort(30001)==true){
			sendList = new ArrayList<BasicPacket>();
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
	
	public synchronized void addSendPacketToList(BasicPacket packet){
		sendList.add(packet);
	}
	
	public synchronized BasicPacket getSendPacketList(){
		BasicPacket tmp = null;
		if(sendList.size()>0){
			tmp = sendList.get(0);
			sendList.remove(0);
		}
		return tmp;
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
				BasicPacket sendpacket= getSendPacketList();
				if(sendpacket!=null){
					DatagramPacket dataPacket = new DatagramPacket(sendpacket.data,
							sendpacket.data.length,sendpacket.ip,sendpacket.port);
					try {
						UDPSOCKET.send(dataPacket);
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
						//System.out.println("ip:"+packet.getAddress()+" port:"+packet.getPort());
						//System.out.println("收到数据："+DataExchange.byteArrayToHexString(xdata));
						PacketCheck packetcheck = new PacketCheck(xdata,datalen,packet.getAddress(),packet.getPort());
						if(packetcheck.check()==true){
							//System.out.println("检查通过,交由客户线程处理");
							RegClient regClient = new RegClient(packetcheck,new CBsendToClient(){

								public boolean sendPacket(BasicPacket packet) {
									// TODO Auto-generated method stub
									addSendPacketToList(packet);
									return true;
								}});
							regClient.startdeal();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
