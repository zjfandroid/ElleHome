package elle.home.database;

import java.util.ArrayList;
import java.util.List;

import elle.home.publicfun.DataExchange;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SceneData {
	
	public String TAG = "SceneData";

	public String sceneName;
	public int sceneicon;
	private Context context;
	public List<OneSceneData> scenelist = new ArrayList<OneSceneData>();
	
	public SceneData(String name,int sceneicon,Context context){
		this.sceneName = name;
		this.sceneicon = sceneicon;
		this.context = context;
	}
	
	public void getAllScene(){
		
		String[] params = new String[1];
		params[0] = this.sceneName;
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		String sql = "select * from scenedatas where scenename = ?";
		Cursor  cursor = db.rawQuery(sql, params);
		
		scenelist.clear();
		
		while(cursor.moveToNext()){
			OneSceneData onescene = new OneSceneData();
			onescene.mac = Long.parseLong(cursor.getString(cursor.getColumnIndex("mac")));
			onescene.devname = cursor.getString(cursor.getColumnIndex("devname"));
			onescene.funid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("funid")));
			onescene.commandData = DataExchange.dbStringToBytes(cursor.getString(cursor.getColumnIndex("commandData")));
			scenelist.add(onescene);
		}
		db.close();
		Log.d(TAG,"场景:"+this.sceneName+" 有场景:"+scenelist.size());
		
	}
	
	public void updateOneSceneData(OneSceneData a){
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] params = new String[5];
		params[0] = String.valueOf(a.funid);
		params[1] = DataExchange.dbBytesToString(a.commandData);
		params[2] = String.valueOf(a.mac);
		params[3] = a.devname;
		params[4] = this.sceneName;
		
		String sql = "UPDATE scenedatas SET funid = ? ,commandData = ? where mac = ? and devname = ? and scenename = ?";
		db.execSQL(sql,params);
		
		db.close();
		
	}
	
	/**
	 * 删除掉某个场景
	 * */
	public void delSceneData(){
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] params = new String[1];
		params[0] = this.sceneName;
		
		String sql = "DELETE FROM scenedatas WHERE scenename = ?";
		String sql2 = "DELETE FROM scenes WHERE scenename = ?";
		db.execSQL(sql,params);
		db.execSQL(sql2,params);
		db.close();
		
	}
	
	public void addOneScene(OneSceneData a){
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] params = new String[5];
		params[0] = this.sceneName;
		params[1] = String.valueOf(a.mac);
		params[2] = a.devname;
		params[3] = String.valueOf(a.funid);
		params[4] = DataExchange.dbBytesToString(a.commandData);
		
		String sql = "INSERT INTO scenedatas (scenename,mac,devname,funid,commandData) VALUES (?,?,?,?,?)";
		db.execSQL(sql, params);
		db.close();
	}
	
}
