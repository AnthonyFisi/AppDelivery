package com.example.yego.View.HomeUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriaResultsAdapter  extends RecyclerView.Adapter<CategoriaResultsAdapter.CategoriaResultsHolder> {

    List<CategoriaEmpresa> results =new ArrayList<>();
    private OnNoteListener onNoteListener;
    int idCategoria=0;

    @NonNull
    @Override
    public CategoriaResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria,parent,false);

        return new CategoriaResultsHolder(itemView,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaResultsHolder holder, int position) {

        CategoriaEmpresa categoriaEmpresa=results.get(position);
        idCategoria=categoriaEmpresa.getIdCategoriaEmpresa();

        if (categoriaEmpresa.getUrl_imagen_categoria()!= null) {
            String imageUrl = categoriaEmpresa.getUrl_imagen_categoria()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.smallThumbnailImageView);
        }

        holder.titleCategoria.setText(categoriaEmpresa.getNombre_categoria());

    }

    @Override
    public int getItemCount() {

        return results.size();

    }

    public void setResults(List<CategoriaEmpresa> results,OnNoteListener onNoteListener){
        this.results=results;
        this.onNoteListener=onNoteListener;
        notifyDataSetChanged();
    }

    public class CategoriaResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView smallThumbnailImageView;
        private TextView titleCategoria;
        OnNoteListener onNoteListener;

        public CategoriaResultsHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            smallThumbnailImageView=itemView.findViewById(R.id.imagen_categoria);
            titleCategoria=itemView.findViewById(R.id.text_nombre_categoria);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(results.get(getPosition()));

        }
    }


    public interface OnNoteListener{
        void onNoteClick(CategoriaEmpresa position);
    }
}
