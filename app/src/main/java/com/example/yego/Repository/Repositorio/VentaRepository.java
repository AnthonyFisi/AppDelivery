package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.Repository.Service.VentaService;

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

public class VentaRepository {


    private VentaService ventaService;

    private MutableLiveData<Venta> ventaMutableLiveData;

    public VentaRepository(){
        ventaMutableLiveData=new MutableLiveData<>();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ventaService = retrofit.create(VentaService.class);

    }

    public void registrarVenta(String token,Venta venta){
        ventaService.registarVenta(token,venta).enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if(response.body()!=null && response.code()==200){

                    ventaMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {

                ventaMutableLiveData.postValue(null);

            }
        });
    }


    public void registrarVentaMesa(String token,Venta venta){
        ventaService.registarVentaMesa(token,venta).enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if(response.body()!=null && response.code()==200){

                    ventaMutableLiveData.postValue(response.body());

                }else {
                    ventaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                ventaMutableLiveData.postValue(null);

            }
        });
    }

    public void registrarVentaAlternative(String token,Venta venta){
        ventaService.registarVentaAlternative(token,venta).enqueue(new Callback<Venta>() {
            @Override
            public void onResponse(Call<Venta> call, Response<Venta> response) {
                if(response.body()!=null && response.code()==200){
                    ventaMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Venta> call, Throwable t) {
                ventaMutableLiveData.postValue(null);

            }
        });
    }

    public LiveData<Venta> getVentaLiveData(){

        return ventaMutableLiveData;
    }

}
