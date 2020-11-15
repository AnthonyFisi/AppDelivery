package com.example.yego.View.OrdenUI;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Gson.RepartidorInformationGson;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.OrdenUI.DetallesUI.DetalleOrdenFragmentArgs;
import com.example.yego.View.OrdenUI.DetallesUI.DetalleOrdenFragmentDirections;
import com.example.yego.View.OrdenUI.DetallesUI.ProductPedidoResultsAdapter;
import com.example.yego.ViewModel.Orden_estado_generalViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesaFragment extends Fragment {

    public static final String TAG = "";

    private static final String GSON_ORDEN ="gson_orden" ;

    private TextView fragment_supervisar_orden_NOMBRE_REPARTIDOR;

    private ImageView fragment_supervisar_orden_IMAGEN_REPARTIDOR,fragment_supervisar_orden_NUMERO_CELULAR,fragment_supervisar_orden_WHATSAPP;

    private ImageView icono_estado_1,icono_estado_1_enable,icono_estado_2,icono_estado_2_enable,icono_estado_3,icono_estado_3_enable,icono_estado_4,icono_estado_4_enable;

    private TextView  nombre_estado_1,fecha_estado_1,nombre_estado_2,fecha_estado_2,nombre_estado_3,fecha_estado_3,nombre_estado_4,fecha_estado_4;

    private TextView registro_venta_TIPO_PAGO,registro_venta_TIEMPO_APROXIMADO,registro_venta_SUB_TOTAL,registro_venta_COSTO_DELIVERY,registro_venta_COSTO_TOTAL;

    private LinearLayout conexion_estado_2,conexion_estado_3,conexion_estado_4;

    private LinearLayout fragment_supervisar_orden_BUSCANDO_REPARTIDOR,fragment_supervisar_orden_REPARTIDOR;

    private RecyclerView fragment_detalle_orden_LISTA_ESTADOS,fragment_detalle_orden_LISTA_PRODUCTOS;

    private Button fragment_detalle_orden_PEDIDO_RECIBIDO;

    private TextView registro_venta_FECHA_ENTREGA,registro_venta_COMENTARIOS;

    private GsonOrden orden;

    // private EstadoPedidoResultsAdapter adapter_estado;

    private ProductPedidoResultsAdapter adapter_producto;

    private NavController mNavController;

    private boolean enable_button_confirmar;

    private Orden_estado_generalViewModel viewModel;

    private ImageView ic_back;


    public MesaFragment() {
        // Required empty public constructor
    }


    public static MesaFragment newInstance(GsonOrden gsonOrden) {
        MesaFragment fragment = new MesaFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable(GSON_ORDEN,gsonOrden);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enable_button_confirmar=false;
       /* if(getArguments()!=null){
            Bundle bundle=getArguments();
            orden=(GsonOrden) bundle.getSerializable(GSON_ORDEN);
        }*/

        // adapter_estado= new EstadoPedidoResultsAdapter();

        adapter_producto= new ProductPedidoResultsAdapter();

        viewModel= new ViewModelProvider(this).get(Orden_estado_generalViewModel.class);
        viewModel.init();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mesa, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null){
            DetalleOrdenFragmentArgs args=DetalleOrdenFragmentArgs.fromBundle(getArguments());

            orden=args.getOrden();
        }

        mNavController= NavHostFragment.findNavController(this);



        declararDataWidget(view);

        setDataWidget();

        setDataEstadoPedido();

        initRecyclerViewData();

        funPusher();

        onClickPedidoRecibido();

        funPusherRepartidor();

        ic_back.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

    }

    private void declararDataWidget(View view){
        fragment_supervisar_orden_IMAGEN_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_IMAGEN_EMPRESA);
        fragment_supervisar_orden_NOMBRE_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_NOMBRE_REPARTIDOR);
        fragment_supervisar_orden_NUMERO_CELULAR=view.findViewById(R.id.fragment_supervisar_orden_NUMERO_CELULAR);
        fragment_supervisar_orden_WHATSAPP=view.findViewById(R.id.fragment_supervisar_orden_WHATSAPP);
        fragment_supervisar_orden_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_REPARTIDOR);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_BUSCANDO_REPARTIDOR);
        fragment_detalle_orden_LISTA_PRODUCTOS=view.findViewById(R.id.fragment_detalle_orden_LISTA_PRODUCTOS);
        //fragment_detalle_orden_LISTA_ESTADOS=view.findViewById(R.id.fragment_detalle_orden_LISTA_ESTADOS);
        fragment_detalle_orden_PEDIDO_RECIBIDO=view.findViewById(R.id.fragment_detalle_orden_PEDIDO_RECIBIDO);

        ic_back=view.findViewById(R.id.ic_back);


        registro_venta_FECHA_ENTREGA=view.findViewById(R.id.registro_venta_FECHA_ENTREGA);
        registro_venta_COMENTARIOS=view.findViewById(R.id.registro_venta_COMENTARIOS);

        //DECLARAMOS LOS ESTADOS DEL PEDIDO

        icono_estado_1=view.findViewById(R.id.icono_estado_1);
        icono_estado_2=view.findViewById(R.id.icono_estado_2);
        icono_estado_3=view.findViewById(R.id.icono_estado_3);
        icono_estado_4=view.findViewById(R.id.icono_estado_4);


        nombre_estado_1=view.findViewById(R.id.nombre_estado_1);
        nombre_estado_2=view.findViewById(R.id.nombre_estado_2);
        nombre_estado_3=view.findViewById(R.id.nombre_estado_3);
        nombre_estado_4=view.findViewById(R.id.nombre_estado_4);


        conexion_estado_2=view.findViewById(R.id.conexion_estado_2);
        conexion_estado_3=view.findViewById(R.id.conexion_estado_3);
        conexion_estado_4=view.findViewById(R.id.conexion_estado_4);

        icono_estado_1_enable=view.findViewById(R.id.icono_estado_1_enable);
        icono_estado_2_enable=view.findViewById(R.id.icono_estado_2_enable);
        icono_estado_3_enable=view.findViewById(R.id.icono_estado_3_enable);
        icono_estado_4_enable=view.findViewById(R.id.icono_estado_4_enable);

        fecha_estado_1=view.findViewById(R.id.fecha_estado_1);
        fecha_estado_2=view.findViewById(R.id.fecha_estado_2);
        fecha_estado_3=view.findViewById(R.id.fecha_estado_3);
        fecha_estado_4=view.findViewById(R.id.fecha_estado_4);



        registro_venta_SUB_TOTAL=view.findViewById(R.id.registro_venta_SUB_TOTAL);
        registro_venta_COSTO_DELIVERY=view.findViewById(R.id.registro_venta_COSTO_DELIVERY);
        registro_venta_COSTO_TOTAL=view.findViewById(R.id.registro_venta_COSTO_TOTAL);

        registro_venta_TIPO_PAGO=view.findViewById(R.id.registro_venta_TIPO_PAGO);
        registro_venta_TIEMPO_APROXIMADO=view.findViewById(R.id.registro_venta_TIEMPO_APROXIMADO);

    }


    private void setDataWidget(){

        String day = (new SimpleDateFormat("EEEE")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "Tuesday"

        String month = (new SimpleDateFormat("MMMM")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "April"

        String year = (new SimpleDateFormat("yyyy")).format(orden.getDetalle_orden().getVenta_fechaentrega().getTime()); // "April"

        int numberDay=orden.getDetalle_orden().getVenta_fechaentrega().getDay();

        //int year=orden.getDetalle_orden().getVenta_fechaentrega().getYear();

        String fecha=day+" "+numberDay+" de "+month+","+year+" - "+orden.getDetalle_orden().getHorario_nombre();
        registro_venta_COMENTARIOS.setText(orden.getDetalle_orden().getComentario());
        registro_venta_FECHA_ENTREGA.setText(fecha);

        registro_venta_SUB_TOTAL.setText(String.valueOf(orden.getDetalle_orden().getVenta_costototal()));
        registro_venta_COSTO_DELIVERY.setText(String.valueOf(orden.getDetalle_orden().getVenta_costodelivery()));
        float total=(orden.getDetalle_orden().getVenta_costototal()+orden.getDetalle_orden().getVenta_costodelivery());
        registro_venta_COSTO_TOTAL.setText(String.valueOf(total));

        registro_venta_TIPO_PAGO.setText(orden.getDetalle_orden().getTipopago_nombre());
        registro_venta_TIEMPO_APROXIMADO.setText(orden.getDetalle_orden().getTiempo_espera());



        fragment_supervisar_orden_REPARTIDOR.setVisibility(View.GONE);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);




        if(orden.getUsuario()!=null){

            setDataRepartidor();

        }else{
            fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.VISIBLE);
        }

        fragment_supervisar_orden_NUMERO_CELULAR.setOnClickListener(v->{
            Intent intent= new Intent((Intent.ACTION_CALL));
            intent.setData(Uri.parse("tel"+orden.getUsuario().getCelular().trim()));
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Habilitar el permiso",Toast.LENGTH_SHORT).show();
                requestPermission();
            }else{
                startActivity(intent);
            }
        });
    }



    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String []{Manifest.permission.CALL_PHONE},1);
    }
    private void setDataEstadoPedido(){
        for(Orden_estado_general estado:orden.getLista_orden_general()){

            if(estado.getIdestadogeneral()==1){

                icono_estado_1.setVisibility(View.VISIBLE);
                icono_estado_1_enable.setVisibility(View.GONE);
                String tiempo=estado.getTiempo_aproximado()+"min , "+estado.getFecha();
                fecha_estado_1.setText(tiempo);
            }

            if(estado.getIdestadogeneral()==2){

                icono_estado_2.setVisibility(View.VISIBLE);
                icono_estado_2_enable.setVisibility(View.GONE);
                conexion_estado_2.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+estado.getFecha();
                fecha_estado_2.setText(tiempo);
            }
            if(estado.getIdestadogeneral()==3){
                icono_estado_3.setVisibility(View.VISIBLE);
                icono_estado_3_enable.setVisibility(View.GONE);
                conexion_estado_3.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+estado.getFecha();
                fecha_estado_3.setText(tiempo);
            }
            if(estado.getIdestadogeneral()==4){
                icono_estado_4.setVisibility(View.VISIBLE);
                icono_estado_4_enable.setVisibility(View.GONE);
                conexion_estado_4.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+estado.getFecha();
                fecha_estado_4.setText(tiempo);
            }

        }

    }

    private void initRecyclerViewData(){

        //ESPERAR POR LA LISTA DE ESTADOS QUE VA VENIR POR UN PUSHER O POR OTRO METODO
        adapter_producto.setResults(orden.getLista_productos());

        fragment_detalle_orden_LISTA_PRODUCTOS.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        new Handler().postDelayed(() -> {

            fragment_detalle_orden_LISTA_PRODUCTOS.setAdapter(adapter_producto);

        },3000);





       /* adapter_estado.setResults(orden.getLista_orden_general());

        fragment_detalle_orden_LISTA_ESTADOS.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        fragment_detalle_orden_LISTA_ESTADOS.setAdapter(adapter_estado);*/


    }


    private void setDataRepartidor(){
        fragment_supervisar_orden_REPARTIDOR.setVisibility(View.VISIBLE);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);
        if (orden.getUsuario().getFoto()!= null) {
            String imageUrl = orden.getDetalle_orden().getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .into(fragment_supervisar_orden_IMAGEN_REPARTIDOR);
        }

        String nombre_apellido=orden.getUsuario().getNombre()+orden.getUsuario().getApellido();
        fragment_supervisar_orden_NOMBRE_REPARTIDOR.setText(nombre_apellido);
    }


    private void funPusher(){
        InicioActivity.channel.bind("my-event", (channelName, eventName, data) -> {
            System.out.println(data+"los datossssssssssssssssss");

            if(getActivity() !=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            JsonParser parser = new JsonParser();
                            JsonElement mJson =  parser.parse(data);
                            Gson gson = new Gson();


                            Orden_estado_restauranteGson object = gson.fromJson(mJson, Orden_estado_restauranteGson.class);

                            orden.setLista_orden_general(object.getListaOrden_estado_general());


                            //adapter_estado.addItemList(getContext(),object.getListaOrden_estado_general());


                            setDataRepartidor();

                            for(Orden_estado_general estado:object.getListaOrden_estado_general()){
                                if(estado.getIdestadogeneral()==4){
                                    enable_button_confirmar=true;
                                }
                            }

                            setDataEstadoPedido();


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }


    private void onClickPedidoRecibido(){



        fragment_detalle_orden_PEDIDO_RECIBIDO.setOnClickListener( v->{
            for(Orden_estado_general estado:orden.getLista_orden_general()){
                if(estado.getIdestadogeneral()==4){
                    enable_button_confirmar=true;
                }
            }
            if(enable_button_confirmar){

                // fragment_detalle_orden_PEDIDO_RECIBIDO.setEnabled(false);



                Orden_estado_general estado=new Orden_estado_general();
                estado.setIdventa(orden.getDetalle_orden().getIdventa());
                estado.setIdestadogeneral(5);
                estado.setTiempo_aproximado("");

                viewModel.registrar_estado_general(estado);

                viewModel.getOrden_estado_generalLiveData().observe(this, new Observer<Orden_estado_general>() {
                    @Override
                    public void onChanged(Orden_estado_general orden_estado_general) {
                        if(orden_estado_general!=null){
                            DetalleOrdenFragmentDirections.ActionDetalleOrdenFragmentToConfirmarEntregaFragment action=DetalleOrdenFragmentDirections.actionDetalleOrdenFragmentToConfirmarEntregaFragment(orden);
                            mNavController.navigate(action);

                        }else{
                            Toast.makeText(getContext(),"Lo sentimos problemas en la red,vuelva a intentarlo",Toast.LENGTH_LONG).show();

                        }
                    }
                });



            }else {
                Toast.makeText(getContext(),"El repartidor aun no llega,aun no puede confirmar el pedido",Toast.LENGTH_LONG).show();
            }


        });



    }


    private void funPusherRepartidor(){
        InicioActivity.channel_repartidor.bind("my-event", (channelName, eventName, data) -> {
            System.out.println(data+"los datossssssssssssssssss");

            if(getActivity() !=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            JsonParser parser = new JsonParser();
                            JsonElement mJson =  parser.parse(data);
                            Gson gson = new Gson();


                            RepartidorInformationGson object = gson.fromJson(mJson, RepartidorInformationGson.class);

                            //orden.getUsuario()=;
                            orden.setUsuario(object.getUsuario_general());

                            fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);

                            //orden.setUsuario(object.getUsuario_general());
                            //adapter.modifiedListWithPushActionWithRepartidorInformation(object.getIdventa(),object.getUsuario_general());
                            setDataRepartidor();

                            //listenChangeLocation();


                        }catch (Exception e){

                            System.out.println(e.getMessage());
                        }
                    }
                });
            }

        });
        InicioActivity.pusher.connect();
    }


}
