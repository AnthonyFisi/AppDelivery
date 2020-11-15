package com.example.yego.View.DetailSubCategoriaEmpresaUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.View.SubCategoriaEmpresaUI.SubCategoriaResultsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubUbicacionCercaResultsAdapter extends RecyclerView.Adapter<SubUbicacionCercaResultsAdapter.SubUbicacionCercaResultsHolder> {


    private List<Empresa> results= new ArrayList<>();
    private OneNoteListener onNoteListener;

    @NonNull
    @Override
    public SubUbicacionCercaResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_categoria_cerca,parent,false);
        return new SubUbicacionCercaResultsAdapter.SubUbicacionCercaResultsHolder(itemView,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubUbicacionCercaResultsHolder holder, int position) {


        Empresa empresa=results.get(position);

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .skipMemoryCache(true)
                    .into(holder.mImageView);
        }

        holder.item_subcategoria_cerca_NOMBRE_EMPRESA.setText(empresa.getNombre_empresa());
        holder.item_subcategoria_cerca_TIEMPO_APROXIMADO.setText(empresa.getTiempo_aproximado_entrega());
        String precio="S/."+empresa.getCosto_delivery();

        holder.item_subcategoria_cerca_PRECIO_DELIVERY.setText(precio);


    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    void setSubUbicacionCercaAdpater(List<Empresa> results, OneNoteListener oneNoteListener){
        this.results=results;
        notifyDataSetChanged();
        this.onNoteListener=oneNoteListener;

    }

    public class SubUbicacionCercaResultsHolder  extends RecyclerView.ViewHolder implements  View.OnClickListener{


        ImageView mImageView;
        TextView item_subcategoria_cerca_NOMBRE_EMPRESA,item_subcategoria_cerca_TIEMPO_APROXIMADO,item_subcategoria_cerca_PRECIO_DELIVERY;

        OneNoteListener oneNoteListener;
        SubUbicacionCercaResultsHolder(@NonNull View itemView, OneNoteListener oneNoteListener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_sub_categoria_ubicacion);
            item_subcategoria_cerca_NOMBRE_EMPRESA=itemView.findViewById(R.id.item_subcategoria_cerca_NOMBRE_EMPRESA);
            item_subcategoria_cerca_TIEMPO_APROXIMADO=itemView.findViewById(R.id.item_subcategoria_cerca_TIEMPO_APROXIMADO);
            item_subcategoria_cerca_PRECIO_DELIVERY=itemView.findViewById(R.id.item_subcategoria_cerca_PRECIO_DELIVERY);


            this.oneNoteListener=oneNoteListener;
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            oneNoteListener.oneNoteClick(results.get(getPosition()));
        }
    }

    public interface OneNoteListener{
        void  oneNoteClick(Empresa empresa);
    }
}
