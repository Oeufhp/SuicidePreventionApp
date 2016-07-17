//Created by Oeuff on 05/07/2016.
//http://muggingsg.com/university/android-app-tutorial-todo-app-using-fragments/

package com.latte.oeuff.suicidepreventionapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

//import class "TaskDBHelper" into here
import com.latte.oeuff.suicidepreventionapp.data.TaskDBHelper;

import java.util.Calendar;

public class TaskAdapter extends CursorAdapter {
    //fields
    private static Context context;
    TaskDBHelper helper;

    //***** get the current date ****
     final Calendar c = Calendar.getInstance();
     int mYear = c.get(Calendar.YEAR);
     int mMonth = c.get(Calendar.MONTH) + 1; //must +1
     int mDay = c.get(Calendar.DAY_OF_MONTH);


 //constructors
    //https://developer.android.com/reference/android/database/Cursor.html
    //This interface provides random read-write access to the result set returned by a database query.
    public TaskAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
        helper = new TaskDBHelper(context);
    }

    //FLOW NO.6 (from Todo.java)
    //Bind all data to a given view.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("bindView", "reach");

        //1. Find Views to populate in inflated template
        TextView list_todo_textview = (TextView) view.findViewById(R.id.list_todo_textview);
        Button done_button = (Button) view.findViewById(R.id.list_todo_done_button);

        //2. Extract properties from cursor **
        final String id = cursor.getString(Todo.COL_TASK_ID);
        final String task = cursor.getString(Todo.COL_TASK_NAME);
        final String date  = cursor.getString(Todo.COL_TASK_DATE);

        //***** IMPORTANT: 3. Populate views with extracted properties *****
        list_todo_textview.setText(task + " : " + date);

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("done","clicked");

                //3.1 Create a SQL command & Execute for deleting a particular ID.
                    //String "sql" is a command to delete a row that has "_ID"=id in table "TABLE_NAME"
                String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                        TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME ,
                        TaskDBHelper.TaskContract.TaskEntry._ID, id);

                SQLiteDatabase sqlDB = helper.getWritableDatabase();
                sqlDB.execSQL(sql);
                notifyDataSetChanged();

                //3.2 Query database for updated data
                    //https://developer.android.com/reference/android/database/Cursor.html
                    //This interface provides random read-write access to the result set returned by a database query.
                Cursor cursor = sqlDB.query(TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME,new String[]
                                            {TaskDBHelper.TaskContract.TaskEntry._ID, TaskDBHelper.TaskContract.TaskEntry.COLUMN_TASK, TaskDBHelper.TaskContract.TaskEntry.COLUMN_DATE},
                        null,null,null,null,null);
                swapCursor(cursor);  //Instance method with TaskAdapter so no need to use adapter.swapCursor()
                                     //Swap in a new Cursor, returning the old Cursor.

                //3.3 Pop up a snackbar !!!
                //Snackbar.make(v,"  You have done the task: \"" + task.toUpperCase() + "\"",Snackbar.LENGTH_LONG).show();
                Snackbar.make(v," You have done the task:" + task + " in " + mDay + "/" + mMonth + "/" + mYear ,Snackbar.LENGTH_LONG).show();
            }
        });

    }

    //FLOW NO.5 (from Todo.java)
    //Inflate a new view and return it. (you don't bind any data to the view at this point.)
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Log.d("newView","reach");

        return LayoutInflater.from(context).inflate(R.layout.list_item_task, viewGroup, false); // = list_item_task.xml
    }
}
