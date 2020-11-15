package com.example.yego.View.SearchUI;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.Empresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSearchResultsAdapter extends RecyclerView.Adapter<ItemSearchResultsAdapter.ItemSearchResultsHolder> {

     private List<Empresa> results=new ArrayList<>();

     private Filtro mFiltro;


    @NonNull
    @Override
    public ItemSearchResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new ItemSearchResultsAdapter.ItemSearchResultsHolder(view,mFiltro);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSearchResultsHolder holder, int position) {

        Empresa objeto=results.get(position);

        holder.nombre.setText(objeto.getNombre_empresa());


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Empresa> results,Filtro filtro){
        this.results=results;
        this.mFiltro=filtro;
        notifyDataSetChanged();
    }

    public void clearResults(){
        results.clear();
        notifyDataSetChanged();
    }



    public class ItemSearchResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Filtro mFiltro;
        private TextView nombre;
        //private CardView carview_categoria_filtro;

        ItemSearchResultsHolder(@NonNull View itemView, Filtro filtro) {
            super(itemView);
            this.mFiltro=filtro;
            nombre=itemView.findViewById(R.id.nombre_filtro);
          //  carview_categoria_filtro=itemView.findViewById(R.id.carview_categoria_filtro);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            mFiltro.clickCategoria(results.get(getAdapterPosition()),getAdapterPosition());


           /* carview_categoria_filtro.setCardBackgroundColor(Color.argb(99,2,182,253));
            nombre.setTextColor(Color.WHITE);

            results.get(getAdapterPosition()).setPorcentajeBusqueda(100);
            notifyDataSetChanged();*/

        }
    }

    public interface Filtro{
        void clickCategoria(Empresa objeto,int position);
    }
}
