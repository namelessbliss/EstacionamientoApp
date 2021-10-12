package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.adapter.AdapterVehiculosDia;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosDelMes;
import com.adolfopardo.estacionamientoapp.retrofit.response.autosTiempoPromedio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaAdminActivity extends AppCompatActivity {

    private TextView tvNombre, tvTipo, tvTiempoProm, tvVehiculoMes;
    private EditText etPlaca;
    Toolbar toolbar;

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_admin);
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
        tvNombre.setText(SharedPreferencesManager.getStringValue(Constants.NAME));
        tvTiempoProm = findViewById(R.id.tvTiempoProm);
        tvVehiculoMes = findViewById(R.id.tvVehiculoMes);

        getTiempoPomedio();
        getVehiculosMes();
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

    public void VehiculosDia(View view) {
        goToActivity(new ListadoVehiculosDiaActivity());
    }

    public void IngresoSalida(View view) {
        goToActivity(new RegistroEntradasSalidasActivity());
    }

    public void FlujoIngresos(View view) {
        goToActivity(new FlujoIngresosActivity());
    }

    private void getTiempoPomedio() {
        Call<List<autosTiempoPromedio>> call = service.calcularAutosPromedio();

        call.enqueue(new Callback<List<autosTiempoPromedio>>() {
            @Override
            public void onResponse(Call<List<autosTiempoPromedio>> call, Response<List<autosTiempoPromedio>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvTiempoProm.setText(response.body().get(0).getPromedio().substring(0, 2) + " horas");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<autosTiempoPromedio>> call, Throwable t) {

            }
        });
    }

    private void getVehiculosMes() {
        Call<List<autosDelMes>> call = service.calcularAutosMes();

        call.enqueue(new Callback<List<autosDelMes>>() {
            @Override
            public void onResponse(Call<List<autosDelMes>> call, Response<List<autosDelMes>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tvVehiculoMes.setText(response.body().get(0).getContador());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<autosDelMes>> call, Throwable t) {

            }
        });
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(CuentaAdminActivity.this, activity.getClass());
        startActivity(intent);
    }
}