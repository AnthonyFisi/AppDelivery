package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Repositorio.UbicacionRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UbicacionViewModel extends AndroidViewModel {

    private UbicacionRepository ubicacionRepository;
    private LiveData<GsonUbicacion> gsonUbicacionLiveData;

    private LiveData<Ubicacion> mUbicacionLiveData;

    private String token;

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
     token= SessionPrefs.get(getApplication()).getTokenPrefs();

        ubicacionRepository=new UbicacionRepository();
        gsonUbicacionLiveData=ubicacionRepository.getUbicacionLiveData();
        mUbicacionLiveData=ubicacionRepository.getUbicacionMutableLivedata();
    }

    public void searchListUbicacion(int idusuario){

        ubicacionRepository.searchListUbicacion(token,idusuario);
    }

    public void searchUbicacion(int idubicacion){

        ubicacionRepository.searchUbicacion(token,idubicacion);
    }

    public void registarUbicacion(Ubicacion ubicacion,int idOldUbicacion){

        ubicacionRepository.registarUbicacion(token,ubicacion,idOldUbicacion);
    }

    public  void updateEstadoUbicacion(int idOldUbicacion, int idNewUbicacion){

        ubicacionRepository.updateEstadoUbicacion(token,idOldUbicacion,idNewUbicacion);
    }

    public void deleteUbicacion(int idUbicacion){

        ubicacionRepository.deleteUbicacion(token,idUbicacion);
    }
    public String searchLocationAddress(String latlong,String key){

        return ubicacionRepository.searchLocationAddress(token,latlong,key);
    }

    public void registrarFirstTimeUbicacio(Ubicacion ubicacion){

        ubicacionRepository.registrarFirstTimeUbicacion(token,ubicacion);
    }

    public LiveData<GsonUbicacion> getUbicacionLiveData() {
        return gsonUbicacionLiveData;
    }


    public LiveData<Ubicacion> getUbicaionLiveDataConfirmation(){
        return mUbicacionLiveData;
    }

}
