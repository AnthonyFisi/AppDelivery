package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Calificacion_Servicio;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Repositorio.Calificacion_ServicioRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Calificacion_ServicioViewModel extends AndroidViewModel {

    private Calificacion_ServicioRepository mCalificacion_servicioRepository;

    private LiveData<Calificacion_Servicio> mCalificacion_servicioLiveData;

    public Calificacion_ServicioViewModel(@NonNull Application application) {
        super(application);

    }

    public void init (){
        mCalificacion_servicioRepository= new Calificacion_ServicioRepository();
        mCalificacion_servicioLiveData= mCalificacion_servicioRepository.getCalificacion_ServicioDataLiveData();
    }

    public void agregarCalificacion(Calificacion_Servicio calificacion){

        String token= SessionPrefs.get(getApplication()).getTokenPrefs();
        mCalificacion_servicioRepository.agregarCalificacion(token,calificacion);
    }

    public LiveData<Calificacion_Servicio> getCalificacion_servicioLiveData(){

        return mCalificacion_servicioLiveData;
    }




}
