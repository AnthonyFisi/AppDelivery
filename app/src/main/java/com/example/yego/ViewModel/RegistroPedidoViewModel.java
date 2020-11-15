package com.example.yego.ViewModel;

import android.app.Application;
import android.content.Context;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Repositorio.RegistroPedidoRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class RegistroPedidoViewModel extends AndroidViewModel {

    private RegistroPedidoRepository registroPedidoRepository;

    String token;

    public RegistroPedidoViewModel(@NonNull Application application) {
        super(application);
    }

    public void init ()
    {
        token = SessionPrefs.get(getApplication()).getTokenPrefs();

        registroPedidoRepository= new RegistroPedidoRepository();
    }

    public void incrementarProducto(MainPedido mainPedido, Context context){
        registroPedidoRepository.incrementarProducto(token,mainPedido,context);
    }

    public void disminuirProducto(MainPedido mainPedido, Context context){
        registroPedidoRepository.disminuirProducto(token,mainPedido,context);

    }

    public void eliminarCarrito(Context context,int idempresa,int idUsuario){
        registroPedidoRepository.eliminarCarrito(token,idempresa,idUsuario,context);
    }
}
