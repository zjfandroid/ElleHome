package elle.home.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.ControllersPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;

/**
 * 多路控制器
 * @author jason
 *
 */
public class ControllersActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener{

	public String TAG = "PlugActivity";
	public static final String TIMEPICKER_TAG = "timepicker";
	
	boolean isFresh;
	
	private CheckBox[] mCheckBoxs = new CheckBox[4];
	
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
	public int index;
	
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
			if(packetcheck != null){
//				ShowInfo.printLogW( (packetcheck.xdata[0] & (byte)0x04) + "___222_packetcheck_long____" + (packetcheck.xdata[0] & (byte)0x08));
				ShowInfo.printLogW( Math.pow(2, 3) + "____packetcheck_____" + DataExchange.dbBytesToString(packetcheck.data));
				
				for (int i = 0; i < mCheckBoxs.length; i++) {
//					ShowInfo.printLogW( (packetcheck.xdata[0] & (byte)Math.pow(2, i)) + "___111_packetcheck_long____" + (int)Math.pow(2, i));
					if((packetcheck.xdata[0] & (byte)Math.pow(2, i)) == (int)Math.pow(2, i)){
						setChecked(i, true);
					}else{
						setChecked(i, false);
					}
					
					int len = 8*i;
					int tmp  = 0;
					SpannableStringBuilder ssb = new SpannableStringBuilder();
					tmp = DataExchange.fourByteToInt(packetcheck.xdata[1 + len], packetcheck.xdata[2 + len], packetcheck.xdata[3 + len], packetcheck.xdata[4 + len]);
					if(tmp!=0){	
						ssb.append(getResources().getString(R.string.plug_status_on))
						.append(":").append(DataExchange.getClockExchange(tmp)).append("\n\r");
					}
					
					tmp = DataExchange.fourByteToInt(packetcheck.xdata[5 + len], packetcheck.xdata[6 + len], packetcheck.xdata[7 + len], packetcheck.xdata[8 + len]);
					if(tmp!=0){	
						ssb.append(getResources().getString(R.string.plug_status_off))
						.append(":").append(DataExchange.getClockExchange(tmp)).append("\n\r");
					}
					
					setCheckBoxText(i, ssb.toString());
				}
			}
		}
	};
	
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_control_ways);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		this.connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
//		this.connectStatus = PublicDefine.ConnectRemote;
		Log.d(TAG,"传输状态:"+this.connectStatus);
		
		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

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
		
		this.userBindService();
		
		initViews();
	}

	protected void setCheckBoxText(final int i, final String string) {
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mCheckBoxs[i].setText(string);				
			}
		});
	}

	protected void setChecked(final int i, final boolean checked) {
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mCheckBoxs[i].setChecked(checked);		
			}
		});
	}

	private void initViews() {
		mCheckBoxs[0] = (CheckBox) findViewById(R.id.btn_1);
		mCheckBoxs[1] = (CheckBox) findViewById(R.id.btn_2);
		mCheckBoxs[2] = (CheckBox) findViewById(R.id.btn_3);
		mCheckBoxs[3] = (CheckBox) findViewById(R.id.btn_4);
		
		for (int i = 0; i < 4; i++) {
			mCheckBoxs[i].setTag(i);
			mCheckBoxs[i].setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				}
			});
			
			mCheckBoxs[i].setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					index = (Integer) v.getTag();
					showTimePicker();
					return true;
				}
			});
			
			mCheckBoxs[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(null == autoBinder){
						return;
					}
					
					ControllersPacket packet = getPacket();
					index = (Integer) v.getTag();	
					
					if(mCheckBoxs[index].isChecked()){
						packet.switchOn(index, DataExchange.longToEightByte(dev.mac), recvListener);
//						ShowInfo.printLogW("____packet switch____on_" + DataExchange.dbBytesToString(packet.data));
					}else{
						packet.switchOff(index, DataExchange.longToEightByte(dev.mac), recvListener);
//						ShowInfo.printLogW("____packet switch__off___" + DataExchange.dbBytesToString(packet.data));
					}
					
					mCheckBoxs[index].toggle();
					autoBinder.addPacketToSend(packet);
				}
			});
		}
		
	}

	protected void showTimePicker() {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
        timePickerDialog.setVibrate(true);
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
//        timePickerDialog.setCloseOnSingleTapMinute(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		TimerTask timetask = new TimerTask(){
			@Override
			public void run() {
				if(autoBinder!=null){
					ControllersPacket packet = getPacket();
					packet.check(DataExchange.longToEightByte(dev.mac), 3, recvListener);
					autoBinder.addPacketToSend(packet);
//					ShowInfo.printLogW("____packet send_____" + DataExchange.dbBytesToString(packetGetMac.data));
				}
			}};
		timer.schedule(timetask, 500, 1000);
	}

	@Override
	protected void onStop() {
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
	
	public void doClick(View v){
		SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(getResources().getString(R.string.manage_dev_tips_del_dev))
		.setCancelText(getResources().getString(R.string.manage_dev_tips_del_dev_no))
		.setConfirmText(getResources().getString(R.string.manage_dev_tips_del_dev_yes))
		.showCancelButton(true)
		.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				sDialog.dismiss();
			}
		})
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				sDialog.setConfirmText("OK")
				.showCancelButton(false)
				.setCancelClickListener(null)
				.setConfirmClickListener(null)
				.setTitleText(getResources().getString(R.string.delete_success))
				.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				
				Log.d(TAG,"确认删除！");
			}
		})
		.show();
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int tag) {
		if(autoBinder != null){
			int count = DataExchange.getOffset(hourOfDay, minute);
			ControllersPacket packet = getPacket();
		
			if(tag == TimePickerDialog.TAG_LEFT){
				packet.setTimeOn(index, DataExchange.longToEightByte(dev.mac), count, recvListener);
			}else{
				packet.setTimeOff(index, DataExchange.longToEightByte(dev.mac), count, recvListener);
			}
		
			autoBinder.addPacketToSend(packet);
//			ShowInfo.printLogW("____packet switch time_____" + DataExchange.dbBytesToString(packet.data));
		}
	}

	private ControllersPacket getPacket() {
		ControllersPacket packet = new ControllersPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		packet.setImportance(BasicPacket.ImportHigh);

		return packet;
	}
}
