package com.example.yego.View.ProductDetailUI;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.EmpresaDetailUI.Tabs.InicioFragment;
import com.example.yego.View.HorarioUI.FragmentListaHorario;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FragmentProductosRelacionados extends Fragment implements ProductoRelacionadosResultsAdapter.OneNoteListener{

    private static final String ARG_PARAM1 = "param1";

    private ProductoViewModel viewModel;
    private ProductoRelacionadosResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    private Producto mProducto;

    private OnDataPass dataPasser;

    private RecyclerView recyclerView;
    private MediaPlayer mediaPlayer;


    public FragmentProductosRelacionados() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        adapter=new ProductoRelacionadosResultsAdapter();

        viewModel = ViewModelProviders.of(this).get(ProductoViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.fragment_productos_relacionados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_productos_relacionados);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView=view.findViewById(R.id.fragment_productos_relacionados_RecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        data();

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.soundorden);

    }

    public void passData(String cantidadTotal, String costoTotal) {
        dataPasser.onDataPass(cantidadTotal,costoTotal);

    }

    private void data(){
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonProducto !=null){

                    List<Producto> list=new ArrayList<>();
                    for(Producto producto:gsonProducto.getListaProducto()){
                        if(mProducto.getIdproducto()!=producto.getIdproducto()){
                            list.add(producto);
                        }
                    }

                    adapter.setProductoAdapter(list, FragmentProductosRelacionados.this,getContext());
                    recyclerView.setAdapter(adapter);

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        dataPasser = (FragmentProductosRelacionados.OnDataPass) context;
    }

    public void setProducto(Producto producto){

        this.mProducto=producto;
        viewModel.searchProducto(producto.getIdcategoriaproducto(),producto.getIdempresa());
    }




    @Override
    public Bundle añadirProducto(Producto producto) {
        Bundle bundle=new Bundle();
        ProductoJOINregistroPedidoJOINpedido objeto= new ProductoJOINregistroPedidoJOINpedido();
        objeto=objeto.existeObjeto(producto.getIdproducto());

        if(objeto !=null){

            //Existe producto

            ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
            RegistroPedidoViewModel registroPedidoViewModel;
            registroPedidoViewModel = ViewModelProviders.of(FragmentProductosRelacionados.this).get(RegistroPedidoViewModel.class);
            registroPedidoViewModel.init();
            MainPedido mainPedido=new MainPedido(
                    producto.getIdproducto(),
                    Cliente_Bi.getCliente().getIdusuario(),
                    producto.getIdempresa(),
                    producto.getProducto_precio(),
                    1,
                    "");
            registroPedidoViewModel.incrementarProducto(mainPedido,getContext());

            int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()+1;

            float costoTotal=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()+producto.getProducto_precio();

            p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);

            p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);

            //ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1;
           // ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio();


            bundle.putBoolean("agregado",false);
            bundle.putInt("cantidad",cantidad);

            int total=  ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
            float precioTotal=  ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());
            //soundEffect();
            passData(String.valueOf(total),String.valueOf(precioTotal));
        }else{

            //No existe producto
            PedidoViewModel pedidoViewModel;
            pedidoViewModel = ViewModelProviders.of(FragmentProductosRelacionados.this).get(PedidoViewModel.class);
            pedidoViewModel.init();
            MainPedido mainPedido=new MainPedido(
                    producto.getIdproducto(),
                    Cliente_Bi.getCliente().getIdusuario(),
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
            int total=  ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
            float precioTotal=  ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());

            passData(String.valueOf(total),String.valueOf(precioTotal));
            bundle.putBoolean("agregado",true);
            bundle.putInt("cantidad",1);



        }

        adapter.notifyDataSetChanged();
        soundEffect();

        return bundle;
    }


    public interface OnDataPass {
        void onDataPass(String cantidadTotal,String costoTotal);
    }

    private void soundEffect(){
        mediaPlayer.start();

        // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }



}
