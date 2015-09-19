package elle.home.airkiss;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class AirKiss {

	public String TAG = "AirKiss";

	//udp通道
	public DatagramSocket dataSocket;
	
	String ssid;
	String password;
	ArrayList<Integer> list;
	
	SendThread sendThread;
	XSendThread xsendThread;
	
	public AirKiss(String ssid,String password){
		this.ssid = ssid;
		this.password = password;	
		list=new ArrayList<Integer>();
		packInfo();
		try {
			dataSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//启动
	public void start(){
		if(sendThread==null){
			sendThread = new SendThread();
			sendThread.start();
			xsendThread = new XSendThread();
			xsendThread.start();
		}
		else{
			if(sendThread.isRun){
				
			}else{
				sendThread.start();
			}
			if(xsendThread.isRun){
				
			}else{
				xsendThread.start();
			}
		}
		
	}
	//停止
	public void stop(){
		if(sendThread!=null){
			sendThread.stopThis();
			sendThread = null;
			xsendThread.stopThis();
			xsendThread = null;
		}
	}
	
	//打包ssid和password
	public void packInfo(){
		int tmp =0 ;
		
		list.clear();

		//password len
		tmp = (((password.length()&0xf0)>>4)+0x40);
		list.add(tmp);
		tmp = ((password.length()&0x0f)+0x50);
		list.add(tmp);
		//password crc
		tmp = (((getCrcByBytes(password.getBytes())&0xf0)>>4)+0x60);
		list.add(tmp);
		tmp = ((getCrcByBytes(password.getBytes())&0x0f)+0x70);
		list.add(tmp);
		
		//ssid len
		tmp = (((ssid.length()&0xf0)>>4)+0x05);
		list.add(tmp);
		tmp = ((ssid.length()&0x0f)+0x10);
		list.add(tmp);
		//ssid crc
		tmp = (((getCrcByBytes(ssid.getBytes())&0xf0)>>4)+0x20);
		list.add(tmp);
		tmp = ((getCrcByBytes(ssid.getBytes())&0x0f)+0x30);
		list.add(tmp);
		
		//password内容
		int frameLen = 0;
		if(password.length()%4!=0){
			frameLen = password.length()/4+1;
		}else{
			frameLen = password.length()/4;
		}
		int lenCount = 0;
		for(int i=0;i<frameLen;i++){
			byte[] passwordBytes = password.getBytes();
			byte[] xtmp = new byte[4];
			for(int x=0;x<4;x++){
				if(lenCount<password.length()){
					xtmp[x] = passwordBytes[lenCount++];
				}else{
					xtmp[x] = 0;
				}
			}
			//crc
			tmp = (getCrcByBytes(xtmp)&0x7f)+0x80;
			tmp = tmp&0xff;
			list.add(tmp);
			//index
			tmp = i+0x80+1;
			tmp = tmp&0xff;
			list.add(tmp);
			//password
			for(int x=0;x<4;x++){
				tmp = 0x100 + (xtmp[x]&0xff);
				list.add(tmp);
			}
		}
		
		list.add(1);
	}
	
	//得到crc
	int getCrcByBytes(byte[] tmp){
		int result = 0;
		int x = 0;
		for(int i=0;i<tmp.length;i++){
			x = tmp[i]&0xff;
			result = result+x;
		}
		
		return result&0xff;
	}
	
	//发送线程
	public class XSendThread extends Thread{

		int timeCount;
		public boolean isRun;
		
		public void stopThis(){
			isRun = false;
		}
		
		//由长度得到需要发送的DatagramPacket 包
		DatagramPacket getPacketByLen(int len){
			DatagramPacket tmp = null;
			if(len<0)
				return null;
			byte[] x = new byte[len];
			Arrays.fill(x, (byte) 'E');
			try {
				tmp = new DatagramPacket(x, len, InetAddress.getByName("255.255.255.255"), 30003);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tmp;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			timeCount = 0;
			isRun = true;
			while(isRun){
				
				timeCount++;
				//这里发送确认序列
				try {
					dataSocket.send(this.getPacketByLen(timeCount%4+1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(timeCount>2000)
					timeCount = 0;
				//Log.d(TAG,"信道确认帧发送完成");
				try {
					this.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	//发送线程
	public class SendThread extends Thread{

		int timeCount;
		public boolean isRun;
		
		public void stopThis(){
			isRun = false;
		}
		
		//由长度得到需要发送的DatagramPacket 包
		DatagramPacket getPacketByLen(int len){
			DatagramPacket tmp = null;
			if(len<0)
				return null;
			byte[] x = new byte[len];
			Arrays.fill(x, (byte) 'E');
			try {
				tmp = new DatagramPacket(x, len, InetAddress.getByName("255.255.255.255"), 30004);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tmp;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			timeCount = 0;
			isRun = true;
			int infoNum = 0;
			while(isRun){
					//这里开始发送协议帧
					try {
						DatagramPacket ptmp = this.getPacketByLen((Integer)(list.get(infoNum)));
						if(ptmp!=null){
							dataSocket.send(ptmp);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					infoNum++;
					if(infoNum>=list.size()){
						infoNum = 0;
					}
					try {
						this.sleep(5);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
		}
	}
	
}
