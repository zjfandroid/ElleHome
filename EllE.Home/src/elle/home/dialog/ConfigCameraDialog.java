package elle.home.dialog;

import vstc2.nativecaller.BridgeService;
import vstc2.nativecaller.BridgeService.IpcamClientInterface;
import vstc2.nativecaller.ContentCommon;
import vstc2.nativecaller.NativeCaller;
import vstc2.nativecaller.PlayActivity;
import vstc2.nativecaller.SystemValue;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

/**
 * 连接摄像头
 * @author jason
 *
 */
public class ConfigCameraDialog implements IpcamClientInterface{
	private static final String STR_DID = "did";
	private static final String STR_MSG_PARAM = "msgparam";
	
	private int option = ContentCommon.INVALID_OPTION;
	private int CameraType = ContentCommon.CAMERA_TYPE_MJPEG;
	
	private Context mContext;
	private AlertDialog dlg;
	
	private View progressBar;
	private TextView tvStatus;
	
	private OneDev onedev;
	
	private boolean isAutoLogin;
	
	public ConfigCameraDialog(Context context, OneDev onedev, boolean isAutoLogin) {
		mContext = context;
		this.isAutoLogin = isAutoLogin;
		this.onedev = onedev;
		
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_connect_camera);
		//弹出输入法 
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		progressBar = window.findViewById(R.id.connect_progress);
		tvStatus = (TextView) window.findViewById(R.id.tv_status);
		Button ok = (Button) window.findViewById(R.id.configbtn);
		final EditText editPass = (EditText) window.findViewById(R.id.et_password);
		final EditText editUser = (EditText) window.findViewById(R.id.et_user_name);
		final EditText editName = (EditText) dlg.getWindow().findViewById(R.id.et_dev_name);
		editName.setText(onedev.devname);
		
		if(isAutoLogin){
			ok.setEnabled(false);
			editUser.setText(onedev.getCameraUserName());
			editPass.setText(onedev.getCameraPassWord());
			tvStatus.setText("自动登录中，请稍后～");
			
//			if(1==SystemValue.status && onedev.getCameraDeviceID() == SystemValue.deviceId){
//				ShowInfo.printLogW("______已登录_______");
//				Intent intent2= new Intent(mContext,PlayActivity.class);
//				mContext.startActivity(intent2);
//				SystemValue.status = 0;
//				dlg.dismiss();
//			}else{
				doConnectCamera(editUser.getText().toString(), editPass.getText().toString(), editName.getText().toString());
//			}
//		}else{
		}
		
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				doConnectCamera(editUser.getText().toString(), editPass.getText().toString(), editName.getText().toString());
				
				// 隐藏输入法
				InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}

		});
	}
	
	private void doConnectCamera(String strUser, String strPwd, String strDevName) {
		if (null == strDevName || strDevName.length() == 0) {
			ShowToast.show(mContext, R.string.input_camera_id);
			return;
		}

		if (null == strUser || strUser.length() == 0) {
			ShowToast.show(mContext, R.string.input_camera_user);
			return;
		}
		
		if (option == ContentCommon.INVALID_OPTION) {
			option = ContentCommon.ADD_CAMERA;
		}
		
		onedev.devname = strDevName;
		onedev.setCameraUserName(strUser);
		onedev.setCameraPassWord(strPwd);
		
		SystemValue.deviceId = onedev.getCameraDeviceID();
		SystemValue.deviceName = strUser;
		SystemValue.devicePass = strPwd;
		
		Intent in = new Intent();
		in.putExtra(ContentCommon.CAMERA_OPTION, option);
		in.putExtra(ContentCommon.STR_CAMERA_ID, SystemValue.deviceId);
		in.putExtra(ContentCommon.STR_CAMERA_USER, strUser);
		in.putExtra(ContentCommon.STR_CAMERA_PWD, strPwd);
		in.putExtra(ContentCommon.STR_CAMERA_TYPE, CameraType);
		
		progressBar.setVisibility(View.VISIBLE);
		BridgeService.setIpcamClientInterface(this);
		
		NativeCaller.Init();
		new Thread(new StartPPPPThread()).start();
	}

	class StartPPPPThread implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(100);
				StartCameraPPPP();
			} catch (Exception e) {

			}
		}
	}

	private void StartCameraPPPP() {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		int result = NativeCaller.StartPPPP(SystemValue.deviceId, SystemValue.deviceName,
				SystemValue.devicePass,1,"");
		Log.i("ip", "result:"+result);
	}
	
	private Handler PPPPMsgHandler = new Handler() {
		public void handleMessage(Message msg) {

			Bundle bd = msg.getData();
			int msgParam = bd.getInt(STR_MSG_PARAM);
			int msgType = msg.what;
			Log.i("aaa", "===="+msgType+"--msgParam:"+msgParam);
			String did = bd.getString(STR_DID);
			switch (msgType) {
			case ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS:
				int resid = R.string.pppp_status_connecting;
				switch (msgParam) {
				case ContentCommon.PPPP_STATUS_CONNECTING://0
					resid = R.string.pppp_status_connecting;
					progressBar.setVisibility(View.VISIBLE);
					SystemValue.status = 2;
					break;
				case ContentCommon.PPPP_STATUS_CONNECT_FAILED://3
					resid = R.string.pppp_status_connect_failed;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				case ContentCommon.PPPP_STATUS_DISCONNECT://4
					resid = R.string.pppp_status_disconnect;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				case ContentCommon.PPPP_STATUS_INITIALING://1
					resid = R.string.pppp_status_initialing;
					progressBar.setVisibility(View.VISIBLE);
					SystemValue.status = 2;
					break;
				case ContentCommon.PPPP_STATUS_INVALID_ID://5
					resid = R.string.pppp_status_invalid_id;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				case ContentCommon.PPPP_STATUS_ON_LINE://2 在线状态
					if(null != dlg && dlg.isShowing()){
						resid = setStatueOnline(did);
					}
					break;
				case ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE://6
					resid = R.string.device_not_on_line;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				case ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT://7
					resid = R.string.pppp_status_connect_timeout;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				case ContentCommon.PPPP_STATUS_CONNECT_ERRER://8
					resid =R.string.pppp_status_pwd_error;
					progressBar.setVisibility(View.GONE);
					SystemValue.status = 0;
					break;
				default:
					resid = R.string.pppp_status_unknown;
				}
				tvStatus.setText(mContext.getResources().getString(resid));
				
				if (msgParam == ContentCommon.PPPP_STATUS_ON_LINE) {
					NativeCaller.PPPPGetSystemParams(did,
							ContentCommon.MSG_TYPE_GET_PARAMS);
				}
				if (msgParam == ContentCommon.PPPP_STATUS_INVALID_ID
						|| msgParam == ContentCommon.PPPP_STATUS_CONNECT_FAILED
						|| msgParam == ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE
						|| msgParam == ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT
						|| msgParam == ContentCommon.PPPP_STATUS_CONNECT_ERRER) {
					NativeCaller.StopPPPP(did);
				}
				break;
			case ContentCommon.PPPP_MSG_TYPE_PPPP_MODE:
				break;

			}

		}

		private int setStatueOnline(String did) {
			int resid;
			resid = R.string.pppp_status_online;
			progressBar.setVisibility(View.GONE);
			//摄像机在线之后读取摄像机类型
			String cmd="get_status.cgi?loginuse=admin&loginpas=" + SystemValue.devicePass
					+ "&user=admin&pwd=" + SystemValue.devicePass;
			NativeCaller.TransferMessage(did, cmd, 1);
			SystemValue.status = 1;
			
			dlg.dismiss();
			if(isAutoLogin){
				ShowInfo.printLogW("__已登录_22222____");
				Intent intent2= new Intent(mContext,PlayActivity.class);
				mContext.startActivity(intent2);
			}else{
				onedev.addToDatabase(mContext);
				ShowToast.show(mContext, mContext.getResources().getString(R.string.tips_add_succeed));
				((Activity)mContext).finish();
			}
			return resid;
		}
	};
	
	@Override
	public void BSMsgNotifyData(String did, int type, int param) {
		Bundle bd = new Bundle();
		Message msg = PPPPMsgHandler.obtainMessage();
		msg.what = type;
		bd.putInt(STR_MSG_PARAM, param);
		bd.putString(STR_DID, did);
		msg.setData(bd);
		PPPPMsgHandler.sendMessage(msg);
		if (type == ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS) {
			Intent intentbrod = new Intent("drop");
			intentbrod.putExtra("ifdrop", param);
			mContext.sendBroadcast(intentbrod);
		}		
	}

	@Override
	public void BSSnapshotNotify(String did, byte[] bImage, int len) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBackUserParams(String did, String user1, String pwd1,
			String user2, String pwd2, String user3, String pwd3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void CameraStatus(String did, int status) {
		// TODO Auto-generated method stub
		
	}

}
