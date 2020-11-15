package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Empresa;
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

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class EmpresaRepository {


    private EmpresaService  empresaService;

    private MutableLiveData<GsonEmpresa> gsonEmpresaMutableLiveData;

    private MutableLiveData<Empresa> mEmpresaMutableLiveData;


    public EmpresaRepository(){
        gsonEmpresaMutableLiveData= new MutableLiveData<>();
        mEmpresaMutableLiveData=new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        empresaService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmpresaService.class);
    }


    public void searchListafindByLocation( String token,int idCategoria,String Ubicacion){
        empresaService.searchListafindByLocation(token,idCategoria,Ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<GsonEmpresa> getListafindByLocationLiveData(){
        return gsonEmpresaMutableLiveData;
    }




    public void searchListaCategoriaComplementaria(String token, int idCategoria,String ubicacion){
        empresaService.searchListaCategoriaComplementaria(token,idCategoria,ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });
    }



    public void searchListafindByLocationSubCategoria( String token,int idSubCategoria,String Ubicacion){
        empresaService.searchListafindByLocationSubCategoria(token,idSubCategoria,Ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });
    }

    public void searchListaSubcategoriaComplementaria(String token, int idSubCategoria,String ubicacion){
        empresaService.searchListaSubcategoriaComplementaria(token,idSubCategoria,ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });

    }

    public void  searchListaTotalcerca(String token,String ubicacion){
        empresaService. searchListaTotalcerca(token,ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);
            }
        });

    }

    public void listaEmpresaFavorita(String token, int idusuario) {

        empresaService.listaEmpresaFavorita(token, idusuario).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);

            }
        });
    }

    public void filtroEmpresa( String auth, int idcategoriaempresa, int distancia, float preciodelivery,String ubicacion){
        empresaService.filtroEmpresa(auth, idcategoriaempresa, distancia, preciodelivery, ubicacion).enqueue(new Callback<GsonEmpresa>() {
            @Override
            public void onResponse(Call<GsonEmpresa> call, Response<GsonEmpresa> response) {
                if(response.code()==200 && response.body()!=null){
                    gsonEmpresaMutableLiveData.postValue(response.body());
                }else {
                    gsonEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<GsonEmpresa> call, Throwable t) {
                gsonEmpresaMutableLiveData.postValue(null);

            }
        });
    }

    /*INICIO DE OTRA LLAMADA*/

    public void getEmpresaById(String token, int idempresa) {
        empresaService.getEmpresaById(token, idempresa).enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                if(response.code()==200 && response.body()!=null){
                    mEmpresaMutableLiveData.postValue(response.body());
                }else {
                    mEmpresaMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                mEmpresaMutableLiveData.postValue(null);

            }
        });
    }
    public LiveData<Empresa> getEmpresaMutableLivedata() {
        return mEmpresaMutableLiveData;
    }







}
