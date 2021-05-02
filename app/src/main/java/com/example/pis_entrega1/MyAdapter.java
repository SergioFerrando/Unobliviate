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


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private NotesContainer mNotesContainer;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor

    MyAdapter(Context context, NotesContainer data) {
        this.mInflater = LayoutInflater.from(context);
        this.mNotesContainer = data;
    }
    // inflates the row layout from xml when needed

    public void addNota (Notes nota){
        mNotesContainer.getContainer().add(nota);
    }

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
            DateFormat df = new SimpleDateFormat("HH:mm:ss dd:MM:yy");
            String dateText = df.format(nota.getDate());
            holder.Date.setText(dateText);
        }
    }

    // total number of rows
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

        ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.TextButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Notes getItem(int id) {
        return mNotesContainer.getContainer().get(id);
    }

    void setmNotesContainer(NotesContainer mNotesContainer){
        this.mNotesContainer = mNotesContainer;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


/*public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private NotesContainer container;
    private final Context parentContext;

    public MyAdapter(NotesContainer container, Context parentContext) {
        this.container = container;
        this.parentContext = parentContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Title;
        private final TextView Type;
        private final TextView Date;
        private final ImageButton item;
        private final LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);

            Title = view.findViewById(R.id.titleView);
            Type = view.findViewById(R.id.typeView);
            Date = view.findViewById(R.id.dateView);
            item = view.findViewById(R.id.TextButton);

            linearLayout = view.findViewById(R.id.linearLayout);
        }

        public TextView getTitle() {
            return Title;
        }

        public LinearLayout getLayout() {
            return linearLayout;
        }

        public ImageButton getPlayButton() {return item;}

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        int color = ContextCompat.getColor(parentContext, R.color.white);
        viewHolder.getLayout().setBackgroundColor(color);
        viewHolder.getTitle().setText((CharSequence) this.container.getContainer().get(position).getName());

        ImageButton playButton = viewHolder.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicButton (playButton, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return container.getContainer().size();
    }

    public void clicButton(ImageButton playButton, int position){
        if (playButton.getTag().equals("@drawable/tex")){
            Intent intent = new Intent(parentContext, TextNote.class);
            intent.putExtra("Text", (Parcelable) container.getContainer().get(position));
            intent.putExtra("Container", container);
            parentContext.startActivity(intent);
        }else if (playButton.getTag().equals("@drawable/micro")){
            Intent intent = new Intent(parentContext, AudioNote.class);
            intent.putExtra("Audio", (Parcelable) container.getContainer().get(position));
            intent.putExtra("Container", container);
            parentContext.startActivity(intent);
        }else if (playButton.getTag().equals("@drawable/camara")){
            Intent intent = new Intent(parentContext, PhotoNote.class);
            intent.putExtra("Photo", (Parcelable) container.getContainer().get(position));
            intent.putExtra("Container", container);
            parentContext.startActivity(intent);
        }
    }
}*/
