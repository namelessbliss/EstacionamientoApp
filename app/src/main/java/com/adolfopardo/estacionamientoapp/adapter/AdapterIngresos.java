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
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPrecioTotal;

import java.util.List;

public class AdapterIngresos extends RecyclerView.Adapter<AdapterIngresos.ViewHolder> {

    private Context ctx;
    private final List<autosPrecioTotal> mValues;
    private RecyclerView recyclerView;

    public AdapterIngresos(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_flujo_ingreso, parent, false);
        return new AdapterIngresos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            holder.tvFecha.setText("Fecha: " + mValues.get(position).getFechaSalida());
            holder.tvIngresos.setText("Ingresos: S/. " + truncarDosDecimales(Double.parseDouble(mValues.get(position).getMonto())));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvFecha, tvIngresos;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvFecha = view.findViewById(R.id.tvFecha);
            tvIngresos = view.findViewById(R.id.tvIngresos);
        }
    }

    public double truncarDosDecimales(double decimal) {
        decimal = (int) (decimal * 100);
        double truncado = decimal / 100.0f;
        return truncado;
    }
}
