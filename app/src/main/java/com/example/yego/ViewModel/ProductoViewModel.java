package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Word;
import com.example.yego.Repository.Repositorio.ProductoRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProductoViewModel extends AndroidViewModel {

    private ProductoRepository productoRepository;
    private LiveData<GsonProducto> gsonProductoLiveData,gsonProductoLiveData2;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
    }

    String token;

    public void init() {
        token = SessionPrefs.get(getApplication()).getTokenPrefs();
        productoRepository = new ProductoRepository();
        gsonProductoLiveData = productoRepository.getListProductoLiveData();

        gsonProductoLiveData2=productoRepository.getListProductoByEmpresaLiveData();
        System.out.println("inicializando los datos VIEW MODEL");
    }


    public void searchProducto(int idcategoriaproducto, int idempresa) {
        System.out.println("estoy en searchproducto VIEW MODEL");

        productoRepository.searchListProducto(token,idcategoriaproducto,idempresa);
    }

    public LiveData<GsonProducto> getProductoLiveData() {
        System.out.println("estoy en getproducto VIEW MODEL");

        return gsonProductoLiveData;
    }


    public void searchProductoByEmpresa( int idempresa) {
        System.out.println("estoy en searchproducto VIEW MODEL");

        productoRepository.searchListProductoByEmpresa(token,idempresa);
    }

    public void searhByWord(int idempresa,String word){
        productoRepository.serachByWord(token,idempresa,word);
    }

    public LiveData<GsonProducto> getProductoByEmpresaLiveData() {
        System.out.println("estoy en getproducto VIEW MODEL");

        return gsonProductoLiveData2;
    }
}
