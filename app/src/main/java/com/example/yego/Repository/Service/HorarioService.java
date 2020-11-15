package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonHorario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface HorarioService {

    @GET("/horarioController/lista")
    Call<GsonHorario> searchListHorario(@Header("Authorization")String auth);
}
