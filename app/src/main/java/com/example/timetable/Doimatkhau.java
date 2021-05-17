package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Database.Userdata;

public class Doimatkhau extends AppCompatActivity {

    EditText tendangnhap, mkcu, matkhau, xacnhanmk;
    public static String hoten, diachi, sdt, email;
    Button btn_xacnhan;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);

        tendangnhap = findViewById(R.id.tendangnhap);
        mkcu = findViewById(R.id.mkcu);
        matkhau = findViewById(R.id.mkm);
        xacnhanmk = findViewById(R.id.xnmkm);
        btn_xacnhan = findViewById(R.id.btn_xacnhan);

    }
    public void capnhatml(View view){
        String tk = tendangnhap.getText().toString().trim();
        String mkcu1 = mkcu.getText().toString().trim();
        String mkm1 = matkhau.getText().toString().trim();
        String xnmk1 = xacnhanmk.getText().toString().trim();
        Query check = FirebaseDatabase.getInstance().getReference("User").orderByChild("tendangnhap").equalTo(tk);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String checkpassword = snapshot.child(tk).child("matkhau").getValue(String.class);
                    if (checkpassword.equals(mkcu1)){
                        if (mkm1.equals(xnmk1)){
                            hoten = snapshot.child(tk).child("hoten").getValue(String.class);
                            email = snapshot.child(tk).child("email").getValue(String.class);
                            sdt = snapshot.child(tk).child("sdt").getValue(String.class);
                            diachi = snapshot.child(tk).child("diachi").getValue(String.class);

                            reference = FirebaseDatabase.getInstance().getReference().child("User");
                            Userdata userdata = new Userdata(MainActivity.hoten,tk,MainActivity.email,MainActivity.sdt,xnmk1,MainActivity.diachi);
                            reference.child(tk).setValue(userdata);
                        Toast.makeText(Doimatkhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Doimatkhau.this,"Nhập sai mật khẩu vui lòng nhập lại",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Doimatkhau.this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Doimatkhau.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }
}