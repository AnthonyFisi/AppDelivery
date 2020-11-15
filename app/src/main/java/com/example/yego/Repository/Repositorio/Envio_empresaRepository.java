package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Envio_empresa;
import com.example.yego.Repository.Modelo.Gson.Envio_empresaGson;
import com.example.yego.Repository.Service.Envio_empresaService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class Envio_empresaRepository {


    private Envio_empresaService mEnvio_empresaService;

    private MutableLiveData<Envio_empresaGson> mEnvio_empresaGsonMutableLiveData;

    public Envio_empresaRepository(){
       mEnvio_empresaGsonMutableLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mEnvio_empresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Envio_empresaService.class);
    }

    public void listaFindByIdempresa(String token,int idempresa){
        mEnvio_empresaService.listaFindByIdempresa(token, idempresa).enqueue(new Callback<Envio_empresaGson>() {
            @Override
            public void onResponse(Call<Envio_empresaGson> call, Response<Envio_empresaGson> response) {
                if (response.body() != null) {
                    mEnvio_empresaGsonMutableLiveData.postValue(response.body());
                }else{
                    mEnvio_empresaGsonMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Envio_empresaGson> call, Throwable t) {
                mEnvio_empresaGsonMutableLiveData.postValue(null);

            }
        });
    }

    public MutableLiveData<Envio_empresaGson> getEnvio_empresaGsonMutableLiveData(){
        return mEnvio_empresaGsonMutableLiveData;
    }
}
