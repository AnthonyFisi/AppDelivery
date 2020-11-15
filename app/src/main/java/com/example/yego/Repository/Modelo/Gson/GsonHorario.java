package com.example.yego.Repository.Modelo.Gson;

import com.example.yego.Repository.Modelo.Horario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonHorario {

    @SerializedName("listaHorario")
    @Expose
    List<Horario> listaHorario;

    public List<Horario> getListaHorario() {
        return listaHorario;
    }

    public void setListaHorario(List<Horario> listaHorario) {
        this.listaHorario = listaHorario;
    }
}
