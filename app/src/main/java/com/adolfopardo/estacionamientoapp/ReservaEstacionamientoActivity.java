package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestlistaPlacasCliente;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestregistroReserva;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestregistroReservaInmediata;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaEstacionamiento;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaPlacaCliente;
import com.adolfopardo.estacionamientoapp.retrofit.response.registroReserva;
import com.adolfopardo.estacionamientoapp.retrofit.response.registroReservaInmediata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaEstacionamientoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView A1, A2, A3, A4, A5, A6,
            B1, B2, B3, B4, B5, B6,
            C1, C2, C3, C4, C5, C6,
            D1, D2, D3, D4, D5, D6,
            E1, E2, E3, E4, E5, E6;

    private EditText etEstacionamiento, etCosto, etFechaEntrada;
    private Spinner spinner;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private ViewGroup contentLoading;
    private Toast toast;

    private ArrayList<listaPlacaCliente> listaPlaca = new ArrayList<>();
    String[] placas;
    ArrayAdapter<String> aa;
    private String idPlaca;


    Toolbar toolbar;


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
                        doSomething();
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_estacionamiento);
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

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        contentLoading = findViewById(R.id.contentLoading);
        A1 = findViewById(R.id.A1);
        A2 = findViewById(R.id.A2);
        A3 = findViewById(R.id.A3);
        A4 = findViewById(R.id.A4);
        A5 = findViewById(R.id.A5);
        A6 = findViewById(R.id.A6);
        B1 = findViewById(R.id.B1);
        B2 = findViewById(R.id.B2);
        B3 = findViewById(R.id.B3);
        B4 = findViewById(R.id.B4);
        B5 = findViewById(R.id.B5);
        B6 = findViewById(R.id.B6);
        C1 = findViewById(R.id.C1);
        C2 = findViewById(R.id.C2);
        C3 = findViewById(R.id.C3);
        C4 = findViewById(R.id.C4);
        C5 = findViewById(R.id.C5);
        C6 = findViewById(R.id.C6);
        D1 = findViewById(R.id.D1);
        D2 = findViewById(R.id.D2);
        D3 = findViewById(R.id.D3);
        D4 = findViewById(R.id.D4);
        D5 = findViewById(R.id.D5);
        D6 = findViewById(R.id.D6);
        E1 = findViewById(R.id.E1);
        E2 = findViewById(R.id.E2);
        E3 = findViewById(R.id.E3);
        E4 = findViewById(R.id.E4);
        E5 = findViewById(R.id.E5);
        E6 = findViewById(R.id.E6);
        etEstacionamiento = findViewById(R.id.etEstacionamiento);
        etCosto = findViewById(R.id.etCosto);
        etFechaEntrada = findViewById(R.id.etFechaEntrada);
        spinner = findViewById(R.id.spin);

        A1.setOnClickListener(this);
        A2.setOnClickListener(this);
        A3.setOnClickListener(this);
        A4.setOnClickListener(this);
        A5.setOnClickListener(this);
        A6.setOnClickListener(this);
        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);
        B5.setOnClickListener(this);
        B6.setOnClickListener(this);
        C1.setOnClickListener(this);
        C2.setOnClickListener(this);
        C3.setOnClickListener(this);
        C4.setOnClickListener(this);
        C5.setOnClickListener(this);
        C6.setOnClickListener(this);
        D1.setOnClickListener(this);
        D2.setOnClickListener(this);
        D3.setOnClickListener(this);
        D4.setOnClickListener(this);
        D5.setOnClickListener(this);
        D6.setOnClickListener(this);
        E1.setOnClickListener(this);
        E2.setOnClickListener(this);
        E3.setOnClickListener(this);
        E4.setOnClickListener(this);
        E5.setOnClickListener(this);
        E6.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        doSomething();
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(ReservaEstacionamientoActivity.this, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("reserva","1");
        startActivity(intent);
        finish();
    }

    public void Reservar(View view) {
        hacerReserva();
        //goToActivity(new ClienteActivity());
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

    private void doSomething() {
        Call<List<listaEstacionamiento>> call = service.listarEstacionamientos();

        call.enqueue(new Callback<List<listaEstacionamiento>>() {
            @Override
            public void onResponse(Call<List<listaEstacionamiento>> call, Response<List<listaEstacionamiento>> response) {
                try {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            for (listaEstacionamiento obj : response.body()) {
                                switch (obj.getIDEstacionamiento()) {
                                    case "A1":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A1.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A1.setTag("0");
                                        } else {
                                            A1.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A1.setTag("1");
                                        }
                                        break;
                                    case "A2":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A2.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A2.setTag("0");
                                        } else {
                                            A2.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A2.setTag("1");
                                        }
                                        break;
                                    case "A3":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A3.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A3.setTag("0");
                                        } else {
                                            A3.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A3.setTag("1");
                                        }
                                        break;
                                    case "A4":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A4.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A4.setTag("0");
                                        } else {
                                            A4.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A4.setTag("1");
                                        }
                                        break;
                                    case "A5":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A5.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A5.setTag("0");
                                        } else {
                                            A5.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A5.setTag("1");
                                        }
                                        break;
                                    case "A6":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            A6.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            A6.setTag("0");
                                        } else {
                                            A6.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            A6.setTag("1");
                                        }
                                        break;
                                    case "B1":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B1.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B1.setTag("0");
                                        } else {
                                            B1.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B1.setTag("1");
                                        }
                                        break;
                                    case "B2":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B2.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B2.setTag("0");
                                        } else {
                                            B2.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B2.setTag("1");
                                        }
                                        break;
                                    case "B3":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B3.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B3.setTag("0");
                                        } else {
                                            B3.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B3.setTag("1");
                                        }
                                        break;
                                    case "B4":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B4.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B4.setTag("0");
                                        } else {
                                            B4.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B4.setTag("1");
                                        }
                                        break;
                                    case "B5":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B5.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B5.setTag("0");
                                        } else {
                                            B5.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B5.setTag("1");
                                        }
                                        break;
                                    case "B6":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            B6.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            B6.setTag("0");
                                        } else {
                                            B6.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            B6.setTag("1");
                                        }
                                        break;
                                    case "C1":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C1.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C1.setTag("0");
                                        } else {
                                            C1.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C1.setTag("1");
                                        }
                                        break;
                                    case "C2":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C2.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C2.setTag("0");
                                        } else {
                                            C2.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C2.setTag("1");
                                        }
                                        break;
                                    case "C3":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C3.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C3.setTag("0");
                                        } else {
                                            C3.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C3.setTag("1");
                                        }
                                        break;
                                    case "C4":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C4.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C4.setTag("0");
                                        } else {
                                            C4.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C4.setTag("1");
                                        }
                                        break;
                                    case "C5":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C5.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C5.setTag("0");
                                        } else {
                                            C5.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C5.setTag("1");
                                        }
                                        break;
                                    case "C6":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            C6.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            C6.setTag("0");
                                        } else {
                                            C6.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            C6.setTag("1");
                                        }
                                        break;
                                    case "D1":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D1.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D1.setTag("0");
                                        } else {
                                            D1.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D1.setTag("1");
                                        }
                                        break;
                                    case "D2":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D2.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D2.setTag("0");
                                        } else {
                                            D2.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D2.setTag("1");
                                        }
                                        break;
                                    case "D3":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D3.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D3.setTag("0");
                                        } else {
                                            D3.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D3.setTag("1");
                                        }
                                        break;
                                    case "D4":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D4.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D4.setTag("0");
                                        } else {
                                            D4.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D4.setTag("1");
                                        }
                                        break;
                                    case "D5":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D5.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D5.setTag("0");
                                        } else {
                                            D5.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D5.setTag("1");
                                        }
                                        break;
                                    case "D6":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            D6.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            D6.setTag("0");
                                        } else {
                                            D6.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            D6.setTag("1");
                                        }
                                        break;
                                    case "E1":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E1.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E1.setTag("0");
                                        } else {
                                            E1.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E1.setTag("1");
                                        }
                                        break;
                                    case "E2":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E2.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E2.setTag("0");
                                        } else {
                                            E2.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E2.setTag("1");
                                        }
                                        break;
                                    case "E3":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E3.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E3.setTag("0");
                                        } else {
                                            E3.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E3.setTag("1");
                                        }
                                        break;
                                    case "E4":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E4.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E4.setTag("0");
                                        } else {
                                            E4.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E4.setTag("1");
                                        }
                                        break;
                                    case "E5":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E5.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E5.setTag("0");
                                        } else {
                                            E5.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E5.setTag("1");
                                        }
                                        break;
                                    case "E6":
                                        if (obj.getEstado().equalsIgnoreCase("0")) {
                                            E6.setImageDrawable(getDrawable(R.drawable.ic_car_green));
                                            E6.setTag("0");
                                        } else {
                                            E6.setImageDrawable(getDrawable(R.drawable.ic_car_red));
                                            E6.setTag("1");
                                        }
                                        break;
                                }
                            }
                            contentLoading.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<listaEstacionamiento>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });

        RequestlistaPlacasCliente request = new RequestlistaPlacasCliente(SharedPreferencesManager.getStringValue(Constants.DNI));
        Call<List<listaPlacaCliente>> call2 = service.listarPlacasCliente(request);

        call2.enqueue(new Callback<List<listaPlacaCliente>>() {
            @Override
            public void onResponse(Call<List<listaPlacaCliente>> call, Response<List<listaPlacaCliente>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        listaPlaca = (ArrayList<listaPlacaCliente>) response.body();
                        placas = new String[response.body().size()];
                        for (int i = 0; i < listaPlaca.size(); i++) {
                            placas[i] = response.body().get(i).getNombrePlaca();
                        }
                        aa = new ArrayAdapter<String>(ReservaEstacionamientoActivity.this, android.R.layout.simple_spinner_item, placas);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(aa);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<listaPlacaCliente>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

    private void hacerReserva() {
        RequestregistroReserva request = new RequestregistroReserva(idPlaca, etEstacionamiento.getText().toString(), "4", SharedPreferencesManager.getStringValue(Constants.DNI));
        Call<registroReserva> call = service.registrarReserva(request);

        call.enqueue(new Callback<registroReserva>() {
            @Override
            public void onResponse(Call<registroReserva> call, Response<registroReserva> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDescripcion().equalsIgnoreCase("Registrado")) {
                        showToast("Reservado Exitosamente");
                        goToActivity(new ClienteActivity());
                    } else {
                        showToast("ERROR: " + response.body().getDescripcion());
                    }
                }
            }

            @Override
            public void onFailure(Call<registroReserva> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.A1) {
            if (A1.getTag() != null && A1.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A1");
            }
        }
        if (view.getId() == R.id.A2) {
            if (A2.getTag() != null && A2.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A2");
            }
        }
        if (view.getId() == R.id.A3) {
            if (A3.getTag() != null && A3.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A3");
            }
        }
        if (view.getId() == R.id.A4) {
            if (A4.getTag() != null && A4.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A4");
            }
        }
        if (view.getId() == R.id.A5) {
            if (A5.getTag() != null && A5.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A5");
            }
        }


        if (view.getId() == R.id.A6) {
            if (A6.getTag() != null && A6.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("A6");
            }
        }


        if (view.getId() == R.id.B1) {
            if (B1.getTag() != null && B1.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B1");
            }
        }

        if (view.getId() == R.id.B2) {
            if (B2.getTag() != null && B2.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B2");
            }
        }

        if (view.getId() == R.id.B3) {
            if (B3.getTag() != null && B3.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B3");
            }
        }

        if (view.getId() == R.id.B4) {
            if (B4.getTag() != null && B4.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B4");
            }
        }

        if (view.getId() == R.id.B5) {
            if (B5.getTag() != null && B5.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B5");
            }
        }

        if (view.getId() == R.id.B6) {
            if (B6.getTag() != null && B6.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("B6");
            }
        }

        if (view.getId() == R.id.C1) {
            if (C1.getTag() != null && C1.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C1");
            }
        }

        if (view.getId() == R.id.C2) {
            if (C2.getTag() != null && C2.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C2");
            }
        }

        if (view.getId() == R.id.C3) {
            if (C3.getTag() != null && C3.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C3");
            }
        }

        if (view.getId() == R.id.C4) {
            if (C4.getTag() != null && C4.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C4");
            }
        }

        if (view.getId() == R.id.C5) {
            if (C5.getTag() != null && C5.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C5");
            }
        }

        if (view.getId() == R.id.C6) {
            if (C6.getTag() != null && C6.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("C6");
            }
        }

        if (view.getId() == R.id.D1) {
            if (D1.getTag() != null && D1.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D1");
            }
        }

        if (view.getId() == R.id.D2) {
            if (D2.getTag() != null && D2.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D2");
            }
        }

        if (view.getId() == R.id.D3) {
            if (D3.getTag() != null && D3.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D3");
            }
        }

        if (view.getId() == R.id.D4) {
            if (D4.getTag() != null && D4.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D4");
            }
        }

        if (view.getId() == R.id.D5) {
            if (D5.getTag() != null && D5.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D5");
            }
        }

        if (view.getId() == R.id.D6) {
            if (D6.getTag() != null && D6.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("D6");
            }
        }

        if (view.getId() == R.id.E1) {
            if (E1.getTag() != null && E1.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E1");
            }
        }

        if (view.getId() == R.id.E2) {
            if (E2.getTag() != null && E2.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E2");
            }
        }

        if (view.getId() == R.id.E3) {
            if (E3.getTag() != null && E3.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E3");
            }
        }

        if (view.getId() == R.id.E4) {
            if (E4.getTag() != null && E4.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E4");
            }
        }

        if (view.getId() == R.id.E5) {
            if (E5.getTag() != null && E5.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E5");
            }
        }

        if (view.getId() == R.id.E6) {
            if (E6.getTag() != null && E6.getTag().equals("1")) {
                showToast("El Espacio Esta Ocupado");
            } else {
                etEstacionamiento.setText("E6");
            }
        }

        setFecha();
    }

    private void setFecha() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm", Locale.getDefault());
        String formattedDate = df.format(c);

        etFechaEntrada.setText(formattedDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        idPlaca = listaPlaca.get(i).getIdPlaca();
        System.out.println(idPlaca);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer();
    }
}