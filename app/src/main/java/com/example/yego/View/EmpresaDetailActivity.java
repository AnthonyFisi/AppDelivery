package com.example.yego.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.View.EmpresaDetailUI.BuscadorUI.BuscadorActivity;
import com.example.yego.View.EmpresaDetailUI.Tabs.Categoria1Fragment;
import com.example.yego.View.EmpresaDetailUI.Tabs.InicioFragment;
import com.example.yego.View.EmpresaDetailUI.Tabs.OfertasFragment;
import com.example.yego.View.EmpresaDetailUI.ProductosTabsPagerAdapter;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.ViewModel.Categoria_producto_empresaViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.yego.R.drawable.activity_home_search;

public class EmpresaDetailActivity extends AppCompatActivity implements InicioFragment.OnDataPass , OfertasFragment.OnDataPass, Categoria1Fragment.OnDataPass {

    private static final String INTENT_NAME_FRAGMENT_EMPRESA_DETAIL = "com.example.yego.View.EmpresaDetailActivity";


    private Empresa empresa;

    TextView  activity_empresa_detail_TITULO,cantidad_total_producto,costo_total_empresa_detail,
            activity_empresa_detail_UBICACION,activity_empresa_detail_HORARIO_FIN,activity_empresa_detail_CANTIDAD_TIEMPO_DELIVERY,
            activity_empresa_detail_COSTO_DELIVERY,activity_empresa_detail_CANTIDAD_ESTRELLAS,titulo_empresa;

    ImageView activity_empresa_detail_PRINCIPAL,activity_empresa_detail_STAR_1;

    LinearLayout carrito_activity_empresa_detail,home_SEARCH;

    private BottomNavigationView bottomNavigationView;

    private AppBarLayout appbar;

    private CollapsingToolbarLayout collapsing;

    private Toolbar toolbar;

    private List<Categoria_producto_empresa> lista_categoria;

    private ImageButton imageButtonBack,imageButtonSearch;

    private List<Producto> listaProducto;

    private boolean carritoEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detail);

         empresa=(Empresa)getIntent().getSerializableExtra(INTENT_NAME_FRAGMENT_EMPRESA_DETAIL);

         listaProducto=new ArrayList<>();

         carritoEnable=false;

        declararWidgets();

        setDataWidget();

        clickButtonCarrito();

        //transparentScreen();

        setSupportActionBar(toolbar);

        loadData();

        appbar.addOnOffsetChangedListener( new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow=true;
            int scrollRange=-1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(scrollRange == -1){

                    scrollRange=appBarLayout.getTotalScrollRange();
                }

                if(scrollRange+verticalOffset==0){
                    toolbar.setBackgroundColor(Color.WHITE);
                    collapsing.setCollapsedTitleTextColor(Color.BLACK);
                    titulo_empresa.setVisibility(View.VISIBLE);

                    isShow=true;
                }else  if(isShow)
                {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.transparente));
                    titulo_empresa.setVisibility(View.GONE);
                    isShow=false;
                }
            }
        });

        clickSearchAndBack();


    }



    private void declararWidgets(){
        activity_empresa_detail_TITULO= findViewById(R.id.activity_empresa_detail_TITULO);
        activity_empresa_detail_PRINCIPAL=findViewById(R.id.activity_empresa_detail_PRINCIPAL);
        costo_total_empresa_detail=findViewById(R.id.costo_total_empresa_detail);
        carrito_activity_empresa_detail=findViewById(R.id.carrito_activity_empresa_detail);

        cantidad_total_producto= findViewById(R.id.cantidad_total_producto_carrito);
        activity_empresa_detail_UBICACION=findViewById(R.id.activity_empresa_detail_UBICACION);
        activity_empresa_detail_HORARIO_FIN=findViewById(R.id.activity_empresa_detail_HORARIO_FIN);
        activity_empresa_detail_CANTIDAD_TIEMPO_DELIVERY=findViewById(R.id.activity_empresa_detail_CANTIDAD_TIEMPO_DELIVERY);
        activity_empresa_detail_COSTO_DELIVERY=findViewById(R.id.activity_empresa_detail_COSTO_DELIVERY);
        activity_empresa_detail_CANTIDAD_ESTRELLAS=findViewById(R.id.activity_empresa_detail_CANTIDAD_ESTRELLAS);
        activity_empresa_detail_STAR_1=findViewById(R.id.activity_empresa_detail_STAR_1);


        appbar = findViewById( R.id.appbar );
        collapsing = findViewById( R.id.collapsing );
        toolbar = findViewById( R.id.toolbar_empresa_detail );

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        titulo_empresa=findViewById(R.id.titulo_empresa);

        imageButtonBack=findViewById(R.id.imageButtonBack);
        imageButtonSearch=findViewById(R.id.imageButtonSearch);

        home_SEARCH=findViewById(R.id.home_SEARCH);
    }




    private void setDataWidget(){

        activity_empresa_detail_TITULO.setText(empresa.getNombre_empresa());

        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())>0){
            bottomNavigationView.setVisibility(View.VISIBLE);
            cantidad_total_producto.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
            costo_total_empresa_detail.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        }


        activity_empresa_detail_UBICACION.setText(empresa.getDireccion_empresa());
        activity_empresa_detail_CANTIDAD_TIEMPO_DELIVERY.setText(String.valueOf(empresa.getTiempo_aproximado_entrega()));
        String precio="S/ "+empresa.getCosto_delivery();
        activity_empresa_detail_COSTO_DELIVERY.setText(precio);
        activity_empresa_detail_CANTIDAD_ESTRELLAS.setText(String.valueOf(empresa.getEstrella_empresa()));
        Glide.with(this).load(empresa.getUrlfoto_empresa()).into(activity_empresa_detail_PRINCIPAL);

        titulo_empresa.setText(empresa.getNombre_empresa());

        //imageButtonSearch.setEnabled(false);
        home_SEARCH.setEnabled(false);

    }

    private void clickSearchAndBack(){

        imageButtonBack.setOnClickListener(v->finish());

        home_SEARCH.setOnClickListener(v->{
            Intent intent= BuscadorActivity.newIntentBuscadorActivity(getApplicationContext(),lista_categoria,empresa);
            startActivity(intent);
        });

    }


    private void loadData(){

        Categoria_producto_empresaViewModel viewModel = new ViewModelProvider(this).get(Categoria_producto_empresaViewModel.class);
        viewModel.init();
        viewModel.getGsonCategoria_producto_empresaLiveData().observe(this, gsonCategoria_producto_empresa -> {

            if(gsonCategoria_producto_empresa !=null){

                if(gsonCategoria_producto_empresa.getListaCategoriaEmpresa()!=null){
                    declararViewPagerAdapater(gsonCategoria_producto_empresa.getListaCategoriaEmpresa());
                    lista_categoria=gsonCategoria_producto_empresa.getListaCategoriaEmpresa();
                }
            }

        });

        viewModel.searchCategoriaProductoEmpresa(empresa.getIdempresa());
    }








    private void clickButtonCarrito(){
        carrito_activity_empresa_detail.setOnClickListener(view -> {
            Intent intent=CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa);
            startActivity(intent);
        });

    }


    private void declararViewPagerAdapater(List<Categoria_producto_empresa> lista){
        ProductosTabsPagerAdapter tabsPagerAdapter = new ProductosTabsPagerAdapter(getSupportFragmentManager(),empresa,lista);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);

    }


    public void transparentScreen(){

        //make translucent statusBar on kitkat devices
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }//        toolbar.setTitleTextColor(getDominanContrassColor(bitmap));

    }

    @Override
    public void onDataPass(String cantidadTotal,String costoTotal) {

        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa()) >0){
            bottomNavigationView.setVisibility(View.VISIBLE);
            cantidad_total_producto.setText(cantidadTotal);
            costo_total_empresa_detail.setText(costoTotal);
        }else {

            bottomNavigationView.setVisibility(View.GONE);
        }



    }

    @Override
    public void loadData(boolean rpta) {
        home_SEARCH.setEnabled(rpta);
    }

    @Override
    public void dataCategoria1(List<Producto> results) {
        listaProducto.addAll(results);
        loadCarrito();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(carritoEnable){

            loadCarrito();

        }

    }

    private void loadCarrito(){

        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa()) >0){
            bottomNavigationView.setVisibility(View.VISIBLE);
            cantidad_total_producto.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
            costo_total_empresa_detail.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        }else {

            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    public static Intent newIntentEmpresa(Context packageContext , Empresa empresa){
        Intent intent =new Intent(packageContext,EmpresaDetailActivity.class);
        intent.putExtra(INTENT_NAME_FRAGMENT_EMPRESA_DETAIL,empresa);
        return intent;
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        carritoEnable=true;
    }
}
