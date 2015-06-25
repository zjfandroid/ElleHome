package elle.home.database;

import java.net.InetAddress;

public class DeviceBase {
	//设备mac地址
	private long mac;
	//远程连接ip
	private InetAddress remoteip;
	//远程连接端口
	private int remoteport;
	//设备名
	private String devname;
	//设备类型
	private byte type;
	//设备版本
	private byte ver;
	//设备数据库id
	private int sqliteid;
	//遥控品牌id
	private int infraBrandId;
	//遥控型号id
	private int infraVerId;
	
	//--------------------------------
	//--下面这堆先不用考虑，交互变了可能就用不上了
	//--------------------------------
	//设备序号
	private int shownum;
	//设备是否可见
	private int visable;
	//设备所属地点的id
	private int locateId;
	//设备所属地点名称
	private String locateNmae;
	// 在线或者远程的判断标识，isLocal：本地，isRemote：远程
	private boolean isLocal;
	private boolean isRemote;
	// 是否打开
	private boolean isTurnOn;
	// 是否选中
	private boolean isChecked;
	// 是否已添加
	private boolean isAdded;
	private int localTimeCount;
	private int remoteTimeCount;
	private String function = "";
}
