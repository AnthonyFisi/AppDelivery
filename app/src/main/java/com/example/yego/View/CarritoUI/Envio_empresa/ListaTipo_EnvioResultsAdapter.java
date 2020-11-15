package com.example.yego.View.CarritoUI.Envio_empresa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Envio_empresa;
import com.example.yego.Repository.Modelo.Tipo_Envio;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaTipo_EnvioResultsAdapter extends RecyclerView.Adapter<ListaTipo_EnvioResultsAdapter.ListaTipo_EnvioResutsHolder> {

    private List<Envio_empresa> results= new ArrayList<>();
    private ItemClickTipo_Envio mItemClickTipo_envio;


    @NonNull
    @Override
    public ListaTipo_EnvioResutsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_envio,parent,false);
        return new ListaTipo_EnvioResultsAdapter.ListaTipo_EnvioResutsHolder(view,mItemClickTipo_envio);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaTipo_EnvioResutsHolder holder, int position) {
        Envio_empresa tipo_envio= results.get(position);

        /*if (tipo_envio.getUrl_foto()!= null) {
            String imageUrl =tipo_envio.getUrl_foto()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.tipo_envio_IMAGEN);
        }*/
        holder.tipo_envio_COSTO_ENVIO.setText(String.valueOf(tipo_envio.getPrecio()));
        holder.tipo_envio_NOMBRE.setText(tipo_envio.getNombre_tipo_envio());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Envio_empresa> results, ItemClickTipo_Envio itemClickTipo_envio ){
        this.mItemClickTipo_envio=itemClickTipo_envio;
        this.results=results;
        notifyDataSetChanged();
    }

    public class ListaTipo_EnvioResutsHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ItemClickTipo_Envio  mItemClickTipo_envio;
        private TextView tipo_envio_COSTO_ENVIO,tipo_envio_NOMBRE;
       // private ImageView tipo_envio_IMAGEN;

        public ListaTipo_EnvioResutsHolder(@NonNull View itemView,ItemClickTipo_Envio itemClickTipo_envio){
            super(itemView);
            mItemClickTipo_envio=itemClickTipo_envio;
            tipo_envio_COSTO_ENVIO=itemView.findViewById(R.id.tipo_envio_COSTO_ENVIO);
            tipo_envio_NOMBRE=itemView.findViewById(R.id.tipo_envio_NOMBRE);
            //tipo_envio_IMAGEN=itemView.findViewById(R.id.tipo_envio_IMAGEN);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mItemClickTipo_envio.itemClick(results.get(getAdapterPosition()));
        }
    }

    public interface ItemClickTipo_Envio{
        void itemClick(Envio_empresa tipo_envio);
    }
}
