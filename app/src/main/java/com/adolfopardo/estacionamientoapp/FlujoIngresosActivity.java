package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterIngresos;
import com.adolfopardo.estacionamientoapp.adapter.AdapterVehiculosDia;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPorDias;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosPrecioTotal;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosTiempoPromedio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlujoIngresosActivity extends AppCompatActivity {

    Toolbar toolbar;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    private AdapterIngresos adapter;
    private List<autosPrecioTotal> list;
    private ViewGroup contentLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujo_ingresos);
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
        Call<List<autosPrecioTotal>> call = service.calcularAutosPrecioTotal();

        call.enqueue(new Callback<List<autosPrecioTotal>>() {
            @Override
            public void onResponse(Call<List<autosPrecioTotal>> call, Response<List<autosPrecioTotal>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() > 0) {
                            list = response.body();
                            adapter = new AdapterIngresos(FlujoIngresosActivity.this, list);
                            rvList.setAdapter(adapter);
                            contentLoading.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(FlujoIngresosActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    showToast(truncarDosDecimales(Double.parseDouble(list.get(position).getMonto())) + "");
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
            public void onFailure(Call<List<autosPrecioTotal>> call, Throwable t) {

            }
        });
    }

    public double truncarDosDecimales(double decimal) {
        decimal = (int) (decimal * 100);
        double truncado = decimal / 100.0f;
        return truncado;
    }
}