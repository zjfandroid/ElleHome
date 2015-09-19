package chrisrenke.elle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elle.pojo.LightColor;

import java.util.ArrayList;
import java.util.List;

import elle.home.airkiss.AirKiss;
import elle.home.database.OneDev;
import elle.home.hal.WifiAdmin;
import elle.home.hal.sdk.CheckNewDevice;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.JsonUtil;
import elle.home.utils.SaveDataPreferences;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

/**
 * Created by jason on 15/8/21.
 */
public class CheckNewDevActivity extends Activity {

    public static final int STATE_CHANGE_WIFI = 0;
    public static final int STATE_CONNECT_SUCCESS = 1;

    private int state = STATE_CHANGE_WIFI;

    WifiAdmin wifiadmin;
    Context mContext;

    //现在连接的wifi的名称
    String conssid = "701";

    //属于elle.home的可配置设备
    List<ConfigDev> configDevDataList = new ArrayList<ConfigDev>();

    //查询新设备的线程
    CheckNewDevice udpchecknew;
    //dev type
    private int type;
    private int count;

    private AirKiss airKiss;

    private int counter = 60;
    private CountDownTimer mCountDownTimer;
    private Button buttonBottom;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:    //刷新添加设备界面
                    foundDevice();
                    final List<OneDev> devs = udpchecknew.newdevs;
                    break;
                case 1:
                    break;
                case 2:    //提示对话框
                    Toast.makeText(mContext, msg.getData().getString("tips"), Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_dev);
        this.mContext = this;

        Intent intent = getIntent();
        type = intent.getIntExtra("type", PublicDefine.TypeLight);
        count = intent.getIntExtra("count", 1);

        wifiadmin = new WifiAdmin(this);
        initViews();

        if (PublicDefine.isWiFiConnect(this)) {
            conssid = wifiadmin.getWifiInfo().getSSID();
            if (conssid.contains("\"")) {
                conssid = conssid.substring(1, conssid.length() - 1);
            }
            startAirkiss();
        } else {
            conssid = "none";
        }

        startUdpCheckNewDevice();
    }

    private void startUdpCheckNewDevice() {
        udpchecknew = new CheckNewDevice(this, new CheckNewDevice.OnDeviceListener() {

            @Override
            public void onDeviceFound(List<OneDev> newdevs) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

                ShowInfo.printLogW("_______OnDeviceListener_____" + newdevs.size());
            }

            @Override
            public void onOneDeviceFound(final OneDev newdev) {

                if (newdev.type == PublicDefine.TypeLight) {
                    newdev.devname = "小兔灯"+ (count+1) +"号";
                    count++;

                    ArrayList<LightColor> mColors = new ArrayList<LightColor>(6);
                    mColors.add(new LightColor("暖白灯", Color.parseColor("#fffefe"), 200));
                    mColors.add(new LightColor("魅蓝灯", Color.parseColor("#332288"), 180));
                    mColors.add(new LightColor("小夜灯", Color.parseColor("#ff9933"), 150));
                    newdev.function = JsonUtil.objectToJson(mColors);

                    if(newdev.addToDatabase(mContext)){
                        setResult(RESULT_OK);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                connectSuccess();
                                ShowToast.show(mContext, "成功添加一个设备:" + newdev.devname);
                            }
                        });
                    }
                } else if (newdev.type == PublicDefine.TypePlug) {
                    newdev.devname = "智能插座" + count;
                } else{
                    newdev.devname = "未知设备-" + newdev.type;
                }
            }

//			public void onnewdev() {
//				//刷新界面
//				Message msg = new Message();
//				msg.what = 0;
//				handler.sendMessage(msg);
//			}
        });

        udpchecknew.startCheck();
    }

    private void startAirkiss() {
        String password = SaveDataPreferences.load(mContext, conssid, "-1");
        ShowInfo.printLogW(conssid + "_______________" + password);
        if(password.equals("-1")){
            showConfigWifiDialog(conssid);
            return;
        }

        airKiss = new AirKiss(conssid, password);
        airKiss.start();

//		StringBuilder sb = new StringBuilder(conssid);
//		sb.append("\r\n").append(password);
//		wifi.setText(sb);
//
//		wifi.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showConfigWifiDialog(conssid);
//			}
//		});
    }

    protected void showConfigWifiDialog(String ssid) {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.setCanceledOnTouchOutside(true);
		dlg.show();

		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_config_aks_wifi);
		//弹出输入法
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		final EditText editPass = (EditText) window.findViewById(R.id.passwordEt);
		final EditText editText = (EditText) window.findViewById(R.id.ssidEt);
		editText.setText(ssid);

		Button ok = (Button) window.findViewById(R.id.configbtn);
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				conssid = editText.getText().toString();
				SaveDataPreferences.save(mContext, conssid, editPass.getText().toString());
				stopAirkiss();
				startAirkiss();
				dlg.dismiss();
			}

		});
    }

    private void initViews() {
        initProgress();

        buttonBottom = (Button) findViewById(R.id.btn_sure);
        buttonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case STATE_CHANGE_WIFI:
                        if (null != configDevDataList) {
                            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                            startActivity(intent);
                        }
                        break;

                    case STATE_CONNECT_SUCCESS:
                        finish();
                        break;
                }
            }
        });
    }

    private void initProgress() {
        mCountDownTimer = new CountDownTimer(counter * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                connectSuccess();
            }
        }.start();
    }

    private void connectSuccess() {
        state = STATE_CONNECT_SUCCESS;
        buttonBottom.setText("配对成功");
    }

    private void stopAirkiss() {
        if (null != airKiss) {
            airKiss.stop();
            airKiss = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        udpchecknew.startCheck();
    }

    @Override
    protected void onStop() {
        super.onStop();
        udpchecknew.stopCheck();
    }

    @Override
    protected void onDestroy() {
        this.setResult(1);
        stopAirkiss();
        super.onDestroy();
    }

    private void foundDevice() {
        stopAirkiss();
    }

    public class ConfigDev {
        //Wifi设备
        ScanResult result;
        String name;

        public ConfigDev(ScanResult result) {
            this.result = result;
            this.name = result.SSID;
        }
    }
}
