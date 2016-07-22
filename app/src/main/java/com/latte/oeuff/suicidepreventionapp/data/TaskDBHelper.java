// Created by Oeuff on 04/07/2016.
//http://muggingsg.com/university/android-app-tutorial-todo-app-using-fragments/

package com.latte.oeuff.suicidepreventionapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

//-----2.TaskDBHelper: open the database to "Execute "--------
public class TaskDBHelper extends SQLiteOpenHelper {

    //---- 1.TaskContract: Defines <1.Constants> which used to access the data  & <2.Other features of a content provider> ---------
    //Each of xxxEntry corresponds to a table in the database.
    public class TaskContract {
        public class TaskEntry implements BaseColumns {
            public static final String TABLE_NAME = "tasks";
            public static final String COLUMN_TASK = "task";
            public static final String COLUMN_DATE = "date";
        }
    }

    //fields
    public static final String DATABASE_NAME = "task.db";
    private static int DATABASE_VERSION = 1;

    //constructor
    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create (very sensitive string (including space))
        final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME +
                " ( " + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.COLUMN_TASK + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_DATE + " TEXT NOT NULL ); ";
        //Execute
        sqLiteDatabase.execSQL(SQL_CREATE_TASKS_TABLE);
        Log.d("create", "task.db");
    }

    //Update the table schema if the stored "version number" is lower than requested in constructor.
    //"version number" https://www.sqlite.org/versionnumbers.html
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("onUp..oldVersion", oldVersion + "");
        Log.d("onUp..newVersion", newVersion + "");

        if (newVersion > oldVersion) {
            Log.d("newVer > oldVer", "reach");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

}
