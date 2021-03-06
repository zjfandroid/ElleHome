package elle.home.hal;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import elle.home.protocol.PacketCheck;
import elle.home.protocol.ProtocolDataList;

public class UdpThread {
	
	public String TAG = "UdpThread";

	//udp通道
	public DatagramSocket dataSocket;
	
	//接收，发送线程
	ProtocolDataList datalist = null;
	RecvThread recvThread;
	SendThread sendThread;
	
	public UdpThread(){
		try {
			dataSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**设置接收队列
	 * @param a
	 */
	public void setDataList(ProtocolDataList a){
		this.datalist = a;
	}
	
	public ProtocolDataList getProtocolDataList(){
		return datalist;
	}
	
	public void startThread(){
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				
				Log.d(TAG,"udp thread start");
				
				sendThread = new SendThread();
				recvThread = new RecvThread();
				
				sendThread.start();
				recvThread.start();
			}}, 1000);
		
	}
	
	public void stopThread(){
		Log.d(TAG,"udp thread stop");
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
					Log.d(TAG,"udp thread stop2");
					dataSocket.close();
				}}, 500);
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
						if(length>=37){
							byte[] xdata = new byte[length];
							System.arraycopy(data, 0, xdata, 0, length);
							PacketCheck packetcheck = new PacketCheck(xdata,length,packet.getAddress(),packet.getPort());
							if(packetcheck.check()){
								//加入到接收队列中去
								if(datalist!=null)
									datalist.dealRecvPacket(packetcheck);
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
			DatagramPacket packet = null;
			while(runFlag){
				//从发送队列拿出包并发送
				if(datalist!=null){
					packet = datalist.getOnePacketFromSendList();
				}
				if(packet!=null){
					try {
						Log.d(TAG,"send once:"+packet.getAddress().toString()+" port:"+packet.getPort());
						dataSocket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
