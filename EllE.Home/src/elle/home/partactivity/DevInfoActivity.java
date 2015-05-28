package elle.home.partactivity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.app.AutoService;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

/**
 * 设备信息界面
 * @author jason
 *
 */
public class DevInfoActivity extends BaseActivity {

	public final String TAG = "DevInfoActivity";

	private Context mContext;
	//返回按钮
	private ImageButton backbtn;
	
	private ImageView addlogo;
	private TextView info1;
	private TextView info2;
	private TextView info3;
	private TextView info4;
	
	private OneDev dev;
	
	private boolean isZigbeeDev;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_dev_info);
		mContext = this;

		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mContext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		initViews();
		
		Intent bindIntent = new Intent(this,AutoService.class);
		bindService(bindIntent,connection,BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(connection);
	}

	@SuppressLint("NewApi")
	private void initViews() {
		dev = (OneDev) getIntent().getSerializableExtra("dev");
		TextView textView = (TextView) findViewById(R.id.title_place_info);
		textView.setText(dev.locateNmae);
		textView = (TextView) findViewById(R.id.title_name_info);
		textView.setText(dev.devname);
		textView = (TextView) findViewById(R.id.title_mac_info);
		textView.setText(DataExchange.dbBytesToString(DataExchange.longToEightByte(dev.mac)));
		
		addlogo = (ImageView)this.findViewById(R.id.addlogo);
		info1 = (TextView)this.findViewById(R.id.info1);
		info2 = (TextView)this.findViewById(R.id.info2);
		info3 = (TextView)this.findViewById(R.id.info3);
		info4 = (TextView)this.findViewById(R.id.info4);
		
		switch(dev.type){
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
		case PublicDefine.TypeController:
			isZigbeeDev = true;
			findViewById(R.id.btn_config).setVisibility(View.GONE);
			break;
		case PublicDefine.TypeGateWay:
			isZigbeeDev = true;
			break;
			
		default:
			break;
		}
	}

	/*
	 * 点击配置按钮
	 */
	public void onConfigClick(View v){
		UMeng_OnEvent(EVENT_ID_CLICK_CONFIG);
		Intent intent = new Intent(mContext, ConfigWifiActivity.class);
		intent.putExtra("dev", dev);
		mContext.startActivity(intent);
	}
	
	/*
	 * 点击复位按钮
	 */
	public void onResetClick(View v){
		UMeng_OnEvent(EVENT_ID_CLICK_RESET);
			SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			
			dialog.setTitleText(getResources().getString(R.string.sure_to_reset))
			.setCancelText(getResources().getString(R.string.manage_dev_tips_del_dev_no))
			.setConfirmText(getResources().getString(R.string.config_reset))
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
//					sDialog.setConfirmText("OK")
//					.showCancelButton(false)
//					.setCancelClickListener(null)
//					.setConfirmClickListener(null)
//					.setTitleText("复位成功")
//					.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
					sendResetPackage();
					sDialog.dismiss();
				}
			})
			.show();
	}

	private void sendResetPackage() {
		// 在这里组包发送配置包
		try {
			BasicPacket packet = new BasicPacket(InetAddress.getByName("255.255.255.255"), 5880);
			packet.setImportance(BasicPacket.ImportHigh);
			packet.setPacketRemote(false);
			packet.setListener(recvListener);
			
			ShowInfo.printLogW(dev.type + "_________sendResetPackage_________" + dev.ver);
			if(isZigbeeDev){
				packet.packZigbeeReset(dev);
			}else{
				packet.packReset(dev);
			}
			
			autoBinder.addPacketToSend(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	OnRecvListener recvListener = new OnRecvListener(){
		@Override
		public void OnRecvData(PacketCheck packetcheck) {
			if(packetcheck!=null){
				ShowInfo.printLogW("_________OnRecvData_________");
				backbtn.post(new Runnable() {
					
					@Override
					public void run() {
						dev.delFromDatabaseWithName(mContext);
						ShowToast.show(mContext, R.string.reset_success);
						finish();
					}
				});
			}else{
				ShowInfo.printLogW("_________OnRecvData__null_______");
//				if(dev.getConnectStatus() != PublicDefine.ConnectLocal){
				backbtn.post(new Runnable() {
					
					@Override
					public void run() {
						ShowToast.show(mContext, R.string.reset_fail);
					}
				});
//				}
			}
		}
	};
}
