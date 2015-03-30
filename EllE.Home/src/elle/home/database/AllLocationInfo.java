package elle.home.database;

import java.util.ArrayList;
import java.util.List;

import elle.home.app.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AllLocationInfo {
	
	public String TAG = "AllLocationInfo";

	public List<DevLocationInfo> allinfo = new ArrayList<DevLocationInfo>();
	
	private DataBaseHelper dbhelper;
	private Context mContext;
	
	public AllLocationInfo(Context context){
		mContext = context;
		dbhelper = new DataBaseHelper(context);
	};
	
	public OneDev getDevByMac(long mac){
		OneDev tmp = null;
		for(int i=0;i<allinfo.size();i++){
			for(int x=0;x<allinfo.get(i).devLocationList.size();x++){
				if(allinfo.get(i).devLocationList.get(x).mac == mac){
					return allinfo.get(i).devLocationList.get(x);
				}
			}
		}
		return null;
	}
	
	/**得到所有地点的信息
	 * 
	 */
	public synchronized void findAllLocationInfo(){
		
		allinfo.clear();
		
		SQLiteDatabase db = null;
		String sql = "select * from locations";
		db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			DevLocationInfo tmp = new DevLocationInfo(cursor.getString(cursor.getColumnIndex("locatname")),
					Integer.parseInt(cursor.getString(cursor.getColumnIndex("locaticon"))),mContext);
			allinfo.add(tmp);
			tmp.getAllDevByLocationInfo(tmp.locatname);
		}
		if(allinfo.size()==0){
			//加入一个默认的家庭
			String[] name = new String[2];
			//name[0] = "家里";
			name[0] = mContext.getResources().getString(R.string.location_home);
			name[1] = "0";
			if(addLocation(name)==true){
				Log.d(TAG,"默认添加的地点成功");
				DevLocationInfo tmp = new DevLocationInfo(name[0],0,mContext);
				allinfo.add(tmp);
			}
		}
	}
	
	/**添加一个地点
	 * @param name
	 * @return
	 */
	public synchronized boolean addLocation(String[] name){
		SQLiteDatabase db = null;
		db = dbhelper.getWritableDatabase();
		String sql = "insert into locations (locatname,locaticon) values(?,?)";
		db.execSQL(sql,name);
		return true;
	}
	
}
