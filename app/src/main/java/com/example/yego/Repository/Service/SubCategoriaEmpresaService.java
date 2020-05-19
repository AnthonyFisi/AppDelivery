package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SubCategoriaEmpresaService {

    @GET("/subCategoriaEmpresa/ListSubCategoriafindByIdEmpresa/{id}")
    Call<GsonSubCategoriaEmpresa> searchSubCategoriaEmpresa(@Path("id") int id);
}
