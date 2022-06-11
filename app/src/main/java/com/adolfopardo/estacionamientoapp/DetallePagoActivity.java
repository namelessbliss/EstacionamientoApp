package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterEntradasSalidas;
import com.adolfopardo.estacionamientoapp.adapter.AdapterPagos;
import com.adolfopardo.estacionamientoapp.retrofit.response.Listado;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosEntradasSalidas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetallePagoActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rvList;
    private AdapterPagos adapter;
    private List<Listado> list;
    private ViewGroup contentLoading;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pago);

        rvList = findViewById(R.id.rvList);
        contentLoading = findViewById(R.id.contentLoading);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        int position = intent.getIntExtra("position", 0);
        ArrayList<autosEntradasSalidas> object = (ArrayList<autosEntradasSalidas>) args.getSerializable("ARRAYLIST");

        list = object.get(position).getListado();
        //Ordena por fecha de forma descendente
        Collections.sort(list, new Comparator<Listado>() {
            public int compare(Listado o1, Listado o2) {
                if (o1.getFechaSalida() == null || o2.getFechaSalida() == null)
                    return 0;
                return o2.getFechaSalida().compareTo(o1.getFechaSalida());
            }
        });
        adapter = new AdapterPagos(DetallePagoActivity.this, list);
        rvList.setAdapter(adapter);
        contentLoading.setVisibility(View.GONE);
        rvList.addOnItemTouchListener(new RecyclerItemClickListener(DetallePagoActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast(list.get(position).getFechaSalida());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}