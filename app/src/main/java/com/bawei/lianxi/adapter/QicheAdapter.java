package com.bawei.lianxi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bawei.lianxi.R;
import com.bawei.lianxi.bean.QicheBean;

import java.util.List;

/**
 * 类用途：
 * 作者：史壮壮
 * 时间：2017/4/7 20:41
 */

public class QicheAdapter extends BaseAdapter {
    private Context context;
    private List<QicheBean.ListBean> list;
    private ViewHolder mHolder;

    public QicheAdapter(Context context, List<QicheBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.layout_list, null);
            mHolder.address = (TextView) convertView.findViewById(R.id.list_address);
            mHolder.site_name = (TextView) convertView.findViewById(R.id.list_site_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.site_name.setText("地点名：" + list.get(position).getSite_name());
        mHolder.address.setText("地址：" + list.get(position).getAddress());
        return convertView;
    }

    class ViewHolder {
        TextView site_name;
        TextView address;
    }
}
