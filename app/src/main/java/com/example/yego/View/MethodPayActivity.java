package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Horario;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.MethodPayUI.FragmentTypesPay;
import com.example.yego.View.VentaUI.FragmentRegistroVenta;

public class MethodPayActivity extends AppCompatActivity implements FragmentTypesPay.OnDataPass {


    private static final String  VENTA="com.example.yego.View.HorarioUI.FragmentListaHorario.Venta";

    private static final String  EMPRESA="com.example.yego.View.HorarioUI.FragmentListaHorario.Empresa";

    private Empresa mEmpresa;

    private Venta mVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_pay);
        reciveDataIntent();
        passDataFragment();

    }

    public static  Intent newInstanceMethodPay(Context context,Empresa empresa,Venta venta){
        Intent intent= new Intent(context,MethodPayActivity.class);
        intent.putExtra(EMPRESA,empresa);
        intent.putExtra(VENTA,venta);
        return intent;
    }


    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(EMPRESA) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(EMPRESA);
        }

        if(getIntent().getSerializableExtra(VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(VENTA);
        }
    }


    private void passDataFragment(){

        Fragment fragment=FragmentTypesPay.newInstance(mVenta,mEmpresa);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_method_pay,fragment).commit();
    }



    @Override
    public void onDataPass(TipoPago tipoPago) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data",tipoPago);
        setResult(Activity.RESULT_OK,returnIntent);
        Toast.makeText(this, "Estamos devolviendo el dato"+tipoPago.getTipopago_nombre(), Toast.LENGTH_SHORT).show();
    }
}
