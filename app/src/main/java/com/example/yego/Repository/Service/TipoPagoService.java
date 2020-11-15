package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonTipoPago;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TipoPagoService {

    @GET("/TipoPagoController/lista")
    Call<GsonTipoPago> searchListTipoPago(@Header("Authorization")String auth);


    @GET("/TipoPagoController/listaEnable")
    Call<GsonTipoPago> searchListTipoPagoEnable(@Header("Authorization")String auth);

}
