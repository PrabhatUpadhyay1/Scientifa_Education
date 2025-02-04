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

import com.prabhat.doubtnut.Model.Maths_Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.pdfView;

import java.util.List;

public class pdfSolutionAdapter extends RecyclerView.Adapter<pdfSolutionAdapter.MyViewHolder> {

    List<String> list,list2;
    Context context;
    String modeFoLogin;

    public pdfSolutionAdapter(String modeFoLogin,List<String> list, List<String> list2, Context context) {
        this.list = list;
        this.modeFoLogin=modeFoLogin;
        this.list2 = list2;
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

        final String Link = list2.get(position);
        final String Tittle=list.get(position);
        holder.tittle.setText(list.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), pdfView.class);
                intent.putExtra("Link", Link);
                intent.putExtra("Tittle",Tittle);
                intent.putExtra("modeOfLogin",modeFoLogin);
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
