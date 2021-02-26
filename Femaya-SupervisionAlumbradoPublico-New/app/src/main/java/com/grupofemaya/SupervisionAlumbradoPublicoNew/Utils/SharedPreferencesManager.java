package com.grupofemaya.SupervisionAlumbradoPublicoNew.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static SharedPreferencesManager sInstance;
    private final SharedPreferences mPref;

    private SharedPreferencesManager(Context context) {
        mPref = context.getSharedPreferences(Constantes.PREFERENCES, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesManager(context);
        }
    }

    public static synchronized SharedPreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(SharedPreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setIdUser(String value) {
        mPref.edit()
                .putString(Constantes.PREF_ID_USER, value)
                .apply();
    }

    public String getIdUser() {
        return mPref.getString(Constantes.PREF_ID_USER, "0");
    }

    public void setUserName(String value) {
        mPref.edit()
                .putString(Constantes.PREF_NAME_USER, value)
                .apply();
    }

    public String getUserName() {
        return mPref.getString(Constantes.PREF_NAME_USER, "");
    }

    public void setTurno(String value) {
        mPref.edit()
                .putString(Constantes.PREF_TURNO, value)
                .apply();
    }

    public String getTurno() {
        return mPref.getString(Constantes.PREF_TURNO, "");
    }

    public void setCheckIn(String value) {
        mPref.edit()
                .putString(Constantes.CHECK_IN, value)
                .apply();
    }

    public String getCheckIn() {
        return mPref.getString(Constantes.CHECK_IN, null);
    }

    public void setCheckOut(String value) {
        mPref.edit()
                .putString(Constantes.CHECK_OUT, value)
                .apply();
    }

    public String getCheckOut() {
        return mPref.getString(Constantes.CHECK_OUT, null);
    }

    public void setVisibleReport(boolean value) {
        mPref.edit()
                .putBoolean(Constantes.VISIBLE_REPORT, value)
                .apply();
    }

    public boolean getVisibleReport() {
        return mPref.getBoolean(Constantes.VISIBLE_REPORT, false);
    }



}
