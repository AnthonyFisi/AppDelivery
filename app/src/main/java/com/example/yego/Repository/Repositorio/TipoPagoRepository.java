package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonTipoPago;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Service.TipoPagoService;
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

public class TipoPagoRepository {

    private TipoPagoService tipoPagoService;

    private MutableLiveData<GsonTipoPago> gsonTipoPagoMutableLiveData;

    public TipoPagoRepository(){
        gsonTipoPagoMutableLiveData= new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

       tipoPagoService = new retrofit2.Retrofit.Builder()
               .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TipoPagoService.class);

    }

    public void searchListTipoPagoEnable(String token){
        tipoPagoService.searchListTipoPagoEnable(token).enqueue(new Callback<GsonTipoPago>() {
            @Override
            public void onResponse(Call<GsonTipoPago> call, Response<GsonTipoPago> response) {
                if(response.body() !=null && response.code()==200){
                    gsonTipoPagoMutableLiveData.postValue(response.body());

                    System.out.println("ENTRAMOS A REPOSITORY RECIVE DATA");
                }
            }

            @Override
            public void onFailure(Call<GsonTipoPago> call, Throwable t) {
                gsonTipoPagoMutableLiveData.postValue(null);
                System.out.println("ENTRAMOS A REPOSITORY NULL DATA");

            }
        });
    }

    public LiveData<GsonTipoPago> getGsonTipoPagoLiveData(){
        System.out.println("NO RETORNAMOS DATA ");

        return gsonTipoPagoMutableLiveData;


    }

}
