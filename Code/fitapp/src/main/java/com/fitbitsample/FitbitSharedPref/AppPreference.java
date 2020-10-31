package com.fitbitsample.FitbitSharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

/**
 * Application Preference to save the instances of previous authorization
 * using strings and booleans
 */
public class AppPreference {
    private static AppPreference appPreference;
    private SharedPreferences mSecurePrefs;

    private AppPreference(Context context) {
        this.mSecurePrefs = new SecurePreferences(context, "pref", "app_preference.xml");
    }

    public static void init(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreference(context);
        }
    }

    public static synchronized AppPreference getInstance() {
        if (appPreference == null) {
            throw new RuntimeException("Initialize appPreference before using it");
        }
        return appPreference;
    }

    public String getString(String key) {
        return mSecurePrefs.getString(key, null);
    }

    public String getString(String key, String value) {
        return mSecurePrefs.getString(key, value);
    }

    public void putString(String key, String value) {
        mSecurePrefs.edit().putString(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return mSecurePrefs.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return mSecurePrefs.getBoolean(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mSecurePrefs.edit().putBoolean(key, value).apply();
    }

}