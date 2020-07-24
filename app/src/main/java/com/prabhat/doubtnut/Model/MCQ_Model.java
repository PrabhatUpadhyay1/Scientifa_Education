package com.prabhat.doubtnut.Model;

import android.widget.LinearLayout;

import java.util.List;

public class MCQ_Model {

    List<String> Questions;
    List<String> OptionA;
    List<String> OptionB;
    List<String> OptionC;
    List<String> OptionD;
    List<String> Answer;

    public MCQ_Model(){}

    public MCQ_Model(List<String> questions, List<String> optionA, List<String> optionB, List<String> optionC, List<String> optionD, List<String> answer) {
        Questions = questions;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        Answer = answer;
    }

    public List<String> getQuestions() {
        return Questions;
    }

    public List<String> getOptionA() {
        return OptionA;
    }

    public List<String> getOptionB() {
        return OptionB;
    }

    public List<String> getOptionC() {
        return OptionC;
    }

    public List<String> getOptionD() {
        return OptionD;
    }

    public List<String> getAnswer() {
        return Answer;
    }

}
