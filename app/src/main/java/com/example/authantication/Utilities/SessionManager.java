package com.example.authantication.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.authantication.R;


public class SessionManager {

//    private Context mCTX;
//
//    public SessionManager(Context mCTX) {
//        this.mCTX = mCTX;
//    }

    public static void setUserToken(String token, Context mCTX)
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = mCTX.getSharedPreferences(mCTX.getString(R.string.app_name), mCTX.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public static String getUserToken(Context mCTX)
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = mCTX.getSharedPreferences(mCTX.getString(R.string.app_name), mCTX.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");

        return token;
    }


}
