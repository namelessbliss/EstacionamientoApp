package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.LugaresConectadosCliente;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestEliminar;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestlistaReservaCliente;
import com.adolfopardo.estacionamientoapp.retrofit.response.ResponseEliminar;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaReservaCliente;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteActivity extends AppCompatActivity {
    Toolbar toolbar;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    private LugaresConectadosCliente adapter;
    private List<listaReservaCliente> list;
    private ViewGroup contentLoading;
    private ProgressBar progressBar;
    TextView tvMsg;

    String TAG = "TAG";

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    public static boolean noLista = false;

    boolean cerrarApp;

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
/*                            if (noLista)
                                showToast("No hay estacionamientos ocupados ni reservados");*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 4000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        retrofitInit();

        bindViews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.setStringValue(Constants.DNI, "");
                eliminarReserva();
                finish();
            }
        });
        startTimer();
        startService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "Started service");
        cerrarApp = true;
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                updateGUI(intent); // or whatever method used to update your GUI fields
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        eliminarReserva();
        if (cerrarApp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ClienteActivity.this);
            builder.setCancelable(false);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(ClienteActivity.this).inflate(R.layout.rate_dialog, viewGroup, false);
            builder.setView(dialogView);
            Button playstore = (Button) dialogView.findViewById(R.id.btnPlaystore);
            Button nota = (Button) dialogView.findViewById(R.id.btnNota);
            Button salir = (Button) dialogView.findViewById(R.id.btnCerrar);
            AlertDialog alertDialog = builder.create();
            //alertDialog.setCanceledOnTouchOutside(false);
            playstore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoPlaystore();
                }
            });
            nota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoSugerencias();
                }
            });
            salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String reserva = intent.getStringExtra("reserva");
        if (reserva != null) {
            if (reserva.equalsIgnoreCase("1")) {
                registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
                Log.i(TAG, "Registered broacast receiver");
            }
        }
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Unregistered broacast receiver");
        if (cerrarApp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ClienteActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(ClienteActivity.this).inflate(R.layout.rate_dialog, viewGroup, false);
            builder.setView(dialogView);
            Button playstore = (Button) dialogView.findViewById(R.id.btnPlaystore);
            Button nota = (Button) dialogView.findViewById(R.id.btnNota);
            Button salir = (Button) dialogView.findViewById(R.id.btnCerrar);
            AlertDialog alertDialog = builder.create();
            //alertDialog.setCanceledOnTouchOutside(false);
            playstore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoPlaystore();
                }
            });
            nota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoSugerencias();
                }
            });
            salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            alertDialog.show();
            super.onPause();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        try {
            //unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "Stopped service");
        if (list != null) {
            eliminarReserva();
        }
        super.onDestroy();
    }

    private void gotoPlaystore() {
        Toast.makeText(ClienteActivity.this, "PLAYSTORE", Toast.LENGTH_SHORT).show();
    }

    private void gotoSugerencias() {
        Toast.makeText(ClienteActivity.this, "NOTA", Toast.LENGTH_SHORT).show();
    }

    public void eliminarReserva() {
        try {
            String idReserva = "";
            listaReservaCliente obj = new listaReservaCliente();

            for (listaReservaCliente lis : list) {
                if (lis.getEstado().equalsIgnoreCase("0")) {
                    idReserva = lis.getIdEstacionamiento();
                    obj = lis;
                }
            }

            RequestEliminar request = new RequestEliminar(SharedPreferencesManager.getStringValue(Constants.DNI), idReserva);
            Call<ResponseEliminar> call = service.eliminar10min(request);

            if (!idReserva.isEmpty())
                showToast("Reserva Eliminada");
            listaReservaCliente finalObj = obj;
            call.enqueue(new Callback<ResponseEliminar>() {
                @Override
                public void onResponse(Call<ResponseEliminar> call, Response<ResponseEliminar> response) {
                    if (response.isSuccessful()) {
                        showToast("Reserva Eliminada");
                        list.remove(finalObj);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ResponseEliminar> call, Throwable t) {
                    showToast("Error al eliminar la reserva, contacte al adm");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);

            long minutes = (millisUntilFinished / 1000) / 60;

            long seconds = (millisUntilFinished / 1000) % 60;

            Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
            Log.i(TAG, " Milliseconds = "
                    + minutes + " minutes and "
                    + seconds + " seconds.");

            adapter.updateTimer(minutes + " : " + seconds);

            if (minutes <= 0 && seconds <= 0) {
                try {
                    eliminarReserva();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        rvList = findViewById(R.id.rvList);
        tvMsg = findViewById(R.id.tvMsg);
        contentLoading = findViewById(R.id.contentLoading);
        progressBar = findViewById(R.id.progressBar);

        //getListado();
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

    public void PerfilCliente(View view) {
        goToActivity(new CuentaClienteActivity());
    }

    public void SeleccionEstacionamiento(View view) {
        goToActivity(new SeleccionEstacionamientoActivity());
    }

    public void ReservaEstacionamiento(View view) {
        goToActivity(new ReservaEstacionamientoActivity());
    }

    public void goToActivity(AppCompatActivity activity) {
        cerrarApp = false;
        Intent intent = new Intent(ClienteActivity.this, activity.getClass());
        startActivity(intent);
    }

    private void getListado() {
        RequestlistaReservaCliente request = new RequestlistaReservaCliente(SharedPreferencesManager.getStringValue(Constants.DNI),
                SharedPreferencesManager.getStringValue(Constants.USER),
                SharedPreferencesManager.getStringValue(Constants.PASS),
                "2");
        Call<List<listaReservaCliente>> call = service.listarReservasCliente(request);

        call.enqueue(new Callback<List<listaReservaCliente>>() {
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
                            ClienteActivity.noLista = false;
                            tvMsg.setVisibility(View.GONE);
                            rvList.addOnItemTouchListener(new RecyclerItemClickListener(ClienteActivity.this, rvList, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    cerrarApp = false;
                                    //showToast("Estacionado en: " + list.get(position).getIdEstacionamiento());
                                    Intent intent = new Intent(ClienteActivity.this, RegistroSalidaClienteActivity.class);
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
                            ClienteActivity.noLista = true;
                            //showToast("No hay estacionamientos ocupados ni reservados");
                            tvMsg.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<listaReservaCliente>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

}