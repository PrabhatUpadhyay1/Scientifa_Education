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
import com.prabhat.doubtnut.Adapter.reccomendedAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;

import java.util.ArrayList;
import java.util.List;

public class videoView extends AppCompatActivity {
    //    MKPlayer mkplayer;
    VideoView videoView;
    TextView likes;
    reccomendedAdapter adapter;
    RecyclerView recyclerView;
    List<Maths_Model> list;
    ImageView fullScreen;
    Button DownloadPdf;
    TextView tittle;

    List<String> list1,list2,list3,list4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        list1=new ArrayList<>();
        list2=new ArrayList<>();
        list3=new ArrayList<>();
        list4=new ArrayList<>();
        //reccomnded
        list1=getIntent().getStringArrayListExtra("list");
        list2=getIntent().getStringArrayListExtra("list2");
        list3=getIntent().getStringArrayListExtra("list3");
        list4=getIntent().getStringArrayListExtra("list4");

        recyclerView=findViewById(R.id.recycler_view);

        adapter = new reccomendedAdapter(list1, list2, list3, list4, this);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);



        videoView = findViewById(R.id.video_view);
        final String Link = getIntent().getStringExtra("Link");
        final String PDF = getIntent().getStringExtra("PDF");
        String Tittle = getIntent().getStringExtra("Video Tittle");
        list = new ArrayList<>();
        DownloadPdf = findViewById(R.id.pdf);
        fullScreen = findViewById(R.id.full_screen_icon);
        tittle=findViewById(R.id.tittle);
        tittle.setText(Tittle);
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


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);


        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(Link));
        videoView.start();

        mediaController.isShowing();
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
