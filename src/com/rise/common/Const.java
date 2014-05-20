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
    public static String ACTION_NOTE_UPDATE = "com.rise.note.update";

    public static String ACTION_SIGN_SUCCESS = "com.rise.sign.success";
    public static String ACTION_SIGN_FAIL = "com.rise.sign.fail";

    public static String PREFERENCE_KEY_REMINDER_TIME = "reminder_time";
    public static String PREFERENCE_KEY_REMINDER = "reminder";


    public static final String SHARED_PREFERENCES_NAME = "com.rise";

}
