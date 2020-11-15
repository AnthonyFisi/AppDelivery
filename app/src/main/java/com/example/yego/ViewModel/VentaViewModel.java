package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.Repository.Repositorio.VentaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class VentaViewModel extends AndroidViewModel {
    private VentaRepository ventaRepository;
    private LiveData<Venta> ventaLiveData;

    private String token;
    public VentaViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        token= SessionPrefs.get(getApplication()).getTokenPrefs();;

        ventaRepository= new VentaRepository();
        ventaLiveData=ventaRepository.getVentaLiveData();
    }

    public void registarVenta(Venta venta){
        ventaRepository.registrarVenta(token,venta);
    }

    public void registrarVentaMesa(Venta venta){
        ventaRepository.registrarVentaMesa(token,venta);
    }

    public void registarVentaAlternative(Venta venta){
        ventaRepository.registrarVentaAlternative(token,venta);
    }


    public LiveData<Venta> getVentaLiveData() {
        return ventaLiveData;
    }

}
