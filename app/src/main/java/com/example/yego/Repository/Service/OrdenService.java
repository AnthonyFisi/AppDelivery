package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface OrdenService {

    @GET("/ordenController/listaDisponible/{idUsuario}")
    Call<OrdenGeneralGson> searchListOrdenDisponible(@Header("Authorization")String auth, @Path("idUsuario")int idUsuario);
}
