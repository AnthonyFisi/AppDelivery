package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Service.OrdenService;
import com.google.gson.Gson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class OrdenRepository {


    private OrdenService ordenService;
    private MutableLiveData<OrdenGeneralGson> gsonOrdenMutableLiveData;

    public OrdenRepository(){
        gsonOrdenMutableLiveData=new MutableLiveData<>();

        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ordenService = retrofit.create(OrdenService.class);
    }

    public void searchListOrdenDisponible(String token,int idUsuario) {
        ordenService.searchListOrdenDisponible(token, idUsuario).enqueue(new Callback<OrdenGeneralGson>() {
            @Override
            public void onResponse(Call<OrdenGeneralGson> call, Response<OrdenGeneralGson> response) {

                if (response.body() != null && response.code()==200) {

                    gsonOrdenMutableLiveData.postValue(response.body());
                }else {
                    gsonOrdenMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<OrdenGeneralGson> call, Throwable t) {
                gsonOrdenMutableLiveData.postValue(null);

            }
        });

    }

    public LiveData<OrdenGeneralGson> getListOrdenLiveData(){
      return gsonOrdenMutableLiveData;
    }

}
