package com.sinifdefterimpro.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveSharedPreferences {

    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private String userToken = "userToken";
    private String GCMRegKey = "GCMRegKey";

    private String isIconCreated = "isIconCreated";

    private String cookieData = "cookieData";

    private String isLogin = "isLogin";

    private String syllabus = "syllabus";

    private String userId = "userId";
    private String userName = "userName";
    private String userEmail = "userEmail";


    public SaveSharedPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getUserToken() {
        return appSharedPrefs.getString(userToken, "unknown");
    }
    public void setUserToken(String tokenValue) {
        prefsEditor.putString(userToken, tokenValue).commit();
    }

    public String getGCMRegKey() {
        return appSharedPrefs.getString(GCMRegKey, "");
    }
    public void setGCMRegKey(String newGCMRegKey) {
        prefsEditor.putString(GCMRegKey, newGCMRegKey).commit();
    }

    public String getCookieData() {
        return appSharedPrefs.getString(cookieData, "");
    }
    public void setCookieData(String s) {
        prefsEditor.putString(cookieData, s).commit();
    }

    public boolean getIsLogin() {
        return appSharedPrefs.getBoolean(isLogin, false);
    }
    public void setIsLogin(boolean b) {
        prefsEditor.putBoolean(isLogin, b).commit();
    }

    public boolean getIsIconCreated() {
        return appSharedPrefs.getBoolean(isIconCreated, false);
    }
    public void setIsIconCreated(boolean b) {
        prefsEditor.putBoolean(isIconCreated, b).commit();
    }

    public String getSyllabus() {
        return appSharedPrefs.getString(syllabus, "[" +
                "{" +
                "id: -1," +
                "name: \"Empty1\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty2\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty3\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty4\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty5\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty6\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "},{"+
                "id: -1," +
                "name: \"Empty7\"," +
                "date: \"24.07.2017 10:00 - 11:00\"," +
                "update_date: \"24.07.2017 10:00 - 11:00\"" +
                "}]");
    }
    public void setSyllabus(String s) {
        prefsEditor.putString(syllabus, s).commit();
    }

    public String getUserId() {
        return appSharedPrefs.getString(userId, "0");
    }
    public void setUserId(String s) {
        prefsEditor.putString(userId, s).commit();
    }

    public String getUserName() {
        return appSharedPrefs.getString(userName, "Misafir");
    }
    public void setUserName(String s) {
        prefsEditor.putString(userName, s).commit();
    }

    public String getUserEmail() {
        return appSharedPrefs.getString(userEmail, "Misafir");
    }
    public void setUserEmail(String s) {
        prefsEditor.putString(userEmail, s).commit();
    }
}