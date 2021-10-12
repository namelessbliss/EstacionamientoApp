
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class listaEstacionamiento {

    @SerializedName("IDEstacionamiento")
    @Expose
    private String iDEstacionamiento;
    @SerializedName("Estado")
    @Expose
    private String estado;

    public String getIDEstacionamiento() {
        return iDEstacionamiento;
    }

    public void setIDEstacionamiento(String iDEstacionamiento) {
        this.iDEstacionamiento = iDEstacionamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



}
