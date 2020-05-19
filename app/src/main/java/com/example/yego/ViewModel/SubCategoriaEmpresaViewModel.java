package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Repositorio.SubCategoriaEmpresaRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SubCategoriaEmpresaViewModel extends AndroidViewModel {

    private SubCategoriaEmpresaRepository subCategoriaEmpresaRepository;
    private LiveData<GsonSubCategoriaEmpresa> gsonSubCategoriaEmpresaLiveData;

    public SubCategoriaEmpresaViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        subCategoriaEmpresaRepository = new SubCategoriaEmpresaRepository();
        gsonSubCategoriaEmpresaLiveData = subCategoriaEmpresaRepository.getSubCategoriaEmpresaLiveData();
    }

    public void searchSubCategoriaEmpresa(int idCategoria) {
        subCategoriaEmpresaRepository.searchSubCategoriaEmpresa(idCategoria);
    }

    public LiveData<GsonSubCategoriaEmpresa> getSubCategoiraEmpresaLiveData() {
        return gsonSubCategoriaEmpresaLiveData;
    }
}
