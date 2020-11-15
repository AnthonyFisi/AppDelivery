package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ubicacion  implements Serializable {


    //public static List<Ubicacion> listaUbicacion=new ArrayList<>();
    public static Ubicacion ubicacionEnable;

    @SerializedName("idubicacion")
    @Expose
    private int idubicacion;

    @SerializedName("ubicacion_nombre")
    @Expose
    private String ubicacion_nombre;

    @SerializedName("ubicacion_comentarios")
    @Expose
    private String ubicacion_comentarios;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;

    @SerializedName("ubicacion_estado")
    @Expose
    private boolean ubicacion_estado;

    @SerializedName("eliminado")
    @Expose
    private boolean eliminado ;

    @SerializedName("maps_distrito")
    @Expose
    private String maps_distrito;

    @SerializedName("maps_detalle")
    @Expose
    private String maps_detalle;

    @SerializedName("maps_coordenada_x")
    @Expose
    private String maps_coordenada_x;

    @SerializedName("maps_coordenada_y")
    @Expose
    private String maps_coordenada_y;


    public Ubicacion(){ }

    public Ubicacion(int idubicacion, String ubicacion_nombre, String ubicacion_comentarios, int idusuario, boolean ubicacion_estado, boolean eliminado, String maps_distrito, String maps_detalle, String maps_coordenada_x, String maps_coordenada_y) {
        this.idubicacion = idubicacion;
        this.ubicacion_nombre = ubicacion_nombre;
        this.ubicacion_comentarios = ubicacion_comentarios;
        this.idusuario = idusuario;
        this.ubicacion_estado = ubicacion_estado;
        this.eliminado = eliminado;
        this.maps_distrito = maps_distrito;
        this.maps_detalle = maps_detalle;
        this.maps_coordenada_x = maps_coordenada_x;
        this.maps_coordenada_y = maps_coordenada_y;
    }

    public int getIdubicacion() {
        return idubicacion;
    }

    public void setIdubicacion(int idubicacion) {
        this.idubicacion = idubicacion;
    }

    public String getUbicacion_nombre() {
        return ubicacion_nombre;
    }

    public void setUbicacion_nombre(String ubicacion_nombre) {
        this.ubicacion_nombre = ubicacion_nombre;
    }

    public String getUbicacion_comentarios() {
        return ubicacion_comentarios;
    }

    public void setUbicacion_comentarios(String ubicacion_comentarios) {
        this.ubicacion_comentarios = ubicacion_comentarios;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public boolean isUbicacion_estado() {
        return ubicacion_estado;
    }

    public void setUbicacion_estado(boolean ubicacion_estado) {
        this.ubicacion_estado = ubicacion_estado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getMaps_distrito() {
        return maps_distrito;
    }

    public void setMaps_distrito(String maps_distrito) {
        this.maps_distrito = maps_distrito;
    }

    public String getMaps_detalle() {
        return maps_detalle;
    }

    public void setMaps_detalle(String maps_detalle) {
        this.maps_detalle = maps_detalle;
    }

    public String getMaps_coordenada_x() {
        return maps_coordenada_x;
    }

    public void setMaps_coordenada_x(String maps_coordenada_x) {
        this.maps_coordenada_x = maps_coordenada_x;
    }

    public String getMaps_coordenada_y() {
        return maps_coordenada_y;
    }

    public void setMaps_coordenada_y(String maps_coordenada_y) {
        this.maps_coordenada_y = maps_coordenada_y;
    }

    public static Ubicacion enableLocation(List<Ubicacion> listaUbicacion){
        Ubicacion ubicacion =null;

        for(Ubicacion data:listaUbicacion){
            if(data.isUbicacion_estado()) ubicacion=data;
        }

        return ubicacion;
    }


}
