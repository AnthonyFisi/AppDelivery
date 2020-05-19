package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.Repository.Service.EmpresaService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpresaRepository {

    private static final String EMPRESA_SERVICE_URL_BASE="https://backend-tiend-go.herokuapp.com/";

    private EmpresaService  empresaService;
    private MutableLiveData<GsonEmpresa> gsonEmpresaMutableLiveData;
    private MutableLiveData<GsonEmpresa> gsonEmpresaMutableLiveData1;
    private MutableLiveData<GsonEmpresa> gsonEmpresaMutableLiveData2;
    private MutableLiveData<GsonEmpresa> gsonEmpresaMutableLiveData3;


    public EmpresaRepository(){
        gsonEmpresaMutableLiveData= new MutableLiveData<>();
        gsonEmpresaMutableLiveData1= new MutableLiveData<>();
        gsonEmpresaMutableLiveData2= new MutableLiveData<>();
        gsonEmpresaMutableLiveData3= new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        empresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(EMPRESA_SERVICE_URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmpresaService.class);
    }


    public void searchListafindByLocation( int idCategoria,String Ubicacion){
        empresaService.searchListafindByLocation(idCategoria,Ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                gsonEmpresaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<GsonEmpresa> getListafindByLocationLiveData(){
        return gsonEmpresaMutableLiveData;
    }




    public void searchListaSortfindBy( int idCategoria){
        empresaService.searchListaSortfindBy(idCategoria).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                gsonEmpresaMutableLiveData1.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData1.postValue(null);
            }
        });
    }

    public LiveData<GsonEmpresa> getListaSortfindByLiveData(){
        return gsonEmpresaMutableLiveData1;
    }


    public void searchListafindByLocationSubCategoria( int idSubCategoria,String Ubicacion){
        empresaService.searchListafindByLocationSubCategoria(idSubCategoria,Ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                gsonEmpresaMutableLiveData2.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData2.postValue(null);
            }
        });
    }

    public LiveData<GsonEmpresa> getListafindByLocationSubCategoriaLiveData(){
        return gsonEmpresaMutableLiveData2;
    }

    public void searchListafindBy( int idSubCategoria){
        empresaService. searchListafindBy(idSubCategoria).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                gsonEmpresaMutableLiveData3.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData3.postValue(null);
            }
        });
    }

    public LiveData<GsonEmpresa> getListafindByLiveData(){
        return gsonEmpresaMutableLiveData3;
    }




}
