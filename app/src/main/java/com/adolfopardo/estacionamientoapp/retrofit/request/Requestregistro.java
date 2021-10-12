package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requestregistro {

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
    @SerializedName("Nombres")
    @Expose
    private String Nombres;
    @SerializedName("nombrePlaca")
    @Expose
    private String nombrePlaca;

    public Requestregistro(String DNI, String user, String pass, String tipo) {
        this.DNI = DNI;
        User = user;
        Pass = pass;
        Tipo = tipo;
    }

    public Requestregistro(String DNI, String user, String pass, String tipo, String nombres, String nombrePlaca) {
        this.DNI = DNI;
        User = user;
        Pass = pass;
        Tipo = tipo;
        Nombres = nombres;
        this.nombrePlaca = nombrePlaca;
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

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getNombrePlaca() {
        return nombrePlaca;
    }

    public void setNombrePlaca(String nombrePlaca) {
        this.nombrePlaca = nombrePlaca;
    }
}
