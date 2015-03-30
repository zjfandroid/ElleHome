package elle.home.database;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;

public class OneDev implements Serializable{
	
	private static final long serialVersionUID = 2387225527198088687L;

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
	
	//Camera
	private String mCameraDeviceID;
	private String mCameraUserName;
	private String mCameraPassWord;
	
	//public 
	public int locateId;
	public String locateNmae;
	
	//在线或者远程的判断标识
	public boolean isLocal;
	public boolean isRemote;
	public int localTimeCount;
	public int remoteTimeCount;
	
	public String function;
	
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
	
	/**
	 * 更新数据库中的远程ip和端口信息
	 * */
	public void updateDevRemoteIp(InetAddress ip,int port,Context context){
		
		this.remoteip = ip;
		this.remoteport = port;
		
		String[] params = new String[3];
		String tmp = ip.toString().substring(1, ip.toString().length());
		Log.d(TAG,"updateDevRemoteIp:"+tmp);
		params[0] = tmp;
		params[1] = String.valueOf(port);
		params[2] = String.valueOf(this.mac);
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "UPDATE devices SET remoteip = ? ,remoteport = ? where mac = ?";
		db.execSQL(sql,params);
		db.close();
	}

	/**
	 * 设置是否可摇一摇
	 * @param tmp
	 */
	public void setCanShake(Context context, boolean tmp) {
		String[] params = new String[2];
		Log.d(TAG,"updateDevRemoteIp:"+tmp);
		if(tmp){
			params[0] = PublicDefine.CONFIG_SHAKE_ON;
		}else{
			params[0] = PublicDefine.CONFIG_SHAKE_OFF;
		}
		params[1] = String.valueOf(this.mac);
		ShowInfo.printLogW("________params[0]_________" + params[0]);
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "UPDATE devices SET function = ? where mac = ?";
		db.execSQL(sql,params);
		db.close();
	}
	
	public boolean getCanShake(Context context){
		String[] params = new String[2];
		params[0] = String.valueOf(mac);
		params[1] = devname;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		String canShake = "";
		String sql = "select * from devices where mac = ? and devname = ?";
		Cursor cursor = db.rawQuery(sql, params);
		while(cursor.moveToNext()){
			canShake = cursor.getString(cursor.getColumnIndex("function"));	
		}
		
		if(PublicDefine.CONFIG_SHAKE_ON.equalsIgnoreCase(canShake)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 从数据库将单个信息取回来，用mac地址和设备名字
	 * */
	public void getFromDatabase(long mac,String devname,Context context){
		String[] params = new String[2];
		params[0] = String.valueOf(mac);
		params[1] = devname;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		String sql = "select * from devices where mac = ? and devname = ?";
		Cursor cursor = db.rawQuery(sql, params);
		while(cursor.moveToNext()){
			try {
				this.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
				this.remoteip = InetAddress.getByName(cursor.getString(cursor.getColumnIndex("remoteip")));
				this.remoteport = Integer.parseInt(cursor.getString(cursor.getColumnIndex("remoteport")));
				this.devname = cursor.getString(cursor.getColumnIndex("devname"));
				this.type = Byte.parseByte(cursor.getString(cursor.getColumnIndex("type")));
				this.shownum = Integer.parseInt(cursor.getString(cursor.getColumnIndex("shownum")));
				this.sqliteid = cursor.getInt(cursor.getColumnIndex("id"));
				this.visable = Integer.parseInt(cursor.getString(cursor.getColumnIndex("visable")));
				this.locateId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("locatid")));
				this.locateNmae = cursor.getString(cursor.getColumnIndex("locatname"));	
				this.mCameraDeviceID = cursor.getString(cursor.getColumnIndex("deviceid"));	
				this.mCameraUserName = cursor.getString(cursor.getColumnIndex("username"));	
				this.mCameraPassWord = cursor.getString(cursor.getColumnIndex("password"));	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			Log.d(TAG,"查询设备mac："+this.mac+" remoteIp:"+this.remoteip.toString()+" orgin ip:"+cursor.getString(cursor.getColumnIndex("remoteip")));
			
		}
		
		db.close();
	}
	
	/*
	 * 从数据库将单个信息刷取回来
	 * */
	public void getFromDatabase(long mac,Context context){
		String[] params = new String[1];
		params[0] = String.valueOf(mac);
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		String sql = "select * from devices where mac = ?";
		Cursor cursor = db.rawQuery(sql, params);
		while(cursor.moveToNext()){
			try {
				this.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
				this.remoteip = InetAddress.getByName(cursor.getString(cursor.getColumnIndex("remoteip")));
				this.remoteport = Integer.parseInt(cursor.getString(cursor.getColumnIndex("remoteport")));
				this.devname = cursor.getString(cursor.getColumnIndex("devname"));
				this.type = Byte.parseByte(cursor.getString(cursor.getColumnIndex("type")));
				this.shownum = Integer.parseInt(cursor.getString(cursor.getColumnIndex("shownum")));
				this.sqliteid = cursor.getInt(cursor.getColumnIndex("id"));
				this.visable = Integer.parseInt(cursor.getString(cursor.getColumnIndex("visable")));
				this.locateId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("locatid")));
				this.locateNmae = cursor.getString(cursor.getColumnIndex("locatname"));			
				this.mCameraDeviceID = cursor.getString(cursor.getColumnIndex("deviceid"));		
				this.mCameraUserName = cursor.getString(cursor.getColumnIndex("username"));	
				this.mCameraPassWord = cursor.getString(cursor.getColumnIndex("password"));	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
			Log.d(TAG,"查询设备mac："+this.mac+" remoteIp:"+this.remoteip.toString()+" orgin ip:"+cursor.getString(cursor.getColumnIndex("remoteip")));
			
		}
		
		db.close();
		
	}
	
	/**
	 * 将这个定义的设备从数据库中删除
	 * */
	public void delFromDatabase(Context context){
		String[] params = new String[1];
		params[0] = String.valueOf(this.mac);
		

		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "DELETE FROM devices WHERE mac = ?";
		db.execSQL(sql, params);

		switch(this.type){
		
		case PublicDefine.TypeInfra:
			String xsql = "DELETE FROM infradatas WHERE mac = ?";
			db.execSQL(xsql, params);
			break;
			
		}
		
		String ssql = "DELETE FROM scenedatas WHERE mac = ?";
		db.execSQL(ssql, params);
		
		db.close();
		
	}
	
	/**
	 * 将这个定义的设备从数据库中删除,由名字和mac一起决定
	 * */
	public void delFromDatabaseWithName(Context context){
		
		String[] params = new String[2];
		params[0] = String.valueOf(this.mac);
		params[1] = this.devname;

		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "DELETE FROM devices WHERE mac = ? AND devname = ?";
		db.execSQL(sql, params);
		
		switch(this.type){
		
		case PublicDefine.TypeInfraAir:
		case PublicDefine.TypeInfraTv:
			String xsql = "DELETE FROM infradatas WHERE mac = ? AND devname = ?";
			db.execSQL(xsql, params);
			break;
			
		}
		
		String ssql = "DELETE FROM scenedatas WHERE mac = ? AND devname = ?";
		db.execSQL(ssql, params);
		
		db.close();
		
	}
	
	/**
	 *将这个定义的设备加入到数据库中 
	 * **/
	public byte addToDatabase(Context context){
		
		locateId = 0;
		visable = 1;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		//检查是否存在了同名设备
		String[] checkParams = new String[1];
		checkParams[0] = this.devname;
		String checkSql = "select * from devices where devname = ?";
		Cursor cursor = db.rawQuery(checkSql, checkParams);
		while(cursor.moveToNext()){
			db.close();
			return 1;
		}
		
		String[] params = new String[13];
		
		params[0] = String.valueOf(this.mac);
		params[1] = "255.255.255.255";
		params[2] = "30001";
		params[3] = devname;
		params[4] = String.valueOf(type);
		params[5] = String.valueOf(ver);
		params[6] = String.valueOf(shownum);
		params[7] = String.valueOf(visable);
		params[8] = String.valueOf(locateId);
		params[9] = locateNmae;
		params[10] = mCameraDeviceID;
		params[11] = mCameraUserName;
		params[12] = mCameraPassWord;

		String sql = "INSERT INTO devices (mac,remoteip,remoteport,devname,type,ver,shownum,visable,locatid,locatname,deviceid,username,password) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.execSQL(sql, params);
		db.close();
		return 0;
	}

	public String getCameraDeviceID() {
		return mCameraDeviceID;
	}

	public void setCameraDeviceID(String mCameraDeviceID) {
		this.mCameraDeviceID = mCameraDeviceID;
	}

	public String getCameraUserName() {
		return mCameraUserName;
	}

	public void setCameraUserName(String mCameraUserName) {
		this.mCameraUserName = mCameraUserName;
	}

	public String getCameraPassWord() {
		return mCameraPassWord;
	}

	public void setCameraPassWord(String mCameraPassWord) {
		this.mCameraPassWord = mCameraPassWord;
	}

}
