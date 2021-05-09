package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Database.CheckInternet;

public class Quenmatkhau_mkmoi extends AppCompatActivity {
    Button btnqmkm;
    TextInputLayout txt_matkhaumoi, txt_nhapmkmoi;

    final String ONE_UPPER_CASE = "^(?=.*[A-Z]).{6,}$";
    final String NO_SPACE = "^(?=\\S+$).{6,}$";
    final String MIN_CHAR = "^[a-zA-Z0-9._-].{5,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quenmatkhau_mkmoi);

        //hooks
        txt_matkhaumoi = findViewById(R.id.matkhaumoi);
        txt_nhapmkmoi =findViewById(R.id.nhapmkmoi);
        btnqmkm = findViewById(R.id.thaydoi_screen);

        btnqmkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quenmatkhau_mkmoi.this, Quenmk_thaydoithanhcong.class);
                startActivity(intent);
            }
        });



    }
    public void btn_setNewPassword(View view){
        CheckInternet checkInternet = new CheckInternet();
        /*if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }*/

        if (!validatePassword()){
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);

        //lấy dữ liệu từ fields
        String _newpassword = txt_matkhaumoi.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("sodienthoai");

        //cập nhật dữ liệu lên firebase và session
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(_phoneNumber).child("matkhau").setValue(_newpassword);
        startActivity(new Intent(getApplicationContext(),Quenmk_thaydoithanhcong.class));
        finish();

    }
    private boolean validatePassword() {
        String val = txt_matkhaumoi.getEditText().getText().toString().trim();
        String val1 = txt_nhapmkmoi.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            txt_matkhaumoi.setError("Mật khẩu không được để trống!");
            return false;
        } else if (val != val1) {
            txt_nhapmkmoi.setError("Nhập sai mật khẩu vui lòng nhập lại");
            return false;
        } else if (!val.matches(MIN_CHAR)) {
            txt_matkhaumoi.setError("Mật khẩu phải có ít nhất 6 kí tự!");
            return false;
        } else if (!val.matches(ONE_UPPER_CASE)) {
            txt_matkhaumoi.setError("Mật khẩu phải có ít nhất 1 chữ viết hoa!");
            return false;
        } else if (!val.matches(NO_SPACE)) {
            txt_matkhaumoi.setError("Mật khẩu không được để khoảng cách!");
            return false;
        } else {
            txt_matkhaumoi.setError(null);
            txt_matkhaumoi.setErrorEnabled(false);
            return true;
        }


    }
    public void backnew_screen(View view) {
        Intent intent = new Intent( Quenmatkhau_mkmoi.this, Quenmk_otp.class);
        finish();
    }
}