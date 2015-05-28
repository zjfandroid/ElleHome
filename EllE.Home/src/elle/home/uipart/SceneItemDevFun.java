package elle.home.uipart;

import java.util.ArrayList;
import java.util.HashMap;

import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SceneItemDevFun extends LinearLayout {

	public String TAG = "SceneItemDevFun";
	
	Context context;
	OneDev dev;
	//列表视图
	GridView gridview;
	//
	ArrayList<HashMap<String,Object>> griditems ;
	GridAdapter gridadapter;
	
	OnClickFun clickfun;
	
	public SceneItemDevFun(Context context,OneDev dev) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initview(context,dev);
	}
	
	public void initview(Context context,OneDev dev){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.scene_item_dev_fun, this);
		this.dev = dev;
		gridview = (GridView)this.findViewById(R.id.dev_fun_grid);
		griditems = getShowData(dev.type);
		gridadapter = new GridAdapter(context);
		gridview.setAdapter(gridadapter);
		gridview.setOnItemClickListener(new ItemClickListener());
		
		int tmp = context.getResources().getDisplayMetrics().widthPixels/4;
		LinearLayout.LayoutParams llp =  (LinearLayout.LayoutParams) gridview.getLayoutParams();
		if(dev.type == PublicDefine.TypeInfra){
			
		}else if(dev.type == PublicDefine.TypeInfraAir){
			llp.height = tmp*2+8;
		}else if(dev.type == PublicDefine.TypeLight){
			llp.height = tmp*3+8;
		}
		else{
			llp.height = tmp+8;
		}
		if(llp!=null)
			Log.d(TAG,"llp width:"+llp.width+" height:"+llp.height);
		
	}
	
	public void setOnClickItem(OnClickFun a){
		this.clickfun = a;
	}
	
	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			Log.d(TAG, "click:"+position);
			if(clickfun!=null)
				clickfun.onclick(position,griditems.size(),dev);
		}
		
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public ArrayList<HashMap<String,Object>> getShowData(byte type){
		ArrayList<HashMap<String,Object>> tmp = new ArrayList<HashMap<String,Object>>();
		
		switch(type){
		case PublicDefine.TypeLight:
			HashMap<String,Object> map1 = new HashMap<String,Object>();
			map1.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightOnIcon));
			map1.put("funname", getContext().getResources().getString(R.string.scene_fun_light_on));
			tmp.add(map1);
			HashMap<String,Object> map2 = new HashMap<String,Object>();
			map2.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightOffIcon));
			map2.put("funname", getContext().getResources().getString(R.string.scene_fun_light_off));
			tmp.add(map2);
			HashMap<String,Object> map3 = new HashMap<String,Object>();
			map3.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightFreeIcon));
			map3.put("funname", getContext().getResources().getString(R.string.scene_fun_light_random));
			tmp.add(map3);
			HashMap<String,Object> map4 = new HashMap<String,Object>();
			map4.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightNullIcon));
			map4.put("funname", getContext().getResources().getString(R.string.scene_fun_light_cancel));
			tmp.add(map4);
			HashMap<String,Object> map5 = new HashMap<String,Object>();
			map5.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor1));
			map5.put("funname", getContext().getResources().getString(R.string.scene_fun_light_orange));
			tmp.add(map5);
			HashMap<String,Object> map6 = new HashMap<String,Object>();
			map6.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor2));
			map6.put("funname", getContext().getResources().getString(R.string.scene_fun_light_green));
			tmp.add(map6);
			HashMap<String,Object> map7 = new HashMap<String,Object>();
			map7.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor3));
			map7.put("funname", getContext().getResources().getString(R.string.scene_fun_light_blue));
			tmp.add(map7);
			HashMap<String,Object> map8 = new HashMap<String,Object>();
			map8.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor4));
			map8.put("funname", getContext().getResources().getString(R.string.scene_fun_light_red));
			tmp.add(map8);
			HashMap<String,Object> map9 = new HashMap<String,Object>();
			map9.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor5));
			map9.put("funname", getContext().getResources().getString(R.string.scene_fun_light_yellow));
			tmp.add(map9);
			HashMap<String,Object> map10 = new HashMap<String,Object>();
			map10.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor6));
			map10.put("funname", getContext().getResources().getString(R.string.scene_fun_light_cyan));
			tmp.add(map10);
			HashMap<String,Object> map11 = new HashMap<String,Object>();
			map11.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor7));
			map11.put("funname", getContext().getResources().getString(R.string.scene_fun_light_indigo));
			tmp.add(map11);
			HashMap<String,Object> map12 = new HashMap<String,Object>();
			map12.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneLightColor8));
			map12.put("funname", getContext().getResources().getString(R.string.scene_fun_light_violet));
			tmp.add(map12);
			break;
		case PublicDefine.TypePlug:
			HashMap<String,Object> xmap1 = new HashMap<String,Object>();
			xmap1.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.ScenePlugOnIcon));
			xmap1.put("funname", getContext().getResources().getString(R.string.scene_fun_plug_on));
			tmp.add(xmap1);
			HashMap<String,Object> xmap2 = new HashMap<String,Object>();
			xmap2.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.ScenePlugOffIcon));
			xmap2.put("funname", getContext().getResources().getString(R.string.scene_fun_plug_off));
			tmp.add(xmap2);
			HashMap<String,Object> xmap3 = new HashMap<String,Object>();
			xmap3.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.ScenePlugNullIcon));
			xmap3.put("funname", getContext().getResources().getString(R.string.scene_fun_plug_cancel));
			tmp.add(xmap3);
			break;
		case PublicDefine.TypeInfra:
			
			break;
		case PublicDefine.TypeInfraAir:
			HashMap<String,Object> amap16 = new HashMap<String,Object>();
			amap16.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirCold16));
			amap16.put("funname", getContext().getResources().getString(R.string.scene_fun_air_refrigeration16));
			tmp.add(amap16);
			HashMap<String,Object> amap24 = new HashMap<String,Object>();
			amap24.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirCold24));
			amap24.put("funname", getContext().getResources().getString(R.string.scene_fun_air_refrigeration23));
			tmp.add(amap24);
			HashMap<String,Object> amap30 = new HashMap<String,Object>();
			amap30.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirCold30));
			amap30.put("funname", getContext().getResources().getString(R.string.scene_fun_air_refrigeration30));
			tmp.add(amap30);
			
			HashMap<String,Object> amap3 = new HashMap<String,Object>();
			amap3.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirCloseIcon));
			amap3.put("funname", getContext().getResources().getString(R.string.scene_fun_air_off));
			tmp.add(amap3);
			
			HashMap<String,Object> wmap16 = new HashMap<String,Object>();
			wmap16.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirWarm16));
			wmap16.put("funname", getContext().getResources().getString(R.string.scene_fun_air_heat16));
			tmp.add(wmap16);
			HashMap<String,Object> wmap24 = new HashMap<String,Object>();
			wmap24.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirWarm24));
			wmap24.put("funname", getContext().getResources().getString(R.string.scene_fun_air_heat23));
			tmp.add(wmap24);
			HashMap<String,Object> wmap30 = new HashMap<String,Object>();
			wmap30.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirWarm30));
			wmap30.put("funname", getContext().getResources().getString(R.string.scene_fun_air_heat30));
			tmp.add(wmap30);
			
			HashMap<String,Object> amap4 = new HashMap<String,Object>();
			amap4.put("funlogo", PublicResDefine.getIconByTypeFun(type,PublicDefine.SceneInfraAirNullIcon));
			amap4.put("funname", getContext().getResources().getString(R.string.scene_fun_air_cancel));
			tmp.add(amap4);
			break;
		}
		
		return tmp;
	}

	public class GridAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		Context context;
		
		public GridAdapter(Context context){
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return griditems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return griditems.get(position);
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
				convertView = this.inflater.inflate(R.layout.scene_item_dev_fun_gtid, null);
				//holder.bk = (ImageView)convertView.findViewById(R.id.scene_fun_grid_bk);
				holder.tv = (TextView)convertView.findViewById(R.id.scene_fun_grid_text);
				holder.iv = (ImageView)convertView.findViewById(R.id.scene_fun_grid_fun);
				convertView.setTag(holder);
				
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			//holder.bk.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_press));
			holder.iv.setImageDrawable(context.getResources().getDrawable((Integer) griditems.get(position).get("funlogo")));
			holder.tv.setText((CharSequence) griditems.get(position).get("funname"));
			
			int tmp = context.getResources().getDisplayMetrics().widthPixels/4;
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(tmp,tmp);
			convertView.setLayoutParams(param);
			
			android.view.ViewGroup.LayoutParams lp = convertView.getLayoutParams();			
			
			return convertView;
		}
		
		public class ViewHolder{
			ImageView iv;
			TextView tv;
			ImageView bk;
		}
		
	}	
	
	public interface OnClickFun{
		void onclick(int a,int size,OneDev dev);
	}

}
