package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Empresa_favorito_usuario;
import com.example.yego.Repository.Repositorio.Empresa_favorito_usuarioRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Empresa_favorito_usuarioViewModel extends AndroidViewModel {

    private Empresa_favorito_usuarioRepository mEmpresa_favorito_usuarioRepository;

    private LiveData<Empresa_favorito_usuario> mEmpresa_favorito_usuarioLiveData;

    private String token;

    public Empresa_favorito_usuarioViewModel(@NonNull Application application) {
        super(application);

        token= SessionPrefs.get(getApplication()).getTokenPrefs();

        mEmpresa_favorito_usuarioRepository= new Empresa_favorito_usuarioRepository();

        mEmpresa_favorito_usuarioLiveData=mEmpresa_favorito_usuarioRepository.getEmpresa_favorito_usuarioMutableLiveData();
    }


    public void agregarFavorito(Empresa_favorito_usuario favorito){
        mEmpresa_favorito_usuarioRepository.agregarFavorito(token,favorito);
    }

    public void eliminarFavorito(int idempresa,int idusuario){
        mEmpresa_favorito_usuarioRepository.eliminarFavorito(token,idempresa,idusuario);
    }


    public LiveData<Empresa_favorito_usuario> getEmpresa_favorito_usuarioLiveData(){
        return mEmpresa_favorito_usuarioLiveData;
    }
}
