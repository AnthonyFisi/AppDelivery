package com.example.yego.View.SubCategoriaEmpresaUI;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.ViewModel.EmpresaViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUbicacionCerca extends Fragment {


    private EmpresaViewModel viewModel;
    private EmpresaResultsAdapter adapter;

    public FragmentUbicacionCerca() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Bundle bundle = getArguments();
        if(bundle != null){
            idCategoria=bundle.getInt(SubCategoriaEmpresaActivity.ARGUMENTOS_ID_CATEGORIA,200);
        }else{
            idCategoria=1;
        }*/
        //

        adapter = new EmpresaResultsAdapter();

        // viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListafindByLocationLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                if(gsonCategoriaEmpresa !=null){
                    adapter.setEmpresaAdpater(gsonCategoriaEmpresa.getListaEmpresa());
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ubicacion_cerca, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.ubicacion_cerca_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setIdCategoria(int idCategoria){
        viewModel.searchListafindByLocation(idCategoria,"Barranco");

    }

}
