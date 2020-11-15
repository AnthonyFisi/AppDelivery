package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.Repository.Repositorio.EmpresaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EmpresaViewModel extends AndroidViewModel {

    private EmpresaRepository  empresaRepository;

    private LiveData<GsonEmpresa>  gsonEmpresaLiveData;

    private LiveData<Empresa> mEmpresaLiveData;

    public EmpresaViewModel(@NonNull Application application) {
        super(application);
    }

    private String token;

    public void init() {
        token= SessionPrefs.get(getApplication()).getTokenPrefs();
        empresaRepository = new EmpresaRepository();
        mEmpresaLiveData=empresaRepository.getEmpresaMutableLivedata();
        gsonEmpresaLiveData = empresaRepository.getListafindByLocationLiveData();
    }

    public void searchListafindByLocation(int idCategoria,String Ubicacion) {

        empresaRepository.searchListafindByLocation(token,idCategoria,Ubicacion);
    }


    public void searchListaCategoriaComplementaria(int idCategoria,String ubicacion) {
        empresaRepository.searchListaCategoriaComplementaria(token,idCategoria,ubicacion);
    }

    public void searchListafindByLocationSubCategoria( int idSubCategoria,String Ubicacion){
        empresaRepository.searchListafindByLocationSubCategoria(token,idSubCategoria,Ubicacion);
    }


    public void searchListaSubcategoriaComplementaria( int idSubCategoria,String ubicacion){
        empresaRepository.searchListaSubcategoriaComplementaria(token,idSubCategoria,ubicacion);
    }

    public void searchListaTotalcerca(String ubicacion){
        empresaRepository.searchListaTotalcerca(token,ubicacion);
    }

    public void listaEmpresaFavorita( int idusuario){
        empresaRepository.listaEmpresaFavorita(token,idusuario);
    }

    public void filtroEmpresa(  int idcategoriaempresa, int distancia, float preciodelivery,String ubicacion){
        empresaRepository.filtroEmpresa(token, idcategoriaempresa, distancia, preciodelivery, ubicacion);
    }


    public void getEmpresaById( int idempresa){
        empresaRepository.getEmpresaById(token,idempresa);
    }

    public LiveData<Empresa> getEmpresaLiveData(){
        return mEmpresaLiveData;
    }

    public LiveData<GsonEmpresa> getListaEmpresanLiveData() {
        return gsonEmpresaLiveData;
    }


}
