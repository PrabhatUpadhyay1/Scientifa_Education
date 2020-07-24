package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Maths_Model;
import com.prabhat.doubtnut.R;

import java.util.List;

public class recommendedVideoAdapter extends RecyclerView.Adapter<recommendedVideoAdapter.MyViewHolder> {

    List<Maths_Model> list;
    Context context;

    public recommendedVideoAdapter(List<Maths_Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//
//        final String Link = list.get(position).getLink();
//
//        Picasso.get().load(list.get(position).getImageUri()).into(holder.imageView);
//
////        final int Views = list.get(position).getViews();
//
////        holder.videoViews.setText(Views);
//
//
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.imageView.getContext(), videoView.class);
//                intent.putExtra("Link", Link);
////                intent.putExtra("Video view", Views);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView videoViews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
//            videoViews = itemView.findViewById(R.id.video_views);
        }
    }
}
