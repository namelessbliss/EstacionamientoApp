
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class listaPlacaCliente {

    @SerializedName("idPlaca")
    @Expose
    private String idPlaca;
    @SerializedName("DNI")
    @Expose
    private String dni;
    @SerializedName("nombrePlaca")
    @Expose
    private String nombrePlaca;

    public listaPlacaCliente(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }

    public listaPlacaCliente() {
    }

    public String getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(String idPlaca) {
        this.idPlaca = idPlaca;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombrePlaca() {
        return nombrePlaca;
    }

    public void setNombrePlaca(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }


}
