package com.elle.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.haarman.listviewanimations.itemmanipulation.SwipeDismissAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import chrisrenke.elle.LightMainActivity;
import chrisrenke.elle.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ShowInfo;
import elle.home.utils.ViewHolder;

public class AddLightDialog extends Dialog implements OnDismissCallback{

    private ListView mListView;
    private List<OneDev> mDevices;

    private Context mContext;
    private View mRoot;

    private int mIndex;
    private Timer mTimer;

    private ProgressBar progressBar;

    public AddLightDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public AddLightDialog(Context context, int theme) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
    }

    protected void showRenameDialog(final int position) {
        final AlertDialog dlg = new AlertDialog.Builder(mContext, R.style.Dialog_Fullscreen).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.show();

        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_rename);
        //弹出输入法
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final EditText editText = (EditText) window.findViewById(R.id.edt_rename);
        window.findViewById(R.id.title_btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        Button ok = (Button) window.findViewById(R.id.btn_done);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (null == editText || editText.getText().toString().isEmpty()) {
                    dlg.dismiss();
                    return;
                }

                OneDev dev = mDevices.get(position);
                dev.updateDeviceName(mContext, editText.getText().toString());
                setDevices(((LightMainActivity)mContext).checkCurrentDev(), mIndex);
                mAdapter.notifyDataSetChanged();
                dlg.dismiss();
            }

        });

    }

    private View getView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_add_light,
                null);

        v.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mListView = (ListView) v.findViewById(R.id.list_view);
        setDismissAdapter();
//        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mIndex = position;
                mAdapter.notifyDataSetChanged();
                ((LightMainActivity) mContext).setCurrentLight(position, false);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showRenameDialog(position);
                return true;
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 800);

        return v;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRoot == null) {
            mRoot = getView();
            setContentView(mRoot);
            startTimer();
            ShowInfo.printLogW("_____dialog____onStart___");
        }

    }

    private void startTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 300, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(null != mTimer){
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
        ShowInfo.printLogW("_____dialog____onStop___");
    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_list, null);
            }

            OneDev item = mDevices.get(position);

            TextView name = ViewHolder.get(convertView, R.id.tv_title);
            name.setText(item.devname);
            if(position == mIndex){
                name.setCompoundDrawablesWithIntrinsicBounds(null,null,mContext.getResources().getDrawable(R.drawable.icon_check),null);
            }else{
                name.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
            }

            ImageView icon = ViewHolder.get(convertView, R.id.img_icon);
//            ShowInfo.printLogW("__onRecv onRecv__getConnectStatus" + item.getConnectStatus());
            if(PublicDefine.ConnectNull == item.getConnectStatus()){
                icon.setImageResource(R.drawable.statue_disconnect);
            }else{
                if(LightMainActivity.isFirstLoad){
                    convertView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doFirstLoad();
                        }

                    }, 50);
                }else{
                    icon.setImageResource(R.drawable.statue_connect);
                }
            }

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            if (null == mDevices || mDevices.size() <= 0) {
                return null;
            }

            return mDevices.get(position);
        }

        @Override
        public int getCount() {
            if (null == mDevices) {
                return 0;
            }

            return mDevices.size();
        }
    };

    private void doFirstLoad() {
        LightMainActivity.isFirstLoad = false;

        List<OneDev> devs = new ArrayList<OneDev>();
        for (OneDev oneDev : mDevices){
            if(PublicDefine.ConnectNull == oneDev.getConnectStatus()){
                devs.add(oneDev);
            }else{
                devs.add(0, oneDev);
            }
        }

        mDevices.clear();
        mDevices.addAll(devs);

        mIndex = 0;
        ((LightMainActivity) mContext).setCurrentLight(0, true);

        progressBar.setVisibility(View.GONE);
    }

    public void setDevices(List<OneDev> devs, int index){
        mDevices = devs;
        mIndex = index;
    }

    @Override
    public void onDismiss(AbsListView absListView, final int[] ints) {
        SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setTitleText(mContext.getResources().getString(R.string.sure_to_del_dev))
                .setCancelText(mContext.getResources().getString(R.string.dialog_cancel))
                .setConfirmText(mContext.getResources().getString(R.string.dialog_ok))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        setDismissAdapter();
                        sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        deleteDevice(ints);
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    private void setDismissAdapter() {
        SwipeDismissAdapter mSwipeDismissAdapter = new SwipeDismissAdapter(mAdapter, AddLightDialog.this);
        mSwipeDismissAdapter.setAbsListView(mListView);
        mListView.setAdapter(mSwipeDismissAdapter);
    }

    public void deleteDevice(int[] ints){
        for(int index : ints){
            if (index >= 0 && index < mDevices.size()){
                mDevices.get(index).delFromDatabase(mContext);
                mDevices.remove(index);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
