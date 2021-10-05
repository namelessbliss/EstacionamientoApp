package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CuentaAdminActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_admin);

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

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(CuentaAdminActivity.this, activity.getClass());
        startActivity(intent);
    }
}