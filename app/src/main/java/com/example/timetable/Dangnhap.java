package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dangnhap extends AppCompatActivity {
    ImageView logo_image;
    TextView logo_name,logon_name;
    TextInputLayout username,password;
    Button btn_dangky;
    Button btnquenmk;
    Button btndangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dangnhap3);
        btndangnhap = findViewById(R.id.dangnhap_screen);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });
        btn_dangky = findViewById(R.id.dangky_screen);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Dangnhap.this, Dangky.class);
                startActivity(intent);
            }
        });

        btnquenmk = findViewById(R.id.quenmk_screen);

        btnquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Dangnhap.this, Quenmatkhau_xacminh.class);
                startActivity(intent);
            }
        });

        //anhxa();

    }


    private Boolean xacnhantaikhoan(){
        username = findViewById(R.id.username);
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()){
            username.setError("Tài khoản trống vui lòng đăng nhập");
            return false;
        }else if(val.length()>= 15){
            username.setError("Tên đăng nhập quá dài");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            username.setError("Bạn không được viết ký tự đặc biệt");
            return false;
        }else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean xacnhanmatkhau(){
        password = findViewById(R.id.password);
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("Không để mật khẩu trống");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void LoginUser(){
        if( ! xacnhantaikhoan() | ! xacnhanmatkhau()){
            return;
        }else {
            isUser();
        }
    }

    private void isUser() {
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        Query checkuser = reference.orderByChild("tendangnhap").equalTo(userEnteredUsername);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);


                    String passwordFromDB = snapshot.child(userEnteredUsername).child("matkhau").getValue(String.class);

                    if(userEnteredPassword.equals(passwordFromDB)){

                        username.setError(null);
                        username.setErrorEnabled(false);

                        //Intent intent = new Intent(Dangnhap.this, Giaodien_trangchu.class);
                        //startActivity(intent);

                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String hotenFromDB = snapshot.child(userEnteredUsername).child("hoten").getValue(String.class);
                        String matkhauFromDB = snapshot.child(userEnteredUsername).child("matkhau").getValue(String.class);
                        String sdtFromDB = snapshot.child(userEnteredUsername).child("sodienthoai").getValue(String.class);
                        String taikhoanFromDB = snapshot.child(userEnteredUsername).child("tendangnhap").getValue(String.class);

                        String diachiFromDB = snapshot.child(userEnteredUsername).child("diachi").getValue(String.class);

                        //Create Database Store
                        Sessionmanager sessionmanager = new Sessionmanager(getApplicationContext(), Sessionmanager.SESSION_USER);
                        sessionmanager.createLoginSession(hotenFromDB, emailFromDB, sdtFromDB,taikhoanFromDB);

                        startActivity(new Intent(getApplicationContext(), Giaodien_trangchu.class));


                        /*if(Intent intent = new Intent(Giaodien_trangchu.this, Thongtincanhan.class)){

                        }*/
                        Intent intent1 = new Intent(getApplicationContext(), Thongtincanhan.class);
                        intent1.putExtra("tendangnhap",taikhoanFromDB);
                        intent1.putExtra("hoten", hotenFromDB);
                        intent1.putExtra("email", emailFromDB);
                        intent1.putExtra("sodienthoai", sdtFromDB);
                        //intent1.putExtra("diachi", diachiFromDB);
                        //startActivity(intent1);

                    }else {
                        password.setError("Sai mật khẩu");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("Tài khoảng không tồn tại");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }







    /*private void anhxa(){
        logo_image = findViewById(R.id.logo_image);
        logo_name = findViewById(R.id.logo_name);
        logon_name = findViewById(R.id.logon_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }*/



}