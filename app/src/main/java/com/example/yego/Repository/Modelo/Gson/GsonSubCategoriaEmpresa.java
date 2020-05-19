package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonSubCategoriaEmpresa {

    @SerializedName("listaSubCategoriaEmpresa")
    @Expose
    private List<SubCategoriaEmpresa> listaSubcategoriaEmpresa=null;

    public List<SubCategoriaEmpresa> getListaSubcategoriaEmpresa() {
        return listaSubcategoriaEmpresa;
    }

    public void setListaSubcategoriaEmpresa(List<SubCategoriaEmpresa> listaSubcategoriaEmpresa) {
        this.listaSubcategoriaEmpresa = listaSubcategoriaEmpresa;
    }
}
