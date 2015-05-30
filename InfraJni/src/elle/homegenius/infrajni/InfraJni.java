package elle.homegenius.infrajni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class InfraJni extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infra_jni);
		
		InfraNative xx = new InfraNative();
		xx.test();
		Log.d("hello ndk","num:"+xx.testInt());
		Log.d("hello ndk","get brand name"+xx.getAirOneBrandNameById(2, 1));
		byte[] result = xx.getAirCommandByBrand(1,2,InfraNative.AIR_TEMP_19, InfraNative.AIR_FLOW_RATE_AUTO, 
				InfraNative.AIR_FLOW_MANUAL_UP, InfraNative.AIR_FLOW_AUTO_OFF, 
				InfraNative.AIR_ONOFF_ON, InfraNative.AIR_KEY_ONOFF, InfraNative.AIR_MODEL_COLD);
		
		StringBuffer sb = new StringBuffer();
		for (byte b:result)
	    {
	        sb.append(String.format("%x", b));
	    }
		Log.d("hello ndk","result:"+sb.toString());

		
	}
}
