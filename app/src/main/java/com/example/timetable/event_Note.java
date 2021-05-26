package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class event_Note extends AppCompatActivity {
    int t1hour, t1minute, t2hour, t2minute;
    EditText event_title, event_description, event_location;
    Toolbar toolbar;
    TextView txtThangnNam;
    private String pattern = "dd/MM/yyyy";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    final Calendar mCalendar = Calendar.getInstance();
    DatabaseReference reference;
    String id, sdt;
    DatePickerDialog.OnDateSetListener setListener;
    public static boolean isCheck2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_note);

        event_title = findViewById(R.id.event_title);
        event_description = findViewById(R.id.event_description);
        txtThangnNam = findViewById(R.id.txtThangNam);
        txtThangnNam.setText(dateInString);


        toolbar = findViewById(R.id.toolbar2);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("Note");
        loadData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCheck2) {
            getMenuInflater().inflate(R.menu.custom_menu_trash, menu);
        } else {
            getMenuInflater().inflate(R.menu.custom_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //thêm sự kiện môn vào
        //if (batloitenmon() && batloisophong() && batloisongaybd() && batloitgbd()) {
            if (item.getItemId() == R.id.check) {
                if (!isCheck2) {
                    txtThangnNam.getText().toString().trim();
                    String key = reference.push().getKey();
                    Note note = new Note(key, event_title.getText().toString(), event_description.getText().toString(),
                            txtThangnNam.getText().toString(), MainActivity.sdt);
                    reference.child(key).setValue(note);
                    this.finish();
                    Toast.makeText(event_Note.this, "Tạo thành công", Toast.LENGTH_SHORT).show();

                } else {
                    reference.child(id).child("title").setValue(event_title.getText().toString().trim());
                    reference.child(id).child("description").setValue(event_description.getText().toString().trim());
                    reference.child(id).child("date").setValue(txtThangnNam.getText().toString().trim());
                    reference.child(id).child("sdt").setValue(MainActivity.sdt);

                    this.finish();
                    Toast.makeText(event_Note.this, "Đang lưu....", Toast.LENGTH_SHORT).show();
                }
            }
        //}
        //xóa 1 sự kiện môn đã thêm vào
        if (item.getItemId() == R.id.trash) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(event_Note.this);
            alertDialogBuilder.setMessage("Bạn có chắc muốn xóa?");
            alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = reference.orderByChild("id").equalTo(id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(event_Note.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            });
            alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialogBuilder.show();



        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData() {
        if (isCheck2 == true) {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            event_title.setText(intent.getStringExtra("title"));
            event_description.setText(intent.getStringExtra("description"));
            txtThangnNam.setText(intent.getStringExtra("date"));
        }
    }
}