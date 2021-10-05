package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Registrar(View view) {
        goToActivity(new RegistroActivity());
    }

    public void Login(View view) {
        goToActivity(new AdministradorActivity());
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(LoginActivity.this, activity.getClass());
        startActivity(intent);
    }
}