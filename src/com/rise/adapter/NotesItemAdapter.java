package com.rise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rise.R;
import com.rise.bean.NotesItemOrder;
import com.rise.common.RiseUtil;

import java.util.List;

/**
 * Created by kai on 2/15/14.<br/>
 * Function :
 */
public class NotesItemAdapter extends BaseAdapter {
	private Context context;
	private List<NotesItemOrder> items;
	private LayoutInflater inflater;
	public NotesItemAdapter(Context context,List<NotesItemOrder> items){
		this.context = context;
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
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
		NotesItemOrder note = items.get(position);
		if(note.getType() == NotesItemOrder.TYPE_MONTH){
			convertView = inflater.inflate(R.layout.notes_item_month,null);
			((TextView)convertView).setText(note.getMonth() + " " + note.getYear());
		}else{
			convertView = inflater.inflate(R.layout.notes_item_content,null);
			TextView monthView = (TextView) convertView.findViewById(R.id.notes_item_content_month);
			TextView dayView = (TextView) convertView.findViewById(R.id.notes_item_content_day);
			TextView textView = (TextView) convertView.findViewById(R.id.notes_item_content_text);
			monthView.setText(note.getItem().getMonth());
			dayView.setText(note.getItem().getDay());
			textView.setText(note.getItem().getContent());
		}
		return convertView;
	}
}
