package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBio extends AppCompatActivity {
    private static final int gallaypick = 1;
    RadioGroup radioGroup, radioGroup2;
    RadioButton radioButton, radioButton2;
    String gender, Classs, Name, Date;
    EditText name;
    DatePicker datePicker;
    AppCompatRadioButton tenth, twelfth;
    CircleImageView imageViewinput;
    Uri imageuri;
    FirebaseAuth auth;
    StorageReference storageReference;
    String modeOfLogin;
    private GoogleSignInClient mGoogleSignInClient;
    Button save;
    String document;
    GoogleSignInAccount googleSignInAccount;
    FirebaseFirestore firestore;
    ProgressDialog dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bio);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        save = findViewById(R.id.save);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.rbClass);
        tenth = findViewById(R.id.tenth);
        twelfth = findViewById(R.id.twelth);
        name = findViewById(R.id.name);
        dialogClass = new ProgressDialog(this);
        firestore = FirebaseFirestore.getInstance();
        datePicker = findViewById(R.id.date_picker);
        imageViewinput = findViewById(R.id.profile_image);
        modeOfLogin = getIntent().getStringExtra("modeOfLogin");
        Log.i("TAG1", modeOfLogin);
        storageReference = FirebaseStorage.getInstance().getReference().child("profileimage");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

//
//        if (modeOfLogin.equals("phoneNumber")) {
//            document = auth.getCurrentUser().getPhoneNumber();
//        } else if (modeOfLogin.equals("goggle")) {
//            document = googleSignInAccount.getEmail();
//            imageuri = googleSignInAccount.getPhotoUrl();
//            Picasso.get().load(imageuri).into(imageViewinput);
//            name.setText(googleSignInAccount.getDisplayName());
//        }
//

        imageViewinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, gallaypick);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void classSelect(View v) {
        boolean isSelected = ((AppCompatRadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.tenth:
                if (isSelected) {
                    tenth.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                    tenth.setTextColor(getResources().getColor(R.color.White));
                    twelfth.setBackground(getResources().getDrawable(R.drawable.buttonshape2));
                    twelfth.setTextColor(getResources().getColor(R.color.colorPrimary));
                    Classs = "Tenth";
                }
                break;

            case R.id.twelth:
                tenth.setBackground(getResources().getDrawable(R.drawable.buttonshape2));
                tenth.setTextColor(getResources().getColor(R.color.colorPrimary));
                twelfth.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                twelfth.setTextColor(getResources().getColor(R.color.White));
                Classs = "Twelfth";
        }
    }

    public void radioClick(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
        gender = radioButton.getText().toString();
    }

    public void saveData() {
        Name = name.getText().toString().trim();
        Date = datePicker.getDayOfMonth() + "_" + datePicker.getMonth() + "_" + datePicker.getYear();
        Log.i("date", Date + "");

        if (Name == null) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }
        if (Classs == null) {
            Toast.makeText(this, "Please select your class", Toast.LENGTH_SHORT).show();
        }
        if (gender == null) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        }
        if(imageuri==null){
            Toast.makeText(this, "Please upload a profile image", Toast.LENGTH_SHORT).show();
        }
        if (Name != null && gender != null && Classs != null && imageuri != null) {
            dialogClass.show();
            dialogClass.setMessage("Please wait a moment");
            final StorageReference ref = storageReference.child("profileimage" + UUID.randomUUID());
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageLink = uri.toString();
                            List<String> list = new ArrayList<>();
                            List<String> list2 = new ArrayList<>();
                            List<String> list3 = new ArrayList<>();
                            List<String> list4 = new ArrayList<>();
                            HashMap<Object, Object> map = new HashMap<>();
                            map.put("Gender", gender);
                            map.put("Name", Name);
                            map.put("Date", Date);
                            map.put("Class", Classs);
                            map.put("ImageUri", imageLink);
                            map.put("doubtText", list);
                            map.put("doubtPhoto",list2);
                            map.put("PdfTittle",list3);
                            map.put("savedPdf",list4);
                            firestore.collection("User").document(modeOfLogin).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MyBio.this, "Logged In", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MyBio.this, Home.class);
                                    intent.putExtra("modeOfLogin",modeOfLogin);
                                    startActivity(intent);
                                    finish();
                                    dialogClass.hide();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MyBio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialogClass.hide();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyBio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogClass.hide();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyBio.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    dialogClass.hide();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallaypick && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            imageViewinput.setImageURI(imageuri);
        }
    }
}
