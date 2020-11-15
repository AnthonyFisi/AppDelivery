package com.example.yego.View.EmpresaDetailUI.BuscadorUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Modelo.Word;
import com.example.yego.View.CarritoActivity;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.DetailCategoriaActivity;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.ProductoResultsAdapter3;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoActivity extends AppCompatActivity implements  ProductoResultsAdapter3.OneNoteListener2 {

    private static final String DATA_EMPRESA = "data_empresa";
    private static final String WORD_SEARCH ="word" ;
    private static final String LISTA_CATEGORIA = "lista_categoria";

    private Empresa empresa;

    private ProductoResultsAdapter3 adapter;
    private Toolbar mToolbar;

    private TextView activity_resultado_PALABRA_CLAVE,activity_resultado_CANTIDAD_RELACIONADO,cantidad_total_producto_carrito;

    private  RecyclerView recyclerView;

    private ProductoViewModel viewModel;

    private Word word;
    private ShimmerFrameLayout mShimmerViewContainer;

    private ArrayList<Categoria_producto_empresa> listaCategoria;

    private LinearLayout id_resultado_vacio,id_resultado_categoria,carrito_activity_empresa_detail;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        mToolbar=findViewById(R.id.toolbar_resultado);
        setSupportActionBar(mToolbar);
        mToolbar.setSubtitle(Ubicacion.ubicacionEnable.getUbicacion_nombre());

        mToolbar.setNavigationOnClickListener(v-> onBackPressed());

        reciveData();

        declararWidget();

        setWidget();

        initData();

        cartEmptyOrFull();

        mediaPlayer = MediaPlayer.create(ResultadoActivity.this, R.raw.soundorden);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.search,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent= BuscadorActivity.newIntentBuscadorActivity(getApplicationContext(),listaCategoria,empresa);
                startActivity(intent);
                finish();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }


    public static Intent newIntentResultadoActivity(Context context, Empresa empresa, Word word, List<Categoria_producto_empresa> listaCategoria){
        Intent intent= new Intent(context, ResultadoActivity.class);
        intent.putExtra(DATA_EMPRESA,empresa);
        intent.putExtra(LISTA_CATEGORIA,(Serializable) listaCategoria);
        intent.putExtra(WORD_SEARCH,word);
        return intent;
    }


    private  void reciveData(){

        if(getIntent().getSerializableExtra(DATA_EMPRESA)!=null){
            empresa=(Empresa) getIntent().getSerializableExtra(DATA_EMPRESA);
        }

        if(getIntent().getSerializableExtra(LISTA_CATEGORIA)!=null){
            listaCategoria= (ArrayList<Categoria_producto_empresa>) getIntent().getSerializableExtra(LISTA_CATEGORIA);
        }

        if(getIntent().getSerializableExtra(WORD_SEARCH)!=null){
            word=(Word) getIntent().getSerializableExtra(WORD_SEARCH);
        }
    }


    void initData(){
        adapter=new ProductoResultsAdapter3();
        viewModel=new ViewModelProvider(this).get(ProductoViewModel.class);
        viewModel.init();
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonProducto!=null){
                    id_resultado_categoria.setVisibility(View.VISIBLE);
                    id_resultado_vacio.setVisibility(View.GONE);

                    Toast.makeText(ResultadoActivity.this,"SIIIIIII HAY DATA",Toast.LENGTH_LONG).show();
                    adapter.setProductoAdapter3(gsonProducto.getListaProducto(),ResultadoActivity.this);
                    String relacionado=gsonProducto.getListaProducto().size()+" productos relacionados";
                    activity_resultado_CANTIDAD_RELACIONADO.setText(relacionado);
                    recyclerView.setAdapter(adapter);

                }else{

                    Toast.makeText(ResultadoActivity.this,"NO HAY DATA",Toast.LENGTH_LONG).show();
                        id_resultado_categoria.setVisibility(View.GONE);
                        id_resultado_vacio.setVisibility(View.VISIBLE);



                }
            }
        });


        viewModel.searhByWord(word.getIdempresa(),word.getWord());
    }


    void declararWidget(){


       // activity_resultado_PALABRA_CLAVE
        activity_resultado_PALABRA_CLAVE=findViewById(R.id.activity_resultado_PALABRA_CLAVE);

        //PRODUCTO_RELACIONADO
        activity_resultado_CANTIDAD_RELACIONADO=findViewById(R.id.activity_resultado_CANTIDAD_RELACIONADO);

        recyclerView=findViewById(R.id.recyclerView_ACTIVITY_RESULTADO);

        cantidad_total_producto_carrito=findViewById(R.id.cantidad_total_producto_carrito_2);



        id_resultado_categoria=findViewById(R.id.id_resultado_categoria);

        id_resultado_vacio=findViewById(R.id.id_resultado_vacio);


        mShimmerViewContainer =findViewById(R.id.shimmer_detail_categoria);
        mShimmerViewContainer.startShimmerAnimation();

        carrito_activity_empresa_detail=findViewById(R.id.carrito_activity_empresa_detail);
    }

    void setWidget(){

        String palabra="Resultado de "+word.getWord();

        activity_resultado_PALABRA_CLAVE.setText(palabra);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));


        cartEmptyOrFull();

        clickButtonCarrito();

    }


    private void clickButtonCarrito(){
        carrito_activity_empresa_detail.setOnClickListener(view -> {
            Intent intent= CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa);
            startActivity(intent);
        });

    }



    private void cartEmptyOrFull(){
        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa()) >0){
            carrito_activity_empresa_detail.setVisibility(View.VISIBLE);
            cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
           // costo_total_empresa_detail.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        }else {

            carrito_activity_empresa_detail.setVisibility(View.GONE);
        }
    }



    @Override
    public void OneNoteClick(Producto producto) {
        Intent intent= ProductDetailActivity.newIntentProductDetail(getApplicationContext(),producto,empresa,listaCategoria);
        startActivity(intent);
        finish();
    }






    @Override
    public int añadirProducto(Producto producto) {

        PedidoViewModel pedidoViewModel;
        pedidoViewModel = ViewModelProviders.of(this).get(PedidoViewModel.class);
        pedidoViewModel.init();



        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                ""
        );



        pedidoViewModel.insertarPedido(mainPedido,getApplicationContext());

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


        cantidad_total_producto_carrito.setText(String.valueOf(totalProductoByEmpresa));

        cartEmptyOrFull();


        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        soundEffect();

        //passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        return total;

    }

    @Override
    public int disminuirProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");
        registroPedidoViewModel.disminuirProducto(mainPedido,getApplicationContext());
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



        cantidad_total_producto_carrito.setText(String.valueOf(totalProductoByEmpresa));

        cartEmptyOrFull();

        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        soundEffect();
        //  passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        return cantidad;



    }

    @Override
    public int incrementarProducto(Producto producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido=new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");
        registroPedidoViewModel.incrementarProducto(mainPedido,getApplicationContext());

        int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()+1;
        float costoTotal=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()+producto.getProducto_precio();

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);



        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());



        cantidad_total_producto_carrito.setText(String.valueOf(totalProductoByEmpresa));


        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        //passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        cartEmptyOrFull();
        soundEffect();

        return cantidad;

    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        cartEmptyOrFull();
    }

    private void soundEffect(){
        mediaPlayer.start();

        // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }
}
