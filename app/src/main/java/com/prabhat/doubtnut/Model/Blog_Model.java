package com.prabhat.doubtnut.Model;

import java.util.List;

public class Blog_Model {

    List<String> Tittle;
    List<String> Description;
    List<String> Link;

    public Blog_Model(List<String> tittle, List<String> description, List<String> link) {
        Tittle = tittle;
        Description = description;
        Link = link;
    }

    public List<String> getTittle() {
        return Tittle;
    }

    public List<String> getDescription() {
        return Description;
    }

    public List<String> getLink() {
        return Link;
    }

    public Blog_Model(){}

}
