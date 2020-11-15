package com.example.yego.View.EmpresaDetailUI.BuscadorUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Producto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductoAdapter2 extends  RecyclerView.Adapter<ProductoAdapter2.ProductoHolder2> {


    private List<Producto> results= new ArrayList<>();
    private ClickBusqueda mClickBusqueda;

    @NonNull
    @Override
    public ProductoHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row,parent,false);
        return new ProductoAdapter2.ProductoHolder2(view,mClickBusqueda);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder2 holder, int position) {

        Producto producto=results.get(position);

        holder.mTextView.setText(producto.getProducto_nombre());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public void setResults(List<Producto> results,ClickBusqueda clickBusqueda){
        this.results=results;
        this.mClickBusqueda=clickBusqueda;
    }


    public class ProductoHolder2 extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView mTextView;
        private ClickBusqueda mClickBusqueda;

        public ProductoHolder2(@NonNull View itemView,ClickBusqueda clickBusqueda) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.textView);
            this.mClickBusqueda=clickBusqueda;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            mClickBusqueda.clickProducto(results.get(getAdapterPosition()),results);

        }
    }


    public interface ClickBusqueda{
        void clickProducto(Producto producto, List<Producto> list);
    }
}
