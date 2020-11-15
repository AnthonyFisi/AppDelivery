package com.example.yego.Repository.Repositorio;

import android.view.View;

import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Service.CategoriaEmpresaService;
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

public class CategoriaEmpresaRepository {

    private CategoriaEmpresaService categoriaEmpresaService;
    private MutableLiveData<GsonCategoriaEmpresa>  gsonCategoriaEmpresaLiveData;


    public CategoriaEmpresaRepository() {
        gsonCategoriaEmpresaLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        categoriaEmpresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CategoriaEmpresaService.class);

    }

    public void searchCategoriaEmpresa(String auth) {
        categoriaEmpresaService.searchCategoriaEmpresa(auth)
                .enqueue(new Callback<GsonCategoriaEmpresa>() {
                    @Override
                    public void onResponse(Call<GsonCategoriaEmpresa> call, Response<GsonCategoriaEmpresa> response) {
                        if (response.body() != null) {
                            System.out.println("ESTOY ACA EN CATEGORIS REPOSITORY");

                            gsonCategoriaEmpresaLiveData.setValue(response.body());
                        }



                    }

                    @Override
                    public void onFailure(Call<GsonCategoriaEmpresa> call, Throwable t) {
                        gsonCategoriaEmpresaLiveData.setValue(null);
                    }
                });
    }

    public LiveData<GsonCategoriaEmpresa> getCategoriaEmpresaLiveData() {
        return gsonCategoriaEmpresaLiveData;
    }

}
