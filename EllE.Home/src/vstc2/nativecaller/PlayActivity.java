package vstc2.nativecaller;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import vstc2.nativecaller.BridgeService.PlayInterface;
import vstc2.nativecaller.MyRender.OnScreenShot;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.app.smart.R;
import elle.home.partactivity.BaseActivity;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

public class PlayActivity extends BaseActivity implements OnTouchListener,
		OnGestureListener, OnClickListener, PlayInterface {

	private static final String LOG_TAG = "PlayActivity";
	private static final int AUDIO_BUFFER_START_CODE = 0xff00ff;
	//surfaceView控件
	private GLSurfaceView playSurface = null;
	
	//视频数据
	private byte[] videodata = null;
	private int videoDataLen = 0;

	public int nVideoWidths = 0;
	// public byte[] videodatas = null;
	public int nVideoHeights = 0;
	private View progressView = null;
	private boolean bProgress = true;
	private GestureDetector mGestureDetector = new GestureDetector(this);
	private int nResolution = 0;
	private int nBrightness = 0;
	private int nContrast = 0;
	private boolean bInitCameraParam = false;
	private boolean bManualExit = false;
	private TextView textosd = null;
	private String strName = null;;
	private String strDID = null;;
	private PopupWindow popupWindow_about = null;
	private View osdView = null;
	private boolean bDisplayFinished = true;
	private CustomBuffer AudioBuffer = null;
	private AudioPlayer audioPlayer = null;
	private boolean bAudioStart = false;
	private final int BRIGHT = 1;
	private final int CONTRAST = 2;
	private boolean isUpDownPressed = false;
	private boolean isShowtoping = false;
	private ImageButton ptzAudio;
	private ImageButton ptzTakePic;
	
	private PopupWindow controlWindow;//设备方向控制提示控件
	private PopupWindow mPopupWindowProgress;//进度条控件
	//上下左右提示文本
	private TextView control_item;
	//正在控制设备
	private boolean isControlDevice = false;
	
	private String stqvga = "qvga";
	private String stvga = "vga";
	private String stqvga1 = "qvga1";
	private String stvga1 = "vga1";
	private String stp720 = "p720";
	private String sthigh = "high";
	private String stmiddle ="middle";
	private String stmax = "max";
	private Button ptzResolutoin;
	//分辨率标识符
	private boolean ismax = false;
	private boolean ishigh = false;
	private boolean isp720 = false;
	private boolean ismiddle = false;
	private boolean isqvga1 = false;
	private boolean isvga1 = false;
	private boolean isqvga = false;
	private boolean isvga = false;
	private Animation showAnim;
	private boolean isTakepic = false;
	private boolean isMcriophone = false;
	public boolean isH264 = false;//是否是H264格式标志
	private PopupWindow resolutionPopWindow;
	private Animation dismissAnim;
	private View ptzOtherSetAnimView;
	private int timeTag = 0;
	private int timeOne = 0;
	private int timeTwo = 0;
	private ImageButton button_back;
	private BitmapDrawable drawable = null;
	// private LinkedList<byte[]> bs = null;
	private MyBrodCast brodCast = null;

	class MyBrodCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if (arg1.getIntExtra("ifdrop", 2) != 2) {
				PPPPMsgHandler.sendEmptyMessage(1004);
			}

		}
	}

	/**
	 * 在UI线程中刷新界面状态
	 * **/
	private Handler PPPPMsgHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1004:
//				Toast.makeText(PlayActivity.this, "相机断线", 0).show();
//				PlayActivity.this.finish();
				ShowInfo.printLogW("_______相机断线________");
				break;
			default:
				break;
			}

		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mPopupWindowProgress != null && mPopupWindowProgress.isShowing()) {
			mPopupWindowProgress.dismiss();

		}
		if (resolutionPopWindow != null && resolutionPopWindow.isShowing()) {
			resolutionPopWindow.dismiss();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
				showSureDialog();
			return true;
		}
		
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!bProgress) {
				showTop();
				showBottom();
			} else {
				showSureDialog();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/****
	 * 退出确定dialog
	 * */
	public void showSureDialog() {
		SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		dialog.setTitleText(getResources().getString(R.string.exit_play_show))
		.setCancelText(getResources().getString(R.string.manage_dev_tips_del_dev_no))
		.setConfirmText(getResources().getString(R.string.manage_dev_tips_del_dev_yes))
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
				Intent intent = new Intent("finish");
				sendBroadcast(intent);
				PlayActivity.this.finish();
				sDialog.dismiss();
			}
		})
		.show();
	}

	private void showTop() {
		if (isShowtoping) {
			isShowtoping = false;
		} else {
			isShowtoping = true;
		}
	}

	//默认视频参数
	private void defaultVideoParams() {
		nBrightness = 1;
		nContrast = 128;
		NativeCaller.PPPPCameraControl(strDID, 1, 0);
		NativeCaller.PPPPCameraControl(strDID, 2, 128);
		showToast(R.string.ptz_default_vedio_params);
	}

	private void showToast(int i) {
		ShowToast.show(this, i);
	}


	//设置视频可见
	private void setViewVisible() {
		if (bProgress)
		{
			bProgress = false;
			progressView.setVisibility(View.INVISIBLE);
			osdView.setVisibility(View.VISIBLE);
			getCameraParams();
		}
	}

	int disPlaywidth;
	private Bitmap mBmp;
	private Handler mHandler = new Handler()
	{

		public void handleMessage(Message msg)
        {
				if (msg.what == 1 || msg.what == 2) {
					setViewVisible();
				}
				if (!isPTZPrompt)
				{
					isPTZPrompt = true;
					showToast(R.string.ptz_control);
				}
				int width = getWindowManager().getDefaultDisplay().getWidth();
				int height = getWindowManager().getDefaultDisplay().getHeight();
				switch (msg.what) {
				case 1: // h264
				{
					if (reslutionlist.size() == 0) {
						if (nResolution == 0) {
							ismax = true;
							ismiddle = false;
							ishigh = false;
							isp720 = false;
							isqvga1 = false;
							isvga1 = false;
							addReslution(stmax, ismax);
						} else if (nResolution == 1) {
							ismax = false;
							ismiddle = false;
							ishigh = true;
							isp720 = false;
							isqvga1 = false;
							isvga1 = false;
							addReslution(sthigh, ishigh);
						} else if (nResolution == 2) {
							ismax = false;
							ismiddle = true;
							ishigh = false;
							isp720 = false;
							isqvga1 = false;
							isvga1 = false;
							addReslution(stmiddle, ismiddle);
						} else if (nResolution == 3) {
							ismax = false;
							ismiddle = false;
							ishigh = false;
							isp720 = true;
							isqvga1 = false;
							isvga1 = false;
							addReslution(stp720, isp720);
							nResolution = 3;
						} else if (nResolution == 4) {
							ismax = false;
							ismiddle = false;
							ishigh = false;
							isp720 = false;
							isqvga1 = false;
							isvga1 = true;
							addReslution(stvga1, isvga1);
						} else if (nResolution == 5) {
							ismax = false;
							ismiddle = false;
							ishigh = false;
							isp720 = false;
							isqvga1 = true;
							isvga1 = false;
							addReslution(stqvga1, isqvga1);
						}
					} else {
						if (reslutionlist.containsKey(strDID))
						{
							getReslution();
						} else {
							if (nResolution == 0) {
								ismax = true;
								ismiddle = false;
								ishigh = false;
								isp720 = false;
								isqvga1 = false;
								isvga1 = false;
								addReslution(stmax, ismax);
							} else if (nResolution == 1) {
								ismax = false;
								ismiddle = false;
								ishigh = true;
								isp720 = false;
								isqvga1 = false;
								isvga1 = false;
								addReslution(sthigh, ishigh);
							} else if (nResolution == 2) {
								ismax = false;
								ismiddle = true;
								ishigh = false;
								isp720 = false;
								isqvga1 = false;
								isvga1 = false;
								addReslution(stmiddle, ismiddle);
							} else if (nResolution == 3) {
								ismax = false;
								ismiddle = false;
								ishigh = false;
								isp720 = true;
								isqvga1 = false;
								isvga1 = false;
								addReslution(stp720, isp720);
								nResolution = 3;
							} else if (nResolution == 4) {
								ismax = false;
								ismiddle = false;
								ishigh = false;
								isp720 = false;
								isqvga1 = false;
								isvga1 = true;
								addReslution(stvga1, isvga1);
							} else if (nResolution == 5) {
								ismax = false;
								ismiddle = false;
								ishigh = false;
								isp720 = false;
								isqvga1 = true;
								isvga1 = false;
								addReslution(stqvga1, isqvga1);
							}
						}

					}

					if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
					{
						FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
								(int)(width * mScale), (int)(mScale * width * 3 / 4));
						lp.gravity = Gravity.CENTER;
						playSurface.setLayoutParams(lp);
					}
					else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
					{
						FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
								(int)(width * mScale), (int)(mScale * height));
						lp.gravity = Gravity.CENTER;
						playSurface.setLayoutParams(lp);
					}
					myRender.writeSample(videodata, nVideoWidths, nVideoHeights);
				}
					break;
				case 2: // JPEG
				{
					if (reslutionlist.size() == 0) {
						if (nResolution == 1) {
							isvga = true;
							isqvga = false;
							addReslution(stvga, isvga);
						} else if (nResolution == 0) {
							isqvga = true;
							isvga = false;
							addReslution(stqvga, isqvga);
						}
					} else {
						if (reslutionlist.containsKey(strDID)) {
							getReslution();
						} else {
							if (nResolution == 1) {
								isvga = true;
								isqvga = false;
								addReslution(stvga, isvga);
							} else if (nResolution == 0) {
								isqvga = true;
								isvga = false;
								addReslution(stqvga, isqvga);
							}
						}
					}
					mBmp = BitmapFactory.decodeByteArray(videodata, 0,
							videoDataLen);
					if (mBmp == null) {
						ShowInfo.printLogW("_______decodeByteArray null_____");
						bDisplayFinished = true;
						return;
					}else{
						ShowInfo.printLogW("_______decodeByteArray videoDataLen_____" + videoDataLen);
					}
	
				}
					break;
				default:
					break;
				}
			   if (msg.what == 1 || msg.what == 2)
			   {
					bDisplayFinished = true;
			   }	
		 }

	};

	public void initExitPopupWindow2() {
		LayoutInflater li = LayoutInflater.from(this);
		View popv = li.inflate(R.layout.popup_d, null);
		popv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		
		Button button_load = (Button) popv.findViewById(R.id.add_check_load);
		Button button_phone = (Button) popv.findViewById(R.id.add_check_phone);
		popupWindow_about = new PopupWindow(popv,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		popupWindow_about.setAnimationStyle(R.style.AnimationPreview);
		popupWindow_about.setFocusable(true);
		popupWindow_about.setOutsideTouchable(true);
		popupWindow_about.setBackgroundDrawable(new ColorDrawable(0));
		button_load.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				NativeCaller.PPPPCameraControl(SystemValue.deviceId, 36, 36);
				popupWindow_about.dismiss();
				ptzResolutoin.setText("VGA");
				Log.d("VGA", "VGA");
			}
		});
		button_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NativeCaller.PPPPCameraControl(SystemValue.deviceId, 37, 37);
				popupWindow_about.dismiss();
				ptzResolutoin.setText("QVGA");
				Log.d("VGA", "QVGA");
			}
		});
		popupWindow_about
				.setOnDismissListener(new PopupWindow.OnDismissListener() {

					@Override
					public void onDismiss() {
						popupWindow_about.dismiss();
					}
				});
		popupWindow_about.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupWindow_about.dismiss();
				}
				return false;
			}
		});
	}

	private void getCameraParams() {

		NativeCaller.PPPPGetSystemParams(strDID,
				ContentCommon.MSG_TYPE_GET_CAMERA_PARAMS);
	}

	private Handler msgHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Log.d("tag", "断线了");
				Toast.makeText(getApplicationContext(),
						R.string.pppp_status_disconnect, Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getDataFromOther();
		setContentView(R.layout.activity_camera_play);
		strName = SystemValue.deviceName;
		strDID = SystemValue.deviceId;
		disPlaywidth = getWindowManager().getDefaultDisplay().getWidth();
		findView();
		InitParams();
		AudioBuffer = new CustomBuffer();
		audioPlayer = new AudioPlayer(AudioBuffer);
		// myvideoRecorder = new CustomVideoRecord(this, strDID);
		BridgeService.setPlayInterface(this);
		NativeCaller.StartPPPPLivestream(strDID, 10, 1);//确保不能重复start

		getCameraParams();
		dismissTopAnim = AnimationUtils.loadAnimation(this,
				R.anim.ptz_top_anim_dismiss);
		showTopAnim = AnimationUtils.loadAnimation(this,
				R.anim.ptz_top_anim_show);
		showAnim = AnimationUtils.loadAnimation(this,
				R.anim.ptz_otherset_anim_show);
		dismissAnim = AnimationUtils.loadAnimation(this,
				R.anim.ptz_otherset_anim_dismiss);
		
		myRender = new MyRender(playSurface);
		playSurface.setRenderer(myRender);
		myRender.setOnScreenShotListener(new OnScreenShot() {
			
			@Override
			public void onSucceed(String path) {
		        /*更新媒体库,扫描抓图文件*/
		        Uri data = Uri.parse("file://" + path);
		        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));				
		        showToast(getResources().getString(R.string.screenshot_successed) + path);
			}
			
			@Override
			public void onFailed(String info) {
				showToast(getResources().getString(R.string.screenshot_failed));
			}
		});

		// prompt user how to control ptz when first enter play
		SharedPreferences sharePreferences = getSharedPreferences("ptzcontrol",
				MODE_PRIVATE);
		isPTZPrompt = sharePreferences.getBoolean("ptzcontrol", false);
		if (!isPTZPrompt) {
			Editor edit = sharePreferences.edit();
			edit.putBoolean("ptzcontrol", true);
			edit.commit();
		}
		initExitPopupWindow2();
		brodCast = new MyBrodCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("drop");
		PlayActivity.this.registerReceiver(brodCast, filter);
	}

	private void InitParams() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		textosd.setText(strName);
	}

	private void StartAudio() {
		synchronized (this) {
			AudioBuffer.ClearAll();
			audioPlayer.AudioPlayStart();
			NativeCaller.PPPPStartAudio(strDID);
		}
	}

	private void StopAudio() {
		synchronized (this) {
			audioPlayer.AudioPlayStop();
			AudioBuffer.ClearAll();
			NativeCaller.PPPPStopAudio(strDID);
		}
	}

	protected void setResolution(int Resolution) {
		Log.d("tag", "setResolution resolution:" + Resolution);
		NativeCaller.PPPPCameraControl(strDID,16, Resolution);
	}

	private void findView() {
		//方向控制提示框
		initControlDailog();
		//视频渲染画面控件
		playSurface = (GLSurfaceView) findViewById(R.id.mysurfaceview);
		playSurface.setOnTouchListener(this);
		playSurface.setLongClickable(true);//确保手势识别正确工作
		
		button_back = (ImageButton) findViewById(R.id.login_top_back);
		imgUp = (ImageView) findViewById(R.id.imgup);
		imgDown = (ImageView) findViewById(R.id.imgdown);
		imgRight = (ImageView) findViewById(R.id.imgright);
		imgLeft = (ImageView) findViewById(R.id.imgleft);
		imgUp.setOnClickListener(this);
		imgDown.setOnClickListener(this);
		imgLeft.setOnClickListener(this);
		imgRight.setOnClickListener(this);
		button_back.setOnClickListener(this);
		progressView = (View) findViewById(R.id.progressLayout);
		textosd = (TextView) findViewById(R.id.textosd);
		osdView = (View) findViewById(R.id.osdlayout);
		ptzAudio = (ImageButton) findViewById(R.id.ptz_audio);
		ptzResolutoin = (Button) findViewById(R.id.ptz_resoluti);
		ptzTakePic = (ImageButton) findViewById(R.id.ptz_take_pic);
		ptzOtherSetAnimView = findViewById(R.id.ptz_othersetview_anim);
		ptzAudio.setOnClickListener(this);
		ptzResolutoin.setOnClickListener(this);
		ptzTakePic.setOnClickListener(this);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.top_bg);
		drawable = new BitmapDrawable(bitmap);
		drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		drawable.setDither(true);
		ptzOtherSetAnimView.setBackgroundDrawable(drawable);

		setScreen();
	}

	/**
	 * 0 竖屏，1横屏
	 */
	private int screenorientation = 0;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		// mBaseMatrix = new Matrix();
		// mSuppMatrix = new Matrix();
		// mDisplayMatrix = new Matrix();
		// videoViewStandard.setImageMatrix(mDisplayMatrix);
		findView();
		setScreen();

	}

	public void setScreen() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.i("info", "竖屏切换");
			screenorientation = 0;
		} else {
			Log.i("info", "横屏切换");
			screenorientation = 1;
		}
	}

	private boolean isDown = false;
	private boolean isSecondDown = false;
	private float x1 = 0;
	private float x2 = 0;
	private float y1 = 0;
	private float y2 = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (!isDown) {
			x1 = event.getX();
			y1 = event.getY();
			isDown = true;
		}
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			originalScale = getScale();
			break;
		case MotionEvent.ACTION_POINTER_UP:

			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs((x1 - x2)) < 25 && Math.abs((y1 - y2)) < 25)
			{

				if (resolutionPopWindow != null
						&& resolutionPopWindow.isShowing()) {
					resolutionPopWindow.dismiss();
				}

				if (mPopupWindowProgress != null
						&& mPopupWindowProgress.isShowing()) {
					mPopupWindowProgress.dismiss();
				}
				if (!isSecondDown) {
					if (!bProgress) {
						showTop();
						showBottom();
					}
				}
				isSecondDown = false;
			}
			x1 = 0;
			x2 = 0;
			y1 = 0;
			y2 = 0;
			isDown = false;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			isSecondDown = true;
			oldDist = spacing(event);
			if (oldDist > 10f) 
			{
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			x2 = event.getX();
			y2 = event.getY();

//			int midx = getWindowManager().getDefaultDisplay().getWidth() / 2;
//			int midy = getWindowManager().getDefaultDisplay().getHeight() / 2;
			if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 0f) {
					float scale = newDist / oldDist;
					Log.d("scale", "scale:" + scale);
					if (scale <= 2.0f && scale >= 0.2f) {
						mScale = originalScale * scale;
//						 zoomTo(originalScale * scale, midx, midy);
					}
				}
			}
		}

		return mGestureDetector.onTouchEvent(event);
	}

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	private int mode = NONE;
	private float oldDist;
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private PointF start = new PointF();
	private PointF mid = new PointF();
	float mMaxZoom = 2.0f;
	float mMinZoom = 0.3125f;
	float originalScale;
	float mScale = 1f;
	float baseValue;
	protected Matrix mBaseMatrix = new Matrix();
	protected Matrix mSuppMatrix = new Matrix();
	private Matrix mDisplayMatrix = new Matrix();
	private final float[] mMatrixValues = new float[9];

	protected void zoomTo(float scale, float centerX, float centerY)
	{
		Log.d("zoomTo", "zoomTo scale:" + scale);
//		playSurface.setScaleX(scale);
//		playSurface.setScaleY(scale);
		
		int width = playSurface.getWidth();
		ShowInfo.printLogW("_____getWidth____" + width);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				(int)(width * scale), (int)(scale * width * 3 / 4));
		lp.gravity = Gravity.CENTER;
		playSurface.setLayoutParams(lp);
		
//		if (scale > mMaxZoom) {
//			scale = mMaxZoom;
//		} else if (scale < mMinZoom) {
//			scale = mMinZoom;
//		}
//
//		float oldScale = getScale();
//		float deltaScale = scale / oldScale;
//		Log.d("deltaScale", "deltaScale:" + deltaScale);
//		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
	}

	protected Matrix getImageViewMatrix() {
		mDisplayMatrix.set(mBaseMatrix);
		mDisplayMatrix.postConcat(mSuppMatrix);
		return mDisplayMatrix;
	}

	protected float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	protected float getScale() {
		return getScale(mSuppMatrix);
	}

	protected float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(mMatrixValues);
		return mMatrixValues[whichValue];
	}

	private float spacing(MotionEvent event) {
		try {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		} catch (Exception e) {
		}
		return 0;
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.d("tag", "onDown");
		return false;
	}

	private final int MINLEN = 80;
	private Animation showTopAnim;
	private Animation dismissTopAnim;
	private boolean isPTZPrompt;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float x1 = e1.getX();
		float x2 = e2.getX();
		float y1 = e1.getY();
		float y2 = e2.getY();

		float xx = x1 > x2 ? x1 - x2 : x2 - x1;
		float yy = y1 > y2 ? y1 - y2 : y2 - y1;

		if (xx > yy)
		{
			if ((x1 > x2) && (xx > MINLEN))
			{// right
				if(!isControlDevice)
					new ControlDeviceTask(ContentCommon.CMD_PTZ_RIGHT).execute();
				
			} else if ((x1 < x2) && (xx > MINLEN)) {// left
				if(!isControlDevice)
				new ControlDeviceTask(ContentCommon.CMD_PTZ_LEFT).execute();
			}

		} else {
			if ((y1 > y2) && (yy > MINLEN))
			{// down
				if(!isControlDevice)
					new ControlDeviceTask(ContentCommon.CMD_PTZ_DOWN).execute();
			} else if ((y1 < y2) && (yy > MINLEN)) {// up
				if(!isControlDevice)
					new ControlDeviceTask(ContentCommon.CMD_PTZ_UP).execute();
			}

		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.ptz_take_pic:
			takePicpure();
			break;
		case R.id.login_top_back:
			bManualExit = true;
//			if (!bProgress) {
//				if (isTakeVideo == true) {
//					showToast(R.string.eixt_show_toast);
//				} else {
					showSureDialog();
//				}
//			}
			break;
		case R.id.imgup:
			if(!isControlDevice)
				new ControlDeviceTask(ContentCommon.CMD_PTZ_UP).execute();
			break;
		case R.id.imgdown:
			if(!isControlDevice)
				new ControlDeviceTask(ContentCommon.CMD_PTZ_DOWN).execute();
			break;
		case R.id.imgleft:
			if(!isControlDevice)
				new ControlDeviceTask(ContentCommon.CMD_PTZ_LEFT).execute();
			break;
		case R.id.imgright:
			if(!isControlDevice)
				new ControlDeviceTask(ContentCommon.CMD_PTZ_RIGHT).execute();
			break;
//		case R.id.ptz_hori_mirror:
//			if (isHorizontalMirror) {
//				ptzHoriMirror2.setBackgroundColor(0x00ffffff);
//				isHorizontalMirror = false;
//				NativeCaller.PPPPCameraControl(strDID, 5,
//						ContentCommon.CMD_PTZ_ORIGINAL);
//				Log.d("tag", "水平镜像还原：" + ContentCommon.CMD_PTZ_ORIGINAL);
//			} else {
//				isHorizontalMirror = true;
//				ptzHoriMirror2.setBackgroundColor(0xff0044aa);
//				NativeCaller.PPPPCameraControl(strDID, 5,
//						ContentCommon.CMD_PTZ_HORIZONAL_MIRROR);
//				Log.d("tag", "水平镜像：" + ContentCommon.CMD_PTZ_HORIZONAL_MIRROR);
//			}
//			break;
//		case R.id.ptz_vert_mirror:
//			if (isVerticalMirror) {
//				isVerticalMirror = false;
//				ptzVertMirror2.setBackgroundColor(0x00ffffff);
//				NativeCaller.PPPPCameraControl(strDID, 5,
//						ContentCommon.CMD_PTZ_ORIGINAL);
//				Log.d("tag", "垂直镜像还原：" + ContentCommon.CMD_PTZ_ORIGINAL);
//			} else {
//				isVerticalMirror = true;
//				ptzVertMirror2.setBackgroundColor(0xff0044aa);
//				NativeCaller.PPPPCameraControl(strDID, 5,
//						ContentCommon.CMD_PTZ_VERTICAL_MIRROR);
//				Log.d("tag", "垂直镜像：" + ContentCommon.CMD_PTZ_VERTICAL_MIRROR);
//			}
//			break;
//
//		case R.id.ptz_hori_tour:
//			if (isLeftRight) {
//				ptzHoriTour2.setBackgroundColor(0x000044aa);
//				isLeftRight = false;
//				NativeCaller.PPPPPTZControl(strDID,
//						ContentCommon.CMD_PTZ_LEFT_RIGHT_STOP);
//				Log.d("tag", "水平巡视停止:" + ContentCommon.CMD_PTZ_LEFT_RIGHT_STOP);
//			} else {
//				ptzHoriTour2.setBackgroundColor(0xff0044aa);
//				isLeftRight = true;
//				NativeCaller.PPPPPTZControl(strDID,
//						ContentCommon.CMD_PTZ_LEFT_RIGHT);
//				Log.d("tag", "水平巡视开始:" + ContentCommon.CMD_PTZ_LEFT_RIGHT);
//			}
//			break;
//		case R.id.ptz_vert_tour:
//			if (isUpDown) {
//				ptzVertTour2.setBackgroundColor(0x000044aa);
//				isUpDown = false;
//				NativeCaller.PPPPPTZControl(strDID,
//						ContentCommon.CMD_PTZ_UP_DOWN_STOP);
//				Log.d("tag", "垂直巡视停止:" + ContentCommon.CMD_PTZ_UP_DOWN_STOP);
//			} else {
//				ptzVertTour2.setBackgroundColor(0xff0044aa);
//				isUpDown = true;
//				NativeCaller.PPPPPTZControl(strDID,
//						ContentCommon.CMD_PTZ_UP_DOWN);
//				Log.d("tag", "垂直巡视开始:" + ContentCommon.CMD_PTZ_UP_DOWN);
//			}
//			break;
		case R.id.ptz_audio:
			dismissBrightAndContrastProgress();
			if (!isMcriophone) {
				if (bAudioStart) {
					Log.d("tag", "没有声音");
					bAudioStart = false;
					ptzAudio.setImageResource(R.drawable.ptz_audio_off);
					StopAudio();
				} else {
					Log.d("tag", "有声音");
					bAudioStart = true;
					ptzAudio.setImageResource(R.drawable.ptz_audio_on);
					StartAudio();
				}
			}
			break;
//		case R.id.ptz_brightness://亮度
//			if (mPopupWindowProgress != null
//					&& mPopupWindowProgress.isShowing()) {
//				mPopupWindowProgress.dismiss();
//				mPopupWindowProgress = null;
//			}
//			setBrightOrContrast(BRIGHT);
//			break;
//		case R.id.ptz_contrast://对比度
//			if (mPopupWindowProgress != null
//					&& mPopupWindowProgress.isShowing()) {
//				mPopupWindowProgress.dismiss();
//				mPopupWindowProgress = null;
//			}
//			setBrightOrContrast(CONTRAST);
//			break;
//		case R.id.ptz_default_set://亮度对比度复位
//			dismissBrightAndContrastProgress();
//			defaultVideoParams();
//			break;
		case R.id.ptz_resoluti:
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			int popupWidth = popupWindow_about.getContentView().getMeasuredWidth();
			int popupHeight = popupWindow_about.getContentView().getMeasuredHeight();
			popupWindow_about.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]+v.getWidth()/2)-popupWidth/2, location[1] - popupHeight);
//			popupWindow_about.showAtLocation(ptzResolutoin, Gravity.CENTER, 0, 0);
			break;
		case R.id.ptz_resolution_jpeg_qvga:
			dismissBrightAndContrastProgress();
			resolutionPopWindow.dismiss();
			nResolution = 1;
			setResolution(nResolution);
			Log.d("tag", "jpeg resolution:" + nResolution + " qvga");
			break;
		case R.id.ptz_resolution_jpeg_vga:
			dismissBrightAndContrastProgress();
			resolutionPopWindow.dismiss();
			nResolution = 0;
			setResolution(nResolution);
			Log.d("tag", "jpeg resolution:" + nResolution + " vga");
			break;
		case R.id.ptz_resolution_h264_qvga:
			dismissBrightAndContrastProgress();
			resolutionPopWindow.dismiss();
			ismax = false;
			ismiddle = false;
			ishigh = false;
			isp720 = false;
			isqvga1 = true;
			isvga1 = false;
			addReslution(stqvga1, isqvga1);
			nResolution = 5;
			setResolution(nResolution);
			break;
		case R.id.ptz_resolution_h264_vga:
			dismissBrightAndContrastProgress();
			resolutionPopWindow.dismiss();
			ismax = false;
			ismiddle = false;
			ishigh = false;
			isp720 = false;
			isqvga1 = false;
			isvga1 = true;
			addReslution(stvga1, isvga1);
			nResolution = 4;
			setResolution(nResolution);
			
			break;
		case R.id.ptz_resolution_h264_720p:
			dismissBrightAndContrastProgress();
			resolutionPopWindow.dismiss();
			ismax = false;
			ismiddle = false;
			ishigh = false;
			isp720 = true;
			isqvga1 = false;
			isvga1 = false;
			addReslution(stp720, isp720);
			nResolution = 3;
			setResolution(nResolution);
			break;
//		case R.id.ptz_playmode:
//			dismissBrightAndContrastProgress();
//			switch (playmode) {
//			case FULLSCREEN:
//				ptzPlayMode.setImageResource(R.drawable.ptz_playmode_enlarge);
//				ptzPlayMode
//						.setBackgroundResource(R.drawable.ptz_takepic_selector);
//				Log.d("tg", "magnify 1");
//				playmode = STANDARD;
//				break;
//			case MAGNIFY:
//				Log.d("tg", "STANDARD 2");
//				playmode = FULLSCREEN;
//				ptzPlayMode.setImageResource(R.drawable.ptz_playmode_standard);
//				ptzPlayMode
//						.setBackgroundResource(R.drawable.ptz_takepic_selector);
//				break;
//			case STANDARD:
//				Log.d("tg", "FULLSCREEN 3");
//				playmode = MAGNIFY;
//				ptzPlayMode
//						.setImageResource(R.drawable.ptz_playmode_fullscreen);
//				ptzPlayMode
//						.setBackgroundResource(R.drawable.ptz_takepic_selector);
//				break;
//			default:
//				break;
//			}
//			break;
		}
	}

	/**
	 * 截图
	 */
	private void takePicpure() {
		myRender.takePicture();
	}
	
	private void showToast(final String info){
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				ShowToast.show(getApplicationContext(), info);
			}
		});
	}
	  
	private void dismissBrightAndContrastProgress() {
		if (mPopupWindowProgress != null && mPopupWindowProgress.isShowing()) {
			mPopupWindowProgress.dismiss();
			mPopupWindowProgress = null;
		}
	}

	private void showBottom() {
		if (isUpDownPressed) {
			isUpDownPressed = false;
			ptzOtherSetAnimView.startAnimation(dismissAnim);
			ptzOtherSetAnimView.setVisibility(View.GONE);
		} else {
			isUpDownPressed = true;
			ptzOtherSetAnimView.startAnimation(showAnim);
			ptzOtherSetAnimView.setVisibility(View.VISIBLE);
		}
	}
	
	/*
	 *异步控制方向
	 */
	private class ControlDeviceTask extends AsyncTask<Void, Void, Integer>
	{
        private int type;
        public ControlDeviceTask(int type)
        {
        	this.type=type;
        }
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(type ==ContentCommon.CMD_PTZ_RIGHT)
			{
				control_item.setText(R.string.right);
			}
			else if(type ==ContentCommon.CMD_PTZ_LEFT)
			{
				control_item.setText(R.string.left);
			}
			else if(type ==ContentCommon.CMD_PTZ_UP)
			{
				control_item.setText(R.string.up);
			}
			else if(type ==ContentCommon.CMD_PTZ_DOWN)
			{
				control_item.setText(R.string.down);
			}
			if (controlWindow != null && controlWindow.isShowing())
				controlWindow.dismiss();
			
			if (controlWindow != null && !controlWindow.isShowing())
				controlWindow.showAtLocation(playSurface, Gravity.CENTER,0, 0);
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			isControlDevice = true;
			if(type == ContentCommon.CMD_PTZ_RIGHT)
			{
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_RIGHT);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_RIGHT_STOP);
			}
			else if(type ==ContentCommon.CMD_PTZ_LEFT)
			{
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_LEFT);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_LEFT_STOP);
			}
			else if(type ==ContentCommon.CMD_PTZ_UP)
			{
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_UP);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_UP_STOP);
			}
			else if(type ==ContentCommon.CMD_PTZ_DOWN)
			{
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_DOWN);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NativeCaller.PPPPPTZControl(strDID,ContentCommon.CMD_PTZ_DOWN_STOP);
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			isControlDevice = false;
			if (controlWindow != null && controlWindow.isShowing())
				controlWindow.dismiss();
		}
		
	}
	/*
	 * 上下左右提示框
	 */
	private void initControlDailog()
	{
		LayoutInflater inflater=LayoutInflater.from(this);
		View view=inflater.inflate(R.layout.control_device_view, null);
		control_item = (TextView) view.findViewById(R.id.textView1_play);
		controlWindow=new PopupWindow(view,FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
		controlWindow.setBackgroundDrawable(new ColorDrawable(0));
		controlWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				controlWindow.dismiss();
			}
		});
		controlWindow.setTouchInterceptor(new OnTouchListener() 
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_OUTSIDE) {
					controlWindow.dismiss();
				}
				return false;
			}
		});
	}
	
	/**
	 * 获取reslution
	 */
	public static Map<String, Map<Object, Object>> reslutionlist = new HashMap<String, Map<Object, Object>>();
	/**
	 * 增加reslution
	 */
	private void addReslution(String mess, boolean isfast)
	{
		if (reslutionlist.size() != 0) {
			if (reslutionlist.containsKey(strDID)) {
				reslutionlist.remove(strDID);
			}
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(mess, isfast);
		reslutionlist.put(strDID, map);
	}
	private void getReslution() {
		if (reslutionlist.containsKey(strDID)) {
			Map<Object, Object> map = reslutionlist.get(strDID);
			if (map.containsKey("qvga")) {
				isqvga = true;
			} else if (map.containsKey("vga")) {
				isvga = true;
			} else if (map.containsKey("qvga1")) {
				isqvga1 = true;
			} else if (map.containsKey("vga1")) {
				isvga1 = true;
			} else if (map.containsKey("p720")) {
				isp720 = true;
			} else if (map.containsKey("high")) {
				ishigh = true;
			} else if (map.containsKey("middle")) {
				ismiddle = true;
			} else if (map.containsKey("max")) {
				ismax = true;
			}
		}
	}
	/*
	 * @param type
	 * 亮度饱和对比度
	 */
	private void setBrightOrContrast(final int type)
	{

		if (!bInitCameraParam) {
			return;
		}
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.brightprogress, null);
		SeekBar seekBar = (SeekBar) layout.findViewById(R.id.brightseekBar1);
		seekBar.setMax(255);
		switch (type) {
		case BRIGHT:
			seekBar.setProgress(nBrightness);
			break;
		case CONTRAST:
			seekBar.setProgress(nContrast);
			break;
		default:
			break;
		}
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				switch (type) {
				case BRIGHT:// 亮度
					nBrightness = progress;
					NativeCaller.PPPPCameraControl(strDID, BRIGHT, nBrightness);
					break;
				case CONTRAST:// 对比度
					nContrast = progress;
					NativeCaller.PPPPCameraControl(strDID, CONTRAST, nContrast);
					break;
				default:
					break;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int progress,
					boolean arg2) {

			}
		});
		mPopupWindowProgress = new PopupWindow(layout, width / 2, 180);
		mPopupWindowProgress.showAtLocation(findViewById(R.id.play),
				Gravity.TOP, 0, 0);

	}

	private MyRender myRender = null;
	// private GLSurfaceView myGlSurfaceView = null;
	private ImageView imgUp = null;
	private ImageView imgDown = null;
	private ImageView imgRight = null;
	private ImageView imgLeft = null;

	@Override
	protected void onDestroy() {
		NativeCaller.StopPPPPLivestream(strDID);
		NativeCaller.StopPPPP(strDID);
		StopAudio();
		if (myRender != null) {
			myRender.destroyShaders();
		}
		if (brodCast != null) {
			unregisterReceiver(brodCast);
		}
		Log.d("tag", "StopPPPP _________ PlayActivity onDestroy");
		super.onDestroy();
	}

	/***
	 * BridgeService callback
	 * 
	 * **/
	@Override
	public void callBackCameraParamNotify(String did, int resolution,
			int brightness, int contrast, int hue, int saturation, int flip) {
	
		nBrightness = brightness;
		nContrast = contrast;
		nResolution = resolution;
		bInitCameraParam = true;
	}

	/***
	 * BridgeService callback 视频数据流回调
	 * 
	 * **/
	@Override
	public void callBaceVideoData(byte[] videobuf, int h264Data, int len,int width, int height) {
//		ShowInfo.printLogW("底层返回数据", "____videobuf:"+videobuf+"--"+"h264Data"+h264Data+"len"+len+"width"+width+"height"+height);
		if (!bDisplayFinished)
			return;
		bDisplayFinished = false;
		videodata = videobuf;
		videoDataLen = len;
		Message msg = new Message();
		if (h264Data == 1) { // H264
			nVideoWidths = width;
			nVideoHeights = height;
			if (isTakepic) {
				isTakepic = false;
				byte[] rgb = new byte[width * height * 2];
				NativeCaller.YUV4202RGB565(videobuf, rgb, width, height);
				ByteBuffer buffer = ByteBuffer.wrap(rgb);
				mBmp = Bitmap
						.createBitmap(width, height, Bitmap.Config.RGB_565);
				mBmp.copyPixelsFromBuffer(buffer);
				ShowInfo.printLogW("_______createBitmap null_____");
				// takePicture(mBmp);
			}
			isH264 = true;
			msg.what = 1;
		} else { // MJPEG
			msg.what = 2;
		}
		mHandler.sendMessage(msg);
	}

	/***
	 * BridgeService callback
	 * 
	 * **/
	@Override
	public void callBackMessageNotify(String did, int msgType, int param) {
		if (bManualExit)
			return;

		if (msgType == ContentCommon.PPPP_MSG_TYPE_STREAM) {
			return;
		}

		if (msgType != ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS) {
			return;
		}

		if (!did.equals(strDID)) {
			return;
		}

		Message msg = new Message();
		msg.what = 1;
		msgHandler.sendMessage(msg);
	}

	/***
	 * BridgeService callback
	 * 
	 * **/
	@Override
	public void callBackAudioData(byte[] pcm, int len) {
		if (!audioPlayer.isAudioPlaying()) {
			return;
		}
		CustomBufferHead head = new CustomBufferHead();
		CustomBufferData data = new CustomBufferData();
		head.length = len;
		head.startcode = AUDIO_BUFFER_START_CODE;
		data.head = head;
		data.data = pcm;
		AudioBuffer.addData(data);
	}

	/***
	 * BridgeService callback
	 * 
	 * **/
	@Override
	public void callBackH264Data(byte[] h264, int type, int size) {
	}

}
