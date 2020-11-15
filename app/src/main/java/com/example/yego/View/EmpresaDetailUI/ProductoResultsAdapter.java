package com.example.yego.View.EmpresaDetailUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Producto;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class ProductoResultsAdapter extends RecyclerView.Adapter<ProductoResultsAdapter.ProductoHolder> {

    private List<Producto> results=new ArrayList<>();
    private OneNoteListener onNoteListener;


    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa_detail_sub_categoria,parent,false);
       return new ProductoResultsAdapter.ProductoHolder(view,onNoteListener);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {


        Producto producto=results.get(position);

        ProductoJOINregistroPedidoJOINpedido objeto= new ProductoJOINregistroPedidoJOINpedido();
        objeto=objeto.existeObjeto(producto.getIdproducto());

        if(objeto !=null){

            System.out.println(objeto.getProducto_nombre()+" --> "+ objeto.getIdproducto());

            holder.item_empresa_detail_sub_categoria_ADD_CART.setEnabled(false);
            holder.item_empresa_detail_sub_categoria_ADD_CART.setTranslationX(100);
            holder.item_empresa_detail_sub_categoria_ADD_CART.setTranslationY(200);
            holder.cantidad_producto.setText(String.valueOf(objeto.getRegistropedido_cantidadtotal()));

        }else{
            holder.item_empresa_detail_sub_categoria_ADD_CART.setTranslationX(0);
            holder.item_empresa_detail_sub_categoria_ADD_CART.setTranslationY(0);
            holder.item_empresa_detail_sub_categoria_ADD_CART.setEnabled(true);
        }



        System.out.println("objeto producto" + producto.getProducto_nombre());
        if (producto.getProducto_uriimagen()!= null) {
            String imageUrl = producto.getProducto_uriimagen()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.empresa_detail_sub_categoria_IMAGE);
        }

        holder.empresa_detail_sub_categoria_TITULO.setText(producto.getProducto_nombre());
        holder.empresa_detail_sub_categoria_DETALLE.setText(producto.getProducto_detalle());
        holder.empresa_detail_sub_categoria_PRICE.setText(String.valueOf(producto.getProducto_precio()));


    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setProductoAdapter(List<Producto> results, OneNoteListener oneNoteListener){
        System.out.println("inicializando los datos en  RESULTS  ADAPTER");
        this.results=results;
        this.onNoteListener=oneNoteListener;
        notifyDataSetChanged();

    }

    public class ProductoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView empresa_detail_sub_categoria_IMAGE;
        TextView empresa_detail_sub_categoria_TITULO,empresa_detail_sub_categoria_DETALLE,empresa_detail_sub_categoria_PRICE,cantidad_producto;
        Button item_empresa_detail_sub_categoria_ADD_CART;
        Button incrementar_producto,disminuir_producto;
        LinearLayout layout_CARRITO_ADD;
        OneNoteListener oneNoteListener;


        public ProductoHolder(@NonNull View itemView,OneNoteListener oneNoteListener) {
            super(itemView);

            empresa_detail_sub_categoria_IMAGE= itemView.findViewById(R.id. producto_IMAGE);
            empresa_detail_sub_categoria_TITULO= itemView.findViewById(R.id.producto_TITULO);
            empresa_detail_sub_categoria_DETALLE= itemView.findViewById(R.id.producto_DETALLE);
            empresa_detail_sub_categoria_PRICE= itemView.findViewById(R.id.producto_PRICE);
            item_empresa_detail_sub_categoria_ADD_CART= itemView.findViewById(R.id.item_empresa_detail_sub_categoria_ADD_CART);
            layout_CARRITO_ADD= itemView.findViewById(R.id.layout_CARRITO_ADD);
            incrementar_producto= itemView.findViewById(R.id.incrementar_producto);
            disminuir_producto= itemView.findViewById(R.id.disminuir_producto);
            cantidad_producto= itemView.findViewById(R.id.cantidad_producto);


            this.oneNoteListener=oneNoteListener;

            empresa_detail_sub_categoria_IMAGE.setOnClickListener(this);
            item_empresa_detail_sub_categoria_ADD_CART.setOnClickListener(this);
            incrementar_producto.setOnClickListener(this);
            disminuir_producto.setOnClickListener(this);


        }




        @Override
        public void onClick(View view) {

            if(empresa_detail_sub_categoria_IMAGE== view.findViewById(R.id.producto_IMAGE)){
                oneNoteListener.OneNoteClick(results.get(getPosition()));
            }
            if(item_empresa_detail_sub_categoria_ADD_CART==view.findViewById(R.id.item_empresa_detail_sub_categoria_ADD_CART)){
                int i=oneNoteListener.añadirProducto(results.get(getPosition()));
              /* startAlphaAnimation(layout_CARRITO_ADD,200,itemView.VISIBLE);*/

                cantidad_producto.setText(String.valueOf(i));
               // startAlphaAnimation(item_empresa_detail_sub_categoria_ADD_CART,0,itemView.INVISIBLE);
                item_empresa_detail_sub_categoria_ADD_CART.setEnabled(false);
                item_empresa_detail_sub_categoria_ADD_CART.setTranslationX(100);
                item_empresa_detail_sub_categoria_ADD_CART.setTranslationY(200);


            }
            if(disminuir_producto==view.findViewById(R.id.disminuir_producto)){
                System.out.println("entro aca ");
                int i=oneNoteListener.disminuirProducto(results.get(getPosition()));

                if(i==0){
                    item_empresa_detail_sub_categoria_ADD_CART.setTranslationX(0);
                    item_empresa_detail_sub_categoria_ADD_CART.setTranslationY(0);
                    item_empresa_detail_sub_categoria_ADD_CART.setEnabled(true);

                }
                cantidad_producto.setText(String.valueOf(i));


            }

            if(incrementar_producto == view.findViewById(R.id.incrementar_producto)){
                System.out.println("entro aca  2");

                int i=oneNoteListener.incrementarProducto(results.get(getPosition()));


                cantidad_producto.setText(String.valueOf(i));

            }
        }
    }


    public interface OneNoteListener{
        void OneNoteClick(Producto producto);

        int añadirProducto(Producto producto);

        int disminuirProducto(Producto producto);

        int incrementarProducto(Producto producto);

    }



    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);

    }
}
