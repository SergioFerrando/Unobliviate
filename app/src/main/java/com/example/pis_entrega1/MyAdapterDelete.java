package com.example.pis_entrega1;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapterDelete extends MyAdapter {

    ArrayList<Integer> toDelete;


    MyAdapterDelete(Context context, ArrayList<Notes> data) {
        super(context, data);
    }

    public class ViewHolderToDelete extends RecyclerView.ViewHolder{
        TextView Title;
        TextView Type;
        TextView Date;
        ImageView item;

        public ViewHolderToDelete(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.titleView);
            Type = itemView.findViewById(R.id.typeView);
            Date = itemView.findViewById(R.id.dateView);
            item = itemView.findViewById(R.id.image);
        }

        void bind ( final Notes nota){
            item.setBackgroundColor(nota.isChecked() ? Color.GRAY : Color.WHITE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nota.setChecked(!nota.isChecked());
                    item.setBackgroundColor(nota.isChecked() ? Color.GRAY : Color.WHITE);
                }
            });
        }
    }

    public ArrayList<Integer> getSelected () {
        return toDelete;
    }

    public void addPosition (int i){
        toDelete.add(i);
    }
}
