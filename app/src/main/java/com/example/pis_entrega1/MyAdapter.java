package com.example.pis_entrega1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Notes> container;
    private final Context parentContext;

    public MyAdapter(ArrayList<Notes> container, Context parentContext) {
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
            item = view.findViewById(R.id.itemView);

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
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        int color = ContextCompat.getColor(parentContext, R.color.white);
        viewHolder.getLayout().setBackgroundColor(color);
        viewHolder.getTitle().setText((CharSequence) this.container.get(position).getName());

        ImageButton playButton = viewHolder.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return container.size();
    }
}
