package com.example.timetable;

public class TimeTable {


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSotiet(){ return sotiet;}

    public void setSotiet(String sotiet){this.sotiet = sotiet;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReminder() {
        return reminder;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
    private String title;

    public TimeTable(String id,String title, String location, String tietbd, String sotiet, String description, String date, String time, String date_end, String time_end, String reminder, String sdt) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.tietbd = tietbd;
        this.sotiet = sotiet;
        this.description = description;
        this.date = date;
        this.time = time;
        this.date_end = date_end;
        this.time_end = time_end;
        this.reminder = reminder;
        this.sdt = sdt;
    }
    public TimeTable(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String location;
    private String tietbd;
    private String sotiet;
    private String description;
    private String date;
    private String time;
    private String date_end;
    private String time_end;
    private String sdt;

    public String getTietbd() {
        return tietbd;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setTietbd(String tietbd) {
        this.tietbd = tietbd;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
/* public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }*/

    private String reminder;
    //private String sdt;
}
