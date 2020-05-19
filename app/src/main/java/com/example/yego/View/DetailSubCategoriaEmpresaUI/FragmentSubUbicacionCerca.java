package com.example.yego.View.DetailSubCategoriaEmpresaUI;

import android.os.Bundle;

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


public class FragmentSubUbicacionCerca extends Fragment {

    private SubUbicacionCercaResultsAdapter adapter;
    private EmpresaViewModel viewModel;




    public FragmentSubUbicacionCerca() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new SubUbicacionCercaResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init2();
        viewModel.getListafindByLocationSubCategoriaLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                if(gsonCategoriaEmpresa !=null){
                    adapter.setSubUbicacionCercaAdpater(gsonCategoriaEmpresa.getListaEmpresa());
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sub_ubicacion_cerca, container, false);

        RecyclerView recyclerView=view.findViewById(R.id.ubicacion_sub_categoria_cerca_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setIdCategoria(int idSubCategoria){
        viewModel.searchListafindByLocationSubCategoria(idSubCategoria,"Barranco");

    }


}
