package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.Envio_empresaGson;
import com.example.yego.Repository.Repositorio.Envio_empresaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Envio_empresaViewModel extends AndroidViewModel {

    private Envio_empresaRepository mEnvio_empresaRepository;

    private LiveData<Envio_empresaGson> mEnvio_empresaGsonLiveData;

    private String token;

    public Envio_empresaViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        token= SessionPrefs.get(getApplication()).getTokenPrefs();
        mEnvio_empresaRepository= new Envio_empresaRepository();
        mEnvio_empresaGsonLiveData=mEnvio_empresaRepository.getEnvio_empresaGsonMutableLiveData();
    }

    public void listaFindByIdempresa(int idempresa){
        mEnvio_empresaRepository.listaFindByIdempresa(token,idempresa);
    }

    public LiveData<Envio_empresaGson> getEnvio_empresaGsonLiveData(){
        return mEnvio_empresaGsonLiveData;
    }

    }
