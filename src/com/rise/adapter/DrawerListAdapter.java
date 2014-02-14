package com.rise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.view.DragView;


/**
 * Created by kai.wang on 2/7/14.
 */
public class DrawerListAdapter extends BaseAdapter {
    private String[] items;

    private LayoutInflater inflater;

    public DrawerListAdapter(Context context, String[] items) {
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.drawer_list_item,null);
        }
        ((TextView)convertView).setText(items[position]);
        convertView.setTag(position);
        return convertView;
    }
}