package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.protocol.PlugControlPacket;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.SilderButton;

public class PlugActivity extends BaseActivity {

	public String TAG = "PlugActivity";
	//
	ImageButton plugBtn;
	TextView plugtext;
	TextView plugShowText;
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
	
	//设定时间显示背景
	FrameLayout setTimerLayout;
	TimePicker timerpicker;
	Button timerBtnSet;
	Button timerBtnCancel;
	boolean setOpenOrClose;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0:
				if(plugStatus){
					plugBtn.setImageDrawable(plugOndraw);
					plugtext.setText(getResources().getString(R.string.plug_switch_on));
					
				}else{
					plugBtn.setImageDrawable(plugOffdraw);
					plugtext.setText(getResources().getString(R.string.plug_switch_off));
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
	
	OnRecvListener recvListener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			// TODO Auto-generated method stub
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
					openTimerStr = getClockExchange(tmp);
				}else{
					openTimerStr = "--:--:--";
				}
				
				tmp = DataExchange.fourByteToInt(packetcheck.xdata[7], packetcheck.xdata[8], packetcheck.xdata[9], packetcheck.xdata[10]);
				if(tmp!=0){	
					closeTimerStr = getClockExchange(tmp);
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
		// TODO Auto-generated method stub
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
		
		timerpicker= (TimePicker)this.findViewById(R.id.timePicker1);
		timerpicker.setIs24HourView(true);
		
		timerpicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				Log.d(TAG,"timer change:"+hourOfDay+" min:"+minute);
			}
		});
		
		openLayoutUp = (LinearLayout)this.findViewById(R.id.plug_timer_up_layout);
		closeLayoutDown = (LinearLayout)this.findViewById(R.id.plug_timer_down_layout);
		
		plugTimerUp = (ImageButton)this.findViewById(R.id.plug_timer_upbk);
		plugTimerDown = (ImageButton)this.findViewById(R.id.plug_timer_downbk);
		
		openTimerOnOff = (SilderButton)this.findViewById(R.id.openSilderButton);
		closeTimerOnOff = (SilderButton)this.findViewById(R.id.closeSilderBtn);
		
		openTimerTv = (TextView)this.findViewById(R.id.openTimerTv);
		closeTimerTv = (TextView)this.findViewById(R.id.closeTimerTv);
		
		setTimerLayout = (FrameLayout)this.findViewById(R.id.setTimeLayout);
		setTimerLayout.setVisibility(View.INVISIBLE);
		
		setTimerLayout.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		timerBtnSet = (Button)this.findViewById(R.id.timerSetBtn);
		timerBtnCancel = (Button)this.findViewById(R.id.timerCancel);
		
		plugShowText = (TextView)this.findViewById(R.id.setTimerShowText);
		
		timerBtnSet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTimerLayout.setVisibility(View.INVISIBLE);
				Calendar c = Calendar.getInstance();
				
				int setTimeCount = timerpicker.getCurrentHour()*3600+timerpicker.getCurrentMinute()*60;
				int curTimeCount = c.get(Calendar.HOUR_OF_DAY)*3600+c.get(Calendar.MINUTE)*60;
				
				Log.d(TAG,"btn set timer hour:"+timerpicker.getCurrentHour()+" min:"+timerpicker.getCurrentMinute()+" timeCount:"+setTimeCount);
				Log.d(TAG,"sys hour:"+c.get(Calendar.HOUR_OF_DAY)+" sys min:"+c.get(Calendar.MINUTE)+" curTimeCount:"+curTimeCount);
				
				int offSet = 0;
				if(Math.abs(setTimeCount - curTimeCount)<10){
					
				}else if(setTimeCount>curTimeCount){
					offSet = setTimeCount - curTimeCount;
				}else{
					offSet = (24*3600 - curTimeCount)+setTimeCount;
				}
				
				if(setOpenOrClose){
					//set open
					Log.d(TAG, "plug time set open offset:"+offSet);
					setOpenTime(offSet);
				}else{
					//set close
					Log.d(TAG, "plug time set close offset:"+offSet);
					setCloseTime(offSet);
				}
				
			}
		});
		
		timerBtnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTimerLayout.setVisibility(View.INVISIBLE);
			}
		});
	
		isFresh = true;
		
		openTimerOnOff.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				// TODO Auto-generated method stub
				Log.d(TAG,"open timer onoff");
				if(!tmp){
					setOpenTime(0);
				}
			}

			@Override
			public void clickDown() {
				// TODO Auto-generated method stub
				isFresh = false;
			}

			@Override
			public void clickUp() {
				// TODO Auto-generated method stub
				isFresh = true;
			}
		});
		
		closeTimerOnOff.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				// TODO Auto-generated method stub
				Log.d(TAG,"close timer onoff");
				if(!tmp){
					setCloseTime(0);
				}
			}

			@Override
			public void clickDown() {
				// TODO Auto-generated method stub
				isFresh = false;
			}

			@Override
			public void clickUp() {
				// TODO Auto-generated method stub
				isFresh = true;
			}
		});
		
		plugTimerDown.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					plugTimerDown.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_down));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					setOpenOrClose = false;
					plugShowText.setText(getResources().getString(R.string.plug_set_switch_off_time));
					plugTimerDown.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_null));
					setTimerLayout.setVisibility(View.VISIBLE);
					Calendar c = Calendar.getInstance();
					timerpicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
					break;
				}
				return true;
			}
		});
		
		plugTimerUp.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					plugTimerUp.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_up));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					setOpenOrClose = true;
					plugShowText.setText(getResources().getString(R.string.plug_set_switch_on_time));
					plugTimerUp.setImageDrawable(getResources().getDrawable(R.drawable.plug_time_null));
					setTimerLayout.setVisibility(View.VISIBLE);
					Calendar c = Calendar.getInstance();
					timerpicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
					break;
				}
				return true;
			}
		});
		
		
		vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
		leftbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Log.d(TAG,"plug ip:"+this.conip.toString()+" port:"+this.conport);
		
		Log.d(TAG,"devmac"+devmac+" mac:"+dev.mac+" name:"+dev.devname);
		
		titletext.setText(dev.devname);
		
		this.userBindService();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
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
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onStop();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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
			//Log.d(TAG,"plug data --> send");
			autoBinder.addPacketToSend(packet);
		}
	}
	
	private String getClockExchange(int sec)
	{
		String tmp = null;
		Date date = new Date();
		Calendar now = Calendar.getInstance();
	    TimeZone timeZone = now.getTimeZone();
	    
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		date.setTime(sec*1000+(System.currentTimeMillis()));
		tmp = sdf.format(date);
		
		//Log.d(TAG,"plug activity:"+timeZone.getDisplayName()+" offset:"+timeZone.getRawOffset());
		return tmp;
	}
	
}
