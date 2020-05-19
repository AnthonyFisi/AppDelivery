package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.View.HomeUI.FragmentCategoria;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentEmpresaSort;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentSubCategoria;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentUbicacionCerca;

public class SubCategoriaEmpresaActivity extends AppCompatActivity {

    public static final String ARGUMENTOS_ID_CATEGORIA="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria_empresa);

        int idCategoria= getIntent().getIntExtra(FragmentCategoria.INTENT_NAME_FRAGMENT_CATEGORIA,1);



        FragmentSubCategoria fragment = (FragmentSubCategoria) getSupportFragmentManager().findFragmentById(R.id.fragment_sub_categoria);
        if(fragment !=null){
            fragment.setIdCategoria(idCategoria);
        }


        FragmentUbicacionCerca fragment1 = (FragmentUbicacionCerca) getSupportFragmentManager().findFragmentById(R.id.fragment_ubicacion_cerca);
        if(fragment1 !=null){
            fragment1.setIdCategoria(idCategoria);
        }



        FragmentEmpresaSort fragment2 = (FragmentEmpresaSort) getSupportFragmentManager().findFragmentById(R.id.fragment_empresa_sort);
        if(fragment2 !=null){
            fragment2.setIdCategoria(idCategoria);
        }

    }
}
