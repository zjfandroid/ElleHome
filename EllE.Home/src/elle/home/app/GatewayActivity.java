package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.GateWayControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.protocol.PlugControlPacket;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class GatewayActivity extends BaseActivity {

	public String TAG = "PlugActivity";
	
	boolean isFresh;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	//设备的mac地址
	private long devmac;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	private AutoService.AutoBinder autoBinder;
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			autoBinder = (AutoService.AutoBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			autoBinder = null;
		}
		
	};
	
	private void userBindService(){
		Intent bindIntent = new Intent(this,AutoService.class);
		bindService(bindIntent,connection,BIND_AUTO_CREATE);
	}
	
	private void userUnbindService(){
		unbindService(connection);
	}
	
	OnRecvListener recvListener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
		}};
	
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_gateway);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		
		
		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		dev = new OneDev();
		dev.getFromDatabase(devmac, this);
		
		if(this.connectStatus == PublicDefine.ConnectRemote){
			this.conip = dev.remoteip;
			this.conport = dev.remoteport;
		}else{
			try {
				this.conip = InetAddress.getByName("255.255.255.255");
				this.conport = PublicDefine.LocalUdpPort;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
		
		Log.d(TAG,"plug ip:"+this.conip.toString()+" port:"+this.conport);
		
		Log.d(TAG,"devmac"+devmac+" mac:"+dev.mac+" name:"+dev.devname);
		
		this.userBindService();
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){
			@Override
			public void run() {
					PlugControlPacket packet = new PlugControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					packet.plugCheck(DataExchange.longToEightByte(dev.mac), recvListener);
					packet.setImportance(BasicPacket.ImportNormal);
					if(autoBinder!=null){
						//Log.d(TAG,"plug data --> send");
						autoBinder.addPacketToSend(packet);
					}
			}};
		timer.schedule(timetask, 500, 1000);
	}

	@Override
	protected void onStop() {
		timer.cancel();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		this.userUnbindService();
		super.onDestroy();
	}
	
	public void doBackClick(View v){
		vibrator.vibrate(vibarator_time);
		finish();
	}
	
	public void doClick(View v){
		GateWayControlPacket packet = new GateWayControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		packet.setImportance(BasicPacket.ImportHigh);
		
		switch (v.getId()) {
		case R.id.btn_allow_in:
			packet.allowIn(DataExchange.longToEightByte(dev.mac), recvListener);
			break;
		case R.id.btn_ban_in:
			packet.banIn(DataExchange.longToEightByte(dev.mac), recvListener);
			break;
		case R.id.btn_request_out:
			packet.requestDevOut(DataExchange.longToEightByte(dev.mac), recvListener, dev);
			break;
		case R.id.btn_post_devin:
			packet.postDevIn(DataExchange.longToEightByte(dev.mac), recvListener, null);
			break;
		case R.id.btn_allow_devin:
			packet.allowDevIn(DataExchange.longToEightByte(dev.mac), recvListener, null);
			break;

		default:
			break;
		}
		
		autoBinder.addPacketToSend(packet);
	}
}
