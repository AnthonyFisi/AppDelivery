package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NombreSubCategoria {

    @SerializedName("idnombresubcategoria")
    @Expose
    private int idnombresubcategoria;

    @SerializedName("productospopulares")
    @Expose
    private String productospopulares;

    @SerializedName("ofertas")
    @Expose
    private String ofertas;

    @SerializedName("categoria1")
    @Expose
    private String categoria1;

    @SerializedName("categoria2")
    @Expose
    private String categoria2;

    @SerializedName("categoria3")
    @Expose
    private String categoria3;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;


    public int getIdnombresubcategoria() {
        return idnombresubcategoria;
    }

    public void setIdnombresubcategoria(int idnombresubcategoria) {
        this.idnombresubcategoria = idnombresubcategoria;
    }

    public String getProductospopulares() {
        return productospopulares;
    }

    public void setProductospopulares(String productospopulares) {
        this.productospopulares = productospopulares;
    }

    public String getOfertas() {
        return ofertas;
    }

    public void setOfertas(String ofertas) {
        this.ofertas = ofertas;
    }

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getCategoria3() {
        return categoria3;
    }

    public void setCategoria3(String categoria3) {
        this.categoria3 = categoria3;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }
}
