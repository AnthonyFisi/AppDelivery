package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Orden;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GsonOrden  implements Serializable {

    @SerializedName("detalle_orden")
    @Expose
    private Orden detalle_orden;

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;

    @SerializedName("lista_productos")
    @Expose
    private List<ProductoJOINregistroPedidoJOINpedido> lista_productos;

    @SerializedName("lista_orden_general")
    @Expose
    private List<Orden_estado_general> lista_orden_general;

    public Orden getDetalle_orden() {
        return detalle_orden;
    }

    public void setDetalle_orden(Orden detalle_orden) {
        this.detalle_orden = detalle_orden;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ProductoJOINregistroPedidoJOINpedido> getLista_productos() {
        return lista_productos;
    }

    public void setLista_productos(List<ProductoJOINregistroPedidoJOINpedido> lista_productos) {
        this.lista_productos = lista_productos;
    }

    public List<Orden_estado_general> getLista_orden_general() {
        return lista_orden_general;
    }

    public void setLista_orden_general(List<Orden_estado_general> lista_orden_general) {
        this.lista_orden_general = lista_orden_general;
    }
}
