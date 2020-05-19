package com.example.yego.View.SubCategoriaEmpresaUI;


import android.content.Intent;
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
import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.example.yego.View.DetailSubCategoriaEmpresaActivity;
import com.example.yego.ViewModel.SubCategoriaEmpresaViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubCategoria extends Fragment implements  SubCategoriaResultsAdapter.OnNoteListener {

    public static final  String INTENT_OBJECT_FRAGMENT_SUBCATEGORIA="objeto";


    private SubCategoriaEmpresaViewModel viewModel;
    private SubCategoriaResultsAdapter adapter;
    //int idCategoria=0;


    public FragmentSubCategoria() {
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

        adapter = new SubCategoriaResultsAdapter();

        // viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        viewModel = ViewModelProviders.of(this).get(SubCategoriaEmpresaViewModel.class);
        viewModel.init();
        viewModel.getSubCategoiraEmpresaLiveData().observe(this, new Observer<GsonSubCategoriaEmpresa>() {
            @Override
            public void onChanged(GsonSubCategoriaEmpresa gsonCategoriaEmpresa) {
                if(gsonCategoriaEmpresa !=null){
                    adapter.setSubCategoriaResultsAdapter(gsonCategoriaEmpresa.getListaSubcategoriaEmpresa(),FragmentSubCategoria.this::onNoteClick);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_sub_categoria, container, false);
        View view= inflater.inflate(R.layout.fragment_sub_categoria, container, false);

       // mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        //viewModel.searchSubCategoriaEmpresa(idCategoria);


        RecyclerView recyclerView=view.findViewById(R.id.Lista_SubCategoria_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);


        

        return view;
    }

    public void setIdCategoria(int idCategoria){
        viewModel.searchSubCategoriaEmpresa(idCategoria);
    }

    @Override
    public void onNoteClick(SubCategoriaEmpresa position) {
        Intent intent=new Intent(FragmentSubCategoria.this.getContext(), DetailSubCategoriaEmpresaActivity.class);
        intent.putExtra(INTENT_OBJECT_FRAGMENT_SUBCATEGORIA,position.getIdSubCategoriaEmpresa());
        startActivity(intent);
    }
}
