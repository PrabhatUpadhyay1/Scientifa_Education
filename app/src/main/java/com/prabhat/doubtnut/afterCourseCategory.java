package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.prabhat.doubtnut.Adapter.chapterAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter1;
import com.prabhat.doubtnut.Model.Maths_Model;

import java.util.ArrayList;
import java.util.List;

public class afterCourseCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> listId,list, list2, list3, list4;
    String data,Id;
    videoSolutionAdapter1 adapter;
    String Class;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_course_category);

        Id = getIntent().getStringExtra("chapter");
        data = getIntent().getStringExtra("data");
        Class=getIntent().getStringExtra("class");

        drawerLayout=findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recycler_view);

        listId = new ArrayList<>();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();

        adapter = new videoSolutionAdapter1(listId,list, list2, list3, list4, this);

        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        getVideoSolution();
    }

    public void getVideoSolution() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Class).document("Subjects")
                .collection(data).document(Id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                String Id = documentSnapshot.getId();
                listId.add(Id);

                Maths_Model model = documentSnapshot.toObject(Maths_Model.class);
                    List<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            list.add(s);
                        }
                    }
                    List<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i));
                            list2.add(l2.get(i));
                        }
                    }
                    List<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            list3.add(l3.get(i));
                        }
                    }
                    List<String> l4 = model.getPdf();
                    if (!l4.isEmpty()) {
                        for (int i = 0; i < l4.size(); i++) {
                            Log.i("ssss", l4.get(i));
                            list4.add(l4.get(i));
                        }
                    }

                    recyclerView.setAdapter(adapter);
                }
        });
    }

}