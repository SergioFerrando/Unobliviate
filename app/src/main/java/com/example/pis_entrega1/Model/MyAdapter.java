package com.example.pis_entrega1.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pis_entrega1.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pis_entrega1.Note.Notes;
import com.example.pis_entrega1.Note.Recording;
import com.example.pis_entrega1.Note.Text;
import com.example.pis_entrega1.R;

import java.util.ArrayList;


/**
 * Class that generates an Adapter to the recycler view
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{

    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private ArrayList<Notes> localDataSet = new ArrayList<Notes>();

    /**
     * Constructor f the class
     * @param context app context
     * @param data List of notes to set
     */
    public MyAdapter(Context context, ArrayList<Notes> data) {
        mInflater = LayoutInflater.from(context);
        this.localDataSet = data;
    }

    /**
     * Method to initialize the view Holder
     * @param parent ViewGroup
     * @param viewType int
     * @return new View Holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    /**
     * Method to set the appearance of the holder
     * @param holder
     * @param position
     */
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

    /**
     * Method that returns size of list of notes
     * @return
     */
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Method to set on click listener for the List
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    /**
     * Method that takes every on click is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(listener!= null){
            listener.onClick(v);
        }
    }

    /**
     * Class view Holder that have all elements of Row_item
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Title;
        TextView Type;
        TextView Date;
        ImageView item;

        /**
         * Constructor of the class
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.image);
        }
    }

}