package com.prabhat.doubtnut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.recommendedVideoAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;

import java.util.ArrayList;
import java.util.List;

public class videoView extends AppCompatActivity {
    //    MKPlayer mkplayer;
    VideoView videoView;
    TextView likes;
    recommendedVideoAdapter adapter;
    RecyclerView recyclerView;
    List<Maths_Model> list;
    ImageView fullScreen;
    Button DownloadPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        videoView = findViewById(R.id.video_view);
        likes = findViewById(R.id.likes);
        final String Link = getIntent().getStringExtra("Link");
        final String PDF = getIntent().getStringExtra("PDF");
        String Tittle = getIntent().getStringExtra("Video Tittle");
        list = new ArrayList<>();
        DownloadPdf = findViewById(R.id.pdf);
        fullScreen = findViewById(R.id.full_screen_icon);
        adapter = new recommendedVideoAdapter(list, this);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getVideoSolution("Class 12","Mathematics");

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(videoView.this, videoFullScreen.class);
                intent.putExtra("Link", Link);
                startActivity(intent);
            }
        });

        DownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(PDF);
            }
        });


        // int Likes=getIntent().getIntExtra("Likes",0);

        // Log.i("vidoe",Likes+"");

//        likes.setText(Likes+"");


        //setting MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);


//
//        mediaController.show();

        //setting Video View;
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(Link));
        videoView.start();

        mediaController.isShowing();
//        mediaController.hide();
//        mkplayer = new MKPlayer(this);
//        mkplayer.play(Link);
//
//        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
//            @Override
//            public void onNextClick() {
//                //It is the method for next song.It is called when you pressed the next icon
//                //Do according to your requirement
//            }
//
//            @Override
//            public void onPreviousClick() {
//                //It is the method for previous song.It is called when you pressed the previous icon
//                //Do according to your requirement
//            }
//        });
////        MKPlayerActivity.configPlayer(this).play(Link);
//
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoView.stopPlayback();
    }

    public void getVideoSolution(String category, String subject) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(category).document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    list.add(model);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    public void downloadPdf(String url) {
        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }
    public void classSelect(View view){

//        RadioButton radioButton=new RadioButton(this);
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        Log.i("kkkk",isSelected+"");
    }
}
