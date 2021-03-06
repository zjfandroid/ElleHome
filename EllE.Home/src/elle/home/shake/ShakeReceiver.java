/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package elle.home.shake;

import elle.home.utils.ShowInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Receives system broadcasts (boot, network connectivity)
 */
public class ShakeReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(final Context context, final Intent intent) {

        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        
//		String action = intent.getAction();
//		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
        if(wifiNetInfo.isConnected()){
			startService(context);
			ShowInfo.printLogW("_________wifiNetInfo.isConnected()________");
		}else{
			context.stopService(new Intent(context, ShakeService.class));
			ShowInfo.printLogW("_________wifiNetInfo not Connected()________");
		}
	}

	private void startService(Context context) {
		context.startService(new Intent(context, ShakeService.class));
	}
}
