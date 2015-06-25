package elle.home.devicetest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OneDev{
	
	public String TAG  = "OneDev";
	
	//self
	public long mac;
	public InetAddress remoteip;
	public int remoteport;
	public String devname;
	public byte type;
	public byte ver;
	public int shownum;
	public int sqliteid;
	public int visable;
	
	//public 
	public int locateId;
	public String locateNmae;
	
	//在线或者远程的判断标识
	public boolean isLocal;
	public boolean isRemote;
	public int localTimeCount;
	public int remoteTimeCount;
	
	
	public OneDev(){
		isLocal = false;
		isRemote= false;
	}
	
	public int getConnectStatus(){
		
		if(this.isLocal){
			return PublicDefine.ConnectLocal;
		}
		
		if(this.isRemote){
			return PublicDefine.ConnectRemote;
		}
		
		return PublicDefine.ConnectNull;
	}
	
	public void lineCountAdd(){
		this.localTimeCount++;
		this.remoteTimeCount++;
		
		if(this.localTimeCount>5){
			this.localTimeCount=5;
			this.isLocal = false;
		}
		if(this.remoteTimeCount>5){
			this.remoteTimeCount = 5;
			this.isRemote = false;
		}
	}
	
	public void clearRemoteTimer(){
		this.remoteTimeCount = 0;
		this.isRemote = true;
	}
	
	public void clearLocalTimer(){
		this.localTimeCount = 0;
		this.isLocal = true;
	}
	
	public void setDevRemote(boolean a){
		this.isRemote = a;
	}
	
	public boolean getDevRemote(){
		return this.isRemote;
	}
	
	public void setDevLocal(boolean a){
		this.isLocal = a;
	}
	
	public boolean getDevLocal(){
		return this.isLocal;
	}
	

	


}