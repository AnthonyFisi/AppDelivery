package com.example.yego.Repository.Repositorio;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa_favorita;
import com.example.yego.Repository.Modelo.Empresa_favorito_usuario;
import com.example.yego.Repository.Modelo.Gson.Empresa_favoritaGson;
import com.example.yego.Repository.Service.Empresa_favoritaService;
import com.example.yego.Repository.UrlBase;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class Empresa_favoritaRepository {


    private Empresa_favoritaService mEmpresa_favoritaService;

    private MutableLiveData<Empresa_favoritaGson> mEmpresa_favoritaGsonMutableLiveData;

    public Empresa_favoritaRepository(){

        mEmpresa_favoritaGsonMutableLiveData= new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mEmpresa_favoritaService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Empresa_favoritaService.class);
    }


    public void listaEmpresaFavorita(String token, int idusuario){
        mEmpresa_favoritaService.listaEmpresaFavorita(token, idusuario).enqueue(new Callback<Empresa_favoritaGson>() {
            @Override
            public void onResponse(Call<Empresa_favoritaGson> call, Response<Empresa_favoritaGson> response) {

                if(response.code()==200 && response.body()!=null){
                    mEmpresa_favoritaGsonMutableLiveData.postValue(response.body());
                }else{
                    mEmpresa_favoritaGsonMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Empresa_favoritaGson> call, Throwable t) {
                mEmpresa_favoritaGsonMutableLiveData.postValue(null);

            }
        });
    }


    public MutableLiveData<Empresa_favoritaGson> getEmpresa_favoritaGsonMutableLiveData(){
        return mEmpresa_favoritaGsonMutableLiveData;
    }


}
