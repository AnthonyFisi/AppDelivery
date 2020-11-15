package com.example.yego.Repository.Repositorio;


import com.example.yego.R;
import com.example.yego.Repository.Modelo.Calificacion_Servicio;
import com.example.yego.Repository.Service.Calificacion_ServicioService;

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

public class Calificacion_ServicioRepository {


    private Calificacion_ServicioService mCalificacion_servicioService;

    private MutableLiveData<Calificacion_Servicio> mCalificacion_servicioMutableLiveData;

    public Calificacion_ServicioRepository(){
       mCalificacion_servicioMutableLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCalificacion_servicioService= retrofit.create(Calificacion_ServicioService.class);
    }

    public void agregarCalificacion(String token,Calificacion_Servicio calificacion){
        mCalificacion_servicioService.agregarCalificacion(token,calificacion).enqueue(new Callback<Calificacion_Servicio>() {
            @Override
            public void onResponse(Call<Calificacion_Servicio> call, Response<Calificacion_Servicio> response) {

                if(response.code()==200 && response.body()!=null){
                    mCalificacion_servicioMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Calificacion_Servicio> call, Throwable t) {

                mCalificacion_servicioMutableLiveData.postValue(null);

            }
        });
    }

    public LiveData<Calificacion_Servicio> getCalificacion_ServicioDataLiveData(){
        return  mCalificacion_servicioMutableLiveData;
    }
}
