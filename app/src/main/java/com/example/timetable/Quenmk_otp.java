package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//tự thêm vào

//
import java.util.concurrent.TimeUnit;

import Database.Userdata;

public class Quenmk_otp extends AppCompatActivity {
    private Button btn_xacminh;
    PinView pinFormUser;
    TextView show_phone;
    String fullname, username, phoneNo, email, password, date, gender, WhatToDo;
    FirebaseDatabase roootNode;
    DatabaseReference reference;
    String codeSystem;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quenmk__o_t_p);

        btn_xacminh = findViewById(R.id.xacminh_screen);

        btn_xacminh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Quenmk_otp.this, Quenmatkhau_mkmoi.class);
                startActivity(intent1);
            }
        });


        //FirebaseApp.initializeApp(this);


        pinFormUser = findViewById(R.id.code_otp);
        show_phone = findViewById(R.id.phone_opt);

        roootNode = FirebaseDatabase.getInstance();
        reference = roootNode.getReference("User");

        fullname = getIntent().getStringExtra("hoten");
        username = getIntent().getStringExtra("tendangnhap");
        phoneNo = getIntent().getStringExtra("sodienthoai");
        email = getIntent().getStringExtra("email");
        WhatToDo = getIntent().getStringExtra("WhatToDo");
        show_phone.setText(phoneNo);



    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {//@org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential
            super.onCodeSent(s, forceResendingToken);
            codeSystem = s;
        }


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                pinFormUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Quenmk_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        //PhoneAuthCredential credential = PhoneAuthCredential.getCredential(codeSystem, code);
        //signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, (task) -> {
            if (task.isSuccessful()) {
                if (WhatToDo.equals("updateData")) {
                    updateOldUserData();
                } else {
                    storeNewUsersData();
                }
            } else {
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(Quenmk_otp.this, "Chưa hoàn thành mã xác minh! hãy thử lại", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void updateOldUserData() {
        Intent intent = new Intent(getApplicationContext(), Quenmatkhau_mkmoi.class);
        intent.putExtra("sodienthoai", phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUsersData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("User");
        Userdata addNewuser = new Userdata();
        reference.child(phoneNo).setValue(addNewuser);
    }
    private void sendCode(String phonenumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS, this, mcallbacks);

    }



    public void close_screen(View view) {
        Intent intent = new Intent(Quenmk_otp.this, Quenmatkhau_xacminh.class);
        finish();
    }
}