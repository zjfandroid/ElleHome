package elle.home.hal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import elle.home.utils.ShowInfo;

public class UdpPublic {

	public String TAG = "UdpPublic";
	
	public DatagramSocket dataSocket;

	private OnUdpRecv onUdpRecv;
	
	private RecvThread recvThread;
	
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
			dataSocket.send(packet);
			ShowInfo.printLogW("_____send data ___add____" + packet.getAddress());
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
			ShowInfo.printLogW("____sendData__RecvThread__stop");
		}

		@Override
		public void run() {
			super.run();
			runFlag = true;
			byte[] data = new byte[4096];
			int length;
			while(runFlag){
				ShowInfo.printLogW("____sendData__RecvThread__run10");
				DatagramPacket packet = new DatagramPacket(data, data.length);
				ShowInfo.printLogW("____sendData__RecvThread__run11");
				try {
					dataSocket.receive(packet);
					ShowInfo.printLogW("____sendData__RecvThread__run12");
					length = packet.getLength();
					ShowInfo.printLogW(length + "____sendData__RecvThread__" + packet.getAddress().toString());
					if(length>0){
						if(onUdpRecv!=null){
							onUdpRecv.onRecv(packet);
						}
					}
				} catch (IOException e) {
					ShowInfo.printLogW("____sendData__RecvThread__e" + e.getMessage());
					e.printStackTrace();
				}

				ShowInfo.printLogW("____sendData__RecvThread__rrunFlag0" + runFlag);
			}

			ShowInfo.printLogW("____sendData__RecvThread__rrunFlag0end" + runFlag);
		}

	}
	
	public interface OnUdpRecv{
		public void onRecv(DatagramPacket packet);
	}
	
}
