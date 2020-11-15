package com.example.yego.View.DetailSubCategoriaEmpresaUI;

import android.content.Intent;
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
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.ViewModel.EmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;


public class FragmentSubUbicacionCerca extends Fragment implements  SubUbicacionCercaResultsAdapter.OneNoteListener{

    private SubUbicacionCercaResultsAdapter adapter;
    private EmpresaViewModel viewModel;
    private ShimmerFrameLayout mShimmerViewContainer;




    public FragmentSubUbicacionCerca() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new SubUbicacionCercaResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListaEmpresanLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonCategoriaEmpresa !=null){
                    adapter.setSubUbicacionCercaAdpater(gsonCategoriaEmpresa.getListaEmpresa(),FragmentSubUbicacionCerca.this::oneNoteClick);
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

        mShimmerViewContainer=view.findViewById(R.id.shimmer_view_container_ubicacion_cerca_2);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setIdCategoria(int idSubCategoria){
        String coordenada= Ubicacion.ubicacionEnable.getMaps_coordenada_x()+","+Ubicacion.ubicacionEnable.getMaps_coordenada_y();
        viewModel.searchListafindByLocationSubCategoria(idSubCategoria,coordenada);

    }


    @Override
    public void oneNoteClick(Empresa empresa) {
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(getContext(),empresa);
        startActivity(intent);
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
}
