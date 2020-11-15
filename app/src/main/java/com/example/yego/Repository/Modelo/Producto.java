package com.example.yego.Repository.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;


public class Producto  implements Serializable {

    @SerializedName("idproducto")
    @Expose
    private int idproducto;

    @SerializedName("idcategoriaproducto")
    @Expose
    private int idcategoriaproducto;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("producto_nombre")
    @Expose
    private String producto_nombre;

    @SerializedName("producto_precio")
    @Expose
    private float producto_precio;

    @SerializedName("producto_stock")
    @Expose
    private int producto_stock;

    @SerializedName("producto_fechacreacion")
    @Expose
    private Date producto_fechacreacion;

    @SerializedName("producto_uriimagen")
    @Expose
    private String producto_uriimagen;

    @SerializedName("producto_calificacion")
    @Expose
    private float producto_calificacion;

    @SerializedName("producto_detalle")
    @Expose
    private String producto_detalle;

    @SerializedName("producto_descuento")
    @Expose
    private float producto_descuento;

    @SerializedName("disponible")
    @Expose
    private boolean disponible;

    @SerializedName("tipomenu")
    @Expose
    private int tipomenu;



    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdcategoriaproducto() {
        return idcategoriaproducto;
    }

    public void setIdcategoriaproducto(int idcategoriaproducto) {
        this.idcategoriaproducto = idcategoriaproducto;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getProducto_nombre() {
        return producto_nombre;
    }

    public void setProducto_nombre(String producto_nombre) {
        this.producto_nombre = producto_nombre;
    }

    public float getProducto_precio() {
        return producto_precio;
    }

    public void setProducto_precio(float producto_precio) {
        this.producto_precio = producto_precio;
    }

    public int getProducto_stock() {
        return producto_stock;
    }

    public void setProducto_stock(int producto_stock) {
        this.producto_stock = producto_stock;
    }

    public Date getProducto_fechacreacion() {
        return producto_fechacreacion;
    }

    public void setProducto_fechacreacion(Date producto_fechacreacion) {
        this.producto_fechacreacion = producto_fechacreacion;
    }

    public String getProducto_uriimagen() {
        return producto_uriimagen;
    }

    public void setProducto_uriimagen(String producto_uriimagen) {
        this.producto_uriimagen = producto_uriimagen;
    }

    public float getProducto_calificacion() {
        return producto_calificacion;
    }

    public void setProducto_calificacion(float producto_calificacion) {
        this.producto_calificacion = producto_calificacion;
    }

    public String getProducto_detalle() {
        return producto_detalle;
    }

    public void setProducto_detalle(String producto_detalle) {
        this.producto_detalle = producto_detalle;
    }

    public float getProducto_descuento() {
        return producto_descuento;
    }

    public void setProducto_descuento(float producto_descuento) {
        this.producto_descuento = producto_descuento;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getTipomenu() {
        return tipomenu;
    }

    public void setTipomenu(int tipomenu) {
        this.tipomenu = tipomenu;
    }
}
