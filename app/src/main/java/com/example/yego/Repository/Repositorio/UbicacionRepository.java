package com.example.yego.Repository.Repositorio;

import com.example.yego.Login.SessionPrefs;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Service.UbicacionService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yego.Repository.UrlBase.URL_BASE;

public class UbicacionRepository  {


    private static final String PRODUCTO_SERVICE_URL_BASE_2="https://maps.googleapis.com/";


    private String respo;

    private UbicacionService ubicacionService,ubicacionService2;

    private MutableLiveData<GsonUbicacion> gsonUbicacionMutableLiveData;

    private MutableLiveData<Ubicacion> mUbicacionMutableLiveData;

    public UbicacionRepository(){

        gsonUbicacionMutableLiveData =new MutableLiveData<>();
        mUbicacionMutableLiveData= new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        ubicacionService = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UbicacionService.class);

        ubicacionService2 = new retrofit2.Retrofit.Builder()
                .baseUrl(PRODUCTO_SERVICE_URL_BASE_2)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UbicacionService.class);

    }




    public void searchListUbicacion(String auth,int idusuario){
        ubicacionService.searchListUbicacion(auth,idusuario).enqueue(new Callback<GsonUbicacion>() {
            @Override
            public void onResponse(Call<GsonUbicacion> call, Response<GsonUbicacion> response) {
                if(response.body()!=null){
                    gsonUbicacionMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonUbicacion> call, Throwable t) {

                System.out.println(t.getMessage());
                if(t instanceof HttpException){
                    HttpException exception = (HttpException) t;
                    switch (exception.code()) {
                        case 400:
                            System.out.println("HERE IS ERROR");
                            break;
                        case 500:
                            // Handle code 500
                            break;
                        default:
                            break;
                    }
                }
                gsonUbicacionMutableLiveData.postValue(null);
            }
        });
    }


    public void searchUbicacion(String auth,int idubicacion){
        ubicacionService.searchUbicacion(auth,idubicacion).enqueue(new Callback<GsonUbicacion>() {
            @Override
            public void onResponse(Call<GsonUbicacion> call, Response<GsonUbicacion> response) {
                if(response.body()!=null){
                    gsonUbicacionMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GsonUbicacion> call, Throwable t) {
                gsonUbicacionMutableLiveData.postValue(null);
            }
        });
    }

    public void  registarUbicacion(String auth,Ubicacion ubicacion,int idOldUbicacion){
        ubicacionService.registarUbicacion(auth,ubicacion,idOldUbicacion).enqueue(new Callback<Ubicacion>() {
            @Override
            public void onResponse(Call<Ubicacion> call, Response<Ubicacion> response) {
                if(response.body()!=null){
                    mUbicacionMutableLiveData.postValue(response.body());
                }
                System.out.println("FUE REGISTRADO EXITOSAMENTE");
            }

            @Override
            public void onFailure(Call<Ubicacion> call, Throwable t) {
                mUbicacionMutableLiveData.postValue(null);
                System.out.println("NO FUE REGISTRADO");

            }
        });
    }

    public void updateEstadoUbicacion(String auth,int idOldUbicacion, int idNewUbicacion){
        ubicacionService.updateEstadoUbicacion(auth,idOldUbicacion,idNewUbicacion).enqueue(new Callback<GsonUbicacion>() {
            @Override
            public void onResponse(Call<GsonUbicacion> call, Response<GsonUbicacion> response) {
                if(response.body()!=null){
                    gsonUbicacionMutableLiveData.postValue(response.body());
                    System.out.println("ELMENTO FUE ACTUALIZADo");

                }
            }

            @Override
            public void onFailure(Call<GsonUbicacion> call, Throwable t) {
                gsonUbicacionMutableLiveData.postValue(null);

            }
        });
    }

    public void deleteUbicacion(String auth,int idUbicacion){
        ubicacionService.deleteUbicacion(auth,idUbicacion).enqueue(new Callback<GsonUbicacion>() {
            @Override
            public void onResponse(Call<GsonUbicacion> call, Response<GsonUbicacion> response) {
                if(response.body()!=null){
                    gsonUbicacionMutableLiveData.postValue(response.body());
                    System.out.println("ELMENTO FUE ELIMINDADO");


                }

            }

            @Override
            public void onFailure(Call<GsonUbicacion> call, Throwable t) {
                gsonUbicacionMutableLiveData.postValue(null);

            }
        });
    }


    public String searchLocationAddress(String auth,String latlong,String key){
        ubicacionService2.searchLocationAddress(latlong,key).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    respo=response.body();
                    System.out.println("LLEGO LA DATA ");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("no llego la daata ");

            }
        });

        return respo;
    }

    public  void registrarFirstTimeUbicacion(String auth,Ubicacion ubicacion){
        ubicacionService.registrarFirstTimeUbicacion(auth,ubicacion).enqueue(new Callback<Ubicacion>() {
            @Override
            public void onResponse(Call<Ubicacion> call, Response<Ubicacion> response) {

                if(response.code()==400 || response.code()==500){
                    gsonUbicacionMutableLiveData.postValue(null);
                }

                mUbicacionMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(Call<Ubicacion> call, Throwable t) {
                mUbicacionMutableLiveData.postValue(null);

            }
        });
    }


    public LiveData<GsonUbicacion> getUbicacionLiveData(){

        System.out.println("LIVEDATA REPOSITORY ");

        return gsonUbicacionMutableLiveData;
    }

    public LiveData<Ubicacion> getUbicacionMutableLivedata(){
        return mUbicacionMutableLiveData;
    }


}
