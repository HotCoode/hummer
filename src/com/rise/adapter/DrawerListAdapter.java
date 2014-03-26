package com.rise.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.fragment.FragmentUtil;


/**
 * Created by kai.wang on 2/7/14.
 */
public class DrawerListAdapter extends BaseAdapter {
    private int[] items;

    private LayoutInflater inflater;
    private Context context;

    public DrawerListAdapter(Context context, int[] items) {
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_drawer_layout,null);
        }
        ((TextView)convertView).setText(Html.fromHtml(context.getResources().getString(items[position])));
        convertView.setTag(items[position]);
	    if(FragmentUtil.getCurrentFragment() == items[position]){
		    convertView.setBackgroundResource(R.drawable.bg_list_item_focus);
	    }else{
		    convertView.setBackgroundResource(R.drawable.bg_pressable_normal);
	    }
        return convertView;
    }
}
