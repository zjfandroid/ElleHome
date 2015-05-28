package elle.home.dialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.app.smart.R;
import elle.home.protocol.UserRegister;
import elle.home.protocol.UserRegister.OnDataListener;
import elle.home.utils.ShowToast;

/**
 * @author jason
 *
 */
public class BackupDialog {
	
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_USER_PWD = "user_pwd";
	public static final String KEY_USER_REAL_NAME = "user_real_name";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_RESULT = "result";
	public static final String KEY_REASON = "reason";
	public static final String KEY_REASON_NOUSER = "noUser";
	public static final String KEY_REASON_userExist = "userExist";
	public static final String KEY_REASON_PWD_ERROR = "passwordError";
	public static final String KEY_REASON_unknow = "unknow";
	public static final String VALUE_RESULT_FAIL = "failed";
	public static final String VALUE_RESULT_SUCCESS = "succeed";

	private Context mContext;
	private AlertDialog dlg;
	
	private Button mBtnBackup;
	private Button mBtnRecover;
	
	private JSONObject mLocationInfo;
	private String name;
	
	private OnRecoverListener mRecoverListener;
	
	public BackupDialog(Context context) {
		mContext = context;
		
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_backup);
		//弹出输入法 
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		mBtnBackup = (Button) window.findViewById(R.id.btn_backup);
		mBtnRecover = (Button) window.findViewById(R.id.btn_recover);
		
		OnClickListener mClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_backup:
					doBackup();
					break;
					
				case R.id.btn_recover:
					doRecover();
					break;

				default:
					break;
				}
			}
		};
		
		mBtnBackup.setOnClickListener(mClickListener);
		mBtnRecover.setOnClickListener(mClickListener);
	}

	protected void doRecover() {
		SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(mContext.getResources().getString(R.string.sure_to_recover))
		.setCancelText(mContext.getResources().getString(R.string.manage_dev_tips_del_dev_no))
		.setConfirmText(mContext.getResources().getString(R.string.manage_dev_tips_del_dev_yes))
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
				UserRegister.getInstance().getBackupVer(name, new OnDataListener() {
					
					@Override
					public void onDataBack(JSONObject json) {
						if(null == json){
							showToast(R.string.recover_failed);
						}else{
							if(null != mRecoverListener){
								try {
									mRecoverListener.onRecover(new JSONObject(json.getString("userData")));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							showToast(R.string.recover_succeed);
						}
					}
				});
				sDialog.dismiss();
			}
		})
		.show();
	}

	protected void doBackup(){
		if(null == mLocationInfo){
			return;
		}
		
		SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(mContext.getResources().getString(R.string.sure_to_backup))
		.setCancelText(mContext.getResources().getString(R.string.manage_dev_tips_del_dev_no))
		.setConfirmText(mContext.getResources().getString(R.string.manage_dev_tips_del_dev_yes))
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
				UserRegister.getInstance().backupDevData(name, mLocationInfo, new OnDataListener() {
					
					@Override
					public void onDataBack(JSONObject json) {
						if(null == json){
							showToast(R.string.backup_failed);
						}else{
							showToast(R.string.backup_succeed);
						}
					}
				});
				sDialog.dismiss();
			}
		})
		.show();
	}

	protected void showToast(final int recoverSucceed) {
		mBtnBackup.post(new Runnable() {
			
			@Override
			public void run() {
				ShowToast.show(mContext, recoverSucceed);
			}
		});
	}
	
	public void setLocationInfo(String name, JSONObject jsonObject) {
		this.name = name;
		this.mLocationInfo = jsonObject;
	}
	
	public void setRecoverListener(OnRecoverListener recoverListener) {
		mRecoverListener = recoverListener;
	}
	
	public interface OnRecoverListener{
		void onRecover(JSONObject json);
	}
}
