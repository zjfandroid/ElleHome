package elle.home.uipart;

import elle.home.app.R;
import elle.home.database.DevLocationInfo;
import elle.home.publicfun.PublicDefine;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SceneItemTitle extends LinearLayout {

	DevLocationInfo locationinfo;
	private ImageView scene_title_iv;
	private TextView scene_title_tv;
	
	public SceneItemTitle(Context context,DevLocationInfo locationinfo) {
		super(context);
		// TODO Auto-generated constructor stub
		this.locationinfo = locationinfo;
		initview(context);
	}
	
	public void initview(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.scene_item_title, this);
		scene_title_iv = (ImageView)this.findViewById(R.id.scene_title_iv);
		scene_title_tv = (TextView)this.findViewById(R.id.scene_title_tv);
		
		this.scene_title_iv.setImageResource(PublicDefine.getResideLocatIcon(locationinfo.locaticon));
		if(locationinfo.locatname!=null)
			this.scene_title_tv.setText(locationinfo.locatname);
		else
			this.scene_title_tv.setText("未输入地点");
		
	}
	

}
