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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.Color;
import com.prabhat.doubtnut.Adapter.bookSolutionAdapter;
import com.prabhat.doubtnut.Adapter.blogAdapter;
import com.prabhat.doubtnut.Adapter.pdfSolutionAdapter;
import com.prabhat.doubtnut.Adapter.videoSolutionAdapter;
import com.prabhat.doubtnut.Login_Details.Login_Page;
import com.prabhat.doubtnut.Model.Blog_Model;
import com.prabhat.doubtnut.Model.Maths_Model;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    Button Send, uploadDoubt;
    FirebaseFirestore firestore;
    bookSolutionAdapter bookAdapter;
    videoSolutionAdapter ncertAdapter, jeeMainsAdapter, getJeeAdvanceAdapter;
    blogAdapter blogadapter;
    pdfSolutionAdapter pdfAdapter;
    ArrayList<Maths_Model> bookList;
    StorageReference storageReference;
    //NCERT Videos
    ArrayList<String> TittleList, ThumbnailList, LinkList, PdfList;
    // jee mains
    ArrayList<String> jeeMainsTittle, jeeMainsThumbnail, jeeMainsLinkList, jeeMainsPdfList;
    // jee advance
    ArrayList<String> jeeAdvanceTittle, jeeAdvanceThumbnail, jeeAdvanceLinkList, jeeAdvancePdfList;
    //blog
    ArrayList<String> blogTittle, blogDescription, blogLink;

    //pdf
    ArrayList<String> pdfTittle, pdfLink;
    RecyclerView bookRecyclerView, ncertRecyclerView, jeeMainsRecyclerView, jeeAdvanceRecyclerView, pdfRecyclerView, blogrecyclerView;
    TextView searchView;
    DrawerLayout drawerLayout;
    CircleImageView profileImage;
    EditText writeDoubt;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    private int gallary_pic = 200;
    ImageView imageView;
    private Uri imageuri;
    String modeOfLogin;
    ProgressDialog dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        searchView = findViewById(R.id.search_view);

        profileImage = findViewById(R.id.profile_image);
        writeDoubt = findViewById(R.id.writedoubt);
        uploadDoubt = findViewById(R.id.upload);
        dialogClass = new ProgressDialog(this);
        //NCERT List
        TittleList = new ArrayList<String>();
        ThumbnailList = new ArrayList<String>();
        LinkList = new ArrayList<String>();
        PdfList = new ArrayList<String>();
        //Jee mains List
        jeeMainsTittle = new ArrayList<String>();
        jeeMainsThumbnail = new ArrayList<String>();
        jeeMainsLinkList = new ArrayList<String>();
        jeeMainsPdfList = new ArrayList<String>();
        //jee Advance list
        jeeAdvanceTittle = new ArrayList<String>();
        jeeAdvanceThumbnail = new ArrayList<String>();
        jeeAdvanceLinkList = new ArrayList<String>();
        jeeAdvancePdfList = new ArrayList<String>();
        //blog
        blogTittle = new ArrayList<String>();
        blogDescription = new ArrayList<String>();
        blogLink = new ArrayList<String>();
//
        pdfTittle = new ArrayList<>();
        pdfLink = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference().child("doubtImage");

        modeOfLogin = getIntent().getStringExtra("modeOfLogin");
        bookAdapter = new bookSolutionAdapter(bookList, this);
        ncertAdapter = new videoSolutionAdapter(TittleList, ThumbnailList, LinkList, PdfList, this);
        jeeMainsAdapter = new videoSolutionAdapter(jeeMainsTittle, jeeMainsThumbnail, jeeMainsLinkList, jeeMainsPdfList, this);
        getJeeAdvanceAdapter = new videoSolutionAdapter(jeeAdvanceTittle, jeeAdvanceThumbnail, jeeAdvanceLinkList, jeeAdvancePdfList, this);

        pdfAdapter = new pdfSolutionAdapter(modeOfLogin,pdfTittle, pdfLink, this);
        blogadapter = new blogAdapter(blogTittle, blogDescription, blogLink, this);


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





        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Search_Screen.class));
            }
        });

        ImageView whatsapp = findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = "8800622475dd";
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+91" + mobileNumber));
                    startActivity(intent);
                } else {
                    Toast.makeText(Home.this, "Whatsapp not installed in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.myDoubts:
                        Intent intent = new Intent(Home.this, MyDoubt.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.cources:
                        Intent intent1 = new Intent(Home.this, MyCourses.class);
                        intent1.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.practices:
                        Intent intent2 = new Intent(Home.this, Practices.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        Intent intent2=new Intent(Home.this,dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        Toast.makeText(Home.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent=new Intent(Home.this,savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Home.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Home.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Home.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Home.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(Home.this,setting_menu.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(Home.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();


        profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        //        getBookSolutionDataData();

        getVideoSolution("Class 11", "Mathematics");
        getVideoSolution("Class 11", "Physics");
        getVideoSolution("Class 11", "Chemistry");

        getVideoSolution("Class 12", "Mathematics");
        getVideoSolution("Class 12", "Physics");
        getVideoSolution("Class 12", "Chemistry");

        getJeeSolution("Mathematics");
        getJeeSolution("Chemistry");
        getJeeSolution("Physics");

        getJeeAdvanceSolution("Mathematics");
        getJeeAdvanceSolution("Chemistry");
        getJeeAdvanceSolution("Physics");

        getPdfSolution("Class 11", "Mathematics");
        getPdfSolution("Class 11", "Chemistry");
        getPdfSolution("Class 11", "Physics");

        Blog();

        updateUavigation();

        final TextView seeAll1 = findViewById(R.id.see_all_1);
        seeAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, seeAllView.class);
                intent.putExtra("clicked", "seeAll1");
                intent.putExtra("modeOfLogin",modeOfLogin);
                startActivity(intent);
            }
        });

        final TextView seeAll2 = findViewById(R.id.see_all_2);
        seeAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, seeAllView.class);
                intent.putExtra("clicked", "seeAll2");
                intent.putExtra("modeOfLogin",modeOfLogin);
                startActivity(intent);
            }
        });

        final TextView seeAll3 = findViewById(R.id.see_all_3);
        seeAll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, seeAllView.class);
                intent.putExtra("clicked", "seeAll3");
                intent.putExtra("modeOfLogin",modeOfLogin);
                startActivity(intent);
            }
        });


        final TextView seeAll4 = findViewById(R.id.see_all_5);
        seeAll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, pdfseeAllView.class);
                intent.putExtra("modeOfLogin",modeOfLogin);
                intent.putExtra("modeOfLogin",modeOfLogin);
                startActivity(intent);
            }
        });


        Send = findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClass.show();
                dialogClass.setMessage("Please wait a moment");
                String Doubt = writeDoubt.getText().toString().trim();
//                List<String> list = new ArrayList<>();
//                list.add(Doubt);
//                Map<Object, Object> map = new HashMap<>();
//                map.put("Doubt",list);
                if (Doubt.isEmpty()) {
                    writeDoubt.requestFocus();
                    writeDoubt.setError("Write your doubt here...");
                }
                if (!Doubt.isEmpty()) {
                    firestore.collection("User").document(modeOfLogin)
                            .update("doubtText", FieldValue.arrayUnion(Doubt)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialogClass.hide();
                            Toast.makeText(Home.this, "Sent", Toast.LENGTH_SHORT).show();
                            writeDoubt.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogClass.hide();
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean appInstalledOrNot(String url) {

        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    // ask doubt dialog
    public void askDoubt(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = getLayoutInflater().inflate(R.layout.mydoubt, null);

        builder.setView(view1);
        imageView = view1.findViewById(R.id.imageView);
        Button uploadImage = view1.findViewById(R.id.upload);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallary_pic);
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<String> list = new ArrayList<>();
//                list.add(imageuri.toString());

                if(imageuri == null){
                    Toast.makeText(Home.this, "Upload your doubt", Toast.LENGTH_SHORT).show();
                }
                if(imageuri!=null) {
                    dialogClass.show();
                    dialogClass.setMessage("Uploading....");
                    final StorageReference ref = storageReference.child("doubtImage" + UUID.randomUUID());
                    ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    firestore.collection("User").document(modeOfLogin)
                                            .update("doubtPhoto", FieldValue.arrayUnion(uri.toString()))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialogClass.hide();
                                            alertDialog.hide();
                                            imageuri=null;
                                            Toast.makeText(Home.this, "Doubt sent successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialogClass.hide();
                                            alertDialog.hide();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogClass.hide();
                            alertDialog.hide();
                            Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    //getting book solution
//    public void getBookSolutionDataData() {
//        firestore.collection("pdfSolution").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                }
//                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
//                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
//                    bookList.add(model);
//                    bookRecyclerView.setAdapter(bookAdapter);
//                }
//            }
//        });
//    }

    public void getVideoSolution(String category, String Subject) {
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

                    ncertRecyclerView.setAdapter(ncertAdapter);
                }
            }
        });
    }

    public void getJeeSolution(String subject) {
        firestore.collection("JEE Mains").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    List<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            jeeMainsTittle.add(s);
                        }
                    }
                    List<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i));
                            jeeMainsThumbnail.add(l2.get(i));
                        }
                    }
                    List<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            jeeMainsLinkList.add(l3.get(i));
                        }
                    }
                    List<String> l4 = model.getPdf();
                    if (!l4.isEmpty()) {
                        for (int i = 0; i < l4.size(); i++) {
                            Log.i("ssss", l4.get(i));
                            jeeMainsPdfList.add(l4.get(i));
                        }
                    }
                    jeeMainsRecyclerView.setAdapter(jeeMainsAdapter);

                }
            }
        });
    }

    public void getJeeAdvanceSolution(String subject) {
        firestore.collection("JEE Advance").document("Subjects").collection(subject).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                }
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    Maths_Model model = documentChange.getDocument().toObject(Maths_Model.class);
                    List<String> l = model.getTittle();
                    if (!l.isEmpty()) {
                        for (String s : l) {
                            Log.i("ssss", s);
                            jeeAdvanceTittle.add(s);
                        }
                    }
                    List<String> l2 = model.getImageThumbnail();
                    if (!l2.isEmpty()) {
                        for (int i = 0; i < l2.size(); i++) {
                            Log.i("ssss", l2.get(i));
                            jeeAdvanceThumbnail.add(l2.get(i));
                        }
                    }
                    List<String> l3 = model.getLink();
                    if (!l3.isEmpty()) {
                        for (int i = 0; i < l3.size(); i++) {
                            Log.i("ssss", l3.get(i));
                            jeeAdvanceLinkList.add(l3.get(i));
                        }
                    }
                    List<String> l4 = model.getPdf();
                    if (!l4.isEmpty()) {
                        for (int i = 0; i < l4.size(); i++) {
                            Log.i("ssss", l4.get(i));
                            jeeAdvancePdfList.add(l4.get(i));
                        }
                    }
                    jeeAdvanceRecyclerView.setAdapter(getJeeAdvanceAdapter);

                }
            }
        });
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
                    pdfRecyclerView.setAdapter(pdfAdapter);
                }
            }
        });
    }

    public void Blog() {
        firestore.collection("Blog").document("Blog").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();
                Blog_Model model = documentSnapshot.toObject(Blog_Model.class);
                List<String> l = model.getTittle();
                if (!l.isEmpty()) {
                    for (String s : l) {
                        Log.i("ssss", s);
                        blogTittle.add(s);
                    }
                }
                List<String> l2 = model.getDescription();
                if (!l2.isEmpty()) {
                    for (int i = 0; i < l2.size(); i++) {
                        Log.i("ssss", l2.get(i));
                        blogDescription.add(l2.get(i));
                    }
                }
                List<String> l3 = model.getLink();
                if (!l3.isEmpty()) {
                    for (int i = 0; i < l3.size(); i++) {
                        Log.i("ssss", l3.get(i));
                        blogLink.add(l3.get(i));
                    }
                }
                blogrecyclerView.setAdapter(blogadapter);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallary_pic && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
        }
    }

    //    public void getUserData() {
//        firestore.collection("User").document(modeOfLogin).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                Log.i("imgae",documentSnapshot.getString("ImageUri")+"");
//                Log.i("Tag1",modeOfLogin);
//            }
//        });
//    }
    public void updateUavigation() {
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        final CircleImageView img = headerView.findViewById(R.id.profile_image1);
        final TextView username = headerView.findViewById(R.id.username);
        final TextView editText = headerView.findViewById(R.id.edit_text);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MyBio.class);
                intent.putExtra("modeOfLogin", modeOfLogin);
                startActivity(intent);
            }
        });

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

    public void click(View v){
        String data;
        switch (v.getId()){
            case R.id.maths:
                data="Mathematics";
                break;
            case R.id.chemistry:
                data="Chemistry";
                break;
            case R.id.physics:
                data="Physics";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        Intent i=new Intent(Home.this,coursesCategory.class);
        i.putExtra("data",data);
        i.putExtra("modeOfLogin",modeOfLogin);
        startActivity(i);
    }
}