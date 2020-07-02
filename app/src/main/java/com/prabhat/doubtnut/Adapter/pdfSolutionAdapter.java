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

import com.prabhat.doubtnut.Model.Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.pdfView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class pdfSolutionAdapter extends RecyclerView.Adapter<pdfSolutionAdapter.MyViewHolder> {

    List<Model> list;
    Context context;

    public pdfSolutionAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_solution, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final String Link = list.get(position).getLink();

        Log.i("pdflink", Link + "");

        holder.tittle.setText(list.get(position).getTittle());
        Picasso.get().load(list.get(position).getImageUri()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), pdfView.class);
                intent.putExtra("Link", Link);
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

        TextView tittle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            tittle=itemView.findViewById(R.id.tittle);

        }
    }
}
