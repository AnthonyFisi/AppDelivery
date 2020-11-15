package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonNombreSubCategoria;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NombreSubCategoriaService {

    @GET("/NombreSubCategoria/listaIdEmpresa/{idempresa}")
    Call<GsonNombreSubCategoria> searchListNombreSubCategoria(@Header("Authorization")String auth, @Path("idempresa")int idempresa);

}
