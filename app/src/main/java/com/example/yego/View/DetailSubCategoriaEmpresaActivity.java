package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.View.DetailSubCategoriaEmpresaUI.EmpresaSubCategoriaResultsAdapter;
import com.example.yego.View.DetailSubCategoriaEmpresaUI.FragmentEmpresaSubCategoria;
import com.example.yego.View.DetailSubCategoriaEmpresaUI.FragmentSubUbicacionCerca;
import com.example.yego.View.SubCategoriaEmpresaUI.FragmentSubCategoria;

public class DetailSubCategoriaEmpresaActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sub_categoria_empresa);

        int idSubCategoria= getIntent().getIntExtra(FragmentSubCategoria.INTENT_OBJECT_FRAGMENT_SUBCATEGORIA,1);


        FragmentSubUbicacionCerca fragmentSubUbicacionCerca = (FragmentSubUbicacionCerca) getSupportFragmentManager().findFragmentById(R.id.fragment_sub_categoria_cerca_detail);
        if(fragmentSubUbicacionCerca !=null){
            fragmentSubUbicacionCerca.setIdCategoria(idSubCategoria);
        }

        FragmentEmpresaSubCategoria fragmentEmpresaSubCategoria = (FragmentEmpresaSubCategoria) getSupportFragmentManager().findFragmentById(R.id.fragmen_total_subEmpresa);
        if(fragmentEmpresaSubCategoria !=null){
            fragmentEmpresaSubCategoria.setIdCategoria(idSubCategoria);
        }
    }



}
