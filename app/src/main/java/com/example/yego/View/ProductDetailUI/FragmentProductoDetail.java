package com.example.yego.View.ProductDetailUI;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Categoria_producto_empresa;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.View.EmpresaDetailUI.BuscadorUI.BuscadorActivity;

import com.example.yego.View.VentaUI.FragmentComentarioBottomSheet;
import com.example.yego.View.VentaUI.FragmentComentarioVentaDialog;
import com.example.yego.ViewModel.PedidoViewModel;
import com.example.yego.ViewModel.RegistroPedidoViewModel;


import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductoDetail extends Fragment  {

    private LinearLayout fragment_comentario_DIALOG;
    private TextView comentario_product_detail;
    private ImageView producto_detail_IMAGEN;
    private TextView producto_detail_NOMBRE,producto_detail_PRECIO,producto_detail_DETALLE;
    private LinearLayout estado_restaurante,linearlayout_AGREGAR_COMENTARIO;

    private String comentario="";

    private Empresa empresa;
    private List<Categoria_producto_empresa> lista_categoria;
    private ImageButton imageButtonBack,imageButtonSearch;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        RegistroPedidoViewModel registroPedidoViewModel = new ViewModelProvider(this).get(RegistroPedidoViewModel.class);
        registroPedidoViewModel.init();

        PedidoViewModel pedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        pedidoViewModel.init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_producto_detail, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declararWidgets(view);

        linearlayout_AGREGAR_COMENTARIO.setOnClickListener(view15 -> showEditDialog());

        clickSearchAndBack();
    }

    private void showEditDialog() {
        /*
        FragmentManager fm = getChildFragmentManager();
        FragmentComentarioDialog editNameDialogFragment = FragmentComentarioDialog.newInstance(comentario);
        editNameDialogFragment.setTargetFragment(FragmentProductoDetail.this, 300);
        editNameDialogFragment.show(fm, "fragment_edit_name");*/

       /* FragmentComentarioBottomSheet fragment=FragmentComentarioBottomSheet.newInstance(1,"Comentario de producto",comentario,"Ejemplo:Con poco aji al pollo");
        fragment.show(getChildFragmentManager(),FragmentComentarioBottomSheet.TAG);*/

        FragmentManager fm = getChildFragmentManager();
        FragmentComentarioVentaDialog alertDialog = FragmentComentarioVentaDialog.newInstance(comentario,"Comentario producto","Ejemplo:Con poco aji",1);
        alertDialog.show(fm, "fragment_alert");
    }




    public void setProducto(Producto producto, Empresa empresa, List<Categoria_producto_empresa> listaCategoria){
        this.empresa=empresa;
        this.lista_categoria=listaCategoria;
        System.out.println("Estoy en set producto actualizandop");
        Glide.with(this).load(producto.getProducto_uriimagen()).into(producto_detail_IMAGEN);
        producto_detail_NOMBRE.setText(producto.getProducto_nombre());
        String precio="S/."+producto.getProducto_precio();
        producto_detail_PRECIO.setText(precio);
        producto_detail_DETALLE.setText(producto.getProducto_detalle());

        ProductoJOINregistroPedidoJOINpedido p = new ProductoJOINregistroPedidoJOINpedido();

        if(!producto.isDisponible()){
            estado_restaurante.setVisibility(View.VISIBLE);
        }





    }

    private void declararWidgets(View view){
        producto_detail_IMAGEN= view.findViewById(R.id.producto_detail_IMAGEN_2);
        producto_detail_NOMBRE=view.findViewById(R.id.producto_detail_NOMBRE);
        producto_detail_PRECIO= view.findViewById(R.id.producto_detail_PRECIO);
        producto_detail_DETALLE=view.findViewById(R.id.producto_detail_DETALLE);
        comentario_product_detail=view.findViewById(R.id.comentario_product_detail);
        fragment_comentario_DIALOG=view.findViewById(R.id.fragment_comentario_DIALOG);
        estado_restaurante=view.findViewById(R.id.estado_restaurante);
        imageButtonBack=view.findViewById(R.id.imageButtonBack);
        imageButtonSearch=view.findViewById(R.id.imageButtonSearch);
        linearlayout_AGREGAR_COMENTARIO=view.findViewById(R.id.linearlayout_AGREGAR_COMENTARIO);


    }

    private void clickSearchAndBack(){

        imageButtonBack.setOnClickListener(v-> Objects.requireNonNull(getActivity()).finish());

        imageButtonSearch.setOnClickListener(v->{
            Intent intent= BuscadorActivity.newIntentBuscadorActivity(getContext(),lista_categoria,empresa);
            startActivity(intent);
            getActivity().finish();
        });

    }
    public void setComentarioProducto(String comentarioChange){

        if(comentarioChange.length()>0){
            fragment_comentario_DIALOG.setVisibility(View.VISIBLE);
            linearlayout_AGREGAR_COMENTARIO.setVisibility(View.GONE);
            this.comentario=comentarioChange;
            comentario_product_detail.setText(comentarioChange);
        }else{
            fragment_comentario_DIALOG.setVisibility(View.GONE);
            linearlayout_AGREGAR_COMENTARIO.setVisibility(View.VISIBLE);
            comentario="";
            comentario_product_detail.setText("");
            comentario_product_detail.setHint("Añadir comentario antes de agegar producto ");
        }
    }
    public void setCleanComentario(){
        fragment_comentario_DIALOG.setVisibility(View.GONE);
        linearlayout_AGREGAR_COMENTARIO.setVisibility(View.VISIBLE);
        comentario="";
        comentario_product_detail.setText("");
        comentario_product_detail.setHint("Añadir comentario antes de agegar producto ");
    }




}
