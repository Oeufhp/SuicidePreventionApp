package com.latte.oeuff.suicidepreventionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.io.File;

public class YourSpace extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//--------------About tabLayout-----------
    // MyPagerAdapter -> ViewPager -> contents (fragment_your_space_photos / videos / diary )
        //MyPageAdapter = Base class providing the adapter to populate pages inside of a ViewPager

    private TabLayout yourspace_tabLayout;    //it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter} that will provide fragments for each of the sections.
    private ViewPager mViewPager;   //This class will host the section contents
//----------------About Dialog --------------
    DialogFragment newLogoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_space);
        newLogoutFragment = new LogOutDialog();

//        final Button showhelpnearyou = (Button)findViewById(R.id.showhelpnearyou);
//        showhelpnearyou.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(YourSpace.this, ShowHelpNearYou.class);
//                startActivity(it);
//            }
//        });

    //------------------------This is for creating the tabLayout and its contents--------------------------//
        //#### Get  ViewPager && set it's PagerAdapter -> so that it can display items ####
        MyPagerAdapter mMypageAdapter = new MyPagerAdapter(getSupportFragmentManager()); //=Base class providing the adapter to populate pages inside of a ViewPager
        mViewPager = (ViewPager) findViewById(R.id.container_yourspace);
        mViewPager.setAdapter(mMypageAdapter);

        //#### Give the TabLayout the ViewPager ####
        yourspace_tabLayout = (TabLayout)findViewById(R.id.yourspace_tabLayout);
        //yourspace_tabLayout.addTab(tabLayout.newTab().setText("..."));
        yourspace_tabLayout.setupWithViewPager(mViewPager);
    //------------------------------------------------------------------------------------------------------//

        //************************ This is for creating the Navigation Menu*********************************
        //Toolbar (Top)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Set a Toolbar to act as  ActionBar for this Activity

        // top-level container of "Navigation Drawer" (side)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( //=tie together "functionality of DrawerLayout" <-> "framework ActionBar"
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle); //Set a listener to be notified of drawer events
        toggle.syncState();               //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout

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
                //This is for going to phone in mobile
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:911"));
                //no need to request a permission
                startActivity(callIntent);
            }
        });
        //**************************************************************************************************

        //--------------Logics across an activity ------------------------------
            //Don't forget to create XML for this logic !
//        aaa = (TextView)findViewById(R.id.aaa);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String go = prefs.getString("keyChannel",null);
//        aaa.setText(go);
        //----------------------------------------------------------------------

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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);  //MenuInflater allows you to inflate the context menu from a menu resource
        getMenuInflater().inflate(R.menu.main, menu);
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
        Intent it ;
        if (id == R.id.nav_home) {
            it = new Intent(YourSpace.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_yourspace) {
            it = new Intent(YourSpace.this, YourSpace.class);
            startActivity(it);
        } else if (id == R.id.nav_reminders) {
            it = new Intent(YourSpace.this, Reminders.class);
            startActivity(it);
        } else if (id == R.id.nav_safetyplanning) {
            it = new Intent(YourSpace.this, SafetyPlanning.class);
            startActivity(it);
        } else if (id == R.id.nav_resources) {
            it = new Intent(YourSpace.this, Resources.class);
            startActivity(it);
        } else if (id == R.id.nav_helpnearyou) {
            it = new Intent(YourSpace.this, HelpNearYou.class);
            startActivity(it);
        } else if (id == R.id.nav_feeling) {
            it = new Intent(YourSpace.this, Feeling.class);
            startActivity(it);
        } else if (id == R.id.nav_setting) {
            it = new Intent(YourSpace.this, Setting.class);
            startActivity(it);
        }
        else if (id == R.id.nav_logout) {
            newLogoutFragment.show(getSupportFragmentManager(), "LogOut");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //*************************************************************************************

//SEE THE FLOW OF MyPagerAdapter && FirstFragment/SecondFragment/ThirdFragment IN LOGCAT & ORANGE NOTEBOOK
    //=Base class providing the adapter to populate pages inside of a ViewPager

    //------------------------ MyPagerAdapter ----------------------------------------------------
        //"1." Class"MyPagerAdapter" returns a fragment corresponding to one of the sections/tabs/pages.
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
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            Log.d("Class:MyPagerAdapter","Method:getCount()");
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=0)");
                    return "Photos";
                case 1:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=1)");
                    return "Videos";
                case 2:
                    Log.d("Class= SectionPage...","Method= getPageTitle(position=2)");
                    return "Diary";
            }
            return null;
        }
    }

    //----------------------FirstFragement--------------------------------------
        //2. Class"FirstFragment" creates a fragment containing a simple view.
    public static class FirstFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_your_space_photos)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewfirst = inflater.inflate(R.layout.fragment_your_space_photos, container, false); //attachToRoot = false important !

            //-----binding-----------
            final TextView photostextview = (TextView)viewfirst.findViewById(R.id.photostextview);
            //-----Logics------------
            photostextview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    photostextview.setText("Change!!!");
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
        //2. class"SecondFragment" creates a fragment containing a simple view.
    public static class SecondFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_your_space_videos)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewsecond = inflater.inflate(R.layout.fragment_your_space_videos, container, false);//attachToRoot = false important !

            //-----binding-----------
            final TextView videostextview = (TextView)viewsecond.findViewById(R.id.videostextview);
            //-----Logics------------
            videostextview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    videostextview.setText("Change!!!");
                    return false;
                }
            });

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
        //3. Class"ThirdFragment" creates a fragment containing a simple view.
    public static class ThirdFragment extends Fragment {

        //This is the main method of this class->create a View (fragment_your_space_diary)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View viewthird = inflater.inflate(R.layout.fragment_your_space_diary, container, false); //attachToRoot = false important !

            //-----binding-----------
            final TextView diarytextview = (TextView)viewthird.findViewById(R.id.diarytextview);
            //-----Logics------------
            diarytextview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    diarytextview.setText("Change!!!");
                    return false;
                }
            });

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

            Log.d("Class:SecondFragment","Method:newInstance(ThirdFragment, Instance 3 )");

            return f;
        }
    }
//-----------------------------------------------------------------------------------------------------//

//--------------------------Dialog for warning before logging out--------------------------
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
                    Intent it = new Intent(YourSpace.this, LoginActivity.class);
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

