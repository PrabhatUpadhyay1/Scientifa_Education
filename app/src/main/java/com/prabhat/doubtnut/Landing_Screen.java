package com.prabhat.doubtnut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.prabhat.doubtnut.Login_Details.Login_Page;

public class Landing_Screen extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);

        logo = findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Landing_Screen.this, Login_Page.class);

                Pair[] pair = new Pair[1];

                pair[0] = new Pair<View, String>(logo, "logo_transition");

                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(Landing_Screen.this, pair);
                startActivity(intent, option.toBundle());
//                ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(Landing_Screen.this,logo, ViewCompat.getTransitionName(logo));
//                startActivity(intent, optionsCompat.toBundle());
                finish();
            }
        }, 5000);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}