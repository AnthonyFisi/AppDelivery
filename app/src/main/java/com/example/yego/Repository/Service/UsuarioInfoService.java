package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.UsuarioInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioInfoService {

    @POST("/api/auth/signupProvider")
    Call<JwtResponse> registrarUsuarioProvider( @Body UsuarioInfo usuarioInfo );
}
