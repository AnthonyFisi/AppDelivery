package com.example.yego.View.OrdenUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Orden;
import com.example.yego.View.OrdenUI.SupervisarOrden.FragmentSupervisarOrden;

public class SupervisarOrdenActivity extends AppCompatActivity {

    private GsonOrden orden;

    private static final String  INTENT_LISTA_ORDEN_ACTIVITY="com.example.yego.View.OrdenUI.SupervisarOrden.FragmentSupervisarOrden";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisar_orden);

        reciveIntent();
        sendDataFragmentSupervisar();


    }


    private void sendDataFragmentSupervisar(){
        /*
        FragmentSupervisarOrden fragmentSupervisarOrden=FragmentSupervisarOrden.newInstance(orden);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_supervisar_orden, fragmentSupervisarOrden , "");
        ft.commit();*/
        FragmentSupervisarOrden fragment = (FragmentSupervisarOrden) getSupportFragmentManager().findFragmentById(R.id.fragment_supervisar_orden);
        if(fragment !=null){
            fragment.setPassOrden(orden);
        }


    }

    private void  reciveIntent(){
        if(getIntent().getSerializableExtra(INTENT_LISTA_ORDEN_ACTIVITY)!=null){
            orden= (GsonOrden) getIntent().getSerializableExtra(INTENT_LISTA_ORDEN_ACTIVITY);
            System.out.println("LLEGARON LOS DATOS A SUPERVISAR ACTIVTY");
        }else{
            System.out.println(" NOOOOOOOOOOOOO ///////**///////// LLEGARON LOS DATOS A SUPERVISAR ACTIVTY");

        }
    }

    public static Intent newIntent(Context context, GsonOrden orden){
        Intent intent= new Intent(context,SupervisarOrdenActivity.class);
        intent.putExtra(INTENT_LISTA_ORDEN_ACTIVITY,orden);
        return  intent;
    }
}
