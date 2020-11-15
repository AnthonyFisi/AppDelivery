package com.example.yego.ViewModel;

import android.app.Application;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonHorario;
import com.example.yego.Repository.Repositorio.HorarioRepository;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HorarioViewModel extends AndroidViewModel {

    private HorarioRepository horarioRepository;
    private LiveData<GsonHorario> gsonHorarioLiveData;

    private String token;
    public HorarioViewModel(@NonNull Application application) {
        super(application);
    }



    public void init() {
        token= SessionPrefs.get(getApplication()).getTokenPrefs();;
        horarioRepository = new HorarioRepository();
        gsonHorarioLiveData = horarioRepository.getListHorarioLiveData();
    }


    public void searchListahorario( ShimmerFrameLayout mShimmerViewContainer) {
        horarioRepository.searchListHorario(token,mShimmerViewContainer);
    }

    public LiveData<GsonHorario> getHorarioLiveData() {
        return gsonHorarioLiveData;
    }
}
