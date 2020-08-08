package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
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
import com.prabhat.doubtnut.Adapter.pdfSolutionAdapter;
import com.prabhat.doubtnut.Adapter.savedPdfAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class pdfseeAllView extends AppCompatActivity {


    savedPdfAdapter Adapter;
    ArrayList<String> pdfTittle, pdfLink;
    RecyclerView recyclerView;
    NavigationView navigationView;
    CircleImageView profileImage;
    String modeOfLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfsee_all_view);


        String clicked=getIntent().getStringExtra("clicked");

        pdfTittle = new ArrayList<>();
        pdfLink = new ArrayList<>();

        modeOfLogin=getIntent().getStringExtra("modeOfLogin");
        Adapter = new savedPdfAdapter(modeOfLogin,pdfTittle, pdfLink, this);

        recyclerView = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        getPdfSolution("Class 11", "Mathematics");
        getPdfSolution("Class 11", "Chemistry");
        getPdfSolution("Class 11", "Physics");


        getPdfSolution("Class 12", "Mathematics");
        getPdfSolution("Class 12", "Chemistry");
        getPdfSolution("Class 12", "Physics");

        getPdfSolution("JEE Mains", "Mathematics");
        getPdfSolution("JEE Mains", "Chemistry");
        getPdfSolution("JEE Mains", "Physics");

        getPdfSolution("JEE Advance", "Mathematics");
        getPdfSolution("JEE Advance", "Chemistry");
        getPdfSolution("JEE Advance", "Physics");





        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        profileImage=findViewById(R.id.profile_image);
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
                        Intent intent2 = new Intent(pdfseeAllView.this, dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        Toast.makeText(pdfseeAllView.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent = new Intent(pdfseeAllView.this, savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(pdfseeAllView.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(pdfseeAllView.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(pdfseeAllView.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(pdfseeAllView.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(pdfseeAllView.this, setting_menu.class));
                        overridePendingTransition(0, 0);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(pdfseeAllView.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();
        TextureView searchView=findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pdfseeAllView.this, Search_Screen.class));
            }
        });

        updateUavigation();
    }

    public void getPdfSolution(String category, String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(category).document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            pdfTittle.add(s);
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        pdfLink.add(s3);
                    }
                    recyclerView.setAdapter(Adapter);
                }
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
                Intent intent=new Intent(pdfseeAllView.this,MyBio.class);
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

}