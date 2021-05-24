package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

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
import java.util.HashMap;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class activity_event extends AppCompatActivity {

    int t1hour, t1minute, t2hour, t2minute;
    EditText event_title, event_description, event_tietbd, event_sotiet, event_location;
    TextView event_date, event_date_end, event_time, event_time_end, event_reminder;
    Toolbar toolbar;
    final Calendar mCalendar = Calendar.getInstance();
    String selected = "Không nhắc nhở";
    DatabaseReference reference;
    String id, sdt;
    //
    DatePickerDialog.OnDateSetListener setListener;
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

        //
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        //
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("TimeTable");
        loadData();
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                mCalendar.set(Calendar.YEAR, year);
//                mCalendar.set(Calendar.MONTH, month);
//                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateDate();
//            }
//        };
        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_event.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String ngay = "";
                        String thang = "";
                        if (day < 10) {
                            ngay = "0" + day;
                        } else {
                            ngay = String.valueOf(day);
                        }
                        if (month < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf(month + 1);
                        }
                        String date = ngay + "/" + thang + "/" + year;

                        event_date.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        event_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_event.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String ngay = "";
                        String thang = "";
                        if (day < 10) {
                            ngay = "0" + day;
                        } else {
                            ngay = String.valueOf(day);
                        }
                        if (month < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf(month + 1);
                        }
                        String date = ngay + "/" + thang + "/" + year;
                        event_date_end.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        activity_event.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1hour = hourOfDay;
                                t1minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t1hour, t1minute);
                                event_time.setText(DateFormat.format("kk:mm", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t1hour, t1minute);
                timePickerDialog.show();
            }
        });
        event_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        activity_event.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2hour = hourOfDay;
                                t2minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, t2hour, t2minute);
                                event_time_end.setText(DateFormat.format("kk:mm", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(t2hour, t2minute);
                timePickerDialog.show();
                //DialogFragment timePickerend = new TimePickerFragmentEnd();
                //timePickerend.show(getSupportFragmentManager(), "Time Picker end");
            }
        });
        event_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity_event.this);
                final String[] options = {"Không nhắc nhở", "Khi bắt đầu", "Nhắc nhở trước 5 phút", "Nhắc nhở trước 15 phút", "Nhắc nhở trước 30 phút", "Nhắc nhở trước 1 giờ"};
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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCheck) {
            getMenuInflater().inflate(R.menu.custom_menu_trash, menu);
        } else {
            getMenuInflater().inflate(R.menu.custom_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean batloitenmon() {
        event_title = findViewById(R.id.event_title);
        String tenmon = event_title.getText().toString().trim();
        if (tenmon.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để tên môn trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloisophong() {
        event_location = findViewById(R.id.event_location);
        String sophong = event_location.getText().toString().trim();
        if (sophong.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để số phòng trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloitietbd() {
        event_tietbd = findViewById(R.id.event_tietbd);
        String tietbd = event_location.getText().toString().trim();
        if (tietbd.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để tiết bắt đầu trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloisotiet() {
        event_sotiet = findViewById(R.id.event_sotiet);
        String sotiet = event_sotiet.getText().toString().trim();
        if (sotiet.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để số tiết học trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloisongaybd() {
        event_date = findViewById(R.id.event_date);
        String ngaybd = event_date.getText().toString().trim();
        if (ngaybd.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để ngày bắt đầu trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloitgbd() {
        event_time = findViewById(R.id.event_time);
        String tgbd = event_time.getText().toString().trim();
        if (tgbd.isEmpty()) {
            Toast.makeText(activity_event.this, "Không để thời gian bắt đầu trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //thêm sự kiện môn vào
        if (batloitenmon() && batloisophong() && batloitietbd() && batloisotiet() && batloisongaybd() && batloitgbd()) {
            if (item.getItemId() == R.id.check) {

                if (!isCheck) {


                    event_date.getText();
                    String key = reference.push().getKey();
                    //Sessionmanager sessionmanager = new Sessionmanager(getApplicationContext(), Sessionmanager.SESSION_USER);
                    TimeTable timeTable = new TimeTable(key, event_title.getText().toString(), event_location.getText().toString(), event_tietbd.getText().toString(),
                            event_sotiet.getText().toString(), event_description.getText().toString(), event_date.getText().toString(), event_time.getText().toString(),
                            event_date_end.getText().toString(), event_time_end.getText().toString(), event_reminder.getText().toString(), MainActivity.sdt);
                    reference.child(key).setValue(timeTable);
                    this.finish();
                    Toast.makeText(activity_event.this, "Tạo thành công", Toast.LENGTH_SHORT).show();


                } else {
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
                    reference.child(id).child("sdt").setValue(MainActivity.sdt);

                    this.finish();
                    Toast.makeText(activity_event.this, "Đang lưu....", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //xóa 1 sự kiện môn đã thêm vào
        if (item.getItemId() == R.id.trash) {
            Query query = reference.orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(activity_event.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
//    private void updateDate(){
//        String myFormat = "dd/MM/yyyy";
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        event_date.setText(sdf.format(mCalendar.getTime()));
//        event_date_end.setText(sdf.format(mCalendar.getTime()));
//    }


    private void loadData() {
        if (isCheck == true) {
            Intent intent = getIntent();
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
