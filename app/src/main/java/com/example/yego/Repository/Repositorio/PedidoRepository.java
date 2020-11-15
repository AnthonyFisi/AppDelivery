package com.example.yego.Repository.Repositorio;

import android.content.Context;
import android.widget.Toast;

import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Service.PedidoService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class PedidoRepository {


    private PedidoService pedidoService;


    public PedidoRepository(){

        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pedidoService = retrofit.create(PedidoService.class);
    }


    public void insertarPedido(String token,MainPedido mainPedido, Context context){
        pedidoService.insertPedido(token,mainPedido).enqueue(new Callback<MainPedido>() {
            @Override
            public void onResponse(Call<MainPedido> call, Response<MainPedido> response) {

            }

            @Override
            public void onFailure(Call<MainPedido> call, Throwable t) {

            }
        });
    }


}

