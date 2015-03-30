package elle.home.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import elle.home.app.R;
import elle.home.hal.WifiAdmin;
import elle.home.utils.ViewHolder;

/**
 * 选择设备
 * @author jason
 *
 */
public class WifiListDialog{
	
	private Context mContext;
	private AlertDialog dlg;
	
	private ListView mListView;
	private ArrayList<DeviceItem> mDeviceLists = new ArrayList<DeviceItem>();
	
	public WifiListDialog(Context context) {
		mContext = context;
		
		initViews(context);
		initDatas();
	}

	private void initDatas() {
		WifiAdmin wifiAdmin = new WifiAdmin(mContext);
		List<ScanResult> tmp = wifiAdmin.getWifiList();
		int size = tmp.size();
		for(int i=0; i<size; i++){
			mDeviceLists.add(new DeviceItem(tmp.get(i).SSID));		
		}
		
		mListView.setAdapter(new DeviceListAdapter());
	}

	private void initViews(Context context) {
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_wifi_list);
		
		mListView = (ListView) window.findViewById(R.id.list_device);
	}

	private class DeviceListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mDeviceLists.size();
		}

		@Override
		public Object getItem(int position) {
			return mDeviceLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wifi_list, null);
			}
			
			DeviceItem item = mDeviceLists.get(position);
			
			TextView name = ViewHolder.get(convertView, R.id.itemtext);
			name.setText(item.getName());
			
			return convertView;
		}
		
	}
	
	class DeviceItem{
		private String name;
		
		public DeviceItem(String name) {
			super();
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mListView.setOnItemClickListener(listener);
	}

	public void dissMiss() {
		if(null != dlg && dlg.isShowing()){
			dlg.dismiss();
		}
	}

	public String getWifiSSID(int position) {
		return mDeviceLists.get(position).getName();
	}
}
