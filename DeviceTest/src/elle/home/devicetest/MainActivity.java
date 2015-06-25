package elle.home.devicetest;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import elle.home.devicetest.UdpCheckNewThread.OnRefreshCallback;




public class MainActivity extends Activity implements
		OnRefreshListener<ListView>, OnLastItemVisibleListener ,OnRefreshCallback{

	private static final String TAG = "MainActivity";
	private PullToRefreshListView mListView = null;
	private RefreshAdapter mAdapter = null;
	private UdpCheckNewThread udpCheckNewThread = null;
	private UdpThread udpThread = null;
	
	//动态查找到的设备
	public List<OneDev> devs = new ArrayList<OneDev>();
	private byte type = PublicDefine.TypeLight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (PullToRefreshListView) this
				.findViewById(R.id.pull_refresh_list);
		udpCheckNewThread = new UdpCheckNewThread(this , this);
		
		udpThread = new UdpThread();
		mAdapter = new RefreshAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnRefreshListener(this);
		mListView.setOnLastItemVisibleListener(this);
		
		udpCheckNewThread.startThread();
		udpThread.startThread();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		udpCheckNewThread.stopCheck();
		udpCheckNewThread.stopThread();
		udpThread.stopThread();
	}



	public void onColor(View v){
		int white = 0,red = 0,green = 0,blue = 0;
		switch (v.getId()) {
		case R.id.rb_white:
			white = 255;
			break;
			
		case R.id.rb_red:
			red = 255;
			break;
			
		case R.id.rb_green:
			green = 255;
			break;
			
		case R.id.rb_blue:
			blue = 255;
			break;

		default:
			break;
		}
		
		for(OneDev dev : devs){
			LightControlPacket packet = new LightControlPacket(null,PublicDefine.LocalUdpPort);
			packet.setImportance(BasicPacket.ImportHigh);
			packet.setIp(dev.remoteip);
			packet.lightColor(DataExchange.longToEightByte(dev.mac), null, red, green, blue, white, 0,100);
			udpThread.getProtocolDataList().addOnePacketToSend(packet);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		new RefreshTask().execute();
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Toast.makeText(this, "onRefresh!", Toast.LENGTH_SHORT).show();
		new RefreshTask().execute();
	}

	@Override
	public void onLastItemVisible() {
		Toast.makeText(this, "onLastItemVisible", Toast.LENGTH_SHORT).show();
	}

	public class RefreshTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			udpCheckNewThread.startCheck();
		}

		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mListView.onRefreshComplete();
			updateDevLists();
		}
	}
	
	public class RefreshAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return devs.size();
		}

		@Override
		public Object getItem(int position) {
			return devs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(type == PublicDefine.TypePlug){
				convertView = getPlugView(position, convertView);
			}else{
				convertView = getBulbView(position, convertView);
			}
			
			return convertView;
		}

		private View getPlugView(int position, View convertView) {
			final OneDev info = devs.get(position);
			
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.deviceitem_plug, null);
			}
			
			TextView holderMac = ViewHolder.get(convertView, R.id.tv_mac);
			TextView holderIp = ViewHolder.get(convertView, R.id.tv_ip);
			RadioButton open = ViewHolder.get(convertView, R.id.rb_open);
			RadioButton close = ViewHolder.get(convertView, R.id.rb_close);
			ImageView onLine = ViewHolder.get(convertView, R.id.iv_online);

			holderMac.setText(DataExchange.byteArrayToHexMacString(DataExchange.longToEightByte(info.mac)));
			holderIp.setText(info.remoteip.getHostAddress());
			OnClickListener mClickListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.rb_close){
						setPlugOff(info);
					}else{
						setPlugOn(info);
					}
				}
			};
			open.setOnClickListener(mClickListener);
			close.setOnClickListener(mClickListener);

			if(info.isLocal){
				onLine.setBackgroundResource(R.drawable.fragment_local);
			}else{
				onLine.setBackgroundResource(R.drawable.fragment_offline);
			}
			return convertView;
		}
		
		private View getBulbView(int position, View convertView) {
			OneDev info = devs.get(position);
			
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.deviceitem, null);
			}
			
			TextView holderMac = ViewHolder.get(convertView, R.id.tv_mac);
			TextView holderIp = ViewHolder.get(convertView, R.id.tv_ip);
			RadioButton white = ViewHolder.get(convertView, R.id.rb_white);
			RadioButton red = ViewHolder.get(convertView, R.id.rb_red);
			RadioButton green = ViewHolder.get(convertView, R.id.rb_green);
			RadioButton blue = ViewHolder.get(convertView, R.id.rb_blue);
			ImageView onLine = ViewHolder.get(convertView, R.id.iv_online);

			holderMac.setText(DataExchange.byteArrayToHexMacString(DataExchange.longToEightByte(info.mac)));
			holderIp.setText(info.remoteip.getHostAddress());
			white.setOnClickListener(new OnColorSelecter(0, 0, 0, 255, info.mac, info.remoteip));
			red.setOnClickListener(new OnColorSelecter(255, 0, 0, 0, info.mac, info.remoteip));
			green.setOnClickListener(new OnColorSelecter(0, 255, 0, 0, info.mac, info.remoteip));
			blue.setOnClickListener(new OnColorSelecter(0, 0, 255, 0, info.mac, info.remoteip));

			if(info.isLocal){
				onLine.setBackgroundResource(R.drawable.fragment_local);
			}else{
				onLine.setBackgroundResource(R.drawable.fragment_offline);
			}
			return convertView;
		}
		
		public class OnColorSelecter implements OnClickListener{
			
			private int red,green,blue,white;
			private long mac;
			InetAddress ip;
			@Override
			public void onClick(View v) {
				LightControlPacket packet = new LightControlPacket(ip,PublicDefine.LocalUdpPort);
				packet.setImportance(BasicPacket.ImportHigh);
				packet.lightColor(DataExchange.longToEightByte(mac), null, red, green, blue, white, 0,800);		
				udpThread.getProtocolDataList().addOnePacketToSend(packet);
			}
			
			public OnColorSelecter(int red , int green , int blue , int white , long mac , InetAddress ip) {
				this.red = red;
				this.green = green;
				this.blue = blue;
				this.white = white;
				this.mac = mac;
				this.ip = ip;
			}
		}
	}

	@Override
	public void onNewDev() {
		updateDevLists();
	}

	@Override
	public void onChangeDev() {
		updateDevLists();
	}
	
	private void updateDevLists(){
		devs.clear();
		for (OneDev dev : udpCheckNewThread.newdevs) {
			if(dev.type == type){
				devs.add(dev);
			}
		}
		
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	public void doBulbClick(View v){
		type = PublicDefine.TypeLight;
		findViewById(R.id.group_plug).setVisibility(View.GONE);
		findViewById(R.id.group_bulb).setVisibility(View.VISIBLE);
		updateDevLists();
	}
	
	public void doPlugClick(View v){
		type = PublicDefine.TypePlug;
		findViewById(R.id.group_bulb).setVisibility(View.GONE);
		findViewById(R.id.group_plug).setVisibility(View.VISIBLE);
		updateDevLists();
	}
	
	public void doCloseAll(View v){
		for (OneDev oneDev : devs) {
			setPlugOff(oneDev);
		}
	}
	
	private void setPlugOff(OneDev dev) {
		PlugControlPacket packet = new PlugControlPacket(dev.remoteip, PublicDefine.LocalUdpPort);
		packet.setImportance(BasicPacket.ImportHigh);
		packet.plugOff(DataExchange.longToEightByte(dev.mac), null);
		udpThread.getProtocolDataList().addOnePacketToSend(packet);
	}

	public void doOpenAll(View v){
		for (OneDev oneDev : devs) {
			setPlugOn(oneDev);
		}
	}

	private void setPlugOn(OneDev dev) {
		PlugControlPacket packet = new PlugControlPacket(dev.remoteip, PublicDefine.LocalUdpPort);
		packet.setImportance(BasicPacket.ImportHigh);
		packet.plugOn(DataExchange.longToEightByte(dev.mac), null);
		udpThread.getProtocolDataList().addOnePacketToSend(packet);		
	}
}
