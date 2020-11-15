package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonTipoPago;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Repositorio.TipoPagoRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TipoPagoViewModel  extends AndroidViewModel {

    private TipoPagoRepository tipoPagoRepository;

    private LiveData<GsonTipoPago> gsonTipoPagoLiveData;

    public TipoPagoViewModel(@NonNull Application application) {
        super(application);
    }

    private String token;

    public void init (){
        token= SessionPrefs.get(getApplication()).getTokenPrefs();;
        tipoPagoRepository= new TipoPagoRepository();
        gsonTipoPagoLiveData=tipoPagoRepository.getGsonTipoPagoLiveData();
    }


    public void  searchListTipoPagoEnable(){
        tipoPagoRepository.searchListTipoPagoEnable(token);
    }

    public LiveData<GsonTipoPago> getGsonTipoPagoLiveData(){
        return gsonTipoPagoLiveData;
    }


}
