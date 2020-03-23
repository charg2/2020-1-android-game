package com.example.smartphonegamep01;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate() called");
        TextView tv = findViewById(R.id.textView3);
        tv.setText("Launched");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public void OnButtonFist(View v)
    {
        TextView tv = findViewById(R.id.textView);

        tv.setText("First Button Pressed");
    }

    public void OnButtonThird(View v)
    {
        TextView tv = findViewById(R.id.textView);
        int count = 0;
        //try
        //{
//            count = Integer.parseInt((Sring));
        //}
    }

    public void onBtnMiddle(View view)
    {
        ImageView iv = findViewById(R.id.catImageView);
        iv.setImageResource(R.mipmap.cat2);

        Random random = new Random();
        int value = random.nextInt(100) + 1;

        TextView middleTextView = findViewById(R.id.textView);
        middleTextView.setText("Random number : " + value);
//
//        Handler handle = new Handler();
//        handle.postDelayed(())
//
    }

    public void onBtnBottom(View view)
    {
        ImageView iv = findViewById(R.id.catImageView);
        iv.setImageResource(R.mipmap.cat);
    }

}
