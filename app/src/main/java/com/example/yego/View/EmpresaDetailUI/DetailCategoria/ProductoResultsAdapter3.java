package com.example.yego.View.EmpresaDetailUI.DetailCategoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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

public class ProductoResultsAdapter3 extends RecyclerView.Adapter<ProductoResultsAdapter3.ProductoHolder2> implements Filterable {
    private List<Producto> results=new ArrayList<>();
    private List<Producto> resultsFiltered=new ArrayList<>();
    private OneNoteListener2 onNoteListener;



    @NonNull
    @Override
    public ProductoHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_detail_categoria,parent,false);


        return new ProductoResultsAdapter3.ProductoHolder2(view,onNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder2 holder, int position) {
        Producto producto=resultsFiltered.get(position);
        if(!producto.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
        }
        ProductoJOINregistroPedidoJOINpedido objeto= new ProductoJOINregistroPedidoJOINpedido();
        objeto=objeto.existeObjeto(producto.getIdproducto());

        if(objeto !=null){

            System.out.println("AGREGADOS"+objeto.getProducto_nombre()+" --> "+ objeto.getIdproducto());

            holder.button_anadir_product.setVisibility(View.VISIBLE);
            holder.item_product_inicio_ADD_CART.setVisibility(View.GONE);

            holder.item_product_inicio_cantidad_producto.setText(String.valueOf(objeto.getRegistropedido_cantidadtotal()));

            holder.linearlayout_INCREMENT.setVisibility(View.GONE);

          /*  holder.item_product_inicio_ADD_CART.setEnabled(false);
            holder.item_product_inicio_ADD_CART.setTranslationX(100);
            holder.item_product_inicio_ADD_CART.setTranslationY(200);
            holder.item_product_inicio_cantidad_producto.setText(String.valueOf(objeto.getRegistropedido_cantidadtotal()));*/

        }else{

            holder.linearlayout_INCREMENT.setVisibility(View.VISIBLE);

            holder.button_anadir_product.setVisibility(View.GONE);
            holder.item_product_inicio_ADD_CART.setVisibility(View.VISIBLE);

           /* holder.item_product_inicio_ADD_CART.setTranslationX(0);
            holder.item_product_inicio_ADD_CART.setTranslationY(0);
            holder.item_product_inicio_ADD_CART.setEnabled(true);*/
        }


        System.out.println("productos que no estan agregados" + producto.getProducto_nombre()+ " ---->"+producto.getIdproducto());
        if (producto.getProducto_uriimagen()!= null) {
            String imageUrl = producto.getProducto_uriimagen()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.empresa_detail_sub_categoria_IMAGE);
        }

        holder.empresa_detail_sub_categoria_TITULO.setText(producto.getProducto_nombre());
      //  holder.empresa_detail_sub_categoria_DETALLE.setText(producto.getProducto_detalle());
        String precio="S/ "+producto.getProducto_precio();
        holder.empresa_detail_sub_categoria_PRICE.setText(precio);


        if(!producto.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
            holder.item_product_inicio_ADD_CART.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return resultsFiltered.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public void setProductoAdapter3(List<Producto> results, OneNoteListener2 oneNoteListener){
        System.out.println("inicializando los datos en  RESULTS  ADAPTER");
        this.results=results;
        this.resultsFiltered=results;
        this.onNoteListener=oneNoteListener;
        notifyDataSetChanged();

    }

    public void deleteData(){
        resultsFiltered.clear();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    resultsFiltered = results;
                } else {
                    List<Producto> filteredList = new ArrayList<>();
                    for (Producto row : results) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match}
                        String cadena=row.getProducto_nombre()+" "+row.getProducto_detalle()+" "+row.getProducto_precio();

                        if (cadena.toLowerCase().contains(charString.toLowerCase())) {
                            System.out.println("PASASMOS POR ACA ");
                            filteredList.add(row);
                        }
                    }

                    resultsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = resultsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultsFiltered= (List<Producto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductoHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView empresa_detail_sub_categoria_IMAGE;
        TextView empresa_detail_sub_categoria_TITULO,empresa_detail_sub_categoria_PRICE,item_product_inicio_cantidad_producto;
        Button item_product_inicio_ADD_CART;
        Button item_product_inicio_incrementar_producto,item_product_inicio_disminuir_producto;
        LinearLayout button_anadir_product,estado_restaurante,linearlayout_INCREMENT;;

        OneNoteListener2 oneNoteListener;

        ProductoHolder2(@NonNull View itemView, OneNoteListener2 oneNoteListener) {
            super(itemView);

            empresa_detail_sub_categoria_IMAGE=(ImageView) itemView.findViewById(R.id. popular_productos_imagen);
            empresa_detail_sub_categoria_TITULO=(TextView) itemView.findViewById(R.id.titulo_product_popular);
            empresa_detail_sub_categoria_PRICE=(TextView) itemView.findViewById(R.id.producto_popular_precio);
            item_product_inicio_ADD_CART= itemView.findViewById(R.id.item_product_inicio_ADD_CART);
            item_product_inicio_incrementar_producto=itemView.findViewById(R.id.item_product_inicio_incrementar_producto);
            item_product_inicio_disminuir_producto= itemView.findViewById(R.id.item_product_inicio_disminuir_producto);
            item_product_inicio_cantidad_producto= itemView.findViewById(R.id.item_product_inicio_cantidad_producto);
            button_anadir_product=itemView.findViewById(R.id.button_añadir_product);
            estado_restaurante=itemView.findViewById(R.id.estado_restaurante);
            linearlayout_INCREMENT=itemView.findViewById(R.id.linearlayout_INCREMENT);

            this.oneNoteListener=oneNoteListener;

            empresa_detail_sub_categoria_IMAGE.setOnClickListener(this);
            item_product_inicio_ADD_CART.setOnClickListener(this);
            item_product_inicio_incrementar_producto.setOnClickListener(this);
            item_product_inicio_disminuir_producto.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if(empresa_detail_sub_categoria_IMAGE== view.findViewById(R.id.popular_productos_imagen)){
                oneNoteListener.OneNoteClick(resultsFiltered.get(getAdapterPosition()));
            }
            if(item_product_inicio_ADD_CART==view.findViewById(R.id.item_product_inicio_ADD_CART)){
                int i=oneNoteListener.añadirProducto(resultsFiltered.get(getAdapterPosition()));
                /* startAlphaAnimation(layout_CARRITO_ADD,200,itemView.VISIBLE);*/

                item_product_inicio_cantidad_producto.setText(String.valueOf(i));
                // startAlphaAnimation(item_empresa_detail_sub_categoria_ADD_CART,0,itemView.INVISIBLE);
                item_product_inicio_ADD_CART.setEnabled(false);

                button_anadir_product.setVisibility(View.VISIBLE);
                item_product_inicio_ADD_CART.setVisibility(View.GONE);

               /* item_product_inicio_ADD_CART.setTranslationX(100);
                item_product_inicio_ADD_CART.setTranslationY(200);*/
                linearlayout_INCREMENT.setVisibility(View.GONE);


            }
            if(item_product_inicio_disminuir_producto==view.findViewById(R.id.item_product_inicio_disminuir_producto)){
                System.out.println("entro aca ");
                int i=oneNoteListener.disminuirProducto(resultsFiltered.get(getPosition()));

                if(i==0){

                    button_anadir_product.setVisibility(View.GONE);
                    item_product_inicio_ADD_CART.setVisibility(View.VISIBLE);

                   /* item_product_inicio_ADD_CART.setTranslationX(0);
                    item_product_inicio_ADD_CART.setTranslationY(0);*/
                    item_product_inicio_ADD_CART.setEnabled(true);
                    linearlayout_INCREMENT.setVisibility(View.VISIBLE);

                }
                item_product_inicio_cantidad_producto.setText(String.valueOf(i));


            }

            if(item_product_inicio_incrementar_producto == view.findViewById(R.id.item_product_inicio_incrementar_producto)){
                System.out.println("entro aca  2");

                int i=oneNoteListener.incrementarProducto(resultsFiltered.get(getPosition()));

                item_product_inicio_cantidad_producto.setText(String.valueOf(i));
                linearlayout_INCREMENT.setVisibility(View.GONE);

            }
        }
    }


    public interface OneNoteListener2{
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