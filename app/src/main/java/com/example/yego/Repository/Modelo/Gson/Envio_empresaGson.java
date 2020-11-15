package com.example.yego.Repository.Modelo.Gson;


import com.example.yego.Repository.Modelo.Envio_empresa;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Envio_empresaGson {

    @SerializedName("listaEnvioEmpresa")
    @Expose
    List<Envio_empresa> listaEnvioEmpresa;

    public List<Envio_empresa> getListaEnvioEmpresa() {
        return listaEnvioEmpresa;
    }

    public void setListaEnvioEmpresa(List<Envio_empresa> listaEnvioEmpresa) {
        this.listaEnvioEmpresa = listaEnvioEmpresa;
    }



}
