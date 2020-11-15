package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Orden_estado_general implements Serializable {

    @SerializedName("idoeg")
    @Expose
    private int idoeg;

    @SerializedName("idventa")
    @Expose
    private int idventa;

    @SerializedName("idestadogeneral")
    @Expose
    private int idestadogeneral;

    @SerializedName("tiempo_aproximado")
    @Expose
    private String tiempo_aproximado;

    @SerializedName("fecha")
    @Expose
    private Timestamp fecha;

    public int getIdoeg() {
        return idoeg;
    }

    public void setIdoeg(int idoeg) {
        this.idoeg = idoeg;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdestadogeneral() {
        return idestadogeneral;
    }

    public void setIdestadogeneral(int idestadogeneral) {
        this.idestadogeneral = idestadogeneral;
    }

    public String getTiempo_aproximado() {
        return tiempo_aproximado;
    }

    public void setTiempo_aproximado(String tiempo_aproximado) {
        this.tiempo_aproximado = tiempo_aproximado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
