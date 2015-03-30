package elle.home.uipart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class InfraTvView extends View {
	
	public String TAG = "InfraTvView";
	
	public final int TouchNull = 0;
	public final int TouchUp = 1;
	public final int TouchDown = 2;
	public final int TouchLeft = 3;
	public final int TouchRight = 4;
	public final int TouchOk = 5;
	public final int TouchPower = 6;
	public final int TouchMenu = 7;
	public final int TouchBack = 8;
	public final int TouchSignal = 9;
	
	
	public final int dViewWidth = 640;
	public final int dViewHeight = 1136;
	
	//定义按键圆形的位置及半径
	public final int dHueX = 100;
	public final int dHueY = 100;
	public final int dOkRadius = 50;
	public final int dBarRadius = 100;
	
	//定义上下左右按键的角度
	public final int dLeftStartAngle = 135;
	public final int dLedtEndAngle = 225;
	public final int dUpStartAngle = 225;
	public final int dUpEndAngle = 315;
	public final int dRightStartAngle = 315;
	public final int dRightEndAngle = 45;
	public final int dDownStartAngle = 45;
	public final int dDownEndAngle = 135;
	
	//定义按键power的位置：
	public final int dPowerLeft =0;
	public final int dPowerRight = 0;
	public final int dPowerUp = 0;
	public final int dPowerDown = 0;
	
	//定义按键menu的位置
	public final int dMenuLeft =0;
	public final int dMenuRight = 0;
	public final int dMenuUp = 0;
	public final int dMenuDown = 0;
	
	//定义按键ok的位置
	public final int dOkLeft =0;
	public final int dOkRight = 0;
	public final int dOkUp = 0;
	public final int dOkDown = 0;
	
	//定义按键back的位置
	public final int dBackLeft =0;
	public final int dBackRight = 0;
	public final int dBackUp = 0;
	public final int dBackDown = 0;
	
	//定义按键signal的位置
	public final int dSignalLeft =0;
	public final int dSignalRight = 0;
	public final int dSignalUp = 0;
	public final int dSignalDown = 0;
	
	//
	public int touchStatus = TouchNull;
	
	public int hueX = 0;
	public int hueY = 0;
	public int barRadius = 0;
	public int okRadius = 0;
	
	//定义按键power的位置：
	public final int powerLeft =0;
	public final int powerRight = 0;
	public final int powerUp = 0;
	public final int powerDown = 0;
	
	//定义按键menu的位置
	public final int menuLeft =0;
	public final int menuRight = 0;
	public final int menuUp = 0;
	public final int menuDown = 0;
	
	//定义按键ok的位置
	public final int okLeft =0;
	public final int okRight = 0;
	public final int okUp = 0;
	public final int okDown = 0;
	
	//定义按键back的位置
	public final int backLeft =0;
	public final int backRight = 0;
	public final int backUp = 0;
	public final int backDown = 0;
	
	//定义按键signal的位置
	public final int signalLeft =0;
	public final int signalRight = 0;
	public final int signalUp = 0;
	public final int signalDown = 0;
	

	public InfraTvView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview(context);
	}
	
	private void initview(Context context){
		
	}
	
	/**
	 * 是否在信号按钮中
	 * */
	private boolean isInSignal(MotionEvent ev){
		if(ev.getX()>this.signalLeft&&ev.getX()<this.signalRight){
			if(ev.getY()>this.signalUp&&ev.getY()<this.signalDown){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否在返回按钮中
	 * */
	private boolean isInBack(MotionEvent ev){
		if(ev.getX()>this.backLeft&&ev.getX()<this.backRight){
			if(ev.getY()>this.backUp&&ev.getY()<this.backDown){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否在menu按钮内
	 * */
	private boolean isInMenu(MotionEvent ev){
		if(ev.getX()>this.menuLeft&&ev.getX()<this.menuRight){
			if(ev.getY()>this.menuUp&&ev.getY()<this.menuDown){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否在ok按钮的范围内
	 * */
	private boolean isInOkCycle(int touchradius){
		if(touchradius<this.okRadius){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否在大圈范围内
	 * */
	private boolean isInBigCycle(int touchradius){
		if(touchradius<this.barRadius){
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

}
