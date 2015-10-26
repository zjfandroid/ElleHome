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
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.elle.pojo.LightColor;
import com.elle.view.AddLightDialog;
import com.elle.view.LightControlMenu;
import com.elle.view.RevealLayout;
import com.elle.view.SetLightColorDialog;
import com.elle.view.SettingDialog;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;

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

import static android.view.Gravity.START;

public class LightMainActivity extends Activity {

    public static boolean isFirstLoad = true;

    private DrawerArrowDrawable drawerArrowDrawable;
    private LightControlMenu menu;
    private ImageView imageLightAnim;
    private DrawerLayout drawer;
    private RevealLayout revealLayout;
    private PopupWindow popupWindow;
    private Button mPopBtn;

    private SetLightColorDialog setLightColorDialog;
    private AddLightDialog addLightDialog;

    private float offset;
    private boolean flipped;
    private boolean onoff, random;

    private AllDevInfo mAllDevInfo;
    private List<OneDev> mDevices;
    private OneDev oneDev;

    private OnBulbChangeListener mOnBulbChangeListener;
    int mred, mgreen, mblue, lux, time, sleepTime;
    private int indexLight;
    private int colorRed;
    private int colorGreen;
    private int colorBlue;
    private int white;
    private final float BlueAdjust = 0.75f;
    private final float GreenAdjust = 0.8f;

    //定时查询回调接口
    private OnRecvListener cycleListener = new OnRecvListener() {
        @Override
        public void OnRecvData(PacketCheck packetcheck) {
            if (packetcheck != null && null != mOnBulbChangeListener) {
                int red, green, blue;
                /*********灯的开关状态*********/
                if (packetcheck.xdata[0] == 0x00) {
                    onoff = false;    //关闭状态
                    mOnBulbChangeListener.onBulbClose();
                } else {
                    onoff = true;    //打开状态
                    mOnBulbChangeListener.onBulbOpen();
                }
                menu.setLightOn(onoff);
                /*********灯的随机变色状态**********/
                if (packetcheck.xdata[1] == 0x00) {
                    random = false;    //关闭状态
                } else {
                    random = true;    //打开状态
                }
                mOnBulbChangeListener.onBulbFree(random);

                /***************亮度***************/
                lux = DataExchange.twoByteToInt(packetcheck.xdata[12], packetcheck.xdata[13]);
                /*************颜色值***************/
                //white
                if(DataExchange.twoByteToInt(packetcheck.xdata[8], packetcheck.xdata[9])>0){
                    //灯泡状态为白色
                }else{
                    ShowInfo.printLogW("状态颜色 原始接收______：" + DataExchange.twoByteToInt(packetcheck.xdata[2], packetcheck.xdata[3])
                            + " " + DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5])
                            + " " + DataExchange.twoByteToInt(packetcheck.xdata[6], packetcheck.xdata[7]));

                    colorRed = LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[2], packetcheck.xdata[3]), lux);
                    colorGreen = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[4], packetcheck.xdata[5]), lux)/GreenAdjust);
                    colorBlue = (int)((float)LuxToColor(DataExchange.twoByteToInt(packetcheck.xdata[6], packetcheck.xdata[7]), lux)/BlueAdjust);

                    ShowInfo.printLogW("状态颜色 接收转后______：" + colorRed + " " + colorGreen + " " + colorBlue);
                }
                /***************睡眠时间**********/
                sleepTime = DataExchange.twoByteToInt(packetcheck.xdata[14], packetcheck.xdata[15]);

                mOnBulbChangeListener.onColorChange(colorRed, colorGreen, colorBlue, white, lux);

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
            }
        }
    };

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
        revealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        imageLightAnim = (ImageView) findViewById(R.id.img_light_anim);
        imageLightAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == oneDev){
                    return;
                }

                if (onoff) {
                    sendBulbClose();
                } else {
                    sendBulbOpen();
                }
            }
        });

        final Resources resources = getResources();

        findViewById(R.id.drawer_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.light_black));
        imageView.setImageDrawable(drawerArrowDrawable);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(START)) {
                    drawer.closeDrawer(START);
                } else {
                    drawer.openDrawer(START);
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

//    styleButton.setOnClickListener(new View.OnClickListener() {
//      boolean rounded = false;
//
//      @Override public void onClick(View v) {
//        styleButton.setText(rounded //
//            ? resources.getString(R.string.rounded) //
//            : resources.getString(R.string.squared));
//
//        rounded = !rounded;
//
//        drawerArrowDrawable = new DrawerArrowDrawable(resources, rounded);
//        drawerArrowDrawable.setParameter(offset);
//        drawerArrowDrawable.setFlip(flipped);
//        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.light_gray));
//
//        imageView.setImageDrawable(drawerArrowDrawable);
//      }
//    });

        mOnBulbChangeListener = new OnBulbChangeListener() {
            @Override
            public void onColorChange(int red, int green, int blue, int white, int lux) {

            }

            @Override
            public void onBulbOpen() {

            }

            @Override
            public void onBulbClose() {

            }

            @Override
            public void onBulbFree(boolean isFree) {

            }
        };

        menu = (LightControlMenu) findViewById(R.id.layout_bottom_menu);
        menu.setBulbControlListener(new LightControlMenu.OnBulbControlListener() {
            @Override
            public void onOffClick() {
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

            @Override
            public void randomClick() {
                ShowInfo.printLogW("______doRandomClick_______" + random);
                if(null == oneDev){
                    return;
                }
                sendBulbFree(!random);
            }

            @Override
            public void onAddClick() {
                if(null == setLightColorDialog){
                    setLightColorDialog = new SetLightColorDialog(LightMainActivity.this);
                }
                setLightColorDialog.show();
            }

            @Override
            public void onColorClick(int pos) {
                List<LightColor> lightColors = JsonUtil.stringToArray(oneDev.function, LightColor[].class);
                int index = pos-2;
                if(null != lightColors && index<lightColors.size()){
                    LightColor lightColor = lightColors.get(index);
                    int pixelColor = lightColor.getColor();
                    int lux = lightColor.getLight();

                    if(pixelColor == Color.WHITE){
                        sendBulbColor(0, 0, 0,
                                WhiteToLux(lux), lux, 800);
                    }else{
                        setColorLight(Color.red(pixelColor), Color.green(pixelColor), Color.blue(pixelColor),
                                0, lux, 800);
                    }
                }
            }

            @Override
            public void onAnionChange(int value) {
                switch (value){
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
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

            @Override
            public void onClick(int arg0) {
                switch (arg0) {
                    case UpdateStatus.Update:
                        break;
                    case UpdateStatus.Ignore:
                        break;
                    case UpdateStatus.NotNow:
                        break;
                }
            }
        });
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
            mPopBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerTask.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
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

    public void setOnBulbChangeListener(OnBulbChangeListener mListener) {
        this.mOnBulbChangeListener = mListener;
    }

    public void doAddLightClick(View v) {
//        Intent intent = new Intent(this, AddLightActivity.class);
//        startActivity(intent);
        addLightDialog = new AddLightDialog(this);
        addLightDialog.setDevices(mDevices, indexLight);
        addLightDialog.show();
    }

    public void doSettingClick(View v) {
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
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

//        colorRed = r;
//        colorGreen = g;
//        colorBlue = b;

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

    public interface OnBulbChangeListener {
        void onColorChange(int red, int green, int blue, int white, int lux);

        void onBulbOpen();

        void onBulbClose();

        /**
         * 随机变色是否打开
         *
         * @param isFree
         */
        void onBulbFree(boolean isFree);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (drawer.isDrawerVisible(START)) {
            drawer.closeDrawer(START);
        } else {
            drawer.openDrawer(START);
        }
        return super.onMenuOpened(featureId, menu);
    }
}
