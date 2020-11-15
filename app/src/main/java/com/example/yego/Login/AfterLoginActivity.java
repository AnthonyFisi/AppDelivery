package com.example.yego.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.InicioActivity;
import com.example.yego.ViewModel.Cliente_BiViewModel;

public class AfterLoginActivity extends AppCompatActivity {

    private static final String ID_USUARIO = "idusuario";
    private Cliente_BiViewModel viewModelCliente;
    private int idusuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);


        reciveData();

        viewModelCliente=new ViewModelProvider(this).get(Cliente_BiViewModel.class);
        viewModelCliente.init();
        viewModelCliente.findInformacionClienteBi(idusuario);

        getDataCLiente_Bi();
    }

    private void getDataCLiente_Bi(){

        viewModelCliente.getCliente_biLiveData().observe(this, new Observer<Cliente_Bi>() {
            @Override
            public void onChanged(Cliente_Bi cliente_bi) {

                if(cliente_bi!=null){

                    Ubicacion.ubicacionEnable=new Ubicacion(cliente_bi.getIdubicacion(),cliente_bi.getUbicacion_nombre(),cliente_bi.getUbicacion_comentarios(),cliente_bi.getIdusuario(),true,false,"",cliente_bi.getMaps_detalle(),cliente_bi.getMaps_coordenada_x(),cliente_bi.getMaps_coordenada_y());

                   // Usuario.idUsuario=cliente_bi.getIdusuario();




                    Cliente_Bi.registrarCliente_bi(cliente_bi);


                    //IR AL HOME
                    Intent intent=InicioActivity.newIntentInicioActivity(getApplicationContext(),false);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(AfterLoginActivity.this, "Lo sentimos no pudo ingresar AFTER LOGIN ACTIVITY", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static Intent newIntentLocationActivity(Context context, int idusuario){
        Intent intent= new Intent(context, AfterLoginActivity.class);
        intent.putExtra(ID_USUARIO,idusuario);
        return intent;
    }


    private  void reciveData(){
        idusuario=getIntent().getIntExtra(ID_USUARIO,0);
    }

}
