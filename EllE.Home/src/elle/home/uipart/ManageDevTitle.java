package elle.home.uipart;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import elle.home.app.R;
import elle.home.database.DevLocationInfo;
import elle.home.publicfun.PublicDefine;

public class ManageDevTitle extends LinearLayout {

	public DevLocationInfo locatinfo;
	Context mcontext;
	
	private LinearLayout bk;
	
	ImageView manageDevTitleLogo;
	TextView manageDevTitleName;
	public ImageButton manageDevDeleteBtn;
	private boolean btnVisable;
	
	// 弹性滑动对象，提供弹性滑动效果
    private Scroller mScroller;
	
	int mHolderWidth = 90;
	FrameLayout holder;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
    private static final int TAN = 2;
	
	public ManageDevTitle(Context context,DevLocationInfo locatinfo) {
		super(context);
		// TODO Auto-generated constructor stub
		this.locatinfo = locatinfo;
		this.mcontext = context;
		initview(context);
	}
	
	public void togBtn(){
		if(btnVisable == false){
			btnVisable = true;
			//manageDevDeleteBtn.setVisibility(View.VISIBLE);
			this.smoothScrollTo(mHolderWidth, 0);
		}else{
			btnVisable = false;
			this.smoothScrollTo(0, 0);
		}		
	}
	
	public void hideBtn(){
		btnVisable = false;
		this.smoothScrollTo(0, 0);
	}
	
	private void initview(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.manage_dev_title, this);
		mScroller = new Scroller(context);
		this.manageDevTitleLogo = (ImageView)this.findViewById(R.id.manage_dev_title_iv);
		this.manageDevTitleName = (TextView)this.findViewById(R.id.manage_dev_title_tv);
		
		this.manageDevTitleLogo.setImageDrawable(getResources().getDrawable(PublicDefine.getResideLocatIcon(locatinfo.locaticon)));
		this.manageDevTitleName.setText(locatinfo.locatname);
		
		this.manageDevDeleteBtn = (ImageButton)this.findViewById(R.id.manage_dev_delete_title_btn);
		//this.manageDevDeleteBtn.setVisibility(View.INVISIBLE);
		this.btnVisable = false;
		
		this.bk = (LinearLayout)this.findViewById(R.id.manage_dev_title_bk);
		
		this.holder = (FrameLayout)this.findViewById(R.id.holderLayout);
		mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();
        
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
			bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_title_bk_down));
			break;
		case MotionEvent.ACTION_MOVE:
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
                // 滑动不满足条件，不做横向滑动
                break;
            }

            // 计算滑动终点是否合法，防止滑动越界
            int newScrollX = scrollX - deltaX;
            if (deltaX != 0) {
                if (newScrollX < 0) {
                    newScrollX = 0;
                } else if (newScrollX > mHolderWidth) {
                    newScrollX = mHolderWidth;
                }
                this.scrollTo(newScrollX, 0);
            }
            
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int xnewScrollX = 0;
            // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
            if (scrollX - mHolderWidth * 0.75 > 0) {
                xnewScrollX = mHolderWidth;
            }
            // 慢慢滑向终点
            this.smoothScrollTo(xnewScrollX, 0);
            // 通知上层滑动事件
            bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_title_bk));
			break;
		}
		mLastX = x;
        mLastY = y;
		return super.onTouchEvent(event);
	}
	
	
	// 将当前状态置为关闭
    public void shrink() {
        if (getScrollX() != 0) {
            this.smoothScrollTo(0, 0);
        }
    }

	private void smoothScrollTo(int destX,int destY){
		int scrollX = getScrollX();
		int delta = destX - scrollX;
		// 以三倍时长滑向destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
	}
	
	@Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

}
