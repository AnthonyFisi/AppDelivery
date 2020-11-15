package com.example.yego.View.SearchUI.ResultSearchUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmpresaSearchResultsAdapter extends RecyclerView.Adapter<EmpresaSearchResultsAdapter.EmpresaSearchResultsHolder>{

    private List<Empresa> results= new ArrayList<>();
    private ImageListener imageListener;




    @NonNull
    @Override
    public EmpresaSearchResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa_sort,parent,false);
        return new EmpresaSearchResultsAdapter.EmpresaSearchResultsHolder(itemView,imageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaSearchResultsHolder holder, int position) {

        Empresa empresa=results.get(position);

        if(!empresa.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
        }

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.item_empresa_sort_IMAGEN);
        }


        holder.item_empresa_sort_UBICACION.setText(empresa.getDireccion_empresa());

        holder.item_empresa_sort_NOMBRE_EMPRESA.setText(empresa.getNombre_empresa());

        holder.item_empresa_sort_TIEMPO_APROXIMADO.setText(empresa.getTiempo_aproximado_entrega());
        String precio="S/."+empresa.getCosto_delivery();

        holder.item_empresa_sort_PRECIO_DELIVERY.setText(precio);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

     void setEmpresaSortAdpater(List<Empresa> results,ImageListener imageListener){
        this.results=results;
        this.imageListener=imageListener;
        notifyDataSetChanged();

    }

    public class EmpresaSearchResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView item_empresa_sort_IMAGEN;
        TextView item_empresa_sort_UBICACION,item_empresa_sort_NOMBRE_EMPRESA,item_empresa_sort_TIEMPO_APROXIMADO,item_empresa_sort_PRECIO_DELIVERY;
        LinearLayout estado_restaurante;
        ImageListener imageListener;

        // TextView descripcion;

        private EmpresaSearchResultsHolder(@NonNull View itemView, ImageListener imageListener) {
            super(itemView);
            item_empresa_sort_IMAGEN =itemView.findViewById(R.id.item_empresa_sort_IMAGEN);
            item_empresa_sort_UBICACION =itemView.findViewById(R.id.item_empresa_sort_UBICACION);
            item_empresa_sort_NOMBRE_EMPRESA =itemView.findViewById(R.id.item_empresa_sort_NOMBRE_EMPRESA);
            item_empresa_sort_TIEMPO_APROXIMADO= itemView.findViewById(R.id.item_empresa_sort_TIEMPO_APROXIMADO);
            item_empresa_sort_PRECIO_DELIVERY=itemView.findViewById(R.id.item_empresa_sort_PRECIO_DELIVERY);
            estado_restaurante=itemView.findViewById(R.id.estado_restaurante);
            this.imageListener=imageListener;
            item_empresa_sort_IMAGEN.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            imageListener.imageClick(results.get(getAdapterPosition()));
        }
    }


    public interface ImageListener{
        void imageClick(Empresa empresa);
    }
}
