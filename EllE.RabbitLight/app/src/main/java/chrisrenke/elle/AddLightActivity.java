package chrisrenke.elle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import elle.home.database.AllDevInfo;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ViewHolder;

/**
 * Created by jason on 15/8/15.
 */
public class AddLightActivity extends Activity {
    private ListView mListView;
    private AllDevInfo mAllDevInfo;
    private List<OneDev> mDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_light);

        mListView = (ListView) findViewById(R.id.list_view);
        mAllDevInfo = new AllDevInfo(getApplicationContext());
        mDevices = mAllDevInfo.getAllDev();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), DevInfoActivity.class);
//                intent.putExtra("dev", mDevices.get(position));
//                getActivity().startActivity(intent);
            }
        });
    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_device_list, null);
            }

            OneDev item = mDevices.get(position);

            TextView name = ViewHolder.get(convertView, R.id.tv_title);
            name.setText(item.devname);

            ImageView icon = ViewHolder.get(convertView, R.id.img_icon);
            if(PublicDefine.ConnectNull == item.getConnectStatus()){
                icon.setImageResource(R.drawable.statue_disconnect);
            }else{
                icon.setImageResource(R.drawable.statue_connect);
            }

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            if (null == mDevices || mDevices.size() <= 0) {
                return null;
            }

            return mDevices.get(position);
        }

        @Override
        public int getCount() {
            if (null == mDevices) {
                return 0;
            }

            return mDevices.size();
        }
    };

    public void doAddLightClick(View view) {
        Intent mIntent = new Intent(this, CheckNewDevActivity.class);
        startActivity(mIntent);
    }

    public void doCloseClick(View view) {
        finish();
    }
}
