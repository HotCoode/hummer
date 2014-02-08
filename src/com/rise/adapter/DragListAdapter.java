package com.rise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.component.DragView;


/**
 * Created by kai.wang on 2/7/14.
 */
public class DragListAdapter extends BaseAdapter {
    private String[] things;

    private LayoutInflater inflater;

    public DragListAdapter(Context context, String[] things) {
        this.things = things;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return things.length;
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
            convertView = inflater.inflate(R.layout.drag_view_item,null);
        }
        ((DragView)convertView).setText(things[position]);
        convertView.setTag(position);
        return convertView;
    }
}
