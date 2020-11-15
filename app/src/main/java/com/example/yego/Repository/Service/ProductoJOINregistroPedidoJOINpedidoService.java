package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductoJOINregistroPedidoJOINpedidoService {

    @GET("/ProductoJOINregistroPedidoJOINpedidoController/listaTotal/{idusuario}")
    Call<GsonProductoJOINregistroPedidoJOINpedido> listaCarrito(@Header("Authorization")String auth, @Path("idusuario")int idusuario);

    @GET("/ProductoJOINregistroPedidoJOINpedidoController/listaByEmpresa/{idusuario}/{idempresa}")
    Call<GsonProductoJOINregistroPedidoJOINpedido> listaCarritoByEmpresa(@Header("Authorization")String auth,@Path("idusuario")int idusuario,@Path("idempresa")int idempresa);

    @GET("/ProductoJOINregistroPedidoJOINpedidoController/listaTotalByUsuario/{idusuario}")
    Call<GsonProductoJOINregistroPedidoJOINpedido> listaCarritoByUsuario(@Header("Authorization")String auth,@Path("idusuario")int idusuario);
}
