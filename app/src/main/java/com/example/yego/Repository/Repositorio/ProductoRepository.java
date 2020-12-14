package com.example.yego.Repository.Repositorio;

import android.view.View;

import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Word;
import com.example.yego.Repository.Service.ProductoService;
import com.example.yego.Repository.UrlBase;
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

public class ProductoRepository {


    private ProductoService productoService;
    private MutableLiveData<GsonProducto>  gsonProductoMutableLiveData;

    public ProductoRepository(){
        gsonProductoMutableLiveData =new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        productoService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductoService.class);

    }



    public void searchListProducto(String token,int idcategoriaproducto, int idempresa){
        productoService.searchListProducto(token,idcategoriaproducto,idempresa).enqueue(new Callback<GsonProducto>() {
            @Override
            public void onResponse(Call<GsonProducto> call, Response<GsonProducto> response) {


                if(response.code()==400 || response.code()==401 || response.code()==500){
                    gsonProductoMutableLiveData.postValue(null);
                }

                if(response.body() !=null && response.code()==200){
                    gsonProductoMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<GsonProducto> call, Throwable t) {
                System.out.println("ESTOY EN DATOS NULOSSSSSS");

                gsonProductoMutableLiveData.postValue(null);
            }
        });
    }


    public LiveData<GsonProducto> getListProductoLiveData(){

        System.out.println("LIVEDATA REPOSITORY  CUWNTA");

        return gsonProductoMutableLiveData;
    }


    public void searchListProductoByEmpresa(String token,int idempresa){
        productoService.searchListProductoByEmpresa(token,idempresa).enqueue(new Callback<GsonProducto>() {
            @Override
            public void onResponse(Call<GsonProducto> call, Response<GsonProducto> response) {
                if(response.body() !=null){
                    System.out.println("ESTOY EN PRODUCTO REPOSITORY BODY ");

                    gsonProductoMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(Call<GsonProducto> call, Throwable t) {
                gsonProductoMutableLiveData.postValue(null);
            }
        });
    }

    public void serachByWord(String auth,int idempresa,String word){
        productoService.serachByWord(auth,idempresa,word).enqueue(new Callback<GsonProducto>() {
            @Override
            public void onResponse(Call<GsonProducto> call, Response<GsonProducto> response) {


                if(response.body() !=null && response.code()==200){
                    gsonProductoMutableLiveData.postValue(response.body());
                }else{
                    gsonProductoMutableLiveData.postValue(null);

                }

            }

            @Override
            public void onFailure(Call<GsonProducto> call, Throwable t) {
                gsonProductoMutableLiveData.postValue(null);

            }
        });
    }


    public LiveData<GsonProducto> getListProductoByEmpresaLiveData(){
        return gsonProductoMutableLiveData;
    }

}
