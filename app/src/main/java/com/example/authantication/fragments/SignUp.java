package com.example.authantication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.authantication.R;
import com.example.authantication.Utilities.JsonApiHolder;
import com.example.authantication.Utilities.RetSngleTon;
import com.example.authantication.Utilities.UtilsSSL;
import com.example.authantication.models.Register;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends Fragment implements View.OnClickListener {

    private static final String TAG = "SignUp";

    private EditText mName, mEmail, mPassword;
    private Button mSignUp;
    private TextView mToLogin;
    private ProgressBar mProgress;

    private JsonApiHolder jsonApiHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_signup, container, false);

        init(view);

        // Create a new object from HttpLoggingInterceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add Interceptor to HttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();



        jsonApiHolder = RetSngleTon.getInstance();

        return view;
    }

    private void init(View view)
    {
        mName = view.findViewById(R.id.signup_name);
        mEmail = view.findViewById(R.id.signup_email);
        mPassword = view.findViewById(R.id.signup_password);
        mSignUp = view.findViewById(R.id.signup_btn);
        mToLogin = view.findViewById(R.id.signup_to_login);
        mProgress = view.findViewById(R.id.signup_progress);

        mToLogin.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.signup_to_login:
                //
                loadFragment(new Login());
                break;
            case R.id.signup_btn:
                //
                loadInfo();
                break;
        }
    }

    private void loadInfo()
    {
        String name, email , password;

        name = mName.getText().toString();
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (name.isEmpty())
            return;
        if (email.isEmpty())
            return;
        if (password.isEmpty())
            return;

        Register register = new Register(name, email, password);
        createUser(register);
    }

    private void createUser(Register user)
    {

        setmProgress(true);

        Call<Register> stringCall = jsonApiHolder.checkEmail(user.getEmail());

        final Call<Register> registerCall = jsonApiHolder.createUser(user);

        stringCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response)
            {
                if (!response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: Response is not successful");
                    setmProgress(false);
                    return;
                }

                Register user = response.body();
                Log.d(TAG, "onResponse: USER : " + user);
                assert user != null;
                if (user.getEmail() != null)
                {
                    setmProgress(false);
                    Toast.makeText(getContext(), "Email is Already Register", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create User
                registerCall.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (!response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Error Code : " + response.code() , Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Code : " + response.code());
                            setmProgress(false);
                            return;
                        }

                        Toast.makeText(getContext(), "Account Created Successfuly", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, " RESPONSE " + response.body());

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setmProgress(false);
                                loadFragment(new Login());
                            }
                        }, 200);


                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {

                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        setmProgress(false);
                    }
                });


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getContext(), "Check Your Network", Toast.LENGTH_SHORT).show();
                setmProgress(false);
            }
        });




    }

    private boolean isAvailable(Register user){
        final boolean[] value = {false};


        return value[0];
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


    private void setmProgress(boolean x)
    {
        if (x)
            mProgress.setVisibility(View.VISIBLE);
        else
            mProgress.setVisibility(View.GONE);
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.d(TAG, "checkClientTrusted: " + chain);
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.d(TAG, "checkServerTrusted: " + chain);
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
