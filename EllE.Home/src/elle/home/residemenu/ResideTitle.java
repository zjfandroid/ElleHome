package elle.home.residemenu;

import elle.home.app.smart.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResideTitle extends LinearLayout {

	private ImageView iv;
	private TextView  tv;
	
	public ResideTitle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initViews(context);
	}
	
	public void initViews(Context context){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.reside_title, this);
		iv = (ImageView)findViewById(R.id.resideImageTitle);
		tv = (TextView)findViewById(R.id.resideTextTitle);
	}
	
	public void setView(int icon,String name){
		this.iv.setImageResource(icon);
		this.tv.setText(name);
	}
	
	

}
