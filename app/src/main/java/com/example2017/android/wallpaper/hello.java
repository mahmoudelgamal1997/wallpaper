package com.example2017.android.wallpaper;

import android.app.Activity;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.app.PendingIntent.getActivity;

/**
 * Created by M7moud on 18-Mar-17.
 */
public class hello  extends Service  {
    int timer;
SharedPreferences sh;
    int count=0;
    public   File[] listFiles;
   public   Bitmap Image_list;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {


        WallpaperManager wall=WallpaperManager.getInstance(getApplicationContext());



        try{
            wall.setBitmap(getFilePaths());
            count++;
        }catch (IOException e) {
            e.printStackTrace();
        }


//repeat process
        starttime();



        return START_STICKY;
    }






    public    void starttime(){

        int timer=0;
        sh=getSharedPreferences("plz",Context.MODE_PRIVATE );
        String s=( sh.getString( "data","")) ;
        timer = Integer.parseInt(String.valueOf(s));




// we make object from class that extends counter to call function from it
    Counterm ct  =  new Counterm(timer,1000);
        ct.start();


    }




    class Counterm extends CountDownTimer {

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



        startService(new Intent(getBaseContext(), hello.class));



    }}

    public Bitmap getFilePaths() {

        File directory = new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");

        // check for directory
        Image_list = null;
        if (directory.isDirectory()) {
            // getting list of file paths
            listFiles = directory.listFiles();

            if (count<listFiles.length){

                Image_list = BitmapFactory.decodeFile(String.valueOf((listFiles[count])));

              //  image=Image_list;
            }else
                count=0;
                 Image_list = BitmapFactory.decodeFile(String.valueOf((listFiles[count])));
        //    image=Image_list;

        }


        return Image_list;

    }





}





