package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaEmpresaService {

    /*
     @GET("books/v1/volumes")
    Call<VolumesResponse> searchVolumes(
            @Query("q") String query,
            @Query("inauthor") String author,
            @Query("key") String apiKey
    );
     */

    @GET("categoriaEmpresa/mostrarCategoria")
    Call<GsonCategoriaEmpresa> searchCategoriaEmpresa();
}
