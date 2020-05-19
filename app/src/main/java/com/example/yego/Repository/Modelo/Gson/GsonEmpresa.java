package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Empresa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonEmpresa {

    @SerializedName("listaEmpresa")
    @Expose
    private List<Empresa> listaEmpresa;

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }
}
