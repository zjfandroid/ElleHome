package elle.home.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import elle.home.app.smart.R;
import elle.home.publicfun.PublicDefine;

public class AllScene {

	public List<SceneData> allscene = new ArrayList<SceneData>();
	Context context;
	public AllScene(Context context){
		this.context = context;
	}
	
	public void freshSceneData(){
		String[] params = new String[0];
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String sql = "select * from scenes";
		Cursor  cursor = db.rawQuery(sql, params);
		allscene.clear();
		while(cursor.moveToNext()){
			SceneData scene = new SceneData(cursor.getString(cursor.getColumnIndex("scenename")),
					Integer.parseInt(cursor.getString(cursor.getColumnIndex("sceneicon"))),
					context);
			scene.getAllScene();
			allscene.add(scene);
		}
		db.close();
		
		if(allscene.size()==0){
			//aadSceneName("晚安",PublicDefine.ResideIconGoodNight);
			aadSceneName(context.getResources().getString(R.string.scene_goodnight),PublicDefine.ResideIconGoodNight);
			SceneData scene = new SceneData(context.getResources().getString(R.string.scene_goodnight),PublicDefine.ResideIconGoodNight,context);
			scene.getAllScene();
			allscene.add(scene);
		}
	}
	
	public int aadSceneName(String name,int icon){
		
		String[] params = new String[2];
		params[0] = name;
		params[1] = String.valueOf(icon);
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String[] checkParams = new String[1];
		checkParams[0] = name;
		String checkSql = "select * from scenes where scenename = ?";
		Cursor cursor = db.rawQuery(checkSql, checkParams);
		while(cursor.moveToNext()){
			db.close();
			return 1;
		}
		
		String sql = "INSERT INTO scenes (scenename,sceneicon) VALUES (?,?)";
		db.execSQL(sql,params);
		db.close();
		return 0;
	}
	
}
