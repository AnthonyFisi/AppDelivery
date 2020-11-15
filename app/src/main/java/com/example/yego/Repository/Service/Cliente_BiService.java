package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Cliente_Bi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Cliente_BiService {

    @GET("/clientebi/basic/{idusuario}")
    Call<Cliente_Bi> findInformacionClienteBi(@Header("Authorization")String auth, @Path("idusuario")int idusuario);
}
