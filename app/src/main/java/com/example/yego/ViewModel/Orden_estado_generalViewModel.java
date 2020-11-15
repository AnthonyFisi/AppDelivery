package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Repositorio.Orden_estado_generalRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Orden_estado_generalViewModel extends AndroidViewModel {

    private Orden_estado_generalRepository mOrden_estado_generalRepository;

    private LiveData<Orden_estado_general> mOrden_estado_generalLiveData;

    String token;

    public Orden_estado_generalViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){

        token= SessionPrefs.get(getApplication()).getTokenPrefs();

        mOrden_estado_generalRepository= new Orden_estado_generalRepository();

        mOrden_estado_generalLiveData=mOrden_estado_generalRepository.getOrden_estado_generalLiveData();

    }


    public void registrar_estado_general(Orden_estado_general estado){
        mOrden_estado_generalRepository.registrar_estado_general(token,estado);
    }

    public LiveData<Orden_estado_general> getOrden_estado_generalLiveData(){
        return mOrden_estado_generalLiveData;
    }
}
