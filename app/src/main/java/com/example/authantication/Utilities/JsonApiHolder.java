package com.example.authantication.Utilities;

import com.example.authantication.models.Register;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonApiHolder {


    @POST("createuser")
    Call<Register> createUser(@Body Register register);
}
