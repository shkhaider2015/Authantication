package com.example.authantication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.authantication.MainActivity;
import com.example.authantication.R;
import com.example.authantication.Utilities.BasicAuthInterceptor;
import com.example.authantication.Utilities.JsonApiHolder;
import com.example.authantication.Utilities.UtilsSSL;
import com.example.authantication.models.Register;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class Login extends Fragment implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mToSignUp;
    private ProgressBar mProgress;
    private JsonApiHolder jsonApiHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        // Create a new object from HttpLoggingInterceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add Interceptor to HttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(UtilsSSL.getUnsafeOkHttpClient())
                .build();

        jsonApiHolder = retrofit.create(JsonApiHolder.class);

        return view;
    }
    private void init(View view)
    {
        mEmail = view.findViewById(R.id.login_email);
        mPassword = view.findViewById(R.id.login_password);
        mLogin = view.findViewById(R.id.login_btn);
        mToSignUp = view.findViewById(R.id.login_to_signup);
        mProgress = view.findViewById(R.id.login_progress);

        mLogin.setOnClickListener(this);
        mToSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_to_signup:
                //
                loadFragment(new SignUp());
                break;
            case R.id.login_btn:
                //
                setmProgress(true);
                login();
                break;
        }
    }

    private void login()
    {
        String email, password;
//        String baseURL = "https://10.0.2.2:5000/";

        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (email.isEmpty())
        {
            setmProgress(false);
            return;
        }
        if (password.isEmpty())
        {
            setmProgress(false);
            return;
        }

//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor(email, password))
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        jsonApiHolder = retrofit.create(JsonApiHolder.class);

        Call<Register> login = jsonApiHolder.login(email, password);
        login.enqueue(new Callback<Register>() {

            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (!response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: ERROR CODE : " + response.code());
                }

                Toast.makeText(getContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), RetrofitHome.class));
                Objects.requireNonNull(getActivity()).finish();
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t)
            {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        setmProgress(false);

    }

    private void setmProgress(boolean x)
    {
        if (x)
            mProgress.setVisibility(View.VISIBLE);
        else
            mProgress.setVisibility(View.GONE);
    }
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
         FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        assert fm != null;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit(); // save the changes
    }
}
