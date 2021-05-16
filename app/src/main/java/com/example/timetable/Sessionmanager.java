package com.example.timetable;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Sessionmanager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String SESSION_USER = "userLoginSession";
    public static final String KEY_REMEMBER_ME = "rememberMe";

    private static final String IS_LOGIN = "IsLoginUser";
    private static  final String IS_LOGOUT = "IslogoutUser";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_HOTEN = "hoten";
    public static final String KEY_MATKHAU = "matkhau";
    public static final String KEY_SDT = "sodienthoai";
    public static final String KEY_TENDANGNHAP = "tendangnhap";


    //public static final String KEY_DIACHI = "diachi";


    public Sessionmanager(Context _context, String sessionName) {
        context = _context;
        userSession = _context.getSharedPreferences("sessionName", context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String hoten, String tendangnhap, String email, String sodienthoai) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_HOTEN, hoten);
        editor.putString(KEY_SDT, sodienthoai);
        editor.putString(KEY_TENDANGNHAP, tendangnhap);


        editor.commit();

    }

    public HashMap<String, String> getInfomationUser() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_HOTEN, userSession.getString(KEY_HOTEN, null));
        userData.put(KEY_SDT, userSession.getString(KEY_SDT, null));
        userData.put(KEY_TENDANGNHAP, userSession.getString(KEY_TENDANGNHAP, null));


        //userData.put(KEY_HOTEN, userSession.getString(KEY_HOTEN,null));

        return userData;
    }

    public boolean ckecklogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else
            return false;
    }
    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

}
