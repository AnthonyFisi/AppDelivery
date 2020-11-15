package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.Envio_empresaGson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Envio_empresaService {

    @GET("/EnvioEmpresaController/ListafindByIdEmpresa/{idempresa}")
    Call<Envio_empresaGson> listaFindByIdempresa(@Header("Authorization")String auth,@Path("idempresa")int idempresa);
}
