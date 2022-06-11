package com.adolfopardo.estacionamientoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolfopardo.estacionamientoapp.R;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaAdmin;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaCliente;

import java.util.List;

public class LugaresConectados extends RecyclerView.Adapter<LugaresConectados.ViewHolder> {

    private Context ctx;
    private final List<listaReservaAdmin> mValues;
    private RecyclerView recyclerView;

    public LugaresConectados(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_estacionamientos_conectados, parent, false);
        return new LugaresConectados.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.tvPlaca.setText("Placa" + mValues.get(position).getNombrePlaca());
            holder.tvCosto.setText("Costo por hora s/. " + mValues.get(position).getCostoHora());
            if (mValues.get(position).getEstado().equalsIgnoreCase("0")) {
                holder.tvEstado.setText("Estado: Reservado");
                holder.tvEstado.setTextColor(ctx.getResources().getColor(R.color.colorReg));
            }else
                holder.tvEstado.setText("Estado: Estacionado");
            holder.tvEstacionamiento.setText(mValues.get(position).getIdEstacionamiento());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPlaca, tvCosto, tvEstado, tvEstacionamiento;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPlaca = view.findViewById(R.id.tvPlaca);
            tvCosto = view.findViewById(R.id.tvCosto);
            tvEstado = view.findViewById(R.id.tvEstado);
            tvEstacionamiento = view.findViewById(R.id.tvEstacionamiento);
        }
    }
}
