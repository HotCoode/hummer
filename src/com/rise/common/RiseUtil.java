package com.rise.common;

import com.rise.R;
import com.rise.bean.NotesItem;
import com.rise.bean.NotesItemOrder;
import com.rise.db.SqlConst;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 2/15/14.<br/>
 * Function :
 */
public class RiseUtil {

	public static String getTypeByName(int name){
		String type = null;
		switch (name){
			case R.string.high_income_long_half_life:
				type = SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE;
				break;
			case R.string.low_income_long_half_life:
				type = SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE;
				break;
			case R.string.high_income_short_half_life:
				type = SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE;
				break;
			case R.string.low_income_short_half_life:
				type = SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE;
				break;
		}
		return type;
	}

	public List<NotesItemOrder> packageNoteItems(List<NotesItem> items){
		List<NotesItemOrder> result = new ArrayList<NotesItemOrder>();
		// todo 组装noteitem，按月分组
		for(NotesItem item : items){

		}
		return result;
	}

}
