package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Repositorio.ProductoJOINregistroPedidoJOINpedidoRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProductoJOINregistroPedidoJOINpedidoViewModel extends AndroidViewModel {

    private ProductoJOINregistroPedidoJOINpedidoRepository productoJOINregistroPedidoJOINpedidoRepository;
    private LiveData<GsonProductoJOINregistroPedidoJOINpedido> gsonProductoJOINregistroPedidoJOINpedidoLiveData;

    private String token;
    public ProductoJOINregistroPedidoJOINpedidoViewModel(@NonNull Application application) {
        super(application);
    }


    public void init() {
        token= SessionPrefs.get(getApplication()).getTokenPrefs();
        productoJOINregistroPedidoJOINpedidoRepository = new ProductoJOINregistroPedidoJOINpedidoRepository();
        gsonProductoJOINregistroPedidoJOINpedidoLiveData = productoJOINregistroPedidoJOINpedidoRepository.getProductoJOINregistroPedidoJOINpedidoLiveData();
        System.out.println("inicializando los datos VIEW MODEL");
    }


    public void searchProducto(int idusuario, ShimmerFrameLayout mShimmerViewContainer) {
        System.out.println("estoy en searchproducto VIEW MODEL");

       productoJOINregistroPedidoJOINpedidoRepository.listaCarrito(token,idusuario,mShimmerViewContainer);
    }

    public void searchCarritoByEmpresa(int idusuario,int idempresa){
        productoJOINregistroPedidoJOINpedidoRepository.listaCarritoByEmpresa(token,idusuario,idempresa);
    }


    public void listaCarritoByUsuario(int idusuario){
        productoJOINregistroPedidoJOINpedidoRepository.listaCarritoByUsuario(token,idusuario);
    }


    public LiveData<GsonProductoJOINregistroPedidoJOINpedido> getProductoJOINregistroPedidoJOINpedidoLiveData() {
        System.out.println("estoy en getproducto VIEW MODEL");

        return gsonProductoJOINregistroPedidoJOINpedidoLiveData;
    }
}
