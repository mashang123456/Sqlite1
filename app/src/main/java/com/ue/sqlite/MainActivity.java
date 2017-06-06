package com.ue.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ue.sqlite.bean.InfoBean;
import com.ue.sqlite.dao.InfoDao;

public class MainActivity extends Activity implements OnClickListener {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        //创建一个帮助类对象
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(mContext);
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

        //找到相应控件
        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.bt_del).setOnClickListener(this);
        findViewById(R.id.bt_update).setOnClickListener(this);
        findViewById(R.id.bt_query).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        InfoDao infoDao = new InfoDao(mContext);//创建一个dao对象做增删改查
        switch (v.getId()) {
            case R.id.bt_add:
                InfoBean bean = new InfoBean();//创建一条记录
                bean.name = "张三";
                bean.phone ="110";
                infoDao.add(bean);

                InfoBean bean1 = new InfoBean();
                bean1.name = "李四";
                bean1.phone ="120";
                infoDao.add(bean1);
                break;

            case R.id.bt_del://删除一条记录
                infoDao.del("张三");
                break;

            case R.id.bt_update://根据name进行更新
                InfoBean bean2 = new InfoBean();
                bean2.name = "张三";
                bean2.phone ="119";
                infoDao.update(bean2);
                break;


            case R.id.bt_query:
                infoDao.query("张三");
                infoDao.query("李四");
                break;

            default:
                break;
        }

    }




}