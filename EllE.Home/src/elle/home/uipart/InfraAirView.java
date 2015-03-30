package elle.home.uipart;

import java.util.Timer;
import java.util.TimerTask;

import elle.home.app.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class InfraAirView extends View {
	
	public String TAG = "InfraAirView";
	
	public final int dViewWidth = 640;
	public final int dViewHeight = 1136;
	
	//public final int dBarRadius = 192;
	public final int dBarRadius = 225;
	public final int dOffRadius = 138;
	public final int dHuex = 320;
	public final int dHuey = 320;
	
	public final int dModeLeft = 195;
	public final int dModeTop = 700;
	public final int dModeRight = 447;
	public final int dModeBottom = 857;
	
	public final int dTextX =320;
	public final int dTextY =600;
	
	private float allScale;
	private Matrix allMatrix;
	private int viewWidth;
	private int viewHeight;
	
	public final int TouchNull =0;
	public final int TouchTemp = 1;
	public final int TouchOff = 2;
	public final int TouchMode = 3;
	public int touchStatus =0;
	
	//背景颜色
	//private Bitmap bgRes;
	//温度进度条
	private Bitmap barcircleRes;
	
	//关机键
	private Bitmap offRes;
	private Bitmap offPressedRes;
	private Bitmap offBit;
	private int offRadius;
	
	//制冷制热
	private Bitmap warmRes;
	private Bitmap coldRes;
	private Bitmap modeBit;
	private int modeLeft;
	private int modeTop;
	private int modeRight;
	private int modeBottom;
	
	public final int ModeCold = 0;
	public final int ModeWarm = 1;
	public int modestatus = 0;
	
	//绘制温度的显示
	public int temp;
	private Paint paint = new Paint();
	private int textX;
	private int textY;
	
	//进度点图片
	private Bitmap barBit;
	private Bitmap barRes;
	private Bitmap barDownRes;
	private int barX =0;
	private int barY =0;
	private int barRadius =0;
	private int barStartAngle = 135;
	private int barSweepAngle = 270;
	private float barStepAngle = 19.285f;
	private int barCurrentAngle = 0;
	private int barMoveAngle = 0;
	private int barStep = 0;
	
	private int barOutRadius =0;
	private int barInRadius =0;
	
	private int hueX =0;
	private int hueY =0;
	
	//震动器
	private Vibrator vibrator;
	private int vibarator_time = 65;
	
	public OnInfraAirChange listener;
	
	//监听按键的时长
	private boolean isLongClick;
	private int timercount;
	private Timer timer = new Timer();
	private TimerTask task = new TimerTask(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			timercount++;
			if(timercount>1000)
				timercount = 1000;
		}
		
	};
	
	private void startCount(){
		timercount = 0;
//		timer.schedule(task, 0,100);
	}
	
	private boolean stopCount(){
//		timer.cancel();
		if(timercount>15){
			return true;
		}
		return false;
	}
	
	public InfraAirView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public InfraAirView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public InfraAirView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context){
		
		//vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
		
		this.allMatrix = new Matrix();
		paint.setColor(getResources().getColor(R.color.whitesmoke));
		paint.setTextSize(100);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.SERIF);
		
		//this.bgRes = decodeResource(getResources(),R.drawable.plug_backbk);
		this.barcircleRes = decodeResource(getResources(),R.drawable.infra_air_bar_circle);
		this.barRes = decodeResource(getResources(),R.drawable.infra_air_bar_noraml);
		this.barDownRes = decodeResource(getResources(),R.drawable.infra_air_bar_down);
		this.offRes = decodeResource(getResources(),R.drawable.infra_air_off_normal);
		this.offPressedRes = decodeResource(getResources(),R.drawable.infra_air_off_pressed);
		this.coldRes = decodeResource(getResources(),R.drawable.infra_air_mode_cold);
		this.warmRes = decodeResource(getResources(),R.drawable.infra_air_mode_warm);
		
		this.barBit = this.barRes;
		this.offBit = this.offRes;
		this.modeBit = this.coldRes;
		
		this.temp = this.barStep+16;
		
		timer.schedule(task, 100,100);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//canvas.drawBitmap(this.bgRes, 0, 0 ,null);
		canvas.drawBitmap(this.modeBit, this.modeLeft, this.modeTop,null);
		canvas.drawBitmap(this.barcircleRes, 0+this.hueX-this.barcircleRes.getWidth()/2, 0+this.hueY-this.barcircleRes.getWidth()/2,null);
		canvas.drawBitmap(this.offBit, 0+this.hueX-this.offBit.getWidth()/2, 0+this.hueY-this.offBit.getWidth()/2,null);
		canvas.drawBitmap(this.barBit, 0+this.barX, 0+this.barY, null);
		canvas.drawText(temp+"°", this.textX, this.textY, paint);
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if(this.viewWidth !=(right-left)){
			this.viewWidth = right-left;
			this.viewHeight = bottom-top;
			this.allScale = ((float)this.viewWidth-0)/(float)this.dViewWidth;
			this.allMatrix.setScale(this.allScale, this.allScale);
			
			//this.bgRes = martixBit(this.bgRes);
			this.barcircleRes = martixBit(this.barcircleRes);
			this.barRes = martixBit(this.barRes);
			this.barDownRes = martixBit(this.barDownRes);
			this.offRes = martixBit(this.offRes);
			this.offPressedRes = martixBit(this.offPressedRes);
			this.coldRes = martixBit(this.coldRes);
			this.warmRes = martixBit(this.warmRes);
			
			this.barBit = this.barRes;
			this.offBit = this.offRes;
			this.modeBit = this.coldRes;
			
			this.barRadius = (int)(this.dBarRadius*this.allScale);
			this.barCurrentAngle = this.barStartAngle;
			this.barMoveAngle = 0;
			
			this.modeLeft = (int)(this.dModeLeft*this.allScale);
			this.modeRight = (int)(this.dModeRight*this.allScale);
			this.modeTop = (int)(this.dModeTop*this.allScale);
			this.modeBottom = (int)(this.dModeBottom*this.allScale);
			
			this.textX = (int)(this.dTextX*this.allScale);
			this.textY = (int)(this.dTextY*this.allScale);
			
			this.offRadius = (int)(this.dOffRadius*this.allScale);
			
			this.barInRadius = this.barRadius-this.barBit.getWidth();
			this.barOutRadius = this.barRadius+this.barBit.getWidth();
			
			this.hueX = (int)(this.dHuex*this.allScale);
			this.hueY = (int)(this.dHuey*this.allScale);
			
			getBarLocation();
		}
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
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_UP:
			motionUp(event);
			break;
		default:
			break;
		}
		return true;
	}	
	
	private void motionDown(MotionEvent ev){
		this.startCount();
		float radius = this.getRadiusToHue(ev.getX(), ev.getY());
		if(this.isInBarCycle(radius)){
			if(isInBarAngle(ev)){
				//.vibrate(vibarator_time);
				this.touchStatus = this.TouchTemp;
				this.barBit = this.barDownRes;
				this.invalidate();
			}
		}else if(this.isInOffCycle(radius)){
			//vibrator.vibrate(vibarator_time);
			this.offBit = this.offPressedRes;
			this.touchStatus = this.TouchOff;
			this.invalidate();
		}else if(this.isInModeChange(ev)){
			//vibrator.vibrate(vibarator_time);
			this.touchStatus = this.TouchMode;
		}
	}
	
	private void motionMove(MotionEvent ev){
		if(this.touchStatus == this.TouchTemp){
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInBarBig(radius)){
				this.freshBarPos(ev);
			}
		}
	}
	
	private void motionUp(MotionEvent ev){
		this.isLongClick = this.stopCount();
		//vibrator.vibrate(20);
		if(this.touchStatus == this.TouchTemp){
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			this.barBit = this.barRes;
			if(this.isInBarBig(radius)){
				this.freshBarPos(ev);
				adjustBarPos();
				if(this.listener!=null)
					this.listener.onTempChange(this.temp,this.isLongClick);
				//this.freshBarPos(ev);
			}
			this.invalidate();
		}else if(this.touchStatus == this.TouchOff){
			this.offBit = this.offRes;
			this.invalidate();
			float radius = this.getRadiusToHue(ev.getX(), ev.getY());
			if(this.isInOffCycle(radius)){
				if(this.listener!=null)
					this.listener.onOffClick(this.isLongClick);
			}
		}else if(this.touchStatus == this.TouchMode){
			if(this.isInModeChange(ev)){
				if(this.modestatus == this.ModeCold){
					this.modestatus = this.ModeWarm;
					this.modeBit = this.warmRes;
					this.invalidate();
				}else{
					this.modestatus = this.ModeCold;
					this.modeBit = this.coldRes;
					this.invalidate();
				}
				if(this.listener!=null)
					this.listener.onModeChange(this.modestatus,this.isLongClick);
			}
		}
		this.touchStatus = this.TouchNull;
	}
	
	/**
	 * 判断是否在调整温度的范围
	 * */
	private boolean isInBarCycle(float touchRadius){
		if(touchRadius>this.barOutRadius||touchRadius<this.barInRadius)
			return false;
		return true;
	}
	
	private boolean isInBarAngle(MotionEvent ev){
		int tmp = (int)this.getTouchAngle(ev.getX(), ev.getY());
		if((tmp>=(this.barStartAngle-10)&&tmp<=360)||(tmp>=0&&tmp<=(180-(this.barStartAngle-10)))){
			return true;
		}
		return false;
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
	
	/**刷新bar点的位置现在
	 * @param ev
	 */
	private void freshBarPos(MotionEvent ev){
		int tmp = (int)this.getTouchAngle(ev.getX(), ev.getY());
		if((tmp>=(this.barStartAngle-10)&&tmp<=360)||(tmp>=0&&tmp<=(180-(this.barStartAngle-10)))){
			
			if(tmp>=(this.barStartAngle-10)&&tmp<=(this.barStartAngle)){
				tmp = this.barStartAngle;
			}
			
			if((tmp<=(180-(this.barStartAngle-10)))&&(tmp>=(180-(this.barStartAngle)))){
				tmp = (180-(this.barStartAngle));
			}
			
			this.barCurrentAngle = tmp;
			this.barMoveAngle = this.exchangeMoveAngle(this.barCurrentAngle);
			this.getBarLocation();
			
			int step = (int)(this.barMoveAngle/this.barStepAngle);
			int xtmp = (int)(this.barMoveAngle-(this.barStepAngle*step));
			if(xtmp<this.barStepAngle/2){
				this.barStep = step;
			}else{
				this.barStep = step+1;
			}
			this.temp = this.barStep+16;
			
			this.invalidate();
		}
	}
	
	/**
	 * 调整bar的位置，按照步数来的角度
	 * */
	public void adjustBarPos(){
		int step = (int)(this.barMoveAngle/this.barStepAngle);
		int tmp = (int)(this.barMoveAngle-(this.barStepAngle*step));
		if(tmp<this.barStepAngle/2){
			this.barStep = step;
		}else{
			this.barStep = step+1;
		}
		Log.d(TAG,"bar step:"+this.barStep);
		this.barMoveAngle = (int)(this.barStep*this.barStepAngle);
		this.temp = this.barStep+16;
		setBarFresh();
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
	
	/**
	 * 得到bar的位置
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
	
	/**
	 * 判断是否在大圈范围内
	 * */
	private boolean isInBarBig(float touchRadius){
		if(touchRadius>this.barOutRadius)
			return false;
		return true;
	}
	
	/**
	 * 判断是否在关机键的按钮范围类
	 * */
	public boolean isInOffCycle(float touchRadius){
		if(touchRadius>this.offRadius)
			return false;
		return true;
	}
	
	/**
	 * 判断是否在模式切换的按钮内
	 * */
	private boolean isInModeChange(MotionEvent ev){
		if(ev.getX()>this.modeLeft&&ev.getX()<this.modeRight){
			if(ev.getY()>this.modeTop&&ev.getX()<this.modeBottom){
				return true;
			}
		}
		return false;
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
	
	public void recyleBit(){
		
		this.barRes.recycle();
		this.barDownRes.recycle();
		//this.bgRes.recycle();
		this.barcircleRes.recycle();
		this.offRes.recycle();
		this.offPressedRes.recycle();
		this.warmRes.recycle();
		this.coldRes.recycle();
		timer.cancel();
	}
	
	public void setAirListener(OnInfraAirChange listener){
		this.listener = listener;
	}
	
	public interface OnInfraAirChange{
		
		void onOffClick(boolean longclick);
		void onTempChange(int temp,boolean longclick);
		void onModeChange(int mode,boolean longclick);
		
	}
	
}
