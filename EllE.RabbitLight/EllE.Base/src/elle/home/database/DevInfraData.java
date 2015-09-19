package elle.home.database;

import java.util.ArrayList;
import java.util.List;

import elle.home.publicfun.DataExchange;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DevInfraData {

	public String TAG = "DevInfraData";
	
	public String devname;
	
	public List<OneInfraData> infraDataList = new ArrayList<OneInfraData>();
	
	
	public DevInfraData(String devname){
		this.devname = devname;
	}
	
	public byte[] getInfraData(int funid){
		for(int i=0;i<infraDataList.size();i++){
			if(infraDataList.get(i).funid == funid){
				return infraDataList.get(i).data;
			}
		}
		return null;
	}
	
	public void addOneInfraData(OneInfraData data,Context context){
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		for(int i=0;i<infraDataList.size();i++){
			if(infraDataList.get(i).funid == data.funid){
				String[] params = new String[4];
				params[3] = String.valueOf(data.mac);
				params[2] = data.devname;
				params[1] = String.valueOf(data.funid);
				params[0] = DataExchange.dbBytesToString(data.data);				
				Log.d(TAG,"重新学习存入数据funid:"+data.funid+" devname:"+params[2]+" mac:"+params[3]+" fundata"+params[0]);
				String sql = "UPDATE infradatas SET fundata = ? WHERE funid = ? AND devname = ? AND mac = ?";
				db.execSQL(sql,params);
				db.close();
				findAllData(context);
				return ;
			}
		}
		
		String[] params = new String[5];
		
		params[0] = data.devname;
		params[1] = String.valueOf(data.mac);
		params[2] = String.valueOf(data.type);
		params[3] = String.valueOf(data.funid);
		params[4] = DataExchange.dbBytesToString(data.data);
		
		Log.d(TAG,"学习存入数据funid:"+data.funid+" fundata"+params[4]);
		
		String sql = "INSERT INTO infradatas (devname,mac,type,funid,fundata) VALUES(?,?,?,?,?)";
		db.execSQL(sql,params);
		db.close();
		
		findAllData(context);
	}
	
	public void findAllData(Context context){
		String[] params = new String[1];
		params[0] = this.devname;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		infraDataList.clear();
		
		String sql = "select * from infradatas where devname = ?";
		Cursor cursor = db.rawQuery(sql, params);
		while(cursor.moveToNext()){
			OneInfraData data = new OneInfraData();
			data.devname = cursor.getString(cursor.getColumnIndex("devname"));
			data.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
			data.type = Byte.parseByte(cursor.getString(cursor.getColumnIndex("type")));
			data.funid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("funid")));
			//Log.d(TAG,"funid:"+data.funid+" infra-data:"+cursor.getString(cursor.getColumnIndex("fundata")));
			data.data = DataExchange.dbStringToBytes(cursor.getString(cursor.getColumnIndex("fundata")));
			infraDataList.add(data);
			
		}
		db.close();
		
	}
	
}
