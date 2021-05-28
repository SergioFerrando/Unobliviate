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
import android.widget.ImageView;
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
import java.util.Date;
import java.util.Locale;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private ArrayList<Notes> localDataSet = new ArrayList<Notes>();

    // data is passed into the constructor

    MyAdapter(Context context, ArrayList<Notes> data) {
        mInflater = LayoutInflater.from(context);
        this.localDataSet = data;
    }
    // inflates the row layout from xml when needed

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public ArrayList<Notes> getLocalDataSet() {
        return localDataSet;
    }

    public void setLocalDataSet(ArrayList<Notes> localDataSet) {
        this.localDataSet = localDataSet;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes nota = localDataSet.get(position);
        holder.Title.setText(nota.getName());
        if (nota instanceof Text){
            holder.Type.setText("Text");
            holder.item.setImageResource(R.drawable.tex);
        }else if (nota instanceof Recording){
            holder.Type.setText("Recording");
            holder.item.setImageResource(R.drawable.micro);
        }else{
            holder.Type.setText("Photo");
            holder.item.setImageResource(R.drawable.camara);
        }
        if (nota.getDate() != null){
            holder.Date.setText(nota.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!= null){
            listener.onClick(v);
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title;
        TextView Type;
        TextView Date;
        ImageView item;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.image);
        }
    }

    // convenience method for getting data at click position
    Notes getItem(int id) {
        return localDataSet.get(id);
    }

}