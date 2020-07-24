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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.myDoubtAdapter;
import com.prabhat.doubtnut.Model.MyDoubtModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDoubt extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MyDoubtModel> list;
    myDoubtAdapter adapter;

    FirebaseFirestore firestore;

    BottomNavigationView bottomNavigationView;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doubt);

        profileImage=findViewById(R.id.profile_image);
        firestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        adapter = new myDoubtAdapter(list, this);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getMyDoubt();


        drawerLayout=findViewById(R.id.drawer_layout);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.myDoubts);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(MyDoubt.this,Home.class));
                        Toast.makeText(MyDoubt.this, "Home", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myDoubts:
                        Toast.makeText(MyDoubt.this, "My Doubt", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cources:
                        startActivity(new Intent(MyDoubt.this,MyCourses.class));
                        Toast.makeText(MyDoubt.this, "Favourites", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.practices:
                        startActivity(new Intent(MyDoubt.this,Practices.class));
                        Toast.makeText(MyDoubt.this, "Profile", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(MyDoubt.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        return true;
                    case R.id.savedpdf:
                        Toast.makeText(MyDoubt.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.history:
                        Toast.makeText(MyDoubt.this, "History", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.aboutUs:
                        Toast.makeText(MyDoubt.this, "About", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.contactus:
                        Toast.makeText(MyDoubt.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.setting:
                        Toast.makeText(MyDoubt.this, "Setting", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    public void getMyDoubt() {
        firestore.collection("MyDoubt").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    MyDoubtModel model = documentChange.getDocument().toObject(MyDoubtModel.class);
                    list.add(model);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

}
