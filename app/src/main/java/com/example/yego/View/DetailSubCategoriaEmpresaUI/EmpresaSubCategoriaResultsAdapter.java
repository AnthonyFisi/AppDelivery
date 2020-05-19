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

    List<Empresa> results=new ArrayList<>();
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
                    .into(holder.mImageView);
        }

        holder.mTextView.setText(empresa.getNombre_empresa());
    }

    @Override
    public int getItemCount() {
        return results.size();

    }


    public void setEmpresaSubCategoriaAdpater(List<Empresa> results,OneNoteListener oneNoteListener){
        this.results=results;
        notifyDataSetChanged();
         this.onNoteListener=oneNoteListener;
    }

    public class EmpresaSubCategoriaResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mImageView;
        TextView mTextView;
        OneNoteListener oneNoteListener;

        public EmpresaSubCategoriaResultHolder(@NonNull View itemView,OneNoteListener oneNoteListener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView_sub_empresa_total);
            mTextView =itemView.findViewById(R.id.textView_nombre_sub_empresa_total);
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
