package com.elle.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.elle.pojo.Constants;
import com.elle.pojo.LightColor;

import chrisrenke.elle.LightMainActivity;
import chrisrenke.elle.R;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

public class SetLightColorDialog extends Dialog {

    private SeekBar mSeekColor;
    private SeekBar mSeekLight;
    private EditText mEditName;
    private ImageView imageViewLight;

    private Context mContext;

    private View mRoot;

    private Bitmap srcColor;

    private long lastChangeTime;

    public SetLightColorDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public SetLightColorDialog(Context context, int theme) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
    }

    private View getView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_set_light_color,
                null);

        mSeekColor = (SeekBar) v.findViewById(R.id.sb_color);
        mSeekLight = (SeekBar) v.findViewById(R.id.sb_light);
        mEditName  = (EditText) v.findViewById(R.id.et_name);
        imageViewLight = (ImageView)v.findViewById(R.id.img_light);

        srcColor = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.progress_color);
        mSeekColor.setMax(srcColor.getWidth()-1);

        mSeekColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pixelColor = getPixelColor(progress);
                long time = System.currentTimeMillis();

                if(time - lastChangeTime > 300){
                    lastChangeTime = time;
                    if(pixelColor == Color.WHITE){
                        ((LightMainActivity)mContext).sendBulbColor(0, 0, 0,
                                WhiteToLux(mSeekLight.getProgress()), mSeekLight.getProgress(), 200);
                    }else{
                        ((LightMainActivity)mContext).setColorLight(Color.red(pixelColor), Color.green(pixelColor),Color.blue(pixelColor),
                                0, mSeekLight.getProgress(), 200);
                    }
                }

                setLightBgColor(pixelColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lastChangeTime = 0;
            }
        });

        mSeekLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pixelColor = getPixelColor(progress);
                long time = System.currentTimeMillis();

                if(time - lastChangeTime > 300) {
                    lastChangeTime = time;
                    if (pixelColor == Color.WHITE) {
                        ((LightMainActivity) mContext).setColorLight(WhiteToLux(progress), progress, 200);
                    } else {
                        ((LightMainActivity) mContext).setColorLight(0, progress, 200);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lastChangeTime = 0;
            }
        });

        v.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mEditName || mEditName.getText().toString().isEmpty()) {
                    ShowToast.show(mContext, R.string.tips_name_first);
                    return;
                }

                int pixelColor = getPixelColor(mSeekColor.getProgress());

                LightColor mLightColor = new LightColor(mEditName.getText().toString(), pixelColor, mSeekLight.getProgress());
                Intent mIntent = new Intent();
                mIntent.putExtra(Constants.KEY_LIGHT_COLOR, mLightColor);

                ((LightMainActivity) mContext).addLightColor(mLightColor);

                dismiss();
            }
        });

        v.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//
//        int roundRadius = 360; // 8dp 圆角半径
//
//        GradientDrawable gd = new GradientDrawable();//创建drawable
//        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
//        gd.setColors(new int[]{Color.RED, Color.YELLOW, Color.CYAN,  Color.GREEN, Color.BLUE, Color.MAGENTA, Color.GRAY, Color.LTGRAY, Color.WHITE});
//        gd.setCornerRadius(roundRadius);
//        mSeekColor.setProgressDrawable(gd);

        return v;
    }

    private int getPixelColor(int progress) {
        int pixelColor = 0;

        int height = srcColor.getHeight();
        int w = srcColor.getWidth();
        int x = progress;

        if(x < 20){
            pixelColor = Color.RED;
        }else if(x>w-20){
            pixelColor = Color.WHITE;
        }else{
            pixelColor = srcColor.getPixel(x, height / 2);
        }

        if(pixelColor>=Color.YELLOW && pixelColor<=Color.WHITE){
            imageViewLight.setImageResource(R.drawable.light_blue_full);
        }else{
            imageViewLight.setImageResource(R.drawable.img_light_white);
        }

        return pixelColor;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRoot == null) {
            mRoot = getView();
            setContentView(mRoot);
        }
    }

    public void setLightBgColor(int color) {
        int roundRadius = 360; // 8dp 圆角半径

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(color);
        gd.setCornerRadius(roundRadius);

        ShowInfo.printLogW("______setLightBgColor_______" + color);
        imageViewLight.setBackgroundDrawable(gd);
    }

    public int WhiteToLux(int a){
        int tmp = 0;
        tmp = (int)((float)255*(float)a/(float)300);
        return tmp;
    }
}
