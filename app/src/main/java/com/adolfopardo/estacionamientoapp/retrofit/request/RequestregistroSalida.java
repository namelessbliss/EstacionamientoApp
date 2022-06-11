package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestregistroSalida {
    @SerializedName("idReservacion")
    @Expose
    private String idReservacion;

    @SerializedName("IDEstacionamiento")
    @Expose
    private String IDEstacionamiento;

    //Todo mandar metodo de pago E= efectivo F=paypal
    @SerializedName("metodo_pago")
    @Expose
    private String metodo_pago;

    public RequestregistroSalida() {
    }

    public RequestregistroSalida(String idReservacion, String IDEstacionamiento, String metodo_pago) {
        this.idReservacion = idReservacion;
        this.IDEstacionamiento = IDEstacionamiento;
        this.metodo_pago = metodo_pago;
    }

    public String getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(String idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getIDEstacionamiento() {
        return IDEstacionamiento;
    }

    public void setIDEstacionamiento(String IDEstacionamiento) {
        this.IDEstacionamiento = IDEstacionamiento;
    }
}
