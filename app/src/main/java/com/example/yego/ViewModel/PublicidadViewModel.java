package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonPublicidad;
import com.example.yego.Repository.Repositorio.PublicidadRepository;
import com.example.yego.Repository.Service.PublicidadService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PublicidadViewModel extends AndroidViewModel {

    private PublicidadRepository publicidadRepository;
    private LiveData<GsonPublicidad> gsonPublicidadLiveData;

    public PublicidadViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        publicidadRepository = new PublicidadRepository();
        gsonPublicidadLiveData = publicidadRepository.getListaPublicidadLiveData();
    }

    public void searchListaPublicidad() {
        String token= SessionPrefs.get(getApplication()).getTokenPrefs();

        publicidadRepository.searchListaPublicidad(token);
    }

    public LiveData<GsonPublicidad> getListaPublicidadLiveData() {
        return gsonPublicidadLiveData;
    }
}
