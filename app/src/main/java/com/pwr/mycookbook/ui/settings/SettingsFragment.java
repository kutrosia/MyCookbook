package com.pwr.mycookbook.ui.settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.RequiresApi;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.pwr.mycookbook.R;

/**
 * Created by olaku on 30.01.2018.
 */

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener  {


    public static final String KEY_ALARM_SOUND = "key_alarm_sound";
    public static final String KEY_VIBRATE = "key_vibrate";
    public static final String KEY_NOTIFICATION_ACTIONBAR = "key_notification_actionbar";
    public static final String KEY_APPEARANCE_COLOR = "key_appearance_color";
    public static final String KEY_GENDER = "key_gender";
    public static final String KEY_SYNC = "key_sync";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_ALARM_SOUND)) {
            android.preference.Preference connectionPref = findPreference(key);
            Toast.makeText(getContext(), sharedPreferences.getString(key, ""), Toast.LENGTH_LONG).show();
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_VIBRATE)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_NOTIFICATION_ACTIONBAR)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_APPEARANCE_COLOR)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_GENDER)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_ALARM_SOUND)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }else if (key.equals(KEY_SYNC)) {
            android.preference.Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }
}
