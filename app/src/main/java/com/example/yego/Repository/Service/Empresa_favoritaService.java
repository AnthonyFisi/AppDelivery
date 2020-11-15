package com.example.yego.Repository.Service;



import com.example.yego.Repository.Modelo.Gson.Empresa_favoritaGson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Empresa_favoritaService {

    @GET("/EmpresaFavoritaController/lista/{idusuario}")
    Call<Empresa_favoritaGson> listaEmpresaFavorita(@Header("Authorization")String auth, @Path("idusuario") int idusuario);

}
