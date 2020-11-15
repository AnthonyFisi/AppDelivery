package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Repositorio.OrdenRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OrdenViewModel extends AndroidViewModel {

    private OrdenRepository ordenRepository;
    private LiveData<OrdenGeneralGson>  gsonOrdenLiveData;

    String token;
    public OrdenViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        token= SessionPrefs.get(getApplication()).getTokenPrefs();
        ordenRepository= new OrdenRepository();
        gsonOrdenLiveData=ordenRepository.getListOrdenLiveData();
    }

    public void searchListOrdenDisponible(int idUsuario){
        ordenRepository.searchListOrdenDisponible(token,idUsuario);
    }

    public  LiveData<OrdenGeneralGson> getOrdenDisponibleLiveData(){
        return  gsonOrdenLiveData;
    }
}
