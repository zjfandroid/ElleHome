package elle.home.app.infra;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

import com.daimajia.numberprogressbar.NumberProgressBar;

import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.InfraControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.homegenius.infrajni.InfraNative;

public class InfraBrandDevActivity extends Activity {

	private List<byte[]> mLists = new ArrayList<byte[]>();
	private byte type = 0;
	private int idBrand = 0;
	private int index = 0;
	private long devmac;
	private int connectStatus;
	private InetAddress conip;
	private int conport;
	private OneDev dev;
	
	private NumberProgressBar mProgressBar;
	private ImageButton mAddBtn;
	
	private AutoService.AutoBinder autoBinder;
	
	private Timer mTimer;
	private AnimationDrawable animationDrawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infra_dev);
		initDatas();
		initViews();
		userBindService();
	}
	
	@Override
	protected void onDestroy() {
		userUnbindService();
		super.onDestroy();
	}

	private void initDatas() {
		Intent intent = this.getIntent();
		type = intent.getByteExtra("type", PublicDefine.TypeInfraAir);
		idBrand = intent.getIntExtra("id", 1);
		
		if(PublicDefine.TypeInfraAir == type){
			initAirDatas();
		}else{
			initTVDatas();
		}
		
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);

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
	}

	private void initTVDatas() {
		ShowInfo.printLogW("__________getTvBrandLenById_______" + idBrand);
		int len = InfraNative.getTvBrandLenById(idBrand);
		ShowInfo.printLogW(len + "______initDatas__id________" + idBrand);
		
		for (int i = 0; i < len; i++) {
			byte[] bytes = InfraNative.getTvCommand(idBrand, i+1, InfraNative.TvFunVolPlus);
			mLists.add(bytes);
		}			
	}

	private void initAirDatas() {
		int len = InfraNative.getAirBrandLenById(idBrand);
		ShowInfo.printLogW(len + "______initDatas__id________" + idBrand);
		
		for (int i = 0; i < len; i++) {
			byte[] bytes = InfraNative.getAirCommandByBrand(idBrand, i+1, InfraNative.AIR_TEMP_19, InfraNative.AIR_FLOW_RATE_AUTO, 
				InfraNative.AIR_FLOW_MANUAL_UP, InfraNative.AIR_FLOW_AUTO_OFF, 
				InfraNative.AIR_ONOFF_ON, InfraNative.AIR_KEY_ONOFF, InfraNative.AIR_MODEL_COLD);
			mLists.add(bytes);
		}		
	}

	private void initViews() {
		mProgressBar = (NumberProgressBar) findViewById(R.id.numberbar);
		mProgressBar.setMax(mLists.size());
		mProgressBar.setProgress(index);
		
		mAddBtn = (ImageButton) findViewById(R.id.btn_add);
		mAddBtn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if(MotionEvent.ACTION_DOWN == action){
					mAddBtn.setImageResource(R.anim.ctrl_connect);
					animationDrawable = (AnimationDrawable) mAddBtn.getDrawable();
					animationDrawable.start();
					startSendPacket();
				}else if(MotionEvent.ACTION_CANCEL == action || MotionEvent.ACTION_UP == action){
					animationDrawable.stop();
					mAddBtn.setImageResource(R.drawable.fingerprint);
					stopSendPacket();
				}
				return false;
			}
		});
	}

	protected void stopSendPacket() {
		stopTimer();
		
		Intent aintent = null;
		if(PublicDefine.TypeInfraAir == type){
			aintent = new Intent(this, InfraAirActivity.class);
		}else{
			aintent = new Intent(this, InfraTVControlActivity.class);
		}
		
		aintent.putExtra("mac", dev.mac);
		aintent.putExtra("devname", dev.devname);
		aintent.putExtra("connect", connectStatus);
		aintent.putExtra("idBrand", idBrand);
		aintent.putExtra("id", index);
		aintent.putExtra("isTest", true);
		startActivity(aintent);
		
	}

	private void stopTimer() {
		if(null != mTimer)
		{
			mTimer.purge();
			mTimer.cancel();
			mTimer = null;
		}
	}

	protected void startSendPacket() {
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				InfraControlPacket infraControlPacket  = new InfraControlPacket(conip, conport);
				infraControlPacket.setImportance(BasicPacket.ImportHigh);
				if(connectStatus == PublicDefine.ConnectRemote){
					infraControlPacket.setPacketRemote(true);
				}
				infraControlPacket.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
					
					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						if(null != packetcheck){
							ShowInfo.printLogW("_________packetcheck___________" + DataExchange.dbBytesToString(packetcheck.data));
						}
					}
				}, mLists.get(index));
				
				if(autoBinder!=null){
					autoBinder.addPacketToSend(infraControlPacket);
				}
				
				InfraBrandDevActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mProgressBar.setProgress(index);
					}
				});
				
				index++;
				int size = mLists.size();
				if(index >= size){
					index = 0;
				}
			}
		}, 100, 1500);
	}
	
	public void doExit(View v){
		stopTimer();
		finish();
	}
	
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
}
