package elle.home.protocol;

import java.net.DatagramPacket;
import java.net.InetAddress;

import elle.home.database.OneDev;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class BasicPacket {

	public static final int BasicLen = 37;
	public static final int ImportNormal = 1;
	public static final int ImportLow = 0;
	public static final int ImportHigh = 2;
	public static final int ImportStatic = 3;
	
	public InetAddress ip;
	public int port;
	public byte[] data;
	public int seq;
	public int importance;
	public int recvcount;
	
	//100ms统计
	public int sendtime;
	public int sendcount;
	
	//回掉
	public OnRecvListener listener;
	public boolean isRemote ;
	
	public BasicPacket(InetAddress ip,int port){
		this.ip = ip;
		this.port = port;
		sendtime = 0;
		sendcount = 0;
		recvcount = 0;
		this.listener = null;
		isRemote = false;
		importance = BasicPacket.ImportNormal;
	}
	
	public void setPacketRemote(boolean a){
		this.isRemote = a;
	}
	
	public void setImportance(int a){
		this.importance = a;
	}
	
	public void setIp(InetAddress ip){
		this.ip = ip;
	}
	
	public int getImportance(){
		return this.importance;
	}
	
	public void setListener(OnRecvListener a){
		this.listener = a;
	}
	
	public int couldSend(int time){
		if(Math.abs(time - sendtime)<3){
			return 0;
		}
		sendtime = time;
		sendcount++;
		return sendcount;
	}
	
	public int getSeq(){
		return this.seq;
	}
	
	/**得到打包好的包
	 * @return
	 */
	public DatagramPacket getUdpData(){
		DatagramPacket packet = new DatagramPacket(this.data,this.data.length,this.ip,this.port);
		return packet;
	}
	
	/**
	 * 特定设备地址的查询包，传入mac地址和seq，seq不能为0
	 * **/
	public void packCheckPacket(byte[] mac,int seq){
		this.packData(PublicDefine.TypePublic, PublicDefine.PublicVerOrgin, 
				PublicDefine.FunCheck, mac, PublicDefine.phonemac, seq, null);
	}
	
	/**
	 * wifi配置查询包
	 * */
	public void packWiFiConfigCheck(OneDev dev){
		byte[] tmp = new byte[1];
		tmp[0] = 0x01;
		this.packData(dev.type, dev.ver, PublicDefine.FunWiFiConfig, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), tmp);
	}
	
	/***
	 * wifi配置设置包
	 * **/
	public void packWifiConfigSet(OneDev dev,String ssid,String password)
	{
		int wifiLen = ssid.length()+password.length()+3;
		byte[] tmp = new byte[wifiLen];
		tmp[0] = 0x03;
		tmp[1] = (byte)(ssid.length()&0xff);
		System.arraycopy(ssid.getBytes(), 0, tmp, 2, ssid.length());
		tmp[2+ssid.length()] = (byte)(password.length()&0xff);
		System.arraycopy(password.getBytes(),0 , tmp, 3+ssid.length(), password.length());
		this.packData(dev.type, dev.ver, PublicDefine.FunWiFiConfig, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), tmp);
	}
	
	/**
	 * wifi配置重启
	 * **/
	public void packWifiConfigRestart(OneDev dev){
		byte[] tmp = new byte[1];
		tmp[0] = 0x05;
		this.packData(dev.type, dev.ver, PublicDefine.FunWiFiConfig, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), tmp);
	}

	/**
	 * 复位包
	 * **/
	public void packReset(OneDev dev){
		this.packData(dev.type, dev.ver, PublicDefine.FunReset, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
	}
	
	/**
	 * Zigbee复位包
	 * **/
	public void packZigbeeReset(OneDev dev){
		this.packZigbeeData(dev.type, dev.ver, PublicDefine.FunReset, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
	}
	
	/**
	 * 通用查询打包函数，seq传入0，即可查询所有的设备
	 * */
	public void packCheckPacket(int seq){
		byte[] devmac = new byte[8];
		this.packData(PublicDefine.TypePublic, PublicDefine.PublicVerOrgin, 
				PublicDefine.FunCheck, devmac, PublicDefine.phonemac, seq, null);
	}
	
	/**
	 * 特定设备的查询包，完整的
	 * */
	public void packCheckPacket(OneDev dev,int seq){
		this.packData(dev.type, dev.ver, PublicDefine.FunCheck, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);		
	}
	
	/**
	 * 注册包
	 * */
	public void packRegPacket(OneDev dev,int seq){
		this.packData(dev.type, dev.ver, PublicDefine.FunReg, DataExchange.longToEightByte(dev.mac), PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);		
	}
	
	public void packData(byte devcode,byte devver,byte devfun,byte[] mac,byte[] controlid,int seq,byte[] xdata){
		packData(devcode, devver, devfun, mac, controlid, seq, xdata, (byte)0, (byte)0);
	}
	
	/**
	 * Zigbee设备打包函数
	 * **/
	public void packZigbeeData(byte devcode,byte devver,byte devfun,byte[] mac,byte[] controlid,int seq,byte[] xdata){
		packData(devcode, devver, devfun, mac, controlid, seq, xdata, (byte)0x01, (byte)0x01);
	}
	
	/**
	 * 基础打包函数
	 * **/
	public void packData(byte devcode,byte devver,byte devfun,byte[] mac,byte[] controlid,int seq,byte[] xdata, byte typeBig, byte typeSmall){
		
		byte[] tmp;
		int len = 0;
		
		this.seq = seq;
		
		if(xdata!=null)
			data = new byte[BasicLen+xdata.length];
		else{
			xdata = new byte[0];
			data = new byte[BasicLen];
		}
		//head
		
		if(this.isRemote==false){
			data[len++] = (byte)0x55;
			data[len++] = (byte)0xaa;
		}else{
			data[len++] = (byte)0x55;
			data[len++] = (byte)0x66;
		}
		
		//data len
		tmp = DataExchange.intToTwoByte(BasicLen+xdata.length);
		data[len++] = tmp[0];
		data[len++] = tmp[1];
		//frame len
		data[len++] = 1;
		//frame num
		data[len++] = 0;
		//frame key
		data[len++] = 0;
		//mac 
		System.arraycopy(mac, 0, data, len, 8);
		len = len+8;
		//frame quality
		data[len++] = 0;
		data[len++] = 0;
		//dev status
		data[len++] = 0;
		//dev code
		data[len++] = devcode;
		//dev ver
		data[len++] = devver;
		//dev fun
		data[len++] = devfun;
		//typebig
		data[len++] = typeBig;
		//typesmall
		data[len++] = typeSmall;
		//control id
		System.arraycopy(controlid, 0, data, len, 4);
		len = len+4;
		//reserved
		len = len+4;
		//seq
		tmp = DataExchange.intToTwoByte(seq);
		data[len++] = tmp[0];
		data[len++] = tmp[1];
		//xdatalen
		tmp = DataExchange.intToTwoByte(xdata.length);
		data[len++] = tmp[0];
		data[len++] = tmp[1];
		//crc
		data[len++] = 0;
		data[len++] = 0;
		//xdata
		System.arraycopy(xdata, 0, data, len, xdata.length);
		len = len+xdata.length;
	}
	
}

