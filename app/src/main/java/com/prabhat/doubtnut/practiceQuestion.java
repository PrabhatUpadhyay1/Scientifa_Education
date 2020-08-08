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
import android.view.TextureView;
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

public class practiceQuestion extends AppCompatActivity {

    RecyclerView recyclerView;
    chapterAdapter adapter;
    List<String> list;
    Spinner category, difficulty, subject;
    ArrayAdapter arrayAdapter;
    DrawerLayout drawerLayout;
    String s, s1, s2, s3;
    String modeOfLogin;
    String course;
    TextView searchView;
    CircleImageView profileImage;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_question);

        category = findViewById(R.id.spinner);
        difficulty = findViewById(R.id.spinner2);
        subject = findViewById(R.id.spinner3);

        drawerLayout=findViewById(R.id.drawer_layout);
        searchView = findViewById(R.id.search);
        profileImage = findViewById(R.id.profile_image);
        s = getIntent().getStringExtra("category");
        s1 = getIntent().getStringExtra("subject");
        modeOfLogin=getIntent().getStringExtra("modeOfLogin");

        list = new ArrayList<>();


        recyclerView=findViewById(R.id.recycler_view);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Difficultylevel, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String diffi=parent.getItemAtPosition(position).toString();
                adapter = new chapterAdapter(s,s2,diffi,s1,modeOfLogin,list, practiceQuestion.this);
                recyclerView = findViewById(R.id.recycler_view);
                getQuestion(s,diffi,s1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Intent intent2=new Intent(practiceQuestion.this,dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        Toast.makeText(practiceQuestion.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent=new Intent(practiceQuestion.this,savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(practiceQuestion.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(practiceQuestion.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(practiceQuestion.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(practiceQuestion.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(practiceQuestion.this,setting_menu.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(practiceQuestion.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();
        updateUavigation();

        TextView searchView=findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(practiceQuestion.this, Search_Screen.class));
            }
        });

    }

    public void getQuestion(String c,String diffi,String sub) {
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(c).document(diffi).collection(sub).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot snapshot = documentChange.getDocument();
                    String Id = snapshot.getId();
                    list.add(Id);
                }
                recyclerView.setAdapter(adapter);
            }
        });

    }

    public  void updateUavigation(){
        navigationView=findViewById(R.id.navigation_view);
        View headerView=navigationView.getHeaderView(0);
        final CircleImageView img=headerView.findViewById(R.id.profile_image1);
        final TextView username = headerView.findViewById(R.id.username);
        final TextView editText = headerView.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(practiceQuestion.this,MyBio.class);
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

//
//    public  void updateUavigation(){
//        navigationView=findViewById(R.id.navigation_view);
//        View headerView=navigationView.getHeaderView(0);
//        final CircleImageView img=headerView.findViewById(R.id.profile_image1);
//        final TextView username = headerView.findViewById(R.id.username);
//        final TextView editText = headerView.findViewById(R.id.edit_text);
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Practices.this,MyBio.class);
//                intent.putExtra("modeOfLoging",modeOfLogin);
//                startActivity(intent);
//            }
//        });
//
//        FirebaseFirestore firestore;
//        firestore=FirebaseFirestore.getInstance();
//        firestore.collection("User").document(modeOfLogin).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(img);
//                username.setText(documentSnapshot.getString("Name"));
//                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(profileImage);
//
//            }
//        });
}