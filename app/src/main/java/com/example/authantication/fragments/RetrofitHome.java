package com.example.authantication.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.authantication.R;

public class RetrofitHome extends AppCompatActivity implements View.OnClickListener {

    private Button btnGET, btnPOST, btnSEND_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_home);
    }

    private void init()
    {
        btnGET = findViewById(R.id.getdata);
        btnPOST = findViewById(R.id.postdata);
        btnSEND_FILE = findViewById(R.id.sendFile);

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
}