package com.example.yego.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.View.EmpresaDetailUI.Tabs.InicioFragment;
import com.example.yego.View.ProductDetailUI.CarritoFragment;
import com.example.yego.View.ProductDetailUI.FragmentProductoDetail;
import com.example.yego.View.ProductDetailUI.FragmentProductosRelacionados;
import com.example.yego.View.VentaUI.FragmentComentarioBottomSheet;
import com.example.yego.View.VentaUI.FragmentComentarioVentaDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements
        FragmentComentarioVentaDialog.FormDialogListener1,
InicioFragment.OnDataPass,
FragmentProductosRelacionados.OnDataPass,
        CarritoFragment.CleanComentario {
    int LAUNCH_SECOND_ACTIVITY = 1;

    private Producto productoBackResult;
    private boolean returnData=false;



    private static final String  INTENT_PRODUCTO_DETAIL_ACTIVITY="com.example.yego.View.ProductDetailActivity.Producto";
    private static final String  INTENT_PRODUCTO_DETAIL_ACTIVITY_EMPRESA="com.example.yego.View.ProductDetailActivity.Empresa";
    private static final String  INTENT_LISTANOMBRECATEGORIA_DETAIL_ACTIVITY_EMPRESA="com.example.yego.View.ProductDetailActivity.nombreCategoria";

    private CarritoFragment fragment2;

    private ArrayList<Categoria_producto_empresa> listaCategoria;

    private Producto producto;

    private FragmentProductoDetail fragment;

   // private LinearLayout activity_product_detail_CARRITO;
    private Toolbar toolbar_PRODUCT_DETAIL;
    private TextView activity_product_detail_CARRITO_CANTIDAD,activity_product_detail_COSTO_TOTAL_CARRITO;
    private Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

       reciveData();
       // activity_product_detail_CARRITO=findViewById(R.id.activity_product_detail_CARRITO);
       // activity_product_detail_CARRITO_CANTIDAD=findViewById(R.id.activity_product_detail_CARRITO_CANTIDAD);
        //activity_product_detail_COSTO_TOTAL_CARRITO=findViewById(R.id.activity_product_detail_COSTO_TOTAL_CARRITO);
        /*toolbar_PRODUCT_DETAIL=findViewById(R.id.toolbar_PRODUCT_DETAIL);
        setSupportActionBar(toolbar_PRODUCT_DETAIL);
        toolbar_PRODUCT_DETAIL.setSubtitle(empresa.getNombre_empresa()+" - "+nombreSubCategoria);
*/

       // activity_product_detail_CARRITO_CANTIDAD.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
//        activity_product_detail_COSTO_TOTAL_CARRITO.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));



        /*activity_product_detail_CARRITO.setOnClickListener(view -> {

            Intent intent= CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa,producto);
            startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

        });*/



        fragment = (FragmentProductoDetail) getSupportFragmentManager().findFragmentById(R.id.product_detail_FRAGMENT);
        if(fragment !=null){
            fragment.setProducto(producto,empresa,listaCategoria);
        }

        FragmentProductosRelacionados fragment1 = (FragmentProductosRelacionados) getSupportFragmentManager().findFragmentById(R.id.productos_relaciondaos_FRAGMENT);
        if(fragment1 !=null){
            fragment1.setProducto(producto);
        }



        InicioFragment fragment3 = (InicioFragment) getSupportFragmentManager().findFragmentById(R.id.all_products_FRAGMENT);
        if(fragment3 !=null){
            fragment3.setProducto(producto,empresa,listaCategoria);
        }



        fragment2 = (CarritoFragment) getSupportFragmentManager().findFragmentById(R.id.carrito_compra_FRAGMENT);
        if(fragment2 !=null){
            fragment2.setProducto(producto,empresa);
        }


    }

    private void reciveData() {

        if(getIntent().getExtras()!=null){
            producto = (Producto) getIntent().getSerializableExtra(INTENT_PRODUCTO_DETAIL_ACTIVITY);
        }
        if(getIntent().getExtras()!=null){
            empresa =(Empresa) getIntent().getSerializableExtra(INTENT_PRODUCTO_DETAIL_ACTIVITY_EMPRESA);

        }
        //listaCategoria=getIntent().getStringExtra(INTENT_LISTANOMBRECATEGORIA_DETAIL_ACTIVITY_EMPRESA);

        if(getIntent().getExtras()!=null){
            listaCategoria= (ArrayList<Categoria_producto_empresa>) getIntent().getSerializableExtra(INTENT_LISTANOMBRECATEGORIA_DETAIL_ACTIVITY_EMPRESA);
        }
    }


    public static Intent newIntentProductDetail(Context packageContext , Producto producto, Empresa empresa, List<Categoria_producto_empresa> listaCategoria){
        Intent intent =new Intent(packageContext,ProductDetailActivity.class);
        intent.putExtra(INTENT_PRODUCTO_DETAIL_ACTIVITY,producto);
        intent.putExtra(INTENT_PRODUCTO_DETAIL_ACTIVITY_EMPRESA,empresa);
        intent.putExtra(INTENT_LISTANOMBRECATEGORIA_DETAIL_ACTIVITY_EMPRESA,(Serializable) listaCategoria);

        return intent;

    }

 /*   @Override
    public void onDataPass(String cantidadTotal, String costoTotal) {

    //    activity_product_detail_CARRITO_CANTIDAD.setText(cantidadTotal);
     //   activity_product_detail_COSTO_TOTAL_CARRITO.setText(costoTotal);

    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

                productoBackResult=(Producto) data.getSerializableExtra("data");

                returnData=true;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

       // activity_product_detail_CARRITO_CANTIDAD.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())));
        //activity_product_detail_COSTO_TOTAL_CARRITO.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        if(returnData){
            System.out.println("Estoy por ON RESUME de activity PRODUCT ");
            if(fragment2 !=null){
                fragment2.setProducto(productoBackResult,empresa);
            }
        }
    }


    @Override
    public void clearComments(boolean respuesta) {
        if(respuesta) {
            if(fragment !=null){
                fragment.setCleanComentario();
            }
        }
    }

    @Override
    public void update(String comentario, int code) {
        if(fragment !=null){
            fragment.setComentarioProducto(comentario);
        }
    }

    @Override
    public void onDataPass(String cantidadTotal, String costoTotal) {

    }
}
