package com.example.yego.View.SubCategoriaEmpresaUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.ViewModel.EmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUbicacionCerca extends Fragment implements  EmpresaResultsAdapter.ImageListener {


    private EmpresaViewModel viewModel;
    private EmpresaResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new EmpresaResultsAdapter();

        viewModel = new ViewModelProvider(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListaEmpresanLiveData().observe(this, gsonCategoriaEmpresa -> {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            if(gsonCategoriaEmpresa !=null){
                adapter.setEmpresaAdpater(gsonCategoriaEmpresa.getListaEmpresa(), FragmentUbicacionCerca.this);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_ubicacion_cerca, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.ubicacion_cerca_RecyclerView);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_ubicacion_cerca_2);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setIdCategoria(int idCategoria){
        String coordenada= Ubicacion.ubicacionEnable.getMaps_coordenada_x()+","+Ubicacion.ubicacionEnable.getMaps_coordenada_y();
        viewModel.searchListafindByLocation(idCategoria,coordenada);

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
        Intent intent=EmpresaDetailActivity.newIntentEmpresa(getContext(),empresa);
        startActivity(intent);
    }
}
