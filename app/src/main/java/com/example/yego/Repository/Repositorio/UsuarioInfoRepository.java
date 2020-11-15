package com.example.yego.Repository.Repositorio;

import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.UsuarioInfo;
import com.example.yego.Repository.Service.UsuarioInfoService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class UsuarioInfoRepository {


    private UsuarioInfoService mUsuarioInfoService;

    private MutableLiveData<JwtResponse> mJwtResponseMutableLiveData;

    public UsuarioInfoRepository(){
        mJwtResponseMutableLiveData =new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mUsuarioInfoService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioInfoService.class);

    }

    public void registrarUsuarioProvider(UsuarioInfo usuarioInfo){

        mUsuarioInfoService.registrarUsuarioProvider(usuarioInfo).enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {


                if (response.code() == 500) {

                    mJwtResponseMutableLiveData.postValue(null);
                }

                if (response.body() != null && response.code() == 200) {

                    mJwtResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {

                mJwtResponseMutableLiveData.postValue(null);

            }
        });
    }


    public LiveData<JwtResponse>  getJwtResponse(){
        return mJwtResponseMutableLiveData;
    }
}
