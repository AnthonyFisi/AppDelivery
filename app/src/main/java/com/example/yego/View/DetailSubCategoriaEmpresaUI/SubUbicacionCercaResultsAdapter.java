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


    List<Empresa> results= new ArrayList<>();
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
                    .into(holder.mImageView);
        }

        holder.mTextView.setText(empresa.getNombre_empresa());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public void setSubUbicacionCercaAdpater(List<Empresa> results/*,OneNoteListener oneNoteListener*/){
        this.results=results;
        notifyDataSetChanged();
       // this.onNoteListener=oneNoteListener;
    }

    public class SubUbicacionCercaResultsHolder  extends RecyclerView.ViewHolder implements  View.OnClickListener{


        ImageView mImageView;
        TextView mTextView;
        OneNoteListener oneNoteListener;
        public SubUbicacionCercaResultsHolder(@NonNull View itemView,OneNoteListener oneNoteListener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_sub_categoria_ubicacion);
            mTextView =itemView.findViewById(R.id.textView_nombre_sub_categoria_ubicacion);
            this.oneNoteListener=oneNoteListener;
            itemView.setOnClickListener(this);
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
