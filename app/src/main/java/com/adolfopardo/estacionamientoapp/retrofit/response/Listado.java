package com.adolfopardo.estacionamientoapp.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Listado implements Parcelable {
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
    private String fechaSalida;
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

    protected Listado(Parcel in) {
        dni = in.readString();
        idReservacion = in.readString();
        idPlaca = in.readString();
        fechaEntrada = in.readString();
        fechaSalida = in.readString();
        idEstacionamiento = in.readString();
        costoTotal = in.readString();
        costoHora = in.readString();
        estado = in.readString();
        nombrePlaca = in.readString();
    }

    public static final Creator<Listado> CREATOR = new Creator<Listado>() {
        @Override
        public Listado createFromParcel(Parcel in) {
            return new Listado(in);
        }

        @Override
        public Listado[] newArray(int size) {
            return new Listado[size];
        }
    };

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

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dni);
        parcel.writeString(idReservacion);
        parcel.writeString(idPlaca);
        parcel.writeString(fechaEntrada);
        parcel.writeString(fechaSalida);
        parcel.writeString(idEstacionamiento);
        parcel.writeString(costoTotal);
        parcel.writeString(costoHora);
        parcel.writeString(estado);
        parcel.writeString(nombrePlaca);
    }
}
