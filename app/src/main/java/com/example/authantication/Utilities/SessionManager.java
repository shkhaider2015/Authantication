package com.example.authantication.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.authantication.R;


public class SessionManager {

    private Context mCTX;

    public SessionManager(Context mCTX) {
        this.mCTX = mCTX;
    }

    public void setUserToken(String token)
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = mCTX.getSharedPreferences(mCTX.getString(R.string.app_name), mCTX.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public String getUserToken()
    {
        SharedPreferences sharedPreferences;
        sharedPreferences = mCTX.getSharedPreferences(mCTX.getString(R.string.app_name), mCTX.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");

        return token;
    }


}
