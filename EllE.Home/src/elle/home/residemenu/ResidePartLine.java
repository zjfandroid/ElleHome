package elle.home.residemenu;

import elle.home.app.smart.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ResidePartLine extends LinearLayout {

	public ResidePartLine(Context context){
		super(context);
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.reside_part_line));
		
	}
	
	public ResidePartLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.reside_part_line));
	}

}
