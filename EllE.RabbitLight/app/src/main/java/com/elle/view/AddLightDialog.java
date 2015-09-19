package com.elle.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import chrisrenke.elle.LightMainActivity;
import chrisrenke.elle.R;
import elle.home.database.OneDev;
import elle.home.publicfun.PublicDefine;
import elle.home.utils.ViewHolder;

public class AddLightDialog extends Dialog {

    private ListView mListView;
    private List<OneDev> mDevices;

    private Context mContext;
    private View mRoot;

    private int mIndex;

    public AddLightDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public AddLightDialog(Context context, int theme) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
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
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mIndex = position;
                ((LightMainActivity)mContext).setCurrentLight(position);
            }
        });

        return v;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRoot == null) {
            mRoot = getView();
            setContentView(mRoot);
        }
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
            if(PublicDefine.ConnectNull == item.getConnectStatus()){
                icon.setImageResource(R.drawable.statue_disconnect);
            }else{
                icon.setImageResource(R.drawable.statue_connect);
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

    public void setDevices(List<OneDev> devs, int index){
        mDevices = devs;
        mIndex = index;
    }
}
