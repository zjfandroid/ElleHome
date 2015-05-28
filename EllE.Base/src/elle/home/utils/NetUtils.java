package elle.home.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

	public static boolean isNetworkEnabled(Context mContext){
		if(null == mContext){
			return false;
		}
		
		final ConnectivityManager connManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo info = connManager.getActiveNetworkInfo();
		
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}
	
}
