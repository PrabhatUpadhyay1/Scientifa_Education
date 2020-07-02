package com.prabhat.doubtnut;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoFullScreen extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String Link = getIntent().getStringExtra("Link");
        videoView=findViewById(R.id.video_view);
        //setting MediaController
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);

//        mediaController.show();

        //setting Video View;
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(Link));
        videoView.start();

    }
}