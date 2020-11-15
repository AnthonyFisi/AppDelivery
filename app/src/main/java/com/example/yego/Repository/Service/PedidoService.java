package com.example.yego.Repository.Service;


import com.example.yego.Repository.Modelo.MainPedido;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PedidoService {


    @POST("/pedidoController/registrarPedido")
    Call<MainPedido> insertPedido(@Header("Authorization")String auth, @Body MainPedido mainPedido);


}
