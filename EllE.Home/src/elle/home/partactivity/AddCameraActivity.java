package elle.home.partactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import vstc2.nativecaller.BridgeService;
import vstc2.nativecaller.BridgeService.AddCameraInterface;
import vstc2.nativecaller.BridgeService.CallBackMessageInterface;
import vstc2.nativecaller.NativeCaller;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.skyfishjy.library.RippleBackground;

import elle.home.app.smart.R;
import elle.home.database.DataBaseHelper;
import elle.home.database.OneDev;
import elle.home.dialog.ConfigCameraDialog;
import elle.home.pojo.DevCamera;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.AddDevItemView;
import elle.home.uipart.AddDevItemView.OnAddDev;
import elle.home.uipart.PublicResDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

public class AddCameraActivity extends Activity implements AddCameraInterface ,CallBackMessageInterface{

	public String TAG = "AddCameraActivity";
	private static final int SEARCH_TIME = 1500;
	private static final int MSG_SEARCH_CAMERA_OVER = 0x123;
	
	Context mContext;
	
	HashMap<String, DevCamera> cameras = new HashMap<String, DevCamera>();
	
	//添加摄像头布局
	private  LinearLayout addCaremaLayout;
	//当前查询的地点
	String locatname;
	//如果增加，在主界面显示的序列号
	int shownum;
	
	//返回按钮
	private ImageButton backbtn;
	
	private RippleBackground foundDevice;
	private MyBroadCast receiver;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case MSG_SEARCH_CAMERA_OVER:
				searchCaremaOver();
				break;
			
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_camera);
		initCameraConfig();
		
		this.mContext = this;
		
		Intent intent = getIntent();
		locatname = intent.getStringExtra("locatname");
		shownum = intent.getIntExtra("shownum", 300);
		Log.d(TAG,TAG+":"+locatname+" shownum:"+shownum);
		
		initViews();
		
		startSearchCamera();
	}

	private void initCameraConfig() {
		BridgeService.setAddCameraInterface(this);
		BridgeService.setCallBackMessage(this);
		
//		receiver = new MyBroadCast();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("finish");
//		registerReceiver(receiver, filter);
	}

	private class MyBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d("ip", "_______AddCameraActivity.this.finish()____");
		}

	}
	
	private void searchCaremaOver() {
		ShowInfo.printLogW("________searchCaremaOver_______");
		DataBaseHelper dbhelper = new DataBaseHelper(this);
		String[] checkParams = new String[1];
		Iterator iterator = cameras.keySet().iterator();
		int count = 0;
		
		while(iterator.hasNext()) {
			final DevCamera camera = cameras.get(iterator.next());
			
			//检查是否已添加设备
			boolean isExist = false;
			checkParams[0] = camera.getStrDeviceID();
			String checkSql = "select * from devices where deviceid = ?";
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(checkSql, checkParams);
			while(cursor.moveToNext()){
				db.close();
				isExist = true;
				ShowInfo.printLogW(camera.getStrDeviceID() + "______isExist______" + camera.getStrName());
			}
			
			if(!isExist){
				AddDevItemView item = new AddDevItemView(mContext,PublicResDefine.getLittleIconByType(PublicDefine.TypeInfraCamera), camera.getStrName(),R.drawable.add_dev_add_icon);
				item.setIsWifi(false);
				addCaremaLayout.addView(item, 0);
				count++;
				
				if(1 == count){
					foundDevice();
				}
				
				item.setListener(new OnAddDev() {
					
					@Override
					public void addone(AddDevItemView a) {
						
						OneDev onedev = new OneDev();
//					onedev.mac = camera.getStrMac();
						onedev.type = PublicDefine.TypeInfraCamera;
//					onedev.ver = intent.getByteExtra("ver",(byte) 0);
						onedev.devname = camera.getStrName();
						onedev.setCameraDeviceID(camera.getStrDeviceID());
						onedev.locateNmae = locatname;
						onedev.shownum = shownum;
						
						ConfigCameraDialog dialog = new ConfigCameraDialog(mContext, onedev, false);
					}
				});
			}
			
		}
		
		if(0 == count){
			ShowToast.show(mContext, R.string.device_not_found);
		}
		
	}

	private void startSearchCamera() {
		new Thread(new SearchThread()).start();
		handler.postDelayed(updateThread, SEARCH_TIME);
	}

	private class SearchThread implements Runnable {
		@Override
		public void run() {
			Log.d("tag", "startSearch");
			NativeCaller.StartSearch();
		}
	}
	
	Runnable updateThread = new Runnable() {

		public void run() {
			NativeCaller.StopSearch();
			Message msg = handler.obtainMessage();
			msg.what = MSG_SEARCH_CAMERA_OVER;
			handler.sendMessage(msg);
		}
	};

	private void initViews() {
		addCaremaLayout = (LinearLayout)this.findViewById(R.id.root_layout);
		
		foundDevice=(RippleBackground)findViewById(R.id.content_ripple);
		foundDevice.startRippleAnimation();
		foundDevice.setVisibility(View.VISIBLE);
		
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
		
        ImageView button=(ImageView)findViewById(R.id.centerImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            	Intent intent = new Intent("android.settings.WIFI_SETTINGS");
	            	startActivity(intent);
            }

        });
	}

	@Override
	protected void onStop() {
		super.onStop();
		NativeCaller.StopSearch();
	}

	@Override
	protected void onDestroy() {
		this.setResult(1);
		super.onDestroy();
	}
	
    private void foundDevice(){
    		if(!foundDevice.isShown()){
    			return;
    		}
    		
    		foundDevice.stopRippleAnimation();
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
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				 foundDevice.setVisibility(View.GONE);
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
	public void callBackSearchResultData(int cameraType, String strMac,
			String strName, String strDeviceID, String strIpAddr, int port) {
		
		ShowInfo.printLogW(strMac + "_______strName_______" + strName + "____strDeviceID____" + strDeviceID);
		
		if(!cameras.containsKey(strDeviceID)){
			cameras.put(strDeviceID, new DevCamera(cameraType, strMac, strName, strDeviceID, strIpAddr, port));
		}
	}

	@Override
	public void CallBackGetStatus(String did, String resultPbuf, int cmd) {
		ShowInfo.printLogW(did + "___________CallBackGetStatus__________" + resultPbuf);
//		if (cmd == ContentCommon.CGI_IEGET_STATUS) {
//			String cameraType = spitValue(resultPbuf, "upnp_status=");
//			int intType = Integer.parseInt(cameraType);
//			int type14 = (int) (intType >> 16) & 1;// 14位 来判断是否报警联动摄像机
//			if (intType == 2147483647) {// 特殊值
//				type14 = 0;
//			}
			
//			if(type14==1){
//				handler.sendEmptyMessage(2);
//			}
//		}		
//	}
//	
//	private String spitValue(String name, String tag) {
//		String[] strs = name.split(";");
//		for (int i = 0; i < strs.length; i++) {
//			String str1 = strs[i].trim();
//			if (str1.startsWith("var ")) {
//				str1 = str1.substring(4, str1.length());
//			}
//			if (str1.startsWith(tag)) {
//				String result = str1.substring(str1.indexOf("=") + 1);
//				return result;
//			}
//		}
//		return -1 + "";
	}
}
