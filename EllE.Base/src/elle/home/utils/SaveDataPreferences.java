package elle.home.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存数据
 * 
 * @version 1.0
 * @author 张建峰
 * @update 2013-1-10 上午8:40:27
 */
public class SaveDataPreferences
{
    private static SharedPreferences preferences;

    public static void save(Context context, String key, String statu)
    {
        getPreferences(context);
        Editor edit = preferences.edit();
        edit.putString(key, statu);
        edit.commit();
    }

    public static void saveInt(Context context, String key, int value)
    {
        getPreferences(context);
        Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int loadInt(Context context, String key, int defValue)
    {
        getPreferences(context);
        return preferences.getInt(key, defValue);
    }
    
    public static void saveBoolean(Context context, String key, boolean value)
    {
        getPreferences(context);
        Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean loadBoolean(Context context, String key, boolean defValue)
    {
        getPreferences(context);
        return preferences.getBoolean(key, defValue);
    }

    public static String load(Context context, String key, String defValue)
    {
        getPreferences(context);
        return preferences.getString(key, defValue);
    }

    private static void getPreferences(Context context)
    {
        if (null == preferences)
        {
            preferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        }
    }
}
