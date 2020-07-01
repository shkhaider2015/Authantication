package com.example.authantication.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authantication.R;
import com.example.authantication.Utilities.JsonApiHolder;
import com.example.authantication.Utilities.RetSngleTon;
import com.example.authantication.Utilities.UtilsSSL;
import com.example.authantication.models.Register;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHome extends AppCompatActivity implements View.OnClickListener {

    private Button btnGET, btnPOST, btnSEND_FILE;
    private TextView mTextView;
    private JsonApiHolder jsonApiHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_home);
        init();
        accessIndex();
    }

    private void init()
    {
        btnGET = findViewById(R.id.getdata);
        btnPOST = findViewById(R.id.postdata);
        btnSEND_FILE = findViewById(R.id.sendFile);
        mTextView = findViewById(R.id.R_Home_textView);

        btnGET.setOnClickListener(this);
        btnPOST.setOnClickListener(this);
        btnSEND_FILE.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.getdata:
                //
                break;
            case R.id.postdata:
                //
                break;
            case R.id.sendFile:
                //
                break;
        }
    }

    private void accessIndex()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add Interceptor to HttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        jsonApiHolder = RetSngleTon.getInstance();

        Call<String> call = jsonApiHolder.index();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = response.body();
                mTextView.setText(content);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}