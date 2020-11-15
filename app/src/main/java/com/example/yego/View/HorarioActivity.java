package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.HorarioUI.FragmentListaHorario;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class HorarioActivity extends AppCompatActivity {

    private static final String  INTENT_EMPRESA_ACTIVITY="com.example.yego.View.CarritoUI.FramentTipo_Envio.Empresa";

    private static final String  INTENT_VENTA="com.example.yego.View.CarritoUI.FramentTipo_Envio.Tipo_Envio";

    private Tipo_Envio mTipo_envio=new Tipo_Envio();

    private Venta mVenta= new Venta();

    private Empresa mEmpresa = new Empresa();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        //Recibir data

        reciveDataIntent();

        //PAss DATA FRAGMENT

        passDataFragemnt();

        //DELCARAR WIDGETS
        declararWidgets();

        //SET DATA WIDGER
        setDataWidget();


        closeHorarioActivity();

    }


    public void declararWidgets(){
        mToolbar=findViewById(R.id.toolbar4);
    }

    private void setDataWidget(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v->onBackPressed());
    }

    private void closeHorarioActivity(){
        mToolbar.setNavigationOnClickListener(v-> onBackPressed());
    }

    private void passDataFragemnt(){
        FragmentListaHorario fragment = (FragmentListaHorario) getSupportFragmentManager().findFragmentById(R.id.fragment_horario_lista);
        if(fragment !=null){
            fragment.setPassData(mEmpresa,mVenta);
        }
    }



    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(INTENT_VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(INTENT_VENTA);
        }
        if(getIntent().getSerializableExtra(INTENT_EMPRESA_ACTIVITY) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(INTENT_EMPRESA_ACTIVITY);
        }
    }

    public static Intent newIntentHorarioActivity(Context packageContext, Empresa empresa, Venta venta){
        Intent intent =new Intent(packageContext,HorarioActivity.class);
        intent.putExtra(INTENT_EMPRESA_ACTIVITY,empresa);
        intent.putExtra(INTENT_VENTA,venta);
        return intent;
    }


}
