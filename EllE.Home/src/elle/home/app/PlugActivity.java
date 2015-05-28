package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.protocol.PlugControlPacket;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.SilderButton;

public class PlugActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener{

	public String TAG = "PlugActivity";
	//
	ImageButton plugBtn;
	TextView plugtext;
	boolean plugStatus;
	boolean isSetOpen;
	boolean isSetClose;
	
	boolean isFresh;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	//
	Drawable plugOndraw;
	Drawable plugOnPressdraw;
	Drawable plugOffPressdraw;
	Drawable plugOffdraw;
	
	//设备的mac地址
	private long devmac;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	private ImageButton leftbtn;
	private TextView titletext;
	
	//定时打开背景
	LinearLayout openLayoutUp;
	ImageButton plugTimerUp;
	SilderButton openTimerOnOff;
	TextView openTimerTv;
	String openTimerStr = "--:--:--";
	
	//定时关闭背景
	LinearLayout closeLayoutDown;
	ImageButton plugTimerDown;
	SilderButton closeTimerOnOff;
	TextView closeTimerTv ;
	String closeTimerStr = "--:--:--";
	
	boolean setOpenOrClose;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				if(plugStatus){
					plugBtn.setImageDrawable(plugOndraw);
					plugtext.setText(getResources().getString(R.string.plug_status_on));
					
				}else{
					plugBtn.setImageDrawable(plugOffdraw);
					plugtext.setText(getResources().getString(R.string.plug_status_off));
				}
				openTimerTv.setText(openTimerStr);
				closeTimerTv.setText(closeTimerStr);
				if(isFresh){
					closeTimerOnOff.setOnOff(isSetClose);
					openTimerOnOff.setOnOff(isSetOpen);
				}
				break;
			}
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
	
	OnRecvListener recvListener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			//Log.d(TAG,"收到插座发出信息的回掉");
			if(packetcheck!=null){
				//Log.d(TAG,DataExchange.byteArrayToHexString(packetcheck.data));
				if(packetcheck.xdata[0] == 0){
					plugStatus = false;
				}else{
					plugStatus = true;
				}
				
				if(packetcheck.xdata[1]!=0){
					isSetOpen = true;
				}else{
					isSetOpen = false;
				}
				
				if(packetcheck.xdata[6]!=0){
					isSetClose = true;
				}else{
					isSetClose = false;
				}
				int tmp  = 0;
				tmp = DataExchange.fourByteToInt(packetcheck.xdata[2], packetcheck.xdata[3], packetcheck.xdata[4], packetcheck.xdata[5]);
				if(tmp!=0){	
					openTimerStr = DataExchange.getClockExchange(tmp);
				}else{
					openTimerStr = "--:--:--";
				}
				
				tmp = DataExchange.fourByteToInt(packetcheck.xdata[7], packetcheck.xdata[8], packetcheck.xdata[9], packetcheck.xdata[10]);
				if(tmp!=0){	
					closeTimerStr = DataExchange.getClockExchange(tmp);
				}else{
					closeTimerStr = "--:--:--";
				}
				
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		}};
	
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_plug);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		titletext = (TextView)this.findViewById(R.id.title_bar_text);
		leftbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		plugBtn = (ImageButton)this.findViewById(R.id.plugbutton);
		plugtext = (TextView)this.findViewById(R.id.plugtext);
		plugBtn.setImageDrawable(getResources().getDrawable(R.drawable.plug_on_logo));
		plugStatus = true;
		
		this.plugOndraw = this.getResources().getDrawable(R.drawable.plug_on_logo);
		this.plugOnPressdraw = this.getResources().getDrawable(R.drawable.plug_on_logo_press);
		this.plugOffdraw = this.getResources().getDrawable(R.drawable.plug_off_logo);
		this.plugOffPressdraw = this.getResources().getDrawable(R.drawable.plug_off_logo_press);
		
		openLayoutUp = (LinearLayout)this.findViewById(R.id.plug_timer_up_layout);
		closeLayoutDown = (LinearLayout)this.findViewById(R.id.plug_timer_down_layout);
		
		plugTimerUp = (ImageButton)this.findViewById(R.id.plug_timer_upbk);
		plugTimerDown = (ImageButton)this.findViewById(R.id.plug_timer_downbk);
		
		openTimerOnOff = (SilderButton)this.findViewById(R.id.openSilderButton);
		closeTimerOnOff = (SilderButton)this.findViewById(R.id.closeSilderBtn);
		
		openTimerTv = (TextView)this.findViewById(R.id.openTimerTv);
		closeTimerTv = (TextView)this.findViewById(R.id.closeTimerTv);
	
		isFresh = true;
		
		openTimerOnOff.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				Log.d(TAG,"open timer onoff");
				if(!tmp){
					setOpenTime(0);
				}
			}

			@Override
			public void clickDown() {
				isFresh = false;
			}

			@Override
			public void clickUp() {
				isFresh = true;
			}
		});
		
		closeTimerOnOff.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				Log.d(TAG,"close timer onoff");
				if(!tmp){
					setCloseTime(0);
				}
			}

			@Override
			public void clickDown() {
				isFresh = false;
			}

			@Override
			public void clickUp() {
				isFresh = true;
			}
		});
		
		plugTimerDown.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					plugTimerDown.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_down));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					setOpenOrClose = false;
					plugTimerDown.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_null));
					showTimePicker();
					break;
				}
				return true;
			}
		});
		
		plugTimerUp.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					plugTimerUp.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_up));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					setOpenOrClose = true;
					plugTimerUp.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_null));
					showTimePicker();
					break;
				}
				return true;
			}
		});
		
		vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
		leftbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					vibrator.vibrate(vibarator_time);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		plugBtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					vibrator.vibrate(vibarator_time);
					if(plugStatus){
						plugBtn.setImageDrawable(plugOnPressdraw);
					}else{
						plugBtn.setImageDrawable(plugOffPressdraw);
					}
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					if(plugStatus){
						plugBtn.setImageDrawable(plugOndraw);
						
						PlugControlPacket packetoff = new PlugControlPacket(conip,conport);
						if(connectStatus == PublicDefine.ConnectRemote){
							packetoff.setPacketRemote(true);
						}
						packetoff.plugOff(DataExchange.longToEightByte(dev.mac), recvListener);
						packetoff.setImportance(BasicPacket.ImportHigh);
						autoBinder.addPacketToSend(packetoff);
						
					}else{
						plugBtn.setImageDrawable(plugOffdraw);
						
						PlugControlPacket packeton = new PlugControlPacket(conip,conport);
						if(connectStatus == PublicDefine.ConnectRemote){
							packeton.setPacketRemote(true);
						}
						packeton.plugOn(DataExchange.longToEightByte(dev.mac), recvListener);
						packeton.setImportance(BasicPacket.ImportHigh);
						autoBinder.addPacketToSend(packeton);
					}
				}
				
				return true;
			}
		});
		
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
		
		titletext.setText(dev.devname);
		
		this.userBindService();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	protected void showTimePicker() {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        timePickerDialog.setVibrate(true);
        timePickerDialog.show(getSupportFragmentManager(), "");
//        timePickerDialog.setCloseOnSingleTapMinute(false);
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
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		timer.cancel();
		super.onStop();
	}

	
	@Override
	protected void onDestroy() {
		this.userUnbindService();
		openTimerOnOff.recyleBit();
		closeTimerOnOff.recyleBit();
		super.onDestroy();
	}
	
	private void setOpenTime(int count){
		PlugControlPacket packet = new PlugControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		packet.plugSetOpen(DataExchange.longToEightByte(dev.mac),count,recvListener);
		packet.setImportance(BasicPacket.ImportHigh);
		if(autoBinder!=null){
			//Log.d(TAG,"plug data --> send");
			autoBinder.addPacketToSend(packet);
		}
	}
	
	private void setCloseTime(int count){
		PlugControlPacket packet = new PlugControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		packet.plugSetClose(DataExchange.longToEightByte(dev.mac),count,recvListener);
		packet.setImportance(BasicPacket.ImportHigh);
		if(autoBinder!=null){
			autoBinder.addPacketToSend(packet);
		}
	}
	
	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int tag) {
		int offSet = DataExchange.getOffset(hourOfDay, minute);
		if(tag == TimePickerDialog.TAG_LEFT){
			setOpenTime(offSet);
		}else{
			setCloseTime(offSet);
		}
	}
}
