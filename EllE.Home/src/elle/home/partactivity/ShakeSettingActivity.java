package elle.home.partactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import elle.home.app.smart.R;
import elle.home.publicfun.PublicDefine;
import elle.home.shake.ShakeService;
import elle.home.uipart.SilderButton;
import elle.home.uipart.SilderButton.OnOff;
import elle.home.utils.SaveDataPreferences;

/**
 * 摇一摇设置
 * @author jason
 *
 */
public class ShakeSettingActivity extends BaseActivity{

	ImageButton btnback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_shake_config);
		btnback = (ImageButton) this.findViewById(R.id.title_btn_left);
		btnback.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
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
		
		boolean isDay = SaveDataPreferences.loadBoolean(this, PublicDefine.SHAKE_DAY, true);
		boolean isNight = SaveDataPreferences.loadBoolean(this, PublicDefine.SHAKE_NIGHT, true);

		setSliderButton(R.id.silderButtonDay, PublicDefine.SHAKE_DAY, isDay);
		setSliderButton(R.id.silderButtonNight, PublicDefine.SHAKE_NIGHT, isNight);
	}

	private void setSliderButton(final int id, final String key, boolean isOn) {
		SilderButton dayButton = (SilderButton) findViewById(id);
		dayButton.setOnOff(isOn);
		dayButton.setListener(new OnOff() {
			
			@Override
			public void onoff(boolean tmp) {
				if(id == R.id.silderButtonDay){
					if(tmp){
						UMeng_OnEvent(EVENT_ID_CLICK_DAYTIME_SETTING, KEY_ONOFF, R.string.plug_status_on);
					}else{
						UMeng_OnEvent(EVENT_ID_CLICK_DAYTIME_SETTING, KEY_ONOFF, R.string.plug_status_off);
					}
				}else{
					if(tmp){
						UMeng_OnEvent(EVENT_ID_CLICK_NIGHT_SETTING, KEY_ONOFF, R.string.plug_status_on);
					}else{
						UMeng_OnEvent(EVENT_ID_CLICK_NIGHT_SETTING, KEY_ONOFF, R.string.plug_status_off);
					}
				}
				SaveDataPreferences.saveBoolean(getApplicationContext(), key, tmp);
			}
			
			@Override
			public void clickUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void clickDown() {
				// TODO Auto-generated method stub
				
			}
		});		
	}

}
