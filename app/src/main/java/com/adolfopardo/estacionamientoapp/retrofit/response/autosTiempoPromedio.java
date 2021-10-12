
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class autosTiempoPromedio {
    @SerializedName("promedio")
    @Expose
    private String promedio;

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }
}
