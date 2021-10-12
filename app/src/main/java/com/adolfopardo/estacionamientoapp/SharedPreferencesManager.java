package com.adolfopardo.estacionamientoapp;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared Preferences Manager custom class
 */
public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "AE_PERU_HARMONYOS_APP_SETTINGS";

    private SharedPreferencesManager() {

    }

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Set string value
     *
     * @param key
     * @param value
     */
    public static void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Set boolean value
     *
     * @param key
     * @param value
     */
    public static void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get string value
     *
     * @param key
     * @return
     */
    public static String getStringValue(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * get boolean value
     *
     * @param key
     * @return
     */
    public static boolean getBooleanValue(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }
}

