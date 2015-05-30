package elle.home.partactivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.InfraControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.InfraAirView;
import elle.home.uipart.InfraAirView.OnInfraAirChange;
import elle.home.utils.ShowInfo;
import elle.homegenius.infrajni.InfraNative;

public class InfraAirActivity extends BaseActivity {

	public String TAG = "InfraAirActivity";

	private int idBrand = 0;
	private int id = 1;
	
	//设备的mac地址
	private long devmac;
	private String devname;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	//红外空调的学习界面
	InfraAirView airview;

	//当前点击的功能码
	private int funid;
	
	private Context mContext;
	//返回按钮
	private ImageButton backbtn;
	private TextView title;

	private boolean isTestMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_infra_air);
		this.userBindService();
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		devname = intent.getStringExtra("devname");
		Log.d(TAG,"infra air mac:"+devmac+" devname:"+devname);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		dev = new OneDev();
		
		isTestMode = intent.getBooleanExtra("isTest", false);
		dev.getFromDatabase(devmac,devname, this);
		
		idBrand = intent.getIntExtra("idBrand", 1);
		id = intent.getIntExtra("id", 1);
		
		mContext = this;
		
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
		
		title = (TextView)this.findViewById(R.id.title_bar_text);
		title.setText(dev.devname);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mContext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		airview = (InfraAirView)this.findViewById(R.id.infraAirView1);
		airview.setAirListener(new OnInfraAirChange(){

			@Override
			public void onOffClick(boolean longclick) {
				byte[] bytes = InfraNative.getAirCommandByBrand(idBrand, id, InfraNative.AIR_TEMP_19, InfraNative.AIR_FLOW_RATE_AUTO, 
						InfraNative.AIR_FLOW_MANUAL_UP, InfraNative.AIR_FLOW_AUTO_OFF, 
						InfraNative.AIR_ONOFF_OFF, InfraNative.AIR_KEY_ONOFF, InfraNative.AIR_MODEL_COLD);
				InfraControlPacket packet = getInfraPacket();
				packet.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
					
					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						if(null != packetcheck){
							ShowInfo.printLogW("_________packetcheck_AIR_ONOFF_OFF__________" + DataExchange.dbBytesToString(packetcheck.data));
						}
					}
				}, bytes);
				autoBinder.addPacketToSend(packet);
			}

			@Override
			public void onTempChange(int temp, boolean longclick) {
				ShowInfo.printLogW("_________onTempChange________" + temp);
				
				byte[] bytes = InfraNative.getAirCommandByBrand(idBrand, id, InfraNative.AIR_TEMP_19 + temp - 19, InfraNative.AIR_FLOW_RATE_AUTO, 
						InfraNative.AIR_FLOW_MANUAL_UP, InfraNative.AIR_FLOW_AUTO_OFF, 
						InfraNative.AIR_ONOFF_ON, InfraNative.AIR_KEY_ONOFF, InfraNative.AIR_MODEL_COLD);
				InfraControlPacket packet = getInfraPacket();
				packet.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
					
					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						if(null != packetcheck){
							ShowInfo.printLogW("_________packetcheck___________" + DataExchange.dbBytesToString(packetcheck.data));
						}
					}
				}, bytes);
				autoBinder.addPacketToSend(packet);
			}

			@Override
			public void onModeChange(int mode, boolean longclick) {
				ShowInfo.printLogW("_______onModeChange________" + mode);
				
				int modeTmp = InfraNative.AIR_MODEL_COLD;
				if(1 == mode){
					modeTmp = InfraNative.AIR_MODEL_HOT;
				}
				
				byte[] bytes = InfraNative.getAirCommandByBrand(idBrand, id, InfraNative.AIR_TEMP_19, InfraNative.AIR_FLOW_RATE_AUTO, 
						InfraNative.AIR_FLOW_MANUAL_UP, InfraNative.AIR_FLOW_AUTO_OFF, 
						InfraNative.AIR_ONOFF_ON, InfraNative.AIR_KEY_ONOFF, modeTmp);
				InfraControlPacket packet = getInfraPacket();
				packet.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
					
					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						if(null != packetcheck){
							ShowInfo.printLogW("_________packetcheck___________" + DataExchange.dbBytesToString(packetcheck.data));
						}
					}
				}, bytes);
				autoBinder.addPacketToSend(packet);
			}
		});
	}
	
	protected InfraControlPacket getInfraPacket() {
		InfraControlPacket infraControlPacket  = new InfraControlPacket(conip, conport);
		infraControlPacket.setImportance(BasicPacket.ImportHigh);
		if(connectStatus == PublicDefine.ConnectRemote){
			infraControlPacket.setPacketRemote(true);
		}
		
		return infraControlPacket;
	}
	
	OnRecvListener listener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			Log.d(TAG,"发送数据成功："+funid);
		}
		
	};
	
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
	
//	Timer timer;

	@Override
	protected void onStart() {
		super.onStart();
//		timer = new Timer();
//		timer.schedule(new TimerTask(){
//
//			@Override
//			public void run() {
//			}
//			
//		}, 500,1000);
	}

	@Override
	protected void onStop() {
		super.onStop();
//		timer.cancel();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		airview.recyleBit();
		this.userUnbindService();
	}
}
