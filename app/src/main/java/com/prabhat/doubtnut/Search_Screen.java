package com.prabhat.doubtnut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.prabhat.doubtnut.Adapter.SearchAdapter;
import com.prabhat.doubtnut.Model.Maths_Model;

import java.util.ArrayList;
import java.util.List;

public class Search_Screen extends AppCompatActivity {

    TextView back;
    MaterialSearchBar searchBar;
    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ArrayList<String> list, list2, list3, list4;
    SearchAdapter adapter;
    EditText searchView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__screen);
        back = findViewById(R.id.back);

        // searchView=findViewById(R.id.search);
        searchBar = (MaterialSearchBar) findViewById(R.id.search);
        searchBar.setHint("Search for Subjects, Books, Topics, PDFs...");
        searchBar.setSpeechMode(false);

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                String Data = text.toString().toLowerCase();
                Toast.makeText(Search_Screen.this, text.toString(), Toast.LENGTH_SHORT).show();
                list.clear();
                list2.clear();
                list3.clear();
                list4.clear();
                getData("Class 11","Chemistry",Data);
                getData("Class 11","Mathematics",Data);
                getData("Class 11","Physics",Data);

                getData("Class 12","Chemistry",Data);
                getData("Class 12","Mathematics",Data);
                getData("Class 12","Physics",Data);

                getData("JEE Mains","Chemistry",Data);
                getData("JEE Mains","Mathematics",Data);
                getData("JEE Mains","Physics",Data);


                getData("JEE Advance","Chemistry",Data);
                getData("JEE Advance","Mathematics",Data);
                getData("JEE Advance","Physics",Data);
                searchBar.setText("");
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        recyclerView = findViewById(R.id.recycler_view);
        //button = findViewById(R.id.uuub);
        adapter = new SearchAdapter(list, list2, list3, list4, this);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.clear();
//                list2.clear();
//                String Data = searchView.getText().toString();
//                Toast.makeText(Search_Screen.this, Data, Toast.LENGTH_SHORT).show();
//                getVideoSolution();
//                searchView.setText("");
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getData(String category,String subject, String data) {
        firestore.collection(category).document("Subjects").collection(subject)
                .whereArrayContains("searchData", data).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    List<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("vvvv", s);
                            list.add(s);
                        }
                    }
                    List<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("vvvv", l2.get(i));
                            list2.add(l2.get(i));
                        }
                    }
                    List<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("vvvv", l3.get(i));
                            list3.add(l3.get(i));
                        }
                    }
                    List<String> l4 = model.getPdf();
                    if (!l4.isEmpty()) {
                        for (int i = 0; i < l4.size(); i++) {
                            Log.i("vvvv", l4.get(i));
                            list4.add(l4.get(i));
                        }
                    }
                    recyclerView.setAdapter(adapter);
                }

            }
        });
    }
}