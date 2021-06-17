package com.example.hci_onfitapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.MyViewHolder>{

    ArrayList<RoutineCardData> listaPersonaje;

    public HorizontalRecyclerAdapter(ArrayList<RoutineCardData> listaPersonaje) {
        this.listaPersonaje = listaPersonaje;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pruebita_recycler,null,false);
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pruebita_recycler,parent,false));
//        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtNombre.setText("algun nombre");
    }

    @Override
    public int getItemCount() {
        return listaPersonaje.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;

        public MyViewHolder (View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.tvAnimalName);
        }
    }
}