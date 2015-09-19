package elle.home.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 *  显示Toast提示信息
 * @version 1.0
 * @author 张建峰
 * @update 2013-1-18 上午8:42:47
 */
public class ShowToast {
	private static Toast mToast;

    /**
     * @param text
     *            提示文本
     * @description 显示Toast提示信息，一次只显示一个
     * @version 1.0
     * @author 张建峰
     * @update 2012-12-18 下午3:02:19
     */
	public static void show(Context context, String text) {
		setText(context, text);
		mToast.setGravity(Gravity.BOTTOM , 0, 80);
		mToast.show();
	}
	
	public static void show(Context context, int resID) {
		setText(context, context.getResources().getString(resID));
		mToast.setGravity(Gravity.BOTTOM , 0, 80);
		mToast.show();
	}
	
	public static void show(Context context, String text,int yOffset) {
		setText(context, text);
		mToast.setGravity(Gravity.TOP , 0, yOffset);
		mToast.show();
	}

	@SuppressLint("ShowToast")
	private static void setText(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
	}
}
