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
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.ViewModel.EmpresaViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEmpresaSubCategoria extends Fragment implements  EmpresaSubCategoriaResultsAdapter.OneNoteListener {

    private EmpresaSubCategoriaResultsAdapter adapter;
    private EmpresaViewModel viewModel;


    public FragmentEmpresaSubCategoria() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new EmpresaSubCategoriaResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init3();
        viewModel. getsearchListafindByLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                if(gsonCategoriaEmpresa !=null){
                    adapter.setEmpresaSubCategoriaAdpater(gsonCategoriaEmpresa.getListaEmpresa(),FragmentEmpresaSubCategoria.this::OneNoteClick);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_empresa_total_sub_categoria, container, false);


        RecyclerView recyclerView=view.findViewById(R.id.Lista_Empresa_total_SubCategoria_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return  view;
    }


    public void setIdCategoria(int idSubCategoria){
        viewModel.searchListafindBy(idSubCategoria);

    }

    @Override
    public void OneNoteClick(Empresa empresa) {
        Toast.makeText(getContext(),"empresa nombre"+empresa.getNombredueno_empresa(),Toast.LENGTH_LONG).show();
    }
}
