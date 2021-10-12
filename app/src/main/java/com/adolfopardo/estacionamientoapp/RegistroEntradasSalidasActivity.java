package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterFechas;
import com.adolfopardo.estacionamientoapp.adapter.AdapterIngresos;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosEntradasSalidas;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPrecioTotal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroEntradasSalidasActivity extends AppCompatActivity {

    Toolbar toolbar;
    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    private AdapterFechas adapter;
    private List<autosEntradasSalidas> list;
    private ViewGroup contentLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entradas_salidas);
        retrofitInit();
        bindViews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        rvList = findViewById(R.id.rvList);
        contentLoading = findViewById(R.id.contentLoading);

        getListado();
    }

    private void retrofitInit() {
        client = AeropuertosPeruClient.getInstance();
        service = client.getAeropuertosPeruService();
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void getListado() {
        Call<List<autosEntradasSalidas>> call = service.listarReservasEntradasSalidas();

        call.enqueue(new Callback<List<autosEntradasSalidas>>() {
            @Override
            public void onResponse(Call<List<autosEntradasSalidas>> call, Response<List<autosEntradasSalidas>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() > 0) {
                            list = response.body();
                            //Ordena por fecha de forma descendente
                            Collections.sort(list, new Comparator<autosEntradasSalidas>() {
                                public int compare(autosEntradasSalidas o1, autosEntradasSalidas o2) {
                                    if (o1.getFecha() == null || o2.getFecha() == null)
                                        return 0;
                                    return o2.getFecha().compareTo(o1.getFecha());
                                }
                            });
                            adapter = new AdapterFechas(RegistroEntradasSalidasActivity.this, list);
                            rvList.setAdapter(adapter);
                            contentLoading.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(RegistroEntradasSalidasActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    showToast(list.get(position).getFecha());
                                    ArrayList<autosEntradasSalidas> object = new ArrayList<autosEntradasSalidas>();
                                    object = (ArrayList<autosEntradasSalidas>) list;
                                    Intent intent = new Intent(RegistroEntradasSalidasActivity.this, DetalleEntradaSalidaActivity.class);
                                    Bundle args = new Bundle();
                                    args.putSerializable("ARRAYLIST", (Serializable) object);
                                    intent.putExtra("BUNDLE", args);
                                    intent.putExtra("position", position);
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }
                            }));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<autosEntradasSalidas>> call, Throwable t) {

            }
        });
    }
}