package com.latte.oeuff.suicidepreventionapp.gallery.model;

import java.io.Serializable;

/**
 * Created by Oeuff on 19/07/2016.
 */

//For more information visits ->http://www.androidhive.info/2016/04/android-glide-image-library-building-image-gallery-app/
public class Image implements Serializable {
    private String name;
    private String medium;
    private String author;

    public Image() {
    }

    public Image(String name, String medium, String author) {
        this.name = name;
        this.medium = medium;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}