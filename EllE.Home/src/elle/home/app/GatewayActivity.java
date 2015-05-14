package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.util.LongSparseArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;

import elle.home.database.DataBaseHelper;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.GateWayControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;
import elle.home.utils.ViewHolder;

public class GatewayActivity extends BaseActivity {

	public String TAG = "PlugActivity";
	
	boolean isFresh;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	//设备的mac地址
	private long devmac;
	private OneDev dev;
	public int connectStatus;
	
	//当前查询的地点
	private String locatname;
	//如果增加，在主界面显示的序列号
	private int shownum;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	private RippleBackground foundDevice;
	private ListView mlistView;
	private Button mBtnAllowIn;
	private boolean isAllowin;
	
	private LongSparseArray<OneDev> devs = new LongSparseArray<OneDev>(5);
	
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
			if(null != packetcheck){
				byte[] datas = packetcheck.xdata;
				int start = 2;
				if(datas[0] == 1){
					start = 3;
				}
				
				int size = datas[1];
				ShowInfo.printLogW(size + "---data__________>" + DataExchange.dbBytesToString(datas));

				for(int i = 0; i<size; i++){
					int index = start + ( i * 4 );
					long mac = DataExchange.eightByteToLong((byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, datas[index], datas[index+1]);
					
					OneDev oneDev = devs.get(mac);
					if(null == oneDev){
						oneDev = new OneDev();
						oneDev.mac = mac;
						oneDev.type = datas[index+2];
						oneDev.ver = datas[index+3];
						oneDev.remoteip = dev.remoteip;
						oneDev.remoteport = dev.remoteport;
						oneDev.devname = getDeviceName(oneDev.type) + mac;
						oneDev.setAdded(true);
						
						devs.put(mac, oneDev);
						
						ShowInfo.printLogW(oneDev.type+"__________>"+oneDev.ver + "____mac=____" + oneDev.mac);
					}
					
					if(mac == oneDev.mac){
						checkGateWay(oneDev, getMacListener);
					}
				}
			}else{
				ShowInfo.printLogW("_______packetcheck == null________");
			}
		}
	};
	
	OnRecvListener getMacListener = new OnRecvListener(){

		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			if(null == packetcheck){
				ShowInfo.printLogW("---xdata___getmac_____null__>");
				return;
			}
			
			ShowInfo.printLogW(DataExchange.dbBytesToString(DataExchange.longToEightByte(packetcheck.mac)) + "---xdata___getmac_______>" + packetcheck.devcode);
			
			int size = devs.size();
			for (int i = 0; i < size; i++) {
				OneDev oneDev = devs.valueAt(i);
				byte[] bytes = DataExchange.longToEightByte(oneDev.mac);
				byte[] newBytes = DataExchange.longToEightByte(packetcheck.mac);
				
				if(bytes[6] == newBytes[6] && bytes[7] == newBytes[7]){
					oneDev.mac = packetcheck.mac;
					oneDev.remoteip = packetcheck.ip;
					oneDev.remoteport = packetcheck.port;
					if(isDevAdded(packetcheck.mac)){
						oneDev.setChecked(true);
						oneDev.setAdded(true);
					}else{
						oneDev.setAdded(false);
					}
					
					ShowInfo.printLogW("---xdata___getmac_____name__>" + oneDev.devname);
					
					break;
				}
			}
			
			GatewayActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					notifyListChanged();
				}
			});
			
		}
	};
	
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_gateway);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		locatname = intent.getStringExtra("locatname");
		shownum = intent.getIntExtra("shownum", 300);
		
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		
		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		dev = new OneDev();
		dev.getFromDatabase(devmac, this);
		dev.type = PublicDefine.TypeGateWay;
		dev.ver = PublicDefine.GateWayOrgin;
		
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
		
		ShowInfo.printLogW(TAG,"____plug ip:"+this.conip.toString()+" port:"+this.conport);
		ShowInfo.printLogW(TAG,"____devmac"+devmac+" mac:"+dev.mac+" name:"+dev.devname);
		
		initViews();
		this.userBindService();
	}

	protected boolean isDevAdded(long mac) {
		DataBaseHelper dbhelper = new DataBaseHelper(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] checkParams = new String[1];
		checkParams[0] = Long.toString(mac);
		String checkSql = "select * from devices where mac = ?";
		Cursor cursor = db.rawQuery(checkSql, checkParams);
		while(cursor.moveToNext()){
			db.close();
			return true;
		}
		return false;
	}

	protected String getDeviceName(byte type) {
		String name = "Dev";
		
		switch (type) {
		case PublicDefine.TypeController:
			name = getResources().getString(R.string.type_control_ways);
			break;

		default:
			break;
		}
		
		return name;
	}

	private void initViews() {
		mBtnAllowIn = (Button) findViewById(R.id.btn_allow_in);
		foundDevice=(RippleBackground)findViewById(R.id.content_ripple);
		foundDevice.startRippleAnimation();
		
		mlistView = (ListView) findViewById(R.id.lists);
		mlistView.setAdapter(baseAdapter);
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowInfo.printLogW("_______onItemClick_______");
				
				OneDev oneDev = devs.get(position);
				if(oneDev.isAdded()){
					if(oneDev.isChecked()){
						Intent cvin = new Intent(getApplicationContext(), ControllersActivity.class);
						cvin.putExtra("mac", oneDev.mac);
						cvin.putExtra("devname", oneDev.devname);
						cvin.putExtra("connect", oneDev.getConnectStatus());
						startActivity(cvin);
					}else{
						ShowToast.show(getApplicationContext(), "无法添加此设备！");
					}
				}else{
					ShowToast.show(getApplicationContext(), "添加此设备！");
				}
						
			}
		});
	}

	private void notifyListChanged() {
		if(devs.size() > 0){
			foundDevice.stopRippleAnimation();
			foundDevice.setVisibility(View.GONE);
			mlistView.setVisibility(View.VISIBLE);
		}
		
		baseAdapter.notifyDataSetChanged();
	}
	
	private boolean isAllDevAdded() {
		int count = 0;
		
		for (int i = 0; i < devs.size(); i++) {
			OneDev oneDev = devs.valueAt(i);
			if(!oneDev.isAdded()){
				count++;
				break;
			}
		}
		
		return count>0?false:true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){
			@Override
			public void run() {
				checkGateWay(dev, recvListener);
			}
		};
		timer.schedule(timetask, 500, 1500);
	}
	
	private void checkGateWay(OneDev oneDev, OnRecvListener listener) {
		GateWayControlPacket packet = getPacket();
		
		packet.checkDev(oneDev.type, oneDev.ver, DataExchange.longToEightByte(oneDev.mac), listener);
		
		if(autoBinder!=null){
			autoBinder.addPacketToSend(packet);
//			if(listener == getMacListener){
//				ShowInfo.printLogW("xdata___check_______>" + DataExchange.dbBytesToString(packet.data));
//			}
		}
	}

	private GateWayControlPacket getPacket() {
		GateWayControlPacket packet = new GateWayControlPacket(conip, conport);
		if (connectStatus == PublicDefine.ConnectRemote) {
			packet.setPacketRemote(true);
		}
		packet.setImportance(BasicPacket.ImportNormal);
		
		return packet;
	}

	@Override
	protected void onStop() {
		banIn();
		timer.cancel();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		this.userUnbindService();
		super.onDestroy();
	}
	
	public void doBackClick(View v){
		vibrator.vibrate(vibarator_time);
		finish();
	}
	
	public void doSelectedAll(View v){
		for (int i = 0; i < devs.size(); i++) {
			OneDev oneDev = devs.valueAt(i);
			oneDev.setChecked(true);
		}
		
		baseAdapter.notifyDataSetChanged();
	}
	
	public void doAdd(View v){
		for (int i = 0; i < devs.size(); i++) {
			OneDev oneDev = devs.valueAt(i);
			if(oneDev.isChecked()){
				GateWayControlPacket packet = getPacket();
				
				packet.allowDevIn(DataExchange.longToEightByte(dev.mac), recvListener, oneDev);
				autoBinder.addPacketToSend(packet);
				ShowInfo.printLogW("xdata___add_______>" + DataExchange.dbBytesToString(packet.data));
				
				oneDev.locateNmae = locatname;
				oneDev.shownum = shownum;
				oneDev.setChecked(false);
				oneDev.setAdded(true);
				
				if(0 == oneDev.addToDatabase(getApplicationContext())){
					shownum++;
				}else{
					ShowToast.show(getApplicationContext(), oneDev.devname + "已存在！");
				}
			}
		}

		baseAdapter.notifyDataSetChanged();
	}
	
	private void banIn() {
		if(isAllowin){
			GateWayControlPacket packet = getPacket();
			packet.banIn(DataExchange.longToEightByte(dev.mac), recvListener);
			autoBinder.addPacketToSend(packet);
			
			foundDevice.stopRippleAnimation();
			mBtnAllowIn.setText(R.string.allow_in);
		}
		
		isAllowin = false;
	}

	public void doClick(View v){
		GateWayControlPacket packet = getPacket();
		vibrator.vibrate(vibarator_time);
		
		switch (v.getId()) {
		case R.id.btn_allow_in:
			if(isAllowin){
				packet.banIn(DataExchange.longToEightByte(dev.mac), recvListener);
				if(devs.size() > 0){
					foundDevice.stopRippleAnimation();
				}
				mBtnAllowIn.setText(R.string.allow_in);
			}else{
				packet.allowIn(DataExchange.longToEightByte(dev.mac), recvListener);
				mBtnAllowIn.setText(R.string.ban_in);
				if(devs.size()<1){
					foundDevice.startRippleAnimation();
				}
			}
			isAllowin = !isAllowin;
			break;
		default:
			break;
		}
		
		autoBinder.addPacketToSend(packet);
		ShowInfo.printLogW("xdata___send_______>" + DataExchange.dbBytesToString(packet.data));
	}
	
	BaseAdapter baseAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = LayoutInflater.from(GatewayActivity.this).inflate(R.layout.item_geteway_device_list, null);
			}
			
			final OneDev oneDev = devs.valueAt(position);
			
			TextView textView = ViewHolder.get(convertView, R.id.itemtext);
			textView.setText(oneDev.devname);
			
			CheckBox checkBox = ViewHolder.get(convertView, R.id.checkbox);
			checkBox.setEnabled(!oneDev.isAdded());
			checkBox.setChecked(oneDev.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					oneDev.setChecked(isChecked);
				}
			});
			
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			if(position >= 0 && position < devs.size()){
				return devs.valueAt(position);
			}else{
				return null;
			}
		}
		
		@Override
		public int getCount() {
			return devs.size();
		}
	};
}
