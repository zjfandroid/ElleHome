package elle.home.uipart;

import elle.home.app.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

public class RgbLightControlView extends View {
	
	public String TAG = "RgbLightControlView";
	
	public int RgbTouchNull = 0;
	public int RgbTouchLux = 1;
	public int RgbTouchColor = 2;
	public int RgbTouchWhite = 3;

	//点的宽度
	private int mArcWidth = 0;
	//点的起始角度
	private int mStartAngle = 0;
	//点需要的角度
	private int mSweepAngle = 0;
	//点的当前角度
	private int mCurrentAngle = 0;
	//点的移动角度
	private int mMoveAngle = 0;
	
	//点的半径
	private int mArcRadius = 0;
	//点的画笔
	private Paint mArcPaint;
	private RectF mArcRect;
	
	//进度条的进度
	private int mSeekBarPrgoress = 0;
	//圆点的位置及大小
	private int barX = 0;
	private int barY = 0;
	private int barWidth = 0;
	private int barHight = 0;
	//可点击的半径
	private int barRadius = 0;
	//半径外圈
	private int barRadiusOut = 0;
	//半径内圈
	private int barRadiusIn = 0;
	
	//圆的范围
	private int cyclex;
	private int cycley;
	private int cyclewidth;
	private int cyclehight;
	//中心点
	private int cPointX;
	private int cPointY;
	
	
	//色盘半径
	private int hueRadius;
	//白光半径
	private int whiteRadius;
	
	
	//视图大小
	private int viewWidth = 0;
	private int viewHight = 0;
	
	//背景图
	private Bitmap bgRes;
	private Matrix bkMatrix;
	//角度点图片
	private Bitmap mThumb;
	
	//白光按下图片
	private Bitmap whiteDownBit;
	//白光正常图片
	private Bitmap whiteNormalBit;
	//白光照片
	private Bitmap whiteBit;
	
	//放大镜
	private Matrix matrix = new Matrix();
	private int zoomRadius = 80;
	private float zoomFactor = 2;
	private int zoomsize = 160;
	
	private Bitmap zoomBig;
	private Bitmap zoombit;
	
	private Bitmap magnifierBit;
	private Magnifier magnifier;
	private PopupWindow popup;
	private Point dstPoint;
	
	
	//当前触控状态
	private int touchStatus;
	
	public RgbLightControlView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public RgbLightControlView(Context context, AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	
	public void init(Context context){
		Log.d(TAG,"rgbLightControl init now");
		mThumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
		bgRes = BitmapFactory.decodeResource(getResources(), R.drawable.huebackground);
		bkMatrix = new Matrix();
		
		whiteNormalBit = BitmapFactory.decodeResource(getResources(),R.drawable.scrubber_control_normal_holo);
		whiteDownBit = BitmapFactory.decodeResource(getResources(),R.drawable.scrubber_control_pressed_holo);
		whiteBit = this.whiteNormalBit;
		
		magnifierBit = BitmapFactory.decodeResource(getResources(), R.drawable.magnifier);
		magnifier = new Magnifier(context);
		popup = new PopupWindow(magnifier,zoomsize,zoomsize);
		popup.setAnimationStyle(android.R.style.Animation_Toast);
		dstPoint = new Point(0,0);
		matrix.postScale(this.zoomFactor, this.zoomFactor);
		//this.zoomBig = Bitmap.createBitmap(this.bgRes,0,0,this.bgRes.getWidth(),this.bgRes.getHeight(),matrix,true);
		//this.zoombit = Bitmap.createBitmap(this.zoomBig,200,200,80,80);
		
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setStyle(Paint.Style.STROKE);
		mArcPaint.setStrokeWidth(10);
		
		mArcRect = new RectF();
		
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//Log.d(TAG,"on draw,height:"+this.getHeight()+" width:"+this.getWidth());
		canvas.drawBitmap(bgRes, bkMatrix, null);
		
		//绘画放大镜
		if(this.touchStatus == this.RgbTouchColor){
			canvas.save();
			
			
			canvas.restore();
		}
		
		canvas.drawArc(mArcRect, 140, 260, false, mArcPaint);
		
		//绘画白光的状态
		canvas.save();
		canvas.drawBitmap(whiteBit, this.cPointX-this.whiteRadius/2, this.cPointY-this.whiteRadius/2,null);
		canvas.restore();
		
		//绘画进度点的图标
		canvas.save();
		canvas.drawBitmap(mThumb, this.barX, this.barY, null);
		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//视图的大小
		viewHight= getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
		viewWidth =getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);		
		float xf = viewHight/this.bgRes.getWidth();
		bkMatrix.setScale(xf,xf);
		this.zoomBig = Bitmap.createBitmap(this.bgRes, 0, 0, this.bgRes.getWidth(), this.bgRes.getHeight(), this.bkMatrix, true);
		
		int min = Math.min(viewHight, viewWidth);
		Log.d(TAG,"on Measure,heightMeasureSpec:"+heightMeasureSpec+" widthMeasureSpec:"+widthMeasureSpec+
				" viewHight:"+viewHight+" viewwidth:"+viewWidth);
		
		//滑动按钮的大小
		barWidth = this.mThumb.getWidth();
		barHight = this.mThumb.getHeight();
		//滑动按钮的半径
		barRadius = (int) (Math.sqrt((Math.pow(barWidth, 2)+Math.pow(barHight, 2))))/2;
		Log.d(TAG,"barwidth:"+ barWidth+" barHight:"+barHight+" barRadius:"+barRadius);
		
		//圆圈半径
		mArcRadius = (int) ((min/2)*0.95f);
		
		//hue半径
		this.hueRadius = (int) (this.mArcRadius*0.9f);
		//white半径
		this.whiteRadius = (int) (this.mArcRadius*0.3f);
		
		//外圈半径
		barRadiusOut = mArcRadius+barRadius;
		//内圈半径
		barRadiusIn = mArcRadius-barRadius;
		
		//圆圈的左边位置
		cyclex = (viewWidth/2)-mArcRadius;
		//圆圈的顶部位置
		cycley = (viewHight/2)-mArcRadius;
		//圆圈的宽度
		cyclewidth = mArcRadius*2;
		//圆圈的高度
		cyclehight = mArcRadius*2;
		//圆圈中心点的位置
		cPointX = cyclex+mArcRadius;
		cPointY = cycley+mArcRadius;
		
		Log.d(TAG,"cyclex:"+cyclex+" cycley:"+cycley+" cyclewidth:"+cyclewidth+" cyclehight:"+cyclehight);
		
		//规定圆圈的范围和位置
		this.mArcRect.set(cyclex, cycley, cyclex+cyclewidth, cycley+cyclehight);
		
		//设定一些角度
		this.mStartAngle = 140;
		this.mSweepAngle = 260;
		this.mCurrentAngle = this.mStartAngle;
		this.mMoveAngle = 0;
		
		this.touchStatus = this.RgbTouchNull;
		
		//测量bar点的位置
		getBarLocation();
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
	
	/**按键按下的时候执行的判断
	 * @param ev
	 */
	private void motionDown(MotionEvent ev){
		if(isInBarArc(ev.getX(),ev.getY())){
			this.touchStatus = this.RgbTouchLux;
		}else if(isInHueCycle(ev.getX(),ev.getY())){
			
			this.touchStatus = this.RgbTouchColor;
			zoomDeal(ev);
			
		}else if(isInWhiteCycle(ev.getX(),ev.getY())){
			this.touchStatus = this.RgbTouchWhite;
			setWhiteStaus(false);
		}
		
	}
	
	/**移动按键的时候
	 * @param ev
	 */
	private void motionMove(MotionEvent ev){
		
		if(this.touchStatus == this.RgbTouchLux){
			if(isInBarCyc(ev.getX(), ev.getY()))
				freshBarPos(ev);
		}else if(this.touchStatus == this.RgbTouchColor){
			
			zoomDeal(ev);
			
		}else if(this.touchStatus == this.RgbTouchWhite){
			
		}
	}
	
	/**按键离开的时候
	 * @param ev
	 */
	private void motionUp(MotionEvent ev){
		
		if(this.touchStatus == this.RgbTouchLux){
			if(isInBarCyc(ev.getX(), ev.getY()))
				freshBarPos(ev);
		}else if(this.touchStatus == this.RgbTouchColor){
			zoomDeal(ev);
			//放大镜消失
			
		}else if(this.touchStatus == this.RgbTouchWhite){
			setWhiteStaus(true);
		}
		this.touchStatus = this.RgbTouchNull;
		
	}
	
	/**设置放大镜的位置
	 * @param ev
	 */
	private void setColorZoom(MotionEvent ev){
	}
	
	/**放大镜的一些处理
	 * @param ev
	 */
	private void zoomDeal(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE){
			int x = (int) event.getX();
			int y = (int) event.getY();
			int xwidth = (int)(x)-this.zoomRadius;
			int yheight = (int)(y) - this.zoomRadius;
			if(xwidth<0)
				xwidth = 0;
			else if(xwidth>(this.zoomBig.getWidth()-2*this.zoomRadius))
				xwidth = this.zoomBig.getWidth()-2*this.zoomRadius;
			
			if(yheight<0)
				yheight = 0;
			else if(yheight>(this.zoomBig.getHeight()-2*this.zoomRadius))
				yheight = this.zoomBig.getHeight()-2*this.zoomRadius;
			
			this.zoombit = Bitmap.createBitmap(this.zoomBig,xwidth,yheight,this.zoomRadius*2,this.zoomRadius*2);
			dstPoint.set(x - this.zoomsize, y - this.zoomsize);
			if(y<0){
				popup.dismiss();
				this.invalidate();
				return;
			}
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				this.removeCallbacks(showZoom);
				postDelayed(showZoom, 100);
			}else if (!popup.isShowing()){
				showZoom.run();
			}
			popup.update(getLeft()+dstPoint.x,getTop()+dstPoint.y,-1,-1);
			magnifier.invalidate();
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			this.removeCallbacks(showZoom);
			popup.dismiss();
		}
		this.invalidate();
	}
	
	/**设置白光的状态
	 * @param status
	 */
	private void setWhiteStaus(boolean status){
		if(status){
			this.whiteBit = this.whiteNormalBit;
		}else{
			this.whiteBit = this.whiteDownBit;
		}
		this.invalidate();
	}
	
	/**更新bar点的位置
	 * @param ev
	 */
	private void freshBarPos(MotionEvent ev){
		int tmp = (int)getTouchAngle(ev.getX(),ev.getY());
		if((tmp>=this.mStartAngle&&tmp<=360)||(tmp>=0&&tmp<=(180-this.mStartAngle))){
			this.mCurrentAngle = tmp;
			this.mMoveAngle = exchangeMoveAngle(this.mCurrentAngle);
			Log.d(TAG,"当前的角度："+this.mCurrentAngle+" 累计角度："+this.mMoveAngle);
			getBarLocation();
			this.invalidate();
		}
	}
	
	/**
	 * 设置bar点的位置，根据movebar点
	 * */
	public void setBarPost(){
		
	}
	
	/**判断是否在操作白光
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isInWhiteCycle(float x,float y){
		float sx = x-cPointX;
		float sy = y-cPointY;
		
		float touchRadius = (float)Math.sqrt(((sx*sx)+(sy*sy)));
		
		if(touchRadius<=this.whiteRadius){
			Log.d(TAG,"在白光内操作");
			return true;
		}
		
		return false;
	}
	
	/**判断是否在hue内
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isInHueCycle(float x,float y){
		float sx = x-cPointX;
		float sy = y-cPointY;
		
		float touchRadius = (float)Math.sqrt(((sx*sx)+(sy*sy)));
		
		if(touchRadius<=this.hueRadius&&touchRadius>this.whiteRadius){
			Log.d(TAG,"在hue内操作");
			return true;
		}
		
		return false;
	}
	
	/**判断是否开始调节进度条
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isInBarArc(float x,float y){
		
		float sx = x-cPointX;
		float sy = y-cPointY;
		
		float touchRadius = (float)Math.sqrt(((sx*sx)+(sy*sy)));
		
		if(touchRadius>this.barRadiusOut){
			Log.d(TAG,"在圈外面");
			return false;
		}
		
		if(touchRadius<this.barRadiusIn){
			Log.d(TAG,"在圈里面");
			return false;
		}
		
		Log.d(TAG,"刚刚好");
		
		return true;
	}
	
	/**判断是否还在控制中
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isInBarCyc(float x,float y){
		float sx = x-cPointX;
		float sy = y-cPointY;
		
		float touchRadius = (float)Math.sqrt(((sx*sx)+(sy*sy)));
		
		if(touchRadius<this.barRadiusOut){
			Log.d(TAG,"在控制中");
			return true;
		}
		
		return false;
	}
	
	/**由点击点得到角度
	 * @param x
	 * @param y
	 * @return
	 */
	private double getTouchAngle(float x,float y){
		
		double angle = 0;
		
		float sx = x - this.cPointX;
		float sy = y - this.cPointY;
		
		angle = Math.toDegrees(Math.atan2(sy, sx));
		
		if(angle<0){
			angle= angle+360;
		}
		
		Log.d(TAG,"点击了一个角度:"+angle);
		
		return angle;
	}
	
	/**得到bar点的位置
	 * 
	 */
	private void getBarLocation(){
		this.barX = (int) (this.mArcRadius*Math.cos(Math.toRadians(this.mCurrentAngle)))+this.cPointX-this.barWidth/2;
		this.barY = (int) (this.mArcRadius*Math.sin(Math.toRadians(this.mCurrentAngle)))+this.cPointY-this.barHight/2;
		Log.d(TAG,"position barx:"+this.barX+" bary:"+this.barY);
	}
	
	private int exchangeMoveAngle(int cangle){
		int angle = 0;
		
		if(cangle>=this.mStartAngle&&cangle<=360){
			angle = cangle - this.mStartAngle;
		}else if(cangle<=(180-this.mStartAngle)&&cangle>=0){
			angle = this.mSweepAngle+cangle;
		}else{
			angle = 0;
		}
		
		return angle;
	}
	
	Runnable showZoom = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			popup.showAtLocation(RgbLightControlView.this, Gravity.NO_GRAVITY, getLeft()+dstPoint.x, getTop()+dstPoint.y);
		}
		
	};
	
	class Magnifier extends View{
		
		private Paint mPaint;
		private Rect mRect;
		private Path clip;

		public Magnifier(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setColor(0xff008000);
			mPaint.setStyle(Style.STROKE);
			mRect = new Rect(0,0,zoomRadius*2,zoomRadius*2);
			clip = new Path();
			clip.addCircle(zoomRadius, zoomRadius, zoomRadius, Direction.CW);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			canvas.save();
			mPaint.setAlpha(255);
			canvas.drawBitmap(zoombit, mRect, mRect,mPaint);
			canvas.restore();
			
			mPaint.setAlpha(220);
			canvas.drawBitmap(magnifierBit, 0,0,mPaint);
		}
	}
	
	public interface OnColorPanChangeListener{
		void onStart(RgbLightControlView seeArc,int r,int g,int b);
		void onMove(RgbLightControlView seeArc,int r,int g,int b);
		void onStop(RgbLightControlView seeArc,int r,int g,int b);
		void onwhite(RgbLightControlView seeArc);
	}
	
	public interface OnSeekArcBarChangeListener{
		//移动中
		void onProgressChanged(RgbLightControlView seeArc,int progress,boolean fromUser);
		//启动移动
		void onProgressStart(RgbLightControlView seeArc);
		//移动完毕
		void onProgressStop(RgbLightControlView seeArc,int progress,boolean fromUser);
	}
	
	

}
