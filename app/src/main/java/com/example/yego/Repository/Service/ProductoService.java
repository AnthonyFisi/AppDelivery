package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Word;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductoService {

    @GET("/productoController/listaCategoria/{idcategoriaproducto}/{idempresa}")
    Call<GsonProducto> searchListProducto(@Header("Authorization")String auth, @Path("idcategoriaproducto")int idcategoriaproducto, @Path("idempresa")int idempresa);

    @GET("/productoController/listaCategoria/{idempresa}")
    Call<GsonProducto> searchListProductoByEmpresa(@Header("Authorization")String auth,@Path("idempresa")int idempresa);


    @GET("/productoController/searchWord/{idempresa}/{word}")
    Call<GsonProducto> serachByWord(@Header("Authorization")String auth,@Path("idempresa")int idempresa,@Path("word")String word);
}
