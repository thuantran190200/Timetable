package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Hoatdong extends AppCompatActivity {
    ArrayList<TimeTable> list = new ArrayList<>();
    RecyclerViewTimeTable adapter;
    RecyclerView recyclerView1;
    ImageButton imbPlus2;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoatdong);

        imbPlus2 = findViewById(R.id.imbPlus2);

        GetDataFromFireBase();
        imbPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hoatdong.this, Them_cv_tkb.class);
                activity_event.isCheck = false;
                startActivity(intent);
            }
        });
    }

    private void GetDataFromFireBase() {

        String sdtmain = MainActivity.sdt.trim();
        reference = FirebaseDatabase.getInstance().getReference().child("Working");
        Query query = reference.orderByChild("sdt").equalTo(sdtmain);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //String TimeTable = dataSnapshot.getKey();
                    //String key = dataSnapshot.getKey();
                    //String sdt = snapshot.child(key).child("sdt").getValue(String.class);
                    //so sánh số điện thoại người dùng và id(số điện thoại) của thời khóa biểu giống thì lấy
                    // if (sdtdn == sdt) {
                    //
                    if (snapshot.exists()) {
                        String key = dataSnapshot.getKey();
                        String title = snapshot.child(key).child("title").getValue(String.class);
                        String location = snapshot.child(key).child("location").getValue(String.class);
                        //String tietBD = snapshot.child(key).child("tietbd").getValue(String.class);
                        //String sotiethoc = snapshot.child(key).child("sotiet").getValue(String.class);
                        String description = snapshot.child(key).child("description").getValue(String.class);
                        String dateFB = snapshot.child(key).child("date").getValue(String.class);
                        String time = snapshot.child(key).child("time").getValue(String.class);
                        String date_end = snapshot.child(key).child("date_end").getValue(String.class);
                        String time_end = snapshot.child(key).child("time_end").getValue(String.class);
                        String reminder = snapshot.child(key).child("reminder").getValue(String.class);
                        String sdt = snapshot.child(key).child("sdt").getValue(String.class);
                        //  if(MainActivity.sdt.equals(sdt)) {


                        TimeTable timeTable = new TimeTable(key, title, location, "", "", description, dateFB, time, date_end, time_end, reminder, sdt);
                        list.add(timeTable);
                        //       }

                    }
                }
                recyclerView1 = findViewById(R.id.recyclerViewhistory1);
                recyclerView1.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Hoatdong.this);
                adapter = new RecyclerViewTimeTable(getApplicationContext(), list);
                recyclerView1.setLayoutManager(layoutManager);
                recyclerView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}