/*
 * Copyright (C) 2014 Chris Renke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package chrisrenke.elle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elle.pojo.LightColor;
import com.elle.view.AddLightDialog;
import com.elle.view.LightControlMenu;
import com.elle.view.RevealLayout;
import com.elle.view.SetLightColorDialog;
import com.elle.view.SettingDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import elle.home.database.AllDevInfo;
import elle.home.database.OneDev;
import elle.home.hal.sdk.SmartHomeService;
import elle.home.protocol.BasicPacket;
import elle.home.protocol.LightControlPacket;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.publicfun.DataExchange;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.JsonUtil;
import elle.home.utils.ShowInfo;

/**
 * 主界面
 */
public class LightMainActivity extends Activity {

    public static final int MSG_DIS_LIGHT = 0x123;

    public static boolean isFirstLoad = true;

    private View layout_light_progress;
    private ProgressBar progress_bar;

    private LightControlMenu menu;
    private ImageView imageLightAnim;
    private DrawerLayout drawer;
    private RevealLayout revealLayout;
    private PopupWindow popupWindow;
    private Button mPopBtn;
    private TextView mTvPower;
    private TextView mTvPowerLeft;

    private SetLightColorDialog setLightColorDialog;
    private AddLightDialog addLightDialog;

    private boolean onoff, random, isWhite;

    private AllDevInfo mAllDevInfo;
    private List<OneDev> mDevices;
    private OneDev oneDev;

    //颜色改变后更换背景
    int mred, mgreen, mblue, lux;
    private int indexLight;
    private int colorRed = -1;
    private int colorGreen = -1;
    private int colorBlue = -1;
    private int power;
    private int anionLevel;
    private final float BlueAdjust = 0.75f;
    private final float GreenAdjust = 0.8f;
    private long lastLuxChangeTime;

    private MyHandler mHandler = new MyHandler(this);

    //定时查询回调接口
    private OnRecvListener cycleListener = new OnRecvListener() {
        @Override
        public void OnRecvData(PacketCheck packetcheck) {
            if (packetcheck != null) {
                /*********灯的开关状态*********/
                boolean onoff = true;
                if (packetcheck.xdata[0] == 0x00) {
                    onoff = false;    //关闭状态
                }

                if(LightMainActivity.this.onoff != onoff){
                    LightMainActivity.this.onoff = onoff;
                    menu.setLightOn(onoff);
                }

                /*********灯的随机变色状态**********/
                if (packetcheck.xdata[1] == 0x00) {
                    random = false;    //关闭状态
                } else {
                    random = true;    //打开状态
                }

                /***************亮度***************/
                lux = DataExchange.twoByteToInt(packetcheck.xdata[12], packetcheck.xdata[13]);
                /*************颜色值***************/
                //white
                if(DataExchange.twoByteToInt(packetcheck.xdata[8], packetcheck.xdata[9])>0){
                    //灯泡状态为白色
                    isWhite = true;
                }else{
                    isWhite = false;
                    ShowInfo.printLogW("状态颜色 原始接收______：" + DataExchange.twoByteToInt(packetcheck.xdata[2], packetcheck.xdata[3])
                            + " " + DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5])
                            + " " + DataExchange.twoByteToInt(packetcheck.xdata[6], packetcheck.xdata[7]));

                    colorRed = LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[2], packetcheck.xdata[3]), lux);
                    colorGreen = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5]), lux)/GreenAdjust);
                    colorBlue = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[6], packetcheck.xdata[7]), lux)/BlueAdjust);

                    ShowInfo.printLogW("状态颜色 接收转后______：" + colorRed + " " + colorGreen + " " + colorBlue);
                }
                /***************睡眠时间**********/
                //sleepTime = DataExchange.twoByteToInt(packetcheck.xdata[14], packetcheck.xdata[15]);

                if(colorRed != mred || colorGreen != mgreen || colorBlue!=mblue ){
                    mred = colorRed;
                    mgreen = colorGreen;
                    mblue = colorBlue;

                    menu.post(new Runnable() {
                        @Override
                        public void run() {
                            int color =  Color.rgb(mred, mgreen, mblue);
                            revealLayout.setBackgroundColor(color, (int)(Math.random()*revealLayout.getWidth()),
                                    (int)(Math.random()*revealLayout.getHeight()), 800);
                            if(null != setLightColorDialog && setLightColorDialog.isShowing()){
                                setLightColorDialog.setLightBgColor(color);
                            }
                        }
                    });
                }

                /**********第17个字节，负离子等级************/
                ShowInfo.printLogW(packetcheck.xdata[16] + "______packetcheck______" + packetcheck.xdata[17]);
                int level = packetcheck.xdata[16];
                if(anionLevel != level){
                    anionLevel = level;
                    menu.setAnionLevel(packetcheck.xdata[16]);
                }

                /**********第18个字节，电量0-0xff************/
                /*电量计算公式：电池电压4.2~3.0V
                采集到电量为a值，则电量电压为0.01941*a，百分比为(0.01941*a-3.0)/1.2*/
                setPower(packetcheck.xdata[17] & 0xff);

                /**********第19个字节，充电状态************/
                if(packetcheck.xdata[18] == 0x01){
                    menu.setAnionEnable(true);
                }else{
                    menu.setAnionEnable(false);
                }
            }
        }
    };

    private GestureDetector mGestureDetector;

    private void setPower(final int a) {
        //* 10 / 9 防止到90充不满的情况
        final int num = Math.round((0.01941f * a - 3.0f)/1.2f * 100 * 10 / 9 );
        if(power == num){
            return;
        }

        ShowInfo.printLogW((0.01941*a-3.0)/1.2 + "____packetcheck_xx_____" + 0.01941*a + "  num = " + num);
        power = num;

        if(null != mTvPower){
            mTvPower.post(new Runnable() {
                @Override
                public void run() {
                    String txt = power + "%";

                    if(power < 20 && power >=0){
                        mTvPower.setVisibility(View.VISIBLE);
                        mTvPower.setText(txt);
                    }else{
                        mTvPower.setVisibility(View.GONE);
                    }

                    mTvPowerLeft.setText(txt);
                }
            });
        }
    }

    Timer timer = new Timer();
    TimerTask timerTask;

    class CheckTask extends TimerTask {
        @Override
        public void run() {
            if(null != oneDev){
                LightControlPacket light = getLightPacket();
                light.setListener(cycleListener);
                light.packCheckPacket(oneDev, PublicDefine.getSeq());
                autoBinder.addPacketToSend(light);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTvPower = (TextView) findViewById(R.id.tv_power);
        mTvPowerLeft = (TextView) findViewById(R.id.tv_bettery);
        revealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        layout_light_progress = findViewById(R.id.layout_light_progress);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        imageLightAnim = (ImageView) findViewById(R.id.img_light_anim);
        imageLightAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOnOffClick();
            }
        });

        findViewById(R.id.drawer_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        mPopBtn = (Button) findViewById(R.id.btn_name);
        mPopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupWindow_view = getLayoutInflater().inflate(R.layout.popupwindow_main, null, false);
                popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setOutsideTouchable(true);
                final LinearLayout linearLayout = (LinearLayout) popupWindow_view.findViewById(R.id.layout_root);

                addLightBtns(linearLayout);

                // 设置动画效果
//                popupWindow.setAnimationStyle(R.style.AnimationFade);
                popupWindow.showAsDropDown(v, -100, -15);
//                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                // 点击其他地方消失
                popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                        return false;
                    }
                });
            }
        });

        menu = (LightControlMenu) findViewById(R.id.layout_bottom_menu);
        menu.setBulbControlListener(new LightControlMenu.OnBulbControlListener() {
            @Override
            public void onOffClick() {
                doOnOffClick();
            }

            @Override
            public void randomClick() {
                ShowInfo.printLogW("______doRandomClick_______" + random);
                if (null == oneDev) {
                    return;
                }
                sendBulbFree(!random);
            }

            @Override
            public void onAddClick() {
                if (null == setLightColorDialog) {
                    setLightColorDialog = new SetLightColorDialog(LightMainActivity.this);
                }
                setLightColorDialog.show();
            }

            @Override
            public void onColorClick(int pos) {
                List<LightColor> lightColors = JsonUtil.stringToArray(oneDev.function, LightColor[].class);
                int index = pos - 2;
                if (null != lightColors && index < lightColors.size()) {
                    LightColor lightColor = lightColors.get(index);
                    int pixelColor = lightColor.getColor();
                    int lux = lightColor.getLight();

                    if (pixelColor == Color.WHITE) {
                        sendBulbColor(0, 0, 0,
                                WhiteToLux(lux), lux, 800);
                    } else {
                        setColorLight(Color.red(pixelColor), Color.green(pixelColor), Color.blue(pixelColor),
                                0, lux, 800);
                    }
                }
            }

            @Override
            public void onAnionChange(int value) {
                switch (value) {
                    case 0:
                        setAnionLevel(PublicDefine.AnionLevelOff);
                        imageLightAnim.setImageResource(R.drawable.light_blue);
                        break;
                    case 1:
                        setAnionLevel(PublicDefine.AnionLevelMid);
                        imageLightAnim.setImageResource(R.drawable.light_blue_mid);
                        break;
                    case 2:
                        setAnionLevel(PublicDefine.AnionLevelMax);
                        imageLightAnim.setImageResource(R.drawable.light_blue_max);
                        break;
                }
            }

            @Override
            public void deleteLightColor(int pos) {
                removeLightColor(pos);
            }
        });

        mAllDevInfo = new AllDevInfo(getApplicationContext());
        checkCurrentDev();

        userBindService();

        //友盟自动更新
        UmengUpdateAgent.update(this);

        setVersionName();

        mGestureDetector = new GestureDetector(this,
                new GestureDetector.OnGestureListener() {
                    public boolean onSingleTapUp(MotionEvent e) {
                        return false;
                    }

                    public boolean onDown(MotionEvent e) {
                        return false;
                    }

                    public void onLongPress(MotionEvent e) {
                    }

                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        return false;
                    }

                    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                            float distanceX, float distanceY) {
                        final double FLING_MIN_VELOCITY = 1;

                        if (distanceY > 0 && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            setLuxChange(true);
                        } else if (distanceY < 0 && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            setLuxChange(false);
                        }
                        return false;
                    }

                    public void onShowPress(MotionEvent e) {
                    }
                });
    }

    private void setLuxChange(boolean isUp) {
        long time = System.currentTimeMillis();
        if(time - lastLuxChangeTime > 100) {
            lastLuxChangeTime = time;
            if (isUp) {
                setLightUp();
            } else {
                setLightDown();
            }

            showLightProgress();
        }
    }

    private void showLightProgress() {
        if(layout_light_progress.isShown()){
            mHandler.removeMessages(MSG_DIS_LIGHT);
            mHandler.sendEmptyMessageDelayed(MSG_DIS_LIGHT, 1500);
        }else{
            layout_light_progress.setVisibility(View.VISIBLE);
        }
    }

    private void dissMissLightProgress() {
        if(null != layout_light_progress){
            layout_light_progress.setVisibility(View.GONE);
        }
    }

    private void setLightDown() {
        int mLux = lux - 10;
        if (mLux > 0){
            lux = mLux;
            if (isWhite) {
                sendBulbColor(0, 0, 0, WhiteToLux(mLux), mLux, 50);
            } else {
                setColorLight(colorRed, colorGreen, colorBlue, 0, mLux, 50);
            }

            progress_bar.setProgress(lux/3);
            ShowInfo.printLogW("_______onScroll_down_______" + lux/3);

        }
    }

    private void setLightUp() {
        int mLux = lux + 10;
        if (mLux < 300){
            lux = mLux;
            if (isWhite) {
                sendBulbColor(0, 0, 0, WhiteToLux(mLux), mLux, 50);
            } else {
                setColorLight(colorRed, colorGreen, colorBlue, 0, mLux, 50);
            }
            progress_bar.setProgress(lux/3);
            ShowInfo.printLogW("_______onScroll_up_______" + lux/3);
        }
    }

    private void setVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;

        TextView ver = (TextView) findViewById(R.id.tv_version);

        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            ver.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     *点击开关灯
     */
    private void doOnOffClick() {
        ShowInfo.printLogW("______doOnOffClick_______" + onoff);

        if(null == oneDev){
            return;
        }

        if (onoff) {
            sendBulbClose();
        } else {
            sendBulbOpen();
        }
    }

    private void addLightBtns(final LinearLayout linearLayout) {
        int count=0;
        final Drawable drawable = getResources().getDrawable(R.drawable.icon_check);

        for (OneDev dev : mDevices) {
            final Button mbtn = new Button(getApplicationContext());
            mbtn.setBackgroundColor(Color.TRANSPARENT);
            mbtn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            mbtn.setText(dev.devname);
            mbtn.setTextSize(22);
            mbtn.setId(count);
            mbtn.setTextColor(getResources().getColorStateList(R.drawable.selector_text_left));
            if(indexLight == count){
                mbtn.setCompoundDrawablePadding(10);
                mbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }

            mbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indexLight = v.getId();
                    linearLayout.removeAllViews();
                    addLightBtns(linearLayout);
                    setCurrentLight();
                }
            });

            linearLayout.addView(mbtn);
            count++;
        }

    }

    public void setCurrentLight(int index, boolean isFresh) {
        if(isFresh || index != indexLight){
            indexLight = index;
            setCurrentLight();
        }
    }

    private void setCurrentLight() {
        menu.resetMenu();
        getCurrentLight();
    }

    private void getCurrentLight() {
        oneDev = mDevices.get(indexLight);
        mPopBtn.setText(oneDev.devname);

        if(oneDev.function == null){
            return;
        }
        ShowInfo.printLogW("_____mDevice_______" + oneDev.devname);

        List<LightColor> lightColors = JsonUtil.stringToArray(oneDev.function, LightColor[].class);
        for (LightColor lightColor:lightColors){
            menu.addLightColor(lightColor);
        }
    }

    public List<OneDev> checkCurrentDev() {
        mDevices = mAllDevInfo.getAllDev();

        if (mDevices.size() > indexLight) {
            getCurrentLight();
        }

        return mDevices;
    }

    public void addLightColor(LightColor mcolor){
        if(null != mcolor && null != oneDev){
            menu.addLightColor(mcolor);
            List<LightColor> lightColors = new ArrayList<LightColor>(6);
            lightColors.addAll(JsonUtil.stringToArray(oneDev.function, LightColor[].class));
            lightColors.add(mcolor);
            oneDev.function = JsonUtil.objectToJson(lightColors);
            oneDev.updateFunctions(this);
        }
    }

    public void removeLightColor(int pos){
        List<LightColor> lightColors = new ArrayList<LightColor>(6);
        lightColors.addAll(JsonUtil.stringToArray(oneDev.function, LightColor[].class));
        lightColors.remove(pos);
        oneDev.function = JsonUtil.objectToJson(lightColors);
        oneDev.updateFunctions(this);
    }

    protected LightControlPacket getLightPacket() {
        LightControlPacket packet = new LightControlPacket(oneDev.getIp(), oneDev.getPort());
        packet.setPacketRemote(oneDev.getDevRemote());
        packet.setImportance(BasicPacket.ImportHigh);
        ShowInfo.printLogW(oneDev.getDevRemote() + "_______dev.getIp()_______" + oneDev.getIp().toString());
        return packet;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != autoBinder) {
            timerTask.cancel();
            timerTask = new CheckTask();
            timer.schedule(timerTask, 100, 2000);    //2秒查询一下设备状态
        }

        checkDeviceMenu();
        MobclickAgent.onResume(this);
    }

    private void checkDeviceMenu() {
        if(null == oneDev){
            menu.setVisibility(View.INVISIBLE);
        }else{
            menu.setVisibility(View.VISIBLE);
        }

        if(null != mDevices && mDevices.size()>1){
            mPopBtn.setVisibility(View.VISIBLE);
        }else{
            mPopBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != timerTask){
            timerTask.cancel();
        }

        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != timer){
            timer.cancel();
        }
        this.userUnbindService();
    }

    public SmartHomeService.AutoBinder autoBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            autoBinder = (SmartHomeService.AutoBinder) service;
//            autoBinder.getUdpCheckLine().addOneDev(oneDev);
            autoBinder.setDeviceLists(mDevices);
            timerTask = new CheckTask();
            timer.schedule(timerTask, 100, 2000);    //2秒查询一下设备状态
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            autoBinder = null;
        }

    };

    private void userBindService() {
        Intent bindIntent = new Intent(this, SmartHomeService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    private void userUnbindService() {
        unbindService(connection);
    }

    /**
     * 设置灯泡颜色亮度
     *
     * @param red   设置白色时，这个填0
     * @param green 设置白色时，这个填0
     * @param blue  设置白色时，这个填0
     * @param white 设置rgb彩色时，这个填0
     * @param lux   亮度值 0-255
     * @param time  渐变时长，设置大于0，避免颜色变化时感觉闪烁
     */
    public void sendBulbColor(int red, int green, int blue, int white, int lux, int time) {
        if(null == oneDev){
            return;
        }

        LightControlPacket packet = getLightPacket();
        packet.lightColor(oneDev, cycleListener, red, green, blue, white, lux, time);
        ShowInfo.printLogW("状态颜色 转后发出______：" + red + " " + green + " " + blue);
        autoBinder.addPacketToSend(packet);
    }

    public void sendBulbOpen() {
        LightControlPacket packet = getLightPacket();
        packet.lightOpen(oneDev, cycleListener);
        autoBinder.addPacketToSend(packet);
    }

    public void sendBulbClose() {
        LightControlPacket packet = getLightPacket();
        packet.lightClose(oneDev, cycleListener);
        autoBinder.addPacketToSend(packet);
    }

    public void setAnionLevel(byte level){
        LightControlPacket packet = getLightPacket();
        packet.setAnionLevel(DataExchange.longToEightByte(oneDev.mac), cycleListener, level);
        autoBinder.addPacketToSend(packet);
    }

    /**
     * 设置是否开启随机变色
     *
     * @param isRandom
     */
    public void sendBulbFree(boolean isRandom) {
        LightControlPacket packet = getLightPacket();
        packet.lightFree(oneDev, cycleListener, isRandom);
        autoBinder.addPacketToSend(packet);
    }

    public void sendResetPackage(OnRecvListener recvListener) {
        // 在这里组包发送配置包
        try {
            BasicPacket packet = new BasicPacket(InetAddress.getByName("255.255.255.255"), 5880);
            packet.setImportance(BasicPacket.ImportHigh);
            packet.setPacketRemote(false);
            packet.setListener(recvListener);

            packet.packReset(oneDev);

            autoBinder.addPacketToSend(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void doAddLightClick(View v) {
        addLightDialog = new AddLightDialog(this);
        addLightDialog.setDevices(mDevices, indexLight);
        addLightDialog.show();
    }

    public void doSettingClick(View v) {
        SettingDialog settingDialog = new SettingDialog(this);
        settingDialog.setDevice(oneDev);
        settingDialog.show();
    }

    public void doClickAboutUs(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void doCheckNewDevClick(View view) {
        Intent mIntent = new Intent(this, CheckNewDevActivity.class);
        mIntent.putExtra("count", mDevices.size());
        startActivityForResult(mIntent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode){
            ShowInfo.printLogW(requestCode + "_____onActivityResult_____" + resultCode);
            checkCurrentDev();
            checkDeviceMenu();
            if(null != addLightDialog && addLightDialog.isShowing()){
                addLightDialog.dismiss();
            }
        }
    }

    public void setColorLight(int white,int lux, int time) {
        if(0 == white){
            sendBulbColor(ColorToLux(colorRed, lux), (int)((float)ColorToLux(colorGreen, lux)*GreenAdjust)
                    , (int)((float)ColorToLux(colorBlue, lux)*BlueAdjust), white, lux, time);
        }else{
            sendBulbColor(0, 0, 0, white, lux, time);
        }
    }

    public void setColorLight(int r, int g, int b, int white,int lux, int time) {
        ShowInfo.printLogW("状态颜色 原始发出______：" + r + " " + g + " " + b);

        colorRed = ColorToLux(r, lux);
        colorGreen = (int)((float)ColorToLux(g, lux)*GreenAdjust);
        colorBlue = (int)((float)ColorToLux(b, lux)*BlueAdjust);

        sendBulbColor(colorRed, colorGreen, colorBlue, white, lux, time);
    }

    public int LuxToColor(int a, int lux){
        int tmp = 0;
        tmp = (int)((float)a*(float)300/(float)lux);
        return tmp;
    }

    public int ColorToLux(int a, int lux){
        int tmp = 0;
        tmp = (int)((float)a*(float)lux/(float)300);
        return tmp;
    }

    public int WhiteToLux(int a){
        int tmp = 0;
        tmp = (int)((float)255*(float)a/(float)300);
        return tmp;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean result = mGestureDetector.onTouchEvent(event);
        if (!result) {
            result = super.dispatchTouchEvent(event);
        }
        return result;
    }

    private static class MyHandler extends Handler {

        private final WeakReference<LightMainActivity> mActivity;

        public MyHandler(LightMainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LightMainActivity activity = mActivity.get();
            if(null == activity || activity.isFinishing()){
                return;
            }

            switch (msg.what){
                case MSG_DIS_LIGHT:
                    activity.dissMissLightProgress();
                break;
            }
        }
    }
}
