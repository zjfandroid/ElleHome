package elle.home.residemenu;

import elle.home.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResideSenseItem extends LinearLayout {

	private ImageView iv;
	private TextView tv;
	private ImageView iv2;
	private LinearLayout bk;
	
	public ResideSenseItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initViews(context);
	}

	public void initViews(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.reside_sense, this);
		this.iv = (ImageView)findViewById(R.id.resideSenseImage);
		this.tv = (TextView)findViewById(R.id.resideSenseText);
		this.iv2= (ImageView)findViewById(R.id.reside_item_next);
		bk = (LinearLayout)this.findViewById(R.id.reside_item_bk);
	}
	
	public void setView(int icon,String str){
		this.iv.setImageResource(icon);
		this.tv.setText(str);
	}
	
	public void setView2(int icon,String str){
		this.iv.setImageResource(icon);
		this.tv.setText(str);
		this.iv2.setAlpha(0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
//		super.onTouchEvent(event);
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//Log.d(TAG,"touch down now!");
			bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.reside_item_pressed));
		}else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
			bk.setBackgroundColor(getResources().getColor(R.color.transparent));
		}
		return super.onTouchEvent(event);
//		return false;
	}
	
}
