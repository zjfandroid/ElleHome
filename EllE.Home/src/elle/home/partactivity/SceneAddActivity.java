package elle.home.partactivity;

import java.util.ArrayList;
import java.util.HashMap;

import elle.home.Fragment.LocationDevFragment.GridAdapter;
import elle.home.Fragment.LocationDevFragment.GridAdapter.ViewHolder;
import elle.home.app.R;
import elle.home.app.R.id;
import elle.home.app.R.layout;
import elle.home.database.AllScene;
import elle.home.database.DevLocationInfo;
import elle.home.publicfun.PublicDefine;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SceneAddActivity extends BaseActivity {

	public String TAG = "SceneAddActivity";
	
	private EditText scenename;
	private ImageButton addscenebtn;
	private Context mcontext;
	
	//返回按钮
	ImageButton backbtn;
	//图案选择
	GridView gridview;
	GridAdapter gridAdapter;
	//选中的场景图片
	private int iconChose = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_scene_add);
		mcontext = this;
		this.scenename = (EditText)this.findViewById(R.id.addSceneNameEdit);
		this.addscenebtn = (ImageButton)this.findViewById(R.id.addSceneBtn);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mcontext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		addscenebtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					addscenebtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_signal_dev_add_press));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					addscenebtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_signal_dev_add));
					if(scenename.getText().length()==0){
						Log.d(TAG,"输入场景名字啊~");
						Toast.makeText(mcontext, getResources().getString(R.string.tips_bename_first), Toast.LENGTH_SHORT).show();
					}else{
						//这里需要判断一下是否已经添加过同一个名字
						AllScene allscene = new AllScene(mcontext);
						allscene.aadSceneName(scenename.getText().toString(), getDefineLogoByClick());
						
						Toast.makeText(mcontext, getResources().getString(R.string.tips_add_succeed), Toast.LENGTH_SHORT).show();
						setResult(5);
						finish();
					}
					break;
				}
				return true;
			}
		});
		
		gridview = (GridView)this.findViewById(R.id.scene_grid_view);
		ArrayList<HashMap<String,Object>> gridSceneItems = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		map1.put("logo",PublicDefine.ResideIconBackHome);
		map1.put("text", getResources().getString(R.string.scene_behome));
		gridSceneItems.add(map1);
		
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		map2.put("logo",PublicDefine.ResideIconDinner);
		map2.put("text", getResources().getString(R.string.scene_dinner));
		gridSceneItems.add(map2);
		
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		map3.put("logo",PublicDefine.ResideIconGoodNight);
		map3.put("text", getResources().getString(R.string.scene_goodnight));
		gridSceneItems.add(map3);
		
		HashMap<String,Object> map4 = new HashMap<String,Object>();
		map4.put("logo",PublicDefine.ResideIconLeaveHome);
		map4.put("text", getResources().getString(R.string.scene_wentout));
		gridSceneItems.add(map4);
		
		HashMap<String,Object> map5 = new HashMap<String,Object>();
		map5.put("logo",PublicDefine.ResideIconMovie);
		map5.put("text", getResources().getString(R.string.scene_movie));
		gridSceneItems.add(map5);
		
		HashMap<String,Object> map6 = new HashMap<String,Object>();
		map6.put("logo",PublicDefine.ResideIconReading);
		map6.put("text", getResources().getString(R.string.scene_reading));
		gridSceneItems.add(map6);
		
		HashMap<String,Object> map7 = new HashMap<String,Object>();
		map7.put("logo",PublicDefine.ResideIconSport);
		map7.put("text", getResources().getString(R.string.scene_sport));
		gridSceneItems.add(map7);
		
		HashMap<String,Object> map8 = new HashMap<String,Object>();
		map8.put("logo",PublicDefine.ResideIconTalking);
		map8.put("text", getResources().getString(R.string.scene_meeting));
		gridSceneItems.add(map8);
		
		HashMap<String,Object> map9 = new HashMap<String,Object>();
		map9.put("logo",PublicDefine.ResideIconWorking);
		map9.put("text", getResources().getString(R.string.scene_working));
		gridSceneItems.add(map9);
		
		gridAdapter = new GridAdapter(this,gridSceneItems);
		gridview.setAdapter(gridAdapter);
		
		gridview.setOnItemClickListener(new ItemClickListener());
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	private int getDefineLogoByClick(){
		switch(iconChose){
		case 0:
			return PublicDefine.ResideIconBackHome;
		case 1:
			return PublicDefine.ResideIconDinner;
		case 2:
			return PublicDefine.ResideIconGoodNight;
		case 3:
			return PublicDefine.ResideIconLeaveHome;
		case 4:
			return PublicDefine.ResideIconMovie;
		case 5:
			return PublicDefine.ResideIconReading;
		case 6:
			return PublicDefine.ResideIconSport;
		case 7:
			return PublicDefine.ResideIconTalking;
		case 8:
			return PublicDefine.ResideIconWorking;
		}
		
		return PublicDefine.ResideIconGoodNight;
	}

	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			iconChose = position;
			
			for(int i=0;i<parent.getChildCount();i++){
				ViewHolder holder = (ViewHolder) parent.getChildAt(i).getTag();
				if(iconChose==i){
					//Log.d(TAG,"iconChose == i ->set blue");
					holder.tv.setTextColor(mcontext.getResources().getColor(R.color.lightseagreen));
					scenename.setText(holder.tv.getText());
				}else{
					holder.tv.setTextColor(mcontext.getResources().getColor(R.color.whitesmoke));
				}
			}
			
			//Log.d(TAG,"click count:"+iconChose+" view num:"+parent.getChildCount());
		}
		
	};
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public class GridAdapter extends BaseAdapter{

		public ArrayList<HashMap<String,Object>> scenelist;
		private LayoutInflater inflater;
		Context context;
		
		public GridAdapter(Context context,ArrayList<HashMap<String,Object>> list){
			this.scenelist = list;
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.scenelist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return this.scenelist.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = this.inflater.inflate(R.layout.grid_scene_items, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.scene_itemimage);
				holder.tv = (TextView) convertView.findViewById(R.id.scene_itemtext);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder)convertView.getTag();
			}
			
			int tmp = context.getResources().getDisplayMetrics().widthPixels/3;
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(tmp,tmp);
			convertView.setLayoutParams(param);
			
			holder.iv.setImageDrawable(context.getResources().getDrawable(PublicDefine.getSceneResideLogo((Integer) scenelist.get(position).get("logo"))));
			holder.tv.setText((CharSequence) scenelist.get(position).get("text"));
			if(iconChose==position){
				//Log.d(TAG,"iconChose == i ->set blue");
				holder.tv.setTextColor(mcontext.getResources().getColor(R.color.lightseagreen));
			}else{
				holder.tv.setTextColor(mcontext.getResources().getColor(R.color.whitesmoke));
			}
			
			return convertView;
		}
	}
	
	public class ViewHolder{
		ImageView iv;
		TextView tv;
	}
}
