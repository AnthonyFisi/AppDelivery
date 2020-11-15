package com.example.yego.Repository.Repositorio;


import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Service.SubCategoriaEmpresaService;
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

public class SubCategoriaEmpresaRepository {


    private SubCategoriaEmpresaService subCategoriaEmpresaService;
    private MutableLiveData<GsonSubCategoriaEmpresa> gsonSubCategoriaEmpresaLiveData;



    public SubCategoriaEmpresaRepository() {
        gsonSubCategoriaEmpresaLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        subCategoriaEmpresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SubCategoriaEmpresaService.class);
    }

    public void searchSubCategoriaEmpresa(String token,int idCategoria) {
        subCategoriaEmpresaService.searchSubCategoriaEmpresa(token,idCategoria)
                .enqueue(new Callback<GsonSubCategoriaEmpresa>() {
                    @Override
                    public void onResponse(Call<GsonSubCategoriaEmpresa> call, Response<GsonSubCategoriaEmpresa> response) {
                        if (response.body() != null) {
                            gsonSubCategoriaEmpresaLiveData.setValue(response.body());
                        }else{

                            gsonSubCategoriaEmpresaLiveData.setValue(null);

                        }

                    }

                    @Override
                    public void onFailure(Call<GsonSubCategoriaEmpresa> call, Throwable t) {
                        gsonSubCategoriaEmpresaLiveData.setValue(null);
                    }
                });
    }

    public LiveData<GsonSubCategoriaEmpresa> getSubCategoriaEmpresaLiveData() {
        return gsonSubCategoriaEmpresaLiveData;
    }
}
