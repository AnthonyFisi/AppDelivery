package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MainPedido {

    @SerializedName("idproducto")
    @Expose
    private int idproducto;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("precio")
    @Expose
    private float precio;

    @SerializedName("cantidad")
    @Expose
    private int cantidad;

    @SerializedName("comentario")
    @Expose
    private String comentario;



    public MainPedido(int idproducto, int idusuario, int idempresa, float precio, int cantidad,String comentario) {
        this.idproducto = idproducto;
        this.idusuario = idusuario;
        this.idempresa = idempresa;
        this.precio = precio;
        this.cantidad = cantidad;
        this.comentario=comentario;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
