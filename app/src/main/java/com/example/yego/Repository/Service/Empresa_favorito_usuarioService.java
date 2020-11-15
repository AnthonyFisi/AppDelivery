package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Empresa_favorito_usuario;
import com.example.yego.Repository.Modelo.Gson.Empresa_favoritaGson;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Empresa_favorito_usuarioService {

    @POST("/EmpresaFavoritoUsuarioController/registrar")
    Call<Empresa_favorito_usuario> agregarFavorito(@Header("Authorization")String auth, @Body Empresa_favorito_usuario favorito);

    @POST("/EmpresaFavoritoUsuarioController/eliminar/{idempresa}/{idusuario}")
    Call<Empresa_favorito_usuario> eliminarFavorito(@Header("Authorization")String auth, @Path("idempresa")int idempresa,@Path("idusuario")int idusuario);


}
