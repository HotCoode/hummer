package com.rise.db;

/**
 * Created by kai.wang on 2/14/14.
 */
public class SqlConst {
    /** 有效的ITEM */
    public static final String ITEM_STATUS_AVAILABLE = "1";
    /** 無效的ITEM */
    public static final String ITEM_STATUS_INVALID = "0";
    /** 直接添加note */
    public static final String ITEM_STATUS_NOTE_ONLY = "2";
    /** note正常 */
    public static final String NOTE_STATUS_AVAILABLE = "1";
    /** note已刪除 */
    public static final String NOTE_STATUS_INVALID = "0";

    /** 高收益-长半衰期 */
    public static final String NOTE_TYPE_HIGH_INCOME_LONG_HALF_LIFE = "1";
    /** 低收益-长半衰期 */
    public static final String NOTE_TYPE_LOW_INCOME_LONG_HALF_LIFE = "2";
    /** 高收益-短半衰期 */
    public static final String NOTE_TYPE_HIGH_INCOME_SHORT_HALF_LIFE = "3";
    /** 低收益-短半衰期 */
    public static final String NOTE_TYPE_LOW_INCOME_SHORT_HALF_LIFE = "4";

    /** 同步 */
    public static final String SYNC_OK = "1";
    /** 沒同步 */
    public static final String SYNC_NONE = "0";
}
