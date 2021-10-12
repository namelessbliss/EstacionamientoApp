package com.adolfopardo.estacionamientoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.Requestlogin;
import com.adolfopardo.estacionamientoapp.retrofit.request.Requestregistro;
import com.adolfopardo.estacionamientoapp.retrofit.response.responseLogin;
import com.adolfopardo.estacionamientoapp.retrofit.response.responseRegistro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        retrofitInit();
    }

    public void Registra(View view) {
        EditText etUser = findViewById(R.id.etUser);
        EditText etpass = findViewById(R.id.etPass);
        EditText etName = findViewById(R.id.etName);
        EditText etDNI = findViewById(R.id.etDNI);
        EditText etPlaca = findViewById(R.id.etPlaca);
        try {
            if (!TextUtils.isEmpty(etUser.getText().toString())
                    && !TextUtils.isEmpty(etUser.getText().toString())
                    && !TextUtils.isEmpty(etName.getText().toString())
                    && !TextUtils.isEmpty(etDNI.getText().toString())
                    && !TextUtils.isEmpty(etPlaca.getText().toString()))
                doSomething(etUser.getText().toString(), etpass.getText().toString(),
                        etName.getText().toString(), etDNI.getText().toString(), etPlaca.getText().toString());
            else
                showToast("Llene los campos");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Usuario o contraseña invalido");
        }
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(RegistroActivity.this, activity.getClass());
        startActivity(intent);
        finish();
    }

    private void retrofitInit() {
        client = AeropuertosPeruClient.getInstance();
        service = client.getAeropuertosPeruService();
    }

    private void doSomething(String user, String pass, String name, String DNI, String placa) {
        Requestregistro request = new Requestregistro(DNI, user, pass, "2",name,placa);
        Call<responseRegistro> call = service.registro(request);

        call.enqueue(new Callback<responseRegistro>() {
            @Override
            public void onResponse(Call<responseRegistro> call, Response<responseRegistro> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDescripcion().equalsIgnoreCase("Registrado")) {

                        SharedPreferencesManager.setStringValue(Constants.USER, user);
                        SharedPreferencesManager.setStringValue(Constants.DNI, DNI);
                        SharedPreferencesManager.setStringValue(Constants.TIPO, "2");
                        SharedPreferencesManager.setStringValue(Constants.PASS, pass);
                        showToast("Registrado Exitosamenste");

                        goToActivity(new ClienteActivity());
                    } else
                        showToast(response.body().getDescripcion());
                }
            }

            @Override
            public void onFailure(Call<responseRegistro> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}