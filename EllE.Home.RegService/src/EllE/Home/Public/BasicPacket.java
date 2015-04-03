package EllE.Home.Public;

import java.net.DatagramPacket;
import java.net.InetAddress;


public class BasicPacket {

	public static final int BasicLen = 37;
	public static final int ImportNormal = 1;
	public static final int ImportLow = 0;
	public static final int ImportHigh = 2;
	
	public InetAddress ip;
	public int port;
	public byte[] data;
	public int seq;
	public int importance;
	
	//100ms统计
	public int sendtime;
	public int sendcount;
	
	//回掉
	public OnRecvListener listener;
	
	public BasicPacket(InetAddress ip,int port){
		this.ip = ip;
		this.port = port;
		sendtime = 0;
		sendcount = 0;
		this.listener = null;
		importance = BasicPacket.ImportNormal;
	}
	
	public void setImportance(int a){
		this.importance = a;
	}
	
	public int getImportance(){
		return this.importance;
	}
	
	public void setListener(OnRecvListener a){
		this.listener = a;
	}
	
	public int couldSend(int time){
		if(Math.abs(time - sendtime)<5){
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
	
	public void packCheckPacket(byte[] mac,int seq){
		this.packData(PublicDefine.TypePublic, PublicDefine.PublicVerOrgin, 
				PublicDefine.FunCheck, mac, PublicDefine.phonemac, seq, null);
	}
	
	public void packCheckPacket(int seq){
		byte[] devmac = new byte[8];
		this.packData(PublicDefine.TypePublic, PublicDefine.PublicVerOrgin, 
				PublicDefine.FunCheck, devmac, PublicDefine.phonemac, seq, null);
	}
	
	public void packDataToService(byte devcode,byte devver,byte devfun,byte[] mac,byte[] controlid,int seq,byte[] xdata){
		
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
		data[len++] = (byte)0x55;
		data[len++] = (byte)0x66;
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
		data[len++] = 0;
		//typesmall
		data[len++] = 0;
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
	
	public void packData(byte devcode,byte devver,byte devfun,byte[] mac,byte[] controlid,int seq,byte[] xdata){
		
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
		data[len++] = (byte)0x55;
		data[len++] = (byte)0xaa;
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
		data[len++] = 0;
		//typesmall
		data[len++] = 0;
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
