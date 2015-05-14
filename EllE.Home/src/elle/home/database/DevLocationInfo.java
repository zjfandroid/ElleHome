package elle.home.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DevLocationInfo {
	
	public String TAG = "DevLocationInfo";

	//某一个地点的所有设备
	public List<OneDev> devLocationList = new ArrayList<OneDev>();
	
	private DataBaseHelper dbhelper;
	
	public String locatname;
	public int locaticon;
	public Context mContext;
	
	public DevLocationInfo(String locat,int icon,Context context){
		this.locatname = locat;
		this.locaticon = icon;
		this.mContext = context;
		dbhelper = new DataBaseHelper(context);
	}
	
	public byte addLocatToDatabase(){
		
		String[] params = new String[2];
		
		params[0] = this.locatname;
		params[1] = String.valueOf(this.locaticon);
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] checkParams = new String[1];
		checkParams[0] = this.locatname;
		String checkSql = "select * from locations where locatname = ?";
		Cursor cursor = db.rawQuery(checkSql, checkParams);
		while(cursor.moveToNext()){
			db.close();
			return 1;
		}
		
		String sql = "INSERT INTO locations (locatname,locaticon) VALUES (?,?)";
		db.execSQL(sql,params);
		return 0;
	}
	
	/**
	 * 将所有的设备设置成为本地，只在初始化的时候使用，后面程序会自动刷新设备是本地或者远程
	 * */
	public synchronized void setAllDevToLocal(){
		for(int i=0;i<devLocationList.size();i++){
			devLocationList.get(i).setDevLocal(true);
		}
	}
	
	/**
	 * 删除地点，将地点表和设备列表都要删除
	 * */
	public synchronized void deleteFromDatabase(Context context){
		String[] tmp = new String[1];
		tmp[0] = locatname;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "DELETE FROM devices WHERE locatname = ?";
		String sql2 = "DELETE FROM locations WHERE locatname = ?";
		
		db.execSQL(sql, tmp);
		db.execSQL(sql2, tmp);
		
		db.close();
		
	}
	
	
	public synchronized void freshAllDev(){
		
		devLocationList.clear();
		
		String[] tmp = new String[1];
		tmp[0] = locatname;
		
		SQLiteDatabase db = null;
		db = dbhelper.getReadableDatabase();
		
		String sql = "select * from devices where locatname = ?";
		Cursor cursor = db.rawQuery(sql, tmp);
		while(cursor.moveToNext()){
			OneDev oneDev = new OneDev();
			try {
				getDevInfos(cursor, oneDev);	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			devLocationList.add(oneDev);
		}
		Log.d(TAG,"地点："+locatname+" 设备数量："+devLocationList.size());
	}

	private void getDevInfos(Cursor cursor, OneDev oneDev)
			throws UnknownHostException {
		oneDev.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
		oneDev.remoteip = InetAddress.getByName(cursor.getString(cursor.getColumnIndex("remoteip")));
		oneDev.remoteport = Integer.parseInt(cursor.getString(cursor.getColumnIndex("remoteport")));
		oneDev.devname = cursor.getString(cursor.getColumnIndex("devname"));
		oneDev.type = Byte.parseByte(cursor.getString(cursor.getColumnIndex("type")));
		oneDev.ver = Byte.parseByte(cursor.getString(cursor.getColumnIndex("ver")));
		oneDev.shownum = Integer.parseInt(cursor.getString(cursor.getColumnIndex("shownum")));
		oneDev.sqliteid = cursor.getInt(cursor.getColumnIndex("id"));
		oneDev.visable = Integer.parseInt(cursor.getString(cursor.getColumnIndex("visable")));
		oneDev.locateId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("locatid")));
		oneDev.locateNmae = cursor.getString(cursor.getColumnIndex("locatname"));
		oneDev.setCameraDeviceID(cursor.getString(cursor.getColumnIndex("deviceid")));	
		oneDev.setCameraUserName(cursor.getString(cursor.getColumnIndex("username")));	
		oneDev.setCameraPassWord(cursor.getString(cursor.getColumnIndex("password")));
	}
	
	public synchronized void getAllDevByLocationInfo(String name){
		
		devLocationList.clear();
		
		String[] tmp = new String[1];
		tmp[0] = name;
		
		SQLiteDatabase db = null;
		db = dbhelper.getReadableDatabase();
		
		String sql = "select * from devices where locatname = ?";
		Cursor cursor = db.rawQuery(sql, tmp);
		while(cursor.moveToNext()){
			OneDev oneDev = new OneDev();
			try {
				getDevInfos(cursor, oneDev);	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			devLocationList.add(oneDev);
			Log.d(TAG,"查询设备mac："+oneDev.mac+" remoteIp:"+oneDev.remoteip.toString());
		}
		Log.d(TAG,"地点："+name+" 设备数量："+devLocationList.size());
	}
	
}
