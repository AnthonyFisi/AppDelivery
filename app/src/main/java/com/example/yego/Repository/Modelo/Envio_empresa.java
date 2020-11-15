package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Envio_empresa {

    @SerializedName("idtee")
    @Expose
    private int idtee;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("idtipoenvio")
    @Expose
    private int idtipoenvio;

    @SerializedName("nombre_tipo_envio")
    @Expose
    private String nombre_tipo_envio;

    @SerializedName("url_foto")
    @Expose
    private String url_foto;

    @SerializedName("precio")
    @Expose
    private float precio;



    public int getIdtee() {
        return idtee;
    }

    public void setIdtee(int idtee) {
        this.idtee = idtee;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdtipoenvio() {
        return idtipoenvio;
    }

    public void setIdtipoenvio(int idtipoenvio) {
        this.idtipoenvio = idtipoenvio;
    }

    public String getNombre_tipo_envio() {
        return nombre_tipo_envio;
    }

    public void setNombre_tipo_envio(String nombre_tipo_envio) {
        this.nombre_tipo_envio = nombre_tipo_envio;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }


    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
