
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class autosDelMes {
    @SerializedName("contador")
    @Expose
    private String contador;

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

}
