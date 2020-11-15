package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface EmpresaService {
    
    @GET("/EmpresaController/ListafindByLocation/{idCategoria}/{ubicacion}")
    Call<GsonEmpresa> searchListafindByLocation(@Header("authorization") String auth, @Path("idCategoria")int idCategoria, @Path("ubicacion") String ubicacion);

    @GET("/EmpresaController/ListaCategoriaComplemento/{idCategoria}/{ubicacion}")
    Call<GsonEmpresa> searchListaCategoriaComplementaria(@Header("authorization") String auth, @Path("idCategoria") int idCategoria, @Path("ubicacion") String ubicacion);


    @GET("/EmpresaController/ListafindByLocationSubCategoria/{idSubCategoria}/{ubicacion}")
    Call<GsonEmpresa> searchListafindByLocationSubCategoria(@Header("authorization") String auth, @Path("idSubCategoria")int idSubCategoria, @Path("ubicacion") String ubicacion);

    @GET("/EmpresaController/ListaSubcategoriaComplemento/{idSubCategoria}/{ubicacion}")
    Call<GsonEmpresa> searchListaSubcategoriaComplementaria(@Header("authorization") String auth, @Path("idSubCategoria")int idSubCategoria, @Path("ubicacion") String ubicacion);


    @GET("/EmpresaController/ListaCerca/{ubicacion}")
    Call<GsonEmpresa> searchListaTotalcerca(@Header("authorization") String auth, @Path("ubicacion") String ubicacion);

    @GET("/EmpresaController/filtro/{idcategoriaempresa}/{distancia}/{preciodelivery}/{ubicacion}")
    Call<GsonEmpresa> filtroEmpresa(@Header("authorization") String auth,
                                            @Path("idcategoriaempresa")int idcategoriaempresa,
                                            @Path("distancia")int distancia,
                                            @Path("preciodelivery")float preciodelivery,
                                            @Path("ubicacion")String ubicacion);



    @GET("/EmpresaController/findByEmpresa/{idempresa}")
    Call<Empresa> getEmpresaById(@Header("authorization") String auth, @Path("idempresa") int idempresa);

    @GET("/EmpresaFavoritoUsuarioController/lista/{idempresa}")
    Call<GsonEmpresa> listaEmpresaFavorita(@Header("authorization") String auth, @Path("idempresa") int idempresa);


}
