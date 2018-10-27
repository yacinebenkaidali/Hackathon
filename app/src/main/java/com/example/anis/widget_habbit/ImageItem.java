package com.example.anis.widget_habbit;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.widget.CheckBox;
import android.widget.Toast;

public class ImageItem {
    public Bitmap image;
    public String title;
    public int ID;
    public int nbr;

    public ImageItem(Bitmap image, String title,int id,int nbr) {
        super();
        this.image = image;
        this.title = title;
        this.ID=id;
        this.nbr=nbr;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
