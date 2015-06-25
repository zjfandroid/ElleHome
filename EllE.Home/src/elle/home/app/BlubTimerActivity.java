package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.protocol.PacketTaskFun;
import elle.home.protocol.TaskFun;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;
import elle.home.utils.ViewHolder;

public class BlubTimerActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener{

	public String TAG = "PlugActivity";
	//
	boolean plugStatus;
	boolean isSetOpen;
	boolean isSetClose;
	
	boolean isFresh;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	//设备的mac地址
	private long devmac;
	private OneDev dev;
	public int connectStatus;
	
	//设备需要通信的ip
	public InetAddress conip;
	public int conport;
	
	private ImageButton leftbtn;
	private TextView titletext;
	
	private SwipeMenuListView mListView;
	private List<TaskFun> lists = new ArrayList<TaskFun>(5);
	
	boolean setOpenOrClose;
	
	private int mEmptyIndex = -1;
	private int mItemClickIndex = -1;
	
	private AutoService.AutoBinder autoBinder;
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			autoBinder = (AutoService.AutoBinder) service;
			getCheckList();
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
	
		OnRecvListener taskListener = new OnRecvListener(){

			@Override
			public synchronized void OnRecvData(PacketCheck packetcheck) {
				if(null != packetcheck){
					byte[] datas = packetcheck.xdata;
					if(datas[0]<101 || datas[0]>124){
						setTimeZone();
						return;
					}
					
					lists.clear();
					mEmptyIndex = -1;
					ShowInfo.printLogW(datas[0] + "_______taskListener xdata == _______" + DataExchange.byteArrayToHexString(datas));
					int index = 0;
					for(int i = 5;i+4<packetcheck.xdatalen;i+=5){
						byte day = datas[i];
						byte hour = datas[i+1];
						byte min = datas[i+2];
						byte second = datas[i+3];
						byte fun = datas[i+4];
						ShowInfo.printLogW(day + ":" + hour + "--" + min + "--" + second + "---fun_____== " + fun);
						
						if(day!=0 && hour !=-1){
							TaskFun taskFun = new TaskFun((byte)index, hour, min, second, fun, day); 
							lists.add(taskFun);
						}else if(mEmptyIndex == -1){
							mEmptyIndex = index;
						}
						index++;
					}
				}else{
					ShowInfo.printLogW("_______taskListener packetcheck == null_______");
					lists.clear();
					mListView.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							getCheckList();
						}
					}, 600);
				}
				
				notifyListChanged();
			}

			private void notifyListChanged() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mBaseAdapter.notifyDataSetChanged();
					}
				});
			}
			
		};
		
		OnRecvListener editListener = new OnRecvListener(){

			@Override
			public void OnRecvData(PacketCheck packetcheck) {
//				if(null != packetcheck){
//					byte[] datas = packetcheck.xdata;
//					ShowInfo.printLogW("_______editListener xdata == _______" + DataExchange.byteArrayToHexString(datas));
//					for(int i = 0;i+5<packetcheck.xdatalen;i+=6){
//						int index = datas[i];
//						int day = datas[i+1];
//						int hour = datas[i+2];
//						int min = datas[i+3];
//						int second = datas[i+4];
//						int fun = datas[i+5];
//						ShowInfo.printLogW(index + "---"+ day + ":" + hour + "--" + min + "--" + second + "---fun_____== " + fun);
//					}
//					
//				}else{
//					ShowInfo.printLogW("_______editListener packetcheck == null_______");
//				}
				getCheckList();
			}
			
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_blub_timer);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		Log.d(TAG,"传输状态:"+this.connectStatus);
		titletext = (TextView)this.findViewById(R.id.title_bar_text);
		leftbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		plugStatus = true;
		
		isFresh = true;
		
		vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		
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
		
		initViews();
	}

	protected void setTimeZone() {
		PacketTaskFun mFun = getTaskFunPacket();
		mFun.setTimeZone(DataExchange.longToEightByte(dev.mac), dev.type, dev.ver, new OnRecvListener() {
			
			@Override
			public void OnRecvData(PacketCheck packetcheck) {
				getCheckList();
				ShowInfo.printLogW("______setTimeZone_packetcheck_______");
			}
		});
		autoBinder.addPacketToSend(mFun);
		ShowInfo.printLogW("______setTimeZone________");
	}

	private PacketTaskFun getTaskFunPacket() {
		PacketTaskFun mFun = new PacketTaskFun(conip, conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			mFun.setPacketRemote(true);
		}
		mFun.setImportance(BasicPacket.ImportHigh);
		
		return mFun;
	}

	private void getCheckList() {
		PacketTaskFun mFun = getTaskFunPacket();
		
		mFun.getCheckList(DataExchange.longToEightByte(dev.mac), dev.type, dev.ver, taskListener);
		autoBinder.addPacketToSend(mFun);
	}
	
	private void editOneTask(TaskFun task, OnRecvListener editListener) {
		PacketTaskFun mFun = getTaskFunPacket();
		
		mFun.editOneTask(DataExchange.longToEightByte(dev.mac), dev.type, dev.ver, editListener, task);
		autoBinder.addPacketToSend(mFun);
	}

	private void initViews() {
		findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTimePicker();
			}
		});
		
		mListView = (SwipeMenuListView) findViewById(R.id.task_list);
		mListView.setAdapter(mBaseAdapter);
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			
			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);
		
		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					TaskFun tmp = lists.get(position);
					TaskFun task = new TaskFun((byte)(tmp.num), (byte)0, (byte)0, (byte)0, (byte)0, (byte)0);
					editOneTask(task, editListener);
					break;
				}
				return false;
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showTimePicker();
				mItemClickIndex = position;
			}
		});
		
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	protected void showTimePicker() {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        timePickerDialog.setVibrate(true);
        timePickerDialog.show(getSupportFragmentManager(), "");
//        timePickerDialog.setCloseOnSingleTapMinute(false);
	}
	
	@Override
	protected void onDestroy() {
		this.userUnbindService();
		super.onDestroy();
	}
	
	@Override
	public void onTimeSet(RadialPickerLayout view, final byte day, final int hourOfDay, final int minute, final int tag) {
//		int offSet = DataExchange.getOffset(hourOfDay, minute);
		
		if(mItemClickIndex == -1){
			getCheckList();
			mListView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					int size = lists.size();
					if(size>=28){
						ShowToast.show(getApplicationContext(), "最多只能添加28组定时任务~");
						return;
					}
					
					byte fun = PublicDefine.FunPlugOff;
					if(tag == TimePickerDialog.TAG_LEFT){
						fun = PublicDefine.FunPlugOn;
					}
					
					TaskFun task = new TaskFun((byte)mEmptyIndex, (byte)hourOfDay, (byte)minute, (byte)0, fun, day);
					editOneTask(task, editListener);
				}
			}, 200);
		}else{
			TaskFun task = lists.get(mItemClickIndex);
			task.day = day;
			task.hour = (byte) hourOfDay;
			task.min = (byte) minute;
			
			byte fun = PublicDefine.FunPlugOff;
			if(tag == TimePickerDialog.TAG_LEFT){
				fun = PublicDefine.FunPlugOn;
			}
			task.fun = fun;
			
			editOneTask(task, editListener);
			mItemClickIndex = -1;
		}
	}

	BaseAdapter mBaseAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_time_task_list, null);
			}
			
			if(position<lists.size()){
				TaskFun mTaskFun = lists.get(position);
				int day = mTaskFun.day&0xff;
				
				TextView mTextView = ViewHolder.get(convertView, R.id.item_text);
				mTextView.setText(String.format("%02d",mTaskFun.hour) + ":" + String.format("%02d", mTaskFun.min));
				
				mTextView = ViewHolder.get(convertView, R.id.item_onoff);
				if(mTaskFun.fun == 0x01){
					mTextView.setText(R.string.on);
				}else{
					mTextView.setText(R.string.off);
				}
				
//			Switch mSwitch = ViewHolder.get(convertView, R.id.item_switch);
//			mSwitch.setVisibility(View.INVISIBLE);
				
				CheckBox mCheckBox = ViewHolder.get(convertView, R.id.box_mon);
				if(0 != (day&0x01)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_tus);
				if(0 != (day&0x02)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_wen);
				if(0 != (day&0x04)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_thr);
				if(0 != (day&0x08)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_fri);
				if(0 != (day&0x10)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_sat);
				if(0 != (day&0x20)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_sun);
				if(0 != (day&0x40)){
					mCheckBox.setChecked(true);
				}else{
					mCheckBox.setChecked(false);
				}
				
				mCheckBox = ViewHolder.get(convertView, R.id.box_cir);
				if(0 != (day&0x80)){
					mCheckBox.setChecked(true);
					mCheckBox.setVisibility(View.VISIBLE);
				}else{
					mCheckBox.setChecked(false);
					mCheckBox.setVisibility(View.GONE);
				}
			}
			
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}
		
		@Override
		public int getCount() {
			return lists.size();
		}
	};
}
