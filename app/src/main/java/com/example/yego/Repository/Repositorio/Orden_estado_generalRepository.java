package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Service.Orden_estado_generalService;
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

public class Orden_estado_generalRepository {



    private Orden_estado_generalService mOrden_estado_generalService;

    private MutableLiveData<Orden_estado_general> mOrden_estado_generalMutableLiveData;


    public Orden_estado_generalRepository(){
        mOrden_estado_generalMutableLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mOrden_estado_generalService = retrofit.create(Orden_estado_generalService.class);
    }


    public void registrar_estado_general(String token,Orden_estado_general estado){
        mOrden_estado_generalService.registrar_estado_general(token,estado).enqueue(new Callback<Orden_estado_general>() {
            @Override
            public void onResponse(Call<Orden_estado_general> call, Response<Orden_estado_general> response) {

                if(response.code()==500 || response.code()==400 || response.code()==401){
                    mOrden_estado_generalMutableLiveData.postValue(null);
                }

                if (response.body() != null && response.code()==200) {

                    mOrden_estado_generalMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Orden_estado_general> call, Throwable t) {

            }
        });
    }

    public LiveData<Orden_estado_general> getOrden_estado_generalLiveData(){
        return mOrden_estado_generalMutableLiveData;
    }
}
