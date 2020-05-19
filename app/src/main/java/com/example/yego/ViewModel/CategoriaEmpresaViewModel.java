package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Repositorio.CategoriaEmpresaRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CategoriaEmpresaViewModel extends AndroidViewModel {

    private CategoriaEmpresaRepository categoriaEmpresaRepository;
    private LiveData<GsonCategoriaEmpresa> gsonCategoriaEMpresaLiveData;

    public CategoriaEmpresaViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        categoriaEmpresaRepository = new CategoriaEmpresaRepository();
       gsonCategoriaEMpresaLiveData = categoriaEmpresaRepository.getCategoriaEmpresaLiveData();
    }

    public void searchCategoriaEmpresa(ShimmerFrameLayout mShimmerViewContainer) {
        categoriaEmpresaRepository.searchCategoriaEmpresa(mShimmerViewContainer);
    }

    public LiveData<GsonCategoriaEmpresa> getCategoiraEmpresaLiveData() {
        return gsonCategoriaEMpresaLiveData;
    }
}
