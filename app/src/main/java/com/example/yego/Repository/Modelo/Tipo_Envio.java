package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tipo_Envio  implements Serializable {

    @SerializedName("idtipo_envio")
    @Expose
    private int idtipo_envio;

    @SerializedName("nombre_tipo_envio")
    @Expose
    private String nombre_tipo_envio;

    public int getIdtipo_envio() {
        return idtipo_envio;
    }

    public void setIdtipo_envio(int idtipo_envio) {
        this.idtipo_envio = idtipo_envio;
    }

    public String getNombre_tipo_envio() {
        return nombre_tipo_envio;
    }

    public void setNombre_tipo_envio(String nombre_tipo_envio) {
        this.nombre_tipo_envio = nombre_tipo_envio;
    }
}
