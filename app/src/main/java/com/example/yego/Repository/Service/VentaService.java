package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Venta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VentaService {

    @POST("/VentaController/registrar")
    Call<Venta> registarVenta(@Header("Authorization")String auth,@Body Venta venta );

    @POST("/VentaController/registrarMesa")
    Call<Venta> registarVentaMesa(@Header("Authorization")String auth,@Body Venta venta );


    @POST("/VentaController/registrarAlternative")
    Call<Venta> registarVentaAlternative(@Header("Authorization")String auth,@Body Venta venta );
}
