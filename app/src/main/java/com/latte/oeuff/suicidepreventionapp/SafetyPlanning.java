//tabbed activity: scrolling activity: Insert codes of "Navigation Drawer Activity"

package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

public class SafetyPlanning  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //TextView addasafetyplanningtextview;
    //---------------------tabLayout---------------------------------
    // MyPagerAdapter -> ViewPager -> contents (fragment_ ...)
        //MyPageAdapter = Base class providing the adapter to populate pages inside of a ViewPager

    private TabLayout tabLayout;    //it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter} that will provide fragments for each of the sections.
    private ViewPager mViewPager;   //This class will host the section contents

    //---------------------- About dialog ----------------------------------------
    static DialogFragment newAddSafetyplanningFragment;
    DialogFragment newLogoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_planning);

        //---About others-------

        //----About dialog------
       //newAddSafetyplanningFragment = new AddSafetyplanningFragment();
        newLogoutFragment = new LogOutDialog();

        //------------------------This is for creating the tabLayout and its contents--------------------------
        //#### Get  ViewPager && set it's PagerAdapter -> so that it can display items ####
        MyPagerAdapter mMypageAdapter = new MyPagerAdapter(getSupportFragmentManager()); //=Base class providing the adapter to populate pages inside of a ViewPager
        mViewPager = (ViewPager) findViewById(R.id.container_safetyplanning);
        mViewPager.setAdapter(mMypageAdapter);

        //#### Give the TabLayout the ViewPager ####
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
//      tabLayout.addTab(tabLayout.newTab().setText("..."));
        tabLayout.setupWithViewPager(mViewPager);


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
            it = new Intent(SafetyPlanning.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(SafetyPlanning.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_reminders) {
            it = new Intent(SafetyPlanning.this, Todo.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(SafetyPlanning.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(SafetyPlanning.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(SafetyPlanning.this, HelpNearYou.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(SafetyPlanning.this, Feeling.class);
            startActivity(it);
        } else if (id == R.id.nav_setting) {
            it = new Intent(SafetyPlanning.this, Setting.class);
            startActivity(it);
        }
        else if (id == R.id.nav_logout) {
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //**************************************************************************************************

// --------------------------------Floating menu--------------------------------------------------
//@Override
//public void onCreateContextMenu(ContextMenu menu, View v,
//                                ContextMenu.ContextMenuInfo menuInfo) {
//    super.onCreateContextMenu(menu, v, menuInfo);
//    MenuInflater inflater = getMenuInflater();
//    inflater.inflate(R.menu.context_menu, menu);
//}
//
//@Override
//public boolean onContextItemSelected(MenuItem item) {
//    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//    switch (item.getItemId()) {
//        case R.id.edit:
//            editNote(info.id);
//            return true;
//        case R.id.delete:
//            deleteNote(info.id);
//            return true;
//        default:
//            return super.onContextItemSelected(item);
//    }
//}
//------------------------------------------------------------------------------------------------

    //SEE THE FLOW OF MyPagerAdapter && FirstFragment/SecondFragment/ThirdFragment IN LOGCAT & ORANGE NOTEBOOK
//------------ =Base class providing the adapter to populate pages inside of a ViewPager ------------------------
//--------------"1." This class returns a fragment corresponding to one of the sections/tabs/pages. ----------------------
    private class MyPagerAdapter extends FragmentPagerAdapter {
        //constructor
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm); //use the constructor of FragmentManager class (msectionsPagerAdapter)
            Log.d("Class:MyPagerAdapter","Method:Constructor");
        }

        //called to instantiate the fragment(PlaceholderFragment: defined as a static inner class below) for the given page.
        @Override
        public Fragment getItem(int pos) {
            switch(pos) {   // &&& do method "newInstance" below
                case 0:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=0)Ins1");
                    return FirstFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=1)Ins2");
                    return SecondFragment.newInstance("SecondFragment, Instance 2");
                case 2:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=2)Ins3");
                    return ThirdFragment.newInstance("ThirdFragment, Instance 3");
                case 3:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=3)Ins4");
                    return FourthFragment.newInstance("FourthFragment, Instance 4");
                case 4:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=4)Ins5");
                    return FifthFragment.newInstance("FifthFragment, Instance 5");
                case 5:
                    Log.d("Class:MyPagerAdapter","Method:getItem(pos=5)Ins6");
                    return SixthFragment.newInstance("SixthFragment, Instance 6");
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            Log.d("Class:MyPagerAdapter","Method:getCount()");
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=0)");
                    return "Plan_No1";
                case 1:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=1)");
                    return "Plan_No2";
                case 2:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Plan_No3";
                case 3:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Plan_No4";
                case 4:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Plan_No5";
                case 5:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Plan_No6";
            }
            return null;
        }
    }

    //----------------------FirstFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class FirstFragment extends Fragment {
        LinearLayout addplan_no1_layout, delplan_no1_layout;
        TextView addasafetyplanningtextview1;

        //This is the main method of this class->create a View (fragment_safetyplanning_plan1)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfirst = inflater.inflate(R.layout.fragment_safetyplanning_plan1, container, false); //attachToRoot = false important !
            //-----binding-----------
            newAddSafetyplanningFragment = new AddSafetyplanningFragment();
            addplan_no1_layout = (LinearLayout)viewfirst.findViewById(R.id.addplan_no1_layout);
            delplan_no1_layout = (LinearLayout)viewfirst.findViewById(R.id.delplan_no1_layout);
            addasafetyplanningtextview1 = (TextView)viewfirst.findViewById(R.id.addasafetyplanningtextview1);
            //-----Logics------------
            addplan_no1_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newAddSafetyplanningFragment.show(getFragmentManager(),"Add a safetyplanning");
                    return false;
                }
            });

            delplan_no1_layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //delete
                    return false;
                }
            });

            Log.d("Class:FirstFragment","Method:onCreateView(...)");

            return viewfirst;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static FirstFragment newInstance(String text) {

            FirstFragment f = new FirstFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:FirstFragment","Method:newInstance(FirstFragment, Instance 1 )");

            return f;
        }
    }

    //----------------------SecondFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class SecondFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan2)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewsecond = inflater.inflate(R.layout.fragment_safetyplanning_plan2, container, false);//attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------


            Log.d("Class:SecondFragment","Method:onCreateView(...)");

            return viewsecond;
        }

        //&&& This method create & return "a new instance" of this fragment according to  "String text".
        public static SecondFragment newInstance(String text) {

            SecondFragment f = new SecondFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //--------------------------
            f.setArguments(b);

            Log.d("Class:SecondFragment","Method:newInstance(SecondFragment, Instance 2 )");

            return f;
        }
    }
    //----------------------ThirdFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class ThirdFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan3)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewthird = inflater.inflate(R.layout.fragment_safetyplanning_plan3, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------

            Log.d("Class:ThirdFragment","Method:onCreateView(...)");

            return viewthird;
        }

        //&&& This method create & return "a new instance" of this fragment according to  "String text".
        public static ThirdFragment newInstance(String text) {

            ThirdFragment f = new ThirdFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:ThirdFragment","Method:newInstance(ThirdFragment, Instance 3 )");

            return f;
        }
    }

    //----------------------FourthFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class FourthFragment extends Fragment {

        //This is the main method of this class->create a View (ffragment_safetyplanning_plan4)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfourth = inflater.inflate(R.layout.fragment_safetyplanning_plan4, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------


            Log.d("Class:FourthFragment","Method:onCreateView(...)");

            return viewfourth;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static FourthFragment newInstance(String text) {

            FourthFragment f = new FourthFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:FourthFragment","Method:newInstance(FourthFragment, Instance 4 )");

            return f;
        }
    }

    //----------------------FifthFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class FifthFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan5)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfifth = inflater.inflate(R.layout.fragment_safetyplanning_plan5, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------

            Log.d("Class:FifthFragment","Method:onCreateView(...)");

            return viewfifth;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static FifthFragment newInstance(String text) {

            FifthFragment f = new FifthFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:FifthFragment","Method:newInstance(FifthFragment, Instance 5 )");

            return f;
        }
    }

    //----------------------SixthFragement--------------------------------------
    //2. This class creates a fragment containing a simple view.
    public static class SixthFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_safetyplanning_plan6)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewsixth = inflater.inflate(R.layout.fragment_safetyplanning_plan6, container, false); //attachToRoot = false important !

            //-----binding-----------

            //-----Logics------------


            Log.d("Class:SixthFragment","Method:onCreateView(...)");

            return viewsixth;
        }

        //&&& 2. This method create & return "a new instance" of this fragment according to  "String text".
        public static SixthFragment newInstance(String text) {

            SixthFragment f = new SixthFragment(); //create a frament for containing a simple view
            //----Bundle <key, value>----
            Bundle b = new Bundle();
            b.putString("msg", text); //put <key,value> into it
            //---------------------------
            f.setArguments(b);

            Log.d("Class:SixthFragment","Method:newInstance(SixthFragment, Instance 6 )");

            return f;
        }
    }

    //-----------------------------------------------------------------------------------------------------

    //------------------------ Dialog for Importing a photo Logics -------------------------------------
    public static class AddSafetyplanningFragment extends DialogFragment {
        TextView addplan_btn;
        TextView cancelplan_btn;
        private static final String TAG = "MainActivity";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //-----Binding-----------
            View view = inflater.inflate(R.layout.dialog_add_safetyplanning, container);
            addplan_btn = (TextView)view.findViewById(R.id.addplan_btn);
            cancelplan_btn = (TextView)view.findViewById(R.id.cancelplan_btn);
            //-----Logics-----------
            addplan_btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newAddSafetyplanningFragment.dismiss();
                  //addasafetyplanningtextview.setText("A safety planning is added !"); //IMPROVE IT BEACUSE IT IS COMPLEXED !
                    return false;
                }
            });
            cancelplan_btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    newAddSafetyplanningFragment.dismiss();
                    return false;
                }
            });

            return view;
        }
    }
//---------------------------------------------------------------------------------------------

    //-----------------------------------Dialog for warning before logging out--------------------------
    public class LogOutDialog extends DialogFragment {

        TextView yesbtn_logout,nobtn_logout;

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
                    Intent it = new Intent(SafetyPlanning.this, LoginActivity.class);
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
