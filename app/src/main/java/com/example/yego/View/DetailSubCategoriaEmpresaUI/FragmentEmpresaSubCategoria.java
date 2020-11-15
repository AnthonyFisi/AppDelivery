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
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.ViewModel.EmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEmpresaSubCategoria extends Fragment implements  EmpresaSubCategoriaResultsAdapter.OneNoteListener {

    public static final  String INTENT_NAME_FRAGMENT_EMPRESA_SUBCATEGORIA="com.example.yego.View.DetailSubCategoriaEmpresaUI";


    private EmpresaSubCategoriaResultsAdapter adapter;
    private EmpresaViewModel viewModel;
    private ShimmerFrameLayout mShimmerViewContainer;


    public FragmentEmpresaSubCategoria() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new EmpresaSubCategoriaResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(EmpresaViewModel.class);
        viewModel.init();
        viewModel.getListaEmpresanLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonCategoriaEmpresa) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
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

        mShimmerViewContainer=view.findViewById(R.id.shimmer_view_container_empresa_total_sub_categoria);
        mShimmerViewContainer.startShimmerAnimation();

        RecyclerView recyclerView=view.findViewById(R.id.Lista_Empresa_total_SubCategoria_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return  view;
    }


    public void setIdCategoria(int idSubCategoria){
        String ubicacion= Cliente_Bi.getCliente().getMaps_coordenada_x()+","+Cliente_Bi.getCliente().getMaps_coordenada_y();

        viewModel.searchListaSubcategoriaComplementaria(idSubCategoria,ubicacion);

    }

    @Override
    public void OneNoteClick(Empresa empresa) {
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
