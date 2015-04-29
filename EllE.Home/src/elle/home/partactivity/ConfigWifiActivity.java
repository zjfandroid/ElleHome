package elle.home.partactivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import elle.home.app.AutoService;
import elle.home.app.R;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowToast;

/**
 * 设备信息界面
 * 
 * @author jason
 *
 */
public class ConfigWifiActivity extends BaseActivity {

	public final String TAG = "DevInfoActivity";

	private Context mContext;
	// 返回按钮
	private ImageButton backbtn;
	//wifi信息
	private EditText ssidText;
	private EditText passwordText;
	
	private OneDev onedev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_config_wifi);
		mContext = this;

		backbtn = (ImageButton) this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					PublicDefine.vibratorNormal(mContext);
				} else if (event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_CANCEL) {
					finish();
				}
				return true;
			}
		});

		initViews();
		userBindService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		userUnbindService();
	}

	private void initViews() {
		onedev = (OneDev) getIntent().getSerializableExtra("dev");
		TextView textView = (TextView) findViewById(R.id.title_place_info);
		textView.setText(onedev.locateNmae);
		textView = (TextView) findViewById(R.id.title_name_info);
		textView.setText(onedev.devname);
		textView = (TextView) findViewById(R.id.title_mac_info);
		textView.setText(DataExchange.dbBytesToString(DataExchange
				.longToEightByte(onedev.mac)));
		
		ssidText = (EditText)this.findViewById(R.id.wifi_ssid_et);
		passwordText = (EditText)this.findViewById(R.id.wifi_password_et);
	}

	/*
	 * 点击确定按钮
	 */
	public void onDoneClick(View v) {
		if (ssidText.length() > 0) {
			if (passwordText.length() > 0) {

					// 在这里组包发送配置包
					BasicPacket packet;
					try {
						packet = new BasicPacket(
								InetAddress.getByName("255.255.255.255"), 5880);
						packet.setImportance(BasicPacket.ImportHigh);
						packet.setPacketRemote(false);
						packet.setListener(configRecvListener);
						packet.packWifiConfigSet(onedev, ssidText.getText()
								.toString(), passwordText.getText().toString());
						autoBinder.addPacketToSend(packet);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
			} else {
				ShowToast.show(mContext, R.string.enter_wifi_password);
			}
		} else {
			ShowToast.show(mContext, R.string.enter_wifi_name);
		}
	}
	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Toast.makeText(mcontext, getResources().getString(R.string.tips_add_succeed), Toast.LENGTH_SHORT).show();
					Message msg = new Message();
					msg.what = 2;
					Bundle bundle = new Bundle();
					bundle.putString("text", getResources().getString(R.string.tips_config_link_succeed));
					msg.setData(bundle);
					handler.sendMessage(msg);
					Log.d(TAG,"添加成功--->"+"mac:"+onedev.mac+" type:"+onedev.type+" ver:"+onedev.ver+" locatname:"+onedev.locateNmae+" shownum:"+onedev.shownum);
					finish();
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
			// TODO Auto-generated method stub
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
					
//					strSsid = new String(ssidtmp);
//					strPassword = new String(passwordtmp);
					
//					localip = packetcheck.ip;
//					localport = packetcheck.port;
//					
//					Log.d(TAG,"ip:"+localip.toString()+"port:"+localport);
					if(ssidlen>0)
					{
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
//						isUserNeedConfig = false;
					}
				}
				
			}else{
				//Log.d(TAG,"wifi配置超时。。。。。");
			}
		}
	};
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 1:
					//需要异步
//					isConfigWiFi.setChecked(false);
//					ssidText.setText(strSsid);
					//passwordText.setHint("WiFi已配置，如需重新配置勾选即可");
				break;
			case 2:
				
				Toast.makeText(mContext, msg.getData().getString("text"), Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
}
