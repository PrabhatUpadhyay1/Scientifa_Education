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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.chapterAdapter;
import com.prabhat.doubtnut.Adapter.chapterAdapter1;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class coursesCategory extends AppCompatActivity {

    ArrayList<String> list, list1;
    chapterAdapter1 adapter;
    RecyclerView recyclerView;
    Spinner spinner;
    CircleImageView profileImage;
    String data;
    String category, modeOfLogin;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_category);

        profileImage = findViewById(R.id.profile_image);
        recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        list1 = new ArrayList<>();

        searchView = findViewById(R.id.search);
        spinner = findViewById(R.id.spinner);

        drawerLayout = findViewById(R.id.drawer_layout);
        modeOfLogin = getIntent().getStringExtra("modeOfLogin");

        data = getIntent().getStringExtra("data");

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.All_Categories, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = parent.getItemAtPosition(position).toString();
                Toast.makeText(coursesCategory.this, category, Toast.LENGTH_SHORT).show();
                list.clear();
                adapter = new chapterAdapter1(modeOfLogin,list, category, data, coursesCategory.this);
                getChapters(category, data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coursesCategory.this, Search_Screen.class));
            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(R.menu.header_navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(coursesCategory.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                    case R.id.savedpdf:
                        Toast.makeText(coursesCategory.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                    case R.id.history:
                        Toast.makeText(coursesCategory.this, "History", Toast.LENGTH_SHORT).show();
                    case R.id.aboutUs:
                        Toast.makeText(coursesCategory.this, "About", Toast.LENGTH_SHORT).show();
                    case R.id.contactus:
                        Toast.makeText(coursesCategory.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    case R.id.setting:
                        Toast.makeText(coursesCategory.this, "Setting", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        updateUavigation();

    }

    public void getChapters(final String s, String s2) {
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(s).document("Subjects").collection(s2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot snapshot = documentChange.getDocument();
                    list1.add(s);
                    String chapter = snapshot.getId();
                    list.add(chapter);
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
                Intent intent = new Intent(coursesCategory.this, MyBio.class);
                intent.putExtra("modeOfLogin", modeOfLogin);
                startActivity(intent);
            }
        });

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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