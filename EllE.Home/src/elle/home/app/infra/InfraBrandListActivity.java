package elle.home.app.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import elle.fun.infra.InfraThread;
import elle.fun.infra.OnBrandListListener;
import elle.home.app.smart.R;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ViewHolder;

public class InfraBrandListActivity extends Activity {

	private ListView mListView;
	private List<Item> mLists = new ArrayList<InfraBrandListActivity.Item>();
	
	private byte type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infra);
		initDatas();
		initViews();
	}

	private void initDatas() {
		type = getIntent().getByteExtra("type", PublicDefine.TypeInfraAir);
		if(PublicDefine.TypeInfraAir == type){
			initDatas(InfraThread.TypeAir);
		}else{
			initDatas(InfraThread.TypeTv);
		}
	}

	private void initDatas(String type) {
		InfraThread thread = new InfraThread();
		
		Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh")){
        	language = InfraThread.LanguageChinese;
        }else{
        	language = InfraThread.LanguageChinese;
        }
        
		thread.startGetBrandList(InfraThread.Company, type, language, new OnBrandListListener() {
			
			@Override
			public void recvBrandList(boolean isok, int len, ArrayList<String> brandlist) {
				for (int i = 0; i < len; i++) {
					Item item = new Item();
					item.brand = brandlist.get(i);
					ShowInfo.printLogW("______initDatas__________" + item.brand);
					mLists.add(item);
				}	
				
				InfraBrandListActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mBaseAdapter.notifyDataSetChanged();						
					}
				});
			}
		});
	}

	private void initViews() {
		mListView = (ListView) findViewById(R.id.infralist);
		mListView.setAdapter(mBaseAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(), InfraBrandDevActivity.class);
				intent.putExtra("id", position+1);
				intent.putExtra("brand", mLists.get(position).brand);
				intent.putExtra("mac", getIntent().getLongExtra("mac", 0));
				intent.putExtra("connect", PublicDefine.ConnectNull);
				intent.putExtra("type", type);
				startActivity(intent);
			}
		});
	}
	
	public void doExit(View v){
		finish();
	}
	
	private BaseAdapter mBaseAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.infra_brand_item, null);
			}
			
			TextView brand = ViewHolder.get(convertView, R.id.infra_dev_introduce);
			brand.setText(mLists.get(position).brand);
			
			ImageView img = ViewHolder.get(convertView, R.id.infra_dev_logo);
			if(PublicDefine.TypeInfraAir == type){
				img.setImageResource(R.drawable.icon_infra_air_normal);
			}else{
				img.setImageResource(R.drawable.icon_infra_tv_normal);
			}
			
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return mLists.get(position);
		}
		
		@Override
		public int getCount() {
			return mLists.size();
		}
	};
	
	class Item{
		String brand;
	}
}
