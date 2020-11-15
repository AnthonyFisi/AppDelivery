package com.example.yego.View.CarritoUI;

import android.content.Context;
import android.os.Bundle;
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
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Utils.listaProofResultsAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaCarritoResultsAdapter extends RecyclerView.Adapter<ListaCarritoResultsAdapter.ListaCarritoHolder> {

    List<ProductoJOINregistroPedidoJOINpedido> results= new ArrayList<>();
    private ImageListener mImageListener;

    @NonNull
    @Override
    public ListaCarritoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);

        return new ListaCarritoResultsAdapter.ListaCarritoHolder(view,mImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCarritoHolder holder, int position) {

        ProductoJOINregistroPedidoJOINpedido producto=results.get(position);


        if (producto.getProducto_uriimagen()!= null) {
            String imageUrl = producto.getProducto_uriimagen()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)

                    .into(holder.lista_carrito_IMAGE);
        }

        holder.lista_carrito_TITULO.setText(producto.getProducto_nombre());
        String precio="S/ "+producto.getRegistropedido_preciototal();
        holder.lista_carrito_PRICE.setText(precio);
        holder.cantidad_lista_carrito.setText(String.valueOf(producto.getRegistropedido_cantidadtotal()));



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (results.size() == 0) return R.layout.fragment_carrito_empty;
        else return R.layout.item_lista_carrito;
    }



    public void setListaCarritoAdapter(List<ProductoJOINregistroPedidoJOINpedido> results , ImageListener imageListener){
        this.results=results;
        this.mImageListener=imageListener;
        notifyDataSetChanged();
    }

    public class ListaCarritoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageListener mImageListener  ;
        ImageView lista_carrito_IMAGE;
        TextView lista_carrito_TITULO,lista_carrito_DETALLE,lista_carrito_PRICE,cantidad_lista_carrito;
        Button incrementar_lista_carrito,disminuir_lista_carrito;

        LinearLayout lista_carrito_CONJUNTO_TITULO,lista_carrito_LINEAR_CANTIDAD;
        CardView lista_carrito_CARD_VIEW;


        public ListaCarritoHolder(@NonNull View itemView, ImageListener imageListener) {
            super(itemView);

            this.mImageListener=imageListener;
            lista_carrito_IMAGE=(ImageView) itemView.findViewById(R.id.lista_carrito_IMAGE);
            lista_carrito_TITULO=(TextView) itemView.findViewById(R.id.lista_carrito_TITULO);
           // lista_carrito_DETALLE=(TextView) itemView.findViewById(R.id.lista_carrito_DETALLE);
            lista_carrito_PRICE=(TextView) itemView.findViewById(R.id.lista_carrito_PRICE);
            cantidad_lista_carrito=(TextView) itemView.findViewById(R.id.cantidad_lista_carrito);
            incrementar_lista_carrito=itemView.findViewById(R.id.incrementar_lista_carrito);
            disminuir_lista_carrito= itemView.findViewById(R.id.disminuir_lista_carrito);


            lista_carrito_CONJUNTO_TITULO=itemView.findViewById(R.id.lista_carrito_CONJUNTO_TITULO);
            lista_carrito_LINEAR_CANTIDAD=itemView.findViewById(R.id.lista_carrito_LINEAR_CANTIDAD);
            lista_carrito_CARD_VIEW=itemView.findViewById(R.id.lista_carrito_CARD_VIEW);


            incrementar_lista_carrito.setOnClickListener(this);
            disminuir_lista_carrito.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            if(disminuir_lista_carrito==view.findViewById(R.id.disminuir_lista_carrito)){
                System.out.println("entro aca ");
                Bundle i=mImageListener.disminuirProducto(results.get(getPosition()));
                System.out.println("el get adapter possiton es "+ getAdapterPosition());
                System.out.println("SIZE DE LISTA ES " + results.size());
                if(i.getInt("cantidad")==0){




                    results.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), results.size());



                    for(ProductoJOINregistroPedidoJOINpedido pe:ProductoJOINregistroPedidoJOINpedido.carrito){
                        System.out.println("antiguo");
                        System.out.println(pe.getIdproducto() + " cantidad del producto  es : "+pe.getRegistropedido_cantidadtotal());
                    }


                    ProductoJOINregistroPedidoJOINpedido p= new ProductoJOINregistroPedidoJOINpedido();
                    ProductoJOINregistroPedidoJOINpedido.carrito.remove(p.existeObjeto(i.getInt("idProducto")));

                    for(ProductoJOINregistroPedidoJOINpedido pe:ProductoJOINregistroPedidoJOINpedido.carrito){
                        System.out.println("nuevo");
                        System.out.println(pe.getIdproducto() + " cantidad del producto  es : "+pe.getRegistropedido_cantidadtotal());
                    }







                }else{

                    cantidad_lista_carrito.setText(String.valueOf(i.getInt("cantidad")));
                    lista_carrito_PRICE.setText(String.valueOf(i.getFloat("precio")));

                }



            }

            if(incrementar_lista_carrito == view.findViewById(R.id.incrementar_lista_carrito)){
                System.out.println("entro aca  2");

                Bundle i=mImageListener.añadirProducto(results.get(getPosition()));


                cantidad_lista_carrito.setText(String.valueOf(i.getInt("cantidad")));

                String precio="S/ "+String.valueOf(i.getFloat("precio"));

                lista_carrito_PRICE.setText(precio);

            }

        }
    }



    public interface ImageListener{

        Bundle añadirProducto(ProductoJOINregistroPedidoJOINpedido producto);

        Bundle disminuirProducto(ProductoJOINregistroPedidoJOINpedido producto);
    }
}
