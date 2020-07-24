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
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.pdfSolutionAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;

public class MyCourses extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Spinner spinner, spinner2, spinner3, spinner4, spinner5, spinner6,spinner7, spinner8;

    videoSolutionAdapter adapter, adapter2, adapter5, adapter6;
    pdfSolutionAdapter adapter3, adapter4,adapter7, adapter8;
    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6, recyclerView7, recyclerView8;

    // class 11th
    ArrayList<String> list, list2, list3, list4;

    // class 12th
    ArrayList<String> list5, list6, list7, list8;

    // pdf class 11 and 12
    ArrayList<String> list9, list10, list11, list12;


    //jee mains
    ArrayList<String> list13, list14, list15, list16;

    // jee advance
    ArrayList<String> list17, list18, list19, list20;

    // pdf jee mains and jee advance
    ArrayList<String> list21, list22, list23, list24;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView2 = findViewById(R.id.recycler_view2);
        recyclerView3 = findViewById(R.id.recycler_view3);
        recyclerView4 = findViewById(R.id.recycler_view4);



        recyclerView5 = findViewById(R.id.recycler_view5);
        recyclerView6 = findViewById(R.id.recycler_view6);


        recyclerView7 = findViewById(R.id.recycler_view7);
        recyclerView8 = findViewById(R.id.recycler_view8);

        //class 11th
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();

        //class 12th
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        list8 = new ArrayList<>();

        //class 11 pdf
        list9 = new ArrayList<>();
        list10 = new ArrayList<>();

        //class 12th pdf
        list11 = new ArrayList<>();
        list12 = new ArrayList<>();

        // jee mains
        list13 = new ArrayList<>();
        list14 = new ArrayList<>();
        list15 = new ArrayList<>();
        list16 = new ArrayList<>();

        //jee advance
        list17 = new ArrayList<>();
        list18 = new ArrayList<>();
        list19 = new ArrayList<>();
        list20 = new ArrayList<>();

        //jee mains pdf
        list21 = new ArrayList<>();
        list22 = new ArrayList<>();

        //jee advance pdf
        list23 = new ArrayList<>();
        list24 = new ArrayList<>();


        adapter = new videoSolutionAdapter(list, list2, list3, list4, this);

        adapter2 = new videoSolutionAdapter(list5, list6, list7, list8, this);

        adapter3 = new pdfSolutionAdapter(list9, list10, this);

        adapter4 = new pdfSolutionAdapter(list11, list12, this);


        adapter5 = new videoSolutionAdapter(list13, list14, list15, list16, this);

        adapter6 = new videoSolutionAdapter(list17, list18, list19, list20, this);


        adapter7 = new pdfSolutionAdapter(list21,list22, this);

        adapter8 = new pdfSolutionAdapter(list23, list24, this);


        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView4.setLayoutManager(linearLayoutManager4);


        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView5.setLayoutManager(linearLayoutManager5);

        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView6.setLayoutManager(linearLayoutManager6);

        LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView7.setLayoutManager(linearLayoutManager7);

        LinearLayoutManager linearLayoutManager8 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView8.setLayoutManager(linearLayoutManager8);


        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        spinner7 = findViewById(R.id.spinner7);
        spinner8 = findViewById(R.id.spinner8);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);


        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);


        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setAdapter(adapter7);

        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this, R.array.SUbjects, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner8.setAdapter(adapter8);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject, Toast.LENGTH_SHORT).show();
                list.clear();
                list2.clear();
                list3.clear();
                list4.clear();
                getVideoSolution(Subject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Subject12 = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject12, Toast.LENGTH_SHORT).show();
                list5.clear();
                list6.clear();
                list7.clear();
                list8.clear();
                getVideoSolutionForClass12(Subject12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String PdfSubject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, PdfSubject, Toast.LENGTH_SHORT).show();
                list9.clear();
                list10.clear();
                getPdfSolutionForClass11(PdfSubject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String PdfSubject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, PdfSubject, Toast.LENGTH_SHORT).show();
                list11.clear();
                list12.clear();
                getPdfSolutionForClass12(PdfSubject);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String Subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject, Toast.LENGTH_SHORT).show();
                list13.clear();
                list14.clear();
                list15.clear();
                list16.clear();
                getVideoSolutionForJeeMains(Subject);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String PdfSubject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, PdfSubject, Toast.LENGTH_SHORT).show();

                String Subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject, Toast.LENGTH_SHORT).show();
                list17.clear();
                list18.clear();
                list19.clear();
                list20.clear();
                getVideoSolutionForJeeAdvance(Subject);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String Subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject, Toast.LENGTH_SHORT).show();
                list21.clear();
                list22.clear();
                getPdfSolutionForJEEMains(Subject);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String PdfSubject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, PdfSubject, Toast.LENGTH_SHORT).show();

                String Subject = parent.getItemAtPosition(position).toString();
                Toast.makeText(MyCourses.this, Subject, Toast.LENGTH_SHORT).show();
                list23.clear();
                list24.clear();
                getPdfSolutionForJEEAdvance(Subject);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView;
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(MyCourses.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                    case R.id.savedpdf:
                        Toast.makeText(MyCourses.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                    case R.id.history:
                        Toast.makeText(MyCourses.this, "History", Toast.LENGTH_SHORT).show();
                    case R.id.aboutUs:
                        Toast.makeText(MyCourses.this, "About", Toast.LENGTH_SHORT).show();
                    case R.id.contactus:
                        Toast.makeText(MyCourses.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    case R.id.setting:
                        Toast.makeText(MyCourses.this, "Setting", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MyCourses.this,setting_menu.class));
                        overridePendingTransition(0,0);
                }
                return true;
            }
        });



        ImageView profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });


        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.cources);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(MyCourses.this, Home.class));
                        Toast.makeText(MyCourses.this, "Home", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.myDoubts:
                        startActivity(new Intent(MyCourses.this, MyDoubt.class));
                        Toast.makeText(MyCourses.this, "My Doubt", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cources:
                        Toast.makeText(MyCourses.this, "Favourites", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.practices:
                        startActivity(new Intent(MyCourses.this, Practices.class));
                        Toast.makeText(MyCourses.this, "Profile", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void getVideoSolution(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Class 11").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list.add(s);
                        }
                    }
                    ArrayList<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i) + "");
                            list2.add(l2.get(i));
                        }
                    }
                    ArrayList<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            list3.add(l3.get(i));
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list4.add(s3);
                    }
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    public void getVideoSolutionForClass12(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Class 12").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list5.add(s);
                        }
                    }
                    ArrayList<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i) + "");
                            list6.add(l2.get(i));
                        }
                    }
                    ArrayList<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            list7.add(l3.get(i));
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list8.add(s3);
                    }
                    recyclerView2.setAdapter(adapter2);
                }
            }
        });
    }

    public void getPdfSolutionForClass11(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Class 11").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list9.add(s);
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list10.add(s3);
                    }
                    recyclerView3.setAdapter(adapter3);
                }
            }
        });
    }

    public void getPdfSolutionForClass12(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Class 12").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list11.add(s);
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list12.add(s3);
                    }
                    recyclerView4.setAdapter(adapter4);
                }
            }
        });
    }

    public void getVideoSolutionForJeeMains(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Mains").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list13.add(s);
                        }
                    }
                    ArrayList<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i) + "");
                            list14.add(l2.get(i));
                        }
                    }
                    ArrayList<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            list15.add(l3.get(i));
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list16.add(s3);
                    }
                    recyclerView5.setAdapter(adapter5);
                }
            }
        });
    }

    public void getVideoSolutionForJeeAdvance(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Advance").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list17.add(s);
                        }
                    }
                    ArrayList<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i) + "");
                            list18.add(l2.get(i));
                        }
                    }
                    ArrayList<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            list19.add(l3.get(i));
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list20.add(s3);
                    }
                    recyclerView6.setAdapter(adapter6);
                }
            }
        });
    }

    public void getPdfSolutionForJEEMains(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Mains").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list21.add(s);
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list22.add(s3);
                    }
                    recyclerView7.setAdapter(adapter7);
                }
            }
        });
    }

    public void getPdfSolutionForJEEAdvance(String subject) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Advance").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    ArrayList<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list23.add(s);
                        }
                    }
                    ArrayList<String> l4 = model.getPdf();
                    for (String s3 : l4) {
                        Log.i("ssss", s3);
                        list24.add(s3);
                    }
                    recyclerView8.setAdapter(adapter8);
                }
            }
        });
    }

}