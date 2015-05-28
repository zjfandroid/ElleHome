package elle.home.utils;

import android.annotation.SuppressLint;

public class StringUtils {

	@SuppressLint("NewApi")
	public static boolean isEmpty(String string){
		if(null == string || string.isEmpty()){
			return true;
		}
		return false;
	}
	
}
