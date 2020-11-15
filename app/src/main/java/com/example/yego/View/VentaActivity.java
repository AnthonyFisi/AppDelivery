package com.example.yego.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.MethodPayUI.FragmentTypesPay;
import com.example.yego.View.ProductDetailUI.FragmentComentarioDialog;
import com.example.yego.View.VentaUI.FragmentComentarioBottomSheet;
import com.example.yego.View.VentaUI.FragmentComentarioVentaDialog;
import com.example.yego.View.VentaUI.FragmentRegistroVenta;

public class VentaActivity extends AppCompatActivity implements  FragmentTypesPay.OnDataPass
, FragmentComentarioVentaDialog.FormDialogListener1 {

    private static final String  VENTA="com.example.yego.View.HorarioUI.FragmentListaHorario.Venta";

    private static final String  EMPRESA="com.example.yego.View.HorarioUI.FragmentListaHorario.Empresa";

    private static final String NOMBRE_HORARIO = "nombrehorario";

    private Empresa mEmpresa=new Empresa();

    private Venta mVenta;

    private String nombre_horario;

    private FragmentRegistroVenta fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        //RECIVE DATA
        reciveDataIntent();

        //PASS DATA
        passDataFragment();

        fragment = (FragmentRegistroVenta) getSupportFragmentManager().findFragmentById(R.id.fragment_registro_venta);



    }

    private void passDataFragment(){
        FragmentRegistroVenta fragment = (FragmentRegistroVenta) getSupportFragmentManager().findFragmentById(R.id.fragment_registro_venta);
        if(fragment !=null){
            fragment.setPassData(mEmpresa,mVenta,nombre_horario);
        }
    }

    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(EMPRESA) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(EMPRESA);
        }

        if(getIntent().getSerializableExtra(VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(VENTA);
        }

        nombre_horario=getIntent().getStringExtra(NOMBRE_HORARIO);
    }



    public static Intent newIntentVentaActivity(Context packageContext, Empresa empresa,Venta venta,String nombre_horario){
        Intent intent =new Intent(packageContext,VentaActivity.class);
        intent.putExtra(EMPRESA,empresa);
        intent.putExtra(VENTA,venta);
        intent.putExtra(NOMBRE_HORARIO,nombre_horario);
        return intent;
    }


    /*@Override
    public void update2(String comentario) {
        if(fragment !=null){
            fragment.resetDataComentario(comentario);
        }
    }

    @Override
    public void updateComentarioUbicacion(String comentario) {
        if(fragment !=null){
            fragment.resetDataComentarioUbicacion(comentario);
        }
    }*/

    @Override
    public void onDataPass(TipoPago tipoPago) {
        if(fragment !=null){
            fragment.resetDataMetodoPago(tipoPago);
        }
    }

    @Override
    public void update(String comentario,int code) {
        if(code==1){
            fragment.resetDataComentarioUbicacion(comentario);
        }
        if(code==2){
            fragment.updateTextViewCelular(comentario);
        }
        if(code==3){
            fragment.resetDataComentario(comentario);
        }
    }
}
