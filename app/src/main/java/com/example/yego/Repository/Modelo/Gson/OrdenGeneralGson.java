package com.example.yego.Repository.Modelo.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdenGeneralGson {

    public static int cantidadOrden=0;


    @SerializedName("lista")
    @Expose
    private List<GsonOrden> lista;


    public List<GsonOrden> getLista() {
        return lista;
    }

    public void setLista(List<GsonOrden> lista) {
        this.lista = lista;
    }
}
