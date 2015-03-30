package elle.home.uipart;

import java.util.Timer;
import java.util.TimerTask;

import elle.home.app.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 律
 *
 */
public class RgbLightView extends View {
	
	public String TAG = "RgbLightView";
	
	public final int dViewWidth = 640;
	public final int dViewHeight = 906;
	public final int dBarRadius = 292;
	public final int dColorRadius = 227;
	public final int dWhiteRadius = 60;
	public final int dHuex = 320;
	public final int dHuey = 333;
	public final int dOnoffLeft = 200;
	public final int dOnoffTop = 710;
	public final int dOnoffRight = 395;
	public final int dOnoffBottom = 850;
	
	public final int dOffset = 50;
	
	public final int RgbTouchNull = 0;
	public final int RgbTouchLux = 1;
	public final int RgbTouchColor = 2;
	public final int RgbTouchWhite = 3;
	public final int RgbTouchOnOff = 4;
	
	public final int ShowStatusOn = 1;
	public final int ShowStatusOff = 0;
	
	private float allScale;
	private Matrix allMatrix;
	private int viewWidth = 0;
	private int viewHeight = 0;
	
	//背景图片
	private Bitmap bgOnRes;
	private Bitmap bgOnDownRes;
	private Bitmap bgOffRes;
	private Bitmap bgOffDownRes;
	private Bitmap bgRes;
	
	//进度点图片
	private Bitmap barRes;
	private Bitmap barDownRes;
	private Bitmap barBit;
	private Bitmap barEmptyRes;
	private int barX = 0;
	private int barY = 0;
	private int barRadius = 0;
	private int barStartAngle = 120;
	private int barSweepAngle = 300;
	private int barCurrentAngle = 0;
	public int barMoveAngle = 0;
	
	//色盘中心点坐标
	private int hueX;
	private int hueY;
	public int colorRed;
	public int colorGreen;
	public int colorBlue;
	
	//白光图片
	private Bitmap whiteBtnRes;
	private Bitmap whiteDownBtnRes;
	private Bitmap whiteBtn;
	private Bitmap whiteEmptyRes;
	
	//放大镜
	private Bitmap magnifierRes;
	private Bitmap magnifierEmptyRes;
	private Bitmap magnifierbit;
	private int magnifierx;
	private int magnifiery;
	
	//开关切换区域
	private int onoffLeft;
	private int onoffTop;
	private int onoffRight;
	private int onoffBottom;
	
	//当前触控状态
	private int touchStatus;
	private int luxOutRadius;
	private int luxInRadius;
	private int colorOutRadius;
	private int whiteRadius;
	
	//当前显示状态
	private int showStatus;
	
	//定时器，1s中只允许采集五次状态
	private Timer timer;
	private boolean runFlag;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 50;
	
	OnRgbLightChange changelistener;
	
	
	/**设定灯的开关状态对应不同的背景
	 * @param tmp
	 */
	public void setLightStatus(boolean tmp){
		if(tmp){
			this.showStatus = this.ShowStatusOn;
			this.bgRes = this.bgOnRes;
			this.barBit = this.barRes;
			this.whiteBtn = this.whiteBtnRes;
		}else{
			this.showStatus = this.ShowStatusOff;
			this.bgRes = this.bgOffRes;
			this.barBit = this.barEmptyRes;
			this.whiteBtn = this.whiteEmptyRes;
		}
		this.invalidate();
	}
	
	public void recycleBit(){
		
		this.whiteBtnRes.recycle();
		this.whiteDownBtnRes.recycle();
		this.whiteEmptyRes.recycle();
		
		this.magnifierRes.recycle();
		this.magnifierEmptyRes.recycle();
		
		this.barRes.recycle();
		this.barDownRes.recycle();
		this.barEmptyRes.recycle();
		
		this.bgOffDownRes.recycle();
		this.bgOffRes.recycle();
		this.bgOnDownRes.recycle();
		this.bgOnRes.recycle();
		
	}
	
	
	public RgbLightView(Context context){
		super(context);
		init(context);
	}

	public RgbLightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//Log.d(TAG,"attrs:"+attrs.getAttributeValue("http://schemas.android.com/apk/res/elle.home.app", "testid"));
		init(context);
	}
	
	private void init(Context context){
		
		//vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
		
		this.allMatrix = new Matrix();
		this.showStatus = this.ShowStatusOn;
		this.touchStatus = this.RgbTouchNull;
		
		//加载图片资源
		//背景
		this.bgOnRes = decodeResource(getResources(),R.drawable.rgb_on_normal);
		this.bgOnDownRes = decodeResource(getResources(),R.drawable.rgb_on_pressed);
		this.bgOffDownRes = decodeResource(getResources(),R.drawable.rgb_light_off_pressed);
		this.bgOffRes = decodeResource(getResources(),R.drawable.rgb_light_off_normal);
		this.bgRes = this.bgOnRes;
		
		//bar点
		this.barRes = decodeResource(getResources(),R.drawable.rgb_bar_normal);
		this.barDownRes = decodeResource(getResources(),R.drawable.rgb_bar_pressed);
		this.barEmptyRes = decodeResource(getResources(), R.drawable.rgb_bar_empty);
		this.barBit = this.barRes;
		
		//white按钮
		this.whiteBtnRes = decodeResource(getResources(),R.drawable.rgb_white_normal);
		this.whiteDownBtnRes = decodeResource(getResources(),R.drawable.rgb_white_pressed);
		this.whiteEmptyRes = decodeResource(getResources(), R.drawable.rgb_white_empty);
		this.whiteBtn = this.whiteBtnRes;
		
		//放大镜
		this.magnifierRes = decodeResource(getResources(),R.drawable.magnifier);
		this.magnifierEmptyRes = decodeResource(getResources(), R.drawable.magnifier_empty);
		this.magnifierbit = this.magnifierRes;
		
		
		this.runFlag = false;
		this.timer = new Timer();
		this.timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runFlag =true;
			}}, 1000,200);
		
		Log.d(TAG,"view width:"+this.getWidth()+" height:"+this.getHeight());
		
	}
	
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		//得到真实的view信息,重新分配图片
		if(this.viewWidth != (right-left)){
			
			this.viewWidth = right-left;
			this.viewHeight = bottom-top;
			this.allScale = ((float)this.viewWidth-0)/(float)this.dViewWidth;
			this.allMatrix.setScale(this.allScale, this.allScale);
			
			//Log.d(TAG,"allScale1:"+allScale+" bg width"+this.bgRes.getWidth()+" bg height:"+this.getHeight());
			//图片变形
			this.bgOnRes = martixBit(this.bgOnRes);
			this.bgOnDownRes = martixBit(this.bgOnDownRes);
			this.bgOffDownRes = martixBit(this.bgOffDownRes);
			this.bgOffRes = martixBit(this.bgOffRes);
			this.bgRes = this.bgOnRes;
			
			this.barRes = martixBit(this.barRes);
			this.barDownRes = martixBit(this.barDownRes);
			this.barEmptyRes = martixBit(this.barEmptyRes);
			this.barBit = this.barRes;
			
			this.whiteBtnRes = martixBit(this.whiteBtnRes);
			this.whiteDownBtnRes = martixBit(this.whiteDownBtnRes);
			this.whiteEmptyRes = martixBit(this.whiteEmptyRes);
			this.whiteBtn = this.whiteBtnRes;
			
			this.magnifierRes = martixBit(this.magnifierRes);
			this.magnifierEmptyRes = martixBit(this.magnifierEmptyRes);
			this.magnifierbit = this.magnifierEmptyRes;
			
			//Log.d(TAG,"allScale2:"+allScale+" bg width"+this.bgRes.getWidth()+" bg height:"+this.bgRes.getHeight());
			
			this.barRadius = (int) (this.dBarRadius*this.allScale);
			this.barCurrentAngle = this.barStartAngle;
			this.barMoveAngle = 0;
			
			this.hueX = (int)(this.dHuex*this.allScale);
			this.hueY = (int)(this.dHuey*this.allScale);
			
			this.onoffLeft = (int)(this.dOnoffLeft*this.allScale);
			this.onoffTop = (int)(this.dOnoffTop*this.allScale);
			this.onoffRight = (int)(this.dOnoffRight*this.allScale);
			this.onoffBottom = (int)(this.dOnoffBottom*this.allScale);
			
			Log.d(TAG,"huex:"+this.hueX+" huey:"+this.hueY);
			
			this.luxOutRadius = this.barRadius+this.barBit.getWidth();
			this.luxInRadius = this.barRadius-this.barBit.getWidth();
			
			this.colorOutRadius = (int)(this.dColorRadius*this.allScale);
			this.whiteRadius = (int)(this.dWhiteRadius*this.allScale);
			
			getBarLocation();	
			
			
		}
		Log.d(TAG,"layout left:"+left+" top:"+top+" right:"+right+" bottom:"+bottom);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawBitmap(this.bgRes, 0, 0,null);
		canvas.drawBitmap(this.whiteBtn, 0+this.hueX-this.whiteBtn.getWidth()/2, 0+this.hueY-this.whiteBtn.getHeight()/2, null);			
		canvas.drawBitmap(this.barBit, 0+this.barX, 0+this.barY,null);
		canvas.drawBitmap(this.magnifierbit, this.magnifierx-this.magnifierbit.getWidth()/2,this.magnifiery-this.magnifierbit.getHeight()/2,null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			motionDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			motionMove(event);
			break;
		case MotionEvent.ACTION_UP:
			motionUp(event);
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		return true;
	}
	
	private void motionDown(MotionEvent ev){
		float radius = this.getRadiusToHue(ev.getX(), ev.getY());
		if(this.isInBarCycle(radius)){
			if(this.showStatus == this.ShowStatusOff)
				return;
			if(isInBarAngle(ev)){
				//vibrator.vibrate(vibarator_time);
				this.touchStatus = this.RgbTouchLux;
				this.barBit = this.barDownRes;
			}
		}else if(this.isInColorCycle(radius)){
			if(this.showStatus == this.ShowStatusOff)
				return;
			//vibrator.vibrate(vibarator_time);
			this.touchStatus = this.RgbTouchColor;
			this.magnifierbit = this.magnifierRes;
			freshMagnifief(ev);
		}else if(this.isInWhiteCycle(radius)){
			if(this.showStatus == this.ShowStatusOff)
				return;
			//vibrator.vibrate(vibarator_time);
			this.touchStatus = this.RgbTouchWhite;
			this.whiteBtn = this.whiteDownBtnRes;
			this.invalidate();
		}else if(this.isInOnOff(ev)){
			//vibrator.vibrate(vibarator_time);
			this.touchStatus = this.RgbTouchOnOff;
			if(this.showStatus == this.ShowStatusOn)
				this.bgRes = this.bgOnDownRes;
			else
				this.bgRes = this.bgOffDownRes;
			this.invalidate();
		}else{
			this.touchStatus = this.RgbTouchNull;
		}
	}
	
	private void motionMove(MotionEvent ev){
		if(this.showStatus == this.ShowStatusOff)
			return;
		if(this.touchStatus == this.RgbTouchLux){
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInBarBig(radius)){
				this.freshBarPos(ev);
				if(this.runFlag == true){
					//getCurrentPgress();
					if(changelistener!=null){
						changelistener.onLuxChange(this, this.barMoveAngle);
					}
				}
			}
			
		}else if(this.touchStatus == this.RgbTouchColor){
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInColorCycle(radius)){
				freshMagnifief(ev);
				if(this.runFlag == true){
					this.getColor(ev);
					if(changelistener!=null){
						changelistener.onColorChange(this, this.colorRed, this.colorGreen, this.colorBlue);
					}
				}
			}
			

			
			
		}else if(this.touchStatus == this.RgbTouchWhite){
			
		}else if(this.touchStatus == this.RgbTouchOnOff){
			
		}else{
			
		}
		this.runFlag = false;
		
	}
	
	private void motionUp(MotionEvent ev){
		
		if(this.touchStatus == this.RgbTouchLux){
			if(this.showStatus == this.ShowStatusOff)
				return;
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			this.barBit = this.barRes;
			if(this.isInBarBig(radius)){
				this.freshBarPos(ev);
				if(changelistener!=null){
					changelistener.onLuxStop(this, this.barMoveAngle);
				}
			}
			this.invalidate();
		}else if(this.touchStatus == this.RgbTouchColor){
			if(this.showStatus == this.ShowStatusOff)
				return;
			this.magnifierbit = this.magnifierEmptyRes;
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInColorCycle(radius)){
				freshMagnifief(ev);
				this.getColor(ev);
				if(changelistener!=null){
					changelistener.onColorStop(this, this.colorRed, this.colorGreen, this.colorBlue);
				}
			}
			this.invalidate();
			
		}else if(this.touchStatus == this.RgbTouchWhite){
			if(this.showStatus == this.ShowStatusOff)
				return;
			this.whiteBtn = this.whiteBtnRes;
			this.invalidate();
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInWhiteCycle(radius)){
				if(this.changelistener!=null){
					this.changelistener.onWhiteClick(this);
				}
			}
		}else if(this.touchStatus == this.RgbTouchOnOff){
			if(this.showStatus == this.ShowStatusOn)
				this.bgRes = this.bgOnRes;
			else
				this.bgRes = this.bgOffRes;
			if(this.isInOnOff(ev)){
				if(this.changelistener!=null){
					this.changelistener.onOnOffClick(this);
				}
			}
			this.invalidate();
		}
		this.touchStatus = this.RgbTouchNull;
		
	}
	
	/**是否在开关按钮区域
	 * @param ev
	 * @return
	 */
	private boolean isInOnOff(MotionEvent ev){
		if(ev.getX()>this.onoffLeft&&ev.getX()<this.onoffRight){
			if(ev.getY()>this.onoffTop&&ev.getY()<this.onoffBottom){
				return true;
			}
		}
		return false;
	}

	/**判断是否在白色按钮内
	 * @param touchRadius
	 * @return
	 */
	private boolean isInWhiteCycle(float touchRadius){
		if(touchRadius>this.whiteRadius)
			return false;
		return true;
	}
	
	/**得到色盘里面的颜色
	 * @param ev
	 */
	private void getColor(MotionEvent ev){
		int rgb = this.bgRes.getPixel((int)ev.getX(), (int)ev.getY());
		int red = Color.red(rgb);
		int green = Color.green(rgb);
		int blue = Color.blue(rgb);
		if(this.touchStatus == this.RgbTouchLux)
			return;
		this.colorRed = red;
		this.colorGreen = green;
		this.colorBlue = blue;
		
		//Log.d(TAG,"color red:"+red+" green:"+green+" blue:"+blue);
	}
	
	private void freshMagnifief(MotionEvent ev){
		this.magnifierx = (int) ev.getX();
		this.magnifiery = (int) ev.getY();
		this.invalidate();
	}
	
	/**判断是否在色环内
	 * @param touchRadius
	 * @return
	 */
	private boolean isInColorCycle(float touchRadius){
		if(touchRadius>this.colorOutRadius||touchRadius<this.whiteRadius)
			return false;
		return true;
	}
	
	/**判断是否在亮度进度调整中
	 * @param touchRadius
	 * @return
	 */
	private boolean isInBarCycle(float touchRadius){
		if(touchRadius>this.luxOutRadius||touchRadius<this.luxInRadius)
			return false;
		return true;
	}
	
	private boolean isInBarAngle(MotionEvent ev){
		int tmp = (int)this.getTouchAngle(ev.getX(), ev.getY());
		if((tmp>=this.barStartAngle&&tmp<=360)||(tmp>=0&&tmp<=(180-this.barStartAngle))){
			return true;
		}
		return false;
	}
	
	private boolean isInBarBig(float touchRadius){
		if(touchRadius>this.luxOutRadius)
			return false;
		return true;
	}
	
	/**刷新bar点的位置现在
	 * @param ev
	 */
	private void freshBarPos(MotionEvent ev){
		int tmp = (int)this.getTouchAngle(ev.getX(), ev.getY());
		if((tmp>=this.barStartAngle&&tmp<=360)||(tmp>=0&&tmp<=(180-this.barStartAngle))){
			this.barCurrentAngle = tmp;
			this.barMoveAngle = this.exchangeMoveAngle(this.barCurrentAngle);
			this.getBarLocation();
			this.invalidate();
		}
	}
	
	public void setBarFresh(){
		
		int tmp = 0;
		if( this.barMoveAngle<=(this.barSweepAngle-(180-this.barStartAngle)) ){
			tmp = this.barStartAngle + this.barMoveAngle;
		}else if(this.barMoveAngle>(this.barSweepAngle-(180-this.barStartAngle))){
			tmp = this.barMoveAngle - (this.barSweepAngle - (180-this.barStartAngle));
		}

		if((tmp>=this.barStartAngle&&tmp<=360)||(tmp>=0&&tmp<=(180-this.barStartAngle))){
			this.barCurrentAngle = tmp;
			this.getBarLocation();
			this.invalidate();
			
		}
		
	}
	
	/**换算成移动了的角度
	 * @param tmp
	 * @return
	 */
	private int exchangeMoveAngle(int tmp){
		int angle = 0;
		if(tmp>=this.barStartAngle&&tmp<=360)
			angle = tmp-this.barStartAngle;
		else if(tmp<=(180-this.barStartAngle)&&tmp>=0)
			angle = this.barSweepAngle+tmp-(180-this.barStartAngle);
		else
			angle = 0;
		return angle;
	}
	
	/**得到距离hue的角度
	 * @param x
	 * @param y
	 * @return
	 */
	private double getTouchAngle(float x,float y){
		double tmp = 0;
		float sx = x - this.hueX;
		float sy = y - this.hueY;
		tmp = Math.toDegrees(Math.atan2(sy,sx));
		if(tmp<0)
			tmp=tmp+360;
		return tmp;
	}
	
	/**得到bar的位置
	 * 
	 */
	private void getBarLocation(){
		this.barX = (int)(this.barRadius*Math.cos(Math.toRadians(this.barCurrentAngle))+this.hueX-this.barBit.getWidth()/2);
		this.barY = (int)(this.barRadius*Math.sin(Math.toRadians(this.barCurrentAngle))+this.hueY-this.barBit.getHeight()/2);
	}
	
	/**得到距离hue中心的半径
	 * @param x
	 * @param y
	 * @return
	 */
	private float getRadiusToHue(float x,float y){
		float sx = x -this.hueX;
		float sy = y -this.hueY;
		float radius = (float)Math.sqrt((sx*sx)+(sy*sy));
		return radius;
	}
	
	private Bitmap martixBit(Bitmap bit){
		Bitmap tmp = Bitmap.createBitmap(bit,0,0,bit.getWidth(),bit.getHeight(),this.allMatrix,true);
		bit.recycle();
		return tmp;
	}

	private Bitmap decodeResource(Resources resources,int id){
		TypedValue value = new TypedValue();
	    resources.openRawResource(id, value);
	    BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inTargetDensity = value.density;
	    return BitmapFactory.decodeResource(resources, id, opts);
	}
	
	public void setOnChangeListener(OnRgbLightChange listener){
		this.changelistener = listener;
	}
	
	public interface OnRgbLightChange{
		
		void onLuxStart(RgbLightView view);
		void onLuxChange(RgbLightView view,int progress);
		void onLuxStop(RgbLightView view,int progress);
		
		void onColorStart(RgbLightView view);
		void onColorChange(RgbLightView view,int r,int g,int b);
		void onColorStop(RgbLightView view,int r,int g,int b);
		
		void onWhiteClick(RgbLightView view);
		
		void onOnOffClick(RgbLightView view);
		
	}
	
}
