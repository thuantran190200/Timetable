package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Database.Userdata;

public class Thongtincanhan extends AppCompatActivity {

    DatabaseReference Mdata;
    TextInputLayout hoten1,email1,sdt1,diachi1;
    TextView hotenlabel, taikhoanlabel;
    Button btn_capnhat;
    public static boolean capnhat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincanhan);



        //tìm giá trị nút
        hotenlabel =findViewById(R.id.hoten_hoso);
        taikhoanlabel = findViewById(R.id.hoten_taikhoan);
        hoten1 = findViewById(R.id.hoten_tt);
        email1 = findViewById(R.id.email_tt);
        sdt1 = findViewById(R.id.sdt_tt);
        diachi1 = findViewById(R.id.diachi_tt);
        btn_capnhat = findViewById(R.id.btncapnhat);




        Sessionmanager sessionmanager = new Sessionmanager(getApplicationContext(), Sessionmanager.SESSION_USER);
        HashMap<String, String> userdetail = sessionmanager.getInfomationUser();

        String email = userdetail.get(sessionmanager.KEY_EMAIL);
        String hoten = userdetail.get(sessionmanager.KEY_HOTEN);
        String sdt = userdetail.get(sessionmanager.KEY_SDT);
        String taikhoan = userdetail.get(sessionmanager.KEY_TENDANGNHAP);
        thongtincanhan();
        loaddata();




       /* if(sessionmanager.ckecklogin())
        {

            email1.getEditText().setText(userdetail.get(Sessionmanager.KEY_TENDANGNHAP));
            hotenlabel.setText(userdetail.get(Sessionmanager.KEY_HOTEN));

            hoten1.getEditText().setText(userdetail.get(Sessionmanager.KEY_HOTEN));

            sdt1.getEditText().setText(userdetail.get(Sessionmanager.KEY_EMAIL));
            taikhoanlabel.setText(userdetail.get(Sessionmanager.KEY_SDT));

        }*/



        //hien thi thong tin ca nhan
        //showAllUserData();

    }
    /*private void showAllUserData(){
        Intent intent = getIntent();
        String tai_khoan = intent.getStringExtra("tendangnhap");
        String ho_ten = intent.getStringExtra("hoten");
        String user_email = intent.getStringExtra("email");
        String user_sdt = intent.getStringExtra("sodienthoai");
        //String dia_chi = intent.getStringExtra("diachi");

        hotenlabel.setText(ho_ten);
        taikhoanlabel.setText(tai_khoan);
        hoten1.getEditText().setText(ho_ten);
        email1.getEditText().setText(user_email);
        sdt1.getEditText().setText(user_sdt);
        //diachi1.getEditText().setText(diachi);

    }*/
    public void thongtincanhan(){
        hotenlabel.setText(MainActivity.hoten);
        hoten1.getEditText().setText(MainActivity.hoten);
        taikhoanlabel.setText(MainActivity.taikhoan);
        email1.getEditText().setText(MainActivity.email);
        sdt1.getEditText().setText(MainActivity.sdt);
        diachi1.getEditText().setText(MainActivity.diachi);
    }
    public void loaddata(){
        if (!capnhat) {
            hotenlabel.setText(MainActivity.hoten);
            hoten1.getEditText().setText(MainActivity.hoten);
            taikhoanlabel.setText(MainActivity.taikhoan);
            email1.getEditText().setText(MainActivity.email);
            sdt1.getEditText().setText(MainActivity.sdt);
            diachi1.getEditText().setText(MainActivity.diachi);
        }
    }
    public void  capnhap(View view){

        Mdata = FirebaseDatabase.getInstance().getReference().child("User");
        Userdata userdata = new Userdata(MainActivity.hoten, MainActivity.taikhoan,MainActivity.email,MainActivity.sdt,MainActivity.matkhau,diachi1.getEditText().getText().toString().trim());
        Mdata.child(MainActivity.taikhoan).setValue(userdata);
        Toast.makeText(Thongtincanhan.this, "Cập nhật thành công",Toast.LENGTH_SHORT).show();

    }
}