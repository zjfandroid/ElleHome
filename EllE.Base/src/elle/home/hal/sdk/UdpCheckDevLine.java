package elle.home.hal.sdk;

import android.content.Context;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import elle.home.database.OneDev;
import elle.home.hal.UdpPublic;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.NetUtils;
import elle.home.utils.ShowInfo;

public class UdpCheckDevLine extends UdpPublic {

	public String TAG = "UdpCheckDevLine";
	
	Context mContext;
	InetAddress regServiceIp;
	UdpCheckDevLine self;
	
	private List<OneDev> devs;
	
	public boolean isneedcheck;
	
	OnUdpRecv onUdpRecv = new OnUdpRecv(){

		@Override
		public void onRecv(DatagramPacket packet) {
			ShowInfo.printLogW("___devs.size()_onRecv_收到设备mac：");
			if(null == devs){
				return;
			}
			
			int length = packet.getLength();
			if(length>0){
				if(length>=37){
					byte[] xdata = new byte[length];
					System.arraycopy(packet.getData(), 0, xdata, 0, length);
					PacketCheck packetcheck = new PacketCheck(xdata,length,packet.getAddress(),packet.getPort());

					ShowInfo.printLogW("___devs.size()__收到设备mac：" + devs.size());
					if(packetcheck.check()){

						for(int x=0;x<devs.size();x++){
							if(devs.get(x).mac == packetcheck.mac){
								if(packetcheck.devfun == PublicDefine.FunCheckBack){

									ShowInfo.printLogW(TAG,"_____收到设备mac："+packetcheck.mac+" 的远程回应包");
									devs.get(x).setDevLocal(false);
									devs.get(x).clearRemoteTimer();
									
								}else if(packetcheck.devfun == PublicDefine.FunRegBack){
									
									int ip1 = packetcheck.xdata[0]&0xff;
									int ip2 = packetcheck.xdata[1]&0xff;
									int ip3 = packetcheck.xdata[2]&0xff;
									int ip4 = packetcheck.xdata[3]&0xff;
									
									String ip = String.valueOf(ip1)+"."+String.valueOf(ip2)+"."+String.valueOf(ip3)+"."+String.valueOf(ip4);
									int port = DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5]);
									ShowInfo.printLogW(TAG, "设备mac：" + packetcheck.mac + " 得到控制ip为：" + ip + " 端口为：" + port + "___name___" + devs.get(x).devname);
									
									try {
										devs.get(x).updateDevRemoteIp(InetAddress.getByName(ip), port, mContext);
									} catch (UnknownHostException e) {
										e.printStackTrace();
									}
									
								}
							}
						}
						
					}
				}
			}
		}
		
	};
	
	public UdpCheckDevLine(Context b){
		
		this.mContext = b;
		self = this;
		isneedcheck = true;
		super.setListenerUdpRecv(onUdpRecv);
		try {
			regServiceIp = InetAddress.getByName("198.199.115.33");
		} catch (UnknownHostException e) {
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
			
			if(isneedcheck==false || null == devs || devs.isEmpty() || !NetUtils.isNetworkEnabled(mContext)){
				return;
			}

			for(int x=0;x<devs.size();x++){
				
				devs.get(x).lineCountAdd();
				
				if(devs.get(x).getDevLocal()==false){
					
					InetAddress ip = devs.get(x).remoteip;

					ShowInfo.printLogW(TAG, "____devname" + devs.get(x).devname + "需要去远端查询状态，控制服务器端口的ip为：" + ip + " port:" + devs.get(x).remoteport);
					if(null == ip || ip.toString().equals("/255.255.255.255")){
						ShowInfo.printLogW(TAG,"_____设备的远端地址还没有配置，因此发送注册包到注册服务器，设备mac："+devs.get(x).mac);
						if(regServiceIp!=null){
							BasicPacket packet = new BasicPacket(regServiceIp,PublicDefine.RemoteRegServicePort);
							packet.setPacketRemote(true);
							packet.packRegPacket(devs.get(x), PublicDefine.getSeq());
							self.sendData(packet.getUdpData());
						}
					}else{
						ShowInfo.printLogW(TAG,"_____设备已经有了远端地址，往远端发送查询包");
						BasicPacket packet = new BasicPacket(devs.get(x).remoteip,devs.get(x).remoteport);
						packet.setPacketRemote(true);
						packet.packCheckPacket(devs.get(x),PublicDefine.getSeq());
						self.sendData(packet.getUdpData());
					}
				}
			}
			
		}};
		
		public void setDevs(List<OneDev> mlists){
			this.devs = mlists;
		}
		
		public void addOneDev(OneDev oneDev){
			if(null == devs){
				devs = new ArrayList<OneDev>();
			}

			if(null != oneDev){
				devs.add(oneDev);
			}
		}
		
}
