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
    ArrayList<Work> list = new ArrayList<>();
    RecyclerViewWork adapter;
    RecyclerView recyclerView1;
    ImageButton imbPlus2;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoatdong);

        imbPlus2 = findViewById(R.id.imbPlus2);
        GetDataFromFireBase1();
        imbPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hoatdong.this, Them_cv_tkb.class);
                activity_event.isCheck = false;
                startActivity(intent);
            }
        });
    }

    private void GetDataFromFireBase1() {

        reference = FirebaseDatabase.getInstance().getReference().child("Work");
        Query query = reference.orderByChild("sdt").equalTo(MainActivity.sdt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        String key = dataSnapshot.getKey();
                        String title = dataSnapshot.child("title").getValue(String.class);
                        String location = dataSnapshot.child("location").getValue(String.class);
                        String description = dataSnapshot.child("description").getValue(String.class);
                        String dateFB = dataSnapshot.child("date").getValue(String.class);
                        String time = dataSnapshot.child("time").getValue(String.class);
                        String time_end = dataSnapshot.child("time_end").getValue(String.class);
                        String reminder = dataSnapshot.child("reminder").getValue(String.class);
                        String sdt = dataSnapshot.child("sdt").getValue(String.class);

                        if(MainActivity.sdt.equals(sdt)) {
                            Work work = new Work(key, title, location, description, dateFB, time, time_end, reminder, sdt);
                            list.add(work);
                        }
                    }
                }
                recyclerView1 = findViewById(R.id.recyclerViewhistory1);
                recyclerView1.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Hoatdong.this);
                adapter = new RecyclerViewWork(getApplicationContext(), list);
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