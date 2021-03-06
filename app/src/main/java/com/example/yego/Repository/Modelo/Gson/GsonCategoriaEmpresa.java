package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GsonCategoriaEmpresa implements Serializable {



    @SerializedName("listaCategoriaEmpresa")
    @Expose
    private List<CategoriaEmpresa> listaCategoriaEmpresa;

    public List<CategoriaEmpresa> getListaCategoriaEmpresa() {
        return listaCategoriaEmpresa;
    }

    public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa) {
        this.listaCategoriaEmpresa = listaCategoriaEmpresa;
    }
}
