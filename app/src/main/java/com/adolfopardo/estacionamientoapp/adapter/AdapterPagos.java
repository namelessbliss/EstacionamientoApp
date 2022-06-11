package com.adolfopardo.estacionamientoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adolfopardo.estacionamientoapp.R;
import com.adolfopardo.estacionamientoapp.retrofit.response.Listado;

import java.util.List;

public class AdapterPagos extends RecyclerView.Adapter<AdapterPagos.ViewHolder> {

    private Context ctx;
    private final List<Listado> mValues;
    private RecyclerView recyclerView;

    public AdapterPagos(Context ctx, List mValues) {
        this.ctx = ctx;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_pago_detalle, parent, false);
        return new AdapterPagos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mValues != null) {
            if (mValues.get(position).getMetodoPago().equalsIgnoreCase("F")) {
                holder.ivPay.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.paypal));
                holder.tvMonto.setText("Monto: $" + Float.parseFloat(mValues.get(position).getCostoTotal()) / 4);
                holder.tvMetodoPago.setText("Metodo de pago: Paypal");
            } else {
                holder.ivPay.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.money));
                holder.tvMonto.setText("Monto: S/" + mValues.get(position).getCostoTotal());
                holder.tvMetodoPago.setText("Metodo de pago: Efectivo");
            }
            holder.tvCliente.setText("Cliente: " + mValues.get(position).getNombres());
            holder.tvFechaPago.setText("Fecha: " + mValues.get(position).getFechaSalida());
            holder.tvEstacionamiento.setText("Estacionamiento: " + mValues.get(position).getIdEstacionamiento());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivPay;
        public final TextView tvCliente, tvFechaPago, tvMonto, tvMetodoPago, tvEstacionamiento;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivPay = view.findViewById(R.id.ivPay);
            tvCliente = view.findViewById(R.id.tvCliente);
            tvFechaPago = view.findViewById(R.id.tvFechaPago);
            tvMonto = view.findViewById(R.id.tvMonto);
            tvMetodoPago = view.findViewById(R.id.tvMetodoPago);
            tvEstacionamiento = view.findViewById(R.id.tvEstacionamiento);
        }
    }

    public double truncarDosDecimales(double decimal) {
        decimal = (int) (decimal * 100);
        double truncado = decimal / 100.0f;
        return truncado;
    }
}
