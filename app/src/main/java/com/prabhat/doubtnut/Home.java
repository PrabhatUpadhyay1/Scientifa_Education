package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.bookSolutionAdapter;
import com.prabhat.doubtnut.Adapter.blogAdapter;
import com.prabhat.doubtnut.Adapter.pdfSolutionAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Login_Details.Login_Page;
import com.prabhat.doubtnut.Model.Model;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    CardView myDoubt;

    RelativeLayout relativeLayout;

    FirebaseAuth auth;
    Button logout, uploadDoubt;
    FirebaseFirestore firestore;
    bookSolutionAdapter bookAdapter;
    videoSolutionAdapter ncertAdapter, jeeSMainsAdapter, getJeeAdvanceAdapter;
    blogAdapter blogadapter;
    pdfSolutionAdapter pdfAdapter;
    ArrayList<Model> bookList, ncertList, jeeMainsList, jeeAdvanceList, pdfList, blogList;
    RecyclerView bookRecyclerView, ncertRecyclerView, jeeMainsRecyclerView, jeeAdvanceRecyclerView, pdfRecyclerView, blogrecyclerView;

    DrawerLayout drawerLayout;

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    NavigationView navigationView;

    BottomNavigationView bottomNavigationView;

    EditText writeDoubt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
//        myDoubt = findViewById(R.id.doubt_cardView);
//        relativeLayout=findViewById(R.id.relativeLayout);
        bottomNavigationView = findViewById(R.id.navigation_bar);
        writeDoubt=findViewById(R.id.writedoubt);
        uploadDoubt = findViewById(R.id.upload);

        bookList = new ArrayList<Model>();
        ncertList = new ArrayList<Model>();
        jeeMainsList = new ArrayList<Model>();
        jeeAdvanceList = new ArrayList<Model>();
        pdfList = new ArrayList<Model>();
        blogList = new ArrayList<Model>();


        myDoubt = findViewById(R.id.doubt_cardView);

        bookAdapter = new bookSolutionAdapter(bookList, this);
        ncertAdapter = new videoSolutionAdapter(ncertList, this);
        jeeSMainsAdapter = new videoSolutionAdapter(jeeMainsList, this);
        getJeeAdvanceAdapter = new videoSolutionAdapter(jeeAdvanceList, this);
        pdfAdapter = new pdfSolutionAdapter(pdfList, this);
        blogadapter = new blogAdapter(blogList, this);


        bookRecyclerView = findViewById(R.id.book_recycler_view);
        ncertRecyclerView = findViewById(R.id.ncert_recycler_view);
        jeeMainsRecyclerView = findViewById(R.id.jee_recycler_view);
        jeeAdvanceRecyclerView = findViewById(R.id.jee_advance_recycler_view);
        pdfRecyclerView = findViewById(R.id.pdf_recycler_view);
        blogrecyclerView = findViewById(R.id.blog_recycler_view);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bookRecyclerView.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ncertRecyclerView.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        jeeMainsRecyclerView.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        jeeAdvanceRecyclerView.setLayoutManager(linearLayoutManager4);

        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        pdfRecyclerView.setLayoutManager(linearLayoutManager5);

        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        blogrecyclerView.setLayoutManager(linearLayoutManager6);
        drawerLayout = findViewById(R.id.drawer_layout);

        ImageView profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.savedpdf:
                        Toast.makeText(Home.this, "Hokme", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        writeDoubt.setEnabled(false);
//
//        relativeLayout.setY(-1000);
//        myDoubt.setY(-1000);
//        uploadDoubt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                relativeLayout.animate().translationYBy(1000).setDuration(1000);
//                myDoubt.animate().translationYBy(1000).setDuration(1000);
//
//            }
//        });
//
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                relativeLayout.animate().translationYBy(-1000).setDuration(1000);
//                myDoubt.animate().translationYBy(-1000).setDuration(1000);
//
//            }
//        });
//


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.myDoubts:
                        startActivity(new Intent(Home.this, MyDoubt.class));
                        return true;
                    default:
                        return false;
                }
            }
        });

        getBookSolutionDataData();

        getVideoSolution();

        getJeeSolution();

        getJeeAdvanceSolution();

        getPdfSolution();

        Blog();


        /// just for temporary LOGOUT
        logout = findViewById(R.id.send);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Login_Page.class));
                finish();
                auth.signOut();
            }
        });
    }


    // ask doubt dialog
    public void askDoubt(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = getLayoutInflater().inflate(R.layout.mydoubt, null);

        builder.setView(view1);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();
    }

    //getting book solution
    public void getBookSolutionDataData() {
        firestore.collection("pdfSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    bookList.add(model);
                    bookRecyclerView.setAdapter(bookAdapter);
                }
            }
        });
    }

    public void getVideoSolution() {
        firestore.collection("videoSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    ncertList.add(model);
                    ncertRecyclerView.setAdapter(ncertAdapter);
                }
            }
        });
    }

    public void getJeeSolution() {
        firestore.collection("videoSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    jeeMainsList.add(model);
                    jeeMainsRecyclerView.setAdapter(jeeSMainsAdapter);

                }
            }
        });
    }

    public void getJeeAdvanceSolution() {
        firestore.collection("videoSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    jeeAdvanceList.add(model);
                    jeeAdvanceRecyclerView.setAdapter(getJeeAdvanceAdapter);

                }
            }
        });
    }

    public void getPdfSolution() {
        firestore.collection("pdfSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    pdfList.add(model);
                    pdfRecyclerView.setAdapter(pdfAdapter);

                }
            }
        });
    }

    public void Blog() {
        firestore.collection("Blogs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Model model = documentChange.getDocument().toObject(Model.class);
                    blogList.add(model);
                    blogrecyclerView.setAdapter(blogadapter);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
//    public void setUpToolbar(){
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toolbar=(Toolbar) findViewById(R.id.rectangle_6);
//
//        setSupportActionBar(toolbar);
//        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//    }
}