package com.example.yego.View.OrdenUI.DetallesUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Orden_estado_general;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EstadoPedidoResultsAdapter extends RecyclerView.Adapter<EstadoPedidoResultsAdapter.EstadoPedidoResultsHolder>{

    private List<Orden_estado_general> results= new ArrayList<>();


    @NonNull
    @Override
    public EstadoPedidoResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_producto,parent,false);
        return new EstadoPedidoResultsAdapter.EstadoPedidoResultsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoPedidoResultsHolder holder, int position) {

        Orden_estado_general estado= results.get(position);

        //String cadena="Estado : "+estado.getId().getIdestado_venta() +" y fecha"+estado.getFecha();

       // holder.item_pedido_producto_ESTADO.setText(cadena);


        estado_mensaje(estado,holder);
        String tiempo=estado.getTiempo_aproximado()+" "+estado.getFecha().toString();
        holder.item_pedido_producto_FECHA.setText(tiempo);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Orden_estado_general> results){
        this.results=results;
        notifyDataSetChanged();
    }

    void addItemList(Context context, List<Orden_estado_general> data){


        System.out.println("Tamaño antes "+results.size());

        results.add(data.get(data.size()-1));

        notifyItemInserted(data.size()-1);

        notifyDataSetChanged();

        System.out.println("Tamaño despues "+results.size());

        Toast.makeText(context,"Fue actualizado"+results.size(),Toast.LENGTH_LONG).show();

    }



    class EstadoPedidoResultsHolder extends RecyclerView.ViewHolder{

        TextView item_pedido_producto_ESTADO,item_pedido_producto_FECHA;


        EstadoPedidoResultsHolder(@NonNull View itemView) {
            super(itemView);
            item_pedido_producto_ESTADO=itemView.findViewById(R.id.item_pedido_producto_ESTADO);
            item_pedido_producto_FECHA=itemView.findViewById(R.id.item_pedido_producto_FECHA);

        }
    }


    private void estado_mensaje(Orden_estado_general orden, EstadoPedidoResultsHolder holder){



        if(orden.getIdestadogeneral()==1 ){
            //EL PEDIDO YA FUE CONFIRMADO Y ESTA SIENDO PREPARADO
            holder.item_pedido_producto_ESTADO.setText(R.string.estado_orden_1);
        }


        if(orden.getIdestadogeneral()==2){
            //EL PEDIDO YA SE ENCUENTRA LISTO PARA ENTREGARSE
            holder.item_pedido_producto_ESTADO.setText(R.string.estado_orden_2);
        }


        if(orden.getIdestadogeneral()==3 ){
            //EL PEDIDO FUE RECIBIDO POR EL REPARTIDOR
            holder.item_pedido_producto_ESTADO.setText(R.string.estado_orden_3);
        }

        if(orden.getIdestadogeneral()==4 ){
            //EL PEDIDO LLEGO AL DESTINO
            holder.item_pedido_producto_ESTADO.setText(R.string.estado_orden_4);
        }

        if(orden.getIdestadogeneral()==5){

            //EL PEDIDO FUE CANCELADO
            holder.item_pedido_producto_ESTADO.setText(R.string.estado_orden_5);


        }

    }

}
