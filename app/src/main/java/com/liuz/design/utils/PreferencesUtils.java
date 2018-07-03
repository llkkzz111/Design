package com.liuz.design.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Han on 2016/3/10.
 */
public class PreferencesUtils {
    public static String PREFERENCE_NAME = "lotus_preferences";


    /**
     * 读取username
     *
     * @return
     */
    public static String getUserName() {
        return getString(SharedPreferencesKeys.USER_NAME_KEY);
    }

    /**
     * 设置username
     *
     * @param username
     * @return
     */
    public static boolean setUsername(String username) {
        return putString(SharedPreferencesKeys.USER_NAME_KEY, username);
    }

    /**
     * 读取Access_token
     *
     * @return
     */
    public static String getAccessToken() {
        return getString(SharedPreferencesKeys.ACCESS_TOKEN_KEY);
    }

    /**
     * 设置AccessToken
     *
     * @param AccessToen
     * @return
     */
    public static boolean setAccessToken(String AccessToen) {
        return putString(SharedPreferencesKeys.ACCESS_TOKEN_KEY, AccessToen);
    }

    /**
     * 读取location_Id
     *
     * @return
     */
    public static int getLocationID() {
        return getInt(SharedPreferencesKeys.LOCATION_ID);
    }

    /**
     * 设置location_Id
     *
     * @param locationId
     * @return
     */
    public static boolean setLocationID(int locationId) {
        return putInt(SharedPreferencesKeys.LOCATION_ID, locationId);
    }


    /**
     * 读取TokenType
     *
     * @return
     */
    public static String getCurrentVersion() {
        return getString(SharedPreferencesKeys.CURRENT_VERSION);
    }

    /**
     * 设置 TokenType
     *
     * @param version
     * @return
     */
    public static boolean setCurrentVersion(String version) {
        return putString(SharedPreferencesKeys.CURRENT_VERSION, version);
    }


    /**
     * 读取登录状态
     *
     * @return
     */
    public static boolean getLoginState() {
        return getBoolean(SharedPreferencesKeys.IS_LOGIN);
    }

    /**
     * 设置登录状态
     *
     * @param IsLogin
     * @return
     */
    public static boolean setLoginState(boolean IsLogin) {
        return putBoolean(SharedPreferencesKeys.IS_LOGIN, IsLogin);
    }

    /**
     * 读取第一次访问App状态
     *
     * @return
     */
    public static boolean getFirstVisitState() {
        return getBoolean(SharedPreferencesKeys.IS_FIRST, true);
    }

    /**
     * 设置第一次访问APP状态
     *
     * @param IsFirst
     * @return
     */
    public static boolean setFirstVisitState(boolean IsFirst) {
        return putBoolean(SharedPreferencesKeys.IS_FIRST, IsFirst);
    }


    /**
     * 读取第一次访问App状态
     *
     * @return
     */
    public static String getIsIngoreVersion() {
        return getString(SharedPreferencesKeys.INGORE_VERSION);
    }

    /**
     * 设置第一次访问APP状态
     *
     * @param IngoreVersion
     * @return
     */
    public static boolean setIsingoreVersion(String IngoreVersion) {
        return putString(SharedPreferencesKeys.INGORE_VERSION, IngoreVersion);
    }

    /**
     * putListener string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(String key, String value) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(String, String)
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * get string preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * putListener int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(String key, int value) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt ( String, int)
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * get int preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * putListener long preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(String key, long value) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong ( String, long)
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * get long preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * putListener float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(String key, float value) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat ( String, float)
     */
    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * get float preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * putListener boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = UIManager.getInstance().getBaseContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
