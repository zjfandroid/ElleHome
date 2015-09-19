package com.elle.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import chrisrenke.elle.ConfigWifiActivity;
import chrisrenke.elle.LightMainActivity;
import chrisrenke.elle.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.database.OneDev;
import elle.home.protocol.OnRecvListener;
import elle.home.protocol.PacketCheck;
import elle.home.utils.ShowInfo;
import elle.home.utils.ShowToast;

public class SettingDialog extends Dialog {

    private OneDev mDevice;

    private Context mContext;
    private View mRoot;

    public SettingDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public SettingDialog(Context context, int theme) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
    }

    private View getView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_setting,
                null);

        v.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == mDevice){
                    ShowToast.show(mContext, "当前无可配置设备哦~");
                    return;
                }

                Intent intent = new Intent(mContext, ConfigWifiActivity.class);
                intent.putExtra("dev", mDevice);
                mContext.startActivity(intent);
            }
        });

        v.findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mDevice) {
                    ShowToast.show(mContext, R.string.reset_fail_null);
                    return;
                }

                SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialog.setTitleText(mContext.getResources().getString(R.string.sure_to_reset))
                        .setCancelText(mContext.getResources().getString(R.string.manage_dev_tips_del_dev_no))
                        .setConfirmText(mContext.getResources().getString(R.string.config_reset))
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
//					sDialog.setConfirmText("OK")
//					.showCancelButton(false)
//					.setCancelClickListener(null)
//					.setConfirmClickListener(null)
//					.setTitleText("复位成功")
//					.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                ((LightMainActivity) mContext).sendResetPackage(recvListener);
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        v.findViewById(R.id.btn_upgrate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForUpdate();
            }
        });

        return v;
    }

    private void checkForUpdate() {
        UmengUpdateAgent.forceUpdate(mContext);
        UmengUpdateAgent.setUpdateAutoPopup(false);
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

        final SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(mContext.getString(R.string.checking));
        pDialog.show();
        pDialog.setCancelable(false);

        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

            @Override
            public void onUpdateReturned(int arg0, UpdateResponse arg1) {
                switch (arg0) {
                    case UpdateStatus.Yes: // has update
                        pDialog.dismiss();
                        UmengUpdateAgent.showUpdateDialog(mContext, arg1);
                        break;
                    case UpdateStatus.No: // has no update
                        pDialog.setTitleText(mContext.getString(R.string.update_no))
                                .setConfirmText(mContext.getString(R.string.dialog_ok))
                                .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        pDialog.setTitleText(mContext.getString(R.string.update_with_wifi))
                                .setConfirmText(mContext.getString(R.string.dialog_ok))
                                .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                        break;
                    case UpdateStatus.Timeout: // time out
                        pDialog.setTitleText(mContext.getString(R.string.pppp_status_connect_timeout))
                                .setConfirmText(mContext.getString(R.string.dialog_ok))
                                .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                        break;
                }
            }
        });
    }

    OnRecvListener recvListener = new OnRecvListener(){
        @Override
        public void OnRecvData(PacketCheck packetcheck) {
            if(packetcheck!=null){
                ShowInfo.printLogW("_________OnRecvData_________");
                mRoot.post(new Runnable() {

                    @Override
                    public void run() {
                        mDevice.delFromDatabaseWithName(mContext);
                        ShowToast.show(mContext, R.string.reset_success);
                        dismiss();
                    }
                });
            }else{
                ShowInfo.printLogW("_________OnRecvData__null_______");
//				if(dev.getConnectStatus() != PublicDefine.ConnectLocal){
                mRoot.post(new Runnable() {

                    @Override
                    public void run() {
                        ShowToast.show(mContext, R.string.reset_fail);
                    }
                });
//				}
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        if (mRoot == null) {
            mRoot = getView();
            setContentView(mRoot);
        }
    }

    public void setDevice(OneDev dev){
        mDevice = dev;
    }
}
