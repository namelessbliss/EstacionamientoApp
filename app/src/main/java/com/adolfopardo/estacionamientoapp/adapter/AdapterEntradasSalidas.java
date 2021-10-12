package com.adolfopardo.estacionamientoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adolfopardo.estacionamientoapp.R;
import com.adolfopardo.estacionamientoapp.retrofit.response.Listado;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosEntradasSalidas;

import java.util.List;

public class AdapterEntradasSalidas extends RecyclerView.Adapter<AdapterEntradasSalidas.ViewHolder> {

    private Context ctx;
    private final List<Listado> mValues;
    private RecyclerView recyclerView;

    public AdapterEntradasSalidas(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_ingreso_salida_detalle, parent, false);
        return new AdapterEntradasSalidas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.tvPlaca.setText("Placa: " + mValues.get(position).getNombrePlaca());
            holder.tvFechaEntrada.setText("Entrada: " + mValues.get(position).getFechaEntrada());
            holder.tvFechaSalida.setText("Salida: " + mValues.get(position).getFechaSalida());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPlaca, tvFechaEntrada, tvFechaSalida;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPlaca = view.findViewById(R.id.tvPlaca);
            tvFechaEntrada = view.findViewById(R.id.tvFechaEntrada);
            tvFechaSalida = view.findViewById(R.id.tvFechaSalida);
        }
    }

    public double truncarDosDecimales(double decimal) {
        decimal = (int) (decimal * 100);
        double truncado = decimal / 100.0f;
        return truncado;
    }
}
