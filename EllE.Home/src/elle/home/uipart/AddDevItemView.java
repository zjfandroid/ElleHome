package elle.home.uipart;

import elle.home.app.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDevItemView extends LinearLayout {

	public String TAG = "AddDevItemView";
	
	public static final byte ConfigDevType = 1;
	public static final byte AddDevType = 0;
	
	private ImageView ivIcon;
	private TextView tvmac;
	private ImageButton ibadd;
	private byte type;
	public Context xcontext;
	
	public String mac;
	
	OnAddDev listener;
	
	AddDevItemView self;
	
	FrameLayout bk;
	
	boolean isWifi;
	
	public void setIsWifi(boolean tmp){
		isWifi = tmp;
	}
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0:
				if(isWifi){
					ibadd.setBackgroundDrawable(xcontext.getResources().getDrawable(R.drawable.config_dev_wifi_btn));
				}else{
					ibadd.setBackgroundDrawable(xcontext.getResources().getDrawable(R.drawable.add_dev_add_icon));
				}
				break;
			case 1:
				if(isWifi){
					ibadd.setBackgroundDrawable(xcontext.getResources().getDrawable(R.drawable.config_dev_wifi_btn_press));
				}else{
					ibadd.setBackgroundDrawable(xcontext.getResources().getDrawable(R.drawable.add_dev_add_icon_pressed));
				}
				break;
			}
		}
		
	};
	
	public AddDevItemView(Context context){
		super(context);
		initViews(context);
	}
	
	public AddDevItemView(Context context,int icon,String mac,int addicon){
		super(context);
		this.xcontext = context;
		initViews(context);
		this.mac = mac;
		this.ivIcon.setImageResource(icon);
		this.tvmac.setText(mac);
		//this.ibadd.setImageResource(addicon);
		ibadd.setBackgroundDrawable(this.getResources().getDrawable(addicon));
		isWifi = true;
	}
	
	public AddDevItemView(Context context,int icon,String mac,int addicon,byte type){
		super(context);
		initViews(context);
		this.mac = mac;
		this.ivIcon.setImageResource(icon);
		this.tvmac.setText(mac);
		ibadd.setBackgroundDrawable(this.getResources().getDrawable(addicon));
		this.type = type;
		isWifi = false;
	}
	
	public AddDevItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initViews(context);
	}

	private void initViews(Context context){
		self = this;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.add_dev_item, this);
		this.ivIcon = (ImageView)this.findViewById(R.id.ivIcon);
		this.tvmac = (TextView)this.findViewById(R.id.tvmac);
		this.ibadd = (ImageButton)this.findViewById(R.id.ibadd);
		this.bk = (FrameLayout)this.findViewById(R.id.add_dev_item_bk);
		this.ibadd.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				switch(event.getAction()){
				case MotionEvent.ACTION_UP:
					
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
					
					if(listener!=null){
						listener.addone(self);
					}
					break;
				case MotionEvent.ACTION_DOWN:
					
					Message xmsg = new Message();
					xmsg.what = 1;
					handler.sendMessage(xmsg);
					
					break;
				case MotionEvent.ACTION_CANCEL:
					break;
				}
				
				return true;
			}
		});
		
		this.bk.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_dev_bk_down));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_dev_bk));
					break;
				}
				return true;
			}
		});
	}
	
	public void setListener(OnAddDev a){
		this.listener = a;
	}
	
	public void setcustom(int icon,String mac,int addicon){
		this.ivIcon.setImageResource(icon);
		this.tvmac.setText(mac);
		this.ibadd.setImageResource(addicon);
	}
	
	public interface OnAddDev{
		public void addone(AddDevItemView a);
	}
	
}
