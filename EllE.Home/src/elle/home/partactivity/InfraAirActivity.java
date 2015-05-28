package elle.home.partactivity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.DevInfraData;
import elle.home.database.OneDev;
import elle.home.database.OneInfraData;
import elle.home.hal.InfraMatchThread;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.InfraControlPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.InfraAirView;
import elle.home.uipart.InfraAirView.OnInfraAirChange;
import elle.home.uipart.SilderButton;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfraAirActivity extends BaseActivity {

	public String TAG = "InfraAirActivity";

	//设备的mac地址
	private long devmac;
	private String devname;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	//学习时的屏蔽界面
	LinearLayout airmasking;
	//红外空调的学习界面
	InfraAirView airview;
	//空调数据库
	DevInfraData airdata;
	
	//当前点击的功能码
	private int funid;
	//当前是否进入学习状态
	boolean isstudy;
	
	private Context mContext;
	//返回按钮
	private ImageButton backbtn;
	private TextView title;
	
	//取消显示按钮
	private Button maskingBackBtn;
	
	//滑动按钮
	SilderButton silderBtn;
	
	//当前学习的seq
	private int studyseq;

	//是否需要云端匹配，空调默认匹配
	private boolean isNeedCmp;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 0:		//show masking
				airmasking.setVisibility(View.VISIBLE);
				isstudy = true;
				break;
			case 1:		//hide masking
				airmasking.setVisibility(View.INVISIBLE);
				isstudy = false;
				break;
			case 2:
				Toast.makeText(mContext, getResources().getString(R.string.infra_air_study_succeed), Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(mContext, getResources().getString(R.string.infra_air_study_failure), Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(mContext, getResources().getString(R.string.infra_air_study_quit), Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(mContext, getResources().getString(R.string.infra_air_match_succeed), Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(mContext, getResources().getString(R.string.infra_air_match_error), Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		dev.getFromDatabase(devmac,devname, this);
		
		airdata = new DevInfraData(dev.devname);
		airdata.findAllData(this);
		
		mContext = this;
		
		if(this.connectStatus == PublicDefine.ConnectRemote){
			this.conip = dev.remoteip;
			this.conport = dev.remoteport;
		}else{
			try {
				this.conip = InetAddress.getByName("255.255.255.255");
				this.conport = PublicDefine.LocalUdpPort;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		title = (TextView)this.findViewById(R.id.title_bar_text);
		title.setText(dev.devname);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mContext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		maskingBackBtn = (Button)this.findViewById(R.id.masking_back_btn);
		maskingBackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				InfraControlPacket infra = new InfraControlPacket(conip,conport);
				if(connectStatus == PublicDefine.ConnectRemote)
					infra.setPacketRemote(true);
				infra.infraQuitStudy(DataExchange.longToEightByte(dev.mac), quitlistener);				
				if(autoBinder!=null){
					autoBinder.addPacketToSend(infra);
					autoBinder.removePacketFromList(studyseq);
				}
			}
		});
		airview = (InfraAirView)this.findViewById(R.id.infraAirView1);
		airview.setAirListener(new OnInfraAirChange(){

			@Override
			public void onOffClick(boolean longclick) {
				// TODO Auto-generated method stub
				funid =31;
				if(longclick){
					studyInfraData(funid);
					Log.d(TAG,"长按重新学习"+funid);
					
				}else{
					for(int i=0;i<airdata.infraDataList.size();i++){
						if(airdata.infraDataList.get(i).funid == funid){
							sendInfraData(funid);
							Log.d(TAG,"可以发送出去空调红外数据："+funid);							
							return;
						}
					}
					studyInfraData(funid);
					Log.d(TAG,"需要学习空调的"+funid);
				}
			}

			@Override
			public void onTempChange(int temp, boolean longclick) {
				// TODO Auto-generated method stub
				if(airview.modestatus == airview.ModeCold){
					funid = temp-15;
				}else{
					funid = temp;
				}
				if(longclick){
					Log.d(TAG,"学习温度功能:"+funid);
					studyInfraData(funid);
				}else{
					for(int i=0;i<airdata.infraDataList.size();i++){
						if(airdata.infraDataList.get(i).funid == funid){
							sendInfraData(funid);
							Log.d(TAG,"可以发送出去空调红外数据："+funid);							
							return;
						}
					}
					studyInfraData(funid);
					Log.d(TAG,"需要学习空调的"+funid);
				}
			}

			@Override
			public void onModeChange(int mode, boolean longclick) {
				// TODO Auto-generated method stub
				if(airview.modestatus == airview.ModeCold){
					funid = airview.temp-15;
				}else{
					funid = airview.temp;
				}
				for(int i=0;i<airdata.infraDataList.size();i++){
					if(airdata.infraDataList.get(i).funid == funid){
						sendInfraData(funid);
						Log.d(TAG,"可以发送出去空调红外数据："+funid);							
						return;
					}
				}
				studyInfraData(funid);
				Log.d(TAG,"需要学习空调的"+funid);
			}
		});
		
		airmasking = (LinearLayout)this.findViewById(R.id.infra_air_masking);
		airmasking.setVisibility(View.INVISIBLE);
		airmasking.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		isNeedCmp = true;
		silderBtn = (SilderButton)this.findViewById(R.id.silderButton1);
		silderBtn.setOnOff(true);
		silderBtn.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				// TODO Auto-generated method stub
				isNeedCmp = tmp;
				if(tmp){
					Log.d(TAG,"需要云端匹配");
				}else{
					Log.d(TAG,"不要云端匹配");
				}
			}
			
			@Override
			public void clickUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void clickDown() {
				// TODO Auto-generated method stub
				
			}
		});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	OnRecvListener studylistener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			// TODO Auto-generated method stub
			if(isstudy==true){
				if(packetcheck!=null){
					if(packetcheck.xdata == null){
						Log.d(TAG,"错误的红外数据");
						
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);
					}else{
						Log.d(TAG,"funid:"+funid+" 收到了学习到的红外数据:"+DataExchange.byteArrayToHexString(packetcheck.xdata));
						if(isNeedCmp){
							InfraMatchThread thread = new InfraMatchThread(packetcheck.xdata,new InfraMatchThread.onInfraCheck() {
								
								@Override
								public void getInfraCheck(JSONObject json) {
									// TODO Auto-generated method stub
									Message msg = new Message();
									if(json==null){
										Log.d(TAG,"匹配异常");
										msg.what = 6;
									}else{
										Log.d(TAG,"匹配返回数据,将数据加入到数据库中，并提示用户匹配到~");
										dealServerMatchData(json);
										msg.what = 5;
									}
									handler.sendMessage(msg);
								}
							});
							
							thread.start();
						}
						
						
						OneInfraData data = new OneInfraData();
						data.setData(dev.devname, dev.mac, dev.type, funid, packetcheck.xdata);
						airdata.addOneInfraData(data, mContext);
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
					}
					
				}
				else{
					Log.d(TAG,"空了啊，超时");
				}
				if(autoBinder!=null){
					autoBinder.removePacketFromList(studyseq);
				}
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}
		
	};
	
	OnRecvListener quitlistener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			// TODO Auto-generated method stub
			Log.d(TAG,"收到退出学习的 ");
			//Toast.makeText(mContext, "退出学习模式~", Toast.LENGTH_SHORT).show();
			Message msg = new Message();
			msg.what = 4;
			handler.sendMessage(msg);
		}
		
	};
	
	OnRecvListener listener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			// TODO Auto-generated method stub
			Log.d(TAG,"发送数据成功："+funid);
		}
		
	};
	
	private void saveInfraData(int funId,byte[] xdata){
		OneInfraData data = new OneInfraData();
		data.setData(dev.devname, dev.mac, dev.type, funId, xdata);
		airdata.addOneInfraData(data, mContext);
	}
	
	private void dealServerMatchData(JSONObject json){
		String isfind = json.optString("isFind");
		if(isfind.equalsIgnoreCase("YES")){
			
			String tmp = json.optString("100","none");
			if(!tmp.equalsIgnoreCase("none")){
				Log.d(TAG, "有关机键");
				saveInfraData(31,DataExchange.dbStringToBytes(tmp));
			}
			for(int i=0;i<15;i++){
				tmp = json.optString(String.valueOf(116+i),"none");
				Log.d(TAG,"制热"+(1+i));
				saveInfraData((i+16),DataExchange.dbStringToBytes(tmp));
			}
			
			for(int i=0;i<15;i++){
				tmp = json.optString(String.valueOf(216+i),"none");
				Log.d(TAG,"制冷"+(1+i));
				saveInfraData((i+1),DataExchange.dbStringToBytes(tmp));
			}

			Log.d(TAG,"匹配的红外数据加入到数据库成功");
		}else if(isfind.equalsIgnoreCase("NO")){
			
		}else{
			
		}
	}
	
	/**
	 * 学习红外数据
	 * */
	private void studyInfraData(int funid){
		this.funid = funid;
		InfraControlPacket infra = new InfraControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote)
			infra.setPacketRemote(true);
		infra.infraEntryStudy(DataExchange.longToEightByte(dev.mac), studylistener);
		infra.setImportance(BasicPacket.ImportStatic);
		this.studyseq = infra.seq;
		if(autoBinder!=null){
			autoBinder.addPacketToSend(infra);
		}
		Message msg = new Message();
		msg.what = 0;
		handler.sendMessage(msg);	
	}
	
	/**
	 * 发送红外数据
	 * */
	private void sendInfraData(int funid){
		this.funid = funid;
		InfraControlPacket infra = new InfraControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote)
			infra.setPacketRemote(true);
		Log.d(TAG,"发送红外数据："+DataExchange.dbBytesToString(airdata.getInfraData(funid)));
		infra.sendCommand(DataExchange.longToEightByte(dev.mac), listener, airdata.getInfraData(funid));
		if(autoBinder!=null){
			autoBinder.addPacketToSend(infra);
		}
	}
	
	
	private AutoService.AutoBinder autoBinder;
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			autoBinder = (AutoService.AutoBinder) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
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
	
	Timer timer;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
			
		}, 500,1000);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub		
		super.onStop();
		timer.cancel();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		airview.recyleBit();
		this.userUnbindService();
	}

	
	
	
}
