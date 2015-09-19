package elle.home.protocol;

import java.net.InetAddress;

import android.util.Log;

import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class InfraControlPacket extends BasicPacket {

	public String TAG = "InfraControlPacket";
	
	public InfraControlPacket(InetAddress ip, int port) {
		super(ip, port);
	}
	
	/**
	 * 红外进入学习
	 * */
	public void infraEntryStudy(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeInfra, PublicDefine.InfraVerOgrin, PublicDefine.FunInfraStudy, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	/**
	 * 红外退出学习
	 * */
	public void infraQuitStudy(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeInfra, PublicDefine.InfraVerOgrin, PublicDefine.FunInfraQuit, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	/**
	 * 发送红外数据
	 * */
	public void sendCommand(byte[] mac,OnRecvListener listener,byte[] data){
		super.packData(PublicDefine.TypeInfra, PublicDefine.InfraVerOgrin, PublicDefine.FunInfraSendData, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), data);
		super.setListener(listener);
		Log.d(TAG,"红外发送的数据为:"+DataExchange.dbBytesToString(super.data));
	}

}
