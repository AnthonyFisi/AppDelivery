package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonNombreSubCategoria;
import com.example.yego.Repository.Repositorio.NombreSubCategoriaRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NombreSubCategoriaViewModel extends AndroidViewModel {

    private NombreSubCategoriaRepository nombreSubCategoriaRepository;
    private LiveData<GsonNombreSubCategoria> gsonNombreSubCategoriaLiveData;


    private String token;

    public NombreSubCategoriaViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        token = SessionPrefs.get(getApplication()).getTokenPrefs();
       nombreSubCategoriaRepository = new NombreSubCategoriaRepository();
        gsonNombreSubCategoriaLiveData = nombreSubCategoriaRepository.getListNombreSubCategoriaLiveData();
    }


    public void searchNombreSubCategoria(int idempresa) {
        nombreSubCategoriaRepository.searchListNombreSubCategoria(token,idempresa);
    }

    public LiveData<GsonNombreSubCategoria> getNombreSubCategoriaLiveData() {
        return gsonNombreSubCategoriaLiveData;
    }
}
