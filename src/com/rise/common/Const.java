package com.rise.common;

import com.base.common.FileUtils;
import com.base.common.Screen;

/**
 * Created by kai.wang on 2/14/14.
 */
public class Const {

    public static final String PATH = FileUtils.getSDRoot()+"hummer_rise/";

	public static int SCREEN_WIDTH;

    /** 广播，item更新，提醒更新item列表 */
    public static String ACTION_ITEM_UPDATE = "com.rise.item.update";

    public static String PREFERENCE_KEY_REMINDER_TIME = "reminder_time";
    public static String PREFERENCE_KEY_REMINDER = "reminder";

    public static final String ALARM_INTENT = "com.rise.alarm";

}
