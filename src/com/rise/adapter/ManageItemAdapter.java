package com.rise.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.bean.Item;

import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.PSource;


/**
 * Created by kai.wang on 2/7/14.
 */
public class ManageItemAdapter extends BaseAdapter {
    private List<Item> things;

    private LayoutInflater inflater;

    private SparseBooleanArray selection = new SparseBooleanArray();

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

    public void selection(int position){
        selection.put(position,true);
        notifyDataSetChanged();
    }

    public void removeSelection(int position){
        selection.delete(position);
        notifyDataSetChanged();
    }

    public void clearSelection(){
        selection.clear();
        notifyDataSetChanged();
    }

    public boolean isSelection(int position){
        return selection.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_manage_items,null);
        }
        convertView.setBackgroundResource(R.drawable.bg_pressable_darker);
        if(selection.get(position)){
            convertView.setBackgroundResource(R.color.theme_darker);
        }
        ((TextView)convertView).setText(things.get(position).getContent());
        convertView.setTag(things.get(position).getId());
        return convertView;
    }
}
