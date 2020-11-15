package com.example.yego.View.ProductDetailUI;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

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

public class ProductoRelacionadosResultsAdapter extends RecyclerView.Adapter<ProductoRelacionadosResultsAdapter.ProductoHolder> {

    private List<Producto> results=new ArrayList<>();
    private OneNoteListener onNoteListener;
    private Context context;

    private MediaPlayer mediaPlayer;

    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_relacionado,parent,false);
        return new ProductoRelacionadosResultsAdapter.ProductoHolder(view,onNoteListener,context);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {


        Producto producto=results.get(position);

        if(!producto.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
        }
        ProductoJOINregistroPedidoJOINpedido objeto= new ProductoJOINregistroPedidoJOINpedido();
        objeto=objeto.existeObjeto(producto.getIdproducto());

        if(objeto !=null){

            System.out.println(objeto.getProducto_nombre()+" --> "+ objeto.getIdproducto());

            holder.producto_relacionado_BACKGROUND_IMAGEN.setTranslationX(0);
            holder.producto_relacionado_BACKGROUND_IMAGEN.setTranslationY(0);

            holder.producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationX(0);
            holder.producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationY(0);

            holder.producto_relacionado_CANTIDAD_PRODUCTO.setText(String.valueOf(objeto.getRegistropedido_cantidadtotal()));


        }else{

            holder.producto_relacionado_BACKGROUND_IMAGEN.setTranslationX(200);
            holder.producto_relacionado_BACKGROUND_IMAGEN.setTranslationY(200);

            holder.producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationX(200);
            holder.producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationY(200);

        }



        System.out.println("objeto producto" + producto.getProducto_nombre());
        if (producto.getProducto_uriimagen()!= null) {
            String imageUrl = producto.getProducto_uriimagen()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.producto_relacionado_IMAGEN_PRODUCTO);
        }


    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setProductoAdapter(List<Producto> results, OneNoteListener oneNoteListener, Context context){
        System.out.println("inicializando los datos en  RESULTS  ADAPTER");
        this.context=context;
        this.results=results;
        this.onNoteListener=oneNoteListener;
        notifyDataSetChanged();

    }

    public class ProductoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView producto_relacionado_CANTIDAD_PRODUCTO;
        LinearLayout producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO,producto_relacionado_BACKGROUND_IMAGEN,estado_restaurante;
        ImageView producto_relacionado_IMAGEN_PRODUCTO;
        OneNoteListener oneNoteListener;


        public ProductoHolder(@NonNull View itemView,OneNoteListener oneNoteListener,Context context) {
            super(itemView);

            producto_relacionado_CANTIDAD_PRODUCTO=itemView.findViewById(R.id.producto_relacionado_CANTIDAD_PRODUCTO);
            producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO=itemView.findViewById(R.id.producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO);
            producto_relacionado_BACKGROUND_IMAGEN=itemView.findViewById(R.id.producto_relacionado_BACKGROUND_IMAGEN);
            producto_relacionado_IMAGEN_PRODUCTO=itemView.findViewById(R.id.producto_relacionado_IMAGEN_PRODUCTO);
            estado_restaurante=itemView.findViewById(R.id.estado_restaurante);


            this.oneNoteListener=oneNoteListener;

            producto_relacionado_IMAGEN_PRODUCTO.setOnClickListener(this);


        }




        @Override
        public void onClick(View view) {


            if(producto_relacionado_IMAGEN_PRODUCTO==view.findViewById(R.id.producto_relacionado_IMAGEN_PRODUCTO) && results.get(getAdapterPosition()).isDisponible()){
                Bundle bundle=oneNoteListener.añadirProducto(results.get(getPosition()));

                boolean agregado = bundle.getBoolean("agregado");
                int cantidad = bundle.getInt("cantidad");

                if(agregado){

                    producto_relacionado_BACKGROUND_IMAGEN.setTranslationX(0);
                    producto_relacionado_BACKGROUND_IMAGEN.setTranslationY(0);

                    producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationX(0);
                    producto_relacionado_BACKGROUND_CANTIDAD_PRODUCTO.setTranslationY(0);

                    producto_relacionado_CANTIDAD_PRODUCTO.setText(String.valueOf(cantidad));

                }else{

                    producto_relacionado_CANTIDAD_PRODUCTO.setText(String.valueOf(cantidad));

                }
            }

        }
    }


    public interface OneNoteListener{

        Bundle añadirProducto(Producto producto);

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
