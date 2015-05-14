package elle.home.partactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.skyfishjy.library.RippleBackground;

import elle.home.app.R;
import elle.home.database.DataBaseHelper;
import elle.home.database.OneDev;
import elle.home.dialog.ConfigCameraDialog;
import elle.home.pojo.DevCamera;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.AddDevItemView;
import elle.home.uipart.AddDevItemView.OnAddDev;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

/**
 * 添加网关设备界面
 * @author jason
 *
 */
public class AddGateWayDevActivity extends Activity{

	public String TAG = "AddGateWayDevActivity";
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_gateway_dev);
		
		this.mContext = this;
		
		Intent intent = getIntent();
		locatname = intent.getStringExtra("locatname");
		shownum = intent.getIntExtra("shownum", 300);
		Log.d(TAG,TAG+":"+locatname+" shownum:"+shownum);
		
		initViews();
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
				AddDevItemView item = new AddDevItemView(mContext,PublicDefine.getLittleIconByType(PublicDefine.TypeInfraCamera), camera.getStrName(),R.drawable.add_dev_add_icon);
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
						onedev.type = PublicDefine.TypeGateWay;
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
        
//        findViewById(R.id.btn_ban_in).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
	}
	
	@Override
	public void finish() {
		setResult(RESULT_OK);
		super.finish();
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
	
}
