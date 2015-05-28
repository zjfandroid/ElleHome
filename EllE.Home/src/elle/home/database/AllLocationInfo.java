package elle.home.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import elle.home.app.smart.R;
import elle.home.utils.ShowInfo;

public class AllLocationInfo {
	
	public String TAG = "AllLocationInfo";
	public static final String KEY_LOCATION = "locaticon";
	public static final String KEY_LOCATNAME = "locatname";

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
	
	public synchronized JSONObject getJSONData() throws JSONException{
		if(null == allinfo || allinfo.isEmpty()){
			return null;
		}
		
		JSONArray infoArray = new JSONArray();
		for (DevLocationInfo devLocationInfo : allinfo) {
			JSONObject json = new JSONObject();
			json.put(KEY_LOCATION, devLocationInfo.locaticon);
			json.put(KEY_LOCATNAME, devLocationInfo.locatname);
			
			JSONArray jsonArray = new JSONArray();
			for(OneDev oneDev: devLocationInfo.devLocationList){
				JSONObject value = new JSONObject();
				value.put("localTimeCount", oneDev.localTimeCount);
				value.put("locateId", oneDev.locateId);
				value.put("mac", oneDev.mac);
				value.put("remoteport", oneDev.remoteport);
				value.put("remoteTimeCount", oneDev.remoteTimeCount);
				value.put("shownum", oneDev.shownum);
				value.put("sqliteid", oneDev.sqliteid);
				value.put("visable", oneDev.visable);
				value.put("devname", oneDev.devname);
				value.put("function", oneDev.function);
				value.put("locateNmae", oneDev.locateNmae);
				value.put("remoteip", oneDev.remoteip.getHostAddress());
				value.put("type", oneDev.type);
				value.put("ver", oneDev.ver);
				value.put("CameraDeviceID", oneDev.getCameraDeviceID());
				value.put("CameraUserName", oneDev.getCameraUserName());
				value.put("CameraPassWord", oneDev.getCameraPassWord());
				jsonArray.put(value);
			}
			
			json.put("devinfo", jsonArray);
			infoArray.put(json);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("locatinfo", infoArray);
		
		return obj;
	}

	public synchronized void recoverFromJSONData(JSONObject jsonObj) throws JSONException, UnknownHostException 
	{
		JSONArray infoArray = jsonObj.getJSONArray("locatinfo");
		
		int size = infoArray.length();
		for (int i = 0; i<size; i++) {
			JSONObject value = infoArray.getJSONObject(i);
			DevLocationInfo devLocationInfo = new DevLocationInfo(value.getString(KEY_LOCATNAME), value.getInt(KEY_LOCATION), mContext);
			devLocationInfo.deleteFromDatabase(mContext);
			devLocationInfo.addLocatToDatabase();
			
			JSONArray jsonArray = value.getJSONArray("devinfo");
			int len = jsonArray.length();
			for (int j = 0; j < len; j++) {
				JSONObject dev = jsonArray.getJSONObject(j);
				OneDev oneDev = new OneDev();
				oneDev.devname = dev.getString("devname");
				oneDev.function = dev.getString("function");
				oneDev.locateNmae = dev.getString("locateNmae");
				oneDev.localTimeCount = dev.getInt("localTimeCount");
				oneDev.locateId = dev.getInt("locateId");
				oneDev.remoteport = dev.getInt("remoteport");
				oneDev.remoteTimeCount = dev.getInt("remoteTimeCount");
				oneDev.shownum = dev.getInt("shownum");
				oneDev.sqliteid = dev.getInt("sqliteid");
				oneDev.visable = dev.getInt("visable");
				oneDev.ver = (byte) dev.getInt("ver");
				oneDev.type = (byte) dev.getInt("type");
				oneDev.mac = dev.getLong("mac");
				oneDev.remoteip = InetAddress.getByName(dev.getString("remoteip"));
				oneDev.setCameraDeviceID(dev.getString("CameraDeviceID"));
				oneDev.setCameraUserName(dev.getString("CameraUserName"));
				oneDev.setCameraPassWord(dev.getString("CameraPassWord"));
				oneDev.delFromDatabaseWithName(mContext);
				oneDev.addToDatabase(mContext);
			}
			
		}
	}
}
