package com.ailk.pmph.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.utils
 * 作者: Chrizz
 * 时间: 2016/11/13
 */

public class SearchHistoryHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;

    public SearchHistoryHelper(Context context) {
        super(context, Constant.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREAT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
