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

import com.prabhat.doubtnut.Quiz_Screen;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.afterCourseCategory;

import java.util.List;

public class chapterAdapter1 extends RecyclerView.Adapter<chapterAdapter1.MyViewHolder> {


    List<String> Chapter;
    String Class,data,modeOfLogin;
    Context context;

    public chapterAdapter1(String modeOfLogin,List<String> chapter,String Class,String data, Context context) {
        Chapter = chapter;
        this.modeOfLogin=modeOfLogin;
        this.Class = Class;
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final String chapter=(Chapter.get(position));
        holder.chapter.setText(chapter);
        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.open.getContext(), afterCourseCategory.class);
                intent.putExtra("class",Class);
                intent.putExtra("data",data);
                intent.putExtra("modeOfLogin",modeOfLogin);
                intent.putExtra("chapter",chapter);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Chapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chapter;
        Button open;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter = itemView.findViewById(R.id.chapter);
            open=itemView.findViewById(R.id.open);
        }
    }
}
