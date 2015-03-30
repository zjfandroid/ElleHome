package elle.home.partactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import elle.home.app.R;
import elle.home.hal.UdpAddDev;
import elle.home.hal.WiFiExchange;
import elle.home.hal.WifiAdmin;

public class ConfigDevWifiActivity extends BaseActivity {
	
	public String TAG = "ConfigDevWifiActivity";
	
	String orginssid;
	String nowssid;
	String password;
	//标识当前连接的是路由还是设备
	boolean isConRouter;

	Context context;
	
	//ssid编辑框
	EditText ssidEt;
	//password编辑框
	EditText passwordEt;
	//确认配置
	Button configbtn;
	//wifi连接状态提示
	TextView statusTv;
	String statusStr;
	ImageButton backbtn;
	//配置设备wifi的线程
	UdpAddDev udpadddev;
	
	boolean isConfiged;
	boolean isConfigOk;
	boolean isStartConfig;
	
	Timer timer;
	Timer xstimer;
	
	WiFiExchange wifi;
	WifiAdmin wifiAdmin;
	ArrayList<HashMap<String,String>> wifiSsidList;
	WifiListAdapter adapter;
	ListView ssidView;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:				//切换wifi成功
				Toast.makeText(context, getResources().getString(R.string.tips_switch_wifi_succeed), Toast.LENGTH_SHORT).show();
				break;
			case 1:
				freshStatus();
				freshWifi();
				break;
			case 2:				//切换wifi成功，1.5s后关闭
				Toast.makeText(context, getResources().getString(R.string.tips_link_config_succeed), Toast.LENGTH_SHORT).show();
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){
					@Override
					public void run() {
						finish();
					}}, 1500);
				
				break;
			case 3:				//连接错误
				Toast.makeText(context, getResources().getString(R.string.tips_link_config_error), Toast.LENGTH_SHORT).show();
				finish();
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		isConfiged = false;
		isConfigOk = false;
		isStartConfig = false;
		if(this.getIntent().getStringExtra("type").equalsIgnoreCase("dev")){
			isConRouter = false;
		}else{
			isConRouter = true;
		}
		wifiAdmin = new WifiAdmin(this);
		wifiSsidList = new ArrayList<HashMap<String,String>>();
		this.setContentView(R.layout.activity_config_dev);
		
		Intent intent = this.getIntent();
		this.orginssid = intent.getStringExtra("orginssid");
		this.nowssid = intent.getStringExtra("nowssid");
		wifi = new WiFiExchange(this); 
		
		Log.d(TAG,"orginssid:"+orginssid+" nowssid:"+nowssid);
		
		this.statusStr = getResources().getString(R.string.tips_connected_to)+this.orginssid;
		ssidView = (ListView)this.findViewById(R.id.ssidlist);
		adapter = new WifiListAdapter();
		
		ssidEt = (EditText)this.findViewById(R.id.ssidEt);
		ssidEt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG,"点击了wifi名称");
			}
		});
		
		passwordEt = (EditText)this.findViewById(R.id.passwordEt);
		configbtn = (Button)this.findViewById(R.id.configbtn);
		statusTv = (TextView)this.findViewById(R.id.statusTv);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					
					break;
				case MotionEvent.ACTION_UP:
					finish();
					break;
				}
				return true;
			}
		});
		this.statusTv.setText("选择您的wifi网络");
		
		configbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isStartConfig == false){
					
					if(ssidEt.getText().toString().length()<1){
						Toast.makeText(context, "wifi名称不符合啦~", Toast.LENGTH_SHORT).show();
						return ;
					}
					orginssid = ssidEt.getText().toString();
					if(passwordEt.getText().toString().length()<1){
						Toast.makeText(context, "wifi密码不符合啦~", Toast.LENGTH_SHORT).show();
						return ;
					}
					password = passwordEt.getText().toString();
					if(isConRouter){
						wifi.startConfig(nowssid,orginssid,password);
					}else{
						Log.d(TAG, "直接 连接设备并配置");
						udpadddev.sendConfig(orginssid, password);
						udpadddev.startCheck();
					}
					isStartConfig = true;

				}else{
					//停止配置
					isStartConfig = false;

					udpadddev.stopCheck();
					wifi.endConfig();
				}
			}
		});
		
		if(orginssid.equals("none")){
			ssidEt.setHint(getResources().getString(R.string.tips_input_ssid));
		}else{
			if(isConRouter){
				ssidEt.setText(this.orginssid);
			}else{
				ssidEt.setHint("选择您的WiFi网络");
				ssidEt.setInputType(InputType.TYPE_NULL);
			}
			
		}
		
		passwordEt.setHint(getResources().getString(R.string.tips_input_password));
		
		this.udpadddev = new UdpAddDev();
		this.udpadddev.setListener(new UdpAddDev.OnAddDev() {
			
			@Override
			public void onGetBack() {
				Log.d(TAG,"得到设备的配置回应");
				isConfiged = true;
			}
			
			@Override
			public void onConfigOk() {
				Log.d(TAG,"得到设备确认配置的回应");
				if(isConfiged){
					if(isConRouter){
						wifi.setConfigOk();
					}else{
						//配置wifi连回到路由器
						//finish();
						Log.d(TAG,"back to router:"+orginssid+" nowssid:"+nowssid);
						wifi.backToRouter(orginssid, nowssid);
					}
					
            	}
			}
		});
		wifi.setListener(new WiFiExchange.OnWifiChange() {
			
			@Override
			public void routerCloseNow() {
				statusStr = getResources().getString(R.string.tips_init_configing);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
			
			@Override
			public void changeToDevAp() {
				statusStr = getResources().getString(R.string.tips_connected_to_dev_configing);
				Log.d(TAG,"发送配置的ssid:"+orginssid+" 密码:"+password);
				udpadddev.sendConfig(orginssid, password);
				udpadddev.startCheck();
				
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
			
			@Override
			public void changeBackToRouter() {
				statusStr = getResources().getString(R.string.tips_config_link_succeed);
				Message msg = new Message();
				msg.what = 1;
				Message xmsg = new Message();
				xmsg.what = 2;
				handler.sendMessage(msg);
				handler.sendMessage(xmsg);
			}

			@Override
			public void onError(String str) {
				Message msg = new Message();
				msg.what = 3;
				handler.sendMessage(msg);
				Log.d(TAG,"wifi 配置连接异常:"+str);
				isStartConfig = false;
			}
		});
		
		ssidView.setAdapter(adapter);
		ssidView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position<wifiSsidList.size()){
					ssidEt.setText(wifiSsidList.get(position).get("ssid"));
				}
			}});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			}}, 100,1000);
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		timer.cancel();
	}

	@Override
	protected void onDestroy() {
		this.udpadddev.stopCheck();
		wifi.endConfig();
		this.setResult(1);
		super.onDestroy();
	}
	
	
	private void freshStatus(){
		if(isStartConfig){
			this.statusTv.setText(this.statusStr);
			configbtn.setText("终止");
		}else{
			this.statusTv.setText("选择您要使用的WiFi网络");
			configbtn.setText("配置");
		}
		
	}
	
	private void freshWifi(){
		wifiSsidList.clear();
		ArrayList<HashMap<String, String>> tmp = wifiAdmin.getWifiSsidList();
		for(int i=0;i<tmp.size();i++){
			wifiSsidList.add(tmp.get(i));
		}
		adapter.notifyDataSetChanged();
	}
	
	public class WifiListAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		
		public WifiListAdapter(){
			inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return null == wifiSsidList? 0:wifiSsidList.size();
		}

		@Override
		public Object getItem(int position) {
			if(wifiSsidList.size()>0){
				return wifiSsidList.get(position);
			}else{
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.wifi_ssid_list_item, null);
				holder.textview = (TextView)convertView.findViewById(R.id.ssid);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.textview.setText(wifiSsidList.get(position).get("ssid"));
			return convertView;
		}
		
		public class ViewHolder{
			TextView textview;
		}
		
	}
}
