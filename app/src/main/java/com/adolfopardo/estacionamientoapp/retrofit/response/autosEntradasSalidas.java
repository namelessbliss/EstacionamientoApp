package com.adolfopardo.estacionamientoapp.retrofit.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class autosEntradasSalidas implements Parcelable {
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("listado")
    @Expose
    private List<Listado> listado = null;

    protected autosEntradasSalidas(Parcel in) {
        fecha = in.readString();
        listado = in.createTypedArrayList(Listado.CREATOR);
    }

    public static final Creator<autosEntradasSalidas> CREATOR = new Creator<autosEntradasSalidas>() {
        @Override
        public autosEntradasSalidas createFromParcel(Parcel in) {
            return new autosEntradasSalidas(in);
        }

        @Override
        public autosEntradasSalidas[] newArray(int size) {
            return new autosEntradasSalidas[size];
        }
    };

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Listado> getListado() {
        return listado;
    }

    public void setListado(List<Listado> listado) {
        this.listado = listado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fecha);
        parcel.writeTypedList(listado);
    }
}
