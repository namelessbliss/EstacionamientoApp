package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEliminar {
    @SerializedName("DNI")
    @Expose
    private String DNI;
    @SerializedName("idEstacionamiento")
    @Expose
    private String idEstacionamiento;

    public RequestEliminar(String DNI, String idEstacionamiento) {
        this.DNI = DNI;
        this.idEstacionamiento = idEstacionamiento;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(String idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }
}
