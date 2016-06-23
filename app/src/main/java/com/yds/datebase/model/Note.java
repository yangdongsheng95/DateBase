package com.yds.datebase.model;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/20.
 * count
 * 实现了这个接口，就可以不用写主键了
 */
public class Note implements BaseColumns{

    //表名
    public static final String TABLE_NAME="notes";

    //字段名
    //要使用游标适配器的话主键名字不能随便写，必须写成_id
    public static final String _ID="_id";
    public static final String _CONTENT="content";
    public static final String _TIME="time";

    public static final String SQL_CREATE=String.format(
            "create table %s(_id integer primary key autoincrement,%s text,%s integer default current_timestamp)",
            TABLE_NAME,
            _CONTENT,
            _TIME
    );

    public static final String SQL_DROP=String.format(
            "drop table if exists %s",
            TABLE_NAME
    );

    public static final String[] ALL={_ID,_CONTENT,_TIME};

    int id;
    String content;
    Long time;

    public Note() {
    }

    public Note(String content) {
        this.content=content;
        time=System.currentTimeMillis();
    }

    /**
     * 返回内容值(表中列和对象字段的映射)
     * @return
     */
    public ContentValues getValues(){
        ContentValues values=new ContentValues();
        values.put(_CONTENT,content);
        values.put(_TIME,time);
        return values;
    }
}
