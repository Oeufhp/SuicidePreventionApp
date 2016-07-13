package com.latte.oeuff.suicidepreventionapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.latte.oeuff.suicidepreventionapp.data.TaskContract;
import com.latte.oeuff.suicidepreventionapp.data.TaskDBHelper;

/**
 * Created by Oeuff on 05/07/2016.
 */
public class TaskAdapter extends CursorAdapter {
    private static Context context;
    TaskDBHelper helper;

    public TaskAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
        helper = new TaskDBHelper(context);
    }
    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_task, viewGroup, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find Views to populate in inflated template
        TextView textView = (TextView) view.findViewById(R.id.list_item_task_textview);
//        Button done_button = (Button) view.findViewById(R.id.list_item_done_button);
        ImageButton done_button=(ImageButton)view.findViewById(R.id.list_item_done_button);
        done_button.setBackgroundResource(R.drawable.done_button);

        // Extract properties from cursor
        final String id = cursor.getString(Todo.COL_TASK_ID);
        final String task = cursor.getString(Todo.COL_TASK_NAME);

        // Populate views with extracted properties
        textView.setText(task);
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a SQL command for deleting a particular ID.
                String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                        TaskContract.TaskEntry.TABLE_NAME,
                        TaskContract.TaskEntry._ID,
                        id);
                SQLiteDatabase sqlDB = helper.getWritableDatabase();
                //Execute the delete command
                sqlDB.execSQL(sql);
                notifyDataSetChanged();

                //Query database for updated data
                Cursor cursor = sqlDB.query(TaskContract.TaskEntry.TABLE_NAME,
                        new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_TASK},
                        null,null,null,null,null);
                //Instance method with TaskAdapter so no need to use adapter.swapCursor()
                swapCursor(cursor);
                Snackbar.make(v,"You have done the task: \""+task.toUpperCase()+"\"",Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
