package com.example.yego.View.OrdenUI.DetallesUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProductPedidoResultsAdapter extends RecyclerView.Adapter<ProductPedidoResultsAdapter.ProductPedidoResultsHolder> {

    private List<ProductoJOINregistroPedidoJOINpedido> results= new ArrayList<>();

    @NonNull
    @Override
    public ProductPedidoResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_carrito,parent,false);
        return new ProductPedidoResultsAdapter.ProductPedidoResultsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPedidoResultsHolder holder, int position) {

        ProductoJOINregistroPedidoJOINpedido producto=results.get(position);



        if (producto.getProducto_uriimagen()!= null) {
            String imageUrl = producto.getProducto_uriimagen()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.lista_carrito_IMAGE);
        }

        holder.lista_carrito_TITULO.setText(producto.getProducto_nombre());
        holder.lista_carrito_PRICE.setText(String.valueOf(producto.getRegistropedido_preciototal()));
        holder.cantidad_lista_carrito.setText(String.valueOf(producto.getRegistropedido_cantidadtotal()));

        holder.incrementar_lista_carrito.setVisibility(View.GONE);
        holder.disminuir_lista_carrito.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public void setResults(List<ProductoJOINregistroPedidoJOINpedido> results){
        this.results=results;
        notifyDataSetChanged();
    }
    class ProductPedidoResultsHolder extends RecyclerView.ViewHolder{

        ImageView lista_carrito_IMAGE;
        TextView lista_carrito_TITULO,lista_carrito_DETALLE,lista_carrito_PRICE,cantidad_lista_carrito;
        Button incrementar_lista_carrito,disminuir_lista_carrito;

        LinearLayout lista_carrito_CONJUNTO_TITULO,lista_carrito_LINEAR_CANTIDAD;
        CardView lista_carrito_CARD_VIEW;


        ProductPedidoResultsHolder(@NonNull View itemView) {
            super(itemView);

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

        }
    }
}
