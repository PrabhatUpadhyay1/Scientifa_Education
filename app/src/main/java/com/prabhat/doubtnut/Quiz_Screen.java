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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.mcQuestionAdapter;
import com.prabhat.doubtnut.Model.MCQ_Model;
import com.prabhat.doubtnut.Model.Maths_Model;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class Quiz_Screen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<String> questionList, optionAList, optionBList, optionCList, optionDList, answerList;
    mcQuestionAdapter adapter;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__screen);

        Id = getIntent().getStringExtra("chapter");

        questionList = new ArrayList<String>();
        optionAList = new ArrayList<String>();
        optionBList = new ArrayList<String>();
        optionCList = new ArrayList<String>();
        optionDList = new ArrayList<String>();
        answerList = new ArrayList<String>();

        adapter = new mcQuestionAdapter(questionList, optionAList, optionBList, optionCList, optionDList, answerList, Id, this);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);


        getQuestion();

        final DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);

        ImageView profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.practices);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(Quiz_Screen.this, Home.class));
                        Toast.makeText(Quiz_Screen.this, "Home", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.myDoubts:
                        startActivity(new Intent(Quiz_Screen.this, MyDoubt.class));
                        Toast.makeText(Quiz_Screen.this, "My Doubt", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cources:
                        startActivity(new Intent(Quiz_Screen.this, MyCourses.class));
                        Toast.makeText(Quiz_Screen.this, "Favourites", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.practices:
                        Toast.makeText(Quiz_Screen.this, "Profile", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    //    public void getQuestion() {
//        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        firestore.collection("JEE Mains").document("Easy").collection("Mathematics").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//
//                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
//                    MCQ_Model model = documentChange.getDocument().toObject(MCQ_Model.class);
//                    String chapter=documentChange.getDocument().getId();
//                    Log.i("chapter",chapter);
//                    List<String> l = model.getQuestions();
//                    if (!l.isEmpty()) {
//                        for (String s : l) {
//                            Log.i("ssss", s);
//                            questionList.add(s);
//                        }
//                    }
//                    List<String> l2 = model.getOptionA();
//                    if (!l2.isEmpty()) {
//                        for (int i = 0; i < l2.size(); i++) {
//                            Log.i("ssss", l2.get(i));
//                            optionAList.add(l2.get(i));
//                        }
//                    }
//                    List<String> l3 = model.getOptionB();
//                    if (!l3.isEmpty()) {
//                        for (int i = 0; i < l3.size(); i++) {
//                            Log.i("ssss", l3.get(i));
//                            optionBList.add(l3.get(i));
//                        }
//                    }
//                    List<String> l4 = model.getOptionC();
//                    if (!l4.isEmpty()) {
//                        for (int i = 0; i < l4.size(); i++) {
//                            Log.i("ssss", l4.get(i));
//                            optionCList.add(l4.get(i));
//                        }
//                    }
//
//                    List<String> l5 = model.getOptionC();
//                    if (!l5.isEmpty()) {
//                        for (int i = 0; i < l5.size(); i++) {
//                            Log.i("ssss", l5.get(i));
//                            optionDList.add(l5.get(i));
//                        }
//                    }
//
//                    List<String> l6 = model.getOptionC();
//                    if (!l6.isEmpty()) {
//                        for (int i = 0; i < l6.size(); i++) {
//                            Log.i("ssss", l6.get(i));
//                            answerList.add(l6.get(i));
//                        }
//                    }
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//        });
//    }
    public void getQuestion() {
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("JEE Mains").document("Easy").collection("Mathematics").document("Function, sets and realtionship").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                MCQ_Model model = documentSnapshot.toObject(MCQ_Model.class);
                String chapter = documentSnapshot.getId();
                Log.i("chapter", chapter);
                List<String> l = model.getQuestions();
                if (!l.isEmpty()) {
                    for (String s : l) {
                        Log.i("ssss", s);
                        questionList.add(s);
                    }
                }
                List<String> l2 = model.getOptionA();
                if (!l2.isEmpty()) {
                    for (int i = 0; i < l2.size(); i++) {
                        Log.i("ssss", l2.get(i));
                        optionAList.add(l2.get(i));
                    }
                }
                List<String> l3 = model.getOptionB();
                if (!l3.isEmpty()) {
                    for (int i = 0; i < l3.size(); i++) {
                        Log.i("ssss", l3.get(i));
                        optionBList.add(l3.get(i));
                    }
                }
                List<String> l4 = model.getOptionC();
                if (!l4.isEmpty()) {
                    for (int i = 0; i < l4.size(); i++) {
                        Log.i("ssss", l4.get(i));
                        optionCList.add(l4.get(i));
                    }
                }

                List<String> l5 = model.getOptionC();
                if (!l5.isEmpty()) {
                    for (int i = 0; i < l5.size(); i++) {
                        Log.i("ssss", l5.get(i));
                        optionDList.add(l5.get(i));
                    }
                }

                List<String> l6 = model.getOptionC();
                if (!l6.isEmpty()) {
                    for (int i = 0; i < l6.size(); i++) {
                        Log.i("ssss", l6.get(i));
                        answerList.add(l6.get(i));
                    }
                }
                recyclerView.setAdapter(adapter);
            }
        });
    }
}