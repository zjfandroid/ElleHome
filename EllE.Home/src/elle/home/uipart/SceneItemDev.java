package elle.home.uipart;

import java.net.InetAddress;
import java.net.UnknownHostException;

import elle.home.app.R;
import elle.home.database.AllLocationInfo;
import elle.home.database.OneDev;
import elle.home.database.OneSceneData;
import elle.home.database.SceneData;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.PublicDefine;
import elle.home.publicfun.SceneFunData;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SceneItemDev extends LinearLayout {
	
	public String TAG = "SceneItemDev";

	private ImageView scene_item_iv;
	private TextView scene_item_tv;
	private ImageView scene_item_tips_iv;
	private ImageView scene_item_connect;

	
	//主要显示的item
	public FrameLayout framelayout;

	//下面动态加载的部件
	private LinearLayout fragment_item_dev_funlayout;
	
	//手指按下的point
	int pressx;
	
	//是否启用了功能加载界面
	boolean isFunAdd;
	
	private OneDev dev;
	private Context mcontext;
	private SceneData scenedata;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 0:		//无此设备
				Log.d(TAG,"mac"+dev.mac+" 无此设备");
				scene_item_connect.setImageDrawable(getResources().getDrawable(R.drawable.scene_connect_nodev));
				break;
			case 1:		//成功
				Log.d(TAG,"mac"+dev.mac+" 成功");
				scene_item_connect.setImageDrawable(getResources().getDrawable(R.drawable.scene_fun_succeed));
				break;
			case 2:		//无连接
				Log.d(TAG,"mac"+dev.mac+" 无连接");
				scene_item_connect.setImageDrawable(getResources().getDrawable(R.drawable.scene_connect_fail));
				break;
			case 3:
				Log.d(TAG,"mac"+dev.mac+" 连接中");
				scene_item_connect.setImageDrawable(getResources().getDrawable(R.drawable.scene_connecting));
				break;
			case 4:
				Log.d(TAG,"mac"+dev.mac+" 无数据发送");
				scene_item_connect.setImageDrawable(getResources().getDrawable(R.drawable.scene_warning));
				break;
			}
		}
		
	};
	
	public SceneItemDev(Context context,OneDev dev,SceneData scenedata) {
		super(context);
		// TODO Auto-generated constructor stub
		this.dev = dev;
		this.mcontext = context;
		this.scenedata = scenedata;
		isFunAdd = false;
		initview(context);
	}
	
	public void initview(Context context){
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.scene_item_dev, this);
		
		this.scene_item_iv = (ImageView)this.findViewById(R.id.scene_item_iv);
		this.scene_item_tv = (TextView)this.findViewById(R.id.scene_item_tv);

		this.scene_item_tips_iv = (ImageView)this.findViewById(R.id.scene_item_tips_iv);
		this.scene_item_connect = (ImageView)this.findViewById(R.id.scene_item_connect_iv);
		this.fragment_item_dev_funlayout = (LinearLayout)this.findViewById(R.id.fragment_item_dev_funlayout);
		
		this.framelayout = (FrameLayout)this.findViewById(R.id.itemlayout);
		
		for(int i=0;i<scenedata.scenelist.size();i++){
			if(scenedata.scenelist.get(i).mac == dev.mac){
				if(scenedata.scenelist.get(i).devname!=null){
					if(scenedata.scenelist.get(i).devname.equalsIgnoreCase(dev.devname)){
						//this.scene_item_iv.setBackgroundResource(PublicDefine.getFragmentIconByType(dev.type));
						this.scene_item_iv.setImageDrawable(this.getResources().getDrawable(PublicDefine.getLittleIconByType(dev.type)));
						this.scene_item_tv.setText(dev.devname);
						//this.scene_item_tips_tv.setText(" ");
						this.scene_item_tips_iv.setImageDrawable(this.getResources().getDrawable(PublicDefine.getIconByTypeFun(dev.type,scenedata.scenelist.get(i).funid)));
						this.scene_item_connect.setImageDrawable(this.getResources().getDrawable(R.drawable.scene_item_empty));
						break;
					}
				}
				
			}
			if(i == (scenedata.scenelist.size()-1)){
				//this.scene_item_iv.setBackgroundResource(PublicDefine.getFragmentIconByType(dev.type));
				this.scene_item_iv.setImageDrawable(this.getResources().getDrawable(PublicDefine.getLittleIconByType(dev.type)));
				this.scene_item_tv.setText(dev.devname);
				//this.scene_item_tips_tv.setText(" ");
				this.scene_item_tips_iv.setImageDrawable(this.getResources().getDrawable(R.drawable.scene_cancel));
				this.scene_item_connect.setImageDrawable(this.getResources().getDrawable(R.drawable.scene_item_empty));
			}
		}
		
	}
	
	public void togShowFun(){
		fragment_item_dev_funlayout.removeAllViews();
		if(isFunAdd){
			isFunAdd = false;
			
		}else{
			isFunAdd = true;
			SceneItemDevFun devfun = new SceneItemDevFun(mcontext,dev); 
			devfun.setOnClickItem(new SceneItemDevFun.OnClickFun() {
				
				@Override
				public void onclick(int a, int size,OneDev dev) {
					// TODO Auto-generated method stub
					fragment_item_dev_funlayout.removeAllViews();
					isFunAdd=false;
					addSceneFunToDB(a,dev);
				}
			});
			fragment_item_dev_funlayout.addView(devfun);
		}
	}
	
	/**
	 * 更新场景动作
	 * */
	public void addSceneFunToDB(int funid,OneDev dev){
		Log.d(TAG,"funid:"+funid+" devmac:"+dev.mac+" devname:"+dev.devname);
		
		for(int i=0;i<scenedata.scenelist.size();i++){
			Log.d(TAG,"scene data list:"+i+" size:"+scenedata.scenelist.size()+" name:"+scenedata.scenelist.get(i).devname);
			if(scenedata.scenelist.get(i).devname!=null){
				if(scenedata.scenelist.get(i).mac == dev.mac&&scenedata.scenelist.get(i).devname.equalsIgnoreCase(dev.devname)){
					
					OneSceneData onedata = new OneSceneData();
					onedata.commandData = new byte[10];
					onedata.funid = funid;
					onedata.mac = dev.mac;
					onedata.devname = dev.devname;
					scenedata.updateOneSceneData(onedata);
					
					scenedata.scenelist.get(i).funid = funid;
					scenedata.scenelist.get(i).commandData = new byte[10];
					this.scene_item_tips_iv.setImageDrawable(this.getResources().getDrawable(PublicDefine.getIconByTypeFun(dev.type,scenedata.scenelist.get(i).funid)));
					Log.d(TAG,"更新场景数据ok");
					break;
				}
			}

		}
	}
	
	public BasicPacket runFun(AllLocationInfo info){
		OneDev tmp = info.getDevByMac(dev.mac);
		if(tmp == null){
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
			Log.d(TAG,"mac:"+dev.mac+" 不存在");
			return null;
		}
		
		InetAddress inet=null;
		int port = 0;
		
		if(tmp.getConnectStatus() == PublicDefine.ConnectNull){
			Message msg = new Message();
			msg.what = 2;
			handler.sendMessage(msg);
			Log.d(TAG,"mac:"+dev.mac+" 不在线");
			return null;
		}else{
			if(tmp.getConnectStatus() == PublicDefine.ConnectRemote){
				inet = tmp.remoteip;
				port = tmp.remoteport;
				Log.d(TAG,"mac:"+dev.mac+" 远程"+" ip:"+tmp.remoteip+" port:"+tmp.remoteport);
			}else{
				try {
					inet = InetAddress.getByName("255.255.255.255");
					Log.d(TAG,"mac:"+dev.mac+" 本地");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				port = 5880;
			}
		}
		
		for(int i=0;i<scenedata.scenelist.size();i++){
			if(scenedata.scenelist.get(i).mac == dev.mac){
				Log.d(TAG,"mac:"+dev.mac+" funid:"+scenedata.scenelist.get(i).funid);
				SceneFunData data = new SceneFunData(dev,scenedata.scenelist.get(i).funid,mcontext);
				BasicPacket xtmp = data.getPacket(inet, port, new OnRecvListener(){

					@Override
					public void OnRecvData(PacketCheck packetcheck) {
						// TODO Auto-generated method stub
						if(packetcheck!=null){
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
						}else{
							Message msg = new Message();
							msg.what = 2;
							handler.sendMessage(msg);
						}
					}
					
				}); 
				if(xtmp == null){
					Message xmsg = new Message();
					xmsg.what = 4;
					handler.sendMessage(xmsg);
				}else{
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}
				return xtmp;
				
			}
		}
		return null;
	}
	
	/**
	 * 显示需要增加的功能
	 * */
	public void showAddFun(){
		isFunAdd = true;
		this.fragment_item_dev_funlayout.removeAllViews();
		SceneItemDevFun devfun = new SceneItemDevFun(mcontext,dev); 
		devfun.setOnClickItem(new SceneItemDevFun.OnClickFun() {
			
			@Override
			public void onclick(int a, int size,OneDev dev) {
				// TODO Auto-generated method stub
				fragment_item_dev_funlayout.removeAllViews();
				isFunAdd=false;
				addSceneFunToDB(a,dev);
			}
		});
		fragment_item_dev_funlayout.addView(devfun);
	}

	/**
	 * 隐藏本地的功能
	 * */
	public void removeAddFun(){
		isFunAdd = false;
		this.fragment_item_dev_funlayout.removeAllViews();
	}
	
}
