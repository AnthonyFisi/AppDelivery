package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Publicidad;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonPublicidad {

    @SerializedName("listaPublicidad")
    @Expose
    private List<Publicidad> listaPublicidad;

    public List<Publicidad> getListaPublicidad() {
        return listaPublicidad;
    }

    public void setListaPublicidad(List<Publicidad> listaPublicidad) {
        this.listaPublicidad = listaPublicidad;
    }
}
