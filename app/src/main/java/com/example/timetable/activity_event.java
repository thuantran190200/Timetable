package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class activity_event extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    EditText event_title,event_description,event_tietbd,event_sotiet,event_location;
    TextView event_date, event_date_end, event_time, event_time_end, event_reminder;
    Toolbar toolbar;
    final Calendar mCalendar = Calendar.getInstance();
    String selected ="Không nhắc nhở";
    DatabaseReference reference;
    String id, sdt;
    public static boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //tim gia trị nút
        event_title = findViewById(R.id.event_title);
        event_location = findViewById(R.id.event_location);
        event_tietbd = findViewById(R.id.event_tietbd);
        event_sotiet = findViewById(R.id.event_sotiet);
        event_description = findViewById(R.id.event_description);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
        event_date_end = findViewById(R.id.event_dateend);
        event_time_end = findViewById(R.id.event_timeend);
        event_reminder = findViewById(R.id.event_reminder);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("TimeTable");
        loadData();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new DialogFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });
        event_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity_event.this);
                final String [] options = {"Không nhắc nhở","Khi bắt đầu","Nhắc nhở trước 5 phút","Nhắc nhở trước 15 phút", "Nhắc nhở trước 30 phút", "Nhắc nhở trước 1 giờ"};
                mBuilder.setTitle("Vui lòng chọn");
                mBuilder.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = options[which];
                        Toast.makeText(activity_event.this, "Bạn đã chọn" + selected, Toast.LENGTH_SHORT).show();
                        event_reminder.setText(selected);
                        dialog.dismiss();
                    }
                });
                mBuilder.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(isCheck){
            getMenuInflater().inflate(R.menu.custom_menu_trash, menu);
        }else {
            getMenuInflater().inflate(R.menu.custom_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.check){
            if (!isCheck){
                event_date.getText();
                String key = reference.push().getKey();
                TimeTable timeTable = new TimeTable(key, event_title.getText().toString(),event_location.getText().toString(), event_tietbd.getText().toString(),event_sotiet.getText().toString(), event_description.getText().toString(),event_date.getText().toString(),event_time.getText().toString(), event_date_end.getText().toString(),event_time_end.getText().toString()/*,Sessionmanager.KEY_SDT*/);
                reference.child(key).setValue(timeTable);
                this.finish();
                Toast.makeText(activity_event.this,"Tạo thành công",Toast.LENGTH_SHORT).show();
            }else {
                    reference.child(id).child("title").setValue(event_title.getText().toString().trim());
                    reference.child(id).child("location").setValue(event_location.getText().toString().trim());
                    reference.child(id).child("tietbd").setValue(event_tietbd.getText().toString().trim());
                    reference.child(id).child("sotiet").setValue(event_sotiet.getText().toString().trim());
                    reference.child(id).child("description").setValue(event_description.getText().toString().trim());
                    reference.child(id).child("date").setValue(event_date.getText().toString().trim());
                    reference.child(id).child("time").setValue(event_time.getText().toString().trim());
                    reference.child(id).child("date_end").setValue(event_date_end.getText().toString().trim());
                    reference.child(id).child("time_end").setValue(event_time_end.getText().toString().trim());
                    reference.child(id).child("reminder").setValue(event_reminder.getText().toString().trim());
                    //reference.child(id).child("sdt").setValue(Sessionmanager.KEY_SDT.trim());

                    this.finish();
                    Toast.makeText(activity_event.this,"Đang lưu....",Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() ==R.id.trash){

        }
        return super.onOptionsItemSelected(item);
    }
    private void updateDate(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        event_date.setText(sdf.format(mCalendar.getTime()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        event_time.setText(hourOfDay + ":" + minute);
    }

    private void loadData(){
        if (isCheck==true){
            Intent intent =getIntent();
            id = intent.getStringExtra("id");
            event_title.setText(intent.getStringExtra("title"));
            event_location.setText(intent.getStringExtra("location"));
            event_tietbd.setText(intent.getStringExtra("tietbd"));
            event_sotiet.setText(intent.getStringExtra("sotiet"));
            event_description.setText(intent.getStringExtra("description"));
            event_date.setText(intent.getStringExtra("date"));
            event_time.setText(intent.getStringExtra("time"));
            event_date_end.setText(intent.getStringExtra("date_end"));
            event_time_end.setText(intent.getStringExtra("time_end"));
            event_reminder.setText(intent.getStringExtra("reminder"));
        }
    }
}
