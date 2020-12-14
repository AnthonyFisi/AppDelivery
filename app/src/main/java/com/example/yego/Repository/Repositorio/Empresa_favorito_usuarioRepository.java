package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Empresa_favorito_usuario;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.Repository.Service.Empresa_favorito_usuarioService;
import com.example.yego.Repository.UrlBase;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class Empresa_favorito_usuarioRepository {


    private Empresa_favorito_usuarioService mEmpresa_favorito_usuarioService;

    private MutableLiveData<Empresa_favorito_usuario> mEmpresa_favorito_usuarioMutableLiveData;

    public Empresa_favorito_usuarioRepository(){
        mEmpresa_favorito_usuarioMutableLiveData= new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mEmpresa_favorito_usuarioService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Empresa_favorito_usuarioService.class);
    }


    public void agregarFavorito(String auth,  Empresa_favorito_usuario favorito){
        mEmpresa_favorito_usuarioService.agregarFavorito(auth, favorito).enqueue(new Callback<Empresa_favorito_usuario>() {
            @Override
            public void onResponse(Call<Empresa_favorito_usuario> call, Response<Empresa_favorito_usuario> response) {

                if(response.code()==200 && response.body()!=null){
                    mEmpresa_favorito_usuarioMutableLiveData.postValue(response.body());
                }else{
                    mEmpresa_favorito_usuarioMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Empresa_favorito_usuario> call, Throwable t) {
                mEmpresa_favorito_usuarioMutableLiveData.postValue(null);

            }
        });
    }

    public void eliminarFavorito(String auth, int idempresa,int idusuario){
        mEmpresa_favorito_usuarioService.eliminarFavorito(auth, idempresa, idusuario).enqueue(new Callback<Empresa_favorito_usuario>() {
            @Override
            public void onResponse(Call<Empresa_favorito_usuario> call, Response<Empresa_favorito_usuario> response) {
                if(response.code()==200 && response.body()!=null){
                    mEmpresa_favorito_usuarioMutableLiveData.postValue(response.body());
                }else{
                    mEmpresa_favorito_usuarioMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Empresa_favorito_usuario> call, Throwable t) {
                mEmpresa_favorito_usuarioMutableLiveData.postValue(null);

            }
        });
    }




        public MutableLiveData<Empresa_favorito_usuario> getEmpresa_favorito_usuarioMutableLiveData(){
        return mEmpresa_favorito_usuarioMutableLiveData;
    }
}
