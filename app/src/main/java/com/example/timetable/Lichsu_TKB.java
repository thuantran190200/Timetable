package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Lichsu_TKB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu_tkb);
    }
    /*private void  GetDataFromFireBase(){
        //String sdt = txtThangnNam.getText().toString().trim();
        //luu y cho nay xem no lam gi
        //Query query = reference.orderByChild("date").equalTo(date);
        //Query check_sdt =reference.orderByChild("sdt").equalTo(sdt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //so sánh số điện thoại người dùng và id(số điện thoại) của thời khóa biểu giống thì lấy
                    /*if(){}*/
    /*                if (snapshot.hasChildren()){
                        String key = dataSnapshot.getKey();
                        String title = snapshot.child(key).child("title").getValue(String.class);
                        String location = snapshot.child(key).child("location").getValue(String.class);
                        String description = snapshot.child(key).child("description").getValue(String.class);
                        String dateFB = snapshot.child(key).child("date").getValue(String.class);
                        String time = snapshot.child(key).child("time").getValue(String.class);
                        String date_end = snapshot.child(key).child("date_end").getValue(String.class);
                        String time_end = snapshot.child(key).child("time_end").getValue(String.class);
                        String reminder = snapshot.child(key).child("reminder").getValue(String.class);
                        String tietBD = snapshot.child(key).child("tietBD").getValue(String.class);
                        String sotiethoc = snapshot.child(key).child("sotiethoc").getValue(String.class);
                        String sdt = snapshot.child(key).child("sdt").getValue(String.class);
                        TimeTable timeTable = new TimeTable(key, title, location, dateFB, time,date_end,time_end,reminder,tietBD,sotiethoc,description,sdt);
                        list.add(timeTable);
                    }
                }
                adapter = new RecyclerViewTimeTable(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}