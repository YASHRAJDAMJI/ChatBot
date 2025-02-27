package com.tutorial.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {
LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.playAnimation();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(Splash.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       },5000);
    }
}