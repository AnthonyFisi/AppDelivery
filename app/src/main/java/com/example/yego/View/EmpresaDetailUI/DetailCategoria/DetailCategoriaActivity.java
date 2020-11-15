package com.example.yego.View.EmpresaDetailUI.DetailCategoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.yego.View.CarritoActivity;
import com.example.yego.View.EmpresaDetailUI.BuscadorUI.BuscadorActivity;
import com.example.yego.View.EmpresaDetailUI.ProductoResultsAdapter;
import com.example.yego.View.EmpresaDetailUI.Tabs.Categoria1Fragment;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailCategoriaActivity extends AppCompatActivity implements  ProductoResultsAdapter3.OneNoteListener2{

    private static final String CATEGORIA = "idusuario";
    private static final String ID_EMPRESA = "idempresa";
    private static final String LISTA_CATEGORIA = "nombrecategoria";

    private Categoria_producto_empresa categoria_empresa;

    private Empresa empresa;

    private ProductoViewModel viewModel;

    private ProductoResultsAdapter3 adapter;

    private ArrayList<Categoria_producto_empresa> listaCategoria;

    private SearchView searchView;

    private Toolbar mToolbar;

    private ShimmerFrameLayout mShimmerViewContainer;

    private TextView nombre_categoria_detail,cantidad_total_producto_carrito;

    private LinearLayout carrito_activity_empresa_detail,id_resultado_vacio,id_resultado_categoria;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_categoria);

        reciveData();

        initData();

        setDataRecyclerView();

        //searchOrder();

        backActivity();

        enableCarrito();

        clickCarrito();

        mediaPlayer = MediaPlayer.create(DetailCategoriaActivity.this, R.raw.soundorden);


    }


    void initData(){
        adapter=new ProductoResultsAdapter3();
        viewModel = ViewModelProviders.of(this).get(ProductoViewModel.class);
        viewModel.init();
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(gsonProducto !=null){
                    adapter.setProductoAdapter3(gsonProducto.getListaProducto(),  DetailCategoriaActivity.this);
                }else{
                    id_resultado_categoria.setVisibility(View.GONE);
                    id_resultado_vacio.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    void setDataRecyclerView(){

        id_resultado_categoria=findViewById(R.id.id_resultado_categoria);

        id_resultado_vacio=findViewById(R.id.id_resultado_vacio);

        carrito_activity_empresa_detail=findViewById(R.id.carrito_activity_empresa_detail);

        mToolbar = findViewById(R.id.toolbar_empresa_detail);

        setSupportActionBar(mToolbar);

        mToolbar.setTitle(Ubicacion.ubicacionEnable.getUbicacion_nombre());

        nombre_categoria_detail=findViewById(R.id.nombre_categoria_detail);

        nombre_categoria_detail.setText(categoria_empresa.getNombre());



        cantidad_total_producto_carrito=findViewById(R.id.cantidad_total_producto_carrito_2);




        cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));

        mShimmerViewContainer =findViewById(R.id.shimmer_detail_categoria);
        mShimmerViewContainer.startShimmerAnimation();

        RecyclerView recyclerView=findViewById(R.id.recyclerView_detail_categoria);

        viewModel.searchProducto(categoria_empresa.getIdcategoriaproductoempresa(),empresa.getIdempresa());

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        recyclerView.setAdapter(adapter);



    }

    private void backActivity(){mToolbar.setNavigationOnClickListener(v->finish()); }

    private void enableCarrito(){

        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa()) >0){
            carrito_activity_empresa_detail.setVisibility(View.VISIBLE);
            cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
            //cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        }else {

            carrito_activity_empresa_detail.setVisibility(View.GONE);
        }


    }

    private void clickCarrito(){
        carrito_activity_empresa_detail.setOnClickListener( v->{
            Intent intent= CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa);
            startActivity(intent);
        });
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

        soundEffect();


        enableCarrito();

        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));


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
            enableCarrito();
        }


        //  passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto-=producto.getProducto_precio()));
        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());



        cantidad_total_producto_carrito.setText(String.valueOf(totalProductoByEmpresa));


        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));


      //  passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));


        enableCarrito();

        soundEffect();

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

        enableCarrito();

        soundEffect();

        return cantidad;

    }


    public static Intent newIntentDetailCategoriaActivity(Context context, Categoria_producto_empresa categoria, Empresa empresa, List<Categoria_producto_empresa> listaCategoria){
        Intent intent= new Intent(context, DetailCategoriaActivity.class);
        intent.putExtra(CATEGORIA,categoria);
        intent.putExtra(ID_EMPRESA,empresa);
        intent.putExtra(LISTA_CATEGORIA,(Serializable) listaCategoria);
        return intent;
    }


    private  void reciveData(){

       // idCategoria=getIntent().getIntExtra(ID_USUARIO,0);

        //nombreCategoria=getIntent().getStringExtra(LISTA_CATEGORIA);

        if(getIntent().getSerializableExtra(CATEGORIA)!=null){
            categoria_empresa=(Categoria_producto_empresa) getIntent().getSerializableExtra(CATEGORIA);
        }

        if(getIntent().getSerializableExtra(LISTA_CATEGORIA)!=null){
            listaCategoria= (ArrayList<Categoria_producto_empresa>) getIntent().getSerializableExtra(LISTA_CATEGORIA);
        }

        if(getIntent().getSerializableExtra(ID_EMPRESA)!=null){
            empresa=(Empresa) getIntent().getSerializableExtra(ID_EMPRESA);
        }
    }


    private void searchOrder(){

        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
       // searchView = findViewById(R.id.searchview_detail_categoria);//.getActionView();
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change88
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater= getMenuInflater();

        menuInflater.inflate(R.menu.menu_subcategoria,menu);

        MenuItem menuItem=menu.findItem(R.id.menu_share);

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

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        enableCarrito();
    }

    private void soundEffect(){
        mediaPlayer.start();

        // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }
}
