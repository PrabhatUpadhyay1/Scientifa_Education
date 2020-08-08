package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Maths_Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.blogView;
import com.prabhat.doubtnut.pdfView;

import java.util.List;

public class blogAdapter extends RecyclerView.Adapter<blogAdapter.MyViewHolder> {

    List<String> list,list2,list3;
    Context context;

    public blogAdapter(List<String> list, List<String> list2, List<String> list3, Context context) {
        this.list = list;
        this.list2 = list2;
        this.list3 = list3;
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

        final String Link = list3.get(position);

       holder.tittle.setText(list.get(position));
       holder.description.setText(list2.get(position));
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.fullView.getContext(), blogView.class);
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
