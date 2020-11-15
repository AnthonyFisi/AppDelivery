package com.example.yego.View.SubCategoriaEmpresaUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.CategoriaEmpresa;
import com.example.yego.Repository.Modelo.SubCategoriaEmpresa;
import com.example.yego.View.SubCategoriaEmpresaActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoriaResultsAdapter extends RecyclerView.Adapter<SubCategoriaResultsAdapter.SubCategoriaResultsHolder>{

    List<SubCategoriaEmpresa> results= new ArrayList<>();
    OnNoteListener oneNoteListener;

    @NonNull
    @Override
    public SubCategoriaResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategoria,parent,false);

        return new SubCategoriaResultsHolder(itemView,oneNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriaResultsHolder holder, int position) {


        SubCategoriaEmpresa subCategoriaEmpresa=results.get(position);


        if (subCategoriaEmpresa.getUrl_imagen_subcategoria()!= null) {
            String imageUrl = subCategoriaEmpresa.getUrl_imagen_subcategoria()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.mImageView);
        }

        holder.mTextView.setText(subCategoriaEmpresa.getNombre_subcategoria());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setSubCategoriaResultsAdapter(List<SubCategoriaEmpresa> results,OnNoteListener oneNoteListener){
        this.results=results;
        this.oneNoteListener=oneNoteListener;
        notifyDataSetChanged();
    }



    public class SubCategoriaResultsHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mTextView;

        OnNoteListener oneNoteListener;

        public SubCategoriaResultsHolder(@NonNull View itemView,OnNoteListener oneNoteListener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_subCategoria);
            mTextView =itemView.findViewById(R.id.textView_nombre_subcategoria);
            this.oneNoteListener=oneNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            oneNoteListener.onNoteClick(results.get(getPosition()));
        }
    }

    public interface OnNoteListener{
        void onNoteClick(SubCategoriaEmpresa position);
    }


}
