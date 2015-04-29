package elle.home.protocol;

import java.net.InetAddress;

import elle.home.database.OneDev;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class GateWayControlPacket extends BasicPacket {

	public GateWayControlPacket(InetAddress ip, int port) {
		super(ip, port);
	}
	
	/*
	 * 允许设备节点入网
	 */
	public void allowIn(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeGateWay, PublicDefine.GateWayOrgin, PublicDefine.GateWayAllowIn, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	/*
	 * 禁止设备节点入网
	 */
	public void banIn(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeGateWay, PublicDefine.GateWayOrgin, PublicDefine.GateWayBan, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	/*
	 * 要求设备脱离网络
	 */
	public void requestDevOut(byte[] mac,OnRecvListener listener, OneDev oneDev){
		byte[] macDes = DataExchange.longToEightByte(oneDev.mac);
		byte[] xdata = new byte[10];
		System.arraycopy(macDes, 0, xdata, 0, 8);
		
		xdata[8] = oneDev.type;
		xdata[9] = oneDev.ver;
		
//		System.arraycopy(type, 0, xdata, 8, 9);
//		System.arraycopy(ver, 0, xdata, 9, 10);
		
		super.packData(PublicDefine.TypeGateWay, PublicDefine.GateWayOrgin, PublicDefine.GateWayRequestDevOut, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), xdata);
		super.setListener(listener);
	}
	
	/*
	 * 提交设备入网请求
	 */
	public void postDevIn(byte[] mac,OnRecvListener listener, byte[] macDes){
		super.packData(PublicDefine.TypeGateWay, PublicDefine.GateWayOrgin, PublicDefine.GateWayPostDevIn, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), macDes);
		super.setListener(listener);
	}
	
	/*
	 * 允许设备入网请求
	 */
	public void allowDevIn(byte[] mac,OnRecvListener listener, byte[] macDes){
		super.packData(PublicDefine.TypeGateWay, PublicDefine.GateWayOrgin, PublicDefine.GateWayAllowDevIn, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), macDes);
		super.setListener(listener);
	}
}
