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

public class RecyclerViewNote extends RecyclerView.Adapter<RecyclerViewNote.ViewHolder>{
    @NonNull
    private Context mContext;
    ArrayList<Note> list;
    public RecyclerViewNote(Context mContext, ArrayList<Note> list){
        this.mContext = mContext;
        this.list = list;
    }
    public RecyclerViewNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new RecyclerViewNote.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewNote.ViewHolder holder, int position){
        holder.event_item_chude.setText(list.get(position).getTitle());
        holder.event_item_noidung.setText(list.get(position).getDescription());
        holder.event_item_holder_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,event_Note.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("date",list.get(position).getDate());
                event_Note.isCheck2 = true;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount(){return list.size();}
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView event_item_color_bar;
        TextView event_item_chude,event_item_noidung;
        RelativeLayout event_item_holder_note;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event_item_holder_note = itemView.findViewById(R.id.event_item_holder_note);
            event_item_color_bar = itemView.findViewById(R.id.event_item_color_bar);
            event_item_chude = itemView.findViewById(R.id.event_item_chude);
            event_item_noidung = itemView.findViewById(R.id.event_item_noidung);
        }
    }
}