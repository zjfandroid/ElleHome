package elle.home.partactivity;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity implements UMengConstant{

	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	public void UMeng_OnEvent(String eventId){
		MobclickAgent.onEvent(this, eventId);
	}
	
	public void UMeng_OnEvent(String eventId , HashMap<String, String> map){
		MobclickAgent.onEvent(this, eventId , map);
	}
	
	public void UMeng_OnEvent(String eventId , String key , String value){
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(key, value);
		MobclickAgent.onEvent(this, eventId , map);
	}
	
	public void UMeng_OnEvent(String eventId , String key , int resid){
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(key, this.getString(resid));
		MobclickAgent.onEvent(this, eventId , map);
	}
	
	public void UMeng_OnEvent(String eventId , String key1 , String value1 ,String key2 , String value2){
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(key1, value1);
		map.put(key2, value2);
		MobclickAgent.onEvent(this, eventId , map);
	}
	
	public void UMeng_OnEvent(String eventId , String key1 , String value1 ,String key2 , String value2 ,String key3 , String value3){
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(key1, value1);
		map.put(key2, value2);
		map.put(key3, value3);
		MobclickAgent.onEvent(this, eventId , map);
	}
	
	public void UMeng_OnEventValue(String eventId , HashMap<String, String> map , int duration){
		MobclickAgent.onEventValue(this, eventId , map , duration);
	}
	
	public void UMeng_OnEventValue(String eventId ,int duration){
		MobclickAgent.onEventValue(this, eventId , new HashMap<String, String>() , duration);
	}
	
	public void UMeng_ReportError(String error){
		MobclickAgent.reportError(this, error);
	}
	
	public void UMeng_ReportError(Throwable error){
		MobclickAgent.reportError(this, error);
	}

}
