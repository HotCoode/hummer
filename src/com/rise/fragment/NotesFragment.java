package com.rise.fragment;

import android.app.AlertDialog;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.base.orm.QueryHelper;
import com.rise.R;
import com.rise.adapter.NotesItemAdapter;
import com.rise.bean.NotesItem;
import com.rise.bean.NotesItemOrder;
import com.rise.common.RiseUtil;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import net.simonvt.messagebar.MessageBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai.wang on 2/14/14.
 */
public class NotesFragment extends Fragment implements BaseFragment,ListView.OnItemLongClickListener,MessageBar.OnMessageClickListener {


	private int id;

	private ListView listView;
	private NotesItemAdapter adapter;
	private List<NotesItemOrder> items = new ArrayList<NotesItemOrder>();

	private ViewGroup containerView;

	private final int DATA_LOAD_FINISH = 100;

    private MessageBar messageBar;

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
	    container = (ViewGroup) inflater.inflate(R.layout.notes_fragment,null);
	    containerView = container;
	    id = getArguments().getInt("id");

	    injectViews(container);
	    loadData();
        setBackground();
	    return container;
    }

    @Override
    public void injectViews(View parentView) {
        messageBar = new MessageBar(getActivity());
        messageBar.setOnClickListener(this);

        adapter = new NotesItemAdapter(getActivity(),items,id);
	    listView = (ListView) parentView.findViewById(R.id.notes_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(this);
    }

	private void loadData(){
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
		);
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        messageBar.clear();
        Bundle b = new Bundle();
        b.putLong("id", items.get(position).getItem().getId());
        messageBar.show(items.get(position).getItem().getContent(), getResources().getString(R.string.delete),b);
        return false;
    }

    @Override
    public void onMessageClick(Parcelable token) {
        messageBar.clear();
        Bundle b = (Bundle) token;
        final long position = b.getLong("position");
        QueryHelper.update(SQL.DELETE_NOTE_BY_ID,new String[]{position + ""},new QueryHelper.UpdateCallBack() {
            @Override
            public void onFinish() {
                items.clear();
                loadData();
            }
        });
        Toast.makeText(getActivity(),R.string.delete_success,Toast.LENGTH_SHORT).show();
    }
}
