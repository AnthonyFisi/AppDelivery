package com.example.yego.View.EmpresaDetailUI.Tabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.DetailCategoriaActivity;
import com.example.yego.View.EmpresaDetailUI.ProductoResultsAdapter2;
import com.example.yego.View.EmpresaDetailUI.ProductosResultsAdapter4;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InicioFragment extends Fragment implements ProductoResultsAdapter2.OneNoteListener2 ,ProductosResultsAdapter4.ClickCategoria {


    private Empresa empresa;
    private ProductoViewModel viewModel_PRODUCTOS_POPULARES ;
    private ProductoResultsAdapter2 adapter_PRODUCTOS_POPULARES,adapter_CATEGORIA_1,adapter_CATEGORIA_2,adapter_CATEGORIA_3,adapter_CATEGORIA_4;
    private RecyclerView recyclerview_lista_categoria_INICIO,recyclerView_PRODUCTOS_POPULARES,recyclerView_CATEGORIA_1,recyclerView_CATEGORIA_2,recyclerView_CATEGORIA_3;
    private OnDataPass dataPasser;
    private TextView  title_PRODUCTOS_POPULARES,title_CATEGORIA_1,title_CATEGORIA_2,title_CATEGORIA_3;


    private List<Producto> lista_PRODUCTOS_POPULARES = new ArrayList<>(),
                    lista_CATEGORIA_1= new ArrayList<>(),
            lista_CATEGORIA_2= new ArrayList<>(),lista_CATEGORIA_3= new ArrayList<>(),lista_CATEGORIA_4= new ArrayList<>();
    private Producto producto;
    private List<Categoria_producto_empresa> listaCategoria;

    private ProductosResultsAdapter4 adapter4;

    private MediaPlayer mediaPlayer;


    public InicioFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter_PRODUCTOS_POPULARES=new ProductoResultsAdapter2();
        adapter_CATEGORIA_1=new ProductoResultsAdapter2();
        adapter_CATEGORIA_2=new ProductoResultsAdapter2();
        adapter_CATEGORIA_3=new ProductoResultsAdapter2();
        adapter_CATEGORIA_4=new ProductoResultsAdapter2();

        adapter4= new ProductosResultsAdapter4();

        viewModel_PRODUCTOS_POPULARES = new ViewModelProvider(this).get(ProductoViewModel.class);
        viewModel_PRODUCTOS_POPULARES.init();
        viewModel_PRODUCTOS_POPULARES.getProductoByEmpresaLiveData().observe(this, gsonProducto -> {
            if(gsonProducto !=null){


               loadDataListProductos(gsonProducto.getListaProducto());

               adapter4.setResults(listaCategoria,InicioFragment.this);
                adapter_PRODUCTOS_POPULARES.setProductoAdapter2(lista_PRODUCTOS_POPULARES, InicioFragment.this);
                adapter_CATEGORIA_1.setProductoAdapter2(lista_CATEGORIA_1, InicioFragment.this);
                adapter_CATEGORIA_2.setProductoAdapter2(lista_CATEGORIA_2, InicioFragment.this);
                adapter_CATEGORIA_3.setProductoAdapter2(lista_CATEGORIA_3, InicioFragment.this);
                adapter_CATEGORIA_4.setProductoAdapter2(lista_CATEGORIA_4, InicioFragment.this);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos_inicio, container, false);


        //Inicializando los recycler view de las categoria

        startRecyclerView(view);

        //Realizar llamado a los datos de la api de los productos segun categoria


        //Configurar el RecyclerView

        settingRecyclerView();



        mediaPlayer = MediaPlayer.create(getContext(), R.raw.soundorden);



        return view;
    }




    private void startRecyclerView(View view){

        title_PRODUCTOS_POPULARES= view.findViewById(R.id.title_PRODUCTOS_POPULARES);
        title_CATEGORIA_1 = view.findViewById(R.id.title_CATEGORIA_1);
        title_CATEGORIA_2 = view.findViewById(R.id.title_CATEGORIA_2);
        title_CATEGORIA_3 = view.findViewById(R.id.title_CATEGORIA_3);
      //  title_CATEGORIA_4 = view.findViewById(R.id.title_CATEGORIA_4);


        title_PRODUCTOS_POPULARES.setVisibility(View.GONE);
        title_CATEGORIA_1.setVisibility(View.GONE);
        title_CATEGORIA_2.setVisibility(View.GONE);
        title_CATEGORIA_3.setVisibility(View.GONE);
        //title_CATEGORIA_4.setVisibility(View.GONE);

        recyclerView_PRODUCTOS_POPULARES=view.findViewById(R.id.RecyclerView_PRODUCTOS_POPULARES);
       recyclerView_CATEGORIA_1=view.findViewById(R.id.RecyclerView_CATEGORIA_1);
       recyclerView_CATEGORIA_2=view.findViewById(R.id.RecyclerView_CATEGORIA_2);
       recyclerView_CATEGORIA_3=view.findViewById(R.id.RecyclerView_CATEGORIA_3);
      // recyclerView_CATEGORIA_4=view.findViewById(R.id.RecyclerView_CATEGORIA_4);

       recyclerView_PRODUCTOS_POPULARES.setVisibility(View.GONE);
       recyclerView_CATEGORIA_1.setVisibility(View.GONE);
       recyclerView_CATEGORIA_2.setVisibility(View.GONE);
       recyclerView_CATEGORIA_3.setVisibility(View.GONE);
      // recyclerView_CATEGORIA_4.setVisibility(View.GONE);

        recyclerview_lista_categoria_INICIO=view.findViewById(R.id.recyclerview_lista_categoria_INICIO);



    }

    private void settingRecyclerView(){



        recyclerView_PRODUCTOS_POPULARES.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_PRODUCTOS_POPULARES.setAdapter(adapter_PRODUCTOS_POPULARES);


        recyclerView_CATEGORIA_1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_CATEGORIA_1.setAdapter(adapter_CATEGORIA_1);


        recyclerView_CATEGORIA_2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_CATEGORIA_2.setAdapter(adapter_CATEGORIA_2);


        recyclerView_CATEGORIA_3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_CATEGORIA_3.setAdapter(adapter_CATEGORIA_3);


       // recyclerView_CATEGORIA_4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerView_CATEGORIA_4.setAdapter(adapter_CATEGORIA_4);


        recyclerview_lista_categoria_INICIO.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_lista_categoria_INICIO.setAdapter(adapter4);

    }


    private void loadDataListProductos(List<Producto> lista){

        List<Categoria_producto_empresa> list=new ArrayList<>();
        for(Categoria_producto_empresa categoria:listaCategoria){

            if(categoria.getIdcategoriaproductoempresa()!=producto.getIdcategoriaproducto()){
                list.add(categoria);
            }
        }


        for(Producto producto:lista){


            if(producto.getIdcategoriaproducto()==list.get(0).getIdcategoriaproductoempresa()){

                completeNameCategoria(title_PRODUCTOS_POPULARES,producto);

                title_PRODUCTOS_POPULARES.setVisibility(View.VISIBLE);
                recyclerView_PRODUCTOS_POPULARES.setVisibility(View.VISIBLE);
                lista_PRODUCTOS_POPULARES.add(producto);
                producto.getIdcategoriaproducto();

            }

            if(producto.getIdcategoriaproducto()==list.get(1).getIdcategoriaproductoempresa()){
                completeNameCategoria(title_CATEGORIA_1,producto);
               // title_CATEGORIA_1.setText(listaCategoria.get(1).getNombre());
                title_CATEGORIA_1.setVisibility(View.VISIBLE);

                recyclerView_CATEGORIA_1.setVisibility(View.VISIBLE);
                lista_CATEGORIA_1.add(producto);
            }

            if(producto.getIdcategoriaproducto()==list.get(2).getIdcategoriaproductoempresa()){
                completeNameCategoria(title_CATEGORIA_2,producto);

               // title_CATEGORIA_2.setText(listaCategoria.get(2).getNombre());
                title_CATEGORIA_2.setVisibility(View.VISIBLE);

                recyclerView_CATEGORIA_2.setVisibility(View.VISIBLE);
                lista_CATEGORIA_2.add(producto);

            }

            if(producto.getIdcategoriaproducto()==list.get(3).getIdcategoriaproductoempresa()){
                completeNameCategoria(title_CATEGORIA_3,producto);

               // title_CATEGORIA_3.setText(listaCategoria.get(3).getNombre());
                title_CATEGORIA_3.setVisibility(View.VISIBLE);
                recyclerView_CATEGORIA_3.setVisibility(View.VISIBLE);
                lista_CATEGORIA_3.add(producto);

            }

            /*if(producto.getIdcategoriaproducto()==5){
                lista_CATEGORIA_4.add(producto);

            }*/

        }
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
        adapter_PRODUCTOS_POPULARES.notifyDataSetChanged();
        adapter_CATEGORIA_1.notifyDataSetChanged();
        adapter_CATEGORIA_2.notifyDataSetChanged();
        adapter_CATEGORIA_3.notifyDataSetChanged();
        adapter_CATEGORIA_4.notifyDataSetChanged();


    }


    @Override
    public void OneNoteClick(Producto producto) {

        Intent intent=ProductDetailActivity.newIntentProductDetail(getContext(),producto,empresa,listaCategoria);
        startActivity(intent);
    }



    @Override
    public int añadirProducto(Producto producto) {
        PedidoViewModel pedidoViewModel;
        pedidoViewModel = ViewModelProviders.of(InicioFragment.this).get(PedidoViewModel.class);
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


        System.out.println("se agrego : " + ProductoJOINregistroPedidoJOINpedido.carrito.size());
        int total=1;

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());





       // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));
        soundEffect();
        return total;

    }

    @Override
    public int disminuirProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(InicioFragment.this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                ""
                );
        registroPedidoViewModel.disminuirProducto(mainPedido,getContext());
        int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()-1;

        float costoTotal=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()- producto.getProducto_precio();
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);



        if(cantidad==0){
            ProductoJOINregistroPedidoJOINpedido.carrito.remove(p.existeObjeto(producto.getIdproducto()));

        }

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        System.out.println("se agrego + : " + ProductoJOINregistroPedidoJOINpedido.carrito.size());




       // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto-=producto.getProducto_precio()));
        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        soundEffect();
        return cantidad;



    }

    @Override
    public int incrementarProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(InicioFragment.this).get(RegistroPedidoViewModel.class);
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

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


      //  passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));


        soundEffect();
        return cantidad;

    }

    public void setProducto(Producto producto, Empresa empresa, List<Categoria_producto_empresa> listaCategoria) {
        this.listaCategoria=listaCategoria;
        this.empresa=empresa;
        this.producto=producto;
        viewModel_PRODUCTOS_POPULARES.searchProductoByEmpresa(empresa.getIdempresa());

    }

    @Override
    public void itemClickCategoria(Categoria_producto_empresa categoria, List<Categoria_producto_empresa> listaCategoria) {

        Intent intent=DetailCategoriaActivity.newIntentDetailCategoriaActivity(getContext(),categoria,empresa,listaCategoria);
        startActivity(intent);
        getActivity().finish();

    }


    public interface OnDataPass {
         void onDataPass(String cantidadTotal,String costoTotal);
    }


    private void completeNameCategoria(TextView textView,Producto producto){
        if( textView.getText().toString().length()<=0){
            for(Categoria_producto_empresa nombre:listaCategoria){

                if(nombre.getIdcategoriaproductoempresa()==producto.getIdcategoriaproducto()){

                    textView.setText(nombre.getNombre());

                }

            }
        }
    }

    private void soundEffect(){
        mediaPlayer.start();
    }

    private void clickCategoriaDetalle(){

        title_PRODUCTOS_POPULARES.setOnClickListener(v-> {

        });

        title_CATEGORIA_1.setOnClickListener(v->{

        });

        title_CATEGORIA_2.setOnClickListener(v->{

        });

        title_CATEGORIA_3.setOnClickListener(v->{

        });
    }


}