package elle.home.app.infra;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.partactivity.BaseActivity;
import elle.home.publicfun.PublicDefine;

public class InfraActivity extends BaseActivity {

	public String TAG = "InfraActivity";
	
	ListView listview;
	InfraDevAdapter adapter;
	
	private long devmac;
	private int shownum;
	private OneDev dev;
	public int connectStatus;
	
	ImageButton backbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_infra);
		this.listview = (ListView)this.findViewById(R.id.infralist);
		backbtn = (ImageButton)this.findViewById(R.id.title_btn_left);
		
		Intent intent = this.getIntent();
		devmac = intent.getLongExtra("mac", 0);
		connectStatus = intent.getIntExtra("connect", PublicDefine.ConnectNull);
		shownum = intent.getIntExtra("shownum", 0);
		
		dev = new OneDev();
		dev.getFromDatabase(devmac, this);
		
		ArrayList<HashMap<String,Object>> listitems = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> mapair = new HashMap<String,Object>();
		mapair.put("logo", R.drawable.icon_little_air);
		mapair.put("introduce", getResources().getString(R.string.infra_add_air));
		
		HashMap<String,Object> maptv = new HashMap<String,Object>();
		maptv.put("logo", R.drawable.icon_little_tv);
		maptv.put("introduce", getResources().getString(R.string.infra_add_tv));
		
		listitems.add(mapair);
		listitems.add(maptv);
		
		adapter = new InfraDevAdapter(this,listitems);
		listview.setAdapter(adapter);
		
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
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public class InfraDevAdapter extends BaseAdapter{

		public ArrayList<HashMap<String,Object>> devlist;
		private LayoutInflater inflater;
		Context context;
		
		public InfraDevAdapter(Context context,ArrayList<HashMap<String,Object>> list){
			this.devlist = list;
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return devlist.size();
		}

		@Override
		public Object getItem(int position) {
			return devlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if(convertView==null){
				holder = new ViewHolder();
				convertView = this.inflater.inflate(R.layout.infra_dev_item, null);
			
				holder.logo = (ImageView) convertView.findViewById(R.id.infra_dev_logo);
				holder.introduce = (TextView) convertView.findViewById(R.id.infra_dev_introduce);
				holder.addbtn = (ImageButton) convertView.findViewById(R.id.infra_dev_add);
				convertView.setTag(holder);
			
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.logo.setImageDrawable(context.getResources().getDrawable((Integer)devlist.get(position).get("logo")));
			//holder.introduce.setImageDrawable(context.getResources().getDrawable((Integer)devlist.get(position).get("introduce")));
			holder.introduce.setText((CharSequence) devlist.get(position).get("introduce"));
			holder.addbtn.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_dev_add_icon_pressed));
					}else if(event.getAction() == MotionEvent.ACTION_UP||event.getAction() == MotionEvent.ACTION_CANCEL){
						Log.d(TAG,"click infra activity:"+listview.getChildCount());
						v.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_dev_add_icon));
						for(int i =0;i<listview.getChildCount();i++){
							ViewHolder x = (ViewHolder)listview.getChildAt(i).getTag();
							if(v == x.addbtn){
								Intent intent = new Intent(context,InfraBrandListActivity.class);
								if(0 == i){
									intent.putExtra("type", PublicDefine.TypeInfraAir);
								}else{
									intent.putExtra("type", PublicDefine.TypeInfraTv);
								}
								
								intent.putExtra("mac", dev.mac);
								intent.putExtra("connect", dev.getConnectStatus());
								intent.putExtra("devname", dev.devname);
								context.startActivity(intent);
								break;
							}
						}
					}
					return false;
				}
			});
			
			return convertView;
		}
		
		public class ViewHolder{
			ImageView logo;
			TextView introduce;
			ImageButton addbtn;
		}
		
	}
	
	
}
