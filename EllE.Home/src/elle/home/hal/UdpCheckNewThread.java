package elle.home.hal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import elle.home.database.AllDevInfo;
import elle.home.database.AllLocationInfo;
import elle.home.database.DevLocationInfo;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.NetUtils;

public class UdpCheckNewThread {

	public String TAG = "UdpCheckNewThread";

	//udp通道
	public DatagramSocket dataSocket;
	public Context mContext;
	
	//接收，发送线程
	RecvThread recvThread;
	SendThread sendThread;
	
	public AllDevInfo allinfo;
	public boolean isanynew;
	
	public boolean isneedcheck;
	
	OnNewDevNow listener;
	
	//动态查找到的设备
	public List<OneDev> newdevs = new ArrayList<OneDev>();
	public AllLocationInfo allLocationInfo;
	
	
	public UdpCheckNewThread(Context context,OnNewDevNow listener,AllLocationInfo info){
		this.listener = listener;
		try {
			dataSocket = new DatagramSocket();
			Log.d(TAG,"检查新设备的udp端口号："+dataSocket.getLocalPort());
			mContext = context;
			allinfo = new AllDevInfo(mContext);
			this.allLocationInfo = info;
			isneedcheck = true;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setListener(OnNewDevNow listener){
		this.listener = listener;
	}
	
	public void setAllLocationInfo(AllLocationInfo info){
		this.allLocationInfo = info;
	}	
	
	public UdpCheckNewThread(Context context){
		try {
			dataSocket = new DatagramSocket();
			Log.d(TAG,"检查新设备的udp端口号："+dataSocket.getLocalPort());
			mContext = context;
			allinfo = new AllDevInfo(mContext);
			isneedcheck = true;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public UdpCheckNewThread(Context context,OnNewDevNow listener){
		this.listener = listener;
		try {
			dataSocket = new DatagramSocket();
			Log.d(TAG,"检查新设备的udp端口号："+dataSocket.getLocalPort());
			mContext = context;
			allinfo = new AllDevInfo(mContext);
			isneedcheck = true;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void freshALlInfo(){
		allinfo.getAllDev();
		newdevs.clear();
	}
	
	public void startCheck(){
		
		newdevs.clear();
		allinfo.getAllDev();
		isneedcheck = true;
		
	}
	
	public void stopCheck(){
		
		isneedcheck = false;
		
	}
	
	public boolean isAnyNewDev(){
		return isanynew;
	}
	
	public void removeOneDev(long mac){
		allinfo.getAllDev();
		for(int i=0;i<newdevs.size();i++){
			if(newdevs.get(i).mac == mac){
				newdevs.remove(i);
				break;
			}
		}
	}
	
	private void removeOneDevNew(long mac){
		for(int i=0;i<newdevs.size();i++){
			if(newdevs.get(i).mac == mac){
				newdevs.remove(i);
				break;
			}
		}
	}
	
	public boolean isNewDev(PacketCheck packet){
		
		//这里查找到的都是本地，因此可以在这里清空本地的离线标识
		if(allLocationInfo!=null){
			
			List<DevLocationInfo> infos = allLocationInfo.allinfo;
			for(int i=0; i<infos.size(); i++){
				
				List<OneDev> lists = infos.get(i).devLocationList;
				for(int x=0; x<lists.size(); x++){
					
					OneDev oneDev = lists.get(x);
					if(oneDev.mac == packet.mac){
						oneDev.setDevRemote(false);
						oneDev.clearLocalTimer();
						
						if(null != packet.xdata && packet.xdata.length>0 && 1 == packet.xdata[0]){
							oneDev.setTurnOn(true);
						}else{
							oneDev.setTurnOn(false);
						}
					}
				}
			}
		}
		
		for(int i=0;i<allinfo.alldevinfo.size();i++){
			if(allinfo.alldevinfo.get(i).mac == packet.mac){
				//Log.d(TAG,"数据库中已经存在");
				removeOneDevNew(packet.mac);
				return false;
			}	
		}
		
		for(int i=0;i<newdevs.size();i++){
			if(newdevs.get(i).mac == packet.mac){
				//Log.d(TAG,"暂存里表中已经存在");
				return false;
			}
		}
		
		OneDev one = new OneDev();
		one.mac = packet.mac;
		one.ver = packet.devver;
		one.type = packet.devcode;
		newdevs.add(one);
		return true;
	}
	
	public void startThread(){
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				Log.d(TAG,"udp check new thread start");
				
				sendThread = new SendThread();
				recvThread = new RecvThread();
				
				sendThread.start();
				recvThread.start();
			}}, 2000);
	}
	
	public void stopThread(){
		Log.d(TAG,"udp check new thread stop");
		if(null != sendThread){
			this.sendThread.stopThis();
		}
		
		if(null != recvThread){
			this.recvThread.stopThis();
		}
		
		if(null != dataSocket){
			dataSocket.disconnect();
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				
				@Override
				public void run() {
					Log.d(TAG,"udp check new thread stop2");
					dataSocket.close();
				}}, 100);
		}
		
	}
	
	public class RecvThread extends Thread{
		
		public boolean runFlag = true;
		
		void stopThis(){
			runFlag = false;
		}
		
		@Override
		public void run() {
			super.run();
			runFlag = true;
			byte[] data = new byte[4096];
			int length;
			while(runFlag && !dataSocket.isClosed()){
				DatagramPacket packet = new DatagramPacket(data,data.length);
				try {
					dataSocket.receive(packet);
					length = packet.getLength();
					if(length>0){
						//Log.d(TAG,"检查是否有新的设备包");
						if(length>=37){
							//Log.d(TAG,"检查是否有新的设备包2");
							byte[] xdata = new byte[length];
							System.arraycopy(data, 0, xdata, 0, length);
							PacketCheck packetcheck = new PacketCheck(xdata,length,packet.getAddress(),packet.getPort());
							if(packetcheck.check()){
								//检查是否是新的设备
								//Log.d(TAG,"检查是否有新的设备包3");
								if(isNewDev(packetcheck)){
									Log.d(TAG,"有新的设备："+packetcheck.mac);
									if(listener!=null){
										listener.onnewdev();
									}
									isanynew = true;
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public class SendThread extends Thread{

		public boolean runFlag = true;
		
		void stopThis(){
			runFlag = false;
		}
		
		@Override
		public void run() {
			super.run();
			
			runFlag = true;
			InetAddress add = null;
			try {
				add = InetAddress.getByName("255.255.255.255");
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			BasicPacket basicpacket = new BasicPacket(add,PublicDefine.LocalUdpPort);
			basicpacket.packCheckPacket(0);
			DatagramPacket packet = null;
			while(runFlag){
				if(isneedcheck == true && NetUtils.isNetworkEnabled(mContext)){
					if(newdevs.size()==0){
						isanynew = false;
						if(listener!=null)
							listener.onnonew();
					}
					//从发送队列拿出包并发送
					packet = basicpacket.getUdpData();
					if(packet!=null){
						try {
							dataSocket.send(packet);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					this.sleep(1800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface OnNewDevNow{
		void onnewdev();
		void onnonew();
	}
	
}
