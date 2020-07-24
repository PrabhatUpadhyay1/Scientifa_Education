package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.MyDoubtModel;
import com.prabhat.doubtnut.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class myDoubtAdapter extends RecyclerView.Adapter<myDoubtAdapter.MyViewHolder> {

    List<MyDoubtModel> list;
    Context context;

    public myDoubtAdapter(List<MyDoubtModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_doubt_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//
//        final String Link = list.get(position).getLink();
//
//        Log.i("pdflink", Link + "");

        holder.doubtText.setText(list.get(position).getDoubtText());

        Picasso.get().load(list.get(position).getDoubtImage()).into(holder.doubtImage);

        holder.Answered.setText(list.get(position).getAnswered());

//
//        holder.doubtImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.doubtImage.getContext(), pdfView.class);
////                intent.putExtra("Link", Link);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView doubtText,Answered;
        ImageView doubtImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            doubtText = itemView.findViewById(R.id.doubt_text);
            doubtImage = itemView.findViewById(R.id.doubt_image);
            Answered = itemView.findViewById(R.id.doubt_answered);
        }
    }
}
