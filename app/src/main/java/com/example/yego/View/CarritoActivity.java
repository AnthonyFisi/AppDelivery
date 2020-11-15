package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.View.CarritoUI.FragmentListaCarrito;


public class CarritoActivity extends AppCompatActivity {

    private static final String  INTENT_CARRITO_ACTIVITY="com.example.yego.View.CarritoActivity.Carrito";

    private Empresa empresa=new Empresa();
    private ProductoJOINregistroPedidoJOINpedido pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        intentNull();

        goFragmentListaCarrito();


        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v->onBackPressed());



    }

    public static Intent newIntentCarritoActivity(Context packageContext, Empresa empresa ){
        Intent intent =new Intent(packageContext,CarritoActivity.class);
        intent.putExtra(INTENT_CARRITO_ACTIVITY,empresa);
        return intent;
    }

    private void goFragmentListaCarrito(){

        FragmentListaCarrito fragment = (FragmentListaCarrito) getSupportFragmentManager().findFragmentById(R.id.fragment_lista_carrito_MAIN);
        if(fragment !=null){
            fragment.setPassProducto(empresa);
        }

    }


    private void intentNull( ){
        if(getIntent().getSerializableExtra(INTENT_CARRITO_ACTIVITY) !=null){
            empresa=(Empresa) getIntent().getSerializableExtra(INTENT_CARRITO_ACTIVITY);
        }
    }


}
