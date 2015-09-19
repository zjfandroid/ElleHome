package chrisrenke.elle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jason on 15/8/21.
 */
public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    public void doCloseClick(View view){
        finish();
    }
}
