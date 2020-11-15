package com.example.yego.View.CarritoUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.Repository.Repositorio.ProductoJOINregistroPedidoJOINpedidoRepository;
import com.example.yego.View.CarritoActivity;
import com.example.yego.View.CarritoUI.Envio_empresa.Envio_empresaFragment;
import com.example.yego.ViewModel.ProductoJOINregistroPedidoJOINpedidoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FragmentListaCarrito extends Fragment implements ListaCarritoResultsAdapter.ImageListener {

    ProductoJOINregistroPedidoJOINpedidoViewModel viewModel;
    private ListaCarritoResultsAdapter adapter;

    TextView activity_carrito_CANTIDAD_TOTAL, activity_carrito_CANTIDAD_SUBTOTAL,activity_carrito_COSTO_DELIVERY,activity_carrito_UBICACION;
    TextView etiqueta_delivery,etiqueta_recoger_tienda;

    private CardView activity_carrito_delivery,activity_carrito_envio_casa;
    Button activity_carrito_SIGUIENTE,activity_carrito_ELIMINAR;
    ImageButton eliminar_carrito;
    private Empresa empresa=new Empresa();
    private ProductoJOINregistroPedidoJOINpedido pro=new ProductoJOINregistroPedidoJOINpedido();
    private float costoDelivery=0;

    private LinearLayout id_carrito_vacio,id_carrito_lleno;
    private Tipo_Envio mTipo_envio= new Tipo_Envio();
    private Venta venta;

    private float suma;

    private int cantidad_descuento;

    private float monto_descontado;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        venta=new Venta();
        adapter = new ListaCarritoResultsAdapter();
       viewModel = ViewModelProviders.of(this).get(ProductoJOINregistroPedidoJOINpedidoViewModel.class);
        viewModel.init();
        viewModel.getProductoJOINregistroPedidoJOINpedidoLiveData().observe(this, gsonProductoJOINregistroPedidoJOINpedido -> {
            if(gsonProductoJOINregistroPedidoJOINpedido !=null){
                id_carrito_lleno.setVisibility(View.VISIBLE);
                id_carrito_vacio.setVisibility(View.GONE);
                adapter.setListaCarritoAdapter(gsonProductoJOINregistroPedidoJOINpedido.getListaProductoJOINregistroPedidoJOINpedido(),FragmentListaCarrito.this);
            }else{
                id_carrito_lleno.setVisibility(View.GONE);
                id_carrito_vacio.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_carrito, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView=view.findViewById(R.id.lista_carrito_RecyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        //DECLARAR WIDGET
        declararWidget(view);


        //SET DATA
        setDataWidget();

        //TOUCH BUTTOM
        touchButtonActivity();

    }


    private void declararWidget(View view){
        activity_carrito_CANTIDAD_TOTAL=view.findViewById(R.id.activity_carrito_CANTIDAD_TOTAL);
        activity_carrito_COSTO_DELIVERY=view.findViewById(R.id.activity_carrito_COSTO_DELIVERY);
        activity_carrito_SIGUIENTE=view.findViewById(R.id.activity_carrito_SIGUIENTE);
        activity_carrito_ELIMINAR=view.findViewById(R.id.activity_carrito_ELIMINAR);
        eliminar_carrito=view.findViewById(R.id.eliminar_carrito);
        //simpleViewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher_CARRITO); // get the reference of ViewSwitcher
        activity_carrito_UBICACION=view.findViewById(R.id.activity_carrito_UBICACION);
        activity_carrito_delivery=view.findViewById(R.id.activity_carrito_delivery);
        activity_carrito_envio_casa=view.findViewById(R.id.activity_carrito_envio_casa);
        id_carrito_vacio=view.findViewById(R.id.id_carrito_vacio);
        id_carrito_lleno=view.findViewById(R.id.id_carrito_lleno);

        activity_carrito_CANTIDAD_SUBTOTAL=view.findViewById(R.id.activity_carrito_CANTIDAD_SUBTOTAL);
        etiqueta_delivery=view.findViewById(R.id.etiqueta_delivery);
        etiqueta_recoger_tienda=view.findViewById(R.id.etiqueta_recoger_tienda);
    }

    private void setDataWidget(){
        mTipo_envio.setIdtipo_envio(1);
        mTipo_envio.setNombre_tipo_envio("Delivery");



        cantidad_descuento=ProductoJOINregistroPedidoJOINpedido.cantidadDescuento(empresa.getIdempresa());

        venta.setDescuento_mesa(cantidad_descuento);

        monto_descontado=cantidad_descuento*empresa.getMonto_descuento_menu();


        String descontado=" - S/ "+monto_descontado;

        suma=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())-monto_descontado;

        activity_carrito_COSTO_DELIVERY.setText(descontado);

        String sub_total="S/ "+ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa());

        activity_carrito_CANTIDAD_SUBTOTAL.setText(sub_total);
        String total="S/ "+suma;

        activity_carrito_CANTIDAD_TOTAL.setText(total);


        activity_carrito_UBICACION.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());


        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(empresa.getIdempresa())==0){
            id_carrito_lleno.setVisibility(View.GONE);
            id_carrito_vacio.setVisibility(View.VISIBLE);


        }else {

            id_carrito_lleno.setVisibility(View.VISIBLE);
            id_carrito_vacio.setVisibility(View.GONE);
            adapter.setListaCarritoAdapter(ProductoJOINregistroPedidoJOINpedido.getListaEmpresa(empresa.getIdempresa()),FragmentListaCarrito.this);


        }


    }


    private void changeDeliveryOrGo(float costoDelivery){

        float suma=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())+costoDelivery;

        activity_carrito_CANTIDAD_SUBTOTAL.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa())));
        activity_carrito_COSTO_DELIVERY.setText(String.valueOf(costoDelivery));
        activity_carrito_CANTIDAD_TOTAL.setText(String.valueOf(suma));

    }


    private void touchButtonActivity(){
        activity_carrito_SIGUIENTE.setOnClickListener(view -> {


            if(empresa.isDisponible()){

                venta.setDescuento_mesa(monto_descontado);

                venta.setVenta_costopedido(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(empresa.getIdempresa()));

                venta.setVenta_costototal(suma);

                venta.setVenta_costodelivery(empresa.getCosto_delivery());

                Envio_empresaFragment fragment= Envio_empresaFragment.newInstance(empresa,venta);
                fragment.show(getChildFragmentManager(),Envio_empresaFragment.TAG);

            }else {
                Toast.makeText(getContext(),"Lo sentimos la tienda esta cerrada",Toast.LENGTH_LONG).show();
            }




        });


        activity_carrito_ELIMINAR.setOnClickListener(view -> {
            RegistroPedidoViewModel registroPedidoViewModel;
            registroPedidoViewModel = new ViewModelProvider(this).get(RegistroPedidoViewModel.class);
            registroPedidoViewModel.init();

            registroPedidoViewModel.eliminarCarrito(getContext(),empresa.getIdempresa(), Cliente_Bi.getCliente().getIdusuario());

            ProductoJOINregistroPedidoJOINpedido.removeProductosByEmpresa(empresa.getIdempresa());

            id_carrito_lleno.setVisibility(View.GONE);
            id_carrito_vacio.setVisibility(View.VISIBLE);

        });

    }

    private void passData( float costoTotal, boolean carritoVacio) {

        cantidad_descuento=ProductoJOINregistroPedidoJOINpedido.cantidadDescuento(empresa.getIdempresa());


        monto_descontado=cantidad_descuento*empresa.getMonto_descuento_menu();


        float suma=costoTotal-monto_descontado;
        String resultSuma="S/ "+suma;

        String cost_Total="S/ "+costoTotal;
        activity_carrito_CANTIDAD_SUBTOTAL.setText(cost_Total);
        String descontado=" - S/ "+monto_descontado;
       activity_carrito_COSTO_DELIVERY.setText(descontado);
        activity_carrito_CANTIDAD_TOTAL.setText(resultSuma);


        if(carritoVacio){
            id_carrito_lleno.setVisibility(View.GONE);
            id_carrito_vacio.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public Bundle añadirProducto(ProductoJOINregistroPedidoJOINpedido producto) {

        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(FragmentListaCarrito.this).get(RegistroPedidoViewModel.class);
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
        float costo=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()+producto.getProducto_precio();

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costo);

        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        passData(totalCostosByEmpresa,false);

        Bundle bundle= new Bundle();
        bundle.putInt("cantidad",cantidad);
        bundle.putFloat("precio",costo);
        return bundle;
    }

    @Override
    public Bundle disminuirProducto(ProductoJOINregistroPedidoJOINpedido producto) {
        ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

        boolean carritoVacio=false;
        RegistroPedidoViewModel registroPedidoViewModel;
        registroPedidoViewModel = ViewModelProviders.of(FragmentListaCarrito.this).get(RegistroPedidoViewModel.class);
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
        float costo=p.existeObjeto(producto.getIdproducto()).getRegistropedido_preciototal()- producto.getProducto_precio();

        p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);
        p.existeObjeto(producto.getIdproducto()).setRegistropedido_preciototal(costo);
        //System.out.println("CANTIDAD DEL CARRITO      "+ProductoJOINregistroPedidoJOINpedido.TotalProductos);



        int totalProductoByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
        float totalCostosByEmpresa=ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());


        if(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa())==0){
            carritoVacio=true;
            System.out.println("ENTRE AL CARRITO VACIO");
        } // passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos+=1),String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalCosto+=producto.getProducto_precio()));
        passData(totalCostosByEmpresa,carritoVacio);


      //  passData(String.valueOf(ProductoJOINregistroPedidoJOINpedido.TotalProductos-=1));
        Bundle bundle= new Bundle();
        bundle.putInt("cantidad",cantidad);
        bundle.putFloat("precio",costo);
        bundle.putInt("idProducto",producto.getIdproducto());
        return bundle;



    }

    public void setPassProducto(Empresa empresa){
        this.empresa=empresa;
        //viewModel.searchCarritoByEmpresa(Usuario.idUsuario,empresa.getIdempresa());
        setDataWidget();
    }






}
