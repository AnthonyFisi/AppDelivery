package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Empresa_favorita;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Empresa_favoritaGson {

    @SerializedName("listaEmpresaFavorita")
    @Expose
    private List<Empresa_favorita> listaEmpresaFavorita;

    public List<Empresa_favorita> getListaEmpresaFavorita() {
        return listaEmpresaFavorita;
    }

    public void setListaEmpresaFavorita(List<Empresa_favorita> listaEmpresaFavorita) {
        this.listaEmpresaFavorita = listaEmpresaFavorita;
    }



}
