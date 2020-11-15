package com.example.yego.View.EmpresaFavoritoUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Empresa_favorita;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmpresaFavoritoResultsAdapter extends RecyclerView.Adapter<EmpresaFavoritoResultsAdapter.EmpresaFavoritoResultsHolder>{

    private List<Empresa>  results=new ArrayList<>();
    private ClickEmpresaFavorita mClickEmpresaFavorita;



    @NonNull
    @Override
    public EmpresaFavoritoResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubicacion_cerca,parent,false);
        return new EmpresaFavoritoResultsAdapter.EmpresaFavoritoResultsHolder(view,mClickEmpresaFavorita);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaFavoritoResultsHolder holder, int position) {

        Empresa empresa=results.get(position);

        if(!empresa.isDisponible()){
            holder.estado_restaurante.setVisibility(View.VISIBLE);
        }

        if (empresa.getUrlfoto_empresa()!= null) {
            String imageUrl = empresa.getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.item_ubicacion_cerca_IMAGEN_RESTAURANTE);
        }

        holder.item_ubicacion_cerca_NOMBRE_EMPRESA.setText(empresa.getNombre_empresa());
        //  holder.descripcion.setText(empresa.getDescripcion_empresa());
        holder.item_ubicacion_cerca_TIEMPO_APROXIMADO.setText(empresa.getTiempo_aproximado_entrega());
        String precio="S/."+empresa.getCosto_delivery();
        holder.item_ubicacion_cerca_PRECIO_DELIVERY.setText(precio);



    }

    public void setResults(List<Empresa> results,ClickEmpresaFavorita clickEmpresaFavorita) {
        this.results = results;
        this.mClickEmpresaFavorita=clickEmpresaFavorita;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class EmpresaFavoritoResultsHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ClickEmpresaFavorita mClickEmpresaFavorita;
        ImageView item_ubicacion_cerca_IMAGEN_RESTAURANTE;
        TextView item_ubicacion_cerca_NOMBRE_EMPRESA,item_ubicacion_cerca_TIEMPO_APROXIMADO,item_ubicacion_cerca_PRECIO_DELIVERY;
        LinearLayout estado_restaurante;

        public EmpresaFavoritoResultsHolder(@NonNull View itemView,ClickEmpresaFavorita clickEmpresaFavorita) {
            super(itemView);
            item_ubicacion_cerca_IMAGEN_RESTAURANTE=itemView.findViewById(R.id.item_ubicacion_cerca_IMAGEN_RESTAURANTE);
            item_ubicacion_cerca_NOMBRE_EMPRESA =itemView.findViewById(R.id.item_ubicacion_cerca_NOMBRE_EMPRESA);
            item_ubicacion_cerca_TIEMPO_APROXIMADO=itemView.findViewById(R.id.item_ubicacion_cerca_TIEMPO_APROXIMADO);
            item_ubicacion_cerca_PRECIO_DELIVERY=itemView.findViewById(R.id.item_ubicacion_cerca_PRECIO_DELIVERY);
            estado_restaurante=itemView.findViewById(R.id.estado_restaurante);
            item_ubicacion_cerca_IMAGEN_RESTAURANTE.setOnClickListener(this);
            this.mClickEmpresaFavorita=clickEmpresaFavorita;
        }

        @Override
        public void onClick(View view) {
            mClickEmpresaFavorita.clickEmpresa(results.get(getAdapterPosition()));
        }
    }


    public interface ClickEmpresaFavorita{
        void clickEmpresa(Empresa empresa);
    }

}
