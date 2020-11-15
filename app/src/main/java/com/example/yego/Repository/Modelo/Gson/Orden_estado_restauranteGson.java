package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Orden_estado_general;

import java.util.List;

public class Orden_estado_restauranteGson {

    private List<Orden_estado_general> listaOrden_estado_general;

    public List<Orden_estado_general> getListaOrden_estado_general() {
        return listaOrden_estado_general;
    }

    public void setListaOrden_estado_general(List<Orden_estado_general> listaOrden_estado_general) {
        this.listaOrden_estado_general = listaOrden_estado_general;
    }
}
