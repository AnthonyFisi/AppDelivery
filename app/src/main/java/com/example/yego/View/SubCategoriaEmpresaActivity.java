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
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.SearchUI.SearchActivity;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentEmpresaSort;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentSubCategoria;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentUbicacionCerca;


public class SubCategoriaEmpresaActivity extends AppCompatActivity {

    private static final String CATEGORIA_EMPRESA = "categoria_empresa";
    private static final String GSONCATEGORIA_EMPRESA = "gsonCategoria";

    private CategoriaEmpresa categoriaEmpresa;
    private GsonCategoriaEmpresa gsonCategoriaEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria_empresa);

        reciveData();

        Toolbar toolbar =  findViewById(R.id.toolbar_2);
        TextView nombre_categoria = findViewById(R.id.nombre_categoria);

        nombre_categoria.setText(categoriaEmpresa.getNombre_categoria());


        setSupportActionBar(toolbar);

        fragments_send_data(categoriaEmpresa.getIdCategoriaEmpresa());

        toolbar.setNavigationOnClickListener(v->{onBackPressed();});

        TextView ubicacion_enable = findViewById(R.id.ubicacion_enable);
        ubicacion_enable.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.menu_subcategoria,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_share);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent= SearchActivity.newIntent(SubCategoriaEmpresaActivity.this,gsonCategoriaEmpresa);
                startActivity(intent);
               // finish();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }



    public void fragments_send_data(int idCategoria){



        FragmentSubCategoria fragment =FragmentSubCategoria.newIntent(gsonCategoriaEmpresa,idCategoria);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_sub_categoria,fragment).commit();


        FragmentUbicacionCerca fragment1 = (FragmentUbicacionCerca) getSupportFragmentManager().findFragmentById(R.id.fragment_ubicacion_cerca);
        if(fragment1 !=null){
            fragment1.setIdCategoria(idCategoria);
        }



        FragmentEmpresaSort fragment2 = (FragmentEmpresaSort) getSupportFragmentManager().findFragmentById(R.id.fragment_empresa_sort);
        if(fragment2 !=null){
            fragment2.setIdCategoria(idCategoria);
        }
    }




    public static Intent newIntentSubCategoriaActivity(Context context, CategoriaEmpresa categoriaEmpresa,GsonCategoriaEmpresa gsonCategoriaEmpresa){
        Intent intent= new Intent(context, SubCategoriaEmpresaActivity.class);
        intent.putExtra(CATEGORIA_EMPRESA,categoriaEmpresa);
        intent.putExtra(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);
        return intent;
    }


    private  void reciveData(){
        if(getIntent().getSerializableExtra(CATEGORIA_EMPRESA)!=null){
            categoriaEmpresa=(CategoriaEmpresa) getIntent().getSerializableExtra(CATEGORIA_EMPRESA);
        }

        if(getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA)!=null){
            gsonCategoriaEmpresa=(GsonCategoriaEmpresa) getIntent().getSerializableExtra(GSONCATEGORIA_EMPRESA);
        }
       // idusuario=getIntent().getIntExtra(ID_USUARIO,0);
    }


}
