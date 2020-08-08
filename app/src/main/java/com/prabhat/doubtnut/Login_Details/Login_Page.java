package com.prabhat.doubtnut.Login_Details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.prabhat.doubtnut.Home;
import com.prabhat.doubtnut.MyBio;
import com.prabhat.doubtnut.R;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.HashMap;
import java.util.Map;

public class Login_Page extends AppCompatActivity {

    Button google;
    TextView all_rights, signUpText;
    ImageView logo;
    int RC_SIGN_IN = 100;
    //Google
    private GoogleSignInClient mGoogleSignInClient;
    //Firebase
    FirebaseFirestore mfirestore;

    TextInputEditText phoneNumber;

    CountryCodePicker countryCodePicker;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    ImageView imageView;


    String modeOfLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        all_rights = findViewById(R.id.all_rights_);
        signUpText = findViewById(R.id.signup_text);
        google = findViewById(R.id.google);
        mfirestore = FirebaseFirestore.getInstance();
        phoneNumber = findViewById(R.id.phoneInput);
        countryCodePicker = findViewById(R.id.codepicker);

        // Configure Google Sign In

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //SignUp Text
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this, Registration.class);
                Pair[] pair = new Pair[2];

                pair[0] = new Pair<View, String>(all_rights, "text_transition");
                pair[1] = new Pair<View, String>(logo, "logo_transition");

                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(Login_Page.this, pair);
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

            Toast.makeText(Login_Page.this, "Logged in", Toast.LENGTH_SHORT).show();
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

        Intent n = new Intent(Login_Page.this, Home.class);
        modeOfLogin=account.getId().toString();
        n.putExtra("modeOfLogin",account.getId().toString());
        startActivity(n);
        finish();
    }

    public void getPhoneNumber(View view) {
        String userPhoneNumber = phoneNumber.getText().toString().trim();
        if (userPhoneNumber.isEmpty()) {
            phoneNumber.setEnabled(true);
            phoneNumber.setError("Enter the your Mobile Number");
        } else {
            Intent intent = new Intent(Login_Page.this, Otp.class);
            intent.putExtra("phoneNumber", "+" + countryCodePicker.getFullNumber() + userPhoneNumber);
            Log.i("prabhatno", "+" + countryCodePicker.getFullNumber() + userPhoneNumber);
            modeOfLogin=auth.getUid().toString();
            intent.putExtra("modeOfLogin",auth.getUid().toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(Login_Page.this,Home.class);
            intent.putExtra("modeOfLogin",modeOfLogin);
            startActivity(intent);
        }
        super.onStart();
    }
}