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
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.View.SubCategoriaEmpresaActivity;
import com.example.yego.ViewModel.EmpresaViewModel;
import com.example.yego.ViewModel.SubCategoriaEmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEmpresaSort extends Fragment implements  EmpresaSortResultsAdapter.ImageListener {


    private EmpresaViewModel viewModel;
    private EmpresaSortResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    //int idCategoria=0;



    public FragmentEmpresaSort() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new EmpresaSortResultsAdapter();

        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListaEmpresanLiveData().observe(this, gsonCategoriaEmpresa -> {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            if(gsonCategoriaEmpresa !=null){
                adapter.setEmpresaSortAdpater(gsonCategoriaEmpresa.getListaEmpresa(),FragmentEmpresaSort.this::imageClick);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* if(getArguments() !=null){
            idCategoria=getArguments().getInt(SubCategoriaEmpresaActivity.ARGUMENTOS_ID_CATEGORIA);

        }else{
            idCategoria=1500000;
        }*/
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_empresa_sort, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.empresa_sort_RecyclerView);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_empresa_sort);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;

    }



    public void setIdCategoria(int idCategoria){
       // this.idCategoria=idCategoria;
        String ubicacion= Cliente_Bi.getCliente().getMaps_coordenada_x()+","+Cliente_Bi.getCliente().getMaps_coordenada_y();
        viewModel.searchListaCategoriaComplementaria(idCategoria,ubicacion);

    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void imageClick(Empresa empresa) {
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(getContext(),empresa);
        startActivity(intent);
    }
}
