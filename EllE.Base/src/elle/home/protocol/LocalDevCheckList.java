package elle.home.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocalDevCheckList {

	//回来的包的队列
	List<OneDevPacket> devlistpacket;
	ProtocolDataList datalisttmp;
	
	Timer timer = new Timer();
	
	public LocalDevCheckList(){
		devlistpacket = new ArrayList<OneDevPacket>();
	}
	
	public void startCheck(){
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*try {
					BasicPacket packet = new BasicPacket(InetAddress.getByName("255.255.255.255"),PublicDefine.LocalUdpPort);
					packet.packCheckPacket(0);
					packet.setImportance(BasicPacket.ImportLow);
					if(datalisttmp!=null){
						datalisttmp.addOnePacketToSend(packet);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
			}}, 1000,2000);
	}
	
	public void stopCheck(){
		timer.cancel();
	}
	
	/**设置外部发送接收队列
	 * @param a
	 */
	public void setDataList(ProtocolDataList a){
		this.datalisttmp = a;
	}
	
	/**处理一个接收到的包
	 * @param packet
	 */
	public void dealOnePacket(PacketCheck packet){
		for(int i=0;i<devlistpacket.size();i++){
			if(devlistpacket.get(i).mac == packet.mac){
				devlistpacket.get(i).setOnePacket(packet);
				return;
			}
		}
		OneDevPacket tmp = new OneDevPacket(packet.mac);
		tmp.setOnePacket(packet);
		devlistpacket.add(tmp);
	}
	
	/**回掉到上层数据区
	 * @param mac
	 * @return
	 */
	public PacketCheck getOnePacket(long mac){
		for(int i=0;i<devlistpacket.size();i++){
			if(devlistpacket.get(i).mac == mac){
				return devlistpacket.get(i).getOnePacket();
			}
		}
		return null;
	}

	public class OneDevPacket{
		
		public long mac;
		public int freshCount;
		public PacketCheck packetcheck;
		
		public OneDevPacket(long mac){
			this.mac = mac;
		}
		
		public void setOnePacket(PacketCheck packet){
			packetcheck = packet;
			freshCount++;
			if(freshCount>200)
				freshCount=200;
		}
		
		public PacketCheck getOnePacket(){
			if(freshCount>0){
				freshCount = 0;
				return packetcheck;
			}else{
				return null;
			}
		}
		
	}
	
}
