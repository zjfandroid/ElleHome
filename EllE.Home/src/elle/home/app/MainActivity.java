package elle.home.app;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import vstc2.nativecaller.BridgeService;
import vstc2.nativecaller.NativeCaller;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import elle.home.Fragment.LocationDevFragment;
import elle.home.app.smart.R;
import elle.home.database.AllScene;
import elle.home.database.DevLocationInfo;
import elle.home.dialog.BackupDialog;
import elle.home.dialog.BackupDialog.OnRecoverListener;
import elle.home.dialog.DeviceListDialog;
import elle.home.dialog.RegisterDialog;
import elle.home.hal.GetSSID;
import elle.home.hal.GetSSID.OnSSIDListener;
import elle.home.hal.UdpCheckNewThread;
import elle.home.hal.WifiAdmin;
import elle.home.partactivity.AddCameraActivity;
import elle.home.partactivity.AddDevNewActivity;
import elle.home.partactivity.AddLocationActivity;
import elle.home.partactivity.BaseActivity;
import elle.home.partactivity.ManageDevActivity;
import elle.home.partactivity.MoreActivity;
import elle.home.partactivity.SceneAddActivity;
import elle.home.publicfun.PublicDefine;
import elle.home.residemenu.ResideLocatItem;
import elle.home.residemenu.ResideMenu;
import elle.home.residemenu.ResideSenseItem;
import elle.home.shake.ShakeService;
import elle.home.uipart.PublicResDefine;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;
import elle.home.utils.StringUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener{

	public String TAG = "MainActivity";
	
	private ResideMenu resideMenu;
	private Context mContext;
	
	//是否真的需要退出
	private boolean isQuit;

	//首页展示的背景图片
	//FrameLayout mainShowBk;	
	//添加设备按钮
	private ImageButton addBtn;
	private Animation addBtnShake;
	private TextView titletext;
	
	//地点视图列表
	private List<ResideLocatItem> locatlist;
	private int currentLocatViewId;
	//场景视图列表
	private List<ResideSenseItem> senseItem;
	
	//所有的地点及对应设备的信息
	//AllLocationInfo allInfo;
	//场景信息
	private AllScene allScene;
	
	//振动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	private boolean isNewDeviceFound;
	
	private AutoService.AutoBinder autoBinder;
	public AutoService.AutoBinder getAutoBinder() {
		return autoBinder;
	}

	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			autoBinder = (AutoService.AutoBinder) service;
			
			Message msg = new Message();
			msg.what = 4;
			handler.sendMessage(msg);
			
			autoBinder.getUdpCheckNew().setListener(new UdpCheckNewThread.OnNewDevNow() {
				
				@Override
				public void onnonew() {
					isNewDeviceFound = false;
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
				
				@Override
				public void onnewdev() {
					isNewDeviceFound = true;
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
					Log.d(TAG,"find a new dev");
				}
			});
			Log.d(TAG,"main binder ok");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private void userBindService(){
		Intent bindIntent = new Intent(this,AutoService.class);
		bindService(bindIntent,connection,BIND_AUTO_CREATE);
	}
	
	private void userUnbindService(){
		unbindService(connection);
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				Log.d(TAG,"new dev shark");
				addBtnShake = AnimationUtils.loadAnimation(mContext, R.anim.add_btn_shake);
				addBtnShake.reset();
				addBtnShake.setFillAfter(true);
				addBtn.startAnimation(addBtnShake);
				break;
			case 1:
				addBtn.clearAnimation();
				break;
			case 2:	//刷新当前的设备界面
				Log.d(TAG,"刷新当前的设备界面");
				changeFragment(new LocationDevFragment(autoBinder.getAllInfo().allinfo.get(currentLocatViewId)));
				break;
			case 3://添加了新的地点，并更新到新的地点界面去
				Log.d(TAG,"添加了新的地点，并更新到新的地点界面去");
				freshResideMenu();
				break;
			case 4:	//连接到service后，获取数据刷新
				freshResideMenu();
				break;
			case 5:	//刷新当前的场景界面
				Log.d(TAG,"添加了新的场景，并更新到新的场景界面去");
				freshResideMenu();
				break;
			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate");
		
		setContentView(R.layout.activity_main);
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				doOnCreate();
			}
		}, 200);
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				final View view = findViewById(R.id.home);
			    ObjectAnimator anim = ObjectAnimator
			            .ofFloat(view, "zhy", 1.0F,  0.0F)
			            .setDuration(1200);
			    anim.start();  
			    anim.addUpdateListener(new AnimatorUpdateListener()  
			    {  
			        @Override  
			        public void onAnimationUpdate(ValueAnimator animation)  
			        {  
			            float cVal = (Float) animation.getAnimatedValue();  
			            view.setAlpha(cVal);  
			        }  
			    }); 
			}
		}, 800);
		
	}

	private void doOnCreate() {
		this.startService(new Intent(this, ShakeService.class));
		mContext = this;
		
		allScene = new AllScene(this);
		allScene.freshSceneData();
	
		Log.d(TAG,"main binder1");
		this.userBindService();
		Log.d(TAG,"main binder2");
		addBtn = (ImageButton)this.findViewById(R.id.title_bar_right_menu);
		titletext = (TextView)this.findViewById(R.id.title_bar_text);
		
		setData();
		setUpMenu();
		initCameraConfig();
		//友盟自动更新
//		UmengUpdateAgent.update(this);
//		UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
//			
//			@Override
//			public void onClick(int arg0) {
//		        switch (arg0) {
//		        case UpdateStatus.Update:
//		        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_UPDATE);
//		            break;
//		        case UpdateStatus.Ignore:
//		        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_IGNORE);
//		            break;
//		        case UpdateStatus.NotNow:
//		        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_NOTNOW);
//		            break;
//		        }
//		    }
//		});
	}

	private void initCameraConfig() {
		Intent intent = new Intent();
		intent.setClass(this, BridgeService.class);
		startService(intent);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					NativeCaller.PPPPInitialOther("ADCBBFAOPPJAHGJGBBGLFLAGDBJJHNJGGMBFBKHIBBNKOKLDHOBHCBOEHOKJJJKJBPMFLGCPPJMJAPDOIPNL");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(null != resideMenu){
			resideMenu.checkLogin();
		}
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG,"onDestroy");
		super.onDestroy();
		Intent intent = new Intent(this,AutoService.class);
		this.userUnbindService();
		this.stopService(intent);
		
		NativeCaller.Free();
		Intent intentBridge = new Intent();
		intentBridge.setClass(this, BridgeService.class);
		stopService(intentBridge);
	}
	
	private void setData(){
		vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);				
		currentLocatViewId = 0;
	}
	
	private void setUpMenu(){
		
		//mainShowBk.setBackgroundResource(R.drawable.main_home_bk1);
		
		resideMenu = new ResideMenu(this);
		resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setOnClickListener(this);
        resideMenu.setOnBtnClickListener(mOnBtnClickListener);
        
        resideMenu.btnscene.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable(R.drawable.reside_item_pressed));
				}else if(event.getAction() == MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					v.setBackgroundColor(getResources().getColor(R.color.transparent));
//					//点击了关于
//					Intent intent = new Intent(mContext,AboutActivity.class);
//					//mContext.startActivityForResult(intent, 3);
//					mContext.startActivity(intent);
					//点击了更多
					Intent intent  = new Intent(mContext,MoreActivity.class);
					mContext.startActivity(intent);
					UMeng_OnEvent(EVENT_ID_CLICK_MORE);
				}
				
				return true;
			}
		});
        
        resideMenu.btnsetting.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable(R.drawable.reside_item_pressed));
				}else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
					v.setBackgroundColor(getResources().getColor(R.color.transparent));
					Log.d(TAG,"btnsetting click");
					Intent intent = new Intent(mContext, ManageDevActivity.class);
					startActivityForResult(intent, 3);
					UMeng_OnEvent(EVENT_ID_CLICK_DEV_MANAGER);
				}
				return true;
			}
		});

        addBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	            UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV);

				vibrator.vibrate(vibarator_time);
				final String ssid = getCurrentSSID();
				
				final DeviceListDialog mDeviceListDialog = new DeviceListDialog(mContext);
				mDeviceListDialog.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(3 == position){
							goAddCameraActivity();
				            UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV , KEY_DEV_TYPE , R.string.type_carema);
						}else if(0 == position){
							onWifiDevClick(ssid, PublicDefine.TypeLight);
				            UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV , KEY_DEV_TYPE , R.string.type_light_string);
						}else if( 1 == position){
							onWifiDevClick(ssid, PublicDefine.TypePlug);
				            UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV , KEY_DEV_TYPE , R.string.type_plug_string);
						}else if( 2 == position){
//							onWifiDevClick(ssid, PublicDefine.TypeGateWay);
							ShowToast.show(mContext, R.string.device_not_found);
							UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV , KEY_DEV_TYPE , R.string.type_gateway);
						}else{
				            UMeng_OnEvent(EVENT_ID_CLICK_ADD_DEV , KEY_DEV_TYPE , R.string.type_infra_string);
							ShowToast.show(mContext, R.string.device_not_found);
						}
						
						mDeviceListDialog.dissMiss();
					}

					private void onWifiDevClick(final String ssid, byte type) {
						if(isNewDeviceFound || isWifiSaved(ssid)){
							goAddDevActivity(type);
						}else{
							showConfigWifiDialog(ssid, type);
						}
					}
				});
			}
			
		});
        
        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
	}
	
	protected boolean isWifiSaved(String ssid) {
		String password = SaveDataPreferences.load(mContext, ssid, "");
		if(null == password || password.isEmpty()){
			return false;
		}
		
		return true;
	}

	protected void showConfigWifiDialog(String ssid, final int type) {
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
				goAddDevActivity(type);
				SaveDataPreferences.save(mContext, editText.getText().toString(), editPass.getText().toString());
				dlg.dismiss();
			}

		});
		
		new GetSSID(new OnSSIDListener() {
			
			@Override
			public void onSSIDGet(final String ssid, final String pass) {
				MainActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setSSID(editPass, editText, ssid, pass);
					}
				});
			}
			
			@Override
			public void onFail(final String info) {
//				MainActivity.this.runOnUiThread(new Runnable() {
//					
//					@Override
//					public void run() {
//						ShowToast.show(mContext, info);
//					}
//				});
			}

			private void setSSID(final EditText editPass,
					final EditText editText, String ssid, String pass) {
				if(null != ssid && !ssid.isEmpty()){
					editText.setText(ssid);
				}
				
				if(null != pass && !pass.isEmpty()){
					editPass.setText(pass);
				}
			}
		});
	}

	private String getCurrentSSID() {
		WifiAdmin wifiadmin = new WifiAdmin(this);
		String conssid = "";
		
		if(PublicDefine.isWiFiConnect(this)){
			conssid = wifiadmin.getWifiInfo().getSSID();	
			if(null != conssid && conssid.contains("\"")){
				conssid = conssid.substring(1, conssid.length()-1);
			}
			ShowInfo.printLogW("______getCurrentSSID_______" + conssid);
		}else{
			Log.d(TAG,"wifi not connect,auto enable wifi");
			PublicDefine.toggleWiFi(mContext, true);
		}
		
		return conssid;
	}

	protected void goAddDevActivity(int type) {
		Intent intent = new Intent(mContext, AddDevNewActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("isNewDeviceFound", isNewDeviceFound);
		intent.putExtra("locatname", locatlist.get(currentLocatViewId).name);
		intent.putExtra("shownum", autoBinder.getAllInfo().allinfo.get(currentLocatViewId).devLocationList.size());
		startActivityForResult(intent, 0);		
	}
	
	protected void goAddCameraActivity() {
		Intent intent = new Intent(mContext,AddCameraActivity.class);
		intent.putExtra("locatname", locatlist.get(currentLocatViewId).name);
		intent.putExtra("shownum", autoBinder.getAllInfo().allinfo.get(currentLocatViewId).devLocationList.size());
		startActivityForResult(intent, 0);		
	}

	private void freshResideMenu(){
		
		resideMenu.rmLeftItems();
		locatlist = getLocatInfo();
		senseItem = getSenseInfo();
		resideMenu.setMenuLocats(locatlist);
        resideMenu.setMenuPartLine();
        resideMenu.setMenuSenses(senseItem);
        resideMenu.setMenuPartLine();
        titletext.setText(autoBinder.getAllInfo().allinfo.get(currentLocatViewId).locatname);
        LocationDevFragment  tmp = new LocationDevFragment(autoBinder.getAllInfo().allinfo.get(currentLocatViewId));
        changeFragment(tmp);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(null == resideMenu){
			return false;
		}
		return resideMenu.dispatchTouchEvent(ev);
	}

	private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }
	
	public ResideMenu getResideMenu(){
        return resideMenu;
    }

	@Override
	public void onClick(View v) {
		if(null == locatlist || null == resideMenu){
			return;
		}
		
		if(v==resideMenu.btnsetting){
			Log.d(TAG,"点击了设置按钮");
		}
		
		int size = locatlist.size();
		for(int i=0;i<size;i++){
			if(v==locatlist.get(i)){
				if(i == size-1){
					//添加地点
					Log.d(TAG,"添加地点");
					Intent intent = new Intent(mContext,AddLocationActivity.class);
					startActivityForResult(intent, 1);
					UMeng_OnEvent(EVENT_ID_CLICK_ADD_LOCATION);
				}else{
					//点中了某个地点
					Log.d(TAG,"点中了某个地点："+i + "   , currentLocatViewId" + currentLocatViewId);
					currentLocatViewId = i;
					DevLocationInfo info = autoBinder.getAllInfo().allinfo.get(currentLocatViewId);
					titletext.setText(info.locatname);
					changeFragment(new LocationDevFragment(info));
					UMeng_OnEvent(EVENT_ID_CLICK_LOCATION ,KEY_LOCATION_NAME ,info.locatname);
				}
			}
		}
		
		size = senseItem.size();
		for(int i=0;i<size;i++){
			if(v == senseItem.get(i)){
				if(i == size-1){
					Log.d(TAG,"点中了添加场景");
					Intent intent = new Intent(mContext,SceneAddActivity.class);
					startActivityForResult(intent, 2);
					UMeng_OnEvent(EVENT_ID_CLICK_ADD_SCENE);
				}else{
					Log.d(TAG,"点中了某个场景："+i);
					Intent intent = new Intent(mContext,SceneActivity.class);
					intent.putExtra("scenename", allScene.allscene.get(i).sceneName);
					intent.putExtra("sceneicon", allScene.allscene.get(i).sceneicon);
					//mContext.startActivity(intent);
					startActivityForResult(intent, 2);
					UMeng_OnEvent(EVENT_ID_CLICK_SCENE , KEY_SCENE_NAME , allScene.allscene.get(i).sceneName);
				}
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg0 == 0){	//从添加设备界面返回
			Log.d(TAG,"从添加设备界面返回");
			if(autoBinder!=null){
				autoBinder.getAllInfo().allinfo.get(currentLocatViewId).freshAllDev();
				autoBinder.freshUdpCheckData();
				Message msg = new Message();
				msg.what = 2;
				//currentLocatViewId = 0;
				handler.sendMessage(msg);
			}
		}else if(arg0 == 1){	//从添加地点界面返回
			Log.d(TAG,"从添加地点界面返回"+arg1);
			if(arg1==2){
				if(autoBinder!=null){
					autoBinder.getAllInfo().findAllLocationInfo();
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}
				//currentLocatViewId = 0;
			}
			
		}else if(arg0 == 2){	//从添加场景界面返回
			Log.d(TAG,"从添加场景界面返回"+arg1);
			if(arg1==5){
				if(autoBinder!=null){
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				}
				
			}
		}else if(arg0 == 3){	//从设备管理界面返回
			if(autoBinder!=null){
				autoBinder.getAllInfo().findAllLocationInfo();
				autoBinder.freshUdpCheckData();
				Message msg = new Message();
				msg.what = 3;
				currentLocatViewId= 0;
				handler.sendMessage(msg);
			}
		}
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
            UMeng_OnEvent(EVENT_ID_CLICK_NAV_BAR, KEY_NAV_BAR_STATE, R.string.plug_status_on);
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
            UMeng_OnEvent(EVENT_ID_CLICK_NAV_BAR, KEY_NAV_BAR_STATE, R.string.plug_status_off);
        }
    };
    
    
    
    private ArrayList<ResideLocatItem> getLocatInfo(){
    	List<ResideLocatItem> tmp = new ArrayList<ResideLocatItem>();
    	for(int i=0;i<autoBinder.getAllInfo().allinfo.size();i++){
    		ResideLocatItem item = new ResideLocatItem(this);
    		item.setView(PublicResDefine.getResideLocatIcon(autoBinder.getAllInfo().allinfo.get(i).locaticon), autoBinder.getAllInfo().allinfo.get(i).locatname);
    		item.setOnClickListener(this);
    		tmp.add(item);
    	}
    	ResideLocatItem item = new ResideLocatItem(this);
    	item.setView2(R.drawable.reside_icon_add, getResources().getString(R.string.residemenu_add_location));
    	item.setOnClickListener(this);
    	tmp.add(item);
    	return (ArrayList<ResideLocatItem>) tmp;
    }
    
    private ArrayList<ResideSenseItem> getSenseInfo(){
    	List<ResideSenseItem> tmp = new ArrayList<ResideSenseItem>();
    	allScene.freshSceneData();
    	for(int i=0;i<allScene.allscene.size();i++){
    		ResideSenseItem item = new ResideSenseItem(this);
        	item.setView2(PublicResDefine.getSceneResideLogo(allScene.allscene.get(i).sceneicon), allScene.allscene.get(i).sceneName);
        	item.setOnClickListener(this);
        	tmp.add(item);
    	}
    	ResideSenseItem item = new ResideSenseItem(this);
    	item.setView2(R.drawable.reside_icon_add, getResources().getString(R.string.residemenu_add_scene));
    	item.setOnClickListener(this);
    	tmp.add(item);
    	
    	return (ArrayList<ResideSenseItem>) tmp;
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.d(TAG,"key back:"+event.getRepeatCount()+" isquit:"+isQuit);
			if(isQuit || null == mContext){
//				 UMeng_OnEvent(EVENT_ID_APP_QUIT);
				finish();
			}else{
				Toast.makeText(mContext, getResources().getString(R.string.tips_click_back_btn), Toast.LENGTH_SHORT).show();
				isQuit = true;
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){

					@Override
					public void run() {
						isQuit = false;
					}}, 1600);
			}
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
    
	private RegisterDialog dlg;
	private OnClickListener mOnBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_login:
				String name = SaveDataPreferences.load(mContext, RegisterDialog.KEY_USER_NAME, "");
				if(StringUtils.isEmpty(name)){
					dlg = new RegisterDialog(mContext);
					dlg.setOnDismissListener(new OnDismissListener(){
						
						@Override
						public void onDismiss(DialogInterface dialog) {
							if(!isFinishing()){
								resideMenu.checkLogin();
							}
						}
						
					});
				}else{
					BackupDialog backupDialog = new BackupDialog(mContext);
					backupDialog.setRecoverListener(new OnRecoverListener(){

						@Override
						public void onRecover(JSONObject json) {
							try {
								autoBinder.getAllInfo().recoverFromJSONData(json);
								autoBinder.getAllInfo().findAllLocationInfo();
								autoBinder.freshUdpCheckData();
								Message msg = new Message();
								msg.what = 3;
								currentLocatViewId= 0;
								handler.sendMessage(msg);
							} catch (UnknownHostException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						
					});
					
					try {
						backupDialog.setLocationInfo(name, autoBinder.getAllInfo().getJSONData());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				break;
				
			case R.id.confirm_button:
				break;
				
			default:
				break;
			}
		}
	};
}
