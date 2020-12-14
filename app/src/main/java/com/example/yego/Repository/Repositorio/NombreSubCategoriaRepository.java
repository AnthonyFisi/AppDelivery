package com.example.yego.Repository.Repositorio;

import android.view.View;

import com.example.yego.Repository.Modelo.Gson.GsonNombreSubCategoria;
import com.example.yego.Repository.Modelo.NombreSubCategoria;
import com.example.yego.Repository.Service.NombreSubCategoriaService;
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

public class NombreSubCategoriaRepository {


    private NombreSubCategoriaService nombreSubCategoriaService;
    private MutableLiveData<GsonNombreSubCategoria> gsonNombreSubCategoriaMutableLiveData;


    public  NombreSubCategoriaRepository(){
        gsonNombreSubCategoriaMutableLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        nombreSubCategoriaService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NombreSubCategoriaService.class);
    }


    public void searchListNombreSubCategoria(String token,int idempresa) {
        nombreSubCategoriaService.searchListNombreSubCategoria(token,idempresa)
                .enqueue(new Callback<GsonNombreSubCategoria>() {
                    @Override
                    public void onResponse(Call<GsonNombreSubCategoria> call, Response<GsonNombreSubCategoria> response) {
                        if (response.body() != null) {
                            gsonNombreSubCategoriaMutableLiveData.setValue(response.body());
                        }



                    }

                    @Override
                    public void onFailure(Call<GsonNombreSubCategoria> call, Throwable t) {
                        gsonNombreSubCategoriaMutableLiveData.setValue(null);
                    }
                });
    }

    public LiveData<GsonNombreSubCategoria> getListNombreSubCategoriaLiveData(){
        return gsonNombreSubCategoriaMutableLiveData;
    }

}
