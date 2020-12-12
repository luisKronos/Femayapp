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

}
