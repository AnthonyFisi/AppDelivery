package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Service.SubCategoriaEmpresaService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubCategoriaEmpresaRepository {

    private static final String SUB_CATEGORIA_SEARCH_SERVICE_URL_BASE="https://backend-tiend-go.herokuapp.com/";

    private SubCategoriaEmpresaService subCategoriaEmpresaService;
    private MutableLiveData<GsonSubCategoriaEmpresa> gsonSubCategoriaEmpresaLiveData;



    public SubCategoriaEmpresaRepository() {
        gsonSubCategoriaEmpresaLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        subCategoriaEmpresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(SUB_CATEGORIA_SEARCH_SERVICE_URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SubCategoriaEmpresaService.class);
    }

    public void searchSubCategoriaEmpresa(int idCategoria) {
        subCategoriaEmpresaService.searchSubCategoriaEmpresa(idCategoria)
                .enqueue(new Callback<GsonSubCategoriaEmpresa>() {
                    @Override
                    public void onResponse(Call<GsonSubCategoriaEmpresa> call, Response<GsonSubCategoriaEmpresa> response) {
                        if (response.body() != null) {
                            gsonSubCategoriaEmpresaLiveData.setValue(response.body());
                        }

                        //mShimmerViewContainer.stopShimmerAnimation();
                        //mShimmerViewContainer.setVisibility(View.GONE);

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
