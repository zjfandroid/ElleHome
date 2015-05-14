package elle.home.protocol;

import java.net.InetAddress;

import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;

/**
 * 多路控制器、随意贴
 * @author jason
 *
 */
public class ControllersPacket extends BasicPacket {

	public ControllersPacket(InetAddress ip, int port) {
		super(ip, port);
	}
	
	/**
	 * 查询
	 * @param mac
	 * @param listener
	 */
	public void check(byte[] mac, int index, OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeController, PublicDefine.VerControllers[index], PublicDefine.FunCheck, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
	
	/**
	 * 打开
	 * @param index 第几个
	 * @param mac
	 * @param listener
	 */
	public void switchOn(int index, byte[] mac, OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeController, PublicDefine.VerControllers[index], PublicDefine.FunControllersSwitch[index], 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), new byte[]{0x01});
		super.setListener(listener);
	}
	
	/**
	 * 关闭
	 * @param index 第几个
	 * @param mac
	 * @param listener
	 */
	public void switchOff(int index, byte[] mac, OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeController, PublicDefine.VerControllers[index], PublicDefine.FunControllersSwitch[index], 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), new byte[]{0x00});
		super.setListener(listener);
	}
	
	/**
	 * 定时打开
	 * @param index 第几个
	 * @param mac 
	 * @param count 距离打开的秒数
	 * @param listener
	 */
	public void setTimeOn(int index, byte[] mac, int count, OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeController, PublicDefine.VerControllers[index], PublicDefine.FunControllersTimeOn[index], 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), DataExchange.intToFourByte(count));
		super.setListener(listener);
	}
	
	/**
	 * 定时关闭
	 * @param index 第几个
	 * @param mac
	 * @param count 距离关闭的秒数
	 * @param listener
	 */
	public void setTimeOff(int index, byte[] mac,int count,OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeController, PublicDefine.VerControllers[index], PublicDefine.FunControllersTimeOff[index], 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), DataExchange.intToFourByte(count));
		super.setListener(listener);
	}
	
	/**
	 * 清空随意贴控制器信息
	 * @param index 第几个
	 * @param mac
	 * @param listener
	 */
	public void deleteFreeStickers(int index, byte[] mac, OnRecvListener listener){
		super.packZigbeeData(PublicDefine.TypeFreeStickers, PublicDefine.VerControllers[index], PublicDefine.CLEAR_FreeStickers, 
				mac, PublicDefine.phonemac, PublicDefine.getSeq(), null);
		super.setListener(listener);
	}
}
