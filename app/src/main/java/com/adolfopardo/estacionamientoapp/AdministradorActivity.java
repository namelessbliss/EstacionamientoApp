package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.LugaresConectados;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestlistaReservaCliente;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaAdmin;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministradorActivity extends AppCompatActivity {
    Toolbar toolbar;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    private LugaresConectados adapter;
    private List<listaReservaAdmin> list;
    private ViewGroup contentLoading;
    private EditText etPlaca;

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();

    //To stop timer
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    //To start timer
    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getListado();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        retrofitInit();
        bindViews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        startTimer();
    }

    private void retrofitInit() {
        client = AeropuertosPeruClient.getInstance();
        service = client.getAeropuertosPeruService();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        rvList = findViewById(R.id.rvList);
        contentLoading = findViewById(R.id.contentLoading);

        getListado();
    }

    public void PerfilAdm(View view) {
        goToActivity(new CuentaAdminActivity());
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(AdministradorActivity.this, activity.getClass());
        startActivity(intent);
    }

    public void BuscarPlaca(View view) {
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

        Call<List<listaReservaAdmin>> call = service.listarReservasAdmin();

        call.enqueue(new Callback<List<listaReservaAdmin>>() {
            @Override
            public void onResponse(Call<List<listaReservaAdmin>> call, Response<List<listaReservaAdmin>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() > 0) {
                            list = response.body();
                            adapter = new LugaresConectados(AdministradorActivity.this, list);
                            rvList.setAdapter(adapter);
                            contentLoading.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(AdministradorActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //showToast(list.toString());
                                    Intent intent = new Intent(AdministradorActivity.this, RegistroSalidaActivity.class);
                                    intent.putExtra("idReservacion", list.get(position).getIdReservacion());
                                    intent.putExtra("IDEstacionamiento", list.get(position).getIdEstacionamiento());
                                    intent.putExtra("placa", list.get(position).getNombrePlaca());
                                    intent.putExtra("fechaEntrada", list.get(position).getFechaEntrada());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }
                            }));
                        } else {
                            list = new ArrayList<>(1);
                            adapter = new LugaresConectados(AdministradorActivity.this, list);
                            rvList.setAdapter(adapter);
                            showToast("No hay registros de estacionamientos ocupados actualmente");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<listaReservaAdmin>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}