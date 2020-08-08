package com.prabhat.doubtnut.Model;

import java.util.List;

public class MyDoubtModel {

    private List<String> doubtPhoto;
    private List<String> doubtText;
    private List<String> answered;


    public List<String> getDoubtPhoto() {
        return doubtPhoto;
    }

    public List<String> getDoubtText() {
        return doubtText;
    }

    public List<String> getAnswered() {
        return answered;
    }

    public MyDoubtModel(List<String> doubtPhoto, List<String> doubtText, List<String> answered) {
        this.doubtPhoto = doubtPhoto;
        this.doubtText = doubtText;
        this.answered = answered;
    }

    public MyDoubtModel() {

    }
}
