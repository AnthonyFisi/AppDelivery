package com.example.yego.View.OrdenUI;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Modelo.Usuario;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOrdenResultsAdapter extends  RecyclerView.Adapter<ListOrdenResultsAdapter.ListOrdenResultsHolder>{

    private List<GsonOrden> results= new ArrayList<>();
    private ClickOrden mClickOrden;

    @NonNull
    @Override
    public ListOrdenResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_orden,parent,false);
        return new ListOrdenResultsAdapter.ListOrdenResultsHolder(view ,mClickOrden);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrdenResultsHolder holder, int position) {

        GsonOrden orden=results.get(position);

        if(position>=1){

            GsonOrden orden1=results.get(position-1);

            String patternFecha = "yyyy-MM-dd";


            @SuppressLint("SimpleDateFormat") DateFormat dateFormat1 = new SimpleDateFormat(patternFecha);
            String fechaPosition1=dateFormat1.format(orden1.getDetalle_orden().getVenta_fechaentrega());


            @SuppressLint("SimpleDateFormat") DateFormat dateFormat2 = new SimpleDateFormat(patternFecha);
            String fechaPosititon2=dateFormat2.format(orden.getDetalle_orden().getVenta_fechaentrega());
            System.out.println(fechaPosition1+" / "+fechaPosititon2+" 1 ");

            if(!fechaPosition1.equals(fechaPosititon2)){

                System.out.println(fechaPosition1+" / "+fechaPosititon2+ " 2 ");

                //ESCRIBIR EL DIA DE HOY
                String day = (new SimpleDateFormat("EEEE")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "Tuesday"
                holder.fecha_historial.setVisibility(View.VISIBLE);
                String dayUpper =dayAndmonthAndYear(orden.getDetalle_orden().getVenta_fechaentrega(),day);
                holder.item_list_orden_DIA.setText(dayUpper);
            }


        }else {

            //restaurante_pedido.getFechahistorial().

            String patternFecha = "yyyy-MM-dd";

            @SuppressLint("SimpleDateFormat") DateFormat dateFormat2 = new SimpleDateFormat(patternFecha);
            String fechaPosititon2=dateFormat2.format(orden.getDetalle_orden().getVenta_fechaentrega());
            String fechaNow= Calendar.getInstance().getTime().toString();

            System.out.println(fechaPosititon2+" 3 ");

            if(fechaNow.equals(fechaPosititon2)){
                //ESCRIBIR EL DIA DE HOY
                holder.fecha_historial.setVisibility(View.VISIBLE);
                holder.item_list_orden_DIA.setText("Hoy");
            }else {
                String day = (new SimpleDateFormat("EEEE")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "Tuesday"

                holder.fecha_historial.setVisibility(View.VISIBLE);
                String dayUpper =dayAndmonthAndYear(orden.getDetalle_orden().getVenta_fechaentrega(),day);
                holder.item_list_orden_DIA.setText(dayUpper);
            }
        }


        holder.esperando_pedido.setVisibility(View.GONE);
        holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.GONE);
        holder.item_list_orden_BUTTON_RECHAZADO.setVisibility(View.GONE);

        // ESCRIBIR UN MENSAJE EN FUNCION DEL CODIGO DE ESTADI QUE NOS DEVUELVA EL REPARTIDOR O EL NEGOCIO
        estado_mensaje(orden ,holder);


        String numero_pedido="#"+orden.getDetalle_orden().getIdventa();
        holder.item_list_orden_NUMERO_ORDEN.setText(numero_pedido);
        holder.item_list_orden_NOMBRE_EMPRESA.setText(orden.getDetalle_orden().getNombre_empresa());
        holder.item_list_orden_PRECIO_TOTAL.setText(String.valueOf(orden.getDetalle_orden().getVenta_costototal()));



/*
        String day = (new SimpleDateFormat("EEEE")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "Tuesday"



        int numberDay=orden.getDetalle_orden().getVenta_fechaentrega().getDay();

        //int year=orden.getDetalle_orden().getVenta_fechaentrega().getYear();

        String fecha=day+" "+numberDay+" de "+month+","+year;

        holder.item_list_orden_DIA.setText(fecha);
        */
        holder.item_list_orden_NOMBRE_HORARIO.setText(orden.getDetalle_orden().getHorario_nombre());



        if (orden.getDetalle_orden().getUrlfoto_empresa()!= null) {
            String imageUrl = orden.getDetalle_orden().getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.item_list_orden_IMAGEN_EMPRESA);
        }



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setResults( List<GsonOrden> results, ClickOrden clickOrden){
        this.mClickOrden=clickOrden;
        this.results=results;
        notifyDataSetChanged();
    }

    void modifiedListWithPushAction(int idventa, List<Orden_estado_general> listaEstados){
        int position=0;
        int i=0;

        for(GsonOrden orden:results){

            if(idventa==orden.getDetalle_orden().getIdventa()){
                position=i;
               // orden.getDetalle_orden().setIdestado_empresa(listaEstados.get(listaEstados.size()-1).getIdestadogeneral());
                orden.getDetalle_orden().setIdestado_general(listaEstados.get(listaEstados.size()-1).getIdestadogeneral());

                orden.setLista_orden_general(listaEstados);
            }

            i++;
        }

        System.out.println("ESTAMOS CAMBIANDO EL NUEVO ESTADO");

        notifyItemChanged(position);
        notifyDataSetChanged();

    }



    void modifiedListWithPushActionWithRepartidorInformation(int idventa, Usuario usuario){
        int position=0;
        int i=0;

        for(GsonOrden orden:results){

            if(idventa==orden.getDetalle_orden().getIdventa()){
                position=i;
                // orden.getDetalle_orden().setIdestado_empresa(listaEstados.get(listaEstados.size()-1).getIdestadogeneral());
                orden.setUsuario(usuario);
            }

            i++;
        }

        System.out.println("ESTAMOS CAMBIANDO EL NUEVO ESTADO");

        notifyItemChanged(position);
        notifyDataSetChanged();

    }



    public class ListOrdenResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ClickOrden mClickOrden;
        private TextView item_list_orden_DIA,item_list_orden_NOMBRE_HORARIO,item_list_orden_NUMERO_ORDEN,item_list_orden_NOMBRE_EMPRESA,item_list_orden_PRECIO_TOTAL;
        private Button item_list_orden_BUTTON_DETALLE,item_list_orden_BUTTON_RECHAZADO;
        private ImageView item_list_orden_IMAGEN_EMPRESA;
        private LinearLayout esperando_pedido,color_background,fecha_historial;

        private ProgressBar progressBarEstado;

        ListOrdenResultsHolder(@NonNull View itemView, ClickOrden clickOrden) {
            super(itemView);
            this.mClickOrden=clickOrden;
            item_list_orden_DIA=itemView.findViewById(R.id.item_list_orden_DIA);
            item_list_orden_NOMBRE_HORARIO=itemView.findViewById(R.id.item_list_orden_NOMBRE_HORARIO);
            item_list_orden_NUMERO_ORDEN=itemView.findViewById(R.id.item_list_orden_NUMERO_ORDEN);
            item_list_orden_NOMBRE_EMPRESA=itemView.findViewById(R.id.item_list_orden_NOMBRE_EMPRESA);
            item_list_orden_PRECIO_TOTAL=itemView.findViewById(R.id.item_list_orden_PRECIO_TOTAL);
            item_list_orden_BUTTON_DETALLE=itemView.findViewById(R.id.item_list_orden_BUTTON_DETALLE);
            item_list_orden_IMAGEN_EMPRESA=itemView.findViewById(R.id. item_list_orden_IMAGEN_EMPRESA);
            esperando_pedido=itemView.findViewById(R.id.esperando_pedido);
            progressBarEstado=itemView.findViewById(R.id.progressBarEstado);
            fecha_historial=itemView.findViewById(R.id.fecha_historial);
            item_list_orden_BUTTON_RECHAZADO=itemView.findViewById(R.id.item_list_orden_BUTTON_RECHAZADO);
            color_background=itemView.findViewById(R.id.color_background);
            item_list_orden_BUTTON_DETALLE.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(item_list_orden_BUTTON_DETALLE==view.findViewById(R.id.item_list_orden_BUTTON_DETALLE)){
                mClickOrden.clickOrdenDetail(results.get(getAdapterPosition()));
            }
        }
    }

    public interface ClickOrden{
        void clickOrdenDetail(GsonOrden orden);
    }


    private void estado_mensaje(GsonOrden orden,ListOrdenResultsHolder holder){

        if(orden.getDetalle_orden().getIdestado_general()==0 ){
            //EL PEDIDO ESTA ESPERANDO A SER CONFIRMADO
            holder.esperando_pedido.setVisibility(View.VISIBLE);

        }

        if(orden.getDetalle_orden().getIdestado_general()==1 ){
            //EL PEDIDO YA FUE CONFIRMADO Y ESTA SIENDO PREPARADO
            holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setProgress(20);
            //holder.item_list_orden_BUTTON_DETALLE.setText(R.string.estado_orden_1);
        }


        if(orden.getDetalle_orden().getIdestado_general()==2){
            //EL PEDIDO YA SE ENCUENTRA LISTO PARA ENTREGARSE
            holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setVisibility(View.VISIBLE);

            holder.progressBarEstado.setProgress(40);

           // holder.item_list_orden_BUTTON_DETALLE.setText(R.string.estado_orden_2);

        }

        if(orden.getDetalle_orden().getIdestado_general()==6 ){
            //EL PEDIDO FUE RECIBIDO POR EL REPARTIDOR
            holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setProgress(60);
            holder.progressBarEstado.setVisibility(View.VISIBLE);


            // holder.item_list_orden_BUTTON_DETALLE.setText(R.string.estado_orden_3);
        }

        if(orden.getDetalle_orden().getIdestado_general()==3 ){
            //EL PEDIDO FUE RECIBIDO POR EL REPARTIDOR
            holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setProgress(80);
            holder.progressBarEstado.setVisibility(View.VISIBLE);


           // holder.item_list_orden_BUTTON_DETALLE.setText(R.string.estado_orden_3);
        }

        if(orden.getDetalle_orden().getIdestado_general()==4 ){
            //EL PEDIDO LLEGO AL DESTINO
            holder.item_list_orden_BUTTON_DETALLE.setVisibility(View.VISIBLE);
            //holder.item_list_orden_BUTTON_DETALLE.setText(R.string.estado_orden_4);
            holder.progressBarEstado.setVisibility(View.VISIBLE);

            holder.progressBarEstado.setProgress(100);

        }

        if(orden.getDetalle_orden().getIdestado_general()==5){

            //EL PEDIDO FUE CANCELADO
            holder.item_list_orden_BUTTON_RECHAZADO.setVisibility(View.VISIBLE);
            holder.progressBarEstado.setVisibility(View.GONE);
            holder.progressBarEstado.setVisibility(View.GONE);

            holder.item_list_orden_BUTTON_RECHAZADO.setText(R.string.estado_orden_5);


        }


    }

    private String dayAndmonthAndYear(Timestamp fecha,String day){
        String month = (new SimpleDateFormat("MMMM")).format(fecha.getTime()); // "April"

      // String year = (new SimpleDateFormat("yyyy")).format(fecha.getTime()); // "April"

        String day1=String.valueOf(fecha.getDate());


        return day.substring(0, 1).toUpperCase() + day.substring(1)+" "+day1+" "+month;
    }
}
