package elle.home.app;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
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
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.RgbLightView;
import elle.home.uipart.RgbLightView.OnRgbLightChange;
import elle.home.uipart.SilderButton;

public class LightRgbActivity extends BaseActivity {

	public String TAG = "LightRgbActivity";
	
	public final int freshBackground = 1;
	public final int freshUiPart = 2;
	
	public final int statusColor = 1;
	public final int statusWhite = 0;
	
	public final float BlueAdjust = 0.75f;
	public final float GreenAdjust = 0.8f;
	
	//两张背景
	private Drawable statusOnDrawable;
	private Drawable statusOffDrawable;
	private ImageButton ibtn;
	private ImageButton rightbtn;
	private TextView titletext;
	private LinearLayout menulayout;
	private TextView sleep15min;
	private TextView sleep30min;
	private LinearLayout sleeplayout;
	private TextView sleeptimeshow;
	private TextView sleeptimecancel;
	
	//控制视图
	RgbLightView rgblight;
	LinearLayout linearbackground2;
	Context mContext;
	public boolean couldFreshLux;
	
	//随机按钮视图
	LinearLayout randomLayout;
	
	//
	boolean rgbLightStatus;
	boolean randomStatus;
	int rgbWhiteStatus;
	public int colorRed;
	public int colorGreen;
	public int colorBlue;
	public int sleeptime;
	
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	//mac地址
	private long devmac;
	private OneDev dev;
	//通信状态标识，外部传入，提示现在是在本地还是远程通信
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	//是否开启随机模式
	SilderButton randombtn;
	SilderButton btnShake;

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
			if(packetcheck!=null){
				//Log.d(TAG,"收到灯泡发出的信息的回应");
				if(packetcheck.xdata.length==14){
					sleeptime = 0;
				}else if(packetcheck.xdata.length==16){
					sleeptime = DataExchange.twoByteToInt(packetcheck.xdata[14], packetcheck.xdata[15]);
				}
				if(rgbLightStatus == false){
					if(packetcheck.xdata[0]!=0){
						//randomLayout.setAlpha((float) 1.0);
						setBackground(true);
						rgbLightStatus = true;
					}
				}else{
					if(packetcheck.xdata[0]==0){
						//randomLayout.setAlpha((float) 0.2);
						setBackground(false);
						rgbLightStatus = false;
					}
				}
				
				if(packetcheck.xdata[1]!=0){
					randomStatus = true;
				}else{
					randomStatus = false;
				}
				
				//white
				if(DataExchange.twoByteToInt(packetcheck.xdata[8], packetcheck.xdata[9])>0){
					//灯泡状态为白色
					rgbWhiteStatus = statusWhite;
				}else{
					rgbWhiteStatus = statusColor;
					if(couldFreshLux){
						colorRed = LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[2], packetcheck.xdata[3]));
						colorGreen = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5]))/GreenAdjust);
						colorBlue = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[6], packetcheck.xdata[7]))/BlueAdjust);
					}
					
					//Log.d(TAG,"状态颜色："+rgblight.colorRed+" "+rgblight.colorGreen+" "+rgblight.colorBlue);
				}
				
				rgblight.barMoveAngle = DataExchange.twoByteToInt(packetcheck.xdata[12], packetcheck.xdata[13]);
				
				Message msg = new Message();
				msg.what = freshUiPart;
				handler.sendMessage(msg);
				
			}
		}
		
	};

	Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		sleeptime = 0;
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac",0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		dev = new OneDev();
		dev.getFromDatabase(devmac, mContext);
		
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
		
		Log.d(TAG,"light ip:"+this.conip.toString()+" port:"+this.conport);
		
		Log.d(TAG,"devmac"+devmac+" mac:"+dev.mac+" name:"+dev.devname);
		this.setContentView(R.layout.activity_light_rgb);
		rgblight = (RgbLightView)this.findViewById(R.id.rgbLightView1);
		linearbackground2 = (LinearLayout)this.findViewById(R.id.rgb_activity_bk2);
		ibtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		titletext = (TextView)this.findViewById(R.id.title_bar_text);
		btnShake = (SilderButton)this.findViewById(R.id.silderButtonShake);
		randombtn = (SilderButton)this.findViewById(R.id.silderButton1);
		titletext.setText(dev.devname);
		randomLayout = (LinearLayout)this.findViewById(R.id.randomshow);
		menulayout = (LinearLayout)this.findViewById(R.id.light_menu_layout);
		rightbtn = (ImageButton)this.findViewById(R.id.title_btn_right);
		sleep15min = (TextView)this.findViewById(R.id.sleep15min);
		sleep30min = (TextView)this.findViewById(R.id.sleep30min);
		sleeplayout = (LinearLayout)this.findViewById(R.id.sleepshow);
		sleeptimeshow = (TextView)this.findViewById(R.id.sleeptimeshow);
		sleeptimecancel = (TextView)this.findViewById(R.id.sleepcancel);
		init();
		
		this.userBindService();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void init(){
		
		rgbWhiteStatus = statusWhite;
		
		vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		couldFreshLux = true;
		rgbLightStatus = true;
		
		this.statusOffDrawable = this.getResources().getDrawable(R.drawable.rgb_light_bk_black);
		this.statusOnDrawable = this.getResources().getDrawable(R.drawable.rgb_light_bk);
		
		this.linearbackground2.setBackground(statusOffDrawable);
		this.linearbackground2.setAlpha(0);
		
		sleeplayout.setVisibility(View.INVISIBLE);
		
		linearbackground2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					if(menulayout.isShown()){
						menulayout.setVisibility(View.INVISIBLE);
					}
				}
				return false;
			}
		});
		
		sleep15min.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"点击了睡眠十五分钟的时间");
				if(menulayout.isShown()){
					menulayout.setVisibility(View.INVISIBLE);
					UMeng_OnEventValue(EVENT_ID_CLICK_SLEEP,15);
					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					packet.setImportance(BasicPacket.ImportHigh);
					packet.lightSleep(DataExchange.longToEightByte(dev.mac), recvListener,900);
					autoBinder.addPacketToSend(packet);
				}
			}
		});
		
		sleep30min.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"点击了睡眠三十分钟的时间");
				if(menulayout.isShown()){
					UMeng_OnEventValue(EVENT_ID_CLICK_SLEEP,30);
					menulayout.setVisibility(View.INVISIBLE);
					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					packet.setImportance(BasicPacket.ImportHigh);
					packet.lightSleep(DataExchange.longToEightByte(dev.mac), recvListener,1800);
					autoBinder.addPacketToSend(packet);
				}
			}
		});
		
		sleeptimecancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"点击了取消灯泡睡眠的按钮");
				if(menulayout.isShown()){
					menulayout.setVisibility(View.INVISIBLE);
					UMeng_OnEvent(EVENT_ID_CLICK_CANCEL_SLEEP);
					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					packet.setImportance(BasicPacket.ImportHigh);
					packet.lightSleep(DataExchange.longToEightByte(dev.mac), recvListener,0);
					autoBinder.addPacketToSend(packet);
				}
			}
		});
		
		menulayout.setVisibility(View.INVISIBLE);
		menulayout.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP){
					if(menulayout.isShown()){
						menulayout.setVisibility(View.INVISIBLE);
					}else{
						menulayout.setVisibility(View.VISIBLE);
					}
				}else if(event.getAction() == MotionEvent.ACTION_DOWN){
					//vibrator.vibrate(vibarator_time);
				}
				return true;
			}
		});
		
		rightbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					if(menulayout.isShown()){
						menulayout.setVisibility(View.INVISIBLE);
					}else{
						menulayout.setVisibility(View.VISIBLE);
					}
				}else if(event.getAction() == MotionEvent.ACTION_DOWN){
					vibrator.vibrate(vibarator_time);
				}
				return true;
			}
		});
		
		btnShake.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				dev.setCanShake(mContext, tmp);
				if(tmp){
					UMeng_OnEvent(EVENT_ID_CLICK_SHAKE, KEY_SHAKE_ONOFF, R.string.plug_status_on);
				}else{
					UMeng_OnEvent(EVENT_ID_CLICK_SHAKE, KEY_SHAKE_ONOFF, R.string.plug_status_off);
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
		btnShake.setOnOff(dev.getCanShake(mContext));
		
		randombtn.setListener(new SilderButton.OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				LightControlPacket packet = new LightControlPacket(conip,conport);
				if(connectStatus == PublicDefine.ConnectRemote){
					packet.setPacketRemote(true);
				}
				if(tmp){
					UMeng_OnEvent(EVENT_ID_CLICK_RANDOM_CHANGE_COLOR, KEY_RADNOM_CHANGE_COLOR_ONOFF, R.string.plug_status_on);
				}else{
					UMeng_OnEvent(EVENT_ID_CLICK_RANDOM_CHANGE_COLOR, KEY_RADNOM_CHANGE_COLOR_ONOFF, R.string.plug_status_off);
				}
				packet.setImportance(BasicPacket.ImportHigh);
				packet.lightFree(DataExchange.longToEightByte(dev.mac), recvListener, tmp);
				autoBinder.addPacketToSend(packet);
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
		
		ibtn.setOnTouchListener(new View.OnTouchListener() {
			
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
		
		rgblight.setOnChangeListener(new OnRgbLightChange(){

			@Override
			public void onLuxStart(RgbLightView view) {
				// TODO Auto-generated method stub
				couldFreshLux = false;
			}

			@Override
			public void onLuxChange(RgbLightView view, int progress) {
				// TODO Auto-generated method stub
				//Log.d(TAG,"onLuxChange:"+progress);
				couldFreshLux = false;

					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					if(rgbWhiteStatus==statusColor){
						//Log.d(TAG,"调整颜色的亮度，r："+rgblight.colorRed+" g:"+rgblight.colorGreen+" b:"+rgblight.colorBlue+" lux:"+rgblight.barMoveAngle);
						packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, ColorToLux(colorRed), 
								(int)((float)ColorToLux(colorGreen)*GreenAdjust),(int)((float)ColorToLux(colorBlue)*BlueAdjust), 0, rgblight.barMoveAngle, 300);
						packet.setImportance(BasicPacket.ImportNormal);
						autoBinder.addPacketToSend(packet);
					}else{
						packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, 0,0,0, WhiteToLux(rgblight.barMoveAngle), rgblight.barMoveAngle, 300);
						packet.setImportance(BasicPacket.ImportNormal);
						autoBinder.addPacketToSend(packet);
					}
				
			}

			@Override
			public void onLuxStop(RgbLightView view, int progress) {
				// TODO Auto-generated method stub
				//Log.d(TAG,"onLuxStop:"+progress);
				couldFreshLux = true;
				
					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					if(rgbWhiteStatus==statusColor){
						packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, ColorToLux(colorRed), 
								(int)((float)ColorToLux(colorGreen)*GreenAdjust),(int)((float)ColorToLux(colorBlue)*BlueAdjust), 0, rgblight.barMoveAngle, 800);
						packet.setImportance(BasicPacket.ImportNormal);
						autoBinder.addPacketToSend(packet);
					}else{
						packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, 0,0,0, WhiteToLux(rgblight.barMoveAngle), rgblight.barMoveAngle, 800);
						packet.setImportance(BasicPacket.ImportNormal);
						autoBinder.addPacketToSend(packet);
					}
					UMeng_OnEvent(EVENT_ID_CLICK_CHANGE_LUX ,"progress", progress+"" );
				
			}

			@Override
			public void onColorStart(RgbLightView view) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onColorChange(RgbLightView view, int r, int g, int b) {
				// TODO Auto-generated method stub
				Log.d(TAG,"onColorChange red:"+r+" green:"+g+" blue:"+b);

					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					
					colorRed = ColorToLux(r);
					colorGreen = (int)((float)ColorToLux(g)*GreenAdjust);
					colorBlue = (int)((float)ColorToLux(b)*BlueAdjust);
					
					packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, colorRed, colorGreen, colorBlue, 0, rgblight.barMoveAngle,300);
					packet.setImportance(BasicPacket.ImportLow);
					autoBinder.addPacketToSend(packet);

			}

			@Override
			public void onColorStop(RgbLightView view, int r, int g, int b) {
				// TODO Auto-generated method stub
				Log.d(TAG,"onColorStop red:"+r+" green:"+g+" blue:"+b);

					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					UMeng_OnEvent(EVENT_ID_CLICK_CHANGE_LIGHT_COLOR , "R" , r+"" , "G" , g+"" , "B" , b+"" );
					colorRed = ColorToLux(r);
					colorGreen = (int)((float)ColorToLux(g)*GreenAdjust);
					colorBlue = (int)((float)ColorToLux(b)*BlueAdjust);
					
					packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, colorRed, colorGreen, colorBlue, 0, rgblight.barMoveAngle,800);
					packet.setImportance(BasicPacket.ImportHigh);
					autoBinder.addPacketToSend(packet);
				
			}

			@Override
			public void onWhiteClick(RgbLightView view) {
				// TODO Auto-generated method stub
				Log.d(TAG,"onWhiteClick:"+rgblight.barMoveAngle);

					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					UMeng_OnEvent(EVENT_ID_CLICK_WHITE_LIGHT);

					packet.lightColor(DataExchange.longToEightByte(dev.mac), recvListener, 0, 0, 0, WhiteToLux(rgblight.barMoveAngle), rgblight.barMoveAngle,800);
					packet.setImportance(BasicPacket.ImportHigh);
					autoBinder.addPacketToSend(packet);

			}

			@Override
			public void onOnOffClick(RgbLightView view) {
				// TODO Auto-generated method stub
				Log.d(TAG,"onOnOffClick");
				
				if(rgbLightStatus==true){
	
						LightControlPacket packetoff = new LightControlPacket(conip,conport);
						if(connectStatus == PublicDefine.ConnectRemote){
							packetoff.setPacketRemote(true);
						}
						UMeng_OnEvent(EVENT_ID_CLICK_CLOSE_LIGHT);
						packetoff.lightClose(DataExchange.longToEightByte(dev.mac), recvListener);
						packetoff.setImportance(BasicPacket.ImportHigh);
						autoBinder.addPacketToSend(packetoff);

					
				}else{
	
						LightControlPacket packeton = new LightControlPacket(conip,conport);
						if(connectStatus == PublicDefine.ConnectRemote){
							packeton.setPacketRemote(true);
						}
						UMeng_OnEvent(EVENT_ID_CLICK_OPEN_LIGHT);
						packeton.lightOpen(DataExchange.longToEightByte(dev.mac), recvListener);
						packeton.setImportance(BasicPacket.ImportHigh);
						autoBinder.addPacketToSend(packeton);

				}
				
			}});
	}
	

	@Override
	protected void onDestroy() {
		rgblight.recycleBit();
		this.userUnbindService();
		super.onDestroy();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){

			@Override
			public void run() {
					LightControlPacket packet = new LightControlPacket(conip,conport);
					if(connectStatus == PublicDefine.ConnectRemote){
						packet.setPacketRemote(true);
					}
					packet.lightCheck(DataExchange.longToEightByte(dev.mac),recvListener);
					packet.setImportance(BasicPacket.ImportNormal);
					
					if(null != autoBinder){
						autoBinder.addPacketToSend(packet);
					}
			}
			
		};
		timer.schedule(timetask, 500,1000);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		timer.cancel();
	}

	showDarkThread showdrak ;
	hideDarkThread hidedrak ;
	
	/**设置背景
	 * @param a
	 */
	private void setBackground(boolean a){
		
		if(a!=this.rgbLightStatus){
			if(a){
				hidedrak = new hideDarkThread();
				hidedrak.start();
			}else{
				showdrak = new showDarkThread();
				showdrak.start();
			}
		}
		
		
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == freshBackground){
				float tmp = msg.getData().getFloat("float");
				linearbackground2.setAlpha(tmp);
			}else if(msg.what == freshUiPart){
				if(couldFreshLux){
					rgblight.setLightStatus(rgbLightStatus);
					rgblight.setBarFresh();
					if(rgbLightStatus){
						randomLayout.setAlpha(1.0f);
						if(sleeptime == 0){
							sleeplayout.setVisibility(View.INVISIBLE);
						}else{
							sleeplayout.setVisibility(View.VISIBLE);
							int min =0;
							int sec =0;
							min = sleeptime/60;
							sec = sleeptime%60;
							if(min<10){
								if(sec<10){
									sleeptimeshow.setText("0"+String.valueOf(min)+":"+"0"+String.valueOf(sec));
								}else{
									sleeptimeshow.setText("0"+String.valueOf(min)+":"+String.valueOf(sec));
								}
							}else{
								if(sec<10){
									sleeptimeshow.setText(String.valueOf(min)+":"+"0"+String.valueOf(sec));
								}else{
									sleeptimeshow.setText(String.valueOf(min)+":"+String.valueOf(sec));
								}
							}
							
						}
					}else{
						randomLayout.setAlpha(0.2f);
						sleeplayout.setVisibility(View.INVISIBLE);
					}
				}
				randombtn.setOnOff(randomStatus);
				
			}
		}
		
	};
	
	public int LuxToColor(int a){
		int tmp = 0;
		tmp = (int)((float)a*(float)300/(float)(rgblight.barMoveAngle));
		return tmp;
	}
	
	public int ColorToLux(int a){
		int tmp = 0;
		tmp = (int)((float)a*(float)(rgblight.barMoveAngle)/(float)300);
		return tmp;
	}
	
	public int WhiteToLux(int a){
		int tmp = 0;
		tmp = (int)((float)255*(float)a/(float)300);
		return tmp;
	}
	
	public class showDarkThread extends Thread{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			int darkRunCount = 0;
			float alpha = 0;
			while(darkRunCount<100){
				//linearbackground2.setAlpha(alpha);
				alpha=alpha+0.01f;
				
				Message msg = new Message();
				msg.what = freshBackground;
				Bundle bundle = new Bundle();
				bundle.putFloat("float", alpha);
				msg.setData(bundle);
				handler.sendMessage(msg);
				
				darkRunCount++;
				try {
					this.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public class hideDarkThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			int hideRunCount = 0;
			float alpha = 1.0f;
			while(hideRunCount<100){
				//linearbackground2.setAlpha(alpha);
				hideRunCount++;
				alpha = alpha- 0.01f;
				Message msg = new Message();
				msg.what = freshBackground;
				Bundle bundle = new Bundle();
				bundle.putFloat("float", alpha);
				msg.setData(bundle);
				handler.sendMessage(msg);
				try {
					this.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
