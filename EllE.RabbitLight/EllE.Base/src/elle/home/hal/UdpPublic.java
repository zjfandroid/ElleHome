package elle.home.hal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import elle.home.utils.ShowInfo;

public class UdpPublic {

	public String TAG = "UdpPublic";
	
	public DatagramSocket dataSocket;
	
	OnUdpRecv onUdpRecv;
	
	RecvThread recvThread;
	
	public UdpPublic(){
		try {
			dataSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void startnow(){
		recvThread = new RecvThread();
		recvThread.start();
	}
	
	public void stopnow(){
		if(recvThread!=null){
			recvThread.stopThis();
		}
	}
	
	public void sendData(DatagramPacket packet){
		try {
			ShowInfo.printLogW("____sendData____" + packet.getAddress().toString());
			dataSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setListenerUdpRecv(OnUdpRecv a){
		this.onUdpRecv = a;
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
			while(runFlag){
				DatagramPacket packet = new DatagramPacket(data,data.length);
				try {
					dataSocket.receive(packet);
					length = packet.getLength();
					ShowInfo.printLogW(length + "____sendData__RecvThread__" + packet.getAddress().toString());
					if(length>0){
						if(onUdpRecv!=null){
							onUdpRecv.onRecv(packet);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public interface OnUdpRecv{
		public void onRecv(DatagramPacket packet);
	}
	
}
