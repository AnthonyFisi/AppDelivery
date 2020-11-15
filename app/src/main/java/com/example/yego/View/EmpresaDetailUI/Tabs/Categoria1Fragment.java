package com.example.yego.View.EmpresaDetailUI.Tabs;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.DetailCategoriaActivity;
import com.example.yego.View.EmpresaDetailUI.ProductoResultsAdapter;
import com.example.yego.View.EmpresaDetailUI.ProductoResultsAdapter2;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Categoria1Fragment extends Fragment  implements ProductoResultsAdapter2.OneNoteListener2{


    private final List<Categoria_producto_empresa> listaCategoria;
    private int idCategoria;
    private Empresa empresa;
    private ProductoViewModel viewModel;
    private ProductoResultsAdapter2 adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private OnDataPass dataPasser;

    private MediaPlayer mediaPlayer;


    public Categoria1Fragment(int idCategoria, Empresa empresa, String nombreCategoria, List<Categoria_producto_empresa> listaCategoria) {
        this.idCategoria=idCategoria;
        this.empresa=empresa;
        this.listaCategoria=listaCategoria;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ProductoResultsAdapter2();
        viewModel =new ViewModelProvider(this).get(ProductoViewModel.class);
        viewModel.init();
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonProducto !=null){
                    passDataLoad(true);

                    passDataCategoria1(gsonProducto.getListaProducto());

                    adapter.setProductoAdapter2(gsonProducto.getListaProducto(),  Categoria1Fragment.this);
                }else {
                    passDataLoad(false);
                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categoria1, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_contacs);
        mShimmerViewContainer.startShimmerAnimation();
        RecyclerView recyclerView=view.findViewById(R.id.contacts_RecyclerView);

        viewModel.searchProducto(idCategoria,empresa.getIdempresa());


        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        recyclerView.setAdapter(adapter);


        mediaPlayer = MediaPlayer.create(getContext(), R.raw.soundorden);

        return view;
    }



     private void passData(String cantidadTotal, String costoTotal) {
        dataPasser.onDataPass(cantidadTotal,costoTotal);
    }

    private void passDataLoad(boolean rpta){
        dataPasser.loadData(rpta);
    }

    private void passDataCategoria1(List<Producto> lista){
        dataPasser.dataCategoria1(lista);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.searchProducto(idCategoria,empresa.getIdempresa());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void OneNoteClick(Producto producto) {
        Intent intent= ProductDetailActivity.newIntentProductDetail(getContext(),producto,empresa,listaCategoria);
        startActivity(intent);
    }






    @Override
    public int añadirProducto(Producto producto) {

        PedidoViewModel pedidoViewModel;
        pedidoViewModel = ViewModelProviders.of(Categoria1Fragment.this).get(PedidoViewModel.class);
        pedidoViewModel.init();



        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                ""
                );



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

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());

        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());

        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));
       // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        soundEffect();

        return total;

    }

    @Override
    public int disminuirProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(Categoria1Fragment.this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
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


      //  passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto-=producto.getProducto_precio()));
        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());





        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        soundEffect();

        return cantidad;



    }

    @Override
    public int incrementarProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(Categoria1Fragment.this).get(RegistroPedidoViewModel.class);
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



       // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());





        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        soundEffect();
        return cantidad;

    }


    public interface OnDataPass {
        void onDataPass(String cantidadTotal,String costoTotal);
        void loadData(boolean rpta);
        void dataCategoria1(List<Producto> results);
    }



    private ProductoJOINregistroPedidoJOINpedido generateObject(Producto producto){

        return  null;
    }

    private void soundEffect(){
        mediaPlayer.start();

       // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }
}
