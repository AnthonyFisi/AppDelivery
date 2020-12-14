package com.example.yego.Repository.Repositorio;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Service.Cliente_BiService;
import com.example.yego.Repository.UrlBase;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class Cliente_BiRepository {

    private Cliente_BiService mCliente_biService;

    private MutableLiveData<Cliente_Bi> mCliente_biMutableLiveData;

    public Cliente_BiRepository(){
        mCliente_biMutableLiveData = new MutableLiveData<>();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mCliente_biService = new retrofit2.Retrofit.Builder()
                .baseUrl(UrlBase.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Cliente_BiService.class);
    }

    public void findInformacionClienteBi(String auth,int idusuario)
    {
        mCliente_biService.findInformacionClienteBi(auth,idusuario).enqueue(new Callback<Cliente_Bi>() {
            @Override
            public void onResponse(Call<Cliente_Bi> call, Response<Cliente_Bi> response) {
                if (response.code() == 500 || response.code()==401 || response.code()==400) {

                    System.out.println("ENTRAMOS AL 401");
                   mCliente_biMutableLiveData.postValue(null);
                }

                if (response.body() != null && response.code() == 200) {
                    System.out.println("ENTRAMOS AL 200");

                    mCliente_biMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cliente_Bi> call, Throwable t) {

                mCliente_biMutableLiveData.postValue(null);

            }
        });
    }


    public MutableLiveData<Cliente_Bi> getCliente_biMutableLiveData(){
        return mCliente_biMutableLiveData;
    }
}
