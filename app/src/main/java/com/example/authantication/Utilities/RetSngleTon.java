package com.example.authantication.Utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetSngleTon {

    private static JsonApiHolder jsonApiHolder = null;

    public static JsonApiHolder getInstance()
    {
        if (jsonApiHolder != null)
            return jsonApiHolder;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(UtilsSSL.getUnsafeOkHttpClient())
                .build();

        jsonApiHolder = retrofit.create(JsonApiHolder.class);
        return jsonApiHolder;
    }

}
