package elle.home.utils;

import android.util.Log;


/**
 * 显示信息类 用于显示提示信息、打印信息等
 * */
public class ShowInfo
{
	
	private static boolean isShow = false;
	
	/**
	 * 打印Log.w信息
	 * @param aPrintLogStr 打印信息字符串
	 */
	public static void printLogW( String aPrintLogStr )
	{
		if(isShow){
			printLogW("JFLog", aPrintLogStr);
		}
	}
	
	public static void printLogW(String tag , String aPrintLogStr )
	{
	    if ( isShow)
	    {
	        Log.w( tag, aPrintLogStr );
	    }
	}

}

