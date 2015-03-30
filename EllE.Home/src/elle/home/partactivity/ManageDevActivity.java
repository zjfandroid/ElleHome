package elle.home.partactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.app.R;
import elle.home.database.AllLocationInfo;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.ManageDevItem;
import elle.home.uipart.ManageDevTitle;

/**
 * 设备管理界面
 * @author jason
 *
 */
public class ManageDevActivity extends BaseActivity {

	public String TAG = "ManageDevActivity";
	
	//所有的地点及对应设备的信息
	AllLocationInfo allInfo;
	
	//添加界面
	LinearLayout addlayout;
	
	Context mContext;
	//返回按钮
	ImageButton backbtn;
	
	int delTmp = 0;
	
	List<ManageDevTitle> titleList = new ArrayList<ManageDevTitle>();
	List<ManageDevItem> itemList = new ArrayList<ManageDevItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_manage_dev);
		mContext = this;
		addlayout = (LinearLayout)this.findViewById(R.id.manage_dev_add_layout);
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
		allInfo = new AllLocationInfo(this);
		fresh();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	private void fresh(){
		allInfo.findAllLocationInfo();
		titleList.clear();
		itemList.clear();
		addlayout.removeAllViews();
		for(int i=0;i<allInfo.allinfo.size();i++){
			ManageDevTitle title = new ManageDevTitle(this,allInfo.allinfo.get(i));
			title.setOnClickListener(listener);
			title.manageDevDeleteBtn.setOnClickListener(dellistener);
			titleList.add(title);
			this.addlayout.addView(title);
			
			List<OneDev> devs = allInfo.allinfo.get(i).devLocationList;
			for(int x=0; x<devs.size(); x++){
				ManageDevItem dev = new ManageDevItem(this, devs.get(x));
				dev.manageDevDeleteBtn.setOnClickListener(dellistener);
				if(PublicDefine.TypeLight == devs.get(x).type){
					dev.manageDevConfigBtn.setOnClickListener(configlistener);
				}else{
					dev.manageDevConfigBtn.setText("");
				}
				dev.setOnClickListener(listener);
				itemList.add(dev);
				this.addlayout.addView(dev);
			}
		}
	}
	
	View.OnClickListener configlistener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			for(int i=0; i<itemList.size(); i++){
				ManageDevItem item = itemList.get(i);
				if(v == item.manageDevConfigBtn){
					Intent intent = new Intent(mContext, DevInfoActivity.class);
					intent.putExtra("dev", item.dev);
					mContext.startActivity(intent);		
					UMeng_OnEvent(EVENT_ID_CLICK_CONFIG_SETTING);
				}
			}
		}
		
	};
	
	View.OnClickListener dellistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			UMeng_OnEvent(EVENT_ID_CLICK_DELETE);

			for(int i=0;i<titleList.size();i++){
				if(v == titleList.get(i).manageDevDeleteBtn){
					Log.d(TAG,"删除"+titleList.get(i).locatinfo.locatname);
					delTmp = i;
					showDeletePlaceDialog(i);
				}
			}
			
			for(int i=0;i<itemList.size();i++){
				if(v == itemList.get(i).manageDevDeleteBtn){
					Log.d(TAG,"删除"+itemList.get(i).dev.devname);
					delTmp = i;
					showDeleteDeviceDialog(i);					
				}
			}
		}

	};
	
	private void showDeleteDeviceDialog(int i) {
		SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(getResources().getString(R.string.manage_dev_tips_del_dev_text)+itemList.get(i).dev.devname+"？")
		.setCancelText(getResources().getString(R.string.manage_dev_tips_del_dev_no))
		.setConfirmText(getResources().getString(R.string.manage_dev_tips_del_dev_yes))
		.showCancelButton(true)
		.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				// reuse previous dialog instance, keep widget user state, reset them if you need
				Log.d(TAG,"不需要删除！");
				allhide();
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
				.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				
				Log.d(TAG,"确认删除！");
				//根据设备类型来删除数据，红外的删除比较特殊
				switch(itemList.get(delTmp).dev.type){
				case PublicDefine.TypeInfra:
					itemList.get(delTmp).dev.delFromDatabase(mContext);
					break;
				case PublicDefine.TypeInfraAir:
				case PublicDefine.TypeInfraTv:
					itemList.get(delTmp).dev.delFromDatabaseWithName(mContext);
					break;
				default:
					itemList.get(delTmp).dev.delFromDatabaseWithName(mContext);
					break;
				}
				fresh();		
			}
		})
		.show();
	}
	
	private void showDeletePlaceDialog(int i) {
		SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(getResources().getString(R.string.manage_dev_tips_del_location_text)+titleList.get(i).locatinfo.locatname+" ？")
		.setCancelText(getResources().getString(R.string.manage_dev_tips_del_location_no))
		.setConfirmText(getResources().getString(R.string.manage_dev_tips_del_location_yes))
		.showCancelButton(true)
		.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				// reuse previous dialog instance, keep widget user state, reset them if you need
				Log.d(TAG,"不需要删除！");
				allhide();
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
				.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				
				Log.d(TAG,"确认删除！");
				titleList.get(delTmp).locatinfo.deleteFromDatabase(mContext);
				fresh();
			}
		})
		.show();
	}
		
	void allhide(){
		for(int i=0;i<titleList.size();i++){
			titleList.get(i).hideBtn();
		}
		for(int i=0;i<itemList.size();i++){
			itemList.get(i).hideBtn();
		}
	}
	
	View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			for(int i=0;i<titleList.size();i++){
				if(v == titleList.get(i)){
					Log.d(TAG,"click "+titleList.get(i).locatinfo.locatname);
					titleList.get(i).togBtn();
				}else{
					titleList.get(i).hideBtn();
				}
			}
			
			for(int i=0;i<itemList.size();i++){
				if(v == itemList.get(i)){
					Log.d(TAG,"click "+itemList.get(i).dev.devname);
					itemList.get(i).togBtn();
				}else{
					itemList.get(i).hideBtn();
				}
			}
		}
	};

}
