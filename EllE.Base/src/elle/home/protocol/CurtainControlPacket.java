package elle.home.protocol;

import java.net.InetAddress;

import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class CurtainControlPacket extends BasicPacket {

	public CurtainControlPacket(InetAddress ip, int port) {
		super(ip, port);
	}
	
	public void packetCheck(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.FunCheck, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void curtainOn(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.CurtainOn, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}

	public void curtainOff(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.CurtainOff, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void curtainProgress(byte[] mac,int progress,OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.CurtainProgress, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), DataExchange.intToFourByte(progress));
		super.setListener(listener);
	}
	
	public void curtainReboot(byte[] mac, OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.CurtainReboot, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void curtainStop(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeCurtain, PublicDefine.CurtainVerOgrin, PublicDefine.CurtainStop, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
}
