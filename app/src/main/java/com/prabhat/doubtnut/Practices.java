package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.chapterAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Practices extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    String category,subject;
    String modeOfLogin;
    NavigationView navigationView;
    TextView searchView;
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices);
        drawerLayout =findViewById(R.id.drawer_layout);

        searchView = findViewById(R.id.search);
        profileImage = findViewById(R.id.profile_image);
        modeOfLogin = getIntent().getStringExtra("modeOfLogin");


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Practices.this, Search_Screen.class));
            }
        });


        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.practices);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent=new Intent(Practices.this, Home.class);
                        overridePendingTransition(0, 0);
                        intent.putExtra("modeOfLogin",modeOfLogin);
                        startActivity(intent);
                        return true;
                    case R.id.myDoubts:
                        Intent intent1 = new Intent(Practices.this, MyDoubt.class);
                        intent1.putExtra("modeOfLogin",modeOfLogin);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cources:
                        Intent intent2=new Intent(Practices.this, MyCourses.class);
                        intent2.putExtra("modeOfLogin",modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.practices:
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        updateUavigation();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Intent intent2=new Intent(Practices.this,dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        Toast.makeText(Practices.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent=new Intent(Practices.this,savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Practices.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Practices.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Practices.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Practices.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(Practices.this,setting_menu.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Practices.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();

//        String s=category.getSelectedItem().toString();
//        String s1=difficulty.getSelectedItem().toString();
//        String s2=  subject.getSelectedItem().toString();

//        Log.i("Tag1",s1);
//        Log.i("Tag1",s2);
//        getQuestion("JEE Mains",s1,s2);

    }

//    public void getQuestion(String s,String s2, String s3) {
//    final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        firestore.collection(s).document(s2).collection(s3).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
//                    DocumentSnapshot snapshot = documentChange.getDocument();
//                    String Id = snapshot.getId();
//                    list.add(Id);
//                }
//                recyclerView.setAdapter(adapter);
//            }
//        });
//
//    }

    public  void updateUavigation(){
        navigationView=findViewById(R.id.navigation_view);
        View headerView=navigationView.getHeaderView(0);
        final CircleImageView img=headerView.findViewById(R.id.profile_image1);
        final TextView username = headerView.findViewById(R.id.username);
        final TextView editText = headerView.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Practices.this,MyBio.class);
                intent.putExtra("modeOfLogin",modeOfLogin);
                startActivity(intent);
            }
        });

        FirebaseFirestore firestore;
        firestore=FirebaseFirestore.getInstance();
        firestore.collection("User").document(modeOfLogin).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(img);
                username.setText(documentSnapshot.getString("Name"));
                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(profileImage);

            }
        });
    }

    public void openClass11(View v){
        switch ((v.getId())){
            case R.id.elevel:
                subject="Mathematics";
                break;

            case R.id.elevel1:
                subject="Chemistry";
                break;

            case R.id.elevel2:
                subject="Physics";
                break;
        }
        Intent i=new Intent(Practices.this,practiceQuestion.class);
        i.putExtra("category","Class 11");
        i.putExtra("modeOfLogin",modeOfLogin);
        i.putExtra("subject",subject);
        startActivity(i);
    }
    public void openClass12(View v){
        switch ((v.getId())){
            case R.id.twelve:
                subject="Mathematics";
                break;

            case R.id.twelve2:
                subject="Chemistry";
                break;

            case R.id.twelve3:
                subject="Physics";
                break;
        }
        Intent i=new Intent(Practices.this,practiceQuestion.class);
        i.putExtra("category","Class 12");
        i.putExtra("modeOfLogin",modeOfLogin);
        i.putExtra("subject",subject);
        startActivity(i);
    }

    public void openMains(View v){
        switch ((v.getId())){
            case R.id.jee:
                subject="Mathematics";
                break;

            case R.id.jee1:
                subject="Chemistry";
                break;

            case R.id.jee2:
                subject="Physics";
                break;
        }
        Intent i=new Intent(Practices.this,practiceQuestion.class);
        i.putExtra("category","JEE Mains");
        i.putExtra("modeOfLogin",modeOfLogin);
        i.putExtra("subject",subject);
        startActivity(i);
    }

    public void openAdvance(View v){
        switch ((v.getId())){
            case R.id.advance:
                subject="Mathematics";
                break;

            case R.id.advance1:
                subject="Chemistry";
                break;

            case R.id.advance2:
                subject="Physics";
                break;
        }
        Intent i=new Intent(Practices.this,practiceQuestion.class);
        i.putExtra("category","JEE Advance");
        i.putExtra("modeOfLogin",modeOfLogin);
        i.putExtra("subject",subject);
        startActivity(i);
    }
}