package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClienteActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

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
        Intent intent = new Intent(ClienteActivity.this, activity.getClass());
        startActivity(intent);
    }
}