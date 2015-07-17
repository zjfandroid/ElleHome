package elle.home.app.infra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.publicfun.PublicDefine;

public class InfraActivity extends BaseActivity {

	public String TAG = "InfraActivity";
	
	private long devmac;
	private OneDev dev;
	public int connectStatus;
	
	ImageButton backbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_infra);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		
		dev = new OneDev();
		dev.getFromDatabase(devmac, this);
		
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					
					break;
				case MotionEvent.ACTION_UP:
					finish();
					break;
				}
				return true;
			}
		});
	}
	
	private void goBradListActivity(byte type){
		Intent intent = new Intent(this, InfraBrandListActivity.class);
		intent.putExtra("type", type);
		
		intent.putExtra("mac", dev.mac);
		intent.putExtra("connect", dev.getConnectStatus());
		intent.putExtra("devname", dev.devname);
		this.startActivity(intent);
	}
	
	public void doClick(View v){
		byte type = PublicDefine.TypeInfraAir;
		
		switch (v.getId()) {
		case R.id.btn_tv:
			type = PublicDefine.TypeInfraTv;
			break;
			
		case R.id.btn_tvbox:
		case R.id.btn_fan:

		default:
			break;
		}
		
		goBradListActivity(type);
	}
}
