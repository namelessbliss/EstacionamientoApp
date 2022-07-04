package com.adolfopardo.estacionamientoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruClient;
import com.adolfopardo.estacionamientoapp.retrofit.AeropuertosPeruService;
import com.adolfopardo.estacionamientoapp.retrofit.request.RequestregistroSalida;
import com.adolfopardo.estacionamientoapp.retrofit.response.registroSalida;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroSalidaClienteActivity extends AppCompatActivity {

    private TextView tvPlaca, tvEsta, tvEstacionamiento, tvCosto, tvHoraEntrada, tvTiempoEstimado, tvCostoTotal;
    Toolbar toolbar;
    private AeropuertosPeruClient client;
    private AeropuertosPeruService service;
    private Toast toast;
    String estacionamiento;

    String idReservacion, IDEstacionamiento;

    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration payPalConfiguration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_salida_cliente);
        retrofitInit();
        bindViews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        startService(intent);
    }


    private void retrofitInit() {
        client = AeropuertosPeruClient.getInstance();
        service = client.getAeropuertosPeruService();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        tvPlaca = findViewById(R.id.tvPlaca);
        tvEsta = findViewById(R.id.tvEsta);
        tvEstacionamiento = findViewById(R.id.tvEstacionamiento);
        tvCosto = findViewById(R.id.tvCosto);
        tvHoraEntrada = findViewById(R.id.tvHoraEntrada);
        tvTiempoEstimado = findViewById(R.id.tvTiempoEstimado);
        tvCostoTotal = findViewById(R.id.tvCostoTotal);

        setData();
    }

    /***
     * DEPRECATED
     * @param view
     */
    public void RegistrarSalida(View view) {

        RequestregistroSalida request;
        request = new RequestregistroSalida(idReservacion, IDEstacionamiento, "F");
        Call<registroSalida> call;
        if (IDEstacionamiento.equalsIgnoreCase("A1")) {
            call = service.editarMetodoPago(request);
        } else {
            call = service.registrarSalidaReserva(request);
        }

        call.enqueue(new Callback<registroSalida>() {
            @Override
            public void onResponse(Call<registroSalida> call, Response<registroSalida> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDescripcion().equalsIgnoreCase("Registrado")) {
                        showToast(response.body().getDescripcion());
                        goToActivity(new AdministradorActivity());
                    } else {
                        showToast("ERROR: " + response.body().getDescripcion());
                    }
                }
            }

            @Override
            public void onFailure(Call<registroSalida> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

    public void RegistrarSalida() {
        RequestregistroSalida request = new RequestregistroSalida(idReservacion, IDEstacionamiento, "F");
        Call<registroSalida> call;
        if (IDEstacionamiento.equalsIgnoreCase("A1")) {
            call = service.editarMetodoPago(request);
        } else {
            call = service.registrarSalidaReserva(request);
        }

        call.enqueue(new Callback<registroSalida>() {
            @Override
            public void onResponse(Call<registroSalida> call, Response<registroSalida> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDescripcion().equalsIgnoreCase("Registrado")) {
                        showToast("Pago " + response.body().getDescripcion());
                        goToActivity(new ClienteActivity());
                    } else {
                        showToast("ERROR: " + response.body().getDescripcion());
                    }
                }
            }

            @Override
            public void onFailure(Call<registroSalida> call, Throwable t) {
                showToast("Ocurrio un error");
            }
        });
    }

    private void setData() {
        try {
            Intent intent = getIntent();
            estacionamiento = intent.getStringExtra("IDEstacionamiento");
            idReservacion = intent.getStringExtra("idReservacion");
            IDEstacionamiento = intent.getStringExtra("IDEstacionamiento");
            tvPlaca.setText(intent.getStringExtra("placa"));
            tvEsta.setText(estacionamiento.toString());
            tvEstacionamiento.setText(intent.getStringExtra("IDEstacionamiento"));
            tvCosto.setText("S/. 4.00");
            tvHoraEntrada.setText(intent.getStringExtra("fechaEntrada"));
            setFecha();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFecha() {
        Date c = Calendar.getInstance(Locale.getDefault()).getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String formattedDate = df.format(c);
        try {
            Date date1 = df.parse(tvHoraEntrada.getText().toString());

/*            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.forLanguageTag("pe")).format(new Date());
            Date date2 = df.parse(date);*/

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("pe"));
            dateFormat.setTimeZone(TimeZone.getDefault());
            String formatted = dateFormat.format(new Date());
            Date date2 = df.parse(formatted);

            printDifference(date1, date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        tvTiempoEstimado.setText(String.format("%d horas, %d minutos,%d s",
                elapsedHours, elapsedMinutes, elapsedSeconds));

        double tiempoTotal = ((24 * elapsedDays) / 60) + (elapsedHours / 60) + elapsedMinutes;
        double costoMinuto = 0.0666666666666667;
        tvCostoTotal.setText(String.valueOf(truncarDosDecimales((4 * (elapsedHours + 1)))));
    }

    public void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(this, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public double truncarDosDecimales(double decimal) {
        decimal = (int) (decimal * 100);
        double truncado = decimal / 100.0f;
        return truncado;
    }

    public void Pagar(View view) {
        //TODO pagar paypal
        PayPalPayment payment = new PayPalPayment(new BigDecimal(Double.parseDouble(tvCostoTotal.getText().toString()) / 4), "USD", "Pagar Estacionamiento", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                RegistrarSalida();
                showToast("Pago Registrado");
            } else {
                showToast("Error Registro pago");
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}