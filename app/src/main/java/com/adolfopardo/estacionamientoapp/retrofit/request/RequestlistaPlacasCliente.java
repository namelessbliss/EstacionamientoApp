package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestlistaPlacasCliente {

    @SerializedName("DNI")
    @Expose
    private String DNI;

    public RequestlistaPlacasCliente() {
    }

    public RequestlistaPlacasCliente(String DNI) {
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
