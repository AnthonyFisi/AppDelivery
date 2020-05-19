package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonPublicidad;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PublicidadService {

    @GET("/PublicidadController/lista")
    Call<GsonPublicidad> searchListaPublicidad();
}
