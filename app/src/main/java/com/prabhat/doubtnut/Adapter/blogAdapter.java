package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.pdfView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class blogAdapter extends RecyclerView.Adapter<blogAdapter.MyViewHolder> {

    List<Model> list;
    Context context;

    public blogAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogitemview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//
//        final String Link = list.get(position).getLink();
//
//        Log.i("pdflink", Link + "");

        holder.tittle.setText(list.get(position).getTittle());

        holder.description.setText(list.get(position).getDescription());

        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.fullView.getContext(), pdfView.class);
//                intent.putExtra("Link", Link);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tittle,description;
        TextView fullView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle = itemView.findViewById(R.id.tittle);
            description = itemView.findViewById(R.id.description);
            fullView = itemView.findViewById(R.id.fullview);
        }
    }
}
