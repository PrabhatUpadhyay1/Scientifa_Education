//package com.prabhat.doubtnut;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.material.card.MaterialCardView;
//
//public class Exams_Asked extends AppCompatActivity {
//
//    TextView ncert,jee,class11,class12,maths,chemistry,physics;
//    CardView classSelector;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exams__asked);
//        ncert=findViewById(R.id.ncert);
//        jee=findViewById(R.id.jee);
//        class11=findViewById(R.id.class11);
//        class12=findViewById(R.id.class12);
//        maths=findViewById(R.id.maths);
//        chemistry=findViewById(R.id.chemistry);
//        physics=findViewById(R.id.physics);
//
//        ncert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                classSelector.setVisibility(View.VISIBLE);
//            }
//        });
//
//        jee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                classSelector.setVisibility(View.GONE);
//            }
//        });
//    }
//}