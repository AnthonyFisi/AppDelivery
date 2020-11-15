package com.example.yego.View.ProductDetailUI;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.MainPedido;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.CarritoActivity;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;

import org.jetbrains.annotations.NotNull;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarritoFragment extends Fragment {

    private Producto producto;
    private Empresa empresa;
    private TextView  cantidad_producto_detail,activity_product_detail_CARRITO_CANTIDAD;
    private ImageButton incrementar_producto_detail, disminuir_producto_detail;
    private LinearLayout activity_product_detail_CARRITO,id_producto_disponible,layout_CARRITO_ADD;
    private RegistroPedidoViewModel registroPedidoViewModel;
    private PedidoViewModel pedidoViewModel;

    private final int LAUNCH_SECOND_ACTIVITY = 1;

    private CleanComentario listener;

    private String comentario="...";

    private MediaPlayer mediaPlayer;

    public CarritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registroPedidoViewModel = ViewModelProviders.of(CarritoFragment.this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();

        pedidoViewModel = ViewModelProviders.of(CarritoFragment.this).get(PedidoViewModel.class);
        pedidoViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrito, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declararWidgets(view);
        incrementORdecreaseProduct();
        clickGoToCarrito();
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.soundorden);

    }

    private void declararWidgets(View view){



        cantidad_producto_detail=view.findViewById(R.id.cantidad_producto_detail);

        incrementar_producto_detail=view.findViewById(R.id.incrementar_producto_detail);

        disminuir_producto_detail= view.findViewById(R.id.disminuir_producto_detail);

        activity_product_detail_CARRITO=view.findViewById(R.id.activity_product_detail_CARRITO);

        id_producto_disponible=view.findViewById(R.id.id_producto_disponible);

        layout_CARRITO_ADD= view.findViewById(R.id.layout_CARRITO_ADD);


        activity_product_detail_CARRITO_CANTIDAD=view.findViewById(R.id.activity_product_detail_CARRITO_CANTIDAD);
    }



    public void setProducto(Producto producto, Empresa empresa){
        this.producto=producto;
        this.empresa=empresa;
        System.out.println("Estoy en set producto actualizandop");
        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido();

        activity_product_detail_CARRITO_CANTIDAD.setText(String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa())));



        if(!producto.isDisponible()){
            id_producto_disponible.setVisibility(View.VISIBLE);
            layout_CARRITO_ADD.setVisibility(View.GONE);
        }




    }

    public void setComentario(String comentario){
        this.comentario=comentario;
    }


    private void  incrementORdecreaseProduct(){
/*
        product_detail_AÑADIR_CARRITO.setOnClickListener((View view1) -> {
            MainPedido mainPedido=new MainPedido(
                    producto.getIdproducto(),
                    Usuario.idUsuario,
                    producto.getIdempresa(),
                    producto.getProducto_precio(),
                    1,
                    comentario);


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
                            Usuario.idUsuario,
                            false,
                            0,
                            0,
                            "",
                            0,
                            "",
                            ""
                    );

            ProductoJOINregistroPedidoJOINpedido.carrito.add(p);
            cantidad_producto_detail.setText(String.valueOf(1));
            product_detail_AÑADIR_CARRITO.setEnabled(false);
            product_detail_AÑADIR_CARRITO.setTranslationY(300);
            comentario_product_detail.setText("");
            int total=  ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
            float precioTotal=  ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());

           // passData(String.valueOf(total),String.valueOf(precioTotal));


        });
*/

        incrementar_producto_detail.setOnClickListener((View view12) -> {

            ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

            MainPedido mainPedido=new MainPedido(
                    producto.getIdproducto(),
                    Cliente_Bi.getCliente().getIdusuario(),
                    producto.getIdempresa(),
                    producto.getProducto_precio(),
                    1,
                    comentario);
            //SI EL PRODUCTO EXISTE INSERTAR EN OTRO DESTINO
            if(p.existeObjeto(producto.getIdproducto())!=null){


                registroPedidoViewModel.incrementarProducto(mainPedido,getContext());

                int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()+1;

                p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);

                cantidad_producto_detail.setText(String.valueOf(cantidad));
            }else{


                pedidoViewModel.insertarPedido(mainPedido,getContext());

                ProductoJOINregistroPedidoJOINpedido p1= new ProductoJOINregistroPedidoJOINpedido
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
                                Cliente_Bi.getCliente().getIdusuario(),
                                false,
                                0,
                                0,
                                "",
                                0,
                                "",
                                "",
                                producto.getTipomenu()

                        );

                ProductoJOINregistroPedidoJOINpedido.carrito.add(p1);
               cantidad_producto_detail.setText(String.valueOf(1));

            }




            int total=  ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
            float precioTotal=  ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());

            //passData(String.valueOf(total),String.valueOf(precioTotal));

            activity_product_detail_CARRITO_CANTIDAD.setText(String.valueOf(total));

            listener.clearComments(true);

            soundEffect();


        });

        disminuir_producto_detail.setOnClickListener((View view13) -> {

            ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();

            MainPedido mainPedido=new MainPedido(
                    producto.getIdproducto(),
                    Cliente_Bi.getCliente().getIdusuario(),
                    producto.getIdempresa(),
                    producto.getProducto_precio(),
                    1,
                    "");
            registroPedidoViewModel.disminuirProducto(mainPedido,getContext());
            int cantidad=p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()-1;

            p.existeObjeto(producto.getIdproducto()).setRegistropedido_cantidadtotal(cantidad);


            if(cantidad ==0){
                int i=0;
                for(ProductoJOINregistroPedidoJOINpedido m: ProductoJOINregistroPedidoJOINpedido.carrito){
                    if(m.getIdproducto() == producto.getIdproducto()){
                        ProductoJOINregistroPedidoJOINpedido.carrito.remove(i);
                    }
                    i++;
                }
                cantidad_producto_detail.setText(String.valueOf(0));

            }


            cantidad_producto_detail.setText(String.valueOf(cantidad));

            int total=  ProductoJOINregistroPedidoJOINpedido.totalProductosByEmpresa(producto.getIdempresa());
            float precioTotal=  ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(producto.getIdempresa());

            activity_product_detail_CARRITO_CANTIDAD.setText(total);

            // passData(String.valueOf(total),String.valueOf(precioTotal));

            soundEffect();


        });

        listener.clearComments(true);

    }

    private void clickGoToCarrito(){
        activity_product_detail_CARRITO.setOnClickListener(view -> {

            Intent intent= CarritoActivity.newIntentCarritoActivity(getApplicationContext(),empresa);
            startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);

        });
    }


    @Override
    public void onResume() {
        super.onResume();
        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido();

        if(p.existeObjeto(producto.getIdproducto()) !=null){
            cantidad_producto_detail.setText(String.valueOf(p.existeObjeto(producto.getIdproducto()).getRegistropedido_cantidadtotal()));
        }else{
            cantidad_producto_detail.setText(String.valueOf(0));
        }


        if(!producto.isDisponible()){
            id_producto_disponible.setVisibility(View.VISIBLE);
            layout_CARRITO_ADD.setVisibility(View.GONE);
        }
    }


    public interface CleanComentario{
        void  clearComments(boolean respuesta);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (CarritoFragment.CleanComentario) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FormDialogListener");
        }
    }

    private void soundEffect(){
        mediaPlayer.start();

        // mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
    }

}
