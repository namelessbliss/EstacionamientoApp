package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void Registra(View view) {
        goToActivity(new ClienteActivity());
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(RegistroActivity.this, activity.getClass());
        startActivity(intent);
    }
}