package com.example.pis_entrega1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private ArrayList<Notes> localDataSet = new ArrayList<Notes>();

    MyAdapter(Context context, ArrayList<Notes> data) {
        mInflater = LayoutInflater.from(context);
        this.localDataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

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

}