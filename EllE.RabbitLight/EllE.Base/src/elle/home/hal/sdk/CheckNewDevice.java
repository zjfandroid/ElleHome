package elle.home.hal.sdk;

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
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.NetUtils;

public class CheckNewDevice {

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
	
	OnDeviceListener listener;
	
	//动态查找到的设备
	public List<OneDev> newdevs = new ArrayList<OneDev>();
	
	public void setListener(OnDeviceListener listener){
		this.listener = listener;
	}
	
	public CheckNewDevice(Context context){
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
	
	public CheckNewDevice(Context context,OnDeviceListener listener){
		this(context);
		this.listener = listener;
	}
	
	public void freshALlInfo(){
		allinfo.getAllDev();
		newdevs.clear();
	}
	
	public void startCheck(){
		
		newdevs.clear();
		allinfo.getAllDev();
		isneedcheck = true;
		
		startThread();
	}
	
	public void stopCheck(){
		
		isneedcheck = false;
		
		stopThread();
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
		
		if(null != listener){
			listener.onOneDeviceFound(one);
		}
		
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
										listener.onDeviceFound(newdevs);
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface OnDeviceListener{
		void onDeviceFound(List<OneDev> newdevs);
		void onOneDeviceFound(OneDev newdev);
	}
	
}
