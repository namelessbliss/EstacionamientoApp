package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterPlacas;
import com.adolfopardo.estacionamientoapp.adapter.LugaresConectados;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestlistaPlacasCliente;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestregistroPlaca;
import com.adolfopardo.estacionamientoapp.retrofit.response.listaPlacaCliente;
import com.adolfopardo.estacionamientoapp.retrofit.response.registroPlaca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaClienteActivity extends AppCompatActivity {

    private TextView tvNombre, tvTipo, tvFecha;
    private EditText etPlaca;
    Toolbar toolbar;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    RecyclerView rvList;
    AdapterPlacas adapter;
    private ViewGroup contentLoading;

    private ArrayList<listaPlacaCliente> listaPlaca = new ArrayList<>();
    String[] placas;
    ArrayAdapter<String> aa;
    private String idPlaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_cliente);
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
        tvNombre = findViewById(R.id.tvNombre);
        tvTipo = findViewById(R.id.tvTipo);
        tvFecha = findViewById(R.id.tvFecha);
        etPlaca = findViewById(R.id.etPlaca);
        rvList = findViewById(R.id.rvList);
        contentLoading = findViewById(R.id.contentLoading);

        tvNombre.setText(SharedPreferencesManager.getStringValue(Constants.NAME));
        if (SharedPreferencesManager.getStringValue(Constants.TIPO).equalsIgnoreCase("1"))
            tvTipo.setText("Administrador");
        else
            tvTipo.setText("Cliente");
        setFecha();

        doSomething();
    }

    private void doSomething() {
        RequestlistaPlacasCliente request = new RequestlistaPlacasCliente(SharedPreferencesManager.getStringValue(Constants.DNI));
        Call<List<listaPlacaCliente>> call2 = service.listarPlacasCliente(request);

        call2.enqueue(new Callback<List<listaPlacaCliente>>() {
            @Override
            public void onResponse(Call<List<listaPlacaCliente>> call, Response<List<listaPlacaCliente>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        listaPlaca = (ArrayList<listaPlacaCliente>) response.body();
                        adapter = new AdapterPlacas(CuentaClienteActivity.this, listaPlaca);
                        rvList.setAdapter(adapter);
                        contentLoading.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<listaPlacaCliente>> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
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

    private void setFecha() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm", Locale.getDefault());
        String formattedDate = df.format(c);

        tvFecha.setText(formattedDate);
    }

    public void RegistrarPlaca(View view) {
        if (!TextUtils.isEmpty(etPlaca.getText())) {
            RequestregistroPlaca request = new RequestregistroPlaca(SharedPreferencesManager.getStringValue(Constants.DNI), etPlaca.getText().toString());
            Call<registroPlaca> call = service.registroPlacas(request);

            call.enqueue(new Callback<registroPlaca>() {
                @Override
                public void onResponse(Call<registroPlaca> call, Response<registroPlaca> response) {
                    if (response.isSuccessful()) {
                        listaPlaca.add(new listaPlacaCliente(etPlaca.getText().toString()));
                        adapter.notifyDataSetChanged();
                        showToast(response.body().getDescripcion());
                        etPlaca.setText("");
                    }
                }

                @Override
                public void onFailure(Call<registroPlaca> call, Throwable t) {
                    showToast("Ocurrio un error");
                }
            });
        } else {
            etPlaca.setError("Llenar campo");
        }
    }
}