package com.example.pis_entrega1;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private NotesContainer mNotesContainer;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    // data is passed into the constructor

    MyAdapter(Context context, NotesContainer data) {
        this.mInflater = LayoutInflater.from(context);
        this.mNotesContainer = data;
    }
    // inflates the row layout from xml when needed

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes nota = mNotesContainer.getContainer().get(position);
        holder.Title.setText(nota.getName());
        if (nota instanceof Text){
            holder.Type.setText("Text");
            holder.item.setImageAlpha(nota.getType());
        }else if (nota instanceof Recording){
            holder.Type.setText("Recording");
            holder.item.setImageAlpha(nota.getType());
        }else{
            holder.Type.setText("Photo");
            holder.item.setImageAlpha(nota.getType());
        }
        if (nota.getDate() != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.GERMANY);
            String date = df.format(Calendar.getInstance().getTime());
            holder.Date.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return mNotesContainer.getContainer().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title;
        TextView Type;
        TextView Date;
        ImageButton item;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.TextButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Notes getItem(int id) {
        return mNotesContainer.getContainer().get(id);
    }

    void setmNotesContainer(NotesContainer mNotesContainer){
        this.mNotesContainer = mNotesContainer;
    }

    void setmItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

}