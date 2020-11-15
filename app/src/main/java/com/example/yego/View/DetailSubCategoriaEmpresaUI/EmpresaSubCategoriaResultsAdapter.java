package com.example.yego.View.DetailSubCategoriaEmpresaUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmpresaSubCategoriaResultsAdapter extends RecyclerView.Adapter<EmpresaSubCategoriaResultsAdapter.EmpresaSubCategoriaResultHolder> {

    private List<Empresa> results=new ArrayList<>();
    private OneNoteListener onNoteListener;


    @NonNull
    @Override
    public EmpresaSubCategoriaResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa_sub_categoria_total,parent,false);
        return new EmpresaSubCategoriaResultsAdapter.EmpresaSubCategoriaResultHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaSubCategoriaResultHolder holder, int position) {
        Empresa empresa=results.get(position);

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .skipMemoryCache(true)
                    .into(holder.mImageView);
        }

        holder.mTextView.setText(empresa.getNombre_empresa());

        holder.ubicacion_cerca.setText(empresa.getDireccion_empresa());
        holder.item_empresa_subcatgoria_sort_TIEMPO_APROXIMADO.setText(empresa.getTiempo_aproximado_entrega());
        String precio="S/."+empresa.getCosto_delivery();

        holder.item_empresa_subcatgoria_sort_PRECIO_DELIVERY.setText(precio);
    }

    @Override
    public int getItemCount() {
        return results.size();


    }


    void setEmpresaSubCategoriaAdpater(List<Empresa> results, OneNoteListener oneNoteListener){
        this.results=results;
        notifyDataSetChanged();
         this.onNoteListener=oneNoteListener;
    }

    public class EmpresaSubCategoriaResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mImageView;
        TextView mTextView,item_empresa_subcatgoria_sort_PRECIO_DELIVERY,item_empresa_subcatgoria_sort_TIEMPO_APROXIMADO;

        TextView ubicacion_cerca;


        OneNoteListener oneNoteListener;

        EmpresaSubCategoriaResultHolder(@NonNull View itemView, OneNoteListener oneNoteListener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_sub_empresa_total);
            mTextView =itemView.findViewById(R.id.textView_nombre_sub_empresa_total);
            ubicacion_cerca=itemView.findViewById(R.id.textView_ubicacion_subcategoria_total);
            item_empresa_subcatgoria_sort_PRECIO_DELIVERY=itemView.findViewById(R.id.item_empresa_subcatgoria_sort_PRECIO_DELIVERY);
            item_empresa_subcatgoria_sort_TIEMPO_APROXIMADO=itemView.findViewById(R.id.item_empresa_subcategoria_sort_TIEMPO_APROXIMADO);
            this.oneNoteListener=oneNoteListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            oneNoteListener.OneNoteClick(results.get(getPosition()));
        }
    }

    public interface OneNoteListener{
        void OneNoteClick(Empresa empresa);
    }
}
