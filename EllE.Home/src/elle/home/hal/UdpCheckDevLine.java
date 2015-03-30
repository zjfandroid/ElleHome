package elle.home.hal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import elle.home.database.AllLocationInfo;
import elle.home.database.DevLocationInfo;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class UdpCheckDevLine extends UdpPublic {

	public String TAG = "UdpCheckDevLine";
	
	AllLocationInfo allLocationInfo;
	Context mContext;
	InetAddress regServiceIp;
	UdpCheckDevLine self;
	
	public boolean isneedcheck;
	
	OnUdpRecv onUdpRecv = new OnUdpRecv(){

		@Override
		public void onRecv(DatagramPacket packet) {
			// TODO Auto-generated method stub
			int length = packet.getLength();
			if(length>0){
				if(length>=37){
					byte[] xdata = new byte[length];
					System.arraycopy(packet.getData(), 0, xdata, 0, length);
					PacketCheck packetcheck = new PacketCheck(xdata,length,packet.getAddress(),packet.getPort());
					if(packetcheck.check()){
						
						if(allLocationInfo==null)
							return;
						
						for(int i=0;i<allLocationInfo.allinfo.size();i++){
							for(int x=0;x<allLocationInfo.allinfo.get(i).devLocationList.size();x++){
								if(allLocationInfo.allinfo.get(i).devLocationList.get(x).mac == packetcheck.mac){
									if(packetcheck.devfun == PublicDefine.FunCheckBack){
										
										//Log.d(TAG,"收到设备mac："+packetcheck.mac+" 的远程回应包");
										allLocationInfo.allinfo.get(i).devLocationList.get(x).setDevLocal(false);
										allLocationInfo.allinfo.get(i).devLocationList.get(x).clearRemoteTimer();
										
									}else if(packetcheck.devfun == PublicDefine.FunRegBack){
										
										int ip1 = packetcheck.xdata[0]&0xff;
										int ip2 = packetcheck.xdata[1]&0xff;
										int ip3 = packetcheck.xdata[2]&0xff;
										int ip4 = packetcheck.xdata[3]&0xff;
										
										String ip = String.valueOf(ip1)+"."+String.valueOf(ip2)+"."+String.valueOf(ip3)+"."+String.valueOf(ip4);
										int port = DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5]);
										Log.d(TAG,"设备mac："+packetcheck.mac+" 得到控制ip为："+ip+" 端口为："+port);
										
										try {
											allLocationInfo.allinfo.get(i).devLocationList.get(x).updateDevRemoteIp(InetAddress.getByName(ip), port, mContext);
										} catch (UnknownHostException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								}
							}
						}
					}
				}
			}
		}
		
	};
	
	public UdpCheckDevLine(AllLocationInfo a,Context b){
		this.allLocationInfo = a;
		this.mContext = b;
		self = this;
		super.setListenerUdpRecv(onUdpRecv);
		try {
			regServiceIp = InetAddress.getByName("198.199.115.33");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAllLocationInfo(AllLocationInfo a){
		this.allLocationInfo = a;
	}
	
	public UdpCheckDevLine(Context b){
		
		this.mContext = b;
		self = this;
		isneedcheck = true;
		super.setListenerUdpRecv(onUdpRecv);
		try {
			regServiceIp = InetAddress.getByName("198.199.115.33");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startCheck(){
		this.isneedcheck = true;
	}
	
	public void stopCheck(){
		this.isneedcheck = false;
	}
	
	public void start(){
		super.startnow();
		timer.schedule(task, 300,1000);
	}
	
	public void stop(){
		timer.cancel();
		super.stopnow();
	}
	
	
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isneedcheck==false){
				return;
			}
			
			if(allLocationInfo == null)
				return;
			
			for(int i=0;i<allLocationInfo.allinfo.size();i++){
				for(int x=0;x<allLocationInfo.allinfo.get(i).devLocationList.size();x++){
					
					allLocationInfo.allinfo.get(i).devLocationList.get(x).lineCountAdd();
					
					if(allLocationInfo.allinfo.get(i).devLocationList.get(x).getDevLocal()==false){
						
						String ip = allLocationInfo.allinfo.get(i).devLocationList.get(x).remoteip.toString();
						
						//Log.d(TAG,"devname"+allLocationInfo.allinfo.get(i).devLocationList.get(x).devname+"需要去远端查询状态，控制服务器端口的ip为："+ip+" port:"+allLocationInfo.allinfo.get(i).devLocationList.get(x).remoteport);
						if(ip.equals("/255.255.255.255")){
							//Log.d(TAG,"设备的远端地址还没有配置，因此发送注册包到注册服务器，设备mac："+allLocationInfo.allinfo.get(i).devLocationList.get(x).mac);
							if(regServiceIp!=null){
								BasicPacket packet = new BasicPacket(regServiceIp,PublicDefine.RemoteRegServicePort);
								packet.setPacketRemote(true);
								packet.packRegPacket(allLocationInfo.allinfo.get(i).devLocationList.get(x), PublicDefine.getSeq());
								self.sendData(packet.getUdpData());
							}
						}else{
							//Log.d(TAG,"设备已经有了远端地址，往远端发送查询包");
							BasicPacket packet = new BasicPacket(allLocationInfo.allinfo.get(i).devLocationList.get(x).remoteip,allLocationInfo.allinfo.get(i).devLocationList.get(x).remoteport);
							packet.setPacketRemote(true);
							packet.packCheckPacket(allLocationInfo.allinfo.get(i).devLocationList.get(x),PublicDefine.getSeq());
							self.sendData(packet.getUdpData());
						}
					}
				}
			}
		}};
	
		
		
		
		
}
