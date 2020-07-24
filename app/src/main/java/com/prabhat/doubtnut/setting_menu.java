package com.prabhat.doubtnut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class setting_menu extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
        auth=FirebaseAuth.getInstance();
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.terms).setOnClickListener(this);
        findViewById(R.id.privacy).setOnClickListener(this);
        findViewById(R.id.contact).setOnClickListener(this);
        findViewById(R.id.about_us).setOnClickListener(this);
        findViewById(R.id.rate_us).setOnClickListener(this);
        findViewById(R.id.infite_friend).setOnClickListener(this);
        findViewById(R.id.how_to_use).setOnClickListener(this);
        findViewById(R.id.notification_seting).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                auth.signOut();
            case R.id.terms:
                //terms and condition
            case R.id.privacy:
                //privacy policy
            case R.id.contact:
                //contact details
            case R.id.about_us:
                //about us
            case R.id.rate_us:
                //rate us
            case R.id.infite_friend:
                //invite friends
            case R.id.how_to_use:
                // how to use
            case R.id.notification_seting:
                //notification setting
        }
    }
}