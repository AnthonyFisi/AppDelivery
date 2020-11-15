package com.example.yego.Login;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.InicioActivity;
import com.example.yego.ViewModel.Cliente_BiViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SplashActivity extends AppCompatActivity implements MyReceiver.ConnectivityReciverListener {

    private boolean response=false;

    private Cliente_BiViewModel viewModel;

    private JwtResponse mJwtResponse;

    boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewModel= new ViewModelProvider(this).get(Cliente_BiViewModel.class);
        viewModel.init();

        checkInternetConnection();


        if (SessionPrefs.get(this).isLoggedIn()) {

            response=true;

            mJwtResponse=SessionPrefs.get(this).data();


            if(mJwtResponse.getIdubicacion()==0){

                //Intent intent= LocationActivity.newIntentLocationActivity(SplashActivity.this,true);
                Intent intent=new Intent(SplashActivity.this,PhoneNumberActivity.class);

                startActivity(intent);
                finish();


            }else{

                viewModel.findInformacionClienteBi(mJwtResponse.getId().intValue());

            }


        }else{

            //INICIAR SESION ACTIVITY

            new Handler().postDelayed(() -> {
                Intent intent= new Intent(SplashActivity.this,IniciarSesionActivity.class);
                startActivity(intent);
                finish();
            },3000);
        }

        loadData();

    }


    private void loadData(){


        if(response){

            viewModel.getCliente_biLiveData().observe(this, cliente_bi -> {
                if(isConnected){

                    if(cliente_bi!=null){

                        //VA IR DIRECTAMENTE AL HOME DE LA APLICACION

                        Cliente_Bi.registrarCliente_bi(cliente_bi);

                        Ubicacion ubicacion=new Ubicacion();
                        ubicacion.setIdubicacion(cliente_bi.getIdubicacion());
                        ubicacion.setUbicacion_nombre(cliente_bi.getUbicacion_nombre());
                        ubicacion.setUbicacion_comentarios(cliente_bi.getUbicacion_comentarios());
                        ubicacion.setMaps_coordenada_x(cliente_bi.getMaps_coordenada_x());
                        ubicacion.setMaps_coordenada_y(cliente_bi.getMaps_coordenada_y());
                        ubicacion.setEliminado(ubicacion.isEliminado());
                        ubicacion.setIdusuario(cliente_bi.getIdusuario());
                        ubicacion.setUbicacion_estado(true);

                        Ubicacion.ubicacionEnable=ubicacion;

                        Intent intent= InicioActivity.newIntentInicioActivity(SplashActivity.this,false);
                        startActivity(intent);
                        finish();

                    }else {

                        //VA IR A INICIO DE SESION SI ES QUE HUBIERA ALGUN PROBLEMA DE INTERNET O ALGUN OTRO EN EL SERVIDOR

                        isConnected=false;
                        Intent intent= new Intent(SplashActivity.this,IniciarSesionActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }else {

                    Intent intent= new Intent(getApplicationContext(), NetworkActivity.class);
                    startActivity(intent);
                    finish();


                }
            });

        }

    }



    private void checkInternetConnection() {

       isConnected=MyReceiver.isConnected();

        System.out.println("VAÑOR DEL INTERNET"+isConnected);


    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter= new IntentFilter();

        intentFilter.addAction(ConnectivityManager.EXTRA_CAPTIVE_PORTAL);

        MyReceiver myReciver= new MyReceiver();

        registerReceiver(myReciver,intentFilter);

        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        this.isConnected=isConnected;
    }
}
