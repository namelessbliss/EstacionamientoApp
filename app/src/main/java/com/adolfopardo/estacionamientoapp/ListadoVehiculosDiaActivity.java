package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterVehiculosDia;
import com.adolfopardo.estacionamientoapp.adapter.LugaresConectadosCliente;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestlistaReservaCliente;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPorDias;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoVehiculosDiaActivity extends AppCompatActivity {

    Toolbar toolbar;
    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    private AdapterVehiculosDia adapter;
    private List<autosPorDias> list;
    private ViewGroup contentLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos_dia);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getListado();
    }

    private void getListado() {

        Call<List<autosPorDias>> call = service.calcularAutosDias();

        call.enqueue(new Callback<List<autosPorDias>>() {
            @Override
            public void onResponse(Call<List<autosPorDias>> call, Response<List<autosPorDias>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() > 0) {
                            list = response.body();
                            adapter = new AdapterVehiculosDia(ListadoVehiculosDiaActivity.this, list);
                            rvList.setAdapter(adapter);
                            contentLoading.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(ListadoVehiculosDiaActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    showToast(list.get(position).getContador());
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
            public void onFailure(Call<List<autosPorDias>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });

        /*call.enqueue(new Callback<List<listaReservaCliente>>() {
            @Override
            public void onResponse(Call<List<listaReservaCliente>> call, Response<List<listaReservaCliente>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    if (response.body() != null) {
                        if (response.body().size() > 0) {
                            list = response.body();
                            adapter = new LugaresConectadosCliente(ClienteActivity.this, list);
                            rvList.setAdapter(adapter);
                            contentLoading.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(ClienteActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    showToast(list.toString());
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
            public void onFailure(Call<List<listaReservaCliente>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });*/
    }
}