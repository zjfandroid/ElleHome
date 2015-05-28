package elle.home.partactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import elle.home.app.smart.R;
import elle.home.database.AllScene;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.ManageSceneItem;

public class ManageSceneActivity extends BaseActivity {

	public String TAG = "ManageSceneActivity";
	
	AllScene allScene;
	
	//添加界面
	LinearLayout addlayout;
	Context mContext;
	
	//返回按钮
	ImageButton backbtn;
	
	List<ManageSceneItem> itemList = new ArrayList<ManageSceneItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_manage_scene);
		mContext = this;
		addlayout = (LinearLayout)this.findViewById(R.id.manage_scene_add_layout);
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
		allScene = new AllScene(this);
		fresh();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	private void fresh(){
		allScene.freshSceneData();
		itemList.clear();
		addlayout.removeAllViews();
		for(int i=0;i<allScene.allscene.size();i++){
			ManageSceneItem item = new ManageSceneItem(mContext,allScene.allscene.get(i));
			item.setOnClickListener(listener);
			item.manageSceneDeleteBtn.setOnClickListener(dellistener);
			itemList.add(item);
			addlayout.addView(item);
		}
	}
	
	View.OnClickListener dellistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			for(int i=0;i<itemList.size();i++){
				if(v == itemList.get(i).manageSceneDeleteBtn){
					Log.d(TAG,"删除场景："+itemList.get(i).scenedata.sceneName);
					itemList.get(i).scenedata.delSceneData();
					fresh();
				}
			}
		}
	};
	
	View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			for(int i=0;i<itemList.size();i++){
				if(v == itemList.get(i)){
					itemList.get(i).togBtn();
				}else{
					itemList.get(i).hideBtn();
				}
			}
		}
	};

}
