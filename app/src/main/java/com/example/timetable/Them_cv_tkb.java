package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class Them_cv_tkb extends AppCompatActivity {

    private int notificationId = 1;

    int t1hour, t1minute, t2hour, t2minute;
    EditText event_title, event_description, event_location;
    TextView event_date, event_date_end, event_time, event_time_end, event_reminder;
    Toolbar toolbar;
    final Calendar mCalendar = Calendar.getInstance();
    String selected = "Không nhắc nhở";
    DatabaseReference reference;
    String id, sdt;
    DatePickerDialog.OnDateSetListener setListener;
    public static boolean isCheck1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_cv_tkb);


        //tim gia trị nút
        event_title = findViewById(R.id.event_title);
        event_location = findViewById(R.id.event_location);
        event_description = findViewById(R.id.event_description);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
        event_time_end = findViewById(R.id.event_timeend);
        event_reminder = findViewById(R.id.event_reminder);
        toolbar = findViewById(R.id.toolbar1);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        reference = FirebaseDatabase.getInstance().getReference().child("Work");
        loadData();
        //


        //
        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Them_cv_tkb.this, new DatePickerDialog.OnDateSetListener() {
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


        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Them_cv_tkb.this,
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
                        Them_cv_tkb.this,
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
            }
        });
        event_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Them_cv_tkb.this);
                final String[] options = {"Không nhắc nhở", "Khi bắt đầu", "Nhắc nhở trước 5 phút", "Nhắc nhở trước 15 phút", "Nhắc nhở trước 30 phút", "Nhắc nhở trước 1 giờ"};
                mBuilder.setTitle("Vui lòng chọn");
                mBuilder.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = options[which];
                        Toast.makeText(Them_cv_tkb.this, "Bạn đã chọn" + selected, Toast.LENGTH_SHORT).show();
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
        if (isCheck1) {
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
            Toast.makeText(Them_cv_tkb.this, "Không để tên môn trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloisophong() {
        event_location = findViewById(R.id.event_location);
        String sophong = event_location.getText().toString().trim();
        if (sophong.isEmpty()) {
            Toast.makeText(Them_cv_tkb.this, "Không để số phòng trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloisongaybd() {
        event_date = findViewById(R.id.event_date);
        String ngaybd = event_date.getText().toString().trim();
        if (ngaybd.isEmpty()) {
            Toast.makeText(Them_cv_tkb.this, "Không để ngày bắt đầu trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean batloitgbd() {
        event_time = findViewById(R.id.event_time);
        String tgbd = event_time.getText().toString().trim();
        if (tgbd.isEmpty()) {
            Toast.makeText(Them_cv_tkb.this, "Không để thời gian bắt đầu trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        //thêm sự kiện môn vàop
        if (batloitenmon() && batloisophong() && batloisongaybd() && batloitgbd()) {
            if (item.getItemId() == R.id.check) {
                if (!isCheck1) {
                    event_date.getText();
                    String key = reference.push().getKey();

                    Work work = new Work(key, event_title.getText().toString(), event_location.getText().toString(), event_description.getText().toString(),
                            event_date.getText().toString(), event_time.getText().toString(), event_time_end.getText().toString(),
                            event_reminder.getText().toString(), MainActivity.sdt);

                    reference.child(key).setValue(work);
                    this.finish();
                    // Intent
                    Intent intent = new Intent(Them_cv_tkb.this, AlarmReceiver.class);
                    intent.putExtra("notificationId", notificationId);
                    intent.putExtra("message", event_description.getText().toString());

                    // PendingIntent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            Them_cv_tkb.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                    );

                    // AlarmManager
                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    // Create time.
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, 23);
                    startTime.set(Calendar.MINUTE, 05);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();

                    // Set Alarm
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                    Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Them_cv_tkb.this, "Tạo thành công", Toast.LENGTH_SHORT).show();

                } else {
                    reference.child(id).child("title").setValue(event_title.getText().toString().trim());
                    reference.child(id).child("location").setValue(event_location.getText().toString().trim());
                    reference.child(id).child("description").setValue(event_description.getText().toString().trim());
                    reference.child(id).child("date").setValue(event_date.getText().toString().trim());
                    reference.child(id).child("time").setValue(event_time.getText().toString().trim());
                    reference.child(id).child("time_end").setValue(event_time_end.getText().toString().trim());
                    reference.child(id).child("reminder").setValue(event_reminder.getText().toString().trim());
                    reference.child(id).child("sdt").setValue(MainActivity.sdt);

                    this.finish();
                    Toast.makeText(Them_cv_tkb.this, "Đang lưu....", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Them_cv_tkb.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        if (isCheck1 == true) {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            event_title.setText(intent.getStringExtra("title"));
            event_location.setText(intent.getStringExtra("location"));
            event_description.setText(intent.getStringExtra("description"));
            event_date.setText(intent.getStringExtra("date"));
            event_time.setText(intent.getStringExtra("time"));
            event_time_end.setText(intent.getStringExtra("time_end"));
            event_reminder.setText(intent.getStringExtra("reminder"));
        }
    }
}