package elle.home.uipart;

import elle.home.app.R;
import elle.home.database.SceneData;
import elle.home.publicfun.PublicDefine;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ManageSceneItem extends LinearLayout {

	public SceneData scenedata;
	private Context mContext;
	
	ImageView manageSceneItemLogo;
	TextView manageSceneItemTv;
	public Button manageSceneDeleteBtn;
	boolean btnVisable;
	
	public ManageSceneItem(Context context,SceneData scenedata) {
		super(context);
		this.mContext = context;
		this.scenedata = scenedata;
		initview(context);
	}
	
	private void initview(Context context){
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.manage_scene_item, this);
		
		this.manageSceneItemLogo = (ImageView)this.findViewById(R.id.manage_scene_item_iv);
		this.manageSceneItemTv = (TextView)this.findViewById(R.id.manage_scene_item_tv);
		
		this.manageSceneItemLogo.setImageDrawable(getResources().getDrawable(PublicDefine.getManageSceneIcon(scenedata.sceneicon)));
		this.manageSceneItemTv.setText(scenedata.sceneName);
		
		this.manageSceneDeleteBtn = (Button)this.findViewById(R.id.manage_scene_delete_btn);
		btnVisable = false;
		manageSceneDeleteBtn.setVisibility(View.INVISIBLE);
	}
	
	public void togBtn(){
		if(btnVisable==false){
			btnVisable = true;
			manageSceneDeleteBtn.setVisibility(View.VISIBLE);
		}else{
			btnVisable = false;
			manageSceneDeleteBtn.setVisibility(View.INVISIBLE);
		}		
	}
	
	public void hideBtn(){
		btnVisable = false;
		manageSceneDeleteBtn.setVisibility(View.INVISIBLE);
	}	

}
