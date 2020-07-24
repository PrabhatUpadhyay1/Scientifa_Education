package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Maths_Model;
import com.prabhat.doubtnut.R;

import java.util.List;

public class bookSolutionAdapter extends RecyclerView.Adapter<bookSolutionAdapter.MyViewHolder> {

    List<Maths_Model> list;
    Context context;

    public bookSolutionAdapter(List<Maths_Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_solution_itemview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//
//        final String Link = list.get(position).getLink();
//
//        Log.i("pdflink", Link + "");
//
//        Picasso.get().load(list.get(position).getImageUri()).into(holder.imageView);
//
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.imageView.getContext(), pdfView.class);
//                intent.putExtra("Link", Link);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
