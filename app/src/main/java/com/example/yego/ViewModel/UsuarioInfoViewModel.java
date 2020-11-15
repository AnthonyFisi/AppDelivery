package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.UsuarioInfo;
import com.example.yego.Repository.Repositorio.UsuarioInfoRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UsuarioInfoViewModel extends AndroidViewModel {

    private UsuarioInfoRepository mUsuarioInfoRepository;

    private LiveData<JwtResponse> mJwtResponseLiveData;


    public UsuarioInfoViewModel(@NonNull Application application) {
        super(application);
    }


    public void init(){
        mUsuarioInfoRepository=new UsuarioInfoRepository();
        mJwtResponseLiveData=mUsuarioInfoRepository.getJwtResponse();
    }

    public void registrarUsuarioProvider(UsuarioInfo usuarioInfo){

        mUsuarioInfoRepository.registrarUsuarioProvider(usuarioInfo);
    }

    public LiveData<JwtResponse> getJwtResponseLiveData(){
        return mJwtResponseLiveData;
    }
}
