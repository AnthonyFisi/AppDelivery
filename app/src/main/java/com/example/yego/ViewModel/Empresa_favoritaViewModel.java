package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.Empresa_favoritaGson;
import com.example.yego.Repository.Repositorio.Empresa_favoritaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Empresa_favoritaViewModel extends AndroidViewModel {

    private Empresa_favoritaRepository mEmpresa_favoritaRepository;

    private MutableLiveData<Empresa_favoritaGson> mEmpresa_favoritaGsonMutableLiveData;

    private String token;

    public Empresa_favoritaViewModel(@NonNull Application application) {
        super(application);

        token= SessionPrefs.get(getApplication()).getTokenPrefs();

        mEmpresa_favoritaRepository=new Empresa_favoritaRepository();

        mEmpresa_favoritaGsonMutableLiveData=mEmpresa_favoritaRepository.getEmpresa_favoritaGsonMutableLiveData();

    }

    public void listaEmpresaFavorita( int idusuario){
        mEmpresa_favoritaRepository.listaEmpresaFavorita(token,idusuario);
    }

    public LiveData<Empresa_favoritaGson> getEmpresa_favoritaLivedata(){
        return mEmpresa_favoritaGsonMutableLiveData;
    }
}
