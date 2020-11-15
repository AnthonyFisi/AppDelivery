package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.MainPedido;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegistroPedidoService {


    @POST("/registroPedido/agregarProducto")
    Call<MainPedido> incrementarProducto(@Header("Authorization")String auth, @Body MainPedido mainPedido);

    @POST("/registroPedido/disminuirProducto")
    Call<MainPedido> disminuirProducto(@Header("Authorization")String auth,@Body MainPedido mainPedido);

    @POST("/registroPedido/eliminarCarrito/{idempresa}/{idusuario}")
    Call<MainPedido> eliminarCarrito(@Header("Authorization")String auth,@Path("idempresa") int idempresa,@Path("idusuario") int idusuario);

}
