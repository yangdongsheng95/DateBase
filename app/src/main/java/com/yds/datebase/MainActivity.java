package com.yds.datebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.yds.datebase.Util.DBHelper;
import com.yds.datebase.model.Note;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;

    //视图
    ListView listView;

    //数据
    Cursor cursor;

    //适配器
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(this,2);
//        for (int i=0;i<5;i++){
//            doSave();
//        }

        listView= (ListView) findViewById(R.id.list_view);

        //获得游标
        cursor=dbHelper.getReadableDatabase().rawQuery("select _id,content,time from notes order by _id desc",null);


        adapter=new SimpleCursorAdapter(this,
                R.layout.note_item,
                cursor,
                Note.ALL,
                new int[]{R.id.textView_id,R.id.textView_content,R.id.textView_time},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new McmListener());
    }

    class McmListener implements AbsListView.MultiChoiceModeListener{

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            //得到选中内容的数量,显示选中的数量
            int count=listView.getCheckedItemCount();
            mode.setTitle(String.valueOf(count));
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            //加载操作模式的菜单
            getMenuInflater().inflate(R.menu.list_action_mode,menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if (item.getItemId()==R.id.action_delet){
               long[] ids= listView.getCheckedItemIds();

                Toast.makeText(MainActivity.this, Arrays.toString(ids),Toast.LENGTH_SHORT).show();

                for (long id:ids){
                    dbHelper.getWritableDatabase()
                            .delete(Note.TABLE_NAME,
                                    "_id=?",
                                    new String[]{String.valueOf(id)});
//                            .execSQL("delete notes where");

                }

                //查询
//                cursor=dbHelper.getWritableDatabase()
//                        .rawQuery("select _id,content,time from notes order by _id desc",null);

                cursor=dbHelper.getReadableDatabase().query(
                        Note.TABLE_NAME,
                        Note.ALL,
                        "_id>? and _id<?",
                        new String[]{"1","15"},
                        null,
                        null,
                        "_id desc",
                        "limit 10 offset 100"
                );

                adapter.swapCursor(cursor);

                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    int i=0;

    public void doSave(){
        Note note=new Note("内容");

//        dbHelper.getWritableDatabase()
//                .insert(Note.TABLE_NAME,null,note.getValues());

//        dbHelper.getWritableDatabase()
//                .execSQL("insert into notes(content,time) values(?,?)",
//                        new Object[]{"数据",System.currentTimeMillis()});

        //方法
        long id=dbHelper.getWritableDatabase().insert(Note.TABLE_NAME,null,note.getValues());

        //查询
        cursor=dbHelper.getWritableDatabase()
                .rawQuery("select _id,content,time from notes order by _id desc",null);

        adapter.swapCursor(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id ==R.id.action_new){
            doSave();
//            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
