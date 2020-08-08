package com.prabhat.doubtnut.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Chapter_Model;
import com.prabhat.doubtnut.Quiz_Screen;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.videoView;

import java.util.List;

import javax.xml.namespace.QName;

public class chapterAdapter extends RecyclerView.Adapter<chapterAdapter.MyViewHolder> {

    List<String> Chapter;
    Context context;
    String modeOfLogin;
    String category,subject,level,chap;

    public chapterAdapter(String category,String subject,String level,String chap,String modeOfLogin,List<String> chapter, Context context) {
        Chapter = chapter;
        this.category=category;
        this.subject=subject;
        this.chap=chap;
        this.level=level;
        this.modeOfLogin=modeOfLogin;
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
                Intent intent = new Intent(holder.open.getContext(), Quiz_Screen.class);
                intent.putExtra("modeOfLogin",modeOfLogin);
                intent.putExtra("category",category);
                intent.putExtra("chap",chap);
                intent.putExtra("subject",subject);
                intent.putExtra("level",level);
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
