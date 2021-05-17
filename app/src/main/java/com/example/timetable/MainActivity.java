package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    //sử dụng biến toàn cục
    public static String hoten;
    public static String sdt;
    public  static String email;
    public  static String diachi;
    public  static String taikhoan;
    public static  String matkhau;
    //-------------
    private static int SPLASH_SCREEN = 4000;
    //variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogon, slogon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);// form dau s

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        image = findViewById(R.id.imageview);
        logo = findViewById(R.id.text1);
        slogon = findViewById(R.id.slogon1);
        slogon2 = findViewById(R.id.textView);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogon.setAnimation(bottomAnim);
        slogon2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( MainActivity.this, Dangnhap.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}