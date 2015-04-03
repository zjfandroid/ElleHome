package EllE.Home.ControlService;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
	
import EllE.Home.Public.PacketCheck;
	
public class CoordinateService {
	
	List<NodeDevInfo> nodeDevInfo;
	DevControlUdpService udpservice;
	
	public CoordinateService(){
		
		nodeDevInfo = new ArrayList<NodeDevInfo>();
		udpservice = new DevControlUdpService(this);
	}
	
	public void startService(){
		udpservice.startService();
	}
	
	public synchronized void addNodeToList(NodeDevInfo node){
		nodeDevInfo.add(node);
	}
	
	 /**按照mac查找节点
	 * @param mac
	 * @return
	 */
	public synchronized NodeDevInfo findNodeByMac(long mac){
		NodeDevInfo tmp = null;
		for(int i=0;i<nodeDevInfo.size();i++){
			if(nodeDevInfo.get(i).mac == mac){
				tmp = nodeDevInfo.get(i);
				return tmp;
			}
		}
		return tmp;
	}
	
	 /**
	  * 由控制设备id找到控制设备
	 * @param controlid
	 * @return
	 */
	public ControlDevInfo findControlIdByNode(long mac,int controlid){
		ControlDevInfo tmp = null;
		NodeDevInfo nodetmp = findNodeByMac(mac);
		if(nodetmp!=null){
			tmp = nodetmp.findControlDev(controlid);
		}
		return tmp;
	}
	
	/**处理来自客户端的包
	 * @param packet
	 */
	public void dealControlDevPacket(PacketCheck packet){
		NodeDevInfo nodetmp = findNodeByMac(packet.mac);
		if(nodetmp!=null){
			ControlDevInfo tmp = nodetmp.findControlDev(packet.controlDevId);
			if(tmp!=null){
				//向设备转发此包
				tmp.freshControlDev(packet.ip, packet.port);
			}else{
				//将控制设备的信息加入设备的会话记录中
				//System.out.println("向节点中加入控制设备，controlid："+packet.controlDevId+" ip:"+packet.ip+" port:"+packet.ip);
				ControlDevInfo xtmp = new ControlDevInfo(packet.controlDevId,packet.ip,packet.port);
				nodetmp.addControlDev(xtmp);
			}
			byte[] data = new byte[packet.data.length];
			System.arraycopy(packet.data, 0, data, 0, packet.data.length);
			data[1] = PacketCheck.HEADL;			
			
			//System.out.println("向节点转发包ip:"+nodetmp.ip+" port:"+nodetmp.port);
			
			DatagramPacket datapcket = new DatagramPacket(data,data.length,nodetmp.ip,nodetmp.port);
			udpservice.addSendPacketToList(datapcket);
		}else{
			//控制服务器无此设备
			//System.out.println("节点未登陆控制服务器mac："+packet.mac);
		}
	}
	
	/**处理来自节点的包
	 * @param packet
	 */
	public void dealNodePacket(PacketCheck packet){
		if(packet.devfun==(byte)0xf9){
			//心跳包，无需回复,更新设备心跳
			NodeDevInfo nodetmp = findNodeByMac(packet.mac);
			if(nodetmp==null){
				//加入一个新的节点设备
				//System.out.println("加入了一个新的设备："+packet.mac+" 来自："+packet.ip+" port:"+packet.port);
				NodeDevInfo node = new NodeDevInfo(packet.mac,packet.ip,packet.port);
				addNodeToList(node);
			}else{
				//更新节点在公网上的ip地址和端口
				nodetmp.freshNodeIp(packet.ip, packet.port);
			}
		}else{
			//需要转发的包,如果查不到对应的controlid，则说明有人在攻击服务器
			ControlDevInfo tmp = findControlIdByNode(packet.mac,packet.controlDevId);
			if(tmp!=null){
				//System.out.println("节点的包要转发到控制设备 mac："+packet.mac+" cip:"+tmp.controlDevIp+" cport:"+tmp.controlDevPort);
				DatagramPacket datapcket = new DatagramPacket(packet.data,packet.data.length,tmp.controlDevIp,tmp.controlDevPort);
				udpservice.addSendPacketToList(datapcket);
			}else{
				//System.out.println("节点返回的包无法查找到控制设备的信息，问题！");
			}
		}
	}
	
	public void dealPacket(PacketCheck packet){
		if(packet.data[1]==PacketCheck.HEADL){
			dealNodePacket(packet);
		}else if(packet.data[1]==PacketCheck.PHONEHEADL){
			dealControlDevPacket(packet);
		}
	}
	
}
