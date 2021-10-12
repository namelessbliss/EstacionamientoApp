package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestregistroReservaInmediata {
    @SerializedName("idPlaca")
    @Expose
    private String idPlaca;

    @SerializedName("idEstacionamiento")
    @Expose
    private String idEstacionamiento;
    @SerializedName("costoHora")
    @Expose
    private String costoHora;
    @SerializedName("DNI")
    @Expose
    private String DNI;

    public RequestregistroReservaInmediata() {
    }

    public RequestregistroReservaInmediata(String idPlaca, String idEstacionamiento, String costoHora, String DNI) {
        this.idPlaca = idPlaca;
        this.idEstacionamiento = idEstacionamiento;
        this.costoHora = costoHora;
        this.DNI = DNI;
    }

    public String getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(String idPlaca) {
        this.idPlaca = idPlaca;
    }

    public String getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(String idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public String getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(String costoHora) {
        this.costoHora = costoHora;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
