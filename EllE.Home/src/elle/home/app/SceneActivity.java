package elle.home.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import elle.home.database.OneDev;
import elle.home.database.OneSceneData;
import elle.home.database.SceneData;
import elle.home.partactivity.BaseActivity;
import elle.home.protocol.BasicPacket;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.KickView;
import elle.home.uipart.SceneItemDev;
import elle.home.uipart.SceneItemTitle;

public class SceneActivity extends BaseActivity {

	public final String TAG = "SceneActivity";
	
	//添加设备
	LinearLayout addlayout;
	//滚动界面
	KickView kick;
	//设备信息
	//AllLocationInfo allinfo;
	//场景信息,可以在每个单独的条例中操作
	SceneData scenedata;
	//执行场景按钮
	Button runbtn;
	//返回按钮
	ImageButton backbtn;
	//删除场景按钮
	ImageButton delSceneBtn;
	//标题
	TextView sceneTitle;
	
	Context context;
	
	//视图列表
	List<SceneItemDev> sceneItemDevs = new ArrayList<SceneItemDev>();
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				init();
				break;				
			}
		}
		
	};
	
	private AutoService.AutoBinder autoBinder;
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			autoBinder = (AutoService.AutoBinder) service;
			initData();
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
	};
	
	private void userBindService(){
		Intent bindIntent = new Intent(this,AutoService.class);
		bindService(bindIntent,connection,BIND_AUTO_CREATE);
	}
	
	private void userUnbindService(){
		unbindService(connection);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_scene);
		
		context = this;
		addlayout = (LinearLayout)this.findViewById(R.id.scene_items_addlayout);
		this.userBindService();
		
		runbtn = (Button)this.findViewById(R.id.scenerunbtn);
		runbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i=0;i<sceneItemDevs.size();i++){
					BasicPacket tmp = sceneItemDevs.get(i).runFun(autoBinder.getAllInfo());
					if(tmp!=null){
						autoBinder.addPacketToSend(tmp);
					}
				}
			}
		});
		
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(context);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		
		sceneTitle = (TextView)this.findViewById(R.id.title_bar_text);
		kick = (KickView)this.findViewById(R.id.scene_items_layout);
		
		delSceneBtn = (ImageButton)this.findViewById(R.id.title_btn_right);
		delSceneBtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(context);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					Log.d(TAG,"确认删除？");
		            	SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
		            	dialog.setCanceledOnTouchOutside(true);
		            	dialog.setCancelable(true);
		            	dialog.setTitleText(getResources().getString(R.string.scene_tips_del_scene_text))
		            	.setConfirmText(getResources().getString(R.string.scene_tips_del_scene_yes))
		            	.setCancelText(getResources().getString(R.string.scene_tips_del_scene_no))
		            	.setCancelClickListener(new OnSweetClickListener() {
							
							@Override
							public void onClick(SweetAlertDialog sweetAlertDialog) {
								Log.d(TAG,"不需要删除！");
								sweetAlertDialog.dismiss();
							}
						})
		            	.setConfirmClickListener(new OnSweetClickListener() {
									
									@Override
									public void onClick(SweetAlertDialog sweetAlertDialog) {
										Log.d(TAG,"确认删除！");
										sweetAlertDialog.dismiss();
										scenedata.delSceneData();
										activityEditOver();
									}
								})
					.show();
				}
				return true;
			}
		});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	
	private void activityEditOver(){
		this.setResult(5);
		this.finish();
	}
	
	private void initData(){
		Intent intent = this.getIntent();
		scenedata = new SceneData(intent.getStringExtra("scenename"),intent.getIntExtra("sceneicon", 0),this);
		scenedata.getAllScene();
		
		//将没有写入该场景的设备加入到场景中去
		for(int i=0;i<autoBinder.getAllInfo().allinfo.size();i++){
			for(int x=0;x<autoBinder.getAllInfo().allinfo.get(i).devLocationList.size();x++){
				if(scenedata.scenelist.size() == 0){
					OneSceneData scene = new OneSceneData();
					scene.mac = autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).mac;
					scene.devname = autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).devname;
					
					if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypePlug){
						scene.funid = PublicDefine.ScenePlugNullIcon;							
					}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeLight){
						scene.funid = PublicDefine.SceneLightNullIcon;							
					}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeInfra){
						scene.funid = PublicDefine.SceneInfraNullIcon;
					}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeInfraAir){
						scene.funid = PublicDefine.SceneInfraAirNullIcon;
					}else{
						
					}
					scene.commandData = new byte[10];
					scenedata.addOneScene(scene);
				}else{
					for(int y=0;y<scenedata.scenelist.size();y++){
						if(scenedata.scenelist.get(y).mac == autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).mac){
							if(scenedata.scenelist.get(y).devname!=null){
								if(scenedata.scenelist.get(y).devname.equalsIgnoreCase(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).devname)){
									OneSceneData scene = scenedata.scenelist.get(y);
									Log.d(TAG,"找到一条场景信息,mac:"+scene.mac+" devname:"+scene.devname+" funid:"+scene.funid);
									break;
								}
							}
						}
						if(y == (scenedata.scenelist.size()-1)){
							OneSceneData scene = new OneSceneData();
							scene.mac = autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).mac;
							scene.devname = autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).devname;
							if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypePlug){
								scene.funid = PublicDefine.ScenePlugNullIcon;							
							}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeLight){
								scene.funid = PublicDefine.SceneLightNullIcon;							
							}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeInfra){
								scene.funid = PublicDefine.SceneInfraNullIcon;
							}else if(autoBinder.getAllInfo().allinfo.get(i).devLocationList.get(x).type == PublicDefine.TypeInfraAir){
								scene.funid = PublicDefine.SceneInfraAirNullIcon;
							}else{
								
							}
							scene.commandData = new byte[10];
							scenedata.addOneScene(scene);
							Log.d(TAG,"加入一条场景信息,mac:"+scene.mac+" devname:"+scene.devname+" funid:"+scene.funid);
						}
					}
				}
				
			}
		}
		scenedata.getAllScene();
	}
	
	/**
	 * 初始化界面
	 * */
	private void init(){
		sceneTitle.setText(this.getIntent().getStringExtra("scenename"));
		sceneItemDevs.clear();
		this.addlayout.removeAllViews();
		for(int i=0;i<autoBinder.getAllInfo().allinfo.size();i++){
			//地点
			SceneItemTitle location = new SceneItemTitle(this,autoBinder.getAllInfo().allinfo.get(i));
			this.addlayout.addView(location);
			
			List<OneDev> devs = autoBinder.getAllInfo().allinfo.get(i).devLocationList;
			int size = devs.size();
			for(int x=0; x<size; x++){
				OneDev oneDev = devs.get(x);
				if(PublicDefine.TypeInfraCamera == oneDev.type){
					continue;
				}else{
					//地点下的设备
					SceneItemDev dev = new SceneItemDev(this, oneDev, scenedata);
					dev.framelayout.setOnClickListener(toglistener);
					sceneItemDevs.add(dev);
					this.addlayout.addView(dev);
				}
			}
		}
	}
	
	View.OnClickListener toglistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d(TAG,"点击了 场景的item");
			for(int i=0;i<sceneItemDevs.size();i++){
				if(sceneItemDevs.get(i).framelayout == v){
					sceneItemDevs.get(i).togShowFun();
					if(i == (sceneItemDevs.size()-1)){
						//Log.d(TAG,"scroll y:"+kick.getScrollY()+" linear height:"+addlayout.getHeight());
						kick.fullScroll(View.FOCUS_DOWN);
					}
				}else{
					sceneItemDevs.get(i).removeAddFun();
				}
				
			}
		}
	};

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.userUnbindService();
	}	
	
}
