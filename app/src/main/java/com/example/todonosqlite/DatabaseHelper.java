package com.example.todonosqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "toDoDatabase3";
    public static final String CONTACTS_TABLE_NAME = "toDoDetails";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "create table " + CONTACTS_TABLE_NAME + "(id INTEGER PRIMARY KEY, name text)"
            );
        } catch (
                SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase toDoDatabase3,int oldVersion, int newVersion) {
        toDoDatabase3.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(toDoDatabase3);
    }

    public boolean insert(String s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", s);
        db.replace(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList getAllContacts() {
        SQLiteDatabase s = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        @SuppressLint("Recycle") Cursor res = s.rawQuery("select * from " +CONTACTS_TABLE_NAME+"  name  ", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("name" )));
            res.moveToNext();
        }
        return array_list;
    }
    public void deleteNote(String name){
        SQLiteDatabase toDoDatabase3 =this.getWritableDatabase();
        toDoDatabase3.delete(CONTACTS_TABLE_NAME,"name = ?" ,new String[] {name});
    }
}
