package elle.home.database;

import java.util.ArrayList;
import java.util.List;

import elle.home.app.R;
import elle.home.publicfun.PublicDefine;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	
	public void aadSceneName(String name,int icon){
		
		String[] params = new String[2];
		params[0] = name;
		params[1] = String.valueOf(icon);
		
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		
		String sql = "INSERT INTO scenes (scenename,sceneicon) VALUES (?,?)";
		db.execSQL(sql,params);
		db.close();
		
	}
	
}
