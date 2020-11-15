package com.example.yego.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Ubicacion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ListAdapterUbicacion extends ArrayAdapter<Ubicacion> implements  View.OnClickListener{

    List<Ubicacion> lista;
    Context context;

    public ListAdapterUbicacion(@NonNull Context context, int resource, @NonNull List<Ubicacion> objects) {
        super(context, resource, objects);
        this.lista=objects;
    }

    @Override
    public void onClick(View view) {
        System.out.println("CLICK DE LAS INCUERENCIAS");
    }

    private static class ViewHolder {
        TextView txtName;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ubicacion ubicacion=lista.get(position);
        final View result;
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder =new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_ubicacion, parent, false);
            viewHolder.txtName=convertView.findViewById(R.id.texto_ubicacion_NOMBRE);
            result=convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(ubicacion.getUbicacion_nombre());
        // Return the completed view to render on screen
        return convertView;
    }

}
