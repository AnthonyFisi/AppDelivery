package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Empresa {

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("idsubcategoriaempresa")
    @Expose
    private int idsubcategoriaempresa;

    @SerializedName("nombre_empresa")
    @Expose
    private String nombre_empresa;

    @SerializedName("ubicacion_empresa")
    @Expose
    private String ubicacion_empresa;

    @SerializedName("ruc_empresa")
    @Expose
    private String ruc_empresa;

    @SerializedName("telefono_empresa")
    @Expose
    private String telefono_empresa;

    @SerializedName("celular_empresa")
    @Expose
    private String celular_empresa;

    @SerializedName("boletas")
    @Expose
    private boolean boletas;

    @SerializedName("descripcion_empresa")
    @Expose
    private String descripcion_empresa;

    @SerializedName("urlfoto_empresa")
    @Expose
    private String urlfoto_empresa;

    @SerializedName("nombredueno_empresa")
    @Expose
    private String nombredueno_empresa;

    @SerializedName("numerolocales")
    @Expose
    private int numerolocales;

    @SerializedName("idcuentaempresa")
    @Expose
    private int idcuentaempresa;

    @SerializedName("porcentaje_popularidad")
    @Expose
    private double porcentaje_popularidad;

    public double getPorcentaje_popularidad() {
        return porcentaje_popularidad;
    }

    public void setPorcentaje_popularidad(double porcentaje_popularidad) {
        this.porcentaje_popularidad = porcentaje_popularidad;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdsubcategoriaempresa() {
        return idsubcategoriaempresa;
    }

    public void setIdsubcategoriaempresa(int idsubcategoriaempresa) {
        this.idsubcategoriaempresa = idsubcategoriaempresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getUbicacion_empresa() {
        return ubicacion_empresa;
    }

    public void setUbicacion_empresa(String ubicacion_empresa) {
        this.ubicacion_empresa = ubicacion_empresa;
    }

    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public String getTelefono_empresa() {
        return telefono_empresa;
    }

    public void setTelefono_empresa(String telefono_empresa) {
        this.telefono_empresa = telefono_empresa;
    }

    public String getCelular_empresa() {
        return celular_empresa;
    }

    public void setCelular_empresa(String celular_empresa) {
        this.celular_empresa = celular_empresa;
    }

    public boolean isBoletas() {
        return boletas;
    }

    public void setBoletas(boolean boletas) {
        this.boletas = boletas;
    }

    public String getDescripcion_empresa() {
        return descripcion_empresa;
    }

    public void setDescripcion_empresa(String descripcion_empresa) {
        this.descripcion_empresa = descripcion_empresa;
    }

    public String getUrlfoto_empresa() {
        return urlfoto_empresa;
    }

    public void setUrlfoto_empresa(String urlfoto_empresa) {
        this.urlfoto_empresa = urlfoto_empresa;
    }

    public String getNombredueno_empresa() {
        return nombredueno_empresa;
    }

    public void setNombredueno_empresa(String nombredueno_empresa) {
        this.nombredueno_empresa = nombredueno_empresa;
    }

    public int getNumerolocales() {
        return numerolocales;
    }

    public void setNumerolocales(int numerolocales) {
        this.numerolocales = numerolocales;
    }

    public int getIdcuentaempresa() {
        return idcuentaempresa;
    }

    public void setIdcuentaempresa(int idcuentaempresa) {
        this.idcuentaempresa = idcuentaempresa;
    }
}
