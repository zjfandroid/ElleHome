package elle.home.uipart;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import elle.home.app.R;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;

public class ManageDevItem extends LinearLayout {

	public OneDev dev;
	Context context;
	
	private FrameLayout framelayout;
	private LinearLayout bk;
	
	private ImageView manageDevItemLogo;
	private TextView manageDevItemName;
	public Button manageDevConfigBtn;
	public Button manageDevDeleteBtn;
	private boolean btnVisable;
	
	// 弹性滑动对象，提供弹性滑动效果
    private Scroller mScroller;
	
	private View self;
	
	int mHolderWidth = 160;
	private RelativeLayout holder;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
    private static final int TAN = 2;
	
	public ManageDevItem(Context context,OneDev dev) {
		super(context);
		// TODO Auto-generated constructor stub
		this.dev = dev;
		this.context = context;
		initview(context);
		self = this;
	}
	
	public void togBtn(){
		if(btnVisable==false){
			btnVisable = true;
			this.smoothScrollTo(mHolderWidth, 0);
			//manageDevDeleteBtn.setVisibility(View.VISIBLE);
		}else{
			btnVisable = false;
			this.smoothScrollTo(0, 0);
			//manageDevDeleteBtn.setVisibility(View.INVISIBLE);
		}		
	}
	
	public void hideBtn(){
		btnVisable = false;
		this.smoothScrollTo(0, 0);
		//manageDevDeleteBtn.setVisibility(View.INVISIBLE);
	}
	
	public void initview(Context context){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.manage_dev_item, this);
		mScroller = new Scroller(context);
		this.manageDevItemLogo = (ImageView)this.findViewById(R.id.manage_dev_item_iv);
		this.manageDevItemName = (TextView)this.findViewById(R.id.manage_dev_item_tv);
		
		this.manageDevItemLogo.setImageDrawable(getResources().getDrawable(PublicDefine.getManageDevIcon(dev.type)));
		this.manageDevItemName.setText(dev.devname);
		
		this.manageDevConfigBtn = (Button)this.findViewById(R.id.manage_dev_config_btn);
		this.manageDevConfigBtn.setVisibility(View.VISIBLE);
		
		this.manageDevDeleteBtn = (Button)this.findViewById(R.id.manage_dev_delete_btn);
		this.manageDevDeleteBtn.setVisibility(View.VISIBLE);
		this.btnVisable = false;
		
		this.framelayout = (FrameLayout)this.findViewById(R.id.manage_dev_item_layout);
		
		this.holder = (RelativeLayout)this.findViewById(R.id.holderLayout);
		mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));
		
		bk = (LinearLayout)this.findViewById(R.id.manage_dev_item_bk);
		
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
			bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_dev_bk_down));
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
			bk.setBackgroundDrawable(getResources().getDrawable(R.drawable.scene_item_dev_bk));
            // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
            if (scrollX - mHolderWidth * 0.75 > 0) {
                xnewScrollX = mHolderWidth;
            }
            // 慢慢滑向终点
            this.smoothScrollTo(xnewScrollX, 0);
            // 通知上层滑动事件
			break;
		}
		mLastX = x;
        mLastY = y;
		return super.onTouchEvent(event);
        //return true;
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
