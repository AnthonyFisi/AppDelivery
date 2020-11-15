package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Repositorio.Cliente_BiRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class Cliente_BiViewModel extends AndroidViewModel {

    private Cliente_BiRepository mCliente_biRepository;

    private LiveData<Cliente_Bi> mCliente_biLiveData;

    public Cliente_BiViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        mCliente_biRepository= new Cliente_BiRepository();
        mCliente_biLiveData=mCliente_biRepository.getCliente_biMutableLiveData();
    }

    public void findInformacionClienteBi(int idusuario){

        String token= SessionPrefs.get(getApplication()).getTokenPrefs();

        mCliente_biRepository.findInformacionClienteBi(token,idusuario);
    }

    public LiveData<Cliente_Bi> getCliente_biLiveData(){
        return mCliente_biLiveData;
    }
}
