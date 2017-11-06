package com.example2017.android.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Second extends AppCompatActivity {

    Bitmap Image_list;
    File[] listFiles;
    Bitmap resized;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new listResource(this));
        starttime();

    }






    class listResource extends BaseAdapter {

        ArrayList<product> mydata;
        Context context;

        listResource(Context context) {

            this.context = context;
            mydata = new ArrayList<product>();


         File directory = new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");
            if (directory.isDirectory()) {
                listFiles = directory.listFiles();


                for (int i = 0; i < listFiles.length; i++) {

                    //resized = Bitmap.createScaledBitmap(getFilePaths(i), 100, 100, true
                    //to resize image
                    mydata.add(new product(resized = Bitmap.createScaledBitmap(getFilePaths(i), 100, 100, true)));
                }

            }




            }




        @Override
        public int getCount() {

                return mydata.size();
        }

        @Override
        public Object getItem(int i) {


            return mydata.get(i);
        }

        @Override
        public long getItemId(int i) {

                return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.image_style, viewGroup, false);
            ImageView img = (ImageView) row.findViewById(R.id.image);
            TextView final_text =(TextView)row.findViewById(R.id.textView2);
            final product temp = mydata.get(i);


                img.setImageBitmap(temp.img);


                final_text.setText(temp.txt);

            return row;
        }




    }

    public Bitmap getFilePaths(int i) {

        File directory = new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");

        // check for directory
        Image_list = null;
        if (directory.isDirectory()) {

            // getting list of file paths
            listFiles = directory.listFiles();

            if (i < listFiles.length) {

                Image_list = BitmapFactory.decodeFile(String.valueOf((listFiles[i])));

            } else {
                i = 0;
                Image_list = BitmapFactory.decodeFile(String.valueOf((listFiles[i])));
                //    image=Image_list;

            }
        }

        return Image_list;
    }


    public    void starttime(){


// we make object from class that extends counter to call function from it
        gridView.setAdapter(new listResource(this));

        Counterm ct  =  new Counterm(2000,1000);
        ct.start();


    }

    public    void starttime(int time){


// we make object from class that extends counter to call function from it
        Intent intent = new Intent(this,First.class);
        startActivity(intent);
        Counterm ct  =  new Counterm(time,1000);
        ct.start();


    }


  public   class Counterm extends CountDownTimer {

        Counterm(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long l) {

        }


        //this function will do after time finish
        // so we put orders on it
        @Override
        public void onFinish() {


starttime();


        }}
}

