package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestregistroPlaca {
    @SerializedName("DNI")
    @Expose
    private String DNI;

    @SerializedName("nombrePlaca")
    @Expose
    private String nombrePlaca;

    public RequestregistroPlaca() {
    }

    public RequestregistroPlaca(String DNI, String nombrePlaca) {
        this.DNI = DNI;
        this.nombrePlaca = nombrePlaca;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombrePlaca() {
        return nombrePlaca;
    }

    public void setNombrePlaca(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }
}
