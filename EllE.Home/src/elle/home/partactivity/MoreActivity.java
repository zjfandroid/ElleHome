package elle.home.partactivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import elle.home.app.R;

public class MoreActivity extends BaseActivity implements OnClickListener {

	ImageButton btnback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_more);
		btnback = (ImageButton) this.findViewById(R.id.title_btn_left);
		btnback.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					break;
				case MotionEvent.ACTION_UP:
					finish();
					break;
				}
				return true;
			}
		});
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		try {
			((TextView) this.findViewById(R.id.tv_curversion)).setText(String
					.format(this.getResources().getString(
							R.string.current_versionname), getVersionName()));
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_about:
			Intent intent = new Intent(this, AboutActivity.class);
			this.startActivity(intent);
			UMeng_OnEvent(EVENT_ID_CLICK_ABOUT);
			break;
		case R.id.item_feedback:

			break;
		case R.id.item_shake:
			Intent intent2 = new Intent(this, ShakeSettingActivity.class);
			this.startActivity(intent2);
			UMeng_OnEvent(EVENT_ID_CLICK_SHAKE_SETTING);
			break;
		case R.id.item_update:
			UMeng_OnEvent(EVENT_ID_CLICK_CHECK_UPDATE);
			UmengUpdateAgent.forceUpdate(this);
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
					
					@Override
					public void onClick(int arg0) {
				        switch (arg0) {
				        case UpdateStatus.Update:
				        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_UPDATE);
				            break;
				        case UpdateStatus.Ignore:
				        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_IGNORE);
				            break;
				        case UpdateStatus.NotNow:
				        	UMeng_OnEvent(EVENT_ID_CLICK_UMENG_NOTNOW);
				            break;
				        }
				    }
			});
			
			final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getResources().getString(R.string.checking));
			pDialog.show();
			pDialog.setCancelable(false);

			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

				@Override
				public void onUpdateReturned(int arg0, UpdateResponse arg1) {
					switch (arg0) {
					case UpdateStatus.Yes: // has update
						 pDialog.dismiss();
						 UmengUpdateAgent.showUpdateDialog(MoreActivity.this,arg1);
						break;
					case UpdateStatus.No: // has no update
						 pDialog.setTitleText(getResources().getString(R.string.update_no))
	                     .setConfirmText(getResources().getString(R.string.dialog_ok))
	                     .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
						break;
					case UpdateStatus.NoneWifi: // none wifi
						 pDialog.setTitleText(getResources().getString(R.string.update_with_wifi))
	                     .setConfirmText(getResources().getString(R.string.dialog_ok))
	                     .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
						break;
					case UpdateStatus.Timeout: // time out
						pDialog.setTitleText(getResources().getString(R.string.pppp_status_connect_timeout))
	                     .setConfirmText(getResources().getString(R.string.dialog_ok))
	                     .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
						break;
					}
				}
			});
			break;

		default:
			break;
		}
	}

}
