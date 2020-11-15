package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Horario implements Serializable {

    @SerializedName("idhorario")
    @Expose
    private int idhorario;

    @SerializedName("horario_nombre")
    @Expose
    private String horario_nombre;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("horario_inicio")
    @Expose
    private int horario_inicio;

    @SerializedName("horario_fin")
    @Expose
    private int horario_fin;


    public int getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(int idhorario) {
        this.idhorario = idhorario;
    }

    public String getHorario_nombre() {
        return horario_nombre;
    }

    public void setHorario_nombre(String horario_nombre) {
        this.horario_nombre = horario_nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(int horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public int getHorario_fin() {
        return horario_fin;
    }

    public void setHorario_fin(int horario_fin) {
        this.horario_fin = horario_fin;
    }


    public static List<Horario> mHorarioList= new ArrayList<>();

    public  static  void getLista(){

        //LLENAR LA LISTA DE HORARIOS QUE VAN A ESTAR SIEMPRE VISIBLES PARA EL USUARIO

        for(int i=1;i<25;i++){
            Horario horario=new Horario();

            horario.setIdhorario(i);//  id : 1 , 2 , 3 , 4 , 5
            horario.setHorario_inicio(i-1);// inicio: 1  , 2  ........  23
            horario.setHorario_fin(i);// fin : (1+1)=2  , (2+1)=3 ...... (23+1)=24

            mHorarioList.add(horario);

            
        }


    }
}
