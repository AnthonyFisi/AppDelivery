package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Service.UsuarioInfoService;
import com.example.yego.Repository.Service.UsuarioService;

import java.lang.invoke.MutableCallSite;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class UsuarioRepository {


    private UsuarioService mUsuarioService;

    private MutableLiveData<Usuario> mUsuarioMutableLiveData;


    public UsuarioRepository(){
        mUsuarioMutableLiveData=new MutableLiveData<>();
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mUsuarioService = retrofit.create(UsuarioService.class);
    }



    public void updateCelular(String token,int idusuario,String celular){
        mUsuarioService.updateCelular(token, idusuario, celular).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.code()==200 && response.body()!=null){
                    mUsuarioMutableLiveData.postValue(response.body());
                }else {
                    mUsuarioMutableLiveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mUsuarioMutableLiveData.postValue(null);

            }
        });
    }


    public MutableLiveData<Usuario> getUsuarioMutableLiveData(){
        return mUsuarioMutableLiveData;
    }
}
