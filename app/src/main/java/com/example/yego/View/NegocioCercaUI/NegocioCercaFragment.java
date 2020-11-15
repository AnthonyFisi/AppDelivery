package com.example.yego.View.NegocioCercaUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class NegocioCercaFragment extends Fragment implements  NegocioCercaResultsAdapter.ImageListener{

    private EmpresaViewModel viewModel;
    private NegocioCercaResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    public NegocioCercaFragment() {
    }



    public static NegocioCercaFragment newInstance(String param1, String param2) {
        return new NegocioCercaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new NegocioCercaResultsAdapter();

        viewModel = new ViewModelProvider(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListaEmpresanLiveData().observe(this, gsonCategoriaEmpresa -> {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            if(gsonCategoriaEmpresa !=null){
                adapter.setEmpresaAdpater(gsonCategoriaEmpresa.getListaEmpresa(), NegocioCercaFragment.this);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_negocio_cerca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=view.findViewById(R.id.ubicacion_cerca_RecyclerView);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_ubicacion_cerca_2);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        String coordenada= Ubicacion.ubicacionEnable.getMaps_coordenada_x()+","+Ubicacion.ubicacionEnable.getMaps_coordenada_y();
        viewModel.searchListaTotalcerca(coordenada);
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
        Toast.makeText(getContext(),"empresa nombre"+empresa.getNombredueno_empresa(),Toast.LENGTH_LONG).show();
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(getContext(),empresa);
        startActivity(intent);
    }
}
