package elle.home.protocol;

import java.net.InetAddress;

import elle.home.publicfun.PublicDefine;

public class PacketTaskFun extends BasicPacket {

	public static final byte SET_TIME_ZONE = (byte) 0xF2;
	public static final byte GetTaskList = (byte) 0xF4;
	public static final byte BackTaskList = (byte) 0xF5;
	public static final byte EditTaskList = (byte) 0xF6;
	
	public PacketTaskFun(InetAddress ip, int port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}
	
	public void setTimeZone(byte[] mac,byte type,byte ver,OnRecvListener listener){
		byte[] xdata = new byte[1];
		xdata[0] = GetTimeZone.getTimeZone();
		super.packData(type, ver, SET_TIME_ZONE, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), xdata);
		super.setListener(listener);
	}
	
	public void getCheckList(byte[] mac,byte type,byte ver,OnRecvListener listener){
		super.packData(type, ver, GetTaskList, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void editOneTask(byte[] mac,byte type,byte ver,OnRecvListener listener,TaskFun fun){
		byte[] xdata = new byte[6];
		xdata[0] = fun.num;
		xdata[1] = fun.day;
		xdata[2] = fun.hour;
		xdata[3] = fun.min;
		xdata[4] = fun.ss;
		xdata[5] = fun.fun;
		super.packData(type, ver, EditTaskList, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), xdata);
		super.setListener(listener);
	}
	
}
