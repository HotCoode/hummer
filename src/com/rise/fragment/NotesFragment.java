package com.rise.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.base.L;
import com.base.common.DateUtils;
import com.base.orm.QueryHelper;
import com.rise.NewNoteActivity;
import com.rise.R;
import com.rise.adapter.NotesItemAdapter;
import com.rise.bean.NotesItem;
import com.rise.bean.NotesItemOrder;
import com.rise.common.RiseUtil;
import com.rise.component.SimpleDialog;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.wang on 2/14/14.
 */
public class NotesFragment extends Fragment implements BaseFragment,ListView.OnItemLongClickListener {


	private int id;

	private ListView listView;
	private NotesItemAdapter adapter;
	private List<NotesItemOrder> items = new ArrayList<NotesItemOrder>();

	private ViewGroup containerView;

	private final int DATA_LOAD_FINISH = 100;

    private View indicatorView;

	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what == DATA_LOAD_FINISH){
				adapter.notifyDataSetChanged();
			}
			return false;
		}
	});

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
	    container = (ViewGroup) inflater.inflate(R.layout.fragment_notes,null);
	    containerView = container;
	    id = getArguments().getInt("id");

	    injectViews(container);
//        setBackground();

	    return container;
    }

    @Override
    public void injectViews(View parentView) {

        adapter = new NotesItemAdapter(getActivity(),items);
	    listView = (ListView) parentView.findViewById(R.id.notes_list_view);
        indicatorView = parentView.findViewById(R.id.indicator);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);

        indicatorView.setBackgroundResource(RiseUtil.getColorByName(id));

    }

	private void loadData(){
        items.clear();
		QueryHelper.findBeans(
				NotesItem.class,
				SQL.FIND_NOTES_BY_TYPE,
				new String[]{RiseUtil.getTypeByName(id), SqlConst.NOTE_STATUS_AVAILABLE},
				new QueryHelper.FindBeansCallBack<NotesItem>() {
					@Override
					public void onFinish(List<NotesItem> beans) {
                        items.addAll(RiseUtil.packageNoteItems(beans));
						handler.sendEmptyMessage(DATA_LOAD_FINISH);
					}
				}
		,300);
	}

	/**
	 * 设置页面载入时背景渐变效果
	 */
	private void setBackground(){
		switch (id){
			case R.string.high_income_long_half_life:
				containerView.setBackgroundResource(R.drawable.bg_notes_perfect);
				break;
			case R.string.low_income_long_half_life:
				containerView.setBackgroundResource(R.drawable.bg_notes_uphold);
				break;
			case R.string.high_income_short_half_life:
				containerView.setBackgroundResource(R.drawable.bg_notes_quick);
				break;
			case R.string.low_income_short_half_life:
				containerView.setBackgroundResource(R.drawable.bg_notes_bad);
				break;
		}
		TransitionDrawable transition = (TransitionDrawable) containerView.getBackground();
        transition.setCrossFadeEnabled(true);
		transition.startTransition(2000);
	}

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final long noteId = items.get(position).getItem().getId();
        String content = items.get(position).getItem().getContent();

        if(DateUtils.isToday(items.get(position).getItem().getTime())){
            final SimpleDialog dialog = new SimpleDialog(getActivity(),content,getString(R.string.delete_event));
            dialog.setOnClickListener(new SimpleDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    QueryHelper.update(SQL.DELETE_NOTE_BY_ID,new String[]{noteId + ""},new QueryHelper.UpdateCallBack() {
                        @Override
                        public void onFinish() {
                            items.clear();
                            loadData();
                        }
                    });
                    Toast.makeText(getActivity(),R.string.delete_success,Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(),R.string.only_can_delete_today_event,Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_note,menu);
        menu.findItem(R.id.menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(getActivity(),NewNoteActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

}
