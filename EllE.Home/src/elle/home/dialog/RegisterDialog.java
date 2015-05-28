package elle.home.dialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import elle.home.app.smart.R;
import elle.home.protocol.UserRegister;
import elle.home.protocol.UserRegister.OnDataListener;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;
import elle.home.utils.StringUtils;

/**
 * @author jason
 *
 */
public class RegisterDialog {
	
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
	
	private boolean isRegister;

	private EditText mEdtName;
	private EditText mEdtPWD;
	private EditText mEdtRealName;
	private EditText mEdtEmail;
	private Button mBtnRegister;
	
	public RegisterDialog(Context context) {
		mContext = context;
		
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_register);
		//弹出输入法 
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		mEdtName = (EditText) window.findViewById(R.id.edt_name);
		mEdtPWD = (EditText) window.findViewById(R.id.edt_pwd);
		mEdtRealName = (EditText) window.findViewById(R.id.edt_real_name);
		mEdtEmail = (EditText) window.findViewById(R.id.edt_email);
		mBtnRegister = (Button) window.findViewById(R.id.register_button);

		window.findViewById(R.id.confirm_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkInfos();
			}
		});
		
		mBtnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isRegister){
					mEdtRealName.setVisibility(View.GONE);
					mEdtEmail.setVisibility(View.GONE);
					mBtnRegister.setText(R.string.register);
				}else{
					mEdtRealName.setVisibility(View.VISIBLE);
					mEdtEmail.setVisibility(View.VISIBLE);
					mBtnRegister.setText(R.string.login);
				}
				isRegister = !isRegister;
			}
		});
	}
	
	protected void checkInfos() {
		String name = mEdtName.getText().toString();
		String pwd = mEdtPWD.getText().toString();
		if(StringUtils.isEmpty(name)){
			ShowToast.show(mContext, R.string.enter_user_name);
			return;
		}
		
		if(StringUtils.isEmpty(pwd)){
			ShowToast.show(mContext, R.string.enter_user_pwd);
			return;
		}
		
		if(isRegister){
			doRegister(name, pwd);
		}else{
			doLogin(name, pwd);
		}
	}

	protected void doLogin(final String name, final String pwd) {
		UserRegister.getInstance().login(name, pwd, new OnDataListener() {
			
			@Override
			public void onDataBack(JSONObject json) {
				if(null == json){
					ShowInfo.printLogW("_____onDataBack  null_____");
				}else{
					try {
						if(VALUE_RESULT_FAIL.equalsIgnoreCase(json.getString(KEY_RESULT))){
							ShowInfo.printLogW("_____onDataBack_____" + json.getString(KEY_REASON));
							showWongInfo(json.getString(KEY_REASON));
						}else{
							ShowToast(R.string.login_succeed);
							SaveDataPreferences.save(mContext, KEY_USER_NAME, name);
							SaveDataPreferences.save(mContext, KEY_USER_PWD, pwd);
							dlg.dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	protected void doRegister(final String name, final String pwd) {
		UserRegister.getInstance().register(name, pwd, mEdtRealName.getText().toString(), mEdtEmail.getText().toString(), new OnDataListener() {
			
			@Override
			public void onDataBack(JSONObject json) {
				if(null == json){
					ShowInfo.printLogW("_____onDataBack  null_____");
				}else{
					try {
						if(VALUE_RESULT_FAIL.equalsIgnoreCase(json.getString(KEY_RESULT))){
							showWongInfo(json.getString(KEY_REASON));
						}else{
							ShowToast(R.string.register_succeed);
							SaveDataPreferences.save(mContext, KEY_USER_NAME, name);
							SaveDataPreferences.save(mContext, KEY_USER_PWD, pwd);
							dlg.dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	protected void showWongInfo(final String reason) {
		mEdtName.post(new Runnable() {
			
			@Override
			public void run() {
				if(KEY_REASON_PWD_ERROR.equalsIgnoreCase(reason)){
					ShowToast.show(mContext, R.string.login_wrong_pwd);
				}else if(KEY_REASON_NOUSER.equalsIgnoreCase(reason)){
					ShowToast.show(mContext, R.string.login_nouser);
				}else if(KEY_REASON_userExist.equalsIgnoreCase(reason)){
					ShowToast.show(mContext, R.string.login_userExist);
				}else{
					ShowToast.show(mContext, reason);
				}
			}
		});
	}
	
	protected void ShowToast(final int id) {
		mEdtName.post(new Runnable() {
			
			@Override
			public void run() {
				ShowToast.show(mContext, id);
			}
		});
	}
	
	public void setOnDismissListener(OnDismissListener listener) {
		dlg.setOnDismissListener(listener);
	}
	
	public boolean isRegister() {
		return isRegister;
	}
}
