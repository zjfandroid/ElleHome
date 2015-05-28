package elle.home.partactivity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.dialog.WifiListDialog;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.PublicResDefine;
import elle.home.utils.SaveDataPreferences;

public class AddSignalDevActivity extends BaseActivity {

	public String TAG = "AddSignalDevActivity";
	
	Context mcontext;
	
	OneDev onedev;
	Button addBtn;
	EditText et;
	
	//返回按钮
	ImageButton backbtn;
	//标题栏
	TextView titletext;
	//背景
	LinearLayout bk;
	
	ImageView addlogo;
	TextView info1;
	TextView info2;
	TextView info3;
	TextView info4;
	
	//是否需要配置wifi
	CheckBox isConfigWiFi;
	boolean isUserNeedConfig;
	
	//wifi信息
	EditText ssidText;
	EditText passwordText;
	String strSsid;
	String strPassword;
	
	public InetAddress localip;
	public int localport;
	
	//定时器，用来查询设备
	Timer xtimer;
	
	OnRecvListener configRecvListener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			if(packetcheck!=null){
				if(packetcheck.xdata[0]==0x04){
					//发射重启
					BasicPacket packet;
					try {
						
						packet = new BasicPacket(InetAddress.getByName("255.255.255.255"),5880);
						packet.setImportance(BasicPacket.ImportHigh);
						packet.setPacketRemote(false);
						packet.setListener(recvListener);
						packet.packWifiConfigRestart(onedev);
						autoBinder.addPacketToSend(packet);
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					
					//不需要配置wifi的话，就直接加入到数据库中
					onedev.devname = et.getText().toString();
					if(onedev.addToDatabase(mcontext) == 0){
						//Toast.makeText(mcontext, getResources().getString(R.string.tips_add_succeed), Toast.LENGTH_SHORT).show();
						Message msg = new Message();
						msg.what = 2;
						Bundle bundle = new Bundle();
						bundle.putString("text", getResources().getString(R.string.tips_add_succeed));
						msg.setData(bundle);
						handler.sendMessage(msg);
						Log.d(TAG,"添加成功--->"+"mac:"+onedev.mac+" type:"+onedev.type+" ver:"+onedev.ver+" locatname:"+onedev.locateNmae+" shownum:"+onedev.shownum);
						finish();
					}else{
						//Toast.makeText(mcontext, getResources().getString(R.string.tips_benamed), Toast.LENGTH_SHORT).show();
						Message msg = new Message();
						msg.what = 2;
						Bundle bundle = new Bundle();
						bundle.putString("text", getResources().getString(R.string.tips_benamed));
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				}
			}else{
				//Toast.makeText(mcontext, "WiFi配置失败", Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				msg.what = 2;
				Bundle bundle = new Bundle();
				bundle.putString("text", getResources().getString(R.string.link_config_failed));
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}
		
	};
	OnRecvListener recvListener = new OnRecvListener(){
		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			if(packetcheck!=null){
				//Log.d(TAG,"收到wifi配置的回应");
				
				if(packetcheck.xdata[0]==(byte)0x02)
				{
					int ssidlen = packetcheck.xdata[1]&0xff;
					int passwordlen = packetcheck.xdata[2+ssidlen]&0xff;
					byte[] ssidtmp = new byte[ssidlen];
					byte[] passwordtmp = new byte[passwordlen];
					System.arraycopy(packetcheck.xdata, 2, ssidtmp, 0, ssidlen);
					System.arraycopy(packetcheck.xdata,3+ssidlen,passwordtmp,0,passwordlen);
					
					strSsid = new String(ssidtmp);
					strPassword = new String(passwordtmp);
					
					localip = packetcheck.ip;
					localport = packetcheck.port;
					
					Log.d(TAG,"ip:"+localip.toString()+"port:"+localport);
					if(ssidlen>0)
					{
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
						isUserNeedConfig = false;
					}else{
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								showWifiListDialog();
							}
						});
					}
					
				}
				
			}else{
				//Log.d(TAG,"wifi配置超时。。。。。");
			}
		}

		private void showWifiListDialog() {
			final WifiListDialog mWifiListDialog = new WifiListDialog(mcontext);
			mWifiListDialog.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					strSsid = mWifiListDialog.getWifiSSID(position);
					ssidText.setText(strSsid);
					passwordText.setText(SaveDataPreferences.load(mcontext, strSsid, ""));
					mWifiListDialog.dissMiss();
				}
			});
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
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_signal_dev);
		mcontext = this;
		onedev = new OneDev();
		Intent intent = getIntent();  
		onedev.mac = intent.getLongExtra("mac",0);

		onedev.type = intent.getByteExtra("type",(byte) 0);
		onedev.ver = intent.getByteExtra("ver",(byte) 0);
		onedev.locateNmae = intent.getStringExtra("locatname");
		onedev.shownum = intent.getIntExtra("shownum", 301);
		Log.d(TAG,"mac:"+onedev.mac+" type:"+onedev.type+" ver:"+onedev.ver+" locatname:"+onedev.locateNmae+" shownum:"+onedev.shownum);
		
		isConfigWiFi = (CheckBox)this.findViewById(R.id.configWiFiCheck);
		isConfigWiFi.setChecked(true);
		isConfigWiFi.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					Log.d(TAG,"wifi需要进行配置");
					passwordText.setHint(getResources().getString(R.string.link_config_input_password));
				}
				else
				{
					Log.d(TAG,"wifi不需要进行配置");
					if(isUserNeedConfig==false)
					{
						//passwordText.setHint("WiFi已配置，如需重新配置勾选即可");
					}
				}
			}});
		isUserNeedConfig = true;
		
		ssidText = (EditText)this.findViewById(R.id.ssidEdit);
		passwordText = (EditText)this.findViewById(R.id.passwordEdit);
		
		addBtn = (Button)this.findViewById(R.id.addSignalDevBtn);
		addBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_null));
		et = (EditText)this.findViewById(R.id.addNameEdit);
		addBtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					addBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_press));
					break;
				case MotionEvent.ACTION_UP:
					addBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_null));
					if(et.getText().length()==0){
						Log.d(TAG,"输入名字啊~");
						Toast.makeText(mcontext, getResources().getString(R.string.tips_bename_first), Toast.LENGTH_SHORT).show();
					}else{
						if(isConfigWiFi.isChecked())
						{	//需要的话，先判断password和ssid的长度
							if(ssidText.length()>0){
								if(passwordText.length()>0){
									
									if(localip!=null){
										//在这里组包发送配置包
										BasicPacket packet;
										try {
											packet = new BasicPacket(InetAddress.getByName("255.255.255.255"),5880);
											packet.setImportance(BasicPacket.ImportHigh);
											packet.setPacketRemote(false);
											packet.setListener(configRecvListener);
											packet.packWifiConfigSet(onedev, ssidText.getText().toString(), passwordText.getText().toString());
											autoBinder.addPacketToSend(packet);
										} catch (UnknownHostException e) {
											e.printStackTrace();
										}
										
									}else{
										Toast.makeText(mcontext, "未检查到设备", Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(mcontext, "需要输入WiFi密码啦", Toast.LENGTH_SHORT).show();
								}
							}else{
								Toast.makeText(mcontext, "需要输入WiFi名称啦", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							//不需要配置wifi的话，就直接加入到数据库中
							onedev.devname = et.getText().toString();
							if(onedev.addToDatabase(mcontext) == 0){
								Toast.makeText(mcontext, getResources().getString(R.string.tips_add_succeed), Toast.LENGTH_SHORT).show();
								Log.d(TAG,"添加成功--->"+"mac:"+onedev.mac+" type:"+onedev.type+" ver:"+onedev.ver+" locatname:"+onedev.locateNmae+" shownum:"+onedev.shownum);
								finish();
							}else{
								Toast.makeText(mcontext, getResources().getString(R.string.tips_benamed), Toast.LENGTH_SHORT).show();
							}
						}
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					addBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_null));
					break;
				}
				return true;
			}
		});
		
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mcontext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		titletext = (TextView)this.findViewById(R.id.title_bar_text);
		titletext.setText(getResources().getString(R.string.add_add_string)+PublicResDefine.getStringByType(getResources(),onedev.type));
		bk = (LinearLayout)this.findViewById(R.id.add_dev_name_bk);
		
		addlogo = (ImageView)this.findViewById(R.id.addlogo);
		info1 = (TextView)this.findViewById(R.id.info1);
		info2 = (TextView)this.findViewById(R.id.info2);
		info3 = (TextView)this.findViewById(R.id.info3);
		info4 = (TextView)this.findViewById(R.id.info4);
		
		switch(onedev.type){
		case PublicDefine.TypeLight:
			addlogo.setBackground(this.getResources().getDrawable(R.drawable.add_logo_bulb));
			info1.setText(getResources().getString(R.string.link_config_light_1));
			info2.setText(getResources().getString(R.string.link_config_light_2));
			info3.setText(getResources().getString(R.string.link_config_light_3));
			info4.setText(getResources().getString(R.string.link_config_light_4));
			break;
		case PublicDefine.TypePlug:
			addlogo.setBackground(this.getResources().getDrawable(R.drawable.add_logo_plug));
			info1.setText(getResources().getString(R.string.link_config_plug_1));
			info2.setText(getResources().getString(R.string.link_config_plug_2));
			info3.setText(getResources().getString(R.string.link_config_plug_3));
			info4.setText(getResources().getString(R.string.link_config_plug_4));
			break;
		case PublicDefine.TypeInfra:
			addlogo.setBackground(this.getResources().getDrawable(R.drawable.add_logo_infra));
			info1.setText(getResources().getString(R.string.link_config_infra_1));
			info2.setText(getResources().getString(R.string.link_config_infra_2));
			info3.setText(getResources().getString(R.string.link_config_infra_3));
			info4.setText(getResources().getString(R.string.link_config_infra_4));
			break;
		case PublicDefine.TypeInfraAir:
			addlogo.setBackground(this.getResources().getDrawable(R.drawable.add_logo_infra));
			info1.setText(getResources().getString(R.string.link_config_air_1));
			info2.setText(getResources().getString(R.string.link_config_air_2));
			info3.setText(getResources().getString(R.string.link_config_air_3));
			info4.setText(getResources().getString(R.string.link_config_air_4));
			break;
		default:
			break;
		}
		
		userBindService();
		xtimer = new Timer();
		xtimer.schedule(new TimerTask(){

			@Override
			public void run() {
				try {
					BasicPacket packet = new BasicPacket(InetAddress.getByName("255.255.255.255"),5880);
					packet.setImportance(BasicPacket.ImportHigh);
					packet.packWiFiConfigCheck(onedev);
					packet.setPacketRemote(false);
					packet.setListener(recvListener);
					
					autoBinder.addPacketToSend(packet);
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}}, 500);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		userUnbindService();
		xtimer.cancel();
	}

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 1:
					//需要异步
					isConfigWiFi.setChecked(false);
					ssidText.setText(strSsid);
					passwordText.setText(SaveDataPreferences.load(mcontext, strSsid, ""));
					//passwordText.setHint("WiFi已配置，如需重新配置勾选即可");
				break;
			case 2:
				
				Toast.makeText(mcontext, msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
	
}
