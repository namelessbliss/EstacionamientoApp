
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class registroSalida {

    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("objeto")
    @Expose
    private Object objeto;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

}
