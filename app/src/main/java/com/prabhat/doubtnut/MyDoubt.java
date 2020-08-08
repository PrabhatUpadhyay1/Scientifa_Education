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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.prabhat.doubtnut.Adapter.myDoubtAdapter;
import com.prabhat.doubtnut.Model.MyDoubtModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyDoubt extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> list,list2;
    myDoubtAdapter adapter;

    FirebaseFirestore firestore;

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView profileImage;
    String modeOfLogin;
    TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doubt);

        profileImage = findViewById(R.id.profile_image);
        firestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        list2 = new ArrayList<>();

        modeOfLogin = getIntent().getStringExtra("modeOfLogin");

        adapter = new myDoubtAdapter(list,list2, this);


        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyDoubt.this, Search_Screen.class));
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
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
                        Intent intent = new Intent(MyDoubt.this, Home.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.myDoubts:
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cources:
                        Intent intent1 = new Intent(MyDoubt.this, MyCourses.class);
                        intent1.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.practices:
                        Intent intent2 = new Intent(MyDoubt.this, Practices.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
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
                        Intent intent2=new Intent(MyDoubt.this,dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        Toast.makeText(MyDoubt.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent=new Intent(MyDoubt.this,savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(MyDoubt.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(MyDoubt.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(MyDoubt.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(MyDoubt.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MyDoubt.this,setting_menu.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(MyDoubt.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();


        updateUavigation();

        getMyDoubt();

     }

    public void getMyDoubt() {
        firestore.collection("User").document(modeOfLogin).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                // for (DocumentChange documentChange : value.getString().getDocumentChanges()) {
                MyDoubtModel model = value.toObject(MyDoubtModel.class);

                List<String> l2 = model.getDoubtText();
                if (!l2.isEmpty()) {
                    for (String s2 : l2) {
                        Log.i("ssss", s2);
                        list.add(s2);
                    }
                }

                List<String> l3 = model.getDoubtPhoto();
                if (!l2.isEmpty()) {
                    for (String s2 : l3) {
                        Log.i("ssss", s2);
                        list2.add(s2);
                    }
                }
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void updateUavigation() {
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        final CircleImageView img = headerView.findViewById(R.id.profile_image1);
        final TextView username = headerView.findViewById(R.id.username);
        final TextView editText = headerView.findViewById(R.id.edit_text);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDoubt.this, MyBio.class);
                intent.putExtra("modeOfLoging", modeOfLogin);
                startActivity(intent);
            }
        });

        firestore.collection("User").document(modeOfLogin).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(img);
                username.setText(documentSnapshot.getString("Name"));
                Log.i("imgae", documentSnapshot.getString("ImageUri") + "");
                Log.i("Tag1", modeOfLogin);
                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(profileImage);

            }
        });
    }
}
