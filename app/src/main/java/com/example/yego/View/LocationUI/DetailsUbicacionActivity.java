package com.example.yego.View.LocationUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.yego.Login.DetalleUbicacionBottonSheetFragment;
import com.example.yego.Login.GoogleMapsOficial;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Ubicacion;

public class DetailsUbicacionActivity extends AppCompatActivity  implements  DetalleUbicacionBottonSheetFragment.BackToInicio{

    private static final String ARGS_UBICACION = "ubicacion";
    private static final String ROUTE = "route";
    private Ubicacion ubicacion;
    private boolean route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ubicacion);
        route=false;


        reciveData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v->onBackPressed());

        DetalleUbicacionBottonSheetFragment fragment=DetalleUbicacionBottonSheetFragment.newInstance(ubicacion,route);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_DETAIL_UBICACION,fragment).commit();
    }


    public static Intent newIntentGoogleMaps(Context context, Ubicacion ubicacion,boolean route){
        Intent intent= new Intent(context, DetailsUbicacionActivity.class);
        intent.putExtra(ARGS_UBICACION,ubicacion);
        intent.putExtra(ROUTE,route);
        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(ARGS_UBICACION)!=null){
            ubicacion=(Ubicacion)getIntent().getSerializableExtra(ARGS_UBICACION);
        }
        route=getIntent().getBooleanExtra(ROUTE,false);
    }

    @Override
    public void back() {
        onBackPressed();
    }
}
