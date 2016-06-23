package com.yds.datebase.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yds.datebase.model.Note;
import com.yds.datebase.model.Person;
import com.yds.datebase.model.Post;

/**
 * Created by Administrator on 2016/6/20.
 */
public class DBHelper extends SQLiteOpenHelper{


    private static final String DB_NAME = "app.db";

    /**
     *                     SQLiteDatabase.CursorFactory factory,指定为空，默认也是空的
     *                     DB_NAME写死了，所以构造方法里面由四个参数变成了两个参数
     * @param context
     * @param version
     */
    public DBHelper(Context context,
                    int version) {
        super(context, DB_NAME, null, version);
    }

    /**
     * 初始化表结构
     * 创建表，也可以往表里面放数据
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.SQL_CREATE);
//        db.execSQL(Person.SQL_CREATE);
//        db.execSQL(Post.SQL_CREATE);
    }

    /**
     * 更新表结构
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除表结构
        db.execSQL(Note.SQL_DROP);

        //创建
        onCreate(db);
    }
}
