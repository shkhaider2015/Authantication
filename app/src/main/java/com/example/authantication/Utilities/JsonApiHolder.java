package com.example.authantication.Utilities;

import com.example.authantication.models.Register;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonApiHolder {

    @FormUrlEncoded
    @POST("adduser")
    Call<Register> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );
}
