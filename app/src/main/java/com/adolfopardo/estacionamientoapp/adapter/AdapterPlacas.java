package com.adolfopardo.estacionamientoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolfopardo.estacionamientoapp.R;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaPlacaCliente;

import java.util.List;

public class AdapterPlacas extends RecyclerView.Adapter<AdapterPlacas.ViewHolder> {

    private Context ctx;
    private final List<listaPlacaCliente> mValues;
    private RecyclerView recyclerView;

    public AdapterPlacas(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_placas, parent, false);
        return new AdapterPlacas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.tvPlaca.setText("Placa " + mValues.get(position).getNombrePlaca());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPlaca;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPlaca = view.findViewById(R.id.tvPlaca);
        }
    }
}
