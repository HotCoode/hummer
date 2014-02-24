package com.rise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.bean.Item;

import java.util.List;


/**
 * Created by kai.wang on 2/7/14.
 */
public class ManageItemAdapter extends BaseAdapter {
    private List<Item> things;

    private LayoutInflater inflater;

    public ManageItemAdapter(Context context, List<Item> things) {
        this.things = things;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return things.size();
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
            convertView = inflater.inflate(R.layout.list_item_manage_items,null);
        }
        ((TextView)convertView).setText(things.get(position).getContent());
        convertView.setTag(things.get(position).getId());
        return convertView;
    }
}
