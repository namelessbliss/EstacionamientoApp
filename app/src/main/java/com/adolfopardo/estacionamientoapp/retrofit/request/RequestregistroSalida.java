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

    public RequestregistroSalida() {
    }

    public RequestregistroSalida(String idReservacion, String IDEstacionamiento) {
        this.idReservacion = idReservacion;
        this.IDEstacionamiento = IDEstacionamiento;
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
