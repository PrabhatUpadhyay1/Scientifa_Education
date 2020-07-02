package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prabhat.doubtnut.Home;
import com.prabhat.doubtnut.Model.Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.videoView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class videoSolutionAdapter extends RecyclerView.Adapter<videoSolutionAdapter.MyViewHolder> {

    List<Model> list;
    Context context;

    public videoSolutionAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_solution, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final String Link = list.get(position).getLink();

        Picasso.get().load(list.get(position).getImageUri()).into(holder.imageView);

        final String Tittle = list.get(position).getTittle();

        holder.videoTittle.setText(Tittle);
        final String PDF=list.get(position).getPdf();

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), videoView.class);
                intent.putExtra("Link", Link);
                intent.putExtra("Video Tittle", Tittle);
                intent.putExtra("PDF",PDF);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView videoTittle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            videoTittle = itemView.findViewById(R.id.video_tittle);
        }
    }
//
//    public void putLikes(int likes) {
//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("likes", likes);
//        firebaseFirestore.collection("videoSolution").document("7icFiGvq42y50hjExybE").update(map).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//
//            }
//        });
//    }
}
