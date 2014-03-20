package com.rise.common;

import com.base.orm.QueryHelper;

/**
 * Created by kai.wang on 3/20/14.
 */
public class AppUtils {

    /**
     * 退出程序
     */
    public static void exit(){
        QueryHelper.close();
        System.exit(1);
    }
}
