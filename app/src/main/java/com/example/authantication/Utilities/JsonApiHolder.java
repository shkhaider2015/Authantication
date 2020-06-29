package com.example.authantication.Utilities;

import com.example.authantication.models.Register;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiHolder {


    @POST("createuser")
    Call<Register> createUser(@Body Register register);

    @GET("checkemail/{email}")
    Call<Register> checkEmail(@Path("email") String email);

    @GET("login/{email}+{password}")
    Call<Register> login(
            @Path("email") String email,
            @Path("password") String Password
                         );

    @GET("index")
    Call<String> index();
}
