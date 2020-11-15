package com.example.yego.Repository.Repositorio;

import android.view.View;

import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Service.ProductoJOINregistroPedidoJOINpedidoService;
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

public class ProductoJOINregistroPedidoJOINpedidoRepository {



    private ProductoJOINregistroPedidoJOINpedidoService productoJOINregistroPedidoJOINpedidoService;
    private MutableLiveData<GsonProductoJOINregistroPedidoJOINpedido> gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData;

    public ProductoJOINregistroPedidoJOINpedidoRepository(){
        gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData =new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        productoJOINregistroPedidoJOINpedidoService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductoJOINregistroPedidoJOINpedidoService.class);

    }



    public void listaCarrito(String token,int idusuario, ShimmerFrameLayout mShimmerViewContainer){
        productoJOINregistroPedidoJOINpedidoService.listaCarrito(token,idusuario).enqueue(new Callback<GsonProductoJOINregistroPedidoJOINpedido>() {
            @Override
            public void onResponse(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Response<GsonProductoJOINregistroPedidoJOINpedido> response) {
                if(response.body() !=null){

                    gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData.setValue(response.body());
                }
               // mShimmerViewContainer.stopShimmerAnimation();
               // mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Throwable t) {

            }
        });
    }

    public void listaCarritoByEmpresa(String token,int idusuario,int idempresa){
        productoJOINregistroPedidoJOINpedidoService.listaCarritoByEmpresa(token,idusuario,idempresa).enqueue(new Callback<GsonProductoJOINregistroPedidoJOINpedido>() {
            @Override
            public void onResponse(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Response<GsonProductoJOINregistroPedidoJOINpedido> response) {

                if(response.body() !=null){

                    gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Throwable t) {

            }
        });
    }


    public void listaCarritoByUsuario(String token,int idusuario){
        productoJOINregistroPedidoJOINpedidoService.listaCarritoByUsuario(token,idusuario).enqueue(new Callback<GsonProductoJOINregistroPedidoJOINpedido>() {
            @Override
            public void onResponse(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Response<GsonProductoJOINregistroPedidoJOINpedido> response) {
                if(response.body()!=null){
                    gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonProductoJOINregistroPedidoJOINpedido> call, Throwable t) {

            }
        });
    }


    public LiveData<GsonProductoJOINregistroPedidoJOINpedido> getProductoJOINregistroPedidoJOINpedidoLiveData(){


        return gsonProductoJOINregistroPedidoJOINpedidoMutableLiveData;
    }

}
