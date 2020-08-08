package com.prabhat.doubtnut.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prabhat.doubtnut.Model.Chapter_Model;
import com.prabhat.doubtnut.Model.MCQ_Model;
import com.prabhat.doubtnut.R;
import com.prabhat.doubtnut.videoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class mcQuestionAdapter extends RecyclerView.Adapter<mcQuestionAdapter.MyViewHolder> {
    RadioGroup radioGroup;
    RadioButton radioButton;


    List<String> questionList, optionAList, optionBList, optionCList, optionDList, answer;
    String Chapter;
    Context context;

    public mcQuestionAdapter(List<String> questionList, List<String> optionAList, List<String> optionBList, List<String> optionCList, List<String> optionDList, List<String> answer, String chapter, Context context) {
        this.questionList = questionList;
        this.optionAList = optionAList;
        this.optionBList = optionBList;
        this.optionCList = optionCList;
        this.optionDList = optionDList;
        this.answer = answer;
        Chapter = chapter;
        this.context = context;
//        Log.i("chapter", Chapter);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mcq_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final String Question = questionList.get(position);
        holder.l1.setText(Chapter);
        holder.question.setText(Question);

        holder.radioOne.setText(optionAList.get(position));
        holder.radioTwo.setText(optionBList.get(position));
        holder.radioThree.setText(optionCList.get(position));
        holder.radioFour.setText(optionDList.get(position));

        final String[] correctAnswer = new String[1];
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_one:
                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        correctAnswer[0] = holder.radioOne.getText().toString();
                        break;
                    case R.id.radio_two:
                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                        correctAnswer[0] = String.valueOf(holder.radioTwo.getText());
                        break;
                    case R.id.radio_three:
                        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                        correctAnswer[0] = String.valueOf(holder.radioThree.getText());
                        break;
                    case R.id.radio_four:
                        correctAnswer[0] = String.valueOf(holder.radioFour.getText());
                        Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
                        break;
                }

                final String Answer = answer.get(position);
                Log.i("Answer",Answer);
                Log.i("Answer",correctAnswer[0]);

                holder.submit.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        if (Answer.equals(correctAnswer[0])) {
                            holder.result.setText("Awesome you are correct!  ");
                            holder.result.setTextColor(context.getResources().getColor(R.color.GREEN));
                            holder.result.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_sentiment_satisfied_alt_24,  0);
                        }
                        if (!Answer.equals(correctAnswer[0])) {
                            holder.result.setText("Oops you are incorrect!  ");
                            holder.result.setTextColor(context.getResources().getColor(R.color.RED));
                            holder.result.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_sentiment_very_dissatisfied_24, 0);

                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, l1;
        RadioButton radioOne, radioTwo, radioThree, radioFour, radioButton;
        Button submit;
        TextView result;
        RadioGroup radioGroup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            radioOne = itemView.findViewById(R.id.radio_one);
            radioTwo = itemView.findViewById(R.id.radio_two);
            radioThree = itemView.findViewById(R.id.radio_three);
            radioFour = itemView.findViewById(R.id.radio_four);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            l1 = itemView.findViewById(R.id.l1);
            submit = itemView.findViewById(R.id.submit);
            result = itemView.findViewById(R.id.result);
        }
    }
}
