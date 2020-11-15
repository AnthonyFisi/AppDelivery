package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Repositorio.SubCategoriaEmpresaRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SubCategoriaEmpresaViewModel extends AndroidViewModel {

    private SubCategoriaEmpresaRepository subCategoriaEmpresaRepository;
    private LiveData<GsonSubCategoriaEmpresa> gsonSubCategoriaEmpresaLiveData;

    public SubCategoriaEmpresaViewModel(@NonNull Application application) {
        super(application);
    }
    private String token;

    public void init() {
        token= SessionPrefs.get(getApplication()).getTokenPrefs();

        subCategoriaEmpresaRepository = new SubCategoriaEmpresaRepository();
        gsonSubCategoriaEmpresaLiveData = subCategoriaEmpresaRepository.getSubCategoriaEmpresaLiveData();
    }

    public void searchSubCategoriaEmpresa(int idCategoria) {
        subCategoriaEmpresaRepository.searchSubCategoriaEmpresa(token,idCategoria);
    }

    public LiveData<GsonSubCategoriaEmpresa> getSubCategoiraEmpresaLiveData() {
        return gsonSubCategoriaEmpresaLiveData;
    }
}
