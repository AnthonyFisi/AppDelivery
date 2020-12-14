package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonPublicidad;
import com.example.yego.Repository.Service.PublicidadService;
import com.example.yego.Repository.UrlBase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class PublicidadRepository {


    private PublicidadService publicidadService;
    private MutableLiveData<GsonPublicidad> gsonPublicidadMutableLiveData;

    public PublicidadRepository(){
        gsonPublicidadMutableLiveData =new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        publicidadService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PublicidadService.class);

    }

    public void searchListaPublicidad(String auth){
        publicidadService.searchListaPublicidad(auth).enqueue(new Callback<GsonPublicidad>() {
            @Override
            public void onResponse(Call<GsonPublicidad> call, Response<GsonPublicidad> response) {
                if(response.body() !=null){
                    gsonPublicidadMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonPublicidad> call, Throwable t) {
                gsonPublicidadMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<GsonPublicidad>  getListaPublicidadLiveData(){
        return gsonPublicidadMutableLiveData;
    }


}
