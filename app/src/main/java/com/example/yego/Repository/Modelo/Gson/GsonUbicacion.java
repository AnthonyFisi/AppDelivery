package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Ubicacion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonUbicacion {

    @SerializedName("listaUbicacion")
    @Expose
    List<Ubicacion> listaUbicacion;

    public List<Ubicacion> getListaUbicacion() {
        return listaUbicacion;
    }

    public void setListaUbicacion(List<Ubicacion> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }
}
