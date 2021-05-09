package com.example.timetable;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

public class custom_calendar extends LinearLayout {

    ImageButton btn_next, btn_back;
    TextView txt_thangnam;
    GridView gridView;
    private static final int Max_calendar_day = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    List<Date> dates = new ArrayList<>();
    //List<Events> eventsList = new ArrayList<>();

    public custom_calendar(Context context) {
        super(context);
    }

    public custom_calendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
