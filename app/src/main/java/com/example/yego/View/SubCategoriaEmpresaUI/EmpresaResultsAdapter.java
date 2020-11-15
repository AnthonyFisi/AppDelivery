package com.example.yego.View.SubCategoriaEmpresaUI;

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

public class EmpresaResultsAdapter extends RecyclerView.Adapter<EmpresaResultsAdapter.EmpresaResultsHolder> {

    List<Empresa> results= new ArrayList<>();

    private ImageListener imageListener;


    @NonNull
    @Override
    public EmpresaResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubicacion_cerca,parent,false);
        return new EmpresaResultsHolder(itemView,imageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaResultsHolder holder, int position) {

        Empresa empresa=results.get(position);

        if(!empresa.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
        }

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()

                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .skipMemoryCache(true)
                    .into(holder.item_ubicacion_cerca_IMAGEN_RESTAURANTE);
        }

        holder.item_ubicacion_cerca_NOMBRE_EMPRESA.setText(empresa.getNombre_empresa());
      //  holder.descripcion.setText(empresa.getDescripcion_empresa());
        holder.item_ubicacion_cerca_TIEMPO_APROXIMADO.setText(empresa.getTiempo_aproximado_entrega());
        String precio="S/."+empresa.getCosto_delivery();
        holder.item_ubicacion_cerca_PRECIO_DELIVERY.setText(precio);




    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setEmpresaAdpater(List<Empresa> results,ImageListener imageListener){
        this.results=results;
        this.imageListener=imageListener;
        notifyDataSetChanged();

    }

    public  class EmpresaResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView item_ubicacion_cerca_IMAGEN_RESTAURANTE;
        TextView item_ubicacion_cerca_NOMBRE_EMPRESA,item_ubicacion_cerca_TIEMPO_APROXIMADO,item_ubicacion_cerca_PRECIO_DELIVERY;
        LinearLayout estado_restaurante;

        ImageListener imageListener;
        public EmpresaResultsHolder(@NonNull View itemView, ImageListener imageListener) {
            super(itemView);
            item_ubicacion_cerca_IMAGEN_RESTAURANTE=itemView.findViewById(R.id.item_ubicacion_cerca_IMAGEN_RESTAURANTE);
            item_ubicacion_cerca_NOMBRE_EMPRESA =itemView.findViewById(R.id.item_ubicacion_cerca_NOMBRE_EMPRESA);
            item_ubicacion_cerca_TIEMPO_APROXIMADO=itemView.findViewById(R.id.item_ubicacion_cerca_TIEMPO_APROXIMADO);
            item_ubicacion_cerca_PRECIO_DELIVERY=itemView.findViewById(R.id.item_ubicacion_cerca_PRECIO_DELIVERY);
            estado_restaurante=itemView.findViewById(R.id.estado_restaurante);
            this.imageListener=imageListener;
            item_ubicacion_cerca_IMAGEN_RESTAURANTE.setOnClickListener(this);
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


