package com.example.yego.View.SubCategoriaEmpresaUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Publicidad;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PublicidadResultsAdapter extends SliderViewAdapter<PublicidadResultsAdapter.PublicidadResultsViewHolder>{

    private Context context;

    List<Publicidad> results=new ArrayList<>();


    public PublicidadResultsAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<Publicidad> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.results.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Publicidad sliderItem) {
        this.results.add(sliderItem);
        notifyDataSetChanged();
    }
    @Override
    public PublicidadResultsViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publicidad, null);

        return new PublicidadResultsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(PublicidadResultsViewHolder viewHolder, int position) {
        Publicidad publicidad= results.get(position);
        Glide.with(viewHolder.itemView)
                .load(publicidad.getUrl_imagen_publicidad())
                .fitCenter()
                .into(viewHolder.mImageView);

    }

    @Override
    public int getCount() {
        return results.size();
    }


    public class PublicidadResultsViewHolder extends SliderViewAdapter.ViewHolder {

        View view;
        ImageView mImageView;

        public PublicidadResultsViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            mImageView=itemView.findViewById(R.id.imagen_publicidad);
        }
    }
}
