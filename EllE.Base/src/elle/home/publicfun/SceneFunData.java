package elle.home.publicfun;

import java.net.InetAddress;

import android.content.Context;
import android.util.Log;

import elle.home.database.DevInfraData;
import elle.home.database.OneDev;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.InfraControlPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PlugControlPacket;

public class SceneFunData {

	public String TAG = "SceneFunData";
	
	OneDev dev;
	int funid;
	OnRecvListener listener;
	Context context;
	public SceneFunData(OneDev dev,int funid,Context context){
		this.dev = dev;
		this.funid = funid;
		this.context = context;
	}
	
	public BasicPacket getPacket(InetAddress inet,int port,OnRecvListener listener){
		BasicPacket tmp = new BasicPacket(inet,port);
		this.listener = listener;
		switch(dev.type){
		case PublicDefine.TypeLight:
			LightControlPacket lightpacket = new LightControlPacket(inet,port);
			if(inet.toString().equals("/255.255.255.255")){
				
			}else{
				Log.d(TAG,"灯泡场景执行为远程");
				lightpacket.setPacketRemote(true);
			}
			switch(funid){
			case PublicDefine.SceneLightOnIcon:
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0, 0, 0, 0xff, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightOffIcon:
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightClose(DataExchange.longToEightByte(dev.mac), listener);
				return lightpacket;
			case PublicDefine.SceneLightNullIcon:
				return null;
			case PublicDefine.SceneLightFreeIcon:
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightFree(DataExchange.longToEightByte(dev.mac), listener,true);
				return lightpacket;
			case PublicDefine.SceneLightColor1:		//橘黄色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0xff, 0x55, 0, 0, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor2:		//绿色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0x00, 0xff, 0, 0, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor3:		//蓝色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0, 0, 0xff, 00, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor4:		//红色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0xff, 0, 00, 00, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor5:		//黄色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0xaa, 0x66, 00, 00, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor6:		//青色
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0, 0xff, 0x55, 00, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor7:
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0x88, 00, 0xff, 00, 120, 500);
				return lightpacket;
			case PublicDefine.SceneLightColor8:
				lightpacket.setImportance(BasicPacket.ImportHigh);
				lightpacket.lightColor(DataExchange.longToEightByte(dev.mac), listener, 0xaa, 0, 0xaa, 00, 120, 500);
				return lightpacket;
			}
			break;
		case PublicDefine.TypePlug:
			PlugControlPacket plugpacket = new PlugControlPacket(inet,port);
			if(inet.toString().equals("/255.255.255.255")){
				
			}else{
				Log.d(TAG,"插座场景执行为远程");
				plugpacket.setPacketRemote(true);
			}
			switch(funid){
			case PublicDefine.ScenePlugOnIcon:
				plugpacket.plugOn(DataExchange.longToEightByte(dev.mac), listener);
				plugpacket.setImportance(BasicPacket.ImportHigh);
				return plugpacket;
			case PublicDefine.ScenePlugOffIcon:
				plugpacket.plugOff(DataExchange.longToEightByte(dev.mac), listener);
				plugpacket.setImportance(BasicPacket.ImportHigh);
				return plugpacket;
			case PublicDefine.ScenePlugNullIcon:
				return null;
			}
			break;
		case PublicDefine.TypeInfra:
			return null;
		case PublicDefine.TypeInfraAir:
			
			InfraControlPacket infrapacket = new InfraControlPacket(inet,port);
			if(inet.toString().equals("/255.255.255.255")){
				
			}else{
				Log.d(TAG,"插座场景执行为远程");
				infrapacket.setPacketRemote(true);
			}
			
			DevInfraData infradata = new DevInfraData(dev.devname);
			infradata.findAllData(context);
			
			switch(funid){
				case PublicDefine.SceneInfraAirCold16:
					if(infradata.getInfraData(16-15) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(16-15));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirCold24:
					if(infradata.getInfraData(24-15) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(24-15));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirCold30:
					if(infradata.getInfraData(30-15) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(30-15));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirWarm16:
					if(infradata.getInfraData(16) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(16));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirWarm24:
					if(infradata.getInfraData(24) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(24));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirWarm30:
					if(infradata.getInfraData(30) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(30));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirCloseIcon:
					if(infradata.getInfraData(31) == null){
						return null;
					}else{
						infrapacket.sendCommand(DataExchange.longToEightByte(dev.mac), listener, infradata.getInfraData(31));
						return infrapacket;
					}
				case PublicDefine.SceneInfraAirNullIcon:
					return null;
			}	
			break;
		}
		return null;
	}
	
}
