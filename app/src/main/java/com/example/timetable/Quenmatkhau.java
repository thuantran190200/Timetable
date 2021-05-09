package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Quenmatkhau extends AppCompatActivity {

    Button btntieptuc;
    Button btnback;
    TextInputLayout sodienthoai;
    CountryCodePicker countryCodePicker;
    RelativeLayout progresBar;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quenmatkhau);
        FirebaseApp.initializeApp(this);
        //hooks
        sodienthoai = findViewById(R.id.txt_sdt);
        countryCodePicker = findViewById(R.id.coutry_phone);


    /*    btntieptuc = findViewById(R.id.tieptuc_screen);
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Quenmatkhau.this, Quenmk_otp.class);
                startActivity(intent);
            }
        });*/
    }


    public void xacnhan_opt(View view){
        //kiểm tra internet
        //CheckInternet checkInternet = new CheckInternet();


        //validate phone number
        //if(!validateFileds()){}
        //progresBar.setVisibility(View.VISIBLE);

        //get data from fields
        String _phoneNumber = sodienthoai.getEditText().getText().toString().trim();
        if(_phoneNumber.charAt(0)=='0'){
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _cpPhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        //check weather user exists or not in database
            Query checkUser = FirebaseDatabase.getInstance().getReference("User").child("sodienthoai").equalTo(_cpPhoneNumber);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//nằm kế bên @NonNull--- @org.jetbrains.annotations.NotNull
                    if(snapshot.exists()){
                        sodienthoai.setError(null);
                        sodienthoai.setErrorEnabled(false);
                        Intent intent = new Intent(getApplicationContext(), Quenmk_otp.class);
                        intent.putExtra("sodienthoai", _cpPhoneNumber);
                        intent.putExtra("WhatToDo", "updateData");
                        startActivity(intent);
                        finish();

                        //progresBar.setVisibility(View.GONE);

                    }else {
                        //progresBar.setVisibility(View.GONE);
                        sodienthoai.setError("Số điện thoại chưa đăng ký");
                        sodienthoai.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {//@org.jetbrains.annotations.NotNull

            }
        });
    }



    public void back_screen(View view) {
        Intent intent = new Intent( Quenmatkhau.this, Dangnhap.class);
        finish();
    }








}