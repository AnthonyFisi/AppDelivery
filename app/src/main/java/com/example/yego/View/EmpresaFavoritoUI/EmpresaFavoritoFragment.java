package com.example.yego.View.EmpresaFavoritoUI;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.View.EmpresaDetailActivity;
import com.example.yego.ViewModel.EmpresaViewModel;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmpresaFavoritoFragment extends Fragment implements  EmpresaFavoritoResultsAdapter.ClickEmpresaFavorita{

    private RecyclerView recyclerview_empresa_favorito;

    private EmpresaFavoritoResultsAdapter adapter;

    private EmpresaViewModel viewModel;

    private LinearLayout empty_favorito;

    private ProgressBar progress_bar;

    public EmpresaFavoritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new EmpresaFavoritoResultsAdapter();
        viewModel=new ViewModelProvider(this).get(EmpresaViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empresa_favorito, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declararWidgets(view);
        setDataWidget();
        loadData();
        responseData();

    }

    private void declararWidgets(View view){
        recyclerview_empresa_favorito=view.findViewById(R.id.recyclerview_empresa_favorito);
        empty_favorito=view.findViewById(R.id.empty_favorito);
        progress_bar=view.findViewById(R.id.progress_bar);

    }

    private void setDataWidget(){
        recyclerview_empresa_favorito.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerview_empresa_favorito.setAdapter(adapter);
    }

    private void loadData(){
        viewModel.listaEmpresaFavorita(Cliente_Bi.getCliente().getIdusuario());
    }

    private void responseData(){
        viewModel.getListaEmpresanLiveData().observe(this, new Observer<GsonEmpresa>() {
            @Override
            public void onChanged(GsonEmpresa gsonEmpresa) {
                progress_bar.setVisibility(View.GONE);
                if(gsonEmpresa!=null){

                    adapter.setResults(gsonEmpresa.getListaEmpresa(),EmpresaFavoritoFragment.this);

                }else {

                    recyclerview_empresa_favorito.setVisibility(View.GONE);
                    empty_favorito.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), "Seguir intentando", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void clickEmpresa(Empresa empresa) {
        Intent intent= EmpresaDetailActivity.newIntentEmpresa(getContext(),empresa);
        startActivity(intent);
    }
}
