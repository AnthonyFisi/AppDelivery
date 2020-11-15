package com.example.yego.View.VentaUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.HomeActivity;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.OrdenActivity;

public class ResultVentActivity extends AppCompatActivity {

    private static final String  EMPRESA="com.example.yego.View.VentaUI.FragmentRegistroVenta.EmpresaObjeto";
    private static final String VENTA = "venta";

    private Empresa mEmpresa;

    private TextView activity_result_vent_NOMBRE_USUARIO,codigo_pedido;//activity_result_vent_NOMBRE_EMPRESA;
    private Button activity_result_vent_BUTTON_ORDEN,activity_result_vent_BUTTON_INICIO;
    private Venta venta;
    //private ImageView activity_result_vent_IMAGEVIEW_CLOSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_vent);
        //RECIVE DATA
        reciveDataIntent();

        //DECLARARA WIDGET
        declararWidget();

        //SET DATA
        setData();

        //CLICK BUTTON
        clickButtonINICIO_CLOSE_ORDEN();

        Toolbar toolbar=findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });

    }



    private void declararWidget(){
        activity_result_vent_NOMBRE_USUARIO=findViewById(R.id.activity_result_vent_NOMBRE_USUARIO);
       // activity_result_vent_NOMBRE_EMPRESA=findViewById(R.id.activity_result_vent_NOMBRE_EMPRESA);
        activity_result_vent_BUTTON_ORDEN=findViewById(R.id.activity_result_vent_BUTTON_ORDEN);
        activity_result_vent_BUTTON_INICIO=findViewById(R.id.activity_result_vent_BUTTON_INICIO);
      //  activity_result_vent_IMAGEVIEW_CLOSE=findViewById(R.id.activity_result_vent_IMAGEVIEW_CLOSE);
        codigo_pedido=findViewById(R.id.codigo_pedido);

    }

    private void setData(){
        String nombre="Felicidades "+Cliente_Bi.getCliente().getNombre();
        activity_result_vent_NOMBRE_USUARIO.setText(nombre);
        codigo_pedido.setText(String.valueOf(venta.getIdEmpresa()));
    }


    private void clickButtonINICIO_CLOSE_ORDEN(){

        activity_result_vent_BUTTON_INICIO.setOnClickListener(view -> {
            onBackPressed();
        });

        activity_result_vent_BUTTON_ORDEN.setOnClickListener(view -> {

            //Intent intent= new Intent(ResultVentActivity.this, OrdenActivity.class);
            Intent intent= InicioActivity.newIntentInicioActivity(this,true);
           // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            //ActivityCompat.finishAffinity(this);
        });
/*
        activity_result_vent_IMAGEVIEW_CLOSE.setOnClickListener( view -> {
            onBackPressed();
        });*/
    }



    private void reciveDataIntent()
    {
        if (getIntent().getSerializableExtra(EMPRESA) != null) {
            mEmpresa = (Empresa) getIntent().getSerializableExtra(EMPRESA);
        }

        if (getIntent().getSerializableExtra(VENTA) != null) {
            venta = (Venta) getIntent().getSerializableExtra(VENTA);
        }
    }

    public static Intent newIntentResultActivity(Context packageContext , Empresa empresa, Venta venta)
    {
        Intent intent =new Intent(packageContext, ResultVentActivity.class);
        intent.putExtra(EMPRESA,empresa);
        intent.putExtra(VENTA,venta);
        return intent;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean answer=false;

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
            answer=true;
        }
        return answer;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent= InicioActivity.newIntentInicioActivity(this,false);
       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        //ActivityCompat.finishAffinity(this);*/
    }
}
