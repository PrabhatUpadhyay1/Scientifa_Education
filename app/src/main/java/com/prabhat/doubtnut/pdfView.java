package com.prabhat.doubtnut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class pdfView extends AppCompatActivity {

    pdfView pdf_view;
    Button savePdf;
    String url;
    String modeOfLogin,Tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf__view);

        Tittle=getIntent().getStringExtra("Tittle");
        url= getIntent().getStringExtra("Link");
        modeOfLogin=getIntent().getStringExtra("modeOfLogin");
        savePdf=findViewById(R.id.save_pdf);
        savePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                firestore.collection("User").document("modeOfLogin")
                        .update("PdfTittle", FieldValue.arrayUnion(Tittle));
                firestore.collection("User").document("modeOfLogin")
                        .update("savedPdf",FieldValue.arrayUnion(url));
            }
        });

        final WebView webView;
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        String finalURL = "https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
        webView.loadUrl(finalURL);
    }
}