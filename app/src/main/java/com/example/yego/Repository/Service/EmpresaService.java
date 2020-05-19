package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmpresaService {


    /* FRAGMENT SUBCATEGORIA BY IDCATEGORIA AND UBICACION */

    @GET("/EmpresaController/ListafindByLocation/{idCategoria}/{Ubicacion}")
    Call<GsonEmpresa> searchListafindByLocation(@Path("idCategoria") int idCategoria, @Path("Ubicacion")String Ubicacion);


    /* FRAGMENT LOS MAS BUSCADOS SORT BY  */

    @GET("/EmpresaController/ListaSortfindBy/{idCategoria}")
    Call<GsonEmpresa> searchListaSortfindBy(@Path("idCategoria") int idCategoria);


    /* FRAGMENT SUBCATEGORIA AND UBICACION*/
    @GET("/EmpresaController/ListafindByLocationSubCategoria/{idSubCategoria}/{Ubicacion}")
    Call<GsonEmpresa> searchListafindByLocationSubCategoria(@Path("idSubCategoria") int idSubCategoria,@Path("Ubicacion")String Ubicacion);


    /*Lista de SubCategoria*/
    @GET("/EmpresaController/ListafindBy/{idSubCategoria}")
    Call<GsonEmpresa> searchListafindBy(@Path("idSubCategoria") int idSubCategoria);


}
