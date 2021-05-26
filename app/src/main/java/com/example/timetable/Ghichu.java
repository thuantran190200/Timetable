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

public class Ghichu extends AppCompatActivity {
    ArrayList<Note> list = new ArrayList<>();
    RecyclerViewNote adapter;
    RecyclerView recyclerView1;
    ImageButton imbPlus3;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghichu);


        imbPlus3 = findViewById(R.id.imbPlus3);
        GetDataFromFireBase1();

        imbPlus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ghichu.this, event_Note.class);
                event_Note.isCheck2 = false;
                startActivity(intent);
            }
        });
    }
    private void GetDataFromFireBase1() {

        reference = FirebaseDatabase.getInstance().getReference().child("Note");
        Query query = reference.orderByChild("sdt").equalTo(MainActivity.sdt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        String key = dataSnapshot.getKey();
                        String title = dataSnapshot.child("title").getValue(String.class);
                        String description = dataSnapshot.child("description").getValue(String.class);
                        String dateFB = dataSnapshot.child("date").getValue(String.class);
                        String sdt = dataSnapshot.child("sdt").getValue(String.class);

                            Note note = new Note(key, title, description, dateFB,  sdt);
                            list.add(note);

                    }
                }
                recyclerView1 = findViewById(R.id.recyclerViewhistory1);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Ghichu.this);
                adapter = new RecyclerViewNote(getApplicationContext(), list);
                recyclerView1.setLayoutManager(layoutManager);
                recyclerView1.setHasFixedSize(true);
                recyclerView1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
    }
}