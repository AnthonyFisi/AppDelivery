package com.example.yego.View.SearchUI;

import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriaFilterResultsAdapter extends RecyclerView.Adapter<CategoriaFilterResultsAdapter.CategoriaFilterResultsHolder> {

    private List<CategoriaEmpresa> results =new ArrayList<>();

    private Context mContext;

    @NonNull
    @Override
    public CategoriaFilterResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_filter,parent,false);
        return new CategoriaFilterResultsAdapter.CategoriaFilterResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaFilterResultsHolder holder, int position) {

        CategoriaEmpresa categoriaEmpresa=results.get(position);

        holder.text_category.setText(categoriaEmpresa.getNombre_categoria());

        if(SearchActivity.listaCategoria.get(position)){
            holder.cardview_background.setBackgroundColor(mContext.getResources().getColor(R.color.mainColor));
            holder.text_category.setTextColor(mContext.getResources().getColor(R.color.white));

        }else {
            holder.cardview_background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.text_category.setTextColor(mContext.getResources().getColor(R.color.titulo));

        }


    }

    @Override
    public int getItemCount() {

        return results.size();

    }

    public void setResults(List<CategoriaEmpresa> results,Context context){
        this.results=results;
        this.mContext=context;
        notifyDataSetChanged();
    }

    public class CategoriaFilterResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView text_category;

        private CardView cardview_background;

        CategoriaFilterResultsHolder(@NonNull View itemView) {
            super(itemView);
            text_category=itemView.findViewById(R.id.text_category);
            cardview_background=itemView.findViewById(R.id.cardview_background);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            changeAllValue();
            updateColor(getAdapterPosition(),results.size());
            notifyDataSetChanged();

        }
    }




    private void changeAllValue(){
        SearchActivity.listaCategoria.clear();
    }

    private void updateColor(int position,int cantidad){

        for(int i=0;i<cantidad;i++){
            if(position==i){
                SearchActivity.listaCategoria.add(true);
            }else {
                SearchActivity.listaCategoria.add(false);

            }
        }
    }

}
