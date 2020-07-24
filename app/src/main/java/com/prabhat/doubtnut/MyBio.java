package com.prabhat.doubtnut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBio extends AppCompatActivity {

    RadioGroup radioGroup, radioGroup2;
    RadioButton radioButton, radioButton2;
    FirebaseFirestore firestore;
    String gender, Class, Name, Date;
    EditText name;
    DatePicker datePicker;
    AppCompatRadioButton tenth, twelfth;
    int gallary_pic = 200;
    CircleImageView imageViewinput;
    Uri imageuri;
    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bio);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firestore= FirebaseFirestore.getInstance();
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.rbClass);
        tenth = findViewById(R.id.tenth);
        twelfth = findViewById(R.id.twelth);
        firestore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.name);
        datePicker = findViewById(R.id.date_picker);
        imageViewinput = findViewById(R.id.profile_image);
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
                    Class = "Tenth";
                    Log.i("hhh", Class);
                }
                break;

            case R.id.twelth:
                tenth.setBackground(getResources().getDrawable(R.drawable.buttonshape2));
                tenth.setTextColor(getResources().getColor(R.color.colorPrimary));
                twelfth.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                twelfth.setTextColor(getResources().getColor(R.color.White));
                Class = "Twelfth";
                Log.i("hhh", Class);
        }
//        Class = radioButton2.getText().toString();
//        switch (v.getId()) {
//            case R.id.tenth:
//                Toast.makeText(this, "tenth clicked", Toast.LENGTH_SHORT).show();
//                Class = "Tenth Class";
//                v.setBackground(getResources().getDrawable(R.drawable.buttonshape));
//                v.setBackgroundColor(getResources().getColor(R.color.White));
//                break;
//            case R.id.twelth:
//                Toast.makeText(this, "twelve clicked", Toast.LENGTH_SHORT).show();
//                Class = "Twelfth Class";
//                v.setBackground(getResources().getDrawable(R.drawable.buttonshape));
//                break;
    }

    public void radioClick(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
        gender = radioButton.getText().toString();
    }

    public void saveData(View view) {
        Name = name.getText().toString().trim();
        Date = datePicker.getDayOfMonth() + "_" + datePicker.getMonth() + "_" + datePicker.getYear();
        Log.i("date", Date + "");

        if (validate()) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Gender", gender);
            map.put("Name", Name);
            map.put("Date", Date);
            map.put("Class", Class);
            map.put("ImageUri", imageuri);
            firestore.collection("UserInfo").document(auth.getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MyBio.this, "saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MyBio.this, Home.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyBio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate() {
        if (Name == null) {
            return false;
        }
        if (gender == null) {
            return false;
        }
        if (Class == null) {
            return false;
        }
        if (Date == null) {
            return false;
        }
        if (Date.equals("27_06_2020")) {
            return false;
        }

        return true;

//        if (Name != null && gender != null && Class != null && Date != null && !Date.equals("27_06_2020")) {
//            return true;
//        }
//        return false;
    }

    public void setProfilePic(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, gallary_pic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageuri = data.getData();
        imageViewinput.setImageURI(imageuri);
        Log.i("imagelll", imageuri + "");
    }
}
