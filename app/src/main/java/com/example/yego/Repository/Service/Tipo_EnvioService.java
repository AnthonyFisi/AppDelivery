package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonTipo_Envio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Tipo_EnvioService {

    @GET("/TipoEnvioController/lista")
    Call<GsonTipo_Envio> searchListTipo_Envio(@Header("Authorization")String auth);
}
