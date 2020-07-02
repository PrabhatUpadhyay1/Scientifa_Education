package com.prabhat.doubtnut.Model;

public class MyDoubtModel {

    private String doubtImage;
    private String doubtText;
    private String answered;


    public String getDoubtImage() {
        return doubtImage;
    }

    public void setDoubtImage(String doubtImage) {
        this.doubtImage = doubtImage;
    }

    public String getDoubtText() {
        return doubtText;
    }

    public void setDoubtText(String doubtText) {
        this.doubtText = doubtText;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public MyDoubtModel(String doubtImage, String doubtText, String answered) {
        this.doubtImage = doubtImage;
        this.doubtText = doubtText;
        this.answered = answered;
    }


    public MyDoubtModel() {

    }
}
