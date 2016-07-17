//scrolling activity: Insert codes of "Navigation Drawer Activity"
//create a "Todo" listview is work in a smartphone but not in a genymotion emulator
//http://muggingsg.com/university/android-app-tutorial-todo-app-using-fragments/

package com.latte.oeuff.suicidepreventionapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Calendar;

//import com.latte.oeuff.suicidepreventionapp.data.TaskContract;
import com.latte.oeuff.suicidepreventionapp.data.TaskDBHelper;

public class Todo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //-----for Todo-------------
    TaskAdapter mTaskAdapter;
    EditText inputField;
    String taskinput, dateinput;
    // These indices are tied to TASKS_COLUMNS.  If TASKS_COLUMNS changes, these must change.
    static final int COL_TASK_ID = 0;
    static final int COL_TASK_NAME = 1;
    static final int COL_TASK_DATE = 2;

    //-------About Calendar------
    private int mYear, mMonth, mDay; //mMonth must +1
    static final int DATE_DIALOG_ID = 0;
    //DatePicker todo_datePicker; not work

    //---About Dialog---
    DialogFragment newLogoutFragment;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        //---About Dialog & Resources---
        newLogoutFragment = new LogOutDialog();

        //----------------------Query todo list when open "todo" page-------------------------//
        ListView listView = (ListView)findViewById(R.id.listview_tasks);
        TaskDBHelper helper = new TaskDBHelper(Todo.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase(); //Get helper into "dqlDB" to read from database

        //Query database to get any existing data
            //https://developer.android.com/reference/android/database/Cursor.html
            //This interface provides random read-write access to the result set returned by a database query.
        Cursor cursor_tmp = sqlDB.query(TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME, new String[]
                        {TaskDBHelper.TaskContract.TaskEntry._ID, TaskDBHelper.TaskContract.TaskEntry.COLUMN_TASK, TaskDBHelper.TaskContract.TaskEntry.COLUMN_DATE},
                null, null, null, null, null);

        //Create a new TaskAdapter and bind it to ListView
        mTaskAdapter = new TaskAdapter(getBaseContext(), cursor_tmp);
        listView.setAdapter(mTaskAdapter);

        Log.d("mTaskAdapter","is set");
        //----------------------------------------------------------------------------------//
        //***** get the current date ****
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1; //must +1
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Log.d("get the current date","reach");

        //------------------------------ Logics --------------------------------------------------------
        //----------------------for create a dialog "to do" -------------------------
        FloatingActionButton fabBtnAddTodo = (FloatingActionButton) findViewById(R.id.fabBtnAddTodo);
        Log.d(TAG, "Add new Todo");
        fabBtnAddTodo.setImageResource(R.drawable.ic_new_todo);

        fabBtnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1. Create an dialog
                //************ IMPORTANT: These can fix a bug of "showing a task" *********************
                LayoutInflater inflator = Todo.this.getLayoutInflater(); //**
                final View view_dialog_todo = inflator.inflate(R.layout.dialog_create_todo, null); //**
                inputField = (EditText)view_dialog_todo.findViewById(R.id.todo_title);
                AlertDialog.Builder builder = new AlertDialog.Builder(Todo.this);
                builder.setView(view_dialog_todo);
                //*********************************************************************************

                builder.setPositiveButton("Add",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Log.d("reach","here");
                        Log.d("inputField",inputField.getText().toString());

                        //*** important : to show a calendar dialog *******
                        showDialog(DATE_DIALOG_ID);
                        Log.d("Todo-onclick","success");

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                //builder.show();
                Log.d("create a dialog","success");
            }
        });

        //************************ This is for creating the Navigation Menu*********************************
        //Toolbar (Top)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Set a Toolbar to act as  ActionBar for this Activity


        // top-level container of "Navigation Drawer" (side)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(  //=tie together "functionality of DrawerLayout" <-> "framework ActionBar"
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);                         //Set a listener to be notified of drawer events
        toggle.syncState();                                       //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout

        //view of "Navigation Drawer" (side)
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //*****To uncover colors of icon**********
        navigationView.setItemIconTintList(null);

        //floating button (bottom)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtn);
        fab.setImageResource(R.drawable.emergencycall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Emergency call ?", Snackbar.LENGTH_LONG) //=bottom black bar
//                        .setAction("Action", null).show();

                //This is for going to phone in mobile
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:112"));
                //no need to request a permission
                startActivity(callIntent);
            }
        });
        //**************************************************************************************************
    }

    //**************** Show TimePicker & DatePicker *******************************
    //This class is for creating a Calendar up-to-date: https://developer.android.com/guide/topics/ui/controls/pickers.html

    //FLOW NO.1 return DatePickerDialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        Log.d("onCreateDialog","reach");
        return null;
    }

    //FLOW NO.2  the call back received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    //FLOW NO.3 Above &&&&
                    mYear = year;
                    mMonth = monthOfYear + 1; //must +1
                    mDay = dayOfMonth;
                    Log.d("DatePickerDialog","reach");
                    Log.d("date after choosing:", mYear + ": " + mMonth + ": "+mDay);

                    //@@@ Keep task input into "taskinput"
                        taskinput = inputField.getText().toString();
                        Log.d("taskinput: ", taskinput);

                    //@@@ Keep date input into "dateinput"
                    dateinput = mDay + "/" + mMonth + "/" + mYear;
                    Log.d("dateinput: ",dateinput);

                    //@@@ Execute to store & show the inputs
                    addTodo();


                }
            };

    //************************ Add a "to do" list  ****************************************************
    //FLOW NO. 4
    public void addTodo(){

        //1.1 Get DBHelper to write into the database
        TaskDBHelper helper = new TaskDBHelper(Todo.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //1.2 Put in the values within a ContentValues.
        ContentValues values = new ContentValues();
        values.clear();
        values.put(TaskDBHelper.TaskContract.TaskEntry.COLUMN_TASK, taskinput);
        values.put(TaskDBHelper.TaskContract.TaskEntry.COLUMN_DATE, dateinput);

        //1.3 Insert the values into the Table for Tasks
        db.insertWithOnConflict(
                TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_IGNORE);

        //1.4 Query database again to get updated data
            //https://developer.android.com/reference/android/database/Cursor.html
            //This interface provides random read-write access to the result set returned by a database query.
        Cursor cursor = db.query(TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME,
                new String[]{TaskDBHelper.TaskContract.TaskEntry._ID, TaskDBHelper.TaskContract.TaskEntry.COLUMN_TASK, TaskDBHelper.TaskContract.TaskEntry.COLUMN_DATE},
                null, null, null, null, null);
       // Swap in a new Cursor, returning the old Cursor.
        mTaskAdapter.swapCursor(cursor);

        //1.5 Find the listView
        ListView listView = (ListView)findViewById(R.id.listview_tasks);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();

        //1.6 Query database to get any existing data
            //https://developer.android.com/reference/android/database/Cursor.html
            //This interface provides random read-write access to the result set returned by a database query.
        Cursor cursor1 = sqlDB.query(TaskDBHelper.TaskContract.TaskEntry.TABLE_NAME,
                new String[]{TaskDBHelper.TaskContract.TaskEntry._ID, TaskDBHelper.TaskContract.TaskEntry.COLUMN_TASK, TaskDBHelper.TaskContract.TaskEntry.COLUMN_DATE},
                null, null, null, null, null);

        //1.7 Create a new TaskAdapter and bind it to ListView
        mTaskAdapter = new TaskAdapter(getBaseContext(), cursor);
        listView.setAdapter(mTaskAdapter);

        Log.d("addTodo","success");

    }
    //FLOW NO.5 , NO.6 are at TaskAdapter.java

    //************************ This is for creating the Navigation Menu*********************************
    //Close "Navigation Drawer"
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) { //GravityCompat = Compatibility shim for accessing newer functionality from Gravity
            //Gravity =  Standard constants, tools for placing an object within a potentially larger container
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Initialize the contents of the Activity's standard options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate(add) the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu); //MenuInflater allows you to inflate the context menu from a menu resource
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Called when the user selects an item from the options menu. Handle action bar item clicks here.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) { //it is in main.xml (ctrl+click to see)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //This method is called whenever a navigation item in your action bar is selected
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //CHECK IN "activity_main_drawer.xml"
        //--------- Logics after pressing items on Navigation ----------------
        Intent it;
        if (id == R.id.nav_home) {
            it = new Intent(Todo.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(Todo.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_todo) {
            it = new Intent(Todo.this, Todo.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(Todo.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(Todo.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(Todo.this, HelpNearYouOverview.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(Todo.this, Feeling.class);
            startActivity(it);
        } else if (id == R.id.nav_survey) {
            it = new Intent(Todo.this, SurveyOverview.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    //**************************************************************************************************

    //-----------------------------------Dialog for warning before logging out--------------------------
    public class LogOutDialog extends DialogFragment {

        TextView yesbtn_logout, nobtn_logout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //-----Binding-----------
            View view = inflater.inflate(R.layout.dialog_logout, container);
            yesbtn_logout = (TextView) view.findViewById(R.id.yesbtn_logout);
            nobtn_logout = (TextView) view.findViewById(R.id.nobtn_logout);

            //-----Logics-----------
            yesbtn_logout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newLogoutFragment.dismiss(); //close the dialog
                    Intent it = new Intent(Todo.this, LoginActivity.class);
                    startActivity(it);
                    return false;
                }
            });
            nobtn_logout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newLogoutFragment.dismiss(); //close the dialog
                    return false;
                }
            });
            return view;
        }
    }
//--------------------------------------------------------------------------------------------------

}
