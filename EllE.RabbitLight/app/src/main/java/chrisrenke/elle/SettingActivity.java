package chrisrenke.elle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jason on 15/8/15.
 */
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void doCloseClick(View view){
        finish();
    }
}
