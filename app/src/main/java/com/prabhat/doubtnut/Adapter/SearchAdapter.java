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
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    List<String> list, list2, list3, list4;
    Context context;

    public SearchAdapter(ArrayList<String> list, ArrayList<String> list2, ArrayList<String> list3, ArrayList<String> list4, Context context) {
        this.list = list;
        this.list2 = list2;
        this.list3 = list3;
        this.list4 = list4;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//
        final String Link = list3.get(position);
//
        final String Tittle = list.get(position);
//
//        holder.videoTittle.setText(Tittle);
        final String PDF = list4.get(position);
//
//
//        Model model=new Model();
//        List<Model> thumbnell = list.get(position).getMathThumbnell();
////
//                    Picasso.get().load(list.get(position)).into(holder.imageView);
////
////
////        for ( String t: thumbnell) {
////            Log.i("thumbnelll", t);
////            model.setTittle(t);
////        }
        holder.videoTittle.setText(Tittle);
        Picasso.get().load(list2.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), videoView.class);
                intent.putExtra("Link", Link);
                intent.putExtra("Video Tittle", Tittle);
                intent.putExtra("PDF", PDF);
                context.startActivity(intent);
            }
        });


        //List<String> thumbnell=list.get(position).getMathThumbnell();
//        String Thumbnell=null;
//
//        for( int i = 0 ; i < thumbnell.size() ; i++) {
//            Thumbnell=thumbnell.get(i);
//        }
//        Picasso.get().load(Thumbnell).into(holder.imageView);
//        Log.i("uuu",Thumbnell);
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
