package com.example.yego.View.EmpresaDetailUI;

import android.content.Context;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonNombreSubCategoria;
import com.example.yego.Repository.Modelo.NombreSubCategoria;
import com.example.yego.View.EmpresaDetailUI.Tabs.Categoria1Fragment;
import com.example.yego.View.EmpresaDetailUI.Tabs.OfertasFragment;
import com.example.yego.View.EmpresaDetailUI.Tabs.InicioFragment;
import com.example.yego.ViewModel.NombreSubCategoriaViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProductosTabsPagerAdapter extends FragmentPagerAdapter {

    private Empresa empresa;
    private NombreSubCategoriaViewModel viewModel;
    private List<Categoria_producto_empresa> listaNombresCategoria;

    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };


    public ProductosTabsPagerAdapter( @NonNull FragmentManager fm,Empresa empresa,List<Categoria_producto_empresa> lista) {
        super(fm);
        this.empresa=empresa;
        this.listaNombresCategoria=lista;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        return new Categoria1Fragment(listaNombresCategoria.get(position).getIdcategoriaproductoempresa(),empresa,listaNombresCategoria.get(position).getNombre(),listaNombresCategoria);

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return listaNombresCategoria.get(position).getNombre();

    }

    @Override
    public int getCount() {
        return listaNombresCategoria.size();
    }


}
