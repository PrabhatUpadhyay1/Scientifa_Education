package com.prabhat.doubtnut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class pdfView extends AppCompatActivity {

    pdfView pdf_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf__view);

        String url = getIntent().getStringExtra("Link");

        Log.i("kjk",url+"");
        final WebView webView;
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        String finalURL = "https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
        webView.loadUrl(finalURL);
    }
}