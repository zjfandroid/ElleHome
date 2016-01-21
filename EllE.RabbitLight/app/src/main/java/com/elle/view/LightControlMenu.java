package com.elle.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.elle.pojo.Constants;
import com.elle.pojo.LightColor;

import java.util.ArrayList;
import java.util.List;

import chrisrenke.elle.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;

/**
 * Created by jason on 15/8/9.
 */
public class LightControlMenu extends FrameLayout {

    private static final int COLOR_BLUE = 0xff58b7f3;
    private Context mContext;
    private View mLayoutLight;
    private View mLayoutAnion;
    private View mArrowLight;
    private TextView textView0;
    private TextView textView1;
    private TextView textView2;
    private View mArrowAnion;
    private OnBulbControlListener mOnBulbControlListener;

    private SlidingTabStrip tabs;
    private TabAdapter adapter;

    private boolean isAnionEnable;
    private SeekBar mSeekBar;

    public LightControlMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }

    public LightControlMenu(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public LightControlMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_light_control_menu, this);
        View mBtn = findViewById(R.id.btn_light);
        mBtn.setOnClickListener(mOnClickListener);
        mBtn = findViewById(R.id.btn_anion);
        mBtn.setOnClickListener(mOnClickListener);

        mLayoutAnion = findViewById(R.id.layout_anion_fun);
        mLayoutLight = findViewById(R.id.layout_light_fun);
        mArrowLight = findViewById(R.id.img_light_arrow);
        mArrowAnion = findViewById(R.id.img_anion_arrow);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutLight.setVisibility(View.GONE);
                mLayoutAnion.setVisibility(View.GONE);
                mArrowLight.setVisibility(View.INVISIBLE);
                mArrowAnion.setVisibility(View.INVISIBLE);
            }
        });

        textView0 = (TextView) findViewById(R.id.tv_off);
        textView1 = (TextView) findViewById(R.id.tv_buty_face);
        textView2 = (TextView) findViewById(R.id.tv_kill_bad);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar_anion);

        textView0.setTextColor(0xff58b7f3);
        textView0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setProgress(0);
            }
        });
        textView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setProgress(1);
            }
        });
        textView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setProgress(2);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                ShowInfo.printLogW("_______onProgressChanged_________" + progress);
                switch (progress) {
                    case 0:
                        setAnionChange(0);
                        textView0.setTextColor(COLOR_BLUE);
                    case 1:
                    case 2:
                        mSeekBar.setProgress(0);
                        break;

                    case 4:
                        setAnionChange(1);
                        textView1.setTextColor(COLOR_BLUE);
                        break;
                    case 3:
                    case 5:
                        mSeekBar.setProgress(4);
                        break;

                    case 8:
                        setAnionChange(2);
                        textView2.setTextColor(COLOR_BLUE);
                        break;
                    case 6:
                    case 7:
                        mSeekBar.setProgress(8);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        adapter = new TabAdapter();
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                tabs = (SlidingTabStrip) findViewById(R.id.layout_light_tabs);
                tabs.setLayoutWith(getWidth());
                tabs.setViewListener(adapter);
                tabs.setmOnTabClickListener(new SlidingTabStrip.OnTabClickListener() {
                    @Override
                    public void onTabClick(int pos, int id) {
                        switch (id) {
                            case Constants.ID_CLOSE:
                                mOnBulbControlListener.onOffClick();
                                break;
                            case Constants.ID_RANDOM:
                                mOnBulbControlListener.randomClick();
                                break;
                            case Constants.ID_ADD:
                                mOnBulbControlListener.onAddClick();
                                break;
                            default:
                                mOnBulbControlListener.onColorClick(pos);
                                break;

                        }
                    }

                    @Override
                    public void onTabLongClick(final int pos, int id) {
                        SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);

                        dialog.setTitleText(mContext.getResources().getString(R.string.sure_to_del_color))
                                .setCancelText(mContext.getResources().getString(R.string.dialog_cancel))
                                .setConfirmText(mContext.getResources().getString(R.string.dialog_ok))
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        adapter.deleteDrawableItem(pos);
                                        tabs.notifyDataSetChanged();
                                        mOnBulbControlListener.deleteLightColor(pos - 2);
                                        sDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
            }
        }, 600);
    }

    private void setAnionChange(int level){
        Resources resources = getResources();
        textView0.setTextColor(resources.getColor(R.color.white));
        textView1.setTextColor(resources.getColor(R.color.white));
        textView2.setTextColor(resources.getColor(R.color.white));
        mOnBulbControlListener.onAnionChange(level);
    }

    public void setLightOn(boolean isOn){
        TabItem item = adapter.mDrawable.get(0);
        String txtOff = getResources().getString(R.string.lamp_off);
        String txtOn = getResources().getString(R.string.lamp_on);

        if(isOn && !txtOff.equals(item.name)){
            item.name = txtOff;
            notifyDataSetChanged();
        }else if(!isOn && !txtOn.equals(item.name)){
            item.name = txtOn;
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged(){
        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.notifyDataSetChanged();
            }
        });
    }

    public void setAnionLevel(byte level) {
        switch (level){
            case PublicDefine.AnionLevelOff:
                mSeekBar.setProgress(0);
                break;
            case PublicDefine.AnionLevelMin:
            case PublicDefine.AnionLevelMid:
                mSeekBar.setProgress(4);
                break;
            case PublicDefine.AnionLevelMax:
                mSeekBar.setProgress(8);
                break;
        }
    }

    public class TabAdapter implements SlidingTabStrip.IconTabProvider {

        private List<TabItem> mDrawable;

        public TabAdapter(){
            initMenuBtn();
        }

        public void initMenuBtn() {
            mDrawable = new ArrayList<TabItem>(8);
            Resources mRes = getResources();
            mDrawable.add(new TabItem(Constants.ID_CLOSE, mRes.getDrawable(R.drawable.btn_light_off), mRes.getString(R.string.lamp_off)));
            mDrawable.add(new TabItem(Constants.ID_RANDOM, mRes.getDrawable(R.drawable.btn_light_random), mRes.getString(R.string.lamp_random)));
            mDrawable.add(new TabItem(Constants.ID_ADD, mRes.getDrawable(R.drawable.btn_add), mRes.getString(R.string.lamp_add)));
        }

        public void addDrawableItem(TabItem drawable){
            int size = mDrawable.size();
            ShowInfo.printLogW("_______addDrawableItem______" + size);
            mDrawable.add(size-1, drawable);
            if(size >=8){
                mDrawable.remove(size);
            }
        }

        public void deleteDrawableItem(int pos){
            int size = mDrawable.size();
            ShowInfo.printLogW(pos + "_______deleteDrawableItem______" + size);
            if(mDrawable.get(size-1).id != Constants.ID_ADD){
                mDrawable.add(new TabItem(Constants.ID_ADD, getResources().getDrawable(R.drawable.btn_add), getResources().getString(R.string.lamp_add)));
            }
            mDrawable.remove(pos);
        }

        @Override
        public int getPageCount() {
            return mDrawable.size();
        }

        @Override
        public int getId(int position) {
            return mDrawable.get(position).id;
        }

        @Override
        public Drawable getDrawable(int position) {
            return mDrawable.get(position).mDrawable;
        }

        @Override
        public String getPageIconName(int position) {
            return mDrawable.get(position).name;
        }

    }

    public class TabItem{
        int id = 0;
        Drawable mDrawable;
        String name;

        public TabItem(Drawable mDrawable, String name){
            this(0, mDrawable, name);
        }

        public TabItem(int id, Drawable mDrawable, String name){
            this.id = id;
            this.mDrawable = mDrawable;
            this.name = name;
        }
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_light:
                    if (!mLayoutLight.isShown()) {
                        mLayoutLight.setVisibility(View.VISIBLE);
                        mArrowLight.setVisibility(View.VISIBLE);
                        mLayoutAnion.setVisibility(View.GONE);
                        mArrowAnion.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.btn_anion:
                    if (isAnionEnable && !mLayoutAnion.isShown()) {
                        mLayoutAnion.setVisibility(View.VISIBLE);
                        mArrowAnion.setVisibility(View.VISIBLE);
                        mLayoutLight.setVisibility(View.GONE);
                        mArrowLight.setVisibility(View.INVISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void setAnionEnable(boolean isEnabled){
        if(this.isAnionEnable != isEnabled){
            isAnionEnable = isEnabled;
            if(!isEnabled){
                this.post(new Runnable() {
                    @Override
                    public void run() {
                        mLayoutAnion.setVisibility(View.GONE);
                        mArrowAnion.setVisibility(View.INVISIBLE);
                        setAnionLevel(PublicDefine.AnionLevelOff);
                    }
                });
            }
        }
    }

    public void setBulbControlListener(OnBulbControlListener onBulbControlListener){
        mOnBulbControlListener = onBulbControlListener;
    }

    public void resetMenu(){
        adapter.initMenuBtn();
    }

    public void addLightColor(LightColor mcolor) {
        int strokeWidth = getResources().getDimensionPixelOffset(R.dimen.DIMEN_8PX); // 3dp 边框宽度
        int roundRadius = 360; // 8dp 圆角半径
        int strokeColor = Color.parseColor("#ffffff");//边框颜色
        int fillColor = mcolor.getColor();//内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        adapter.addDrawableItem(new TabItem(gd, mcolor.getName()));
        if(null != tabs){
            tabs.notifyDataSetChanged();
        }

        ShowInfo.printLogW(mcolor.getName() + "+______setLightColor________" + mcolor.getColor());
    }

    public interface OnBulbControlListener{
        void onOffClick();
        void randomClick();
        void onAddClick();
        void onColorClick(int pos);
        void onAnionChange(int value);
        void deleteLightColor(int pos);
    }
//
//    /**
//     * 计算控件的大小
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int measureWidth = measureWidth(widthMeasureSpec);
//        int measureHeight = measureHeight(heightMeasureSpec);
//        // 计算自定义的ViewGroup中所有子控件的大小
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        // 设置自定义的控件MyViewGroup的大小
//        setMeasuredDimension(measureWidth, measureHeight);
//    }
//
//    private int measureWidth(int pWidthMeasureSpec) {
//        int result = 0;
//        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
//        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸
//
//        switch (widthMode) {
//            /**
//             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
//             * MeasureSpec.AT_MOST。
//             *
//             *
//             * MeasureSpec.EXACTLY是精确尺寸，
//             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
//             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
//             *
//             *
//             * MeasureSpec.AT_MOST是最大尺寸，
//             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
//             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
//             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
//             *
//             *
//             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
//             * 通过measure方法传入的模式。
//             */
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.EXACTLY:
//                result = widthSize;
//                break;
//        }
//        return result;
//    }
//
//    private int measureHeight(int pHeightMeasureSpec) {
//        int result = 0;
//
//        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
//
//        switch (heightMode) {
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.EXACTLY:
//                result = heightSize;
//                break;
//        }
//        return result;
//    }
//
//    /**
//     * 覆写onLayout，其目的是为了指定视图的显示位置，方法执行的前后顺序是在onMeasure之后，因为视图肯定是只有知道大小的情况下，
//     * 才能确定怎么摆放
//     */
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        // 记录总高度
//        int mTotalHeight = 0;
//        // 遍历所有子视图
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//
//            // 获取在onMeasure中计算的视图尺寸
//            int measureHeight = childView.getMeasuredHeight();
//            int measuredWidth = childView.getMeasuredWidth();
//
//            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight
//                    + measureHeight);
//
//            mTotalHeight += measureHeight;
//
//        }
//    }
}
