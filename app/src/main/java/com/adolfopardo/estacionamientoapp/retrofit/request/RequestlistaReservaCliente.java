package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestlistaReservaCliente {

    @SerializedName("DNI")
    @Expose
    private String DNI;
    @SerializedName("User")
    @Expose
    private String User;

    @SerializedName("Pass")
    @Expose
    private String Pass;
    @SerializedName("Tipo")
    @Expose
    private String Tipo;

    public RequestlistaReservaCliente() {
    }

    public RequestlistaReservaCliente(String DNI, String user, String pass, String tipo) {
        this.DNI = DNI;
        User = user;
        Pass = pass;
        Tipo = tipo;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
