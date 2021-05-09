package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Giaodien_trangchu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodien_trangchu);

        /*--------------------------Hooks----------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.s_ho_n_h_o_t_c_t_ng_ch_t_m_t, R.string.n_c_n_b_n_tay_c_a_th_i_gian);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //
        switch (item.getItemId()){
            case R.id.nav_tkb:
                onBackPressed();
                break;
            case R.id.nav_login:
                Intent intent = new Intent(Giaodien_trangchu.this, Thongtincanhan.class);
                startActivity(intent);
                break;
            case R.id.nav_change:
                //Sessionmanager.logoutUser();
                finish();
                break;


        }

        return true;
    }
    private void showUser(){
        Intent intent = getIntent();
        String ho_ten = intent.getStringExtra("hoten");

        //fullname.setText(ho_ten);
    }
}