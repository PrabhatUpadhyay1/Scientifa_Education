package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class seeAllView extends AppCompatActivity {

    videoSolutionAdapter Adapter;
    ArrayList<String> TittleList, ThumbnailList, LinkList, PdfList;
    RecyclerView recyclerView;
    String modeOfLogin;
    CircleImageView profileImage;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_view);


        String clicked = getIntent().getStringExtra("clicked");
        modeOfLogin = getIntent().getStringExtra("modeOfLogin");
        TittleList = new ArrayList<>();
        ThumbnailList = new ArrayList<>();
        LinkList = new ArrayList<>();
        PdfList = new ArrayList<>();

        Adapter = new videoSolutionAdapter(TittleList, ThumbnailList, LinkList, PdfList, this);

        recyclerView = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (clicked.equals("seeAll1")) {
            getVideoSolution("Class 11", "Mathematics");
            getVideoSolution("Class 11", "Physics");
            getVideoSolution("Class 11", "Chemistry");

            getVideoSolution("Class 12", "Mathematics");
            getVideoSolution("Class 12", "Physics");
            getVideoSolution("Class 12", "Chemistry");

        }
        if (clicked.equals("seeAll2")) {
            getVideoSolution("JEE Mains", "Mathematics");
            getVideoSolution("JEE Mains", "Chemistry");
            getVideoSolution("JEE Mains", "Physics");
        }
        if (clicked.equals("seeAll3")) {
            getVideoSolution("JEE Advance", "Mathematics");
            getVideoSolution("JEE Advance", "Chemistry");
            getVideoSolution("JEE Advance", "Physics");
        }


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
                        Intent intent2 = new Intent(seeAllView.this, dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        Toast.makeText(seeAllView.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent = new Intent(seeAllView.this, savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);finish();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(seeAllView.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(seeAllView.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(seeAllView.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(seeAllView.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(seeAllView.this, setting_menu.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.END);finish();
                        Toast.makeText(seeAllView.this, "Setting", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(seeAllView.this, Search_Screen.class));
            }
        });

        updateUavigation();

    }

    public void getVideoSolution(String category, String Subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(category).document("Subjects").collection(Subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    List<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            TittleList.add(s);
                        }
                    }
                    List<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i));
                            ThumbnailList.add(l2.get(i));
                        }
                    }
                    List<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            LinkList.add(l3.get(i));
                        }
                    }
                    List<String> l4 = model.getPdf();
                    if (!l4.isEmpty()) {
                        for (int i = 0; i < l4.size(); i++) {
                            Log.i("ssss", l4.get(i));
                            PdfList.add(l4.get(i));
                        }
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
                Intent intent=new Intent(seeAllView.this,MyBio.class);
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