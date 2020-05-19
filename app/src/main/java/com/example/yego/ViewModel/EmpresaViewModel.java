package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.Repository.Repositorio.EmpresaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EmpresaViewModel extends AndroidViewModel {

    private EmpresaRepository  empresaRepository;
    private LiveData<GsonEmpresa>  gsonEmpresaLiveData;

    public EmpresaViewModel(@NonNull Application application) {
        super(application);
    }


    public void init() {
        empresaRepository = new EmpresaRepository();
        gsonEmpresaLiveData = empresaRepository.getListafindByLocationLiveData();
    }

    public void searchListafindByLocation(int idCategoria,String Ubicacion) {
        empresaRepository.searchListafindByLocation(idCategoria,Ubicacion);
    }

    public LiveData<GsonEmpresa> getListafindByLocationLiveData() {
        return gsonEmpresaLiveData;
    }




    public void init1() {
        empresaRepository = new EmpresaRepository();
        gsonEmpresaLiveData = empresaRepository.getListaSortfindByLiveData();
    }

    public void searchListaSortfindBy(int idCategoria) {
        empresaRepository.searchListaSortfindBy(idCategoria);
    }

    public LiveData<GsonEmpresa> getListaSortfindByLiveData() {
        return gsonEmpresaLiveData;
    }



    public void init2() {
        empresaRepository = new EmpresaRepository();
        gsonEmpresaLiveData = empresaRepository.getListafindByLocationSubCategoriaLiveData();
    }

    public void searchListafindByLocationSubCategoria( int idSubCategoria,String Ubicacion){
        empresaRepository.searchListafindByLocationSubCategoria(idSubCategoria,Ubicacion);
    }

    public LiveData<GsonEmpresa> getListafindByLocationSubCategoriaLiveData() {
        return gsonEmpresaLiveData;
    }



    public void init3() {
        empresaRepository = new EmpresaRepository();
        gsonEmpresaLiveData = empresaRepository.getListafindByLiveData();
    }

    public void searchListafindBy( int idSubCategoria){
        empresaRepository.searchListafindBy(idSubCategoria);
    }

    public LiveData<GsonEmpresa> getsearchListafindByLiveData() {
        return gsonEmpresaLiveData;
    }
}
