package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonTipo_Envio;
import com.example.yego.Repository.Service.Tipo_EnvioService;
import com.example.yego.Repository.UrlBase;

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

public class Tipo_EnvioRepository {


    private Tipo_EnvioService tipo_EnvioService;

    private MutableLiveData<GsonTipo_Envio> gsonTipo_EnvioMutableLiveData;

    public Tipo_EnvioRepository(){
        gsonTipo_EnvioMutableLiveData=new MutableLiveData<>();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       tipo_EnvioService = retrofit.create(Tipo_EnvioService.class);
    }

    public void searchListTipo_Envio(String token){
        tipo_EnvioService.searchListTipo_Envio(token).enqueue(new Callback<GsonTipo_Envio>() {
            @Override
            public void onResponse(Call<GsonTipo_Envio> call, Response<GsonTipo_Envio> response) {
                if(response!=null && response.code()==200){
                    gsonTipo_EnvioMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonTipo_Envio> call, Throwable t) {
                gsonTipo_EnvioMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<GsonTipo_Envio> getTipo_EnvioLiveData(){
        return gsonTipo_EnvioMutableLiveData;
    }

}
