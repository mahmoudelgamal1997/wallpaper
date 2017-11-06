package com.example2017.android.wallpaper;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by M7moud on 22-Sep-17.
 */
public class product {

     public Bitmap img ;
    public String txt;

    public product(Bitmap image){

        this.img=image;
    }
    public product(String s){

        this.txt=s;
    }

}
