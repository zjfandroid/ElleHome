package elle.home.Fragment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import elle.home.app.ControllersActivity;
import elle.home.app.CurtainActivity;
import elle.home.app.GatewayActivity;
import elle.home.app.LightRgbActivity;
import elle.home.app.MainActivity;
import elle.home.app.PlugActivity;
import elle.home.app.infra.InfraActivity;
import elle.home.app.infra.InfraAirActivity;
import elle.home.app.infra.InfraTVControlActivity;
import elle.home.app.smart.R;
import elle.home.app.view.SizeAdjustingTextView;
import elle.home.database.DevLocationInfo;
import elle.home.database.OneDev;
import elle.home.dialog.ConfigCameraDialog;
import elle.home.partactivity.BaseActivity;
import elle.home.partactivity.UMengConstant;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.PlugControlPacket;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.uipart.PublicResDefine;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.ShowInfo;
import elle.home.utils.ViewHolder;

public class LocationDevFragment extends Fragment {
	
	public String TAG = "LocationDevFragment";
	
	private ArrayList<HashMap<String,Object>> gridDevItems = new ArrayList<HashMap<String,Object>>();
	
	private View parentView;
	private GridView gridview;
	private Context mContext;
	
	private DevLocationInfo locatInfo;
	private GridAdapter gridAdapter;
	
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask(){

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
		
	};
	
	public Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				if(gridAdapter!=null){
					gridAdapter.notifyDataSetChanged();
				}
				break;
			}
		}
		
	};
	
	public LocationDevFragment(){
		
	}
	
	public LocationDevFragment(DevLocationInfo tmp){
		locatInfo = tmp;
		//locatInfo.setAllDevToLocal();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		parentView = inflater.inflate(R.layout.location_dev_fragment, container, false);
		gridview = (GridView)parentView.findViewById(R.id.dev_grid_view);
		
		View v = parentView.findViewById(R.id.homebk);
		Resources res = getResources();
		if(null != locatInfo){
			Drawable drawable = res.getDrawable(res.getIdentifier("main_home_bk_" + locatInfo.locaticon, "drawable", getActivity().getPackageName()));
			v.setBackground(drawable);
		}
		
		this.mContext = container.getContext();
		if(locatInfo!=null){
			for(int i=0;i<locatInfo.devLocationList.size();i++){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("type", locatInfo.devLocationList.get(i).type);
				map.put("devname", locatInfo.devLocationList.get(i).devname);
				gridDevItems.add(map);
			}
			
			if(locatInfo.devLocationList.size()<12){
				for(int i = locatInfo.devLocationList.size();i<12;i++){
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("type", PublicDefine.TypeNull);
					map.put("devname", "");
					gridDevItems.add(map);
				}
			}
			
			if((gridDevItems.size()%3)!=0){
				int ss = 3-(gridDevItems.size()%3);
				for(int xx=0;xx<ss;xx++){
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("type", PublicDefine.TypeNull);
					map.put("devname", "");
					gridDevItems.add(map);
				}
			}
			
			gridAdapter = new GridAdapter(container.getContext(), gridDevItems);
			
			gridview.setAdapter(gridAdapter);
			gridview.setOnItemClickListener(new ItemClickListener());
			gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					ShowInfo.printLogW("________onItemLongClick________" + position);
					if(position<locatInfo.devLocationList.size()){
						OneDev onedev = locatInfo.devLocationList.get(position);
						
						switch(onedev.type){
						case PublicDefine.TypeLight:
							packetLight(onedev);
							break;
						case PublicDefine.TypePlug:
							packetPlug(onedev);
							break;
						default:
							break;
						}
					}
					
					return true;
				}
			});
			
			timer.schedule(task, 1000,1000);			
		}
		return parentView;
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}

	private void packetPlug(OneDev onedev) {
		int connectStatus = onedev.getConnectStatus();
		InetAddress conip = null;
		int conport = 0;
		
		if(connectStatus == PublicDefine.ConnectRemote){
			conip = onedev.remoteip;
			conport = onedev.remoteport;
		}else{
			try {
				conip = InetAddress.getByName("255.255.255.255");
				conport = PublicDefine.LocalUdpPort;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		PlugControlPacket packetoff = new PlugControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packetoff.setPacketRemote(true);
		}
		
		if(onedev.isTurnOn()){
			packetoff.plugOff(DataExchange.longToEightByte(onedev.mac), null);
		}else{
			packetoff.plugOn(DataExchange.longToEightByte(onedev.mac), null);
		}
		
		packetoff.setImportance(BasicPacket.ImportHigh);
		((MainActivity)getActivity()).getAutoBinder().addPacketToSend(packetoff);
	}
	
	private void packetLight(OneDev onedev) {
		int connectStatus = onedev.getConnectStatus();
		InetAddress conip = null;
		int conport = 0;
		
		if(connectStatus == PublicDefine.ConnectRemote){
			conip = onedev.remoteip;
			conport = onedev.remoteport;
		}else{
			try {
				conip = InetAddress.getByName("255.255.255.255");
				conport = PublicDefine.LocalUdpPort;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		LightControlPacket packet = new LightControlPacket(conip,conport);
		if(connectStatus == PublicDefine.ConnectRemote){
			packet.setPacketRemote(true);
		}
		
		if(onedev.isTurnOn()){
				((BaseActivity)getActivity()).UMeng_OnEvent(BaseActivity.EVENT_ID_CLICK_CLOSE_LIGHT);
				packet.lightClose(DataExchange.longToEightByte(onedev.mac), null);
			}else{
				((BaseActivity)getActivity()).UMeng_OnEvent(BaseActivity.EVENT_ID_CLICK_OPEN_LIGHT);
				packet.lightOpen(DataExchange.longToEightByte(onedev.mac), null);
		}
		
		packet.setImportance(BasicPacket.ImportHigh);
		((MainActivity)getActivity()).getAutoBinder().addPacketToSend(packet);
	}

	class ItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d(TAG,"click num:"+position);
			HashMap<String, String> map = null;
			int size = locatInfo.devLocationList.size();
			if(position < size){
				OneDev onedev = locatInfo.devLocationList.get(position);
				
				switch(onedev.type){
				case PublicDefine.TypeNull:
					break;
				case PublicDefine.TypeLight:
					Intent xintent = new Intent(mContext,LightRgbActivity.class);
					xintent.putExtra("mac", onedev.mac);
					xintent.putExtra("devname", onedev.devname);
					xintent.putExtra("connect", onedev.getConnectStatus());
					mContext.startActivity(xintent);
				    map = new HashMap<String, String>();
					map.put(UMengConstant.KEY_DEV_MAC, DataExchange.byteArrayToHexString(DataExchange.longToEightByte(onedev.mac)));
					map.put(UMengConstant.KEY_DEV_NAME, onedev.devname);
					map.put(UMengConstant.KEY_DEV_STATE, onedev.getConnectStatus()+"");
					map.put(UMengConstant.KEY_DEV_TYPE, mContext.getString(R.string.type_light_string));
					MobclickAgent.onEvent(mContext,UMengConstant.EVENT_ID_CLICK_DEV ,map);
					break;
				case PublicDefine.TypePlug:
					Intent intent = new Intent(mContext,PlugActivity.class);
					Log.d(TAG,"start dev activity mac:"+onedev.mac);
					intent.putExtra("mac", onedev.mac);
					intent.putExtra("connect", onedev.getConnectStatus());
					intent.putExtra("devname", onedev.devname);
					mContext.startActivity(intent);
				    map = new HashMap<String, String>();
					map.put(UMengConstant.KEY_DEV_MAC, DataExchange.byteArrayToHexString(DataExchange.longToEightByte(onedev.mac)));
					map.put(UMengConstant.KEY_DEV_NAME, onedev.devname);
					map.put(UMengConstant.KEY_DEV_STATE, onedev.getConnectStatus()+"");
					map.put(UMengConstant.KEY_DEV_TYPE, mContext.getString(R.string.type_plug_string));
					MobclickAgent.onEvent(mContext,UMengConstant.EVENT_ID_CLICK_DEV ,map);
					break;
				case PublicDefine.TypeInfra:
					Intent sintent = new Intent(mContext,InfraActivity.class);
					Log.d(TAG,"start dev activity mac:"+onedev.mac);
					sintent.putExtra("mac", onedev.mac);
					sintent.putExtra("connect", onedev.getConnectStatus());
					sintent.putExtra("shownum", locatInfo.devLocationList.size());
					sintent.putExtra("devname", onedev.devname);
					//mcontext.startActivity(sintent);
					getActivity().startActivityForResult(sintent, 0);
				    map = new HashMap<String, String>();
					map.put(UMengConstant.KEY_DEV_MAC, DataExchange.byteArrayToHexString(DataExchange.longToEightByte(onedev.mac)));
					map.put(UMengConstant.KEY_DEV_NAME, onedev.devname);
					map.put(UMengConstant.KEY_DEV_STATE, onedev.getConnectStatus()+"");
					map.put(UMengConstant.KEY_DEV_TYPE, mContext.getString(R.string.type_infra_string));
					MobclickAgent.onEvent(mContext,UMengConstant.EVENT_ID_CLICK_DEV ,map);
					break;
				case PublicDefine.TypeInfraAir:
					Intent aintent = new Intent(mContext,InfraAirActivity.class);
					Log.d(TAG,"start dev activity mac:"+onedev.mac);
					aintent.putExtra("mac", onedev.mac);
					aintent.putExtra("devname", onedev.devname);
					aintent.putExtra("connect", onedev.getConnectStatus());
					mContext.startActivity(aintent);
				    map = new HashMap<String, String>();
					map.put(UMengConstant.KEY_DEV_MAC, DataExchange.byteArrayToHexString(DataExchange.longToEightByte(onedev.mac)));
					map.put(UMengConstant.KEY_DEV_NAME, onedev.devname);
					map.put(UMengConstant.KEY_DEV_STATE, onedev.getConnectStatus()+"");
					map.put(UMengConstant.KEY_DEV_TYPE, mContext.getString(R.string.type_infra_string));
					MobclickAgent.onEvent(mContext,UMengConstant.EVENT_ID_CLICK_DEV ,map);
					break;
				case PublicDefine.TypeInfraTv:
					Intent intentTV = new Intent(mContext, InfraTVControlActivity.class);
					Log.d(TAG,"start dev activity mac:"+onedev.mac);
					intentTV.putExtra("mac", onedev.mac);
					intentTV.putExtra("devname", onedev.devname);
					intentTV.putExtra("connect", onedev.getConnectStatus());
					mContext.startActivity(intentTV);
					break;
				case PublicDefine.TypeInfraCamera:
					ConfigCameraDialog dialog = new ConfigCameraDialog(mContext, onedev, true);
				    map = new HashMap<String, String>();
					map.put(UMengConstant.KEY_DEV_MAC, DataExchange.byteArrayToHexString(DataExchange.longToEightByte(onedev.mac)));
					map.put(UMengConstant.KEY_DEV_NAME, onedev.devname);
					map.put(UMengConstant.KEY_DEV_STATE, onedev.getConnectStatus()+"");
					map.put(UMengConstant.KEY_DEV_TYPE, mContext.getString(R.string.type_carema));
					MobclickAgent.onEvent(mContext,UMengConstant.EVENT_ID_CLICK_DEV ,map);
					break;
				case PublicDefine.TypeGateWay:
					Intent in = new Intent(mContext,GatewayActivity.class);
					in.putExtra("mac", onedev.mac);
					in.putExtra("devname", onedev.devname);
					in.putExtra("connect", onedev.getConnectStatus());
					
					in.putExtra("locatname", locatInfo.locatname);
					in.putExtra("shownum", locatInfo.devLocationList.size());
					getActivity().startActivityForResult(in, 0);
					break;
				case PublicDefine.TypeController:
					Intent cvin = new Intent(mContext,ControllersActivity.class);
					cvin.putExtra("mac", onedev.mac);
					cvin.putExtra("devname", onedev.devname);
					cvin.putExtra("connect", getControllerConnectStatus(onedev.devname));
					mContext.startActivity(cvin);
					break;
				case PublicDefine.TypeCurtain:
					Intent curtain = new Intent(mContext, CurtainActivity.class);
					curtain.putExtra("mac", onedev.mac);
					curtain.putExtra("devname", onedev.devname);
					curtain.putExtra("connect", getControllerConnectStatus(onedev.devname));
					mContext.startActivity(curtain);
					break;
				}
//			}else{
//				Intent intentG = null;
//				intentG = new Intent(mContext, InfraActivity.class);
//				intentG.putExtra("mac", 123l);
//				intentG.putExtra("devname", "test");
//				intentG.putExtra("isTest", true);
//				mContext.startActivity(intentG);
			}
			
		}
		
	}
	
	public class GridAdapter extends BaseAdapter{

		public ArrayList<HashMap<String,Object>> devlist;
		private LayoutInflater inflater;
		Context context;
		
		public GridAdapter(Context context,ArrayList<HashMap<String,Object>> list){
			this.devlist = list;
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return this.devlist.size();
		}

		@Override
		public Object getItem(int position) {
			return this.devlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = this.inflater.inflate(R.layout.grid_dev_items, null);
			}
			
			if(position%2==0){
				convertView.setBackgroundResource(R.drawable.scene_item_iv);
			}else{
				convertView.setBackgroundResource(R.drawable.scene_item_bk);
			}
			
			ImageView itemlogo = ViewHolder.get(convertView, R.id.itemlogo);
			SizeAdjustingTextView itemtext = ViewHolder.get(convertView, R.id.itemtext);
			ImageView connectLogo = ViewHolder.get(convertView, R.id.connectLogo);
			TextView turnON = ViewHolder.get(convertView, R.id.tv_turn_on);
			
			int tmp = context.getResources().getDisplayMetrics().widthPixels/3;
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(tmp,tmp);
			convertView.setLayoutParams(param);
			
			itemlogo.setImageDrawable(mContext.getResources().getDrawable(PublicResDefine.getFragmentIconByType((Byte) devlist.get(position).get("type"))));
			itemtext.setText((String) devlist.get(position).get("devname"));
			
			switch((Byte) devlist.get(position).get("type")){
			case PublicDefine.TypeInfra:
			case PublicDefine.TypeInfraAir:
			case PublicDefine.TypeLight:
			case PublicDefine.TypePlug:
			case PublicDefine.TypeGateWay:
				OneDev oneDev = locatInfo.devLocationList.get(position);
				int status = oneDev.getConnectStatus();
				
				connectLogo.setImageDrawable(mContext.getResources().getDrawable(PublicResDefine.getConnectFragmentLogo(status)));
				
				if(oneDev.isTurnOn() && PublicDefine.ConnectNull != status){
					turnON.setVisibility(View.VISIBLE);
				}else{
					turnON.setVisibility(View.GONE);
				}
				break;
				
			default:
				connectLogo.setImageDrawable(mContext.getResources().getDrawable(PublicResDefine.getConnectFragmentLogo(PublicDefine.ConnectNullIcon)));
				turnON.setVisibility(View.GONE);
				break;
			}
			return convertView;
		}
		
	}
	
	private Bitmap decodeResource(Resources resources,int id){
		TypedValue value = new TypedValue();
	    resources.openRawResource(id, value);
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inTargetDensity = value.density;
	    return BitmapFactory.decodeResource(resources, id, opts);
	}

	public int getControllerConnectStatus(String devname) {
		String mac = SaveDataPreferences.load(mContext, devname, "");
		int size = locatInfo.devLocationList.size();
		int status = 0;
		for(int index = 0; index < size; index++){
			OneDev dev = locatInfo.devLocationList.get(index);
			if(Long.toString(dev.mac).equals(mac)){
				status = dev.getConnectStatus();
				break;
			}
					
		}
		return status;
	}
	
}
