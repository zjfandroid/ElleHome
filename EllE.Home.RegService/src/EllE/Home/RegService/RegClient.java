package EllE.Home.RegService;

import java.net.InetAddress;
import java.net.UnknownHostException;

import EllE.Home.Public.DataExchange;
import EllE.Home.Public.PacketCheck;

public class RegClient {
	
	static final byte FunDevReg = (byte)0xfb; 

	PacketCheck packet;
	RegPacket regPacket;
	CBsendToClient cbSendToClient;
	
	public InetAddress clientip;
	public int clientport;
	
	public RegClient(PacketCheck packet,CBsendToClient cbSendToClient){
		this.packet = packet;
		this.cbSendToClient = cbSendToClient;
	}
	
	public void startdeal(){
		
		if(packet.devfun == FunDevReg){
			RegClientThread client = new RegClientThread();
			client.start();
		}
	}
	
	/**
	 *给设备分配一个控制服务器地址 
	 */
	public void setClientControlService(){
		try {
			
			DevRegControlSql sql = new DevRegControlSql();
			DevRegInfo info = sql.findOneDev(packet.mac);
			
			if(info==null){
				
				info = new DevRegInfo(packet.mac,"198.199.115.33",30002);
				sql.addNewOne(info);
				clientip = InetAddress.getByName("198.199.115.33");
				clientport = 30002;
				sql.stopCon();
				
				//System.out.println("没有该设备，加入到数据库");
				
			}else{
				//System.out.println("找到该设备，使用数据库中的信息");
				clientip = InetAddress.getByName(info.devip);
				clientport = info.devport;
			}
	
			regPacket = new RegPacket(packet.ip,packet.port);
			regPacket.setControlIp(clientip,clientport);

			
			if(packet.data[1]==PacketCheck.HEADL){
				regPacket.packData(packet.devcode, packet.devver, (byte)0xfa, 
						DataExchange.longToEightByte(packet.mac), DataExchange.intToFourByte(packet.controlDevId),
						packet.seq, regPacket.xdata);	
			}else if(packet.data[1]==PacketCheck.PHONEHEADL){
				regPacket.packDataToService(packet.devcode, packet.devver, (byte)0xfa, 
						DataExchange.longToEightByte(packet.mac), DataExchange.intToFourByte(packet.controlDevId),
						packet.seq, regPacket.xdata);	
			}
			
					
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class RegClientThread extends Thread{

		boolean runFlag ;
		int timecount;
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			setClientControlService();
			
			timecount = 0;
			runFlag = true;
			while(runFlag){
				if(cbSendToClient.sendPacket(regPacket)==true){
					//加入发送队列ok
					runFlag = false;
				}
				else{
					timecount++;
					if(timecount>100){
						//处理完客户信息后加入发送队列失败
						runFlag = false;
					}
				}
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
