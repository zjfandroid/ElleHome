package elle.home.shake;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import elle.home.app.smart.R;
import elle.home.database.AllDevInfo;
import elle.home.database.OneDev;
import elle.home.partactivity.UMengConstant;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.LuminaireControlPacket;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.ShowInfo;

public class ShakeService extends Service{

	private SensorManager sensorManager;
	private Vibrator vibrator;
	private SoundPool soundPool ;
	private int soundId_on;
	private int soundId_off;

//	private UdpThread udpThread = null;
	//udp通道
	public DatagramSocket dataSocket;
	
	private static final int DAYTIME_BEGIN = 7;
	private static final int DAYTIME_END = 19;
	private static final String TAG = "ShakeService";
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private AllDevInfo mAllDevInfo ;

	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0); 
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100); 
		soundId_on = soundPool.load(this, R.raw.light_on, 1); 
		soundId_off = soundPool.load(this, R.raw.light_off, 1); 
		if (sensorManager != null) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
//		udpThread = new UdpThread();
//		udpThread.setDataList(new ProtocolDataList());
//		udpThread.startThread();
		mAllDevInfo = new AllDevInfo(ShakeService.this);
		acquireWakeLock();
		
		try {
			dataSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		ShowInfo.printLogW("__________shake service onCreate___________");
	}
	
	private WakeLock wakeLock = null;
	 /** 
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行 
     */  
    private void acquireWakeLock() {  
        if (null == wakeLock) {  
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);  
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK , getClass()  
                    .getCanonicalName());  
            if (null != wakeLock) {  
                Log.i(TAG, "call acquireWakeLock");  
                wakeLock.acquire();  
            }  
        }  
    }  
  
    // 释放设备电源锁  
    private void releaseWakeLock() {  
        if (null != wakeLock && wakeLock.isHeld()) {  
            Log.i(TAG, "call releaseWakeLock");  
            wakeLock.release();  
            wakeLock = null;  
        }  
    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (sensorManager != null) {// 取消监听器
			sensorManager.unregisterListener(sensorEventListener);
		}
//		if(udpThread != null){
//			udpThread.stopThread();
//		}
		releaseWakeLock();
		
		if(null != dataSocket){
			dataSocket.disconnect();
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				
				@Override
				public void run() {
					ShowInfo.printLogW("__________udp thread stop___________");
					dataSocket.close();
				}}, 500);
		}
		
		ShowInfo.printLogW("__________shake service onDestroy___________");
	}
	
	private int mShakeCounter = 0;
	private static final int RESPONSE_DELAY = 800;
	private static final int RESPONSE_COUNTER = 3;
	private static final int VIB_TIMER = 600;
	private long mLastShakeTime = 0;
	
	public void onShake(){
		if(Math.abs(mLastShakeTime - System.currentTimeMillis()) > RESPONSE_DELAY){
			mShakeCounter = 0;
		}
		mShakeCounter++;
//		vibrator.vibrate(15);
		if(mShakeCounter >= RESPONSE_COUNTER){
			handler.removeCallbacks(response);
			handler.postDelayed(response, RESPONSE_DELAY);
		}
		mLastShakeTime = System.currentTimeMillis();
	}
	
	/**
	 * 动作执行
	 */
	Handler handler = new Handler();
	Runnable response = new  Runnable() {
		
		@Override
		public void run() {
			onExecControl();
		}
	};
	
	public void onExecControl(){
		mShakeCounter = 0;
//		Toast.makeText(ShakeService.this, "检测到摇晃，执行操作！",
//				Toast.LENGTH_SHORT).show();
		
		if(isNetworkEnabled()){
			ShowInfo.printLogW("network enabled");
			mAllDevInfo.getAllDev();
			if(isDayTime()){
				ShowInfo.printLogW("day time");
				if(SaveDataPreferences.loadBoolean(this, PublicDefine.SHAKE_DAY, true)){
					ShowInfo.printLogW("day on");
					vibrator.vibrate(VIB_TIMER);
					sendPacket();
				}else{
					ShowInfo.printLogW("day off");
				}
			}else{
				ShowInfo.printLogW("night time");
				if(SaveDataPreferences.loadBoolean(this, PublicDefine.SHAKE_NIGHT, true)){
					ShowInfo.printLogW("night on");
					vibrator.vibrate(VIB_TIMER);
					sendPacket();
				}else{
					ShowInfo.printLogW("night off");
				}
			}
//		}else{
//			ShowInfo.printLogW("network disnabled");
		}
		
	}
	
	private void sendPacket(){
		boolean isOn = SaveDataPreferences.loadBoolean(this, "LIGHT_ON", true);
		boolean isNeedSound = false;
		
		if(isOn){
			MobclickAgent.onEvent(this, UMengConstant.EVENT_ID_SHAKE_CLOSE_LIGHT);
		}else{
			MobclickAgent.onEvent(this, UMengConstant.EVENT_ID_SHAKE_OPEN_LIGHT);
		}
		
		for(OneDev dev : mAllDevInfo.alldevinfo){
			if(checkFunction(PublicDefine.CONFIG_SHAKE_ON, dev.function)){
				ShowInfo.printLogW("turn off " + isOn);
				isNeedSound = true;
				
				DatagramPacket mDatagramPacket = null;
				if(PublicDefine.TypeLight == dev.type){
					mDatagramPacket = getLightPacket(isOn, dev);
				}else{
					mDatagramPacket = getLuminairePacket(isOn, dev);
				}
				
				final DatagramPacket tmp = mDatagramPacket;
				new Thread(){
					public void run() {
						try {
							dataSocket.send(tmp);
							dataSocket.send(tmp);
							dataSocket.send(tmp);
						} catch (IOException e) {
							e.printStackTrace();
						}
					};
				}.start();
//				udpThread.getProtocolDataList().addOnePacketToSend(packet);
//				udpThread.getProtocolDataList().addOnePacketToSend(packet);
//				udpThread.getProtocolDataList().addOnePacketToSend(packet);
			}
		}
		
		if(isNeedSound){
			playSound(isOn);
		}
		SaveDataPreferences.saveBoolean(this, "LIGHT_ON", !isOn);
	}

	private DatagramPacket getLuminairePacket(boolean isOn, OneDev dev) {
		LuminaireControlPacket packet = new LuminaireControlPacket(null,PublicDefine.LocalUdpPort);
		packet.setImportance(BasicPacket.ImportHigh);
		try {
			packet.setIp(InetAddress.getByName("255.255.255.255"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
		
		if(isOn){
			packet.lightClose(DataExchange.longToEightByte(dev.mac), null);
		}else{
			packet.lightOpen(DataExchange.longToEightByte(dev.mac), null);
		}
		
		return packet.getUdpData();
	}

	private DatagramPacket getLightPacket(boolean isOn, OneDev dev) {
		LightControlPacket packet = new LightControlPacket(null,PublicDefine.LocalUdpPort);
		packet.setImportance(BasicPacket.ImportHigh);
		try {
			packet.setIp(InetAddress.getByName("255.255.255.255"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
		
		if(isOn){
			packet.lightClose(DataExchange.longToEightByte(dev.mac), null);
		}else{
			packet.lightOpen(DataExchange.longToEightByte(dev.mac), null);
		}
		
		return packet.getUdpData();
	}
	
	private boolean checkFunction(String key , String function){
		if(function != null && function.contains(key)){
			return true;
		}
		return false;
	}
	
	public boolean isDayTime(){
		Calendar calendar = Calendar.getInstance();
		int dayHour = calendar.get(Calendar.HOUR_OF_DAY);

		ShowInfo.printLogW("dayHour:"+dayHour);
		if(dayHour >= DAYTIME_BEGIN && dayHour <DAYTIME_END){ 
			return true;
		}
		return false;
	}
	
	private void playSound(boolean isOn){
		AudioManager mgr = (AudioManager)ShakeService.this.getSystemService(ShakeService.this.AUDIO_SERVICE);   
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		float volume = streamVolumeCurrent / streamVolumeMax; 
		ShowInfo.printLogW("volume:"+volume);
		if(isOn){
			soundPool.play(soundId_off,  volume,volume,1, 0, 1.0f); 
		}else{
			soundPool.play(soundId_on,  volume,volume,1, 0, 1.0f); 
		}
	}
	
	private boolean isNetworkEnabled(){
		final ConnectivityManager connManager = (ConnectivityManager) ShakeService.this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo info = connManager.getActiveNetworkInfo();
		
		if (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 重力感应监听
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
//			int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
			int medumValue = 19;
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
					|| Math.abs(z) > medumValue) {
				onShake();
			}		
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

}
