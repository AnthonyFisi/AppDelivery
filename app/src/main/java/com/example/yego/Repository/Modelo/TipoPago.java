package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipoPago implements Serializable {

    @SerializedName("idtipopago")
    @Expose
    private int idtipopago;

    @SerializedName("tipopago_nombre")
    @Expose
    private String tipopago_nombre;

    @SerializedName("tipopago_estado")
    @Expose
    private boolean tipopago_estado;

    @SerializedName("tipopago_url")
    @Expose
    private String tipopago_url;

    public  TipoPago(){}

    public TipoPago(int idtipopago, String tipopago_nombre, boolean tipopago_estado, String tipopago_url) {
        this.idtipopago = idtipopago;
        this.tipopago_nombre = tipopago_nombre;
        this.tipopago_estado = tipopago_estado;
        this.tipopago_url = tipopago_url;
    }

    public int getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(int idtipopago) {
        this.idtipopago = idtipopago;
    }

    public String getTipopago_nombre() {
        return tipopago_nombre;
    }

    public void setTipopago_nombre(String tipopago_nombre) {
        this.tipopago_nombre = tipopago_nombre;
    }

    public boolean isTipopago_estado() {
        return tipopago_estado;
    }

    public void setTipopago_estado(boolean tipopago_estado) {
        this.tipopago_estado = tipopago_estado;
    }

    public String getTipopago_url() {
        return tipopago_url;
    }

    public void setTipopago_url(String tipopago_url) {
        this.tipopago_url = tipopago_url;
    }
}
