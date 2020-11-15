package com.example.yego.View.EmpresaDetailUI.BuscadorUI;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.yego.Repository.Modelo.Producto;

import java.util.List;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import java.util.ArrayList;

import com.example.yego.R;

import androidx.annotation.Nullable;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    private Context context;
    private int resourceId;
    private List<Producto> items, tempItems, suggestions;

    public ProductoAdapter(@NonNull Context context, int resourceId, ArrayList<Producto> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();

        System.out.println(items.size()+"TAMAÑO ");


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            Producto producto = getItem(position);

           TextView name =  view.findViewById(R.id.textView);



            name.setText(producto.getProducto_nombre());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public Producto getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return productoFilter;
    }


    private Filter productoFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Producto producto = (Producto) resultValue;
            return producto.getProducto_nombre();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Producto producto: tempItems) {



                    if (producto.getProducto_nombre().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(producto);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Producto> tempValues;
            tempValues = (ArrayList<Producto>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (Producto productoObj : tempValues) {
                    add(productoObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}