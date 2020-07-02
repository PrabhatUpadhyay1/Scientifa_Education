package com.prabhat.doubtnut.Model;

import android.net.Uri;

public class Model {
    String imageUri;
    String tittle;
    String link;
    String description;
    String pdf;


    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Model(String imageUri, String tittle, String link, String description,String pdf) {
        this.imageUri = imageUri;
        this.tittle = tittle;
        this.link = link;
        this.pdf=pdf;
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }


    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Model() {
    }
}
