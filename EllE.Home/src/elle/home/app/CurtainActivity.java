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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.CurtainControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;

/**
 * Wifi窗帘
 * @author jason
 *
 */
public class CurtainActivity extends BaseActivity{

	public String TAG = "CurtainActivity";
	
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
	public int index;
	
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
			if(packetcheck != null){
				ShowInfo.printLogW("____packetcheck_____" + DataExchange.dbBytesToString(packetcheck.data));
			}else{
				ShowInfo.printLogW("____packetcheck__null___");
			}
		}
	};
	
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_curtain);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
//		this.connectStatus = PublicDefine.ConnectRemote;
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
		
		initViews();
	}

	private void initViews() {
		SeekBar mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				CurtainControlPacket packet = getPacket();
				packet.curtainProgress(DataExchange.longToEightByte(dev.mac), progress, recvListener);
				autoBinder.addPacketToSend(packet);
				ShowInfo.printLogW(progress + "____packet curtainProgress_____" + DataExchange.dbBytesToString(packet.data));
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){
			@Override
			public void run() {
				if(autoBinder!=null){
					CurtainControlPacket packet = getPacket();
					packet.packetCheck(DataExchange.longToEightByte(dev.mac), recvListener);
					autoBinder.addPacketToSend(packet);
					ShowInfo.printLogW("____packet send_____" + DataExchange.dbBytesToString(packet.data));
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
	
	private CurtainControlPacket getPacket() {
		CurtainControlPacket packet = new CurtainControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		packet.setImportance(BasicPacket.ImportHigh);

		return packet;
	}
	
	public void doClick(View v){
		if(null == autoBinder){
			return;
		}
		
		CurtainControlPacket packet = getPacket();
		
		switch (v.getId()) {
		case R.id.tips1:
			packet.curtainOn(DataExchange.longToEightByte(dev.mac), recvListener);
			break;
		case R.id.tips2:
			packet.curtainOff(DataExchange.longToEightByte(dev.mac), recvListener);
			break;
		case R.id.tips3:
			packet.curtainStop(DataExchange.longToEightByte(dev.mac), recvListener);
			break;
		case R.id.btn_reboot:
			packet.curtainReboot(DataExchange.longToEightByte(dev.mac), recvListener);
			break;

		default:
			break;
		}
		
		autoBinder.addPacketToSend(packet);
	}
}
