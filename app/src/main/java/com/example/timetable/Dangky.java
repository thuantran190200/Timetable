package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


import Database.Userdata;

import static android.widget.Toast.LENGTH_SHORT;

public class Dangky extends AppCompatActivity {

    Button btntrolaidangnhap;
    Button btnDangky;
    TextInputLayout hoten, tendangnhap, email,sdt,matkhau2,matkhau;
    //FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog;
    //FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dangky);

        progressDialog= new ProgressDialog(this);
        btntrolaidangnhap = findViewById(R.id.btn_trolaidangnhap);
        btnDangky = findViewById(R.id.btndangky);
        hoten=findViewById(R.id.name);
        email=findViewById(R.id.email);
        tendangnhap=findViewById(R.id.username1);
        sdt=findViewById(R.id.sdt);
        matkhau=findViewById(R.id.matkhau);
        matkhau2=findViewById(R.id.nhaplaimatkhau);



        btntrolaidangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Dangky.this, Dangnhap.class);
                finish();
                /*Intent intent = new Intent( Dangky.this, Dangnhap.class);
                startActivity(intent);*/
            }
        });

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference().child("User");

                //reference = FirebaseDatabase.getInstance("https://timetable-ee30a-default-rtdb.firebaseio.com/").getReference().child("User");
                reference = FirebaseDatabase.getInstance().getReference().child("User");
                 String ho_ten = hoten.getEditText().getText().toString();
                 String ten_dangnhap = tendangnhap.getEditText().getText().toString();
                 String mail = email.getEditText().getText().toString();
                 String sodienthoai = sdt.getEditText().getText().toString();
                 String mat_khau = matkhau.getEditText().getText().toString();
                 String mat_khau2 = matkhau2.getEditText().getText().toString();
                if(TextUtils.isEmpty(ho_ten)||TextUtils.isEmpty(ten_dangnhap)||TextUtils.isEmpty(mail)||TextUtils.isEmpty(sodienthoai) ||TextUtils.isEmpty(mat_khau))
                {
                    Toast.makeText(Dangky.this, "vui lòng nhập đầy đủ thông tin", LENGTH_SHORT).show();
                }/*else if(!isvalidEmail(mail)) {
                    email.setError("Email không hợp lệ");
                    return;
                } else if (mat_khau2.equals(mat_khau)) {
                    matkhau2.setError("Mật khẩu không khớp");
                }*/


                Userdata userdata = new Userdata(ho_ten, ten_dangnhap, mail, sodienthoai, mat_khau, "");

                reference.child(sodienthoai).setValue(userdata);


                Toast.makeText(Dangky.this, "Đăng ký thành công", LENGTH_SHORT).show();
                finish();


            }
        });
    }


   private boolean isvalidEmail(CharSequence target){
        return(!TextUtils.isEmpty(target)&& Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}