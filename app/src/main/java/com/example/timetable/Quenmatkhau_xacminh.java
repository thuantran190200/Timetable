package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Quenmatkhau_xacminh extends AppCompatActivity {

    Button btnback;
    Button btnsms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quenmatkhau_xacminh);

        btnsms = findViewById(R.id.sms_screen);

        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Quenmatkhau_xacminh.this, Quenmatkhau.class);
                startActivity(intent1);
            }
        });

    }
    public void back_screen(View view) {
            Intent intent = new Intent( Quenmatkhau_xacminh.this, Dangnhap.class);
            finish();
    }
}