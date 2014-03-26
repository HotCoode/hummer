package com.rise.fragment;

import com.rise.R;

/**
 * Created by kai on 2/15/14.<br/>
 */
public class FragmentUtil {
	private static int currentFragment = R.string.events;


	public static int getCurrentFragment(){
		return currentFragment;
	}

	public static void setCurrentFragment(int currentFragment){
		FragmentUtil.currentFragment = currentFragment;
	}
}
