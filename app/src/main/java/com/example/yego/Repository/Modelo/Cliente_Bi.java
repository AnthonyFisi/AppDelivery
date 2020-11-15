package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cliente_Bi implements Serializable {

    private static Cliente_Bi mCliente_bi=new Cliente_Bi();


    @SerializedName("idusuariogeneral")
    @Expose
    private int idusuariogeneral;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("foto")
    @Expose
    private String foto;


    @SerializedName("idcuentausuario")
    @Expose
    private int idcuentausuario;

    @SerializedName("activa")
    @Expose
    private boolean activa;

    @SerializedName("idtipocuenta")
    @Expose
    private int idtipocuenta;

    @SerializedName("idubicacion")
    @Expose
    private int idubicacion;

    @SerializedName("ubicacion_nombre")
    @Expose
    private String ubicacion_nombre;

    @SerializedName("ubicacion_comentarios")
    @Expose
    private String ubicacion_comentarios;

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


    public int getIdusuariogeneral() {
        return idusuariogeneral;
    }

    public void setIdusuariogeneral(int idusuariogeneral) {
        this.idusuariogeneral = idusuariogeneral;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public int getIdcuentausuario() {
        return idcuentausuario;
    }

    public void setIdcuentausuario(int idcuentausuario) {
        this.idcuentausuario = idcuentausuario;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public int getIdtipocuenta() {
        return idtipocuenta;
    }

    public void setIdtipocuenta(int idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
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


    public static void registrarCliente_bi(Cliente_Bi cliente_bi){
        mCliente_bi=cliente_bi;
    }
    public static Cliente_Bi getCliente(){
        return mCliente_bi;
    }
}
