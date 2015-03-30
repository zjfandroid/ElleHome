package elle.home.uipart;

import elle.home.app.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class SilderButton extends View {

	public String TAG = "SilderButton";
	
	public final int dViewWidth = 112;
	public final int dViewHeight = 73;
	
	public final int dBarLeftX = 0;
	public final int dBarLeftY = 0;
	public final int dBarRightX = 47;
	public final int dBarRightY = 0;
	
	private float allScale;
	private Matrix allMatrix;
	private int viewWidth;
	private int viewHeight;
	private int barLeftX = 0;
	private int barLeftY = 0;
	private int barRightX = 0;
	private int barRightY = 0;
	
	private Bitmap bgRes;
	private Bitmap bgOn;
	private Bitmap bgOff;
	
	private Bitmap bar;	
	
	private boolean rightOrleft;
	
	OnOff listener;
	
	public SilderButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public SilderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public SilderButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context){
		this.allMatrix = new Matrix();
		
		this.bgOff = decodeResource(getResources(), R.drawable.silder_off);
		this.bgOn = decodeResource(getResources(),R.drawable.silder_on);
		this.bar = decodeResource(getResources(), R.drawable.silder_dot);
		
		this.bgRes = this.bgOff;
		this.rightOrleft = false;
		
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//canvas.drawBitmap(this.bar, 15, 15,null);
		if(this.rightOrleft == false){
			this.bgRes = this.bgOff;
		}else{
			this.bgRes = this.bgOn;
		}
		canvas.drawBitmap(this.bgRes, 0, 0,null);
		if(this.rightOrleft == false){
			canvas.drawBitmap(this.bar, this.barLeftX, this.barLeftY-1,null);
		}else{
			canvas.drawBitmap(this.bar, this.barRightX, this.barRightY-1,null);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if(this.viewWidth !=(right-left)){
			this.viewWidth = right - left;
			this.viewHeight = bottom - top;
			this.allScale = ((float)this.viewWidth-0)/(float)this.dViewWidth;
			float yScll = ((float)this.viewHeight-0)/(float)this.dViewHeight;
			this.allMatrix.setScale(this.allScale, yScll);
			
			this.bgOff = martixBit(this.bgOff);
			this.bgOn = martixBit(this.bgOn);
			this.bar = martixBit(this.bar);
			
			this.barLeftX = (int)(this.dBarLeftX*this.allScale);
			this.barLeftY = (int)(this.dBarLeftY*this.allScale);
			this.barRightX = (int)(this.dBarRightX*this.allScale);
			this.barRightY = (int)(this.dBarRightY*this.allScale);
			
			this.bgRes = this.bgOff;
			
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void setOnOff(boolean tmp){
		if(tmp){
			this.rightOrleft = true;
			this.invalidate();
		}else{
			this.rightOrleft = false;
			this.invalidate();
		}
	}
	
	public void setListener(OnOff tmp){
		this.listener = tmp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(listener!=null)
				listener.clickDown();
			break;
		case MotionEvent.ACTION_UP:
			Log.d(TAG," silder button up");
			if(this.rightOrleft == false){
				this.rightOrleft = true;
			}else{
			
				this.rightOrleft = false;
			}
			this.invalidate();
			if(listener!=null){
				listener.onoff(this.rightOrleft);
				listener.clickUp();
			}
			
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}
	
	public void recyleBit(){
		bgOn.recycle();
		bgOff.recycle();
		bar.recycle();
		
	}
	
	private Bitmap decodeResource(Resources resources,int id){
		TypedValue value = new TypedValue();
	    resources.openRawResource(id, value);
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inTargetDensity = value.density;
	    return BitmapFactory.decodeResource(resources, id, opts);
	}
	
	private Bitmap martixBit(Bitmap bit){
		Bitmap tmp = Bitmap.createBitmap(bit,0,0,bit.getWidth(),bit.getHeight(),this.allMatrix,true);
		bit.recycle();
		return tmp;
	}
	
	public interface OnOff{
		void onoff(boolean tmp); 
		void clickDown();
		void clickUp();
	}
	
	
}
