package elle.home.protocol;

import java.net.InetAddress;

import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class PlugControlPacket extends BasicPacket {

	public PlugControlPacket(InetAddress ip, int port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}
	
	public void plugOn(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypePlug, PublicDefine.PlugVerOgrin, PublicDefine.FunPlugOn, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}

	public void plugOff(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypePlug, PublicDefine.PlugVerOgrin, PublicDefine.FunPlugOff, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void plugSetOpen(byte[] mac,int count,OnRecvListener listener){
		super.packData(PublicDefine.TypePlug, PublicDefine.PlugVerOgrin, PublicDefine.FunPlugSetOpen, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), DataExchange.intToFourByte(count));
		super.setListener(listener);
	}
	
	public void plugSetClose(byte[] mac,int count,OnRecvListener listener){
		super.packData(PublicDefine.TypePlug, PublicDefine.PlugVerOgrin, PublicDefine.FunPlugSetClose, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), DataExchange.intToFourByte(count));
		super.setListener(listener);
	}
	
	public void plugCheck(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypePlug, PublicDefine.PlugVerOgrin, PublicDefine.FunCheck, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
}
