package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.DetailSubCategoriaEmpresaUI.FragmentEmpresaSubCategoria;
import com.example.yego.View.DetailSubCategoriaEmpresaUI.FragmentSubUbicacionCerca;
import com.example.yego.View.SearchUI.SearchActivity;

public class DetailSubCategoriaEmpresaActivity extends AppCompatActivity  {


    private static final String SUB_CATEGORIA_EMPRESA ="subcategoiraempresa" ;
    private static final String GSONCATEGORIA_EMPRESA = "gsonCategoria";

    Toolbar toolbar;

    private SubCategoriaEmpresa subcategoriaEmpresa;

    private GsonCategoriaEmpresa gsonCategoriaEmpresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sub_categoria_empresa);

        //int idSubCategoria= getIntent().getIntExtra(FragmentSubCategoria.INTENT_OBJECT_FRAGMENT_SUBCATEGORIA,1);
        reciveData();

        TextView nombre_subcategoria=findViewById(R.id.nombre_subcategoria);
        nombre_subcategoria.setText(subcategoriaEmpresa.getNombre_subcategoria());

        toolbar=findViewById(R.id.toolbar_3_sub) ;
        setSupportActionBar(toolbar);
       // toolbar.setTitle(Ubicacion.ubicacionEnable.getUbicacion_nombre());

        toolbar.setNavigationOnClickListener(v->{onBackPressed();});

        TextView ubicacion_enable = findViewById(R.id.ubicacion_enable);
        ubicacion_enable.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());


        FragmentSubUbicacionCerca fragmentSubUbicacionCerca = (FragmentSubUbicacionCerca) getSupportFragmentManager().findFragmentById(R.id.fragment_sub_categoria_cerca_detail);
        if(fragmentSubUbicacionCerca !=null){
            fragmentSubUbicacionCerca.setIdCategoria(subcategoriaEmpresa.getIdSubCategoriaEmpresa());
        }

        FragmentEmpresaSubCategoria fragmentEmpresaSubCategoria = (FragmentEmpresaSubCategoria) getSupportFragmentManager().findFragmentById(R.id.fragmen_total_subEmpresa);
        if(fragmentEmpresaSubCategoria !=null){
            fragmentEmpresaSubCategoria.setIdCategoria(subcategoriaEmpresa.getIdSubCategoriaEmpresa());
        }
    }



    public static Intent newIntentDetailSubCategoriaActivity(Context context, SubCategoriaEmpresa subcategoriaEmpresa, GsonCategoriaEmpresa gsonCategoriaEmpresa){
        Intent intent= new Intent(context, DetailSubCategoriaEmpresaActivity.class);
        intent.putExtra(SUB_CATEGORIA_EMPRESA,subcategoriaEmpresa);
        intent.putExtra(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);

        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(SUB_CATEGORIA_EMPRESA)!=null){
            subcategoriaEmpresa=(SubCategoriaEmpresa) getIntent().getSerializableExtra(SUB_CATEGORIA_EMPRESA);
        }

        if(getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA)!=null){
            gsonCategoriaEmpresa=(GsonCategoriaEmpresa) getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA);
        }
        // idusuario=getIntent().getIntExtra(ID_USUARIO,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.menu_subcategoria,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_share);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent= SearchActivity.newIntent(DetailSubCategoriaEmpresaActivity.this,gsonCategoriaEmpresa);
                startActivity(intent);
                finish();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }


}
