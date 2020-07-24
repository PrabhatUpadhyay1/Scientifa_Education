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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.chapterAdapter;

import java.util.ArrayList;
import java.util.List;

public class Practices extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

    chapterAdapter adapter;
    List<String> list;
    Spinner category, difficulty, subject;
    ArrayAdapter arrayAdapter, arrayAdapter2, arrayAdapter3;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices);
        drawerLayout =findViewById(R.id.drawer_layout);
        category = findViewById(R.id.spinner);
        difficulty = findViewById(R.id.spinner2);
        subject = findViewById(R.id.spinner3);

        list = new ArrayList<>();

        adapter = new chapterAdapter(list, this);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

//        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.All_Categories, android.R.layout.simple_spinner_item);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        category.setAdapter(arrayAdapter);

        arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.Difficultylevel, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(arrayAdapter2);

        arrayAdapter3 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(arrayAdapter3);


        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.practices);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(Practices.this, Home.class));
                        overridePendingTransition(0, 0);
                        Toast.makeText(Practices.this, "Home", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.myDoubts:
                        Intent intent = new Intent(Practices.this, MyDoubt.class);
                        startActivity(intent);
                        Toast.makeText(Practices.this, "My Doubt", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cources:
                        startActivity(new Intent(Practices.this, MyCourses.class));
                        overridePendingTransition(0, 0);
                        Toast.makeText(Practices.this, "Favourites", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.practices:
                        Toast.makeText(Practices.this, "Profile", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        NavigationView navigationView;
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(Practices.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                    case R.id.savedpdf:
                        Toast.makeText(Practices.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                    case R.id.history:
                        Toast.makeText(Practices.this, "History", Toast.LENGTH_SHORT).show();
                    case R.id.aboutUs:
                        Toast.makeText(Practices.this, "About", Toast.LENGTH_SHORT).show();
                    case R.id.contactus:
                        Toast.makeText(Practices.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    case R.id.setting:
                        Toast.makeText(Practices.this, "Setting", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getQuestion();
    }

    public void getQuestion() {
    final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Mains").document("Easy").collection("Mathematics").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

}