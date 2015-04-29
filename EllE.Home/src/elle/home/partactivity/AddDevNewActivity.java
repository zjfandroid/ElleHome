package elle.home.partactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.skyfishjy.library.RippleBackground;

import elle.home.airkiss.AirKiss;
import elle.home.app.R;
import elle.home.database.OneDev;
import elle.home.hal.UdpCheckNewThread;
import elle.home.hal.WifiAdmin;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.AddDevItemView;
import elle.home.uipart.AddDevItemView.OnAddDev;
import elle.home.utils.SaveDataPreferences;

public class AddDevNewActivity extends Activity{

	public String TAG = "AddDevNewActivity";
	
	WifiAdmin wifiadmin;
	Context mContext;
	
	//现在连接的wifi的名称
	String conssid;
	
	//属于elle.home的可配置设备
	List<ConfigDev> configDevDataList = new ArrayList<ConfigDev>();
	
	//添加在线设备的容器
	private LinearLayout addDevLayout;
	//查询新设备的线程
	UdpCheckNewThread udpchecknew;
	//当前查询的地点
	String locatname;
	//如果增加，在主界面显示的序列号
	int shownum;
	//dev type
	private int type;
	
	//返回按钮
	private ImageButton backbtn;
	
	private AirKiss airKiss;
	private RippleBackground foundDevice;

	private int counter = 180;
	private ProgressHelper mProgressHelper;
	private CountDownTimer mCountDownTimer;
	private Button buttonProgress;
	private TextView txtTips;
	private TextView wifi;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:	//刷新添加设备界面
				foundDevice();
				addDevLayout.removeAllViews();
				
				final List<OneDev> devs = udpchecknew.newdevs;
				for(int i=0;i<devs.size();i++){
					//AddDevItemView item = new AddDevItemView(mcontext,R.drawable.ic_launcher,String.valueOf(udpchecknew.newdevs.get(i).mac),R.drawable.ic_launcher);
					AddDevItemView item = new AddDevItemView(mContext,PublicDefine.getLittleIconByType(devs.get(i).type),String.valueOf(devs.get(i).mac),R.drawable.add_dev_add_icon);
					item.setIsWifi(false);
					item.setListener(new OnAddDev(){
						@Override
						public void addone(AddDevItemView a) {
							Log.d(TAG,"外部需要调用单个设备的添加界面："+a.mac);
							for(int i=0; i<devs.size(); i++){
								if(devs.get(i).mac == Long.parseLong(a.mac)){					
									Intent intent = new Intent(mContext,AddSignalDevActivity.class);
									intent.putExtra("mac", devs.get(i).mac);
									intent.putExtra("type", devs.get(i).type);
									intent.putExtra("ver", devs.get(i).ver);
									intent.putExtra("locatname", locatname);
									intent.putExtra("shownum", shownum);
									mContext.startActivity(intent);
									break;
								}
							}
						}});
					addDevLayout.addView(item);
				}
				break;
			case 1:
				addDevLayout.removeAllViews();
				foundDevice.startRippleAnimation();
				foundDevice.setVisibility(View.VISIBLE);
				break;
			case 2:	//提示对话框				
				Toast.makeText(mContext, msg.getData().getString("tips"), Toast.LENGTH_SHORT).show();
				break;
			
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_dev_new);
		this.mContext = this;
		
		Intent intent = getIntent();
		boolean isNewDeviceFound = intent.getBooleanExtra("isNewDeviceFound", false);
		locatname = intent.getStringExtra("locatname");
		shownum = intent.getIntExtra("shownum", 300);
		type = intent.getIntExtra("type", PublicDefine.TypeLight);
		Log.d(TAG,TAG+":"+locatname+" shownum:"+shownum);
		
		wifiadmin = new WifiAdmin(this);
		initViews();
		
		if(PublicDefine.isWiFiConnect(this)){
			conssid = wifiadmin.getWifiInfo().getSSID();			
			Log.d(TAG,"con ssid:"+conssid);
			conssid = conssid.substring(1, conssid.length()-1);
			startAirkiss();
		}else{
			Log.d(TAG,"wifi not connect,auto enable wifi");
			conssid = "none";
		}
		
		startUdpCheckNewDevice();
	}

	private void startUdpCheckNewDevice() {
		udpchecknew = new UdpCheckNewThread(this,new UdpCheckNewThread.OnNewDevNow() {
			
			@Override
			public void onnonew() {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
			
			@Override
			public void onnewdev() {
				//刷新界面
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
		udpchecknew.startThread();
	}
    
	private void startAirkiss() {
		buttonProgress.setEnabled(false);
		
		String password = SaveDataPreferences.load(mContext, conssid, "");
		airKiss = new AirKiss(conssid, password);
		airKiss.start();
		Log.d(TAG, password  + "____enable wifi______" + conssid);
		
		StringBuilder sb = new StringBuilder(conssid);
		sb.append("\r\n").append(password);
		wifi.setText(sb);
		
		wifi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showConfigWifiDialog(conssid);
			}
		});
	}
	
	protected void showConfigWifiDialog(String ssid) {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_config_aks_wifi);
		//弹出输入法 
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		final EditText editPass = (EditText) window.findViewById(R.id.passwordEt);
		final EditText editText = (EditText) window.findViewById(R.id.ssidEt);
		editText.setText(ssid);
		
		Button ok = (Button) window.findViewById(R.id.configbtn);
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				conssid = editText.getText().toString();
				SaveDataPreferences.save(mContext, conssid, editPass.getText().toString());
				stopAirkiss();
				startAirkiss();
				dlg.dismiss();
			}

		});
	}

	private void initViews() {
		addDevLayout = (LinearLayout)this.findViewById(R.id.add_dev_layout);
		
		foundDevice=(RippleBackground)findViewById(R.id.content_ripple);
		foundDevice.startRippleAnimation();
		foundDevice.setVisibility(View.VISIBLE);
		wifi = (TextView) findViewById(R.id.txt_tips_wifi);
		
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
		
		initProgress();
		
	    buttonProgress = (Button)findViewById(R.id.centerImage);
        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	        		if(null != configDevDataList){
//	        			Intent intent = new Intent("android.net.wifi.PICK_WIFI_NETWORK");
	        			Intent intent = new Intent("android.settings.WIFI_SETTINGS");
	        			startActivity(intent);
//	        			goConfigWifiActivity();
	        		}else{
		            	SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
		            	dialog.setCanceledOnTouchOutside(true);
		            	dialog.setCancelable(true);
		            	dialog.setTitleText("没找到可连接的设备哦～")
		            	.show();
	        		}
            }
        });
	}
	
	private void initProgress() {
		mProgressHelper = new ProgressHelper(mContext);
		mProgressHelper.setProgressWheel((ProgressWheel)findViewById(R.id.progressWheel));
		
		txtTips = (TextView) findViewById(R.id.txt_tips);
		if(type == PublicDefine.TypePlug){
	        	txtTips.setText(mContext.getResources().getString(R.string.connect_plug_0));
		}
		
		mCountDownTimer = new CountDownTimer(counter * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            		buttonProgress.setText(counter+"");
                counter--;
                switch (counter%7){
                    case 0:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        mProgressHelper.setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                counter = -1;
                if(null != foundDevice){
	                	foundDevice.stopRippleAnimation();
	                	if(type == PublicDefine.TypePlug){
	                		txtTips.setText(getResources().getString(R.string.connect_plug_fail));
	                	}else{
	                		txtTips.setText(getResources().getString(R.string.connect_bulb_fail));
	                	}
	                	
	                	mProgressHelper.setProgressVisibility(View.GONE);
	                	buttonProgress.setText(getResources().getString(R.string.connect_by_user));
	                	buttonProgress.setEnabled(true);
	                	findViewById(R.id.txt_tips_wifi).setVisibility(View.GONE);
                }
            }
        }.start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG,"________airKiss.start()__________");
//		try {
//			airKiss.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void stopAirkiss() {
		if(null != airKiss){
			Log.d(TAG,"____airKiss.stop()____");
			airKiss.stop();
			airKiss = null;
		}
		buttonProgress.setEnabled(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		udpchecknew.startCheck();
	}

	@Override
	protected void onStop() {
		super.onStop();
		udpchecknew.stopCheck();
	}

	@Override
	protected void onDestroy() {
		this.setResult(1);
		udpchecknew.stopThread();
		stopAirkiss();
		super.onDestroy();
	}
	
    private void foundDevice(){
    		if(!foundDevice.isShown()){
    			return;
    		}
    		
    		stopAirkiss();
    		
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 1f, 1.2f, 0f);
//        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 1f, 1.2f, 0f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        animatorSet.start();
        
        animatorSet.addListener(new AnimatorListener() {

			@Override
			public void onAnimationCancel(Animator arg0) {
				 foundDevice.setVisibility(View.GONE);
				 foundDevice.stopRippleAnimation();
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				 foundDevice.setVisibility(View.GONE);
				 foundDevice.stopRippleAnimation();
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
			}

			@Override
			public void onAnimationStart(Animator arg0) {
			}
        	
        });
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG,"requeestCode:"+ requestCode+" resultCode:"+resultCode);
		if(requestCode == 0){	//	配置设备wifi回来
			
		}
		
	}
	
	public class ConfigDev{
		//Wifi设备
		ScanResult result;
		String name;
		
		public ConfigDev(ScanResult result){
			this.result = result;
			this.name = result.SSID;
		}	
	}
	
}
