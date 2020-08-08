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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.prabhat.doubtnut.Adapter.savedPdfAdapter;
import com.prabhat.doubtnut.Model.MyDoubtModel;
import com.prabhat.doubtnut.Model.savedPdfModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class savedPPdf extends AppCompatActivity {

    savedPdfAdapter adapter;
    List<String> list,list2;
    RecyclerView recyclerView;
    String modeOfLogin;
    NavigationView navigationView;
    CircleImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_p_pdf);


        list=new ArrayList<>();
        list2=new ArrayList<>();

        modeOfLogin=getIntent().getStringExtra("modeOfLogin");
        recyclerView=findViewById(R.id.recycler_view);

        adapter=new savedPdfAdapter(modeOfLogin,list,list2,this);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        getSavedPdf();


        final DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);


        profileImage = findViewById(R.id.profile_image);
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
                        Intent intent2 = new Intent(savedPPdf.this, dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        Toast.makeText(savedPPdf.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent = new Intent(savedPPdf.this, savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(savedPPdf.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(savedPPdf.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(savedPPdf.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(savedPPdf.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(savedPPdf.this, setting_menu.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        finish();
                        Toast.makeText(savedPPdf.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();

        updateUavigation();
        TextureView searchView=findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(savedPPdf.this, Search_Screen.class));
            }
        });

    }
    public void getSavedPdf() {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("User").document(modeOfLogin).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                // for (DocumentChange documentChange : value.getString().getDocumentChanges()) {
                savedPdfModel model = value.toObject(savedPdfModel.class);

                List<String> l = model.getPdfTittle();
                if (!l.isEmpty()) {
                    for (String s : l) {
                        Log.i("ssss", s);
                        list.add(s);
                    }
                }
                List<String> l2 = model.getSavedPdf();
                if (!l2.isEmpty()) {
                    for (String s2 : l2) {
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
                Intent intent = new Intent(savedPPdf.this, MyBio.class);
                intent.putExtra("modeOfLoging", modeOfLogin);
                startActivity(intent);
            }
        });

        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
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