package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.blogView;
import com.prabhat.doubtnut.pdfView;

import java.util.List;

public class savedPdfAdapter extends RecyclerView.Adapter<savedPdfAdapter.MyViewHolder> {

    List<String> list,list2;
    Context context;
    String modeOfLogin;

    public savedPdfAdapter(String modeOfLogin,List<String> list, List<String> list2, Context context) {
        this.list = list;
        this.list2 = list2;
        this.modeOfLogin=modeOfLogin;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_pdf_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

       holder.tittle.setText(list.get(position));
       final String Link=list2.get(position);

        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.open.getContext(), pdfView.class);
                intent.putExtra("Link", Link);
                intent.putExtra("modeOfLogin",modeOfLogin);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tittle;
        Button open;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.tittle);
            open = itemView.findViewById(R.id.open);
        }
    }
}
