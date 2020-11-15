package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonTipo_Envio;
import com.example.yego.Repository.Repositorio.Tipo_EnvioRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Tipo_EnvioViewModel extends AndroidViewModel {

    private Tipo_EnvioRepository tipo_EnvioRepository;
    private LiveData<GsonTipo_Envio> gsonTipo_EnvioLiveData;
    private String token;

    public Tipo_EnvioViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        token= SessionPrefs.get(getApplication()).getTokenPrefs();
        tipo_EnvioRepository= new Tipo_EnvioRepository();
        gsonTipo_EnvioLiveData=tipo_EnvioRepository.getTipo_EnvioLiveData();
    }

    public void searchListTipo_Envio(){
        tipo_EnvioRepository.searchListTipo_Envio(token);
    }

    public LiveData<GsonTipo_Envio> getTipo_EnvioaLiveData() {
        return gsonTipo_EnvioLiveData;
    }
}
