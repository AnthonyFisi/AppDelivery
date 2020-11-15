package com.example.yego.View.LocationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Ubicacion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListUbicacionResultsAdapter extends RecyclerView.Adapter<ListUbicacionResultsAdapter.ListUbicacionHolder>{

    private List<Ubicacion> results= new ArrayList<>();
    private UbicacionClick ubicacionClick;

    @NonNull
    @Override
    public ListUbicacionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubicacion,parent,false);
        return new ListUbicacionResultsAdapter.ListUbicacionHolder(view,ubicacionClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUbicacionHolder holder, int position) {

        Ubicacion ubicacion=results.get(position);

        holder.texto_ubicacion_NOMBRE.setText(ubicacion.getUbicacion_nombre());
        if(ubicacion.getUbicacion_comentarios().length()>0){
            holder.detalles.setVisibility(View.VISIBLE);
            holder.id_detalles_ubicacion.setVisibility(View.VISIBLE);
            holder.id_detalles_ubicacion.setText(ubicacion.getUbicacion_comentarios());
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
    public int getItemViewType(int position) {
        return position;
    }

    public void setResults(List<Ubicacion> results,UbicacionClick ubicacionClick){
        this.results=results;
        this.ubicacionClick=ubicacionClick;
        notifyDataSetChanged();
    }

    public void changeItem(int position){
        Ubicacion answer=results.get(position);

        results.remove(results.get(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, results.size());

        results.add(Ubicacion.ubicacionEnable);
        notifyDataSetChanged();

        Ubicacion.ubicacionEnable=answer;

    }

    public class ListUbicacionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        UbicacionClick ubicacionClick;
        TextView texto_ubicacion_NOMBRE,id_detalles_ubicacion,detalles;
        LinearLayout linearlayout_change_ubicacion;
        ImageButton eliminar_ubicacion;


        public ListUbicacionHolder(@NonNull View itemView,UbicacionClick ubicacionClick) {
            super(itemView);
            texto_ubicacion_NOMBRE=itemView.findViewById(R.id.texto_ubicacion_NOMBRE);
            linearlayout_change_ubicacion=itemView.findViewById(R.id.linearlayout_change_ubicacion);
            eliminar_ubicacion=itemView.findViewById(R.id.eliminar_ubicacion);
            id_detalles_ubicacion=itemView.findViewById(R.id.id_detalles_ubicacion);
            detalles=itemView.findViewById(R.id.detalles);
            this.ubicacionClick=ubicacionClick;
            /*linearlayout_change_ubicacion.setOnClickListener(this);
            eliminar_ubicacion.setOnClickListener(this);*/

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

           // if(linearlayout_change_ubicacion==view.findViewById(R.id.linearlayout_change_ubicacion)){
                ubicacionClick.itemUbicacionClick(results.get(getAdapterPosition()),getAdapterPosition());

           /* }

            if(eliminar_ubicacion==view.findViewById(R.id.eliminar_ubicacion)){

                ubicacionClick.itemDeleteClick(results.get(getAdapterPosition()));

                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), results.size());
            }*/

        }
    }


    public interface UbicacionClick{

        void itemUbicacionClick(Ubicacion ubicacion,int position);

        void itemDeleteClick(Ubicacion ubicacion);


    }
}
