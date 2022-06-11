package com.adolfopardo.estacionamientoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolfopardo.estacionamientoapp.R;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPorDias;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaPlacaCliente;

import java.util.List;

public class AdapterVehiculosDia extends RecyclerView.Adapter<AdapterVehiculosDia.ViewHolder> {

    private Context ctx;
    private final List<autosPorDias> mValues;
    private RecyclerView recyclerView;

    public AdapterVehiculosDia(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_vehiculos_dia, parent, false);
        return new AdapterVehiculosDia.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.tvFecha.setText("Fecha: " + mValues.get(position).getFechaSalida());
            holder.tvCantidad.setText("Cantidad: " + mValues.get(position).getContador() + " Vehiculos");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvFecha, tvCantidad;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvFecha = view.findViewById(R.id.tvFecha);
            tvCantidad = view.findViewById(R.id.tvCantidad);
        }
    }
}
