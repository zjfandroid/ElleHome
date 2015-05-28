package elle.home.uipart;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class KickView extends ScrollView {
	
	String TAG = "KickView";

	private static final int size = 4;
	private View inner;
	private float y;
	private Rect normal = new Rect();
	
	public KickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public KickView(Context context) {  
        super(context);  
    } 
	
	@Override
	protected void onFinishInflate() {
		if(getChildCount()>0){
			inner = getChildAt(0);
			Log.d(TAG,"inner get a child");
		}else{
			Log.d(TAG,"inner no child");
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//Log.d(TAG,"on touch event");
		if(inner == null){
			
		}else{
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}
	
	public void commOnTouchEvent(MotionEvent event){
		switch(event.getAction()){
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_DOWN:
			y = event.getY();
			Log.d(TAG,"--action_down--");
			break;  
		case MotionEvent.ACTION_UP:
			if(isNeedAnimation()){
				animation();
			}
			Log.d(TAG,"action_up");
			break;
		case MotionEvent.ACTION_MOVE:
			final float preY = y;
			float nowY = event.getY();
			int deltaY = (int) (preY - nowY) / size; 
			y = nowY;
			if(isNeedMove()){
				if (normal.isEmpty()) {  
                    // 保存正常的布局位置  
                    normal.set(inner.getLeft(), inner.getTop(),  
                            inner.getRight(), inner.getBottom());  
                    return;  
                }  
                int yy = inner.getTop() - deltaY;  
  
                // 移动布局  
                inner.layout(inner.getLeft(), yy, inner.getRight(),  
                        inner.getBottom() - deltaY); 
			}
			break;
		default:
			break;
		}
	}
	
	public void animation(){
		TranslateAnimation ta = new TranslateAnimation(0,0,inner.getTop(),normal.top);
		ta.setDuration(200);
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);  
        normal.setEmpty();
	}
	
	// 是否需要开启动画  
    public boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  
  
    // 是否需要移动布局  
    public boolean isNeedMove() {  
        int offset = inner.getMeasuredHeight() - getHeight();  
        int scrollY = getScrollY();  
        if (scrollY == 0 || scrollY == offset) {  
            return true;  
        }  
        return false;  
    }  

}
