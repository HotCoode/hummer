package com.rise.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.L;
import com.base.orm.QueryHelper;
import com.rise.R;
import com.rise.bean.NotesItem;
import com.rise.bean.NotesItemOrder;
import com.rise.common.RiseUtil;
import com.rise.db.SQL;
import com.rise.db.SqlConst;

import java.util.List;

/**
 * Created by kai.wang on 2/14/14.
 */
public class NotesFragment extends Fragment implements BaseFragment {


	private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    container = (ViewGroup) inflater.inflate(R.layout.notes_fragment,null);
	    id = getArguments().getInt("id");
	    injectViews(container);
	    loadData();
	    return container;
    }

    @Override
    public void injectViews(View parentView) {
	    setBackground(parentView);
    }

	private void loadData(){
		QueryHelper.findBeans(
				NotesItem.class,
				SQL.FIND_NOTES_BY_TYPE,
				new String[]{RiseUtil.getTypeByName(id), SqlConst.NOTE_STATUS_AVAILABLE},
				new QueryHelper.FindBeansCallBack<NotesItem>() {
					@Override
					public void onFinish(List<NotesItem> beans) {
						List<NotesItemOrder> items =
					}
				}
		);
	}

	/**
	 * 设置页面载入时背景渐变效果
	 * @param parent
	 */
	private void setBackground(View parent){
		switch (id){
			case R.string.high_income_long_half_life:
				parent.setBackgroundResource(R.drawable.bg_notes_perfect);
				break;
			case R.string.low_income_long_half_life:
				parent.setBackgroundResource(R.drawable.bg_notes_uphold);
				break;
			case R.string.high_income_short_half_life:
				parent.setBackgroundResource(R.drawable.bg_notes_quick);
				break;
			case R.string.low_income_short_half_life:
				parent.setBackgroundResource(R.drawable.bg_notes_bad);
				break;
		}
		TransitionDrawable transition = (TransitionDrawable) parent.getBackground();
		transition.startTransition(2000);
	}
}
