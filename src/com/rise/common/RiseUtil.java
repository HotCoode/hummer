package com.rise.common;

import android.content.Context;
import android.widget.Toast;

import com.base.common.DateUtils;
import com.rise.R;
import com.rise.bean.NotesItem;
import com.rise.bean.NotesItemOrder;
import com.rise.bean.Time;
import com.rise.db.SqlConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kai on 2/15/14.<br/>
 * Function :
 */
public class RiseUtil {

    public static String getTypeByName(int name) {
        String type = null;
        switch (name) {
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

    public static int getColorByName(int name) {
        int color = 0;
        switch (name) {
            case R.string.high_income_long_half_life:
                color = R.color.perfect;
                break;
            case R.string.low_income_long_half_life:
                color = R.color.uphold;
                break;
            case R.string.high_income_short_half_life:
                color = R.color.quick;
                break;
            case R.string.low_income_short_half_life:
                color = R.color.bad;
                break;
        }
        return color;
    }

    public static int getColorByType(String type) {
        int color = 0;
        if (SqlConst.NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE.equals(type)) {
            color = R.color.perfect;
        } else if (SqlConst.NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE.equals(type)) {
            color = R.color.uphold;
        } else if (SqlConst.NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE.equals(type)) {
            color = R.color.quick;
        } else if (SqlConst.NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE.equals(type)) {
            color = R.color.bad;
        }
        return color;
    }

    public static int getRealName(int colorName) {
        int name = colorName;
        switch (colorName) {
            case R.string.high_income_long_half_life_color:
                name = R.string.high_income_long_half_life;
                break;
            case R.string.low_income_long_half_life_color:
                name = R.string.low_income_long_half_life;
                break;
            case R.string.high_income_short_half_life_color:
                name = R.string.high_income_short_half_life;
                break;
            case R.string.low_income_short_half_life_color:
                name = R.string.low_income_short_half_life;
                break;
        }
        return name;
    }

    public static List<NotesItemOrder> packageNoteItems(List<NotesItem> items) {
        List<NotesItemOrder> result = new ArrayList<NotesItemOrder>();
        Time splitTime = new Time();
        String tmpMonth;
        String tmpMonthShort = "";
        for (NotesItem item : items) {
            Time tmpTime = getTimeByMillis(item.getTime());
            if (!(splitTime.getYear() == tmpTime.getYear() && splitTime.getMonth() == tmpTime.getMonth())) {
                splitTime = tmpTime;
                tmpMonth = DateUtils.getNormalMonth(splitTime.getMonth());
                tmpMonthShort = DateUtils.getShortNormalMonths(splitTime.getMonth());
                NotesItemOrder order = new NotesItemOrder();
                order.setType(NotesItemOrder.TYPE_MONTH);
                order.setMonth(tmpMonth);
                order.setYear(splitTime.getYear() + "");
                result.add(order);
            }
            NotesItemOrder order = new NotesItemOrder();
            item.setMonth(tmpMonthShort);
            item.setDay(tmpTime.getDay() + "");
            order.setItem(item);
            order.setType(NotesItemOrder.TYPE_ITEM);
            result.add(order);
        }
        return result;
    }

    public static Time getTimeByMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Time time = new Time();
        time.setYear(calendar.get(Calendar.YEAR));
        time.setMonth(calendar.get(Calendar.MONTH) + 1);
        time.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return time;
    }

    public static int getYearByMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    public static void requestFailToast(Context context){
        Toast.makeText(context,R.string.net_request_fail,Toast.LENGTH_SHORT).show();
    }

}
