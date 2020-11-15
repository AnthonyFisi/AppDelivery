package com.example.yego.View.CarritoUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaProductoCarritoResultsAdapter extends  RecyclerView.Adapter<ListaProductoCarritoResultsAdapter.ListaProductoCarritoResultsHolder> {

    private List<ProductoJOINregistroPedidoJOINpedido> resultsEmpresa=new ArrayList<>();
    private ItemClickWidget mItemClickWidget;


    @NonNull
    @Override
    public ListaProductoCarritoResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_carrito,parent,false);
        return new ListaProductoCarritoResultsAdapter.ListaProductoCarritoResultsHolder(view,mItemClickWidget);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProductoCarritoResultsHolder holder, int position) {
        ProductoJOINregistroPedidoJOINpedido producto=resultsEmpresa.get(position);
        holder.producto_carrito_TITULO_EMPRESA.setText(producto.getNombre_empresa());
        holder.producto_carrito_COSTO_TOTAL.setText(String.valueOf(producto.getPedido_montototal()));
        String imageUrl = producto.getUrlfoto_empresa()
                .replace("http://", "https://");
        Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.producto_carrito_IMAGEN_EMPRESA);


    }

    @Override
    public int getItemCount() {
        return resultsEmpresa.size();
    }

    public  void setResults(List<ProductoJOINregistroPedidoJOINpedido> resultsEmpresa,ItemClickWidget itemClickWidget){
        this.resultsEmpresa=resultsEmpresa;
        this.mItemClickWidget=itemClickWidget;
        notifyDataSetChanged();
    }

    public class  ListaProductoCarritoResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView producto_carrito_IMAGEN_EMPRESA;
        private TextView producto_carrito_TITULO_EMPRESA,producto_carrito_COSTO_TOTAL;
        ItemClickWidget mItemClickWidget;


        public ListaProductoCarritoResultsHolder(@NonNull View itemView,ItemClickWidget itemClickWidget) {
            super(itemView);
            producto_carrito_IMAGEN_EMPRESA=itemView.findViewById(R.id.producto_carrito_IMAGEN_EMPRESA);
            producto_carrito_TITULO_EMPRESA=itemView.findViewById(R.id.producto_carrito_TITULO_EMPRESA);
            producto_carrito_COSTO_TOTAL=itemView.findViewById(R.id.producto_carrito_COSTO_TOTAL);
            this.mItemClickWidget=itemClickWidget;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            mItemClickWidget.itemClickCarritoByEmpresa(resultsEmpresa.get(getAdapterPosition()));
        }
    }



    public  interface  ItemClickWidget{
        void itemClickCarritoByEmpresa(ProductoJOINregistroPedidoJOINpedido producto);
    }
}
