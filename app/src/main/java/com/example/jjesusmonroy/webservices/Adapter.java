package com.example.jjesusmonroy.webservices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jjesusmonroy on 9/04/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder> {
    String [][]data;

    public Adapter(String[][] data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_alumno,parent,false);
        return new Adapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final String id = data[position][0];
        holder.nombreAlumno.setText(data[position][1]);
        holder.direccionAlumno.setText(data[position][2]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView nombreAlumno,direccionAlumno;
        Button edit,modify;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nombreAlumno=itemView.findViewById(R.id.name);
            direccionAlumno=itemView.findViewById(R.id.address);
            edit=itemView.findViewById(R.id.edit);
            modify=itemView.findViewById(R.id.modify);
        }
    }
}
