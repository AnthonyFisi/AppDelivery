package com.example.yego.View.EmpresaDetailUI.BuscadorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonCategoriaEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonEmpresa;
import com.example.yego.Repository.Modelo.Gson.GsonProducto;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Word;
import com.example.yego.View.CarritoActivity;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.DetailCategoriaActivity;
import com.example.yego.View.EmpresaDetailUI.DetailCategoria.ProductoResultsAdapter3;
import com.example.yego.View.EmpresaDetailUI.ProductosResultsAdapter4;
import com.example.yego.View.EmpresaDetailUI.Tabs.Categoria1Fragment;
import com.example.yego.View.EmpresaDetailUI.Tabs.InicioFragment;
import com.example.yego.View.ProductDetailActivity;
import com.example.yego.View.SearchUI.CategoriaFilterResultsAdapter;
import com.example.yego.View.SearchUI.ItemSearchResultsAdapter;
import com.example.yego.View.SearchUI.ResultSearchUI.FiltroResultActivity;
import com.example.yego.View.SearchUI.ResultSearchUI.ResultSearchActivity;
import com.example.yego.View.SearchUI.SearchActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.ProductoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuscadorActivity extends AppCompatActivity implements ProductoResultsAdapter3.OneNoteListener2 {

    private static final String DATA_EMPRESA = "data_empresa";

    private static final String LISTA_CATEGORIA = "lista_categoria";

    private Empresa empresa;

    private ArrayList<Categoria_producto_empresa> lista_categoria;

    private List<CategoriaEmpresa> proof = new ArrayList<>();

    private ImageButton ic_back;

    private SearchView searchView;

    private RecyclerView recyclerview_resultado;

    private LinearLayout linearlayout_filtro;

    private ProductoViewModel viewModel;

    private ProductoResultsAdapter3 adapter;

    private TextView activity_resultado_PALABRA_CLAVE, activity_resultado_CANTIDAD_RELACIONADO, cantidad_total_producto_carrito;

    private LinearLayout carrito_activity_empresa_detail,linearlayout_box;

    private LinearLayout mProgressBar;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        reciveData();

        declararWidget();
        loadDataWord();
        searchOrder();

        ic_back.setOnClickListener(v -> {
            onBackPressed();
        });


        linearlayout_filtro.setOnClickListener(v -> {
            searchView.clearFocus();
            searchView.setIconified(true);
            ic_back.setVisibility(View.VISIBLE);


        });

        mediaPlayer = MediaPlayer.create(this, R.raw.soundorden);

        enableCarrito();

        carrito_activity_empresa_detail.setOnClickListener(v->{
            Intent intent= CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa);
            startActivity(intent);
        });

    }

    public static Intent newIntentBuscadorActivity(Context context, List<Categoria_producto_empresa> lista, Empresa empresa) {
        Intent intent = new Intent(context, BuscadorActivity.class);
        intent.putExtra(LISTA_CATEGORIA, (Serializable) lista);
        intent.putExtra(DATA_EMPRESA, empresa);
        return intent;
    }


    private void reciveData() {

        if (getIntent().getExtras() != null) {
            lista_categoria = (ArrayList<Categoria_producto_empresa>) getIntent().getSerializableExtra(LISTA_CATEGORIA);
        }

        if (getIntent().getSerializableExtra(DATA_EMPRESA) != null) {
            empresa = (Empresa) getIntent().getSerializableExtra(DATA_EMPRESA);
        }
    }


    private void declararWidget() {


        ic_back = findViewById(R.id.ic_back);

        searchView = findViewById(R.id.searchview);

        recyclerview_resultado = findViewById(R.id.recyclerview_resultado);
        recyclerview_resultado.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerview_resultado.setVisibility(View.GONE);

        linearlayout_filtro = findViewById(R.id.linearlayout_filtro);

        mProgressBar = findViewById(R.id.progress_bar);

        // activity_resultado_PALABRA_CLAVE
        activity_resultado_PALABRA_CLAVE = findViewById(R.id.activity_resultado_PALABRA_CLAVE);

        //PRODUCTO_RELACIONADO
        activity_resultado_CANTIDAD_RELACIONADO = findViewById(R.id.activity_resultado_CANTIDAD_RELACIONADO);

        carrito_activity_empresa_detail = findViewById(R.id.carrito_activity_empresa_detail);

        cantidad_total_producto_carrito = findViewById(R.id.cantidad_total_producto_carrito);


        linearlayout_box=findViewById(R.id.linearlayout_box);

        mProgressBar.setVisibility(View.GONE);
    }

    private void searchOrder() {


        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnSearchClickListener(v -> {
            ic_back.setVisibility(View.GONE);
            linearlayout_box.setVisibility(View.GONE);
        });

        searchView.setOnCloseListener(() -> {

            ic_back.setVisibility(View.VISIBLE);
            linearlayout_box.setVisibility(View.VISIBLE);
            linearlayout_filtro.setVisibility(View.VISIBLE);

            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //adapter.getFilter().filter(query);
                if (query.length() > 1) {
                    recyclerview_resultado.setVisibility(View.VISIBLE);
                    linearlayout_filtro.setVisibility(View.GONE);

                    mProgressBar.setVisibility(View.VISIBLE);

                    viewModel.searhByWord(empresa.getIdempresa(), query);

                    //index.searchAsync(new Query(query), completionHandler);
                } else {
                    mProgressBar.setVisibility(View.GONE);

                    recyclerview_resultado.setVisibility(View.GONE);
                    linearlayout_filtro.setVisibility(View.VISIBLE);
                    //adapter.clearResults();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //adapter.getFilter().filter(query);

                return false;
            }
        });

    }


    void loadDataWord() {
        adapter = new ProductoResultsAdapter3();
        viewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        viewModel.init();
        viewModel.getProductoLiveData().observe(this, new Observer<GsonProducto>() {
            @Override
            public void onChanged(GsonProducto gsonProducto) {
                mProgressBar.setVisibility(View.GONE);

                if (gsonProducto != null) {
                    recyclerview_resultado.setVisibility(View.VISIBLE);
                    linearlayout_filtro.setVisibility(View.GONE);

                    adapter.deleteData();
                    String cantidad = gsonProducto.getListaProducto().size() + " productos relacionados";
                    activity_resultado_CANTIDAD_RELACIONADO.setText(cantidad);

                    String nombreClave = "Resultado de " + searchView.getQuery().toString();
                    activity_resultado_PALABRA_CLAVE.setText(nombreClave);

                    adapter.setProductoAdapter3(gsonProducto.getListaProducto(), BuscadorActivity.this);
                    recyclerview_resultado.setAdapter(adapter);
                } else {

                    recyclerview_resultado.setVisibility(View.GONE);
                    linearlayout_filtro.setVisibility(View.VISIBLE);

                }
            }
        });


    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            searchView.clearFocus();
            searchView.setIconified(true);

        }
    }


    @Override
    public void OneNoteClick(Producto producto) {

        Intent intent=ProductDetailActivity.newIntentProductDetail(this,producto,empresa,lista_categoria);
        startActivity(intent);
        finish();

    }

    @Override
    public int añadirProducto(Producto producto) {

        PedidoViewModel pedidoViewModel;
        pedidoViewModel = ViewModelProviders.of(this).get(PedidoViewModel.class);
        pedidoViewModel.init();
        MainPedido mainPedido = new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                ""
        );
        pedidoViewModel.insertarPedido(mainPedido, this);

        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido
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
        int total = 1;

        int totalProductoByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        enableCarrito();


        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        //passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));
        soundEffect();
        return total;
    }

    @Override
    public int disminuirProducto(Producto producto) {
        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido();

        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido = new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                ""
        );
        registroPedidoViewModel.disminuirProducto(mainPedido, this);
        int cantidad = p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal() - 1;

        float costoTotal = p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal() - producto.getProducto_precio();
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);


        if (cantidad == 0) {
            ProductoJOINregistroPedidoJOINpedido.carrito.remove(p.existeObjeto(producto.getIdproducto()));

        }

        int totalProductoByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        System.out.println("se agrego + : " + ProductoJOINregistroPedidoJOINpedido.carrito.size());


        // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto-=producto.getProducto_precio()));
        //  passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));
        enableCarrito();
        soundEffect();
        return cantidad;
    }

    @Override
    public int incrementarProducto(Producto producto) {
        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();
        MainPedido mainPedido = new MainPedido(
                producto.getIdproducto(),
                Cliente_Bi.getCliente().getIdusuario(),
                producto.getIdempresa(),
                producto.getProducto_precio(),
                1,
                "");
        registroPedidoViewModel.incrementarProducto(mainPedido, this);

        int cantidad = p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal() + 1;

        float costoTotal = p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal() + producto.getProducto_precio();

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costoTotal);

        int totalProductoByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa = ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        //  passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));

        //  passData(String.valueOf(totalProductoByEmpresa),String.valueOf(totalCostosByEmpresa));

        enableCarrito();

        soundEffect();
        return cantidad;
    }


    private void soundEffect() {
        mediaPlayer.start();

        // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }

    private void enableCarrito() {

        if (ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa()) > 0) {
            carrito_activity_empresa_detail.setVisibility(View.VISIBLE);
            cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
            //cantidad_total_producto_carrito.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        } else {

            carrito_activity_empresa_detail.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        enableCarrito();
    }
}

