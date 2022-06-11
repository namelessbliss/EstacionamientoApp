package com.adolfopardo.estacionamientoapp.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requestlogin {
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Pass")
    @Expose
    private String pass;

    public Requestlogin(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public Requestlogin() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
