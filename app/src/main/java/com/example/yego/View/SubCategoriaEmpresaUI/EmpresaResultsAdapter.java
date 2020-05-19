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

public class EmpresaResultsAdapter extends RecyclerView.Adapter<EmpresaResultsAdapter.EmpresaResultsHolder> {

    List<Empresa> results= new ArrayList<>();


    @NonNull
    @Override
    public EmpresaResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubicacion_cerca,parent,false);
        return new EmpresaResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaResultsHolder holder, int position) {

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

    public void setEmpresaAdpater(List<Empresa> results){
        this.results=results;
        notifyDataSetChanged();

    }

    public  class EmpresaResultsHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTextView;
        TextView ubicacion;
        TextView descripcion;
        public EmpresaResultsHolder(@NonNull View itemView) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_restaurante_ubicacion);
            mTextView =itemView.findViewById(R.id.textView_nombre_ubicacion);
            ubicacion =itemView.findViewById(R.id.textView_ubicacion_empresa);
            descripcion =itemView.findViewById(R.id.textView_descripcion_empresa);


        }
    }


}


