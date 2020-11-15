package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubCategoriaEmpresa implements Serializable {

    @SerializedName("idsubcategoriaempresa")
    @Expose
    private int idSubCategoriaEmpresa;

    @SerializedName("idcategoriaempresa")
    @Expose
    private int idCategoriaEmpresa;

    @SerializedName("nombre_subcategoria")
    @Expose
    private String nombre_subcategoria;

    @SerializedName("descripcion_subcategoria")
    @Expose
    private String descripcion_subcategoria;

    @SerializedName("porcentajebusqueda")
    @Expose
    private double porcentajeBusqueda;

    @SerializedName("url_imagen_subcategoria")
    @Expose
    private String url_imagen_subcategoria;


    public int getIdSubCategoriaEmpresa() {
        return idSubCategoriaEmpresa;
    }

    public void setIdSubCategoriaEmpresa(int idSubCategoriaEmpresa) {
        this.idSubCategoriaEmpresa = idSubCategoriaEmpresa;
    }

    public int getIdCategoriaEmpresa() {
        return idCategoriaEmpresa;
    }

    public void setIdCategoriaEmpresa(int idCategoriaEmpresa) {
        this.idCategoriaEmpresa = idCategoriaEmpresa;
    }

    public String getNombre_subcategoria() {
        return nombre_subcategoria;
    }

    public void setNombre_subcategoria(String nombre_subcategoria) {
        this.nombre_subcategoria = nombre_subcategoria;
    }

    public String getDescripcion_subcategoria() {
        return descripcion_subcategoria;
    }

    public void setDescripcion_subcategoria(String descripcion_subcategoria) {
        this.descripcion_subcategoria = descripcion_subcategoria;
    }

    public double getPorcentajeBusqueda() {
        return porcentajeBusqueda;
    }

    public void setPorcentajeBusqueda(double porcentajeBusqueda) {
        this.porcentajeBusqueda = porcentajeBusqueda;
    }

    public String getUrl_imagen_subcategoria() {
        return url_imagen_subcategoria;
    }

    public void setUrl_imagen_subcategoria(String url_imagen_subcategoria) {
        this.url_imagen_subcategoria = url_imagen_subcategoria;
    }
}
