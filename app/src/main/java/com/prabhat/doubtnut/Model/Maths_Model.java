package com.prabhat.doubtnut.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Maths_Model {

    ArrayList<String> ImageThumbnail;
    ArrayList<String> Pdf;
    ArrayList<String> Link;
    ArrayList<String> Tittle;

    public ArrayList<String> getImageThumbnail() {
        return ImageThumbnail;
    }

    public ArrayList<String> getPdf() {
        return Pdf;
    }

    public ArrayList<String> getLink() {
        return Link;
    }

    public ArrayList<String> getTittle() {
        return Tittle;
    }

    public Maths_Model(ArrayList<String> imageThumbnail, ArrayList<String> pdf, ArrayList<String> link, ArrayList<String> tittle) {
        ImageThumbnail = imageThumbnail;
        Pdf = pdf;
        Link = link;
        Tittle = tittle;
    }

    public Maths_Model(){}

}