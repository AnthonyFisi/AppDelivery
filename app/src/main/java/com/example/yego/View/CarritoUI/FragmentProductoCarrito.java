package com.example.yego.View.CarritoUI;


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
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.CarritoActivity;
import com.example.yego.ViewModel.ProductoJOINregistroPedidoJOINpedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductoCarrito extends Fragment implements ListaProductoCarritoResultsAdapter.ItemClickWidget {

    private  ListaProductoCarritoResultsAdapter adapter;
    private ProductoJOINregistroPedidoJOINpedidoViewModel viewModel;
    private List<ProductoJOINregistroPedidoJOINpedido> listaEmpresa= new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;


    public FragmentProductoCarrito() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter= new ListaProductoCarritoResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(ProductoJOINregistroPedidoJOINpedidoViewModel.class);
        viewModel.init();
        viewModel.getProductoJOINregistroPedidoJOINpedidoLiveData().observe(this, new Observer<GsonProductoJOINregistroPedidoJOINpedido>() {
            @Override
            public void onChanged(GsonProductoJOINregistroPedidoJOINpedido gsonProductoJOINregistroPedidoJOINpedido) {
                if (gsonProductoJOINregistroPedidoJOINpedido != null) {
                    adapter.setResults(gsonProductoJOINregistroPedidoJOINpedido.getListaProductoJOINregistroPedidoJOINpedido(), FragmentProductoCarrito.this);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_producto_carrito, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_lista_carrito_GENERAL);
        viewModel.searchProducto(Cliente_Bi.getCliente().getIdusuario(),mShimmerViewContainer);

        RecyclerView recyclerView=view.findViewById(R.id.producto_carrito_RECYCLERVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void itemClickCarritoByEmpresa(ProductoJOINregistroPedidoJOINpedido producto) {
        ///Intent intent= CarritoActivity.newIntentCarritoActivity2(getContext(),producto);
       // startActivity(intent);
    }


}
