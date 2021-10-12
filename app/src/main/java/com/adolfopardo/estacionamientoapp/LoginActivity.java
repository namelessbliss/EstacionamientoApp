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
import com.adolfopardo.estacionamientoapp.retrofit.response.responseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofitInit();
        if (SharedPreferencesManager.getStringValue(Constants.DNI) != null) {
            if (!SharedPreferencesManager.getStringValue(Constants.DNI).isEmpty()) {
                if (SharedPreferencesManager.getStringValue(Constants.TIPO).equalsIgnoreCase("1"))
                    goToActivity(new AdministradorActivity());
                else
                    goToActivity(new ClienteActivity());
            }
        }
    }

    public void Registrar(View view) {
        goToActivity(new RegistroActivity());
    }

    public void Login(View view) {
        EditText etUser = findViewById(R.id.etUser);
        EditText etpass = findViewById(R.id.etPass);
        try {
            if (!TextUtils.isEmpty(etUser.getText().toString()) && !TextUtils.isEmpty(etUser.getText().toString()))
                doLogin(etUser.getText().toString(), etpass.getText().toString());
            else
                showToast("Llene los campos");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Usuario o contraseña invalido");
        }
        //goToActivity(new AdministradorActivity());

    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(LoginActivity.this, activity.getClass());
        startActivity(intent);
    }

    private void retrofitInit() {
        client = AeropuertosPeruClient.getInstance();
        service = client.getAeropuertosPeruService();
    }

    private void doLogin(String user, String pass) {
        Requestlogin request = new Requestlogin(user, pass);
        Call<responseLogin> call = service.doLogin(request);

        call.enqueue(new Callback<responseLogin>() {
            @Override
            public void onResponse(Call<responseLogin> call, Response<responseLogin> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    if (response.body().getDni() != null) {
                        if (response.body().getTipo().equalsIgnoreCase("1"))
                            goToActivity(new AdministradorActivity());
                        else
                            goToActivity(new ClienteActivity());

                        SharedPreferencesManager.setStringValue(Constants.USER, response.body().getUser());
                        SharedPreferencesManager.setStringValue(Constants.DNI, response.body().getDni());
                        SharedPreferencesManager.setStringValue(Constants.TIPO, response.body().getTipo());
                        SharedPreferencesManager.setStringValue(Constants.PASS, pass);
                        SharedPreferencesManager.setStringValue(Constants.NAME, response.body().getNombres());
                    } else
                        showToast("Usuario o contraseña invalido");
                }
            }

            @Override
            public void onFailure(Call<responseLogin> call, Throwable t) {
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