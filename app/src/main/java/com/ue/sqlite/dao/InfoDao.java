package com.ue.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ue.sqlite.MySqliteOpenHelper;
import com.ue.sqlite.bean.InfoBean;

public class InfoDao {

    private MySqliteOpenHelper mySqliteOpenHelper;
    public InfoDao(Context context){
        //创建一个帮助类对象
        mySqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    public boolean add(InfoBean bean){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        //(1)sql:sql语句，  bindArgs：sql语句中占位符的值,无返回值
        //db.execSQL("insert into info(name,phone) values(?,?);", new Object[]{bean.name,bean.phone});
        ContentValues values = new ContentValues();//是用map封装的对象，用来存放值
        values.put("name", bean.name);
        values.put("phone", bean.phone);
        //(2有返回值)table: 表名 , nullColumnHack：可以为空，标示添加一个空行, values:数据一行的值 , 返回值：代表添加这个新行的Id ，-1代表添加失败
        long result = db.insert("info", null, values);//底层是在拼装sql语句
        db.close();
        if(-1 != result){//-1代表添加失败
            return true;
        }else{
            return false;
        }
    }

    public int del(String name){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句，  bindArgs：sql语句中占位符的值
        //db.execSQL("delete from info where name=?;", new Object[]{name});
        //table ：表名, whereClause: 删除条件, whereArgs：条件的占位符的参数 ; 返回值：成功删除多少行
        int result = db.delete("info", "name = ?", new String[]{name});
        //关闭数据库对象
        db.close();
        return result;
    }

    public int update(InfoBean bean){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句，  bindArgs：sql语句中占位符的值
        //db.execSQL("update info set phone=? where name=?;", new Object[]{bean.phone,bean.name});

        ContentValues values = new ContentValues();//是用map封装的对象，用来存放值
        values.put("phone", bean.phone);
        //table:表名, values：更新的值, whereClause:更新的条件, whereArgs：更新条件的占位符的值,返回值：成功修改多少行
        int result = db.update("info", values, "name = ?", new String[]{bean.name});
        //关闭数据库对象
        db.close();
        return result;
    }

    public void query(String name){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句，  selectionArgs:查询条件占位符的值,返回一个cursor对象
        //Cursor cursor = db.rawQuery("select _id, name,phone from info where name = ?;", new String []{name});

        //table:表名, columns：查询的列名,如果null代表查询所有列； selection:查询条件, selectionArgs：条件占位符的参数值,
        //groupBy:按什么字段分组, having:分组的条件, orderBy:按什么字段排序
        Cursor cursor = db.query("info", new String[]{"_id","name","phone"}, "name = ?", new String[]{name}, null, null, "_id desc");
        if(cursor != null && cursor.getCount() >0){//判断cursor中是否存在数据
            //循环遍历结果集，获取每一行的内容
            while(cursor.moveToNext()){//条件，游标能否定位到下一行
                //获取数据
                int id = cursor.getInt(0);
                String name_str = cursor.getString(1);
                String phone = cursor.getString(2);
                System.out.println("_id:"+id+";name:"+name_str+";phone:"+phone);
            }
            cursor.close();//关闭结果集

        }
        db.close();
    }
}
