package elle.home.residemenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import elle.home.app.smart.R;
import elle.home.dialog.RegisterDialog;
import elle.home.uipart.KickView;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.StringUtils;

public class ResideMenu extends FrameLayout {
	
	public String TAG = "ResideMenu";
	
    public  static final int DIRECTION_LEFT  = 0;
    public  static final int DIRECTION_RIGHT = 1;
    private static final int PRESSED_MOVE_HORIZANTAL = 2;
    private static final int PRESSED_DOWN = 3;
    private static final int PRESSED_DONE = 4;
    private static final int PRESSED_MOVE_VERTICAL = 5;

    private LinearLayout layoutLeftMenu;
    private LinearLayout layoutRightMenu;
    //private ScrollView scrollViewLeftMenu;
    private KickView scrollViewLeftMenu;
    private ScrollView scrollViewRightMenu;
    private KickView scrollViewMenu;
    private LinearLayout funlayout;
    /** the activity that view attach to */
    private Activity activity;
    /** the decorview of the activity    */
    private ViewGroup viewDecor;
    /** the viewgroup of the activity    */
    private TouchDisableView viewActivity;
    /** the flag of menu open status     */
    private boolean              isOpened;
    private GestureDetector gestureDetector;
    private float shadowAdjustScaleX;
    private float shadowAdjustScaleY;
    /** the view which don't want to intercept touch event */
    private List<View> ignoredViews;
    
    private ResideTitle resideTitle;
    private List<ResideMenuItem> leftMenuItems;
    private List<ResideLocatItem> leftLocatItems;
    private List<ResideSenseItem> leftSenseItems;
    
    private List<ResideMenuItem> rightMenuItems;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private OnMenuListener menuListener;
    private float lastRawX;
    private boolean isInIgnoredView = false;
    private int scaleDirection = DIRECTION_LEFT;
    private int pressedState   = PRESSED_DOWN;
    private List<Integer> disabledSwipeDirection = new ArrayList<Integer>();
    //valid scale factor is between 0.0f and 1.0f.
    private float mScaleValue = 0.5f;	
    
    public View btnsetting;
    public View btnscene;
    
    Context mcontext;

	public ResideMenu(Context context) {
		super(context);
		this.mcontext = context;
		initViews(context);
	}
	
	private void initViews(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.residemenu, this);
		//this.scrollViewLeftMenu = (ScrollView) findViewById(R.id.sv_left_menu);
		this.scrollViewLeftMenu = (KickView) findViewById(R.id.sv_left_menu);
		layoutLeftMenu = (LinearLayout)this.findViewById(R.id.layout_left_menu);
		btnsetting = this.findViewById(R.id.reside_set);
		btnscene = this.findViewById(R.id.reside_more);
		funlayout = (LinearLayout)this.findViewById(R.id.funlayout);
	}
	
	public void attachToActivity(Activity activity){
		initValue(activity);
		setShadowAdjustScaleXByOrientation();
		viewDecor.addView(this,0);
		setViewPadding();
	}
	
	private void initValue(Activity activity){
		this.activity = activity;
		leftMenuItems = new ArrayList<ResideMenuItem>();
		leftLocatItems = new ArrayList<ResideLocatItem>();
		leftSenseItems = new ArrayList<ResideSenseItem>();
		this.resideTitle = new ResideTitle(this.activity);
		ignoredViews = new ArrayList<View>();
		viewDecor = (ViewGroup)activity.getWindow().getDecorView();
		viewActivity = new TouchDisableView(this.activity);
		
		View mContent   = viewDecor.getChildAt(0);
        viewDecor.removeViewAt(0);
        viewActivity.setContent(mContent);
        addView(viewActivity);
        
        ViewGroup parent = (ViewGroup) this.scrollViewLeftMenu.getParent();
        
	}

	private void setShadowAdjustScaleXByOrientation(){
		shadowAdjustScaleX = 0.06f;
        shadowAdjustScaleY = 0.07f;
	}
	
	private void setViewPadding(){
		this.setPadding(viewActivity.getPaddingLeft(), viewActivity.getPaddingTop(), viewActivity.getPaddingRight(), viewActivity.getPaddingBottom());
	}
	
	/**设置侧边栏的标题
	 * @param icon
	 * @param name
	 */
	public void setMenuTitle(int icon,String name){
		this.resideTitle.setView(icon, name);
		layoutLeftMenu.addView(this.resideTitle);
	}
	
	public void setMenuPartLine(){
		ResidePartLine partline = new ResidePartLine(activity);
		layoutLeftMenu.addView(partline);
	}
	
	/**设置侧边栏的地点信息
	 * @param list
	 */
	public void setMenuLocats(List<ResideLocatItem> list){
		this.leftLocatItems = list;
		for(int i=0;i<leftLocatItems.size();i++){
			layoutLeftMenu.addView(leftLocatItems.get(i));
		}
	}
	
	/**设置侧边栏的场景信息
	 * @param list
	 */
	public void setMenuSenses(List<ResideSenseItem> list){
		this.leftSenseItems = list;
		for(int i=0;i<this.leftSenseItems.size();i++){
			this.layoutLeftMenu.addView(this.leftSenseItems.get(i));
		}
	}
	
	public void rmLeftItems(){
		this.layoutLeftMenu.removeAllViews();
	}
	
	public void addMenuItem(ResideMenuItem menuItem,int direction){
		this.leftMenuItems.add(menuItem);
		layoutLeftMenu.addView(menuItem);
	}
	
	public void setMenuItems(List<ResideMenuItem> menuItems,int direction){
		this.leftMenuItems = menuItems;
		rebulidMenu();
	}
	
	private void rebulidMenu(){
		layoutLeftMenu.removeAllViews();
		for(int i=0;i<leftMenuItems.size();i++){
			layoutLeftMenu.addView(leftMenuItems.get(i),i);
		}
	}
	
	public List<ResideMenuItem> getMenuItems(int direction){
		return leftMenuItems;
	}
	
	public void setMenuListener(OnMenuListener menuListener){
		this.menuListener = menuListener;
	}
	
	public OnMenuListener getMenuListener(){
		return this.menuListener;
	}
	
	public void openMenu(int direction){
		setScaleDirection(direction);

        isOpened = true;
        AnimatorSet scaleDown_activity = buildScaleDownAnimation(viewActivity, mScaleValue, mScaleValue);
        AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 1.0f);
        AnimatorSet alpha_fun = buildMenuAnimation(funlayout, 1.0f);
        scaleDown_activity.playTogether(alpha_menu);
        scaleDown_activity.playTogether(alpha_fun);
        scaleDown_activity.start();
	}
	
	public void closeMenu(){
		isOpened = false;
        AnimatorSet scaleUp_activity = buildScaleUpAnimation(viewActivity, 1.0f, 1.0f);
        AnimatorSet alpha_menu = buildMenuAnimation(scrollViewMenu, 0.0f);
        AnimatorSet alpha_fun = buildMenuAnimation(funlayout, 0.0f);
        scaleUp_activity.addListener(animationListener);
        scaleUp_activity.playTogether(alpha_menu);
        scaleUp_activity.playTogether(alpha_fun);
        scaleUp_activity.start();
	}
	
	public void setSwipeDirectionDisable(int direction){
        disabledSwipeDirection.add(direction);
    }
	
	private boolean isInDisableDirection(int direction){
        return disabledSwipeDirection.contains(direction);
    }
	
	private void setScaleDirection(int direction){
		int screenWidth = getScreenWidth();
		float pivotX;
		float pivotY = getScreenHeight()*0.5f;
		scrollViewMenu = scrollViewLeftMenu;
        pivotX  = screenWidth * 1.5f;
        
        ViewHelper.setPivotX(viewActivity, pivotX);
        ViewHelper.setPivotY(viewActivity, pivotY);
        scaleDirection = direction;
	}
	
	public boolean isOpened() {
        return isOpened;
    }
	
	private OnClickListener viewAcitivityOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isOpened())
				closeMenu();
		}
		
	};
	
	private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
		
		@Override
		public void onAnimationStart(Animator animation) {
			// TODO Auto-generated method stub
			if(isOpened()){
				showScrollViewMenu(scrollViewMenu);
				if(menuListener != null)
					menuListener.openMenu();
			}
		}
		
		@Override
		public void onAnimationEnd(Animator animation) {
			// TODO Auto-generated method stub
			if(isOpened()){
				viewActivity.setTouchDisable(true);
				viewActivity.setOnClickListener(viewAcitivityOnClickListener);
			}else{
				viewActivity.setTouchDisable(false);
				viewActivity.setOnClickListener(null);
				hideScrollViewMenu(scrollViewLeftMenu);
				if(menuListener!=null)
					menuListener.closeMenu();
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private AnimatorSet buildScaleDownAnimation(View target,float targetScaleX,float targetScaleY){
		AnimatorSet scaleDown = new AnimatorSet();
		scaleDown.playTogether(
				ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
				ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
				);
		scaleDown.setInterpolator(AnimationUtils.loadInterpolator(activity, android.R.anim.decelerate_interpolator));
		scaleDown.setDuration(250);
		return scaleDown;
	}
	
	private AnimatorSet buildScaleUpAnimation(View target,float targetScaleX,float targetScaleY){

        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
        );

        scaleUp.setDuration(250);
        return scaleUp;
    }
	
	private void showScrollViewMenu(ScrollView scrollViewMenu){
        if (scrollViewMenu != null && scrollViewMenu.getParent() == null){
            addView(scrollViewMenu);
        }
    }
	
	private AnimatorSet buildMenuAnimation(View target, float alpha){

        AnimatorSet alphaAnimation = new AnimatorSet();
        alphaAnimation.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", alpha)
        );

        alphaAnimation.setDuration(250);
        return alphaAnimation;
    }

    private void hideScrollViewMenu(ScrollView scrollViewMenu){
        if (scrollViewMenu != null && scrollViewMenu.getParent() != null){
            removeView(scrollViewMenu);
        }
    }
    
    public void addIgnoredView(View v){
        ignoredViews.add(v);
    }
    
    public void removeIgnoredView(View v){
        ignoredViews.remove(v);
    }
    
    public void clearIgnoredViewList(){
        ignoredViews.clear();
    }
	
	public int getScreenHeight(){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getScreenWidth(){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    
    public void setScaleValue(float scaleValue) {
        this.mScaleValue = scaleValue;
    }
    
    private boolean isInIgnoredView(MotionEvent ev) {
        Rect rect = new Rect();
        for (View v : ignoredViews) {
            v.getGlobalVisibleRect(rect);
            if (rect.contains((int) ev.getX(), (int) ev.getY()))
                return true;
        }
        return false;
    }
    
    private void setScaleDirectionByRawX(float currentRawX){
        setScaleDirection(DIRECTION_LEFT);
    }
    
    private float getTargetScale(float currentRawX){
        float scaleFloatX = ((currentRawX - lastRawX) / getScreenWidth()) * 0.75f;

        float targetScale = ViewHelper.getScaleX(viewActivity) - scaleFloatX;
        targetScale = targetScale > 1.0f ? 1.0f : targetScale;
        targetScale = targetScale < 0.5f ? 0.5f : targetScale;
        return targetScale;
    }
    
    private float lastActionDownX, lastActionDownY;

    public boolean dispatchTouchEvent(MotionEvent ev){
    	
    	float currentActivityScaleX = ViewHelper.getScaleX(viewActivity);
    	if (currentActivityScaleX == 1.0f)
            setScaleDirectionByRawX(ev.getRawX());
    	
    	switch(ev.getAction()){
    		case MotionEvent.ACTION_DOWN:
    			lastActionDownX = ev.getX();
                lastActionDownY = ev.getY();
                isInIgnoredView = isInIgnoredView(ev) && !isOpened();
                pressedState    = PRESSED_DOWN;
    			break;
    		case MotionEvent.ACTION_MOVE:
    			if(isInIgnoredView || isInDisableDirection(scaleDirection))
    				break;
    			if(pressedState != PRESSED_DOWN && pressedState != PRESSED_MOVE_HORIZANTAL)
    				break;
    			int xOffset = (int) (ev.getX() - lastActionDownX);
                int yOffset = (int) (ev.getY() - lastActionDownY);
                if(pressedState == PRESSED_DOWN) {
                    if(yOffset > 25 || yOffset < -25) {
                        pressedState = PRESSED_MOVE_VERTICAL;
                        break;
                    }
                    if(xOffset < -50 || xOffset > 50) {
                        pressedState = PRESSED_MOVE_HORIZANTAL;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                } else if(pressedState == PRESSED_MOVE_HORIZANTAL) {
                    if (currentActivityScaleX < 0.95)
                        showScrollViewMenu(scrollViewMenu);

                    float targetScale = getTargetScale(ev.getRawX());
                    ViewHelper.setScaleX(viewActivity, targetScale);
                    ViewHelper.setScaleY(viewActivity, targetScale);
                    ViewHelper.setAlpha(scrollViewMenu, (1 - targetScale) * 2.0f);
                    ViewHelper.setAlpha(funlayout, (1 - targetScale) * 2.0f);
                    
                    lastRawX = ev.getRawX();
                    return true;
                }
    			break;
    		case MotionEvent.ACTION_UP:

                if (isInIgnoredView) break;
                if (pressedState != PRESSED_MOVE_HORIZANTAL) break;

                pressedState = PRESSED_DONE;
                if (isOpened()){
                    if (currentActivityScaleX > 0.56f)
                        closeMenu();
                    else
                        openMenu(scaleDirection);
                }else{
                    if (currentActivityScaleX < 0.94f){
                        openMenu(scaleDirection);
                    }else{
                        closeMenu();
                    }
                }

                break;
    	}
    	lastRawX = ev.getRawX();
    	return super.dispatchTouchEvent(ev);
    }
	
	public interface OnMenuListener{

        /**
         * the method will call on the finished time of opening menu's animation.
         */
        public void openMenu();

        /**
         * the method will call on the finished time of closing menu's animation  .
         */
        public void closeMenu();
    }
	
	public void setOnBtnClickListener(OnClickListener onClickListener) {
		final TextView btn = (TextView) findViewById(R.id.tv_login);
		btn.setOnClickListener(onClickListener);
		
		String name = SaveDataPreferences.load(mcontext, RegisterDialog.KEY_USER_NAME, "");
		if(!StringUtils.isEmpty(name)){
			btn.setText(name);
		}
		
		btn.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				SaveDataPreferences.save(mcontext, RegisterDialog.KEY_USER_NAME, "");
				btn.setText(mcontext.getResources().getString(R.string.login));
				return true;
			}
		});
	}

	public void checkLogin() {
		TextView btn = (TextView) findViewById(R.id.tv_login);
		String name = SaveDataPreferences.load(mcontext, RegisterDialog.KEY_USER_NAME, "");
		if(StringUtils.isEmpty(name)){
			btn.setText(getResources().getString(R.string.login));
		}else{
			btn.setText(name);
		}
	}
}
