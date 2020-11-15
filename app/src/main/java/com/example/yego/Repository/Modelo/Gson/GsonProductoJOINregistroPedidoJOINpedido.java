package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonProductoJOINregistroPedidoJOINpedido {

    @SerializedName("listaProductoJOINregistroPedidoJOINpedido")
    @Expose
    List<ProductoJOINregistroPedidoJOINpedido> listaProductoJOINregistroPedidoJOINpedido;

    @SerializedName("cantidadOrden")
    @Expose
    int cantidadOrden;


    public List<ProductoJOINregistroPedidoJOINpedido> getListaProductoJOINregistroPedidoJOINpedido() {
        return listaProductoJOINregistroPedidoJOINpedido;
    }

    public void setListaProductoJOINregistroPedidoJOINpedido(List<ProductoJOINregistroPedidoJOINpedido> listaProductoJOINregistroPedidoJOINpedido) {
        this.listaProductoJOINregistroPedidoJOINpedido = listaProductoJOINregistroPedidoJOINpedido;
    }

    public int getCantidadOrden() {
        return cantidadOrden;
    }

    public void setCantidadOrden(int cantidadOrden) {
        this.cantidadOrden = cantidadOrden;
    }
}
