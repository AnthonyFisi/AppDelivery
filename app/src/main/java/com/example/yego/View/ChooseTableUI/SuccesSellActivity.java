package com.example.yego.View.ChooseTableUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.InicioActivity;
import com.example.yego.ViewModel.VentaViewModel;

public class SuccesSellActivity extends AppCompatActivity {

    private static final String  EMPRESA="com.example.yego.View.HorarioUI.FragmentListaHorario.Empresa";

    private Empresa mEmpresa;

    private TextView nombre_restaurante;

    private LinearLayout seguir_orden,inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_sell);

        reciveDataIntent();

        declararWidgets();

        setOnClickSecondScreen();
    }


    public static Intent newIntentSuccesSellActivity(Context packageContext, Empresa empresa){
        Intent intent =new Intent(packageContext,SuccesSellActivity.class);
        intent.putExtra(EMPRESA,empresa);
        return intent;
    }


    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(EMPRESA) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(EMPRESA);
        }
    }

    private void declararWidgets(){

        seguir_orden=findViewById(R.id.seguir_orden);

        inicio=findViewById(R.id.inicio);

        nombre_restaurante=findViewById(R.id.nombre_restaurante);

        nombre_restaurante.setText(mEmpresa.getNombre_empresa());

    }


    private void setOnClickSecondScreen(){

        seguir_orden.setOnClickListener(v->{
            Intent intent= InicioActivity.newIntentInicioActivity(this,true);
            startActivity(intent);
            finish();
        });


        inicio.setOnClickListener(v->{
            Intent intent= new Intent(SuccesSellActivity.this, InicioActivity.class);
            startActivity(intent);
            finish();
        });

    }

}
