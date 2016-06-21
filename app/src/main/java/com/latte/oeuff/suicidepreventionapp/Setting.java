//setting activity: Insert codes of "Navigation Drawer Activity"

package com.latte.oeuff.suicidepreventionapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.List;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
 * A {@link PreferenceActivity} that presents a set of application settings.
          On handset devices = a single list.
          On tablets = are split by category, shown to the left.
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * For more information:
             </a> for design guidelines and the <a href="http://developer.android.com/guide/topics/ui/settings.html">Settings API Guide</a>
 */
public class Setting extends AppCompatPreferenceActivity  {

    //=================== About Preference Value & Preference Summary ====================================

//***  A preference value change listener that updates the preference's summary / to reflect its new value.
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) { //***Called when a Preference has been changed by the user.
            String stringValue = value.toString();
                //Receive preference as a parameter
            if (preference instanceof ListPreference) { //if you can declare "ListPreference preference =..."
                ListPreference listPreference = (ListPreference) preference;  //var = preference type 'ListPreference'
                int index = listPreference.findIndexOfValue(stringValue);     //find the index of "stringValue" in var
                //***Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index] //get "stringValue" in var
                                : null);    //if index<0

            } else if (preference instanceof RingtonePreference) { //A Preference that allows the user to choose a ringtone from those on the device. The chosen ringtone's URI will be persisted as a string.
                //***  For ringtone preferences, look up the correct display value using RingtoneManager (=provides access to ringtones, notification, and other types of sounds).
                if (TextUtils.isEmpty(stringValue)) { //if stringValue is Empty -> setSummary to 'no ringtone'
                    preference.setSummary(R.string.pref_ringtone_silent);
                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone( //get a Ringtone from the device by  2. getting Context & 1. looking with ringtone's Url.
                            preference.getContext(), Uri.parse(stringValue));

                    //After getting a ringtone
                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                //***For all other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    //Binds a preference's summary(line of text below the preference title) <-> its value. Preference title is updated to reflect the value. @see #sBindPreferenceSummaryToValueListener
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    //Helper method to determine if the device has an extra-large screen.
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    //===================== onCreate ===========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    //====================== About UI ====================================================
    //Set up the {@link android.app.ActionBar}, if the API is available.
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar(); //It is at above of a page
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //{@inheritDoc} like: http://www.vogella.com/tutorials/AndroidFragments/article.html
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    //This method stops fragment injection in malicious applications.Make sure to deny any unknown fragments here.
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || DataSyncPreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    //=========1. This fragment shows "HEADERS preferences" only. ->(pref_headers.xml)===========

    //{@inheritDoc}
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) //Enumeration of the currently known SDK version codes
    public void onBuildHeaders(List<Header> target) { //Called when the activity needs its list of headers build
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    //ONLY ONE LISTVIEW
    //=========2. This fragment shows "GENERAL preferences" only. It is used when the activity is showing a two-pane settings UI. ->(pref_general.xml) ===========

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences to their values, Updated to reflect the new value.
            bindPreferenceSummaryToValue(findPreference("displaynamekey"));
            bindPreferenceSummaryToValue(findPreference("addfriendskey"));
//
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString("keyChannel", "hello123");
//            editor.commit();// commit is important here.

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), Setting.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//       // username = (TextView)findViewById(R.id.username);
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        //--------- Logics after pressing items on Navigation ----------------
//        Intent it;
//        if (id == R.id.nav_home) {
//
//        }
//            //Dialouge ??
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    //ONLY ONE LISTVIEW
    //================= 3. This fragment shows "NOTIFICATION preferences" only. It is used when the activity is showing a two-pane settings UI.->(pref_notification.xml) ========

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences to their values. When their values change, their summaries are updated.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), Setting.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    //ONLY ONE LISTVIEW
    //=========== 4. This fragment shows "DATA & SYNC preferences" only. It is used when the activity is showing a two-pane settings UI.->(pref_data_sync.xml) =============

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences to their values. When their values change, their summaries are updated.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), Setting.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    //--------------Logics across an activity ----------------------

}
