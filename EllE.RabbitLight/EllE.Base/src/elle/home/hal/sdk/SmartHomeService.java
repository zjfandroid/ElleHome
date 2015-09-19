package elle.home.hal.sdk;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import elle.home.hal.UdpThread;
import elle.home.hal.UserData;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.ProtocolDataList;
import elle.home.publicfun.PublicDefine;

public class SmartHomeService extends Service {

	public String TAG = "AutoService";
	
	AutoBinder binder = new AutoBinder();
	
	//后续这个要封装成为一个类
	//接收，发送队列
	ProtocolDataList datalist;
	//udp线程
	UdpThread udpthread;
	
	//这个负责通信到注册服务器并保持设备的查询状态
	private UdpCheckDevLine udpcheckline;
	
	//简单信息的存储及获取
	UserData userdata;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		userdata = new UserData(this);
		/*******_设置mac地址_*******/
		PublicDefine.setPhoneMac(userdata.getUuid());
		
		/*****--******/
		datalist = new ProtocolDataList();
		udpthread = new UdpThread();
		udpthread.setDataList(datalist);
		udpthread.startThread();
		/*****--******/
		/**********--********/
		udpcheckline = new UdpCheckDevLine(this);
		udpcheckline.start();
		
	}

	@Override
	public void onDestroy() {
		datalist.StopTimer();
		udpthread.stopThread();
		udpcheckline.stop();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class AutoBinder extends Binder{
		
		public SmartHomeService getService(){
			return SmartHomeService.this;
		}
		
		public void addPacketToSend(BasicPacket packet){
			datalist.addOnePacketToSend(packet);
		}
		
		public void removePacketFromList(int seq){
			datalist.delOnePacketFromList(seq);
		}
		
		public UdpCheckDevLine getUdpCheckLine(){
			return udpcheckline;
		}
	}
}
