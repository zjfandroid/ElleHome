package elle.home.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import elle.home.database.AllLocationInfo;
import elle.home.hal.UdpCheckDevLine;
import elle.home.hal.UdpCheckNewThread;
import elle.home.hal.UdpThread;
import elle.home.hal.UserData;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.ProtocolDataList;
import elle.home.publicfun.PublicDefine;

public class AutoService extends Service {

	public String TAG = "AutoService";
	
	AutoBinder binder = new AutoBinder();
	
	//后续这个要封装成为一个类
	//接收，发送队列
	ProtocolDataList datalist;
	//udp线程
	UdpThread udpthread;
	
	//后续这两个类也要封装到一起
	//查询是否有新的设备的服务，这里也会找到本地服务设备
	private UdpCheckNewThread udpchecknew;
	//这个负责通信到注册服务器并保持设备的查询状态
	private UdpCheckDevLine udpcheckline;
	
	//所有的地点及对应设备的信息
	AllLocationInfo allInfo;
	
	//简单信息的存储及获取
	UserData userdata;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		userdata = new UserData(this);
		/*******_设置mac地址_*******/
		PublicDefine.setPhoneMac(userdata.getUuid());
		
		/*******-----******/
		allInfo = new AllLocationInfo(this);
		allInfo.findAllLocationInfo();
		/*******-----******/
		
		/*****--******/
		datalist = new ProtocolDataList();
		udpthread = new UdpThread();
		udpthread.setDataList(datalist);
		udpthread.startThread();
		/*****--******/
		/**********--********/
		udpchecknew  = new UdpCheckNewThread(this);
		udpcheckline = new UdpCheckDevLine(this);
		
		udpchecknew.setAllLocationInfo(allInfo);
		udpcheckline.setAllLocationInfo(allInfo);
		
		udpchecknew.startCheck();
		
		udpchecknew.startThread();
		udpcheckline.start();
		
		/**********--********/
		Log.d(TAG,TAG+" onCreate");
	}

	@Override
	public void onDestroy() {
		datalist.StopTimer();
		udpthread.stopThread();
		udpchecknew.stopCheck();
		udpchecknew.stopThread();
		udpcheckline.stop();
		Log.d(TAG,TAG+"停止service");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class AutoBinder extends Binder{
		
		public AutoService getService(){
			return AutoService.this;
		}
		
		public void addPacketToSend(BasicPacket packet){
			datalist.addOnePacketToSend(packet);
		}
		
		public void removePacketFromList(int seq){
			datalist.delOnePacketFromList(seq);
		}
		
		public UdpCheckNewThread getUdpCheckNew(){
			return udpchecknew;
		}
		
		public UdpCheckDevLine getUdpCheckLine(){
			return udpcheckline;
		}
		
		public AllLocationInfo getAllInfo(){
			return allInfo;
		}
		
		public void freshUdpCheckData(){
			udpchecknew.freshALlInfo();
		}
		
	}

}
