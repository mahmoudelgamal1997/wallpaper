package com.example2017.android.wallpaper;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



public class First extends AppCompatActivity {

    int flag=0;
    int Time_onCreat=72000000;
    ProgressDialog pro ;
    int sum;
    int num;
    EditText edit_time;
    String selected;
    Bitmap yourimage;
    Drawable d;
    String filepath;
    public static final int selected_picture = 1;
    File mypath;
    static File directory;
    SharedPreferences sh;

    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(First.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-7819737441034557/1983433345");

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });






        pro =new ProgressDialog(this);
        edit_time = (EditText) findViewById(R.id.alltime);



    //to add Spinner Items
        final ArrayList<String> time_type =new ArrayList<String>();
        time_type.add("Minute");
        time_type.add("Hour");
        time_type.add("Day");


        final  Spinner sub = (Spinner) findViewById(R.id.spinner_timer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time_type);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sub.setAdapter(adapter);

//method of Spinner
        sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //to get item_selected name
                selected=time_type.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    public int calculate_time(String s) {

        if (edit_time.getText().toString().equals("")){
            num=5;

        }else {
            num= Integer.parseInt((edit_time.getText().toString()));
        }

        switch (selected) {
            case "Minute":
                sum = num * 60 * 1000;
                break;

            case "Hour":
                sum = num * 60 * 60 * 1000;
                break;
            case "Day":
                sum = num * 60 * 60 * 24 * 1000;
                break;
        }

        return sum;
    }

    // button to start image in wallpaper
    public void StartService(View view)

    {

        File directory = new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");
        if (directory.isDirectory()) {

            String d = edit_time.getText().toString();

            //to move time value to hello_class
            sh = getSharedPreferences("plz", Context.MODE_PRIVATE);
            SharedPreferences.Editor mydata = sh.edit();
            mydata.putString("data", String.valueOf((calculate_time(selected))));
            mydata.commit();


            //to show time of work to user
            Toast.makeText(getApplicationContext(), "" + calculate_time(selected), Toast.LENGTH_LONG).show();


            // if user doesn't put time
            if (d.equals("")) {

                Toast.makeText(getApplicationContext(), "wallpaper will change every  5  Minute", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "wallpaper will change every " + d + " " + selected, Toast.LENGTH_LONG).show();
            }


            startService(new Intent(getBaseContext(), hello.class));


        }else{
            Toast.makeText(getApplicationContext(), "You should Select Images to Start", Toast.LENGTH_LONG).show();

        }


    }







    // to select image from your gallery
    public void gallery(View view)
    {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, selected_picture);
    }


    //to delete all images in file
    public void clear(View view)
    {

        File f=new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");
        deleteRecursive(f);
        Toast.makeText(this,"All Images deleted",Toast.LENGTH_LONG).show();

    }

    //to delete file
    void deleteRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    //function to save image which we select from gallery in internal storage

    private String saveToInternalStorage(Bitmap bitmapImage) {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        directory = cw.getDir("ImageDir", Context.MODE_PRIVATE);
        // Create imageDir
        //yourimage.toString()  means the image name  in file we created
        //we save image's name like its name in gallery
        mypath = new File(directory, yourimage.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                File directory = new File("/data/data/com.example2017.android.wallpaper/app_ImageDir");
                File[] listFiles = directory.listFiles();


                    Toast.makeText(getApplication(), "Done,You have selected  " +
                            ""+listFiles.length, Toast.LENGTH_LONG).show();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return directory.getAbsolutePath();
    }


    //when u go to any intent when return, this function carry your data from this intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selected_picture) {
            if (resultCode == RESULT_OK) {



                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(projection[0]);
                filepath = cursor.getString(column);
                cursor.close();

                yourimage = BitmapFactory.decodeFile(filepath);
                d = new BitmapDrawable(yourimage);



                saveToInternalStorage(yourimage);
            }

        }
    }

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    public    void starttime(int time){


// we make object from class that extends counter to call function from it
      //  Intent intent = new Intent(this,First.class);
       // startActivity(intent);

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

            if(isNetworkAvailable()){
                // do your thing here.

                intent();
            }
                starttime(Time_onCreat);


        }}




    @Override
    protected void onStop()
    {
        // call the superclass method first
        super.onStop();
        sh=getSharedPreferences("pleaz",Context.MODE_PRIVATE );


        flag=sh.getInt("Flag",0);
        if (flag==0)
    {
        starttime(Time_onCreat);
    flag++;
    sh=getSharedPreferences("pleaz",Context.MODE_PRIVATE );
    SharedPreferences.Editor  mydata=sh.edit();
    mydata.putInt( "Flag",flag);
    mydata.commit();

    }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }



    void intent ()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }



}