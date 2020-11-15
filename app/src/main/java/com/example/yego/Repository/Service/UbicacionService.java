package com.example.yego.Repository.Service;

import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.Ubicacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UbicacionService {

    @GET("/UbicacionController/listaTotalByUsuario/{idusuario}")
    Call<GsonUbicacion> searchListUbicacion(@Header("Authorization") String auth,@Path("idusuario")int idusuario);

    @GET("/UbicacionController/ObjetoByUbicacion/{idubicacion}")
    Call<GsonUbicacion> searchUbicacion(@Header("Authorization") String auth,@Path("idubicacion")int idubicacion);

    @POST("/UbicacionController/registrar/{idOldUbicacion}")
    Call<Ubicacion> registarUbicacion(@Header("Authorization") String auth,@Body Ubicacion ubicacion ,@Path("idOldUbicacion") int idOldUbicacion);

    @POST("/UbicacionController/updateEstadoUbicacion/{idOldUbicacion}/{idNewUbicacion}")
    Call<GsonUbicacion> updateEstadoUbicacion(@Header("Authorization") String auth,@Path("idOldUbicacion")int idOldUbicacion,@Path("idNewUbicacion")int idNewUbicacion);

    @POST("/UbicacionController/deleteUbicacion/{idUbicacion}")
    Call<GsonUbicacion> deleteUbicacion(@Header("Authorization") String auth,@Path("idUbicacion")int idUbicacion);

    @GET("maps/api/geocode/json")
    Call<String> searchLocationAddress(@Query("latlng")String LatLong, @Query("key")String key);


    @POST("UbicacionController/registrarFirstTime")
    Call<Ubicacion> registrarFirstTimeUbicacion(@Header("Authorization")String auth,@Body Ubicacion ubicacion);

}
