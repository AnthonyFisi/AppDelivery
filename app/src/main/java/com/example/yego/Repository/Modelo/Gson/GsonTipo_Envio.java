package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonTipo_Envio {

    @SerializedName("listaTipo_Envio")
    @Expose
    private List<Tipo_Envio> listaTipo_Envio;

    public List<Tipo_Envio> getListaTipo_Envio() {
        return listaTipo_Envio;
    }

    public void setListaTipo_Envio(List<Tipo_Envio> listaTipo_Envio) {
        this.listaTipo_Envio = listaTipo_Envio;
    }

}
