package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriaEmpresa {
   /* @SerializedName("idCategoria")
    @Expose
    private int idCategoria;

    @SerializedName("categoria_nombre")
    @Expose
    private String categoria_nombre;

    @SerializedName("categoria_descripcion")
    @Expose
    private String categoria_descripcion;

    @SerializedName("categoria_imagen")
    @Expose
    private String categoria_uri_post;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria_nombre() {
        return categoria_nombre;
    }

    public void setCategoria_nombre(String categoria_nombre) {
        this.categoria_nombre = categoria_nombre;
    }

    public String getCategoria_descripcion() {
        return categoria_descripcion;
    }

    public void setCategoria_descripcion(String categoria_descripcion) {
        this.categoria_descripcion = categoria_descripcion;
    }

    public String getCategoria_uri_post() {
        return categoria_uri_post;
    }

    public void setCategoria_uri_post(String categoria_uri_post) {
        this.categoria_uri_post = categoria_uri_post;
    }
*/
    @SerializedName("idcategoriaempresa")
    @Expose
    private int idCategoriaEmpresa;

    @SerializedName("nombre_categoria")
    @Expose
    private String nombre_categoria;

    @SerializedName("descripcion_categoria")
    @Expose
    private String descripcion_categoria;

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

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
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
