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
import com.example.authantication.models.Register;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends Fragment implements View.OnClickListener {

    private EditText mName, mEmail, mPassword;
    private Button mSignUp;
    private TextView mToLogin;

    private JsonApiHolder jsonApiHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_signup, container, false);

        init(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://127.0.0.1:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApiHolder = retrofit.create(JsonApiHolder.class);

        return view;
    }

    private void init(View view)
    {
        mName = view.findViewById(R.id.signup_name);
        mEmail = view.findViewById(R.id.signup_email);
        mPassword = view.findViewById(R.id.signup_password);
        mSignUp = view.findViewById(R.id.signup_btn);
        mToLogin = view.findViewById(R.id.signup_to_login);

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

    private void createUser(Register register)
    {
        Map<String, String> fields = new HashMap<>();
        fields.put("name", register.getName());
        fields.put("email", register.getEmail());
        fields.put("password", register.getPassword());

        Call<Register> registerCall = jsonApiHolder.createUser(fields);

        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Error Code : " + response.code() , Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "Account Created Successfuly", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadFragment(new Login());
                    }
                }, 200);


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
