package com.rise.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.L;
import com.rise.R;

/**
 * Created by kai.wang on 2/14/14.
 */
public class NotesFragment extends Fragment implements BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    container = (ViewGroup) inflater.inflate(R.layout.notes_fragment,null);
	    injectViews(container);
	    return container;
    }

    @Override
    public void injectViews(View parentView) {
	    setBackground(parentView);
    }

	private void setBackground(View parent){
		switch (getArguments().getInt("id")){
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
