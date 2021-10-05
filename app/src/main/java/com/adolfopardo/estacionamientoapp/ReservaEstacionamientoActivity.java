package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReservaEstacionamientoActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_estacionamiento);
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

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(ReservaEstacionamientoActivity.this, activity.getClass());
        startActivity(intent);
        finish();
    }

    public void Reservar(View view) {
        goToActivity(new ClienteActivity());
    }
}