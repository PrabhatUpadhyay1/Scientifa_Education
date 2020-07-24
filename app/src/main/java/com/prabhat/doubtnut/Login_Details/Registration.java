package com.prabhat.doubtnut.Login_Details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.prabhat.doubtnut.MyBio;
import com.prabhat.doubtnut.OtpAfterRegistration;
import com.prabhat.doubtnut.R;

import java.util.HashMap;

public class Registration extends AppCompatActivity {
    ImageView back;
    Button registerButton;
    TextView all_rights;
    ImageView logo;
    TextView login;
    FirebaseFirestore mfirestore;
    Button google;
    //Google
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 100;
    TextInputEditText phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        google = findViewById(R.id.google);
        mfirestore = FirebaseFirestore.getInstance();

        login = findViewById(R.id.login_text);
        all_rights = findViewById(R.id.all_rights_);
        logo = findViewById(R.id.logo);
        phoneNumber = findViewById(R.id.phoneInput);
        countryCodePicker = findViewById(R.id.codepicker);

        // Configure Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login_Page.class);
                Pair[] pair = new Pair[2];

                pair[0] = new Pair<View, String>(all_rights, "text_transition");
                pair[1] = new Pair<View, String>(logo, "logo_transition");

                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(Registration.this, pair);
                startActivity(intent, option.toBundle());
            }
        });

        //Google text
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {

            Intent n = new Intent(Registration.this, MyBio.class);
            startActivity(n);
            finish();
            Toast.makeText(Registration.this, "Logged in", Toast.LENGTH_SHORT).show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("Email", googleSignInAccount.getEmail());
        hashMap.put("Name", googleSignInAccount.getDisplayName());

        mfirestore.collection("googleCustomer").document(googleSignInAccount.getId()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNewPhoneNumber(View view) {
        String userPhoneNumber = phoneNumber.getText().toString();

        if (userPhoneNumber.equals(null)) {
            phoneNumber.setError("Enter the your Mobile Number");
        }
        Intent intent = new Intent(Registration.this, OtpAfterRegistration.class);
        intent.putExtra("phoneNumber", "+" + countryCodePicker.getFullNumber() + userPhoneNumber);
        Log.i("prabhatno", "+" + countryCodePicker.getFullNumber() + userPhoneNumber);
        startActivity(intent);
    }
}