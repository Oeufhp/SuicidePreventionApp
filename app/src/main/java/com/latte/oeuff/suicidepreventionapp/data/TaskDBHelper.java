package com.latte.oeuff.suicidepreventionapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oeuff on 04/07/2016.
 */
public class TaskDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="task.db";
    private static int DATABASE_VERSION=1;

    public TaskDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TASKS_TABLE="CREATE TABLE " +TaskContract.TaskEntry.TABLE_NAME+" ("+
                TaskContract.TaskEntry._ID+" INTEGER PRIMARY KEY,"+TaskContract.TaskEntry.COLUMN_TASK+" TEXT NOT NULL );";

        sqLiteDatabase.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TaskContract.TaskEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
