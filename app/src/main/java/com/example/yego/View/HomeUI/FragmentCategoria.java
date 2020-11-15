package com.example.yego.View.HomeUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.View.SubCategoriaEmpresaActivity;
import com.example.yego.ViewModel.CategoriaEmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategoria extends Fragment implements  CategoriaResultsAdapter.OnNoteListener {

    public static final  String INTENT_NAME_FRAGMENT_CATEGORIA="idCategoria";

    private CategoriaEmpresaViewModel viewModel;
    private CategoriaResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;


    public FragmentCategoria() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CategoriaResultsAdapter();

        // viewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        viewModel = ViewModelProviders.of(this).get(CategoriaEmpresaViewModel.class);
        viewModel.init();
        viewModel.getCategoiraEmpresaLiveData().observe(this, new Observer<GsonCategoriaEmpresa>() {
            @Override
            public void onChanged(GsonCategoriaEmpresa gsonCategoriaEmpresa) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonCategoriaEmpresa !=null){
                    CategoriaEmpresa.listaFiltro=gsonCategoriaEmpresa.getListaCategoriaEmpresa();
                    adapter.setResults(gsonCategoriaEmpresa.getListaCategoriaEmpresa(),FragmentCategoria.this::onNoteClick);
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_categoria, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        viewModel.searchCategoriaEmpresa();

        RecyclerView recyclerView=view.findViewById(R.id.recycler_view_categoria);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapter);


        return view;


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
    public void onNoteClick(CategoriaEmpresa categoriaEmpresa) {

    //    Intent intent=SubCategoriaEmpresaActivity.newIntentSubCategoriaActivity(getContext(),categoriaEmpresa,);
      //  startActivity(intent);

    }
}
