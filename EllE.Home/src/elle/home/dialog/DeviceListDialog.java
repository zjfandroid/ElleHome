package elle.home.dialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import elle.home.app.R;
import elle.home.utils.ViewHolder;

/**
 * 选择设备
 * @author jason
 *
 */
public class DeviceListDialog{
	
	private Context mContext;
	private AlertDialog dlg;
	
	private ListView mListView;
	private ArrayList<DeviceItem> mDeviceLists = new ArrayList<DeviceItem>();
	
	public DeviceListDialog(Context context) {
		mContext = context;
		
		initViews(context);
		initDatas();
	}

	private void initDatas() {
		Resources res = mContext.getResources();
		
		mDeviceLists.add(new DeviceItem(R.drawable.icon_black_light, res.getString(R.string.type_light_string)));		
		mDeviceLists.add(new DeviceItem(R.drawable.icon_black_plug, res.getString(R.string.type_plug_string)));		
		mDeviceLists.add(new DeviceItem(R.drawable.icon_black_gateway, res.getString(R.string.type_gateway)));		
		mDeviceLists.add(new DeviceItem(R.drawable.icon_black_camera, res.getString(R.string.type_carema)));		
		mDeviceLists.add(new DeviceItem(R.drawable.icon_black_infra, res.getString(R.string.type_infra_string)));		
//		mDeviceLists.add(new DeviceItem(R.drawable.icon_infra_air_normal, res.getString(R.string.type_air_string)));	
//		mDeviceLists.add(new DeviceItem(R.drawable.icon_infra_tv_normal, res.getString(R.string.type_tv_string)));	
		
		mListView.setAdapter(new DeviceListAdapter());
	}

	private void initViews(Context context) {
		dlg = new AlertDialog.Builder(context).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();
		
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_device_list);
		
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
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_list, null);
			}
			
			DeviceItem item = mDeviceLists.get(position);
			ImageView logo = ViewHolder.get(convertView, R.id.itemlogo);
			logo.setImageResource(item.getResID());
//			logo.setBackgroundResource(item.getResID());
			
			TextView name = ViewHolder.get(convertView, R.id.itemtext);
			name.setText(item.getName());
			
			return convertView;
		}
		
	}
	
	class DeviceItem{
		private int resID;
		private String name;
		
		public DeviceItem(int resID, String name) {
			super();
			this.resID = resID;
			this.name = name;
		}

		public int getResID() {
			return resID;
		}
		
		public void setResID(int resID) {
			this.resID = resID;
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
}
