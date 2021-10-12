package com.adolfopardo.estacionamientoapp.retrofit;


import com.adolfopardo.estacionamientoapp.retrofit.request.*;
import com.adolfopardo.estacionamientoapp.retrofit.response.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AeropuertosPeruService {

    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<responseLogin> doLogin(@Body Requestlogin request);

    @Headers("Content-Type: application/json")
    @POST("registrarUsuario.php")
    Call<responseRegistro> registro(@Body Requestregistro request);

    @Headers("Content-Type: application/json")
    @POST("listarReservasCliente.php")
    Call<List<listaReservaCliente>> listarReservasCliente(@Body RequestlistaReservaCliente request);

    @Headers("Content-Type: application/json")
    @POST("listarEstacionamientos.php")
    Call<List<listaEstacionamiento>> listarEstacionamientos();

    @Headers("Content-Type: application/json")
    @POST("registroPlacas.php")
    Call<registroPlaca> registroPlacas(@Body RequestregistroPlaca request);

    @Headers("Content-Type: application/json")
    @POST("listarPlacas.php")
    Call<List<listaPlacaCliente>> listarPlacasCliente(@Body RequestlistaPlacasCliente request);

    @Headers("Content-Type: application/json")
    @POST("registrarReserva.php")
    Call<registroReserva> registrarReserva(@Body RequestregistroReserva request);

    @Headers("Content-Type: application/json")
    @POST("registrarReservaInmediata.php")
    Call<registroReservaInmediata> registrarReservaInmediata(@Body RequestregistroReservaInmediata request);

    @Headers("Content-Type: application/json")
    @POST("listarReservasAdmin.php")
    Call<List<listaReservaAdmin>> listarReservasAdmin();


    @Headers("Content-Type: application/json")
    @POST("calcularAutosDias.php")
    Call<List<autosPorDias>> calcularAutosDias();

    @Headers("Content-Type: application/json")
    @POST("calcularAutosPrecioTotal.php")
    Call<List<autosPrecioTotal>> calcularAutosPrecioTotal();

    @Headers("Content-Type: application/json")
    @POST("listarReservasEntradasSalidas.php")
    Call<List<autosEntradasSalidas>> listarReservasEntradasSalidas();

    @Headers("Content-Type: application/json")
    @POST("calcularAutosPromedio.php")
    Call<List<autosTiempoPromedio>> calcularAutosPromedio();

    @Headers("Content-Type: application/json")
    @POST("calcularAutosMes.php")
    Call<List<autosDelMes>> calcularAutosMes();

    @Headers("Content-Type: application/json")
    @POST("registrarSalidaReserva.php")
    Call<registroSalida> registrarSalidaReserva(@Body RequestregistroSalida request);

    @Headers("Content-Type: application/json")
    @POST("eliminar10min.php")
    Call<ResponseEliminar> eliminar10min(@Body RequestEliminar request);

}
