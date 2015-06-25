package elle.home.devicetest;

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

public class UdpCheckNewThread {

	public String TAG = "UdpCheckNewThread";

	//udp通道
	public DatagramSocket dataSocket;
	public Context mContext;
	
	//接收，发送线程
	RecvThread recvThread;
	SendThread sendThread;
	
	public boolean isanynew;
	
	public boolean isneedcheck;
	
	OnRefreshCallback listener;
	
	//动态查找到的设备
	public List<OneDev> newdevs = new ArrayList<OneDev>();
	
	public UdpCheckNewThread(Context context){
		try {
			dataSocket = new DatagramSocket();
			Log.d(TAG,"检查新设备的udp端口号："+dataSocket.getLocalPort());
			mContext = context;
			isneedcheck = false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UdpCheckNewThread(Context context,OnRefreshCallback listener){
		this.listener = listener;
		try {
			dataSocket = new DatagramSocket();
			Log.d(TAG,"检查新设备的udp端口号："+dataSocket.getLocalPort());
			mContext = context;
			isneedcheck = false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void freshALlInfo(){
		newdevs.clear();
	}
	
	public void startCheck(){
		newdevs.clear();
		isneedcheck = true;
	}
	
	public void stopCheck(){
		isneedcheck = false;
	}
	
	public boolean isAnyNewDev(){
		return isanynew;
	}
	
	public void removeOneDev(long mac){
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
		for(int i=0;i<newdevs.size();i++){
			if(newdevs.get(i).mac == packet.mac){
				newdevs.get(i).clearLocalTimer();
				//Log.d(TAG,"暂存里表中已经存在");
				return false;
			}
		}
		OneDev one = new OneDev();
		one.mac = packet.mac;
		one.ver = packet.devver;
		one.type = packet.devcode;
		one.remoteip = packet.ip;
		newdevs.add(one);
		return true;
	}
	
//	public boolean isLight(PacketCheck packet){
//		if(packet.devcode == PublicDefine.TypeLight){
//			return true;
//		}
//		return false;
//	}
	
	public void startThread(){
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Log.d(TAG,"udp check new thread start");
				
				sendThread = new SendThread();
				recvThread = new RecvThread();
				
				sendThread.start();
				recvThread.start();
			}}, 100);
	}
	
	public void stopThread(){
		Log.d(TAG,"udp check new thread stop");
		this.sendThread.stopThis();
		this.recvThread.stopThis();
		dataSocket.disconnect();
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Log.d(TAG,"udp check new thread stop2");
				
				dataSocket.close();
			}}, 100);
		
	}
	
	public class RecvThread extends Thread{
		
		public boolean runFlag = true;
		
		void stopThis(){
			runFlag = false;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			runFlag = true;
			byte[] data = new byte[4096];
			int length;
			while(runFlag){
				DatagramPacket packet = new DatagramPacket(data,data.length);
				try {
					dataSocket.receive(packet);
					length = packet.getLength();
					if(length>0){
//						Log.d(TAG,"检查是否有新的设备包");
						if(length>=37){
//							Log.d(TAG,"检查是否有新的设备包2");
							byte[] xdata = new byte[length];
							System.arraycopy(data, 0, xdata, 0, length);
							PacketCheck packetcheck = new PacketCheck(xdata,length,packet.getAddress(),packet.getPort());
							if(packetcheck.check()){
//								if(packetcheck.check() && isLight(packetcheck)){
								//检查是否是新的设备
//								Log.d(TAG,"检查是否有新的设备包3");
								if(isNewDev(packetcheck)){
									Log.d(TAG,"有新的设备："+ packetcheck.mac);
									if(listener!=null){
										listener.onNewDev();
									}
									isanynew = true;
								}else{
									Log.d(TAG,"刷新设备："+ packetcheck.mac);
									if(listener!=null){
										listener.onChangeDev();
									}
								}
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
			// TODO Auto-generated method stub
			super.run();
			runFlag = true;
			InetAddress add = null;
			try {
				add = InetAddress.getByName("255.255.255.255");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BasicPacket basicpacket = new BasicPacket(add,PublicDefine.LocalUdpPort);
			basicpacket.packCheckPacket(0);
			DatagramPacket packet = null;
			while(runFlag){
				if(isneedcheck == true){
					//从发送队列拿出包并发送
					packet = basicpacket.getUdpData();
					if(packet!=null){
						try {
							for(OneDev dev : newdevs){
								dev.lineCountAdd();
							}
							dataSocket.send(packet);
							Log.v(TAG, "我要发查询包了！！！，长度为：" 
									 +basicpacket.data.length);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface OnRefreshCallback{
		void onNewDev();
		void onChangeDev();
	}
	
}
