package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.TipoPago;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonTipoPago {

    @SerializedName("listaTipoPago")
    @Expose
    private List<TipoPago> listaTipoPago;

    public List<TipoPago> getListaTipoPago() {
        return listaTipoPago;
    }

    public void setListaTipoPago(List<TipoPago> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }
}
