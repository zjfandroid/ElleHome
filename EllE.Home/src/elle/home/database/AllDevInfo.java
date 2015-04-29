package elle.home.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AllDevInfo {
	
	public String TAG = "AllDevInfo";

	public List<OneDev> alldevinfo= new ArrayList<OneDev>();
	
	Context mContext;
	private DataBaseHelper dbhelper;
	
	public AllDevInfo(Context context){
		mContext = context;
		dbhelper = new DataBaseHelper(context);
	}
	
	public void getAllDev(){
		SQLiteDatabase db = null;
		db = dbhelper.getReadableDatabase();
		String sql = "select * from devices";
		Cursor cursor = db.rawQuery(sql, null);
		alldevinfo.clear();
		while(cursor.moveToNext()){
			OneDev oneDev = new OneDev();
			try {
				oneDev.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
				oneDev.remoteip = InetAddress.getByName(cursor.getString(cursor.getColumnIndex("remoteip")));
				oneDev.remoteport = Integer.parseInt(cursor.getString(cursor.getColumnIndex("remoteport")));
				oneDev.devname = cursor.getString(cursor.getColumnIndex("devname"));
				oneDev.type = Byte.parseByte(cursor.getString(cursor.getColumnIndex("type")));
				oneDev.shownum = Integer.parseInt(cursor.getString(cursor.getColumnIndex("shownum")));
				oneDev.sqliteid = cursor.getInt(cursor.getColumnIndex("id"));
				oneDev.visable = Integer.parseInt(cursor.getString(cursor.getColumnIndex("visable")));
				oneDev.locateId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("locatid")));
				oneDev.locateNmae = cursor.getString(cursor.getColumnIndex("locatname"));
				
				//add by xym 
				oneDev.function = cursor.getString(cursor.getColumnIndex("function"));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			alldevinfo.add(oneDev);
		}
		Log.d(TAG,"得到所有设备的数量为："+alldevinfo.size());
	}
	
}
