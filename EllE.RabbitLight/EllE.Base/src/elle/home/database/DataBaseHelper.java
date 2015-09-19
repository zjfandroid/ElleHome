package elle.home.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	public static final String dbname = "EllEHomeApp.db";
	public static final int version = 1;
	
	public static final String sql1 = "CREATE TABLE IF NOT EXISTS devices ("+
			"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
			"mac TEXT,remoteip TEXT,remoteport TEXT,"+
			"devname TEXT,type TEXT,ver TEXT,shownum TEXT,visable TEXT,"+
			"locatid TEXT,locatname TEXT,deviceid TEXT,username TEXT,password TEXT, function TEXT)";
	
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}
	
	public DataBaseHelper(Context context){
		super(context,dbname,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql2 = "CREATE TABLE IF NOT EXISTS locations ("+
				"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
				"locatname TEXT,locaticon TEXT)";
		
		String sql3 = "CREATE TABLE IF NOT EXISTS scenes("+
				"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"scenename TEXT,sceneicon TEXT)";
		
		String sql4 = "CREATE TABLE IF NOT EXISTS scenedatas(" +
				"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"scenename TEXT,mac TEXT,devname TEXT,funid TEXT,commandData TEXT)";
		
		String sql5 = "CREATE TABLE IF NOT EXISTS infradatas(" +
				"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"devname TEXT,mac TEXT,type TEXT,funid TEXT,fundata TEXT)";
		
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		
	}

	/**
	 * 
	 *将现有表重命名为临时表；
	 *创建新表；
 	 *将临时表的数据导入新表；
	 *删除临时表。
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//	    if (oldVersion==1){
//	        db.execSQL("ALTER TABLE devices RENAME TO t_region_temp");
//	        db.execSQL(sql1);
//	        db.execSQL("insert into devices(id, mac, remoteip, remoteport, devname, type, ver, shownum, visable, locatid, locatname) " 
//	            + "select id, mac, remoteip, remoteport, devname, type, ver, shownum, visable, locatid, locatname from t_region_temp");
//	        db.execSQL("DROP TABLE t_region_temp");
//	      }
	}

}
