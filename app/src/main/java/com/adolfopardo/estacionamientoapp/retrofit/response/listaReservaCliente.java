
package com.adolfopardo.estacionamientoapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class listaReservaCliente {

    @SerializedName("DNI")
    @Expose
    private String dni;
    @SerializedName("idReservacion")
    @Expose
    private String idReservacion;
    @SerializedName("idPlaca")
    @Expose
    private String idPlaca;
    @SerializedName("fechaEntrada")
    @Expose
    private String fechaEntrada;
    @SerializedName("fechaSalida")
    @Expose
    private Object fechaSalida;
    @SerializedName("idEstacionamiento")
    @Expose
    private String idEstacionamiento;
    @SerializedName("costoTotal")
    @Expose
    private String costoTotal;
    @SerializedName("costoHora")
    @Expose
    private String costoHora;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("nombrePlaca")
    @Expose
    private String nombrePlaca;
    private String timer;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(String idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(String idPlaca) {
        this.idPlaca = idPlaca;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Object getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Object fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getIdEstacionamiento() {
        return idEstacionamiento;
    }

    public void setIdEstacionamiento(String idEstacionamiento) {
        this.idEstacionamiento = idEstacionamiento;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(String costoHora) {
        this.costoHora = costoHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrePlaca() {
        return nombrePlaca;
    }

    public void setNombrePlaca(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}
