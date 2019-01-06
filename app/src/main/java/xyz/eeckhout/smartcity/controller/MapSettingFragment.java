package xyz.eeckhout.smartcity.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import xyz.eeckhout.smartcity.R;

//implements SharedPreferences.OnSharedPreferenceChangeListener
public class MapSettingFragment extends PreferenceFragmentCompat {
    private static final String TAG = "error";

    SharedPreferences sharedPreferences;

    public MapSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.map_preference, rootKey);
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //onSharedPreferenceChanged(sharedPreferences, getString(R.string.movies_categories_key));
    }

    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
//        getPreferenceScreen().getSharedPreferences()
//                .registerOnSharedPreferenceChangeListener(this);
    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Preference preference = findPreference(key);
//        if (preference instanceof ListPreference) {
//            ListPreference listPreference = (ListPreference) preference;
//            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
//            if (prefIndex >= 0) {
//                preference.setSummary(listPreference.getEntries()[prefIndex]);
//            }
//        } else {
//            preference.setSummary(sharedPreferences.getString(key, ""));
//
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
//        //unregister the preference change listener
//        getPreferenceScreen().getSharedPreferences()
//                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
