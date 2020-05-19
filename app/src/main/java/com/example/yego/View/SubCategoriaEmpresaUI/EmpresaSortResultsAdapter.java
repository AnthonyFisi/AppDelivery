package com.example.yego.View.SubCategoriaEmpresaUI;

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

public class EmpresaSortResultsAdapter extends RecyclerView.Adapter<EmpresaSortResultsAdapter.EmpresaSortResultsHolder>{

    List<Empresa> results= new ArrayList<>();


    @NonNull
    @Override
    public EmpresaSortResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa_sort,parent,false);
        return new EmpresaSortResultsAdapter.EmpresaSortResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaSortResultsHolder holder, int position) {

        Empresa empresa=results.get(position);

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.mImageView);
        }

        holder.mTextView.setText(empresa.getNombre_empresa());
        holder.ubicacion.setText(empresa.getUbicacion_empresa());
        holder.descripcion.setText(empresa.getDescripcion_empresa());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setEmpresaSortAdpater(List<Empresa> results){
        this.results=results;
        notifyDataSetChanged();

    }

    public class EmpresaSortResultsHolder extends RecyclerView.ViewHolder{


        ImageView mImageView;
        TextView mTextView;
        TextView ubicacion;
        TextView descripcion;

        public EmpresaSortResultsHolder(@NonNull View itemView) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_empresa_sort);
            mTextView =itemView.findViewById(R.id.textView_nombre_sort);
            ubicacion =itemView.findViewById(R.id.textView_ubicacion_empresa_sort);
            descripcion =itemView.findViewById(R.id.textView_descripcion_empresa_sort);
        }
    }
}
