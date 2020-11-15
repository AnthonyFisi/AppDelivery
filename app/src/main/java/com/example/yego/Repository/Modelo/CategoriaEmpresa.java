package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoriaEmpresa implements Serializable {

    @SerializedName("idcategoriaempresa")
    @Expose
    private int idCategoriaEmpresa;

    @SerializedName("nombre_categoria")
    @Expose
    private String nombre_categoria;

    @SerializedName("descripcion_categoria")
    @Expose
    private String descripcion_categoria;

    public static List<CategoriaEmpresa> listaFiltro;

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }

    @SerializedName("porcentajebusqueda")
    @Expose
    private double porcentajeBusqueda;

    @SerializedName("url_imagen_categoria")
    @Expose
    private String url_imagen_categoria;



    public int getIdCategoriaEmpresa() {
        return idCategoriaEmpresa;
    }

    public void setIdCategoriaEmpresa(int idCategoriaEmpresa) {
        this.idCategoriaEmpresa = idCategoriaEmpresa;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }



    public double getPorcentajeBusqueda() {
        return porcentajeBusqueda;
    }

    public void setPorcentajeBusqueda(double porcentajeBusqueda) {
        this.porcentajeBusqueda = porcentajeBusqueda;
    }

    public String getUrl_imagen_categoria() {
        return url_imagen_categoria;
    }

    public void setUrl_imagen_categoria(String url_imagen_categoria) {
        this.url_imagen_categoria = url_imagen_categoria;
    }



}
