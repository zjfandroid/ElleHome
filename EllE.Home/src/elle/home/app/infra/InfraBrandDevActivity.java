package elle.home.app.infra;

import java.io.UnsupportedEncodingException;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

import com.daimajia.numberprogressbar.NumberProgressBar;

import elle.fun.infra.InfraThread;
import elle.fun.infra.OnDevListListener;
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

	private List<Item> mLists = new ArrayList<Item>();
	private byte type = 0;
	private String brand;
	private int idBrand = 0;
	private int index = 0;
	private byte[] mData;
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
		brand = intent.getStringExtra("brand");
		idBrand = intent.getIntExtra("id", 1);
		
		if(PublicDefine.TypeInfraAir == type){
			initDatas(InfraThread.TypeAir, new OnDevListListener() {
				
				@Override
				public void recvDevListListener(boolean isOk, int len, ArrayList<String> datalist) {
					for(int i=0;i<datalist.size();i++){
						Item item = new Item();
						item.sourceData = DataExchange.dbStringToBytes(datalist.get(i));
						byte[] bytes = InfraNative.getAirComand(item.sourceData, 
								InfraNative.AIR_TEMP_19, InfraNative.AIR_FLOW_RATE_HIGH, InfraNative.AIR_FLOW_MANUAL_MID, 
								InfraNative.AIR_FLOW_AUTO_ON, InfraNative.AIR_ONOFF_ON, InfraNative.AIR_KEY_AUTO_FLOW, InfraNative.AIR_MODEL_COLD);
						Log.d("xxxd","ss______"+i+":"+DataExchange.dbBytesToString(bytes));
						item.command = bytes;
						mLists.add(item);
					}
				}
			});
		}else{
			initDatas(InfraThread.TypeTv, new OnDevListListener() {
				
				@Override
				public void recvDevListListener(boolean isOk, int len, ArrayList<String> datalist) {
					for(int i=0;i<datalist.size();i++){
						Item item = new Item();
						item.sourceData = DataExchange.dbStringToBytes(datalist.get(i));
						byte[] bytes = InfraNative.getTvCommand(item.sourceData, InfraNative.TvFunVolPlus);
						Log.d("xxxd","ss______"+i+":"+DataExchange.dbBytesToString(bytes));
						item.command = bytes;
						mLists.add(item);
					}
				}
			});
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

	private void initDatas( String type, OnDevListListener devListener) {
		InfraThread thread = new InfraThread();
		try {
			thread.startGetDevList(InfraThread.Company, type, new String(brand.getBytes(), "utf-8"),devListener);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
		aintent.putExtra("source", mData);
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
				
				Item mItem = mLists.get(index);
				mData = mItem.sourceData;
				infraControlPacket.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
					
					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						if(null != packetcheck){
							ShowInfo.printLogW("_________packetcheck___________" + DataExchange.dbBytesToString(packetcheck.data));
						}
					}
				}, mItem.command);
				
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
	
	class Item{
		byte[] sourceData;
		byte[] command;
	}
}
