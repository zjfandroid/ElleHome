package elle.home.partactivity;

import java.util.ArrayList;
import java.util.HashMap;

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
import elle.home.app.R;
import elle.home.database.DevLocationInfo;
import elle.home.publicfun.PublicDefine;

public class AddLocationActivity extends BaseActivity {

	public String TAG = "AddLocationActivity";
	
	Context mcontext;
	ImageButton addbtn;
	EditText et;
	
	//返回按钮
	ImageButton backbtn;
	//图案选择
	GridView gridview;
	GridAdapter gridAdapter;
	//选中的场景图片
	private int iconChose = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_add_location);
		mcontext = this;
		addbtn = (ImageButton)this.findViewById(R.id.addLocationBtn);
		et = (EditText)this.findViewById(R.id.addNameEdit);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		backbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					PublicDefine.vibratorNormal(mcontext);
				}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL){
					finish();
				}
				return true;
			}
		});
		addbtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					addbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_signal_dev_add_press));
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					addbtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_signal_dev_add));
					if(et.getText().length()==0){
						Log.d(TAG,"输入地点名字啊~");
						Toast.makeText(mcontext, getResources().getString(R.string.tips_bename_first), Toast.LENGTH_SHORT).show();
					}else{
						DevLocationInfo locationInfo = new DevLocationInfo(et.getText().toString(), iconChose ,mcontext);
						if(locationInfo.addLocatToDatabase()==0){
							Toast.makeText(mcontext, getResources().getString(R.string.tips_add_succeed), Toast.LENGTH_SHORT).show();
							setResult(2);
							finish(); 
						}else{
							Toast.makeText(mcontext, getResources().getString(R.string.tips_benamed), Toast.LENGTH_SHORT).show();
						}
						
					}
					break;
				}
				return true;
			}
		});
		
		gridview = (GridView)this.findViewById(R.id.location_grid_view);
		ArrayList<HashMap<String,Object>> gridSceneItems = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		map1.put("logo",PublicDefine.ResideIconLocatHome);
		map1.put("text", getResources().getString(R.string.location_home));
		gridSceneItems.add(map1);
		
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		map2.put("logo",PublicDefine.ResideIconLocatApartment);
		map2.put("text", getResources().getString(R.string.location_apartment));
		gridSceneItems.add(map2);
		
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		map3.put("logo",PublicDefine.ResideIconLocatBaby);
		map3.put("text", getResources().getString(R.string.location_nursery));
		gridSceneItems.add(map3);
		
		HashMap<String,Object> map4 = new HashMap<String,Object>();
		map4.put("logo",PublicDefine.ResideIconLocatCar);
		map4.put("text", getResources().getString(R.string.location_garage));
		gridSceneItems.add(map4);
		
		HashMap<String,Object> map5 = new HashMap<String,Object>();
		map5.put("logo",PublicDefine.ResideIconLocatCookhouse);
		map5.put("text", getResources().getString(R.string.location_kitchen));
		gridSceneItems.add(map5);
		
		HashMap<String,Object> map6 = new HashMap<String,Object>();
		map6.put("logo",PublicDefine.ResideIconLocatDorm);
		map6.put("text", getResources().getString(R.string.location_dorm));
		gridSceneItems.add(map6);
		
		HashMap<String,Object> map7 = new HashMap<String,Object>();
		map7.put("logo",PublicDefine.ResideIconLocatGarden);
		map7.put("text", getResources().getString(R.string.location_garden));
		gridSceneItems.add(map7);
		
		HashMap<String,Object> map8 = new HashMap<String,Object>();
		map8.put("logo",PublicDefine.ResideIconLocatHotel);
		map8.put("text", getResources().getString(R.string.location_hotel));
		gridSceneItems.add(map8);
		
		HashMap<String,Object> map9 = new HashMap<String,Object>();
		map9.put("logo",PublicDefine.ResideIconLocatOffice);
		map9.put("text", getResources().getString(R.string.location_office));
		gridSceneItems.add(map9);
		
		gridAdapter = new GridAdapter(this,gridSceneItems);
		gridview.setAdapter(gridAdapter);
		
		gridview.setOnItemClickListener(new ItemClickListener());
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	private int getDefineLogoByClick(){
		switch(iconChose){
		case 0:
			return PublicDefine.ResideIconLocatHome;
		case 1:
			return PublicDefine.ResideIconLocatApartment;
		case 2:
			return PublicDefine.ResideIconLocatBaby;
		case 3:
			return PublicDefine.ResideIconLocatCar;
		case 4:
			return PublicDefine.ResideIconLocatCookhouse;
		case 5:
			return PublicDefine.ResideIconLocatDorm;
		case 6:
			return PublicDefine.ResideIconLocatGarden;
		case 7:
			return PublicDefine.ResideIconLocatHotel;
		case 8:
			return PublicDefine.ResideIconLocatOffice;
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
					et.setText(holder.tv.getText());
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
		this.setResult(1);
		
		super.onDestroy();
	}

	public class GridAdapter extends BaseAdapter{

		public ArrayList<HashMap<String,Object>> locatlist;
		private LayoutInflater inflater;
		Context context;
		
		public GridAdapter(Context context,ArrayList<HashMap<String,Object>> list){
			this.locatlist = list;
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.locatlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return this.locatlist.get(position);
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
			
			holder.iv.setImageDrawable(context.getResources().getDrawable(PublicDefine.getResideLocatIcon((Integer) locatlist.get(position).get("logo"))));
			holder.tv.setText((CharSequence) locatlist.get(position).get("text"));
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
