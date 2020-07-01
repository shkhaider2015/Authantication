package com.example.authantication.Utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetSngleTon {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance()
    {
        if (retrofit != null)
            return retrofit;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(UtilsSSL.getUnsafeOkHttpClient())
                .build();

        return retrofit;
    }

}
