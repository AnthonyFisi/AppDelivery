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
import com.example.yego.ViewModel.SubCategoriaEmpresaViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEmpresaSort extends Fragment {


    private EmpresaViewModel viewModel;
    private EmpresaSortResultsAdapter adapter;


    public FragmentEmpresaSort() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new EmpresaSortResultsAdapter();

        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init1();
        viewModel.getListaSortfindByLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                if(gsonCategoriaEmpresa !=null){
                    adapter.setEmpresaSortAdpater(gsonCategoriaEmpresa.getListaEmpresa());
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_empresa_sort, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.empresa_sort_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;

    }



    public void setIdCategoria(int idCategoria){
        viewModel.searchListaSortfindBy(idCategoria);

    }
}
