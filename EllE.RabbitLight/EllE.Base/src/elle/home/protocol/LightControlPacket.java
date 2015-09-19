package elle.home.protocol;

import java.net.InetAddress;

import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

public class LightControlPacket extends BasicPacket {

	public LightControlPacket(InetAddress ip, int port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}
	
	public void lightOpen(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunLightOpen, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void lightClose(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunLightClose, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	public void lightFree(byte[] mac,OnRecvListener listener,boolean status){
		byte[] tmp = new byte[1];
		if(status){
			tmp[0] = 0x01;
		}else{
			tmp[0] = 0x00;
		}
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunLightRandom, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), tmp);
		super.setListener(listener);
	}

	/**
	 * 负离子等级
	 * @param mac
	 * @param listener
	 */
	public void setAnionLevel(byte[] mac,OnRecvListener listener, byte level){
		byte[] tmp = new byte[1];
		tmp[0] = level;
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunSetAnionLevel, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), tmp);
		super.setListener(listener);
	}

	public void lightColor(byte[] mac,OnRecvListener listener,int red,int green,int blue,int white,int lux,int time){
		byte[] xdata = new byte[12];
		System.arraycopy(DataExchange.intToTwoByte(red), 0, xdata, 0, 2);
		System.arraycopy(DataExchange.intToTwoByte(green), 0, xdata, 2, 2);
		System.arraycopy(DataExchange.intToTwoByte(blue), 0, xdata, 4, 2);
		System.arraycopy(DataExchange.intToTwoByte(white), 0, xdata, 6, 2);
		System.arraycopy(DataExchange.intToTwoByte(time), 0, xdata, 8, 2);
		System.arraycopy(DataExchange.intToTwoByte(lux), 0, xdata, 10, 2);
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunLightColor, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), xdata);
		super.setListener(listener);
	}
	
	public void lightSleep(byte[] mac,OnRecvListener listener,int time){
		byte[] xdata = new byte[2];
		System.arraycopy(DataExchange.intToTwoByte(time), 0, xdata, 0, 2);
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunLightSleep, mac, PublicDefine.getPhoneMac(), PublicDefine.getSeq(), xdata);
		super.setListener(listener);
	}
	
	public void lightCheck(byte[] mac,OnRecvListener listener){
		super.packData(PublicDefine.TypeLight, PublicDefine.LightVerOrgin, PublicDefine.FunCheck, mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
}








