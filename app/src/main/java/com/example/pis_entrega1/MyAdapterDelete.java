package com.example.pis_entrega1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapterDelete extends RecyclerView.Adapter<MyAdapterDelete.ViewHolderToDelete> implements View.OnClickListener {

    ArrayList<Notes> toDelete;
    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private ArrayList<Notes> localDataSet = new ArrayList<Notes>();

    MyAdapterDelete(Context context, ArrayList<Notes> data) {
        this.mInflater = LayoutInflater.from(context);
        this.localDataSet = data;
        toDelete = new ArrayList<>();
    }

    public ArrayList<Notes> getLocalDataSet() {
        return localDataSet;
    }

    public void setLocalDataSet(ArrayList<Notes> localDataSet) {
        this.localDataSet = localDataSet;
    }


    @NonNull
    @Override
    public ViewHolderToDelete onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderToDelete(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderToDelete holder, int position) {
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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nota.setChecked(!nota.isChecked());
                notifyDataSetChanged();
            }
        });
        if (nota.isChecked()){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderToDelete extends RecyclerView.ViewHolder{
        TextView Title;
        TextView Type;
        TextView Date;
        ImageView item;
        LinearLayout linearLayout;

        public ViewHolderToDelete(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.image);
        }
    }

    public ArrayList<Notes> getSelected () {
        return toDelete;
    }

    public void addPosition (int i){
        if(localDataSet.get(i).isChecked()){
            toDelete.remove(localDataSet.get(i));
        }else {
            toDelete.add(localDataSet.get(i));
        }
        localDataSet.get(i).setChecked(!localDataSet.get(i).isChecked());
    }
}
