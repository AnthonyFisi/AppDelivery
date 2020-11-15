package com.example.yego.View.EmpresaDetailUI.Tabs;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Utils.PageViewModel;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.View.EmpresaDetailUI.ProductoResultsAdapter;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

public class OfertasFragment extends Fragment implements ProductoResultsAdapter.OneNoteListener {


    private int idCategoria;
    private Empresa empresa;
    private ProductoViewModel viewModel;
    private ProductoResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    OnDataPass dataPasser;

    public OfertasFragment(int idCategoria, Empresa empresa) {
        this.idCategoria=idCategoria;
        this.empresa=empresa;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ProductoResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(ProductoViewModel.class);
        viewModel.init();
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                if(gsonProducto !=null){

                    adapter.setProductoAdapter(gsonProducto.getListaProducto(), OfertasFragment.this);
                }else{

                    System.out.println("NO HAY DATOS");

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ofertas, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_recents);

        RecyclerView recyclerView=view.findViewById(R.id.recents_RecyclerView);
        viewModel.searchProducto(idCategoria,empresa.getIdempresa());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void passData(String cantidadTotal,String costoTotal) {
        dataPasser.onDataPass(cantidadTotal,costoTotal);
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void OneNoteClick(Producto producto) {

       // Toast.makeText(getContext(),"empresa nombre"+producto.getProducto_nombre(),Toast.LENGTH_LONG).show();
        //Intent intent= ProductDetailActivity.newIntentProductDetail(getContext(),producto,empresa,"");
        //startActivity(intent);
    }





    @Override
    public int añadirProducto(Producto producto) {

        PedidoViewModel pedidoViewModel;
        pedidoViewModel = ViewModelProviders.of(OfertasFragment.this).get(PedidoViewModel.class);
        pedidoViewModel.init();



        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                2,
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");



        pedidoViewModel.insertarPedido(mainPedido,getContext());











        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido
                (
                        producto.getIdproducto(),
                        producto.getIdempresa(),
                        producto.getProducto_nombre(),
                        producto.getProducto_precio(),
                        producto.getProducto_uriimagen(),
                        producto.getProducto_descuento(),
                        producto.getProducto_precio(),
                        producto.getProducto_descuento(),
                        1,
                        producto.getProducto_precio(),
                        10,
                        2,
                        false,
                        0,
                        0,
                        "",
                        0,
                        "",
                        "",
                        producto.getTipomenu()
                );

        ProductoJOINregistroPedidoJOINpedido.carrito.add(p);
        int total=1;


        passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));


        return total;

    }

    @Override
    public int disminuirProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(OfertasFragment.this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                2,
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");
        registroPedidoViewModel.disminuirProducto(mainPedido,getContext());
        int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()-1;
        float costo=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()-producto.getProducto_precio();
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costo);
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);



        if(cantidad==0){
            ProductoJOINregistroPedidoJOINpedido.carrito.remove(p.existeObjeto(producto.getIdproducto()));

        }


        passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto-=producto.getProducto_precio()));


        return cantidad;



    }

    @Override
    public int incrementarProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(OfertasFragment.this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                2,
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");
        registroPedidoViewModel.incrementarProducto(mainPedido,getContext());

        int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()+1;
        float costoTotal=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()+producto.getProducto_precio();

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);



        passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        return cantidad;

    }


    public interface OnDataPass {
        void onDataPass(String cantidadTotal,String costoTotal);
    }


}
