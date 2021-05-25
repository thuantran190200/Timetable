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

public class RecyclerViewWork extends RecyclerView.Adapter<RecyclerViewWork.ViewHolder>{
    @NonNull
    private Context mContext;
    ArrayList<Work> list;
    public RecyclerViewWork(Context mContext, ArrayList<Work> list){
        this.mContext = mContext;
        this.list = list;
    }
    public RecyclerViewWork.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable_couple, parent, false);
        return new RecyclerViewWork.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewWork.ViewHolder holder, int position){
        holder.event_item_time.setText(list.get(position).getTime());
        holder.event_item_time_end.setText(list.get(position).getTime_end());
        holder.event_item_title.setText(list.get(position).getTitle());
        holder.event_item_holder_couple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Them_cv_tkb.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("location",list.get(position).getLocation());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("date",list.get(position).getDate());
                intent.putExtra("time",list.get(position).getTime());
                intent.putExtra("time_end",list.get(position).getTime_end());
                intent.putExtra("reminder",list.get(position).getReminder());
                Them_cv_tkb.isCheck1 = true;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount(){return list.size();}
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView event_item_color_bar;
        TextView event_item_time,event_item_title,event_item_time_end;
        RelativeLayout event_item_holder_couple;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event_item_holder_couple = itemView.findViewById(R.id.event_item_holder_couple);
            event_item_color_bar = itemView.findViewById(R.id.event_item_color_bar);
            event_item_time = itemView.findViewById(R.id.event_item_time);
            event_item_time_end = itemView.findViewById(R.id.event_item_time_end);
            event_item_title = itemView.findViewById(R.id.event_item_title);
        }
    }
}
