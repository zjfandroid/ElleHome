package elle.home.app.infra;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import elle.home.app.smart.R;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ViewHolder;
import elle.homegenius.infrajni.InfraNative;

public class InfraBrandListActivity extends Activity {

	private ListView mListView;
	private List<Item> mLists = new ArrayList<InfraBrandListActivity.Item>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infra);
		initDatas();
		initViews();
	}

	private void initDatas() {
		int len = InfraNative.getAirAllBrandListLen();
		for (int i = 0; i < len; i++) {
			Item item = new Item();
			item.brand = InfraNative.getAirOneBrandNameById(i+1, 1);
			ShowInfo.printLogW("______initDatas__________" + item.brand);
			mLists.add(item);
		}
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
				intent.putExtra("mac", getIntent().getLongExtra("mac", 0));
				intent.putExtra("connect", PublicDefine.ConnectNull);
				startActivity(intent);
			}
		});
	}
	
	private BaseAdapter mBaseAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.infra_brand_item, null);
			}
			
			TextView brand = ViewHolder.get(convertView, R.id.infra_dev_introduce);
			brand.setText(mLists.get(position).brand);
			
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
