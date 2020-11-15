package com.example.yego.View.SubCategoriaEmpresaUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonSubCategoriaEmpresa;
import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.example.yego.View.DetailSubCategoriaEmpresaActivity;
import com.example.yego.View.SubCategoriaEmpresaActivity;
import com.example.yego.ViewModel.SubCategoriaEmpresaViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubCategoria extends Fragment implements  SubCategoriaResultsAdapter.OnNoteListener {

    public static final  String INTENT_OBJECT_FRAGMENT_SUBCATEGORIA="objeto";

    private static final String IDCATEGORIA = "idcategoria";

    private static final String GSONCATEGORIA_EMPRESA = "gsonCategoria";

    private SubCategoriaEmpresaViewModel viewModel;
    private SubCategoriaResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private int idCategoria;
    private GsonCategoriaEmpresa categoriaEmpresa;


    public FragmentSubCategoria() {
        // Required empty public constructor
    }


    public static FragmentSubCategoria newIntent(GsonCategoriaEmpresa gsonCategoriaEmpresa,int idcategoria){
        FragmentSubCategoria fragment=new FragmentSubCategoria();
        Bundle bundle=new Bundle();
        bundle.putSerializable(GSONCATEGORIA_EMPRESA,gsonCategoriaEmpresa);
        bundle.putInt(IDCATEGORIA,idcategoria);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new SubCategoriaResultsAdapter();

        viewModel = new ViewModelProvider(this).get(SubCategoriaEmpresaViewModel.class);
        viewModel.init();
        viewModel.getSubCategoiraEmpresaLiveData().observe(this, new Observer<GsonSubCategoriaEmpresa>() {
            @Override
            public void onChanged(GsonSubCategoriaEmpresa gsonCategoriaEmpresa) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonCategoriaEmpresa !=null){
                    adapter.setSubCategoriaResultsAdapter(gsonCategoriaEmpresa.getListaSubcategoriaEmpresa(),FragmentSubCategoria.this::onNoteClick);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_sub_categoria, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container_empresa_subcategoria_2);


        RecyclerView recyclerView=view.findViewById(R.id.Lista_SubCategoria_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        if(getArguments()!=null){

            Bundle bundle=getArguments();

            idCategoria=bundle.getInt(IDCATEGORIA);
            mShimmerViewContainer.startShimmerAnimation();

            categoriaEmpresa=(GsonCategoriaEmpresa) bundle.getSerializable(GSONCATEGORIA_EMPRESA);

            viewModel.searchSubCategoriaEmpresa(idCategoria);
        }




    }


    @Override
    public void onNoteClick(SubCategoriaEmpresa subCategoriaEmpresa) {

        Intent intent=DetailSubCategoriaEmpresaActivity.newIntentDetailSubCategoriaActivity(getContext(),subCategoriaEmpresa,categoriaEmpresa);
        startActivity(intent);
    }
}
