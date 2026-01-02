package com.example.to_dolist;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sp = context.getSharedPreferences("login_session", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setLogin(boolean status) {
        editor.putBoolean("loggedIn", status);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sp.getBoolean("loggedIn", false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
