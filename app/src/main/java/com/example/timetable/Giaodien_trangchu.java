package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Giaodien_trangchu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView fullname;
    //thoikhoabieu
    CalendarView mCalendarView;
    TextView txtThangnNam;
    private String pattern = "dd/MM/yyyy";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    ImageButton imbPlus;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<TimeTable> list = new ArrayList<>();
    RecyclerViewTimeTable adapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodien_trangchu);

        /*--------------------------Hooks----------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        //tkb
        mCalendarView = findViewById(R.id.mCalenderView);
        imbPlus = findViewById(R.id.imbPlus);
        txtThangnNam = findViewById(R.id.txtThangNam);
        txtThangnNam.setText(dateInString);

        //tkb calendar
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                list.clear();
                String ngay = "";
                String thang = "";
                if (dayOfMonth < 10) {
                    ngay = "0" + dayOfMonth;
                } else {
                    ngay = String.valueOf(dayOfMonth);
                }
                if (month < 10) {
                    thang = "0" + (month + 1);
                } else {
                    thang = String.valueOf(month + 1);
                }
                String date = ngay + "/" + thang + "/" + year;

                txtThangnNam.setText(date);

                reference = FirebaseDatabase.getInstance().getReference().child("TimeTable");
                //đặt if xét điều kiện tài khoản ở đây
                GetDataFromFireBase();

            }
        });
        imbPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Giaodien_trangchu.this, activity_event.class);
                activity_event.isCheck = false;
                startActivity(intent);
            }
        });
        //fullname =findViewById(R.id.hoten_nguoidung);
        /*---------------------hiển thị tên người dùng--------------*/
        showUser();

        /*--------------------------Tool bar------------------------*/
        setSupportActionBar(toolbar);

        /*--------------------------Navigation Drawer view---------*/

        //hide or show items
       /* Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_login).setVisible(false);*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.s_ho_n_h_o_t_c_t_ng_ch_t_m_t, R.string.n_c_n_b_n_tay_c_a_th_i_gian);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //
        switch (item.getItemId()) {
            case R.id.nav_tkb:
                onBackPressed();
                break;
            case R.id.nav_login:
                Intent intent = new Intent(Giaodien_trangchu.this, Thongtincanhan.class);
                startActivity(intent);
                break;
            case R.id.lichsuthemtkb:
                Intent intent2 = new Intent(Giaodien_trangchu.this, Lichsu_TKB.class);
                startActivity(intent2);
                break;
            case R.id.nav_change:
                Sessionmanager sessionmanager = new Sessionmanager(getApplicationContext(), Sessionmanager.SESSION_USER);
                sessionmanager.logoutUser();
                Intent intent4 = new Intent(Giaodien_trangchu.this, Dangnhap.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.nav_changepassword:
                Intent intent3 = new Intent(Giaodien_trangchu.this, Doimatkhau.class);
                startActivity(intent3);
                break;

        }

        return true;
    }

    private void showUser() {
        Intent intent = getIntent();
        String ho_ten = intent.getStringExtra("hoten");

        //fullname.setText(ho_ten);
    }

    //tkb timetable get data from firebase
    private void GetDataFromFireBase() {
        String date = txtThangnNam.getText().toString().trim();
        //String sdtdn = MainActivity.sdt.toString().trim();
        Query query = reference.orderByChild("date").equalTo(date);
        String sdtmain = MainActivity.sdt;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //String TimeTable = dataSnapshot.getKey();
                    String key = dataSnapshot.getKey();
                    String sdt = snapshot.child(key).child("sdt").getValue(String.class);
                    //so sánh số điện thoại người dùng và id(số điện thoại) của thời khóa biểu giống thì lấy
                    // if (sdtdn == sdt) {

                    if (snapshot.exists() && sdtmain.equals(sdt)) {
                        //String key = dataSnapshot.getKey();
                        String title = snapshot.child(key).child("title").getValue(String.class);
                        String location = snapshot.child(key).child("location").getValue(String.class);
                        String tietBD = snapshot.child(key).child("tietbd").getValue(String.class);
                        String sotiethoc = snapshot.child(key).child("sotiet").getValue(String.class);
                        String description = snapshot.child(key).child("description").getValue(String.class);
                        String dateFB = snapshot.child(key).child("date").getValue(String.class);
                        String time = snapshot.child(key).child("time").getValue(String.class);
                        String date_end = snapshot.child(key).child("date_end").getValue(String.class);
                        String time_end = snapshot.child(key).child("time_end").getValue(String.class);
                        String reminder = snapshot.child(key).child("reminder").getValue(String.class);

                        //String sdt = snapshot.child(key).child("sdt").getValue(String.class);
                        TimeTable timeTable = new TimeTable(key, title, location, tietBD, sotiethoc, description, dateFB, time, date_end, time_end, reminder, sdt);
                        list.add(timeTable);
                    }
                    //}
                }
                recyclerView = findViewById(R.id.recyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Giaodien_trangchu.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new RecyclerViewTimeTable(getApplicationContext(), list);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}