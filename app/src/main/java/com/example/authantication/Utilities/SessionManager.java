package com.example.authantication.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.authantication.R;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private Context mCTX;

    public SessionManager(Context mCTX) {
        this.mCTX = mCTX;
        this.sharedPreferences = mCTX.getSharedPreferences(mCTX.getString(R.string.app_name), mCTX.MODE_PRIVATE);
    }
}
