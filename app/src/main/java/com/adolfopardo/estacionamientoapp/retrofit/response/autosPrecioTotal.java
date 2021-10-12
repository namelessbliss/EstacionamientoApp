
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class autosPrecioTotal {
    @SerializedName("monto")
    @Expose
    private String monto;
    @SerializedName("fechaSalida")
    @Expose
    private String fechaSalida;

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
