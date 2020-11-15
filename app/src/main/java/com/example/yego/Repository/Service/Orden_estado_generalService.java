package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Orden_estado_general;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Orden_estado_generalService {

    @POST("/OrdenEstadoGeneralController/registrar")
    Call<Orden_estado_general> registrar_estado_general(@Header("Authorization")String auth, @Body Orden_estado_general estado);

}
