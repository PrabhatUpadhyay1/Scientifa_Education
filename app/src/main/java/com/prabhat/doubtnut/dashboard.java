package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class dashboard extends AppCompatActivity {

    String modeOfLogin;
    TextView username, dob, classstudy, gender;
    CircleImageView profileImage,profileImage1,profileImage2;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        navigationView = findViewById(R.id.navigation_view);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        modeOfLogin = getIntent().getStringExtra("modeOfLogin");

        username = findViewById(R.id.username);
        gender = findViewById(R.id.gender);
        classstudy = findViewById(R.id.classstudy);
        dob = findViewById(R.id.dob);
        profileImage2 = findViewById(R.id.profile_image2);


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
                        Intent intent2 = new Intent(dashboard.this, dashboard.class);
                        intent2.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0, 0);
                        Toast.makeText(dashboard.this, "DashBoard", Toast.LENGTH_SHORT).show();
                        Log.i("clicked", "dashboard");
                        drawerLayout.closeDrawer(GravityCompat.END);
                        break;
                    case R.id.savedpdf:
                        Intent intent = new Intent(dashboard.this, savedPPdf.class);
                        intent.putExtra("modeOfLogin", modeOfLogin);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(dashboard.this, "Saved Pdf", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(dashboard.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aboutUs:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(dashboard.this, "About", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contactus:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        Toast.makeText(dashboard.this, "Contact Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        startActivity(new Intent(dashboard.this, setting_menu.class));
                        overridePendingTransition(0, 0);
                        drawerLayout.closeDrawer(GravityCompat.END);
                        finish();
                        Toast.makeText(dashboard.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        navigationView.bringToFront();

        updateUavigation();

        TextView searchView = findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, Search_Screen.class));
            }
        });


        firestore.collection("User").document(modeOfLogin).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                username.setText(documentSnapshot.getString("Name"));
                gender.setText(documentSnapshot.getString("Gender"));
                classstudy.setText(documentSnapshot.getString("Class"));
                dob.setText(documentSnapshot.getString("Date"));
                Picasso.get().load(documentSnapshot.getString("ImageUri")).into(profileImage2);

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
                Intent intent = new Intent(dashboard.this, MyBio.class);
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