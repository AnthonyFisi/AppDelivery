package com.example.yego.Repository.Repositorio;

import android.view.View;

import com.example.yego.Repository.Modelo.Gson.GsonHorario;
import com.example.yego.Repository.Service.HorarioService;
import com.example.yego.Repository.UrlBase;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class HorarioRepository {


    private HorarioService horarioService;
    private MutableLiveData<GsonHorario> gsonHorarioMutableLiveData;


    public  HorarioRepository(){
        gsonHorarioMutableLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        horarioService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HorarioService.class);
    }


    public void searchListHorario( String token,ShimmerFrameLayout mShimmerViewContainer) {
        horarioService.searchListHorario(token).enqueue(new Callback<GsonHorario>() {
            @Override
            public void onResponse(Call<GsonHorario> call, Response<GsonHorario> response) {
                if (response.body() != null) {
                    gsonHorarioMutableLiveData.setValue(response.body());
                }

                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GsonHorario> call, Throwable t) {

            }
        });
    }

    public LiveData<GsonHorario> getListHorarioLiveData(){
        return gsonHorarioMutableLiveData;
    }
}
