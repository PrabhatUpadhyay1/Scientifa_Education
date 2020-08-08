package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.videoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class videoSolutionAdapter1 extends RecyclerView.Adapter<videoSolutionAdapter1.MyViewHolder> {

    ArrayList<String> listId,list, list2, list3, list4;
    Context context;

    public videoSolutionAdapter1(ArrayList<String> listId,ArrayList<String> list, ArrayList<String> list2, ArrayList<String> list3, ArrayList<String> list4, Context context) {
        this.listId = listId;
        this.list = list;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;
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



        final String Tittle = list.get(position);

        Picasso.get().load(list2.get(position)).into(holder.imageView);

        final String Link = list3.get(position);

        final String PDF = list4.get(position);

        holder.videoTittle.setText(Tittle);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), videoView.class);
                intent.putExtra("Link", Link);
                intent.putExtra("Id",listId.get(position));
                intent.putExtra("Video Tittle", Tittle);
                intent.putExtra("PDF", PDF);
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
