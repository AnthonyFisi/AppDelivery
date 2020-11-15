package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.NombreSubCategoria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonNombreSubCategoria {

    @SerializedName("listaNombreSubCategoria")
    @Expose
    private List<NombreSubCategoria> listaNombreSubCategoria;

    public List<NombreSubCategoria> getListaNombreSubCategoria() {
        return listaNombreSubCategoria;
    }

    public void setListaNombreSubCategoria(List<NombreSubCategoria> listaNombreSubCategoria) {
        this.listaNombreSubCategoria = listaNombreSubCategoria;
    }
}
