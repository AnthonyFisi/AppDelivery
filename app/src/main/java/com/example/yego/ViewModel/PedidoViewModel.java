package com.example.yego.ViewModel;

import android.app.Application;
import android.content.Context;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Repositorio.PedidoRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class PedidoViewModel extends AndroidViewModel {

    PedidoRepository pedidoRepository;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
    }

    String token;

    public void init(){
        token = SessionPrefs.get(getApplication()).getTokenPrefs();

        pedidoRepository = new PedidoRepository();
    }

    public void insertarPedido(MainPedido mainPedido, Context context){
        pedidoRepository.insertarPedido(token,mainPedido,context);
    }
}
