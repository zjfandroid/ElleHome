package elle.home.app.infra;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.InfraControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;
import elle.home.utils.StringUtils;
import elle.homegenius.infrajni.InfraNative;

public class InfraTVControlActivity extends BaseActivity {

	public String TAG = "InfraAirActivity";

	private int idBrand = 0;
	private int id = 1;
	private byte[] source;
	
	//设备的mac地址
	private long devmac;
	private String devname;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	//当前点击的功能码
	private int funid;
	
	private Context mContext;
	//返回按钮
	private ImageButton backbtn;
	private EditText title;

	private boolean isTestMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_infra_tv_ctrl);
		this.userBindService();
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		devname = intent.getStringExtra("devname");
		source = intent.getByteArrayExtra("source");
		Log.d(TAG,"infra air mac:"+devmac+" devname:"+devname);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		dev = new OneDev();
		
		isTestMode = intent.getBooleanExtra("isTest", false);
//		dev.getFromDatabase(devmac, devname, this);
		
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
		
		title = (EditText)this.findViewById(R.id.title_bar_text);
		if(isTestMode){
			idBrand = intent.getIntExtra("idBrand", 1);
			id = intent.getIntExtra("id", 1);

			dev.function = DataExchange.dbBytesToString(source);
			
			title.setText(dev.devname + idBrand + id);
			View v = findViewById(R.id.btn_add);
			v.setVisibility(View.VISIBLE);
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					doAddDev();
					v.setVisibility(View.GONE);
				}
			});
		}else{
			title.setText(dev.devname);
			title.setEnabled(false);
			
			source = DataExchange.dbStringToBytes(dev.function);
		}
		
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mContext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					showExitDialog();
				}
				return true;
			}
		});
		
	}
	
	protected void showExitDialog() {
		if(isTestMode){
			SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			
			dialog.setTitleText(getResources().getString(R.string.sure_to_exit))
			.setCancelText(getResources().getString(R.string.exit))
			.setConfirmText(getResources().getString(R.string.add_add_string))
			.showCancelButton(true)
			.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
				@Override
				public void onClick(SweetAlertDialog sDialog) {
					sDialog.dismiss();
					finish();
				}
			})
			.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
				@Override
				public void onClick(SweetAlertDialog sDialog) {
					doAddDev();
					sDialog.dismiss();
				}
			})
			.show();
		}else{
			finish();
		}		
	}

	protected void doAddDev() {
		String name = title.getText().toString();
		if(StringUtils.isEmpty(name)){
			ShowToast.show(mContext, R.string.tips_bename_first);
		}else{
			dev.devname = name;
			dev.type = PublicDefine.TypeInfraAir;
			if(dev.addToDatabase(mContext)){
				title.setEnabled(false);
				isTestMode = false;
				ShowToast.show(mContext, R.string.tips_add_succeed);
			}else{
				ShowToast.show(mContext, R.string.tips_benamed);
			}
		}
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
		this.userUnbindService();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode){
			showExitDialog();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void doOffClick(View v){
		byte[] bytes = InfraNative.getTvCommand(source, InfraNative.TvFunPower);
		sendCommand(bytes);
	}
	
	private void sendCommand(byte[] bytes) {
		InfraControlPacket packet = getInfraPacket();
		packet.sendCommand(DataExchange.longToEightByte(dev.mac), new OnRecvListener() {
			
			@Override
			public void OnRecvData(PacketCheck packetcheck) {
				if(null != packetcheck){
					ShowInfo.printLogW(source + "_________packetcheck_AIR_ONOFF_OFF__________" + DataExchange.dbBytesToString(packetcheck.data));
				}
			}
		}, bytes);
		autoBinder.addPacketToSend(packet);
	}

	public void doModeClick(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunTVAV));
	}
	
	public void doClickOK(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunSure));
	}
	
	public void doClickLeft(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunLeft));
	}
	
	public void doClickRight(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunRight));
	}
	
	public void doClickUp(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunUp));
	}
	public void doClickDown(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunDown));
	}
	public void doClickBack(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunBack));
	}
	public void doClickMinus(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFunMute));
	}
	public void doClickZero(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun0));
	}
	public void doClickOne(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun1));
	}
	public void doClickTwo(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun2));
	}
	public void doClickThree(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun3));
	}
	public void doClickFour(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun4));
	}
	public void doClickFive(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun5));
	}
	public void doClickSix(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun6));
	}
	public void doClickSeven(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun7));
	}
	public void doClickEight(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun8));
	}
	public void doClickNine(View v){
		sendCommand(InfraNative.getTvCommand(source, InfraNative.TvFun9));
	}
}
