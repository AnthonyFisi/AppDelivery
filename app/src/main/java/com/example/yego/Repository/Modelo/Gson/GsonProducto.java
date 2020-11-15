package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Producto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonProducto {

    @SerializedName("listaProducto")
    @Expose
    List<Producto> listaProducto;



    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }
}
