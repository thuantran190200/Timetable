package com.example.timetable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewTimeTable extends RecyclerView.Adapter<RecyclerViewTimeTable.ViewHolder>{
    @NonNull
    private Context mContext;
    ArrayList<TimeTable> list;
    public RecyclerViewTimeTable(Context mContext, ArrayList<TimeTable> list){
        this.mContext = mContext;
        this.list = list;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new RecyclerViewTimeTable.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.event_item_time.setText(list.get(position).getTime());
        holder.event_item_title.setText(list.get(position).getTitle());
        holder.event_item_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,activity_event.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("location",list.get(position).getLocation());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("date",list.get(position).getDate());
                intent.putExtra("time",list.get(position).getTime());
                intent.putExtra("reminder",list.get(position).getReminder());
                activity_event.isCheck = true;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){return list.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView event_item_color_bar;
        TextView event_item_time,event_item_title;
        RelativeLayout event_item_holder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event_item_holder = itemView.findViewById(R.id.event_item_holder);
            event_item_color_bar = itemView.findViewById(R.id.event_item_color_bar);
            event_item_time = itemView.findViewById(R.id.event_item_time);
            event_item_title = itemView.findViewById(R.id.event_item_title);
        }
    }
}
