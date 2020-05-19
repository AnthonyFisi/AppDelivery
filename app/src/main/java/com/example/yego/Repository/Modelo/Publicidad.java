package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Publicidad {

    @SerializedName("idpublicidad")
    @Expose
    private int idpublicidad;

    @SerializedName("detalle_publicidad")
    @Expose
    private String detalle_publicidad;

    @SerializedName("url_imagen_publicidad")
    @Expose
    private String url_imagen_publicidad;

    public int getIdpublicidad() {
        return idpublicidad;
    }

    public void setIdpublicidad(int idpublicidad) {
        this.idpublicidad = idpublicidad;
    }

    public String getDetalle_publicidad() {
        return detalle_publicidad;
    }

    public void setDetalle_publicidad(String detalle_publicidad) {
        this.detalle_publicidad = detalle_publicidad;
    }

    public String getUrl_imagen_publicidad() {
        return url_imagen_publicidad;
    }

    public void setUrl_imagen_publicidad(String url_imagen_publicidad) {
        this.url_imagen_publicidad = url_imagen_publicidad;
    }
}
