package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.myDoubtAdapter;
import com.prabhat.doubtnut.Model.Model;
import com.prabhat.doubtnut.Model.MyDoubtModel;

import java.util.ArrayList;
import java.util.List;

public class MyDoubt extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MyDoubtModel> list;
    myDoubtAdapter adapter;

    FirebaseFirestore firestore;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doubt);

        firestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        adapter = new myDoubtAdapter(list, this);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getMyDoubt();


        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(MyDoubt.this, Home.class));
                        return true;
                    case R.id.myDoubts:
                        Toast.makeText(MyDoubt.this, "My Doubt", Toast.LENGTH_SHORT).show();
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
