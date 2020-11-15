package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Empresa_favorito_usuario {

    @SerializedName("idefu")
    @Expose
    private int idefu;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;

    @SerializedName("fecha")
    @Expose
    private Timestamp fecha;

    public int getIdefu() {
        return idefu;
    }

    public void setIdefu(int idefu) {
        this.idefu = idefu;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }


}
