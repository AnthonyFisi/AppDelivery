package com.example.yego.View.OrdenUI.DetallesUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.Settings;
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
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Modelo.Gson.Orden_estado_restauranteGson;
import com.example.yego.Repository.Modelo.Gson.RepartidorInformationGson;
import com.example.yego.Repository.Modelo.Orden_estado_general;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.View.InicioActivity;
import com.example.yego.View.OrdenUI.SupervisarOrden.FragmentSupervisarOrdenArgs;
import com.example.yego.ViewModel.Orden_estado_generalViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.text.ParseException;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class DetalleOrdenFragment extends Fragment implements OnMapReadyCallback {


    public static final String TAG = "";

    private static final String GSON_ORDEN ="gson_orden" ;

    private TextView fragment_supervisar_orden_NOMBRE_REPARTIDOR;

    private ImageView fragment_supervisar_orden_IMAGEN_REPARTIDOR, fragment_supervisar_orden_NUMERO_CELULAR,
            fragment_supervisar_orden_WHATSAPP,fragment_supervisar_orden_IMAGEN_EMPRESA, fragment_supervisar_orden_NUMERO_CELULAR_EMPRESA,fragment_supervisar_orden_WHATSAPP_EMPRESA;

    private ImageView ic_back,icono_estado_1,icono_estado_1_enable,icono_estado_2,icono_estado_2_enable,icono_estado_3,icono_estado_3_enable,icono_estado_4,icono_estado_4_enable,icono_estado_5,icono_estado_5_enable;

    private TextView  nombre_estado_1,fecha_estado_1,nombre_estado_2,fecha_estado_2,nombre_estado_3,fecha_estado_3,nombre_estado_4,fecha_estado_4,nombre_estado_5,fecha_estado_5;

    private TextView registro_venta_TIPO_PAGO,registro_venta_TIEMPO_APROXIMADO,registro_venta_SUB_TOTAL,registro_venta_COSTO_DELIVERY,registro_venta_COSTO_TOTAL;

    private LinearLayout conexion_estado_2,conexion_estado_3,conexion_estado_4,conexion_estado_5;

    private LinearLayout fragment_supervisar_orden_BUSCANDO_REPARTIDOR,fragment_supervisar_orden_REPARTIDOR;

    private RecyclerView fragment_detalle_orden_LISTA_ESTADOS,fragment_detalle_orden_LISTA_PRODUCTOS;

    private Button fragment_detalle_orden_PEDIDO_RECIBIDO;

    private TextView registro_venta_FECHA_ENTREGA,registro_venta_COMENTARIOS,fragment_supervisar_orden_NOMBRE_EMPRESA,registro_venta_UBICACION,
            fragment_supervisar_orden_UBICACION_EMPRESA;

    private GsonOrden orden;


    private CardView cardView_gps;

    private ProductPedidoResultsAdapter adapter_producto;

    private NavController mNavController;

    private boolean enable_button_confirmar;

    private Orden_estado_generalViewModel viewModel;

    private GoogleMap mMap;

    public static DetalleOrdenFragment newInstance(GsonOrden gsonOrden) {
        DetalleOrdenFragment fragment = new DetalleOrdenFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable(GSON_ORDEN,gsonOrden);
        fragment.setArguments(bundle);
        return fragment;
    }


    public DetalleOrdenFragment() {
        // Required empty public constructor
    }


  /*  public static DetalleOrdenFragment newInstance(String param1, String param2) {
        DetalleOrdenFragment fragment = new DetalleOrdenFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }*/

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
        return inflater.inflate(R.layout.fragment_detalle_orden, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     /*   if(getArguments()!=null){
            DetalleOrdenFragmentArgs args=DetalleOrdenFragmentArgs.fromBundle(getArguments());

            orden=args.getOrden();
        }

        mNavController=NavHostFragment.findNavController(this);

*/

        if(getArguments()!=null){

            DetalleOrdenFragmentArgs args=DetalleOrdenFragmentArgs.fromBundle(getArguments());

            orden=args.getOrden();
        }

        mNavController= Navigation.findNavController(view);

        System.out.println(orden.getLista_orden_general().size()+"TAMAÑO DE LA LISTA");

        MapView mapView = (MapView) view.findViewById(R.id.google_map_supervisar_orden);

        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment


        declararDataWidget(view);

        setDataWidget();

        setDataEstadoPedido();

        initRecyclerViewData();

        funPusher();

        onClickPedidoRecibido();

        funPusherRepartidor();


        for(Orden_estado_general estado:orden.getLista_orden_general()){
            if(estado.getIdestadogeneral()==4){
                enable_button_confirmar=true;
                fragment_detalle_orden_PEDIDO_RECIBIDO.setVisibility(View.VISIBLE);
            }
        }

        mapView.setOnClickListener(v->{
            NavController nav= Navigation.findNavController(v);

            DetalleOrdenFragmentDirections.ActionDetalleOrdenFragmentToFragmentSupervisarOrden action=DetalleOrdenFragmentDirections.actionDetalleOrdenFragmentToFragmentSupervisarOrden(orden);
            nav.navigate(action);
        });


        clickToGps();

        ic_back.setOnClickListener(v->{
            getActivity().onBackPressed();
        });


    }

    private void declararDataWidget(View view){
        fragment_supervisar_orden_IMAGEN_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_IMAGEN_REPARTIDOR);
        fragment_supervisar_orden_NOMBRE_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_NOMBRE_REPARTIDOR);
        fragment_supervisar_orden_NUMERO_CELULAR=view.findViewById(R.id.fragment_supervisar_orden_NUMERO_CELULAR);
        fragment_supervisar_orden_WHATSAPP=view.findViewById(R.id.fragment_supervisar_orden_WHATSAPP);

        fragment_supervisar_orden_IMAGEN_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_IMAGEN_EMPRESA);
        fragment_supervisar_orden_NOMBRE_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_NOMBRE_EMPRESA);
        fragment_supervisar_orden_NUMERO_CELULAR_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_NUMERO_CELULAR_EMPRESA);
        fragment_supervisar_orden_WHATSAPP_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_WHATSAPP_EMPRESA);
        fragment_supervisar_orden_UBICACION_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_UBICACION_EMPRESA);

        fragment_supervisar_orden_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_REPARTIDOR);
        fragment_supervisar_orden_BUSCANDO_REPARTIDOR=view.findViewById(R.id.fragment_supervisar_orden_BUSCANDO_REPARTIDOR);
        fragment_detalle_orden_LISTA_PRODUCTOS=view.findViewById(R.id.fragment_detalle_orden_LISTA_PRODUCTOS);
        //fragment_detalle_orden_LISTA_ESTADOS=view.findViewById(R.id.fragment_detalle_orden_LISTA_ESTADOS);
        fragment_detalle_orden_PEDIDO_RECIBIDO=view.findViewById(R.id.fragment_detalle_orden_PEDIDO_RECIBIDO);


        ic_back=view.findViewById(R.id.ic_back);

        registro_venta_FECHA_ENTREGA=view.findViewById(R.id.registro_venta_FECHA_ENTREGA);
        registro_venta_COMENTARIOS=view.findViewById(R.id.registro_venta_COMENTARIOS);

        registro_venta_UBICACION=view.findViewById(R.id.registro_venta_UBICACION);
        //DECLARAMOS LOS ESTADOS DEL PEDIDO

        icono_estado_1=view.findViewById(R.id.icono_estado_1);
        icono_estado_2=view.findViewById(R.id.icono_estado_2);
        icono_estado_3=view.findViewById(R.id.icono_estado_3);
        icono_estado_4=view.findViewById(R.id.icono_estado_4);
        icono_estado_5=view.findViewById(R.id.icono_estado_5);


        nombre_estado_1=view.findViewById(R.id.nombre_estado_1);
        nombre_estado_2=view.findViewById(R.id.nombre_estado_2);
        nombre_estado_3=view.findViewById(R.id.nombre_estado_3);
        nombre_estado_4=view.findViewById(R.id.nombre_estado_4);
        nombre_estado_5=view.findViewById(R.id.nombre_estado_5);


        conexion_estado_2=view.findViewById(R.id.conexion_estado_2);
        conexion_estado_3=view.findViewById(R.id.conexion_estado_3);
        conexion_estado_4=view.findViewById(R.id.conexion_estado_4);
        conexion_estado_5=view.findViewById(R.id.conexion_estado_5);


        icono_estado_1_enable=view.findViewById(R.id.icono_estado_1_enable);
        icono_estado_2_enable=view.findViewById(R.id.icono_estado_2_enable);
        icono_estado_3_enable=view.findViewById(R.id.icono_estado_3_enable);
        icono_estado_4_enable=view.findViewById(R.id.icono_estado_4_enable);
        icono_estado_5_enable=view.findViewById(R.id.icono_estado_5_enable);


        fecha_estado_1=view.findViewById(R.id.fecha_estado_1);
        fecha_estado_2=view.findViewById(R.id.fecha_estado_2);
        fecha_estado_3=view.findViewById(R.id.fecha_estado_3);
        fecha_estado_4=view.findViewById(R.id.fecha_estado_4);
        fecha_estado_5=view.findViewById(R.id.fecha_estado_5);



        registro_venta_SUB_TOTAL=view.findViewById(R.id.registro_venta_SUB_TOTAL);
        registro_venta_COSTO_DELIVERY=view.findViewById(R.id.registro_venta_COSTO_DELIVERY);
        registro_venta_COSTO_TOTAL=view.findViewById(R.id.registro_venta_COSTO_TOTAL);

        registro_venta_TIPO_PAGO=view.findViewById(R.id.registro_venta_TIPO_PAGO);
        registro_venta_TIEMPO_APROXIMADO=view.findViewById(R.id.registro_venta_TIEMPO_APROXIMADO);

        cardView_gps=view.findViewById(R.id.cardView_gps);

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



        if (orden.getDetalle_orden().getUrlfoto_empresa()!= null) {
            String imageUrl = orden.getDetalle_orden().getUrlfoto_empresa()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .into(fragment_supervisar_orden_IMAGEN_EMPRESA);
        }

        fragment_supervisar_orden_NUMERO_CELULAR_EMPRESA.setOnClickListener(v->{
            Intent intent= new Intent((Intent.ACTION_CALL));
            intent.setData(Uri.parse("tel:"+orden.getDetalle_orden().getCelular_empresa().trim()));

            if(validarPermiso()){

                startActivity(intent);

            }
           /*
            if(checkSelfPermission(getContext(), CALL_PHONE )!= PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Habilitar el permiso",Toast.LENGTH_SHORT).show();
                requestPermission();
            }else{
            }*/
        });

        //fragment_supervisar_orden_WHATSAPP_EMPRESA=view.findViewById(R.id.fragment_supervisar_orden_WHATSAPP_EMPRESA);

        fragment_supervisar_orden_UBICACION_EMPRESA.setText(orden.getDetalle_orden().getUbicacion_nombre());
        fragment_supervisar_orden_NOMBRE_EMPRESA.setText(orden.getDetalle_orden().getNombre_empresa());

        registro_venta_UBICACION.setText(Cliente_Bi.getCliente().getUbicacion_nombre());

        if(orden.getUsuario()!=null){

            setDataRepartidor();

        }else{
            fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.VISIBLE);
        }

        fragment_supervisar_orden_NUMERO_CELULAR.setOnClickListener(v->{
            Intent intent= new Intent((Intent.ACTION_CALL));
            intent.setData(Uri.parse("tel:"+orden.getUsuario().getCelular().trim()));

            if(validarPermiso()){
                startActivity(intent);
            }
            /*if(checkSelfPermission(getContext(), CALL_PHONE )!= PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Habilitar el permiso",Toast.LENGTH_SHORT).show();
                requestPermission();
            }else{
                startActivity(intent);
            }*/
        });
    }



    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String []{CALL_PHONE},1);
    }
    @SuppressLint("ResourceType")
    private void setDataEstadoPedido(){
        for(Orden_estado_general estado:orden.getLista_orden_general()){

            if(estado.getIdestadogeneral()==1 && orden.getLista_orden_general().size() == 1){

                icono_estado_1.setVisibility(View.VISIBLE);
                icono_estado_1_enable.setVisibility(View.GONE);
                String tiempo=estado.getTiempo_aproximado()+"min , "+convertTimestampToHour(estado.getFecha());

                nombre_estado_1.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                nombre_estado_1.setTextColor(getResources().getColor(R.color.original_color));
                nombre_estado_1.setTextSize(24);

                fecha_estado_1.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                fecha_estado_1.setTextSize(16);

                fecha_estado_1.setText(tiempo);

            }else{
                icono_estado_1.setVisibility(View.GONE);
                icono_estado_1_enable.setVisibility(View.VISIBLE);

                nombre_estado_1.setTextAppearance(getContext(),R.font.proxima_nova_alt_light);
                nombre_estado_1.setTextColor(getResources().getColor(R.color.subtitulolight));
                nombre_estado_1.setTextSize(14);

                fecha_estado_1.setText("");

            }

            if(estado.getIdestadogeneral()==2 && orden.getLista_orden_general().size() == 2){

                icono_estado_2.setVisibility(View.VISIBLE);
                icono_estado_2_enable.setVisibility(View.GONE);
                conexion_estado_2.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+convertTimestampToHour(estado.getFecha());

                nombre_estado_2.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                nombre_estado_2.setTextColor(getResources().getColor(R.color.original_color));
                nombre_estado_2.setTextSize(24);

                fecha_estado_2.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                fecha_estado_2.setTextSize(16);


                fecha_estado_2.setText(tiempo);
            }else{
                icono_estado_2.setVisibility(View.GONE);
                icono_estado_2_enable.setVisibility(View.VISIBLE);
                nombre_estado_2.setTextAppearance(getContext(),R.font.proxima_nova_alt_light);
                nombre_estado_2.setTextColor(getResources().getColor(R.color.subtitulolight));
                nombre_estado_2.setTextSize(14);

                fecha_estado_2.setText("");

            }

            if(estado.getIdestadogeneral()==6 && orden.getLista_orden_general().size() == 3){
                icono_estado_5.setVisibility(View.VISIBLE);
                icono_estado_5_enable.setVisibility(View.GONE);
                conexion_estado_5.setBackgroundColor(getResources().getColor(R.color.original_color));

                nombre_estado_5.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                nombre_estado_5.setTextColor(getResources().getColor(R.color.original_color));
                nombre_estado_5.setTextSize(24);

                String tiempo=estado.getTiempo_aproximado()+"min , "+convertTimestampToHour(estado.getFecha());


                fecha_estado_5.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                fecha_estado_5.setTextSize(16);
                fecha_estado_5.setText(tiempo);
            }else{

                icono_estado_5.setVisibility(View.GONE);
                icono_estado_5_enable.setVisibility(View.VISIBLE);

                nombre_estado_5.setTextAppearance(getContext(),R.font.proxima_nova_alt_light);
                nombre_estado_5.setTextColor(getResources().getColor(R.color.subtitulolight));
                nombre_estado_5.setTextSize(14);

                fecha_estado_5.setText("");

            }


            if(estado.getIdestadogeneral()==3 && orden.getLista_orden_general().size() == 4){
                icono_estado_3.setVisibility(View.VISIBLE);
                icono_estado_3_enable.setVisibility(View.GONE);
                conexion_estado_3.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+convertTimestampToHour(estado.getFecha());


                nombre_estado_3.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                nombre_estado_3.setTextColor(getResources().getColor(R.color.original_color));
                nombre_estado_3.setTextSize(24);

                fecha_estado_3.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                fecha_estado_3.setTextSize(16);

                fecha_estado_3.setText(tiempo);
            }else{
                icono_estado_3.setVisibility(View.GONE);
                icono_estado_3_enable.setVisibility(View.VISIBLE);

                nombre_estado_3.setTextAppearance(getContext(),R.font.proxima_nova_alt_light);
                nombre_estado_3.setTextColor(getResources().getColor(R.color.subtitulolight));
                nombre_estado_3.setTextSize(14);

                fecha_estado_3.setText("");

            }
            if(estado.getIdestadogeneral()==4 && orden.getLista_orden_general().size() == 5){
                icono_estado_4.setVisibility(View.VISIBLE);
                icono_estado_4_enable.setVisibility(View.GONE);
                conexion_estado_4.setBackgroundColor(getResources().getColor(R.color.original_color));
                String tiempo=estado.getTiempo_aproximado()+"min , "+convertTimestampToHour(estado.getFecha());

                nombre_estado_4.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                nombre_estado_4.setTextColor(getResources().getColor(R.color.original_color));
                nombre_estado_4.setTextSize(24);

                fecha_estado_4.setTextAppearance(getContext(),R.font.proxima_nova_alt_bold);
                fecha_estado_4.setTextSize(16);

                fecha_estado_4.setText(tiempo);
            }else{
                icono_estado_4.setVisibility(View.GONE);
                icono_estado_4_enable.setVisibility(View.VISIBLE);

                nombre_estado_4.setTextAppearance(getContext(),R.font.proxima_nova_alt_light);
                nombre_estado_4.setTextColor(getResources().getColor(R.color.subtitulolight));
                nombre_estado_4.setTextSize(14);

                fecha_estado_4.setText("");

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
            String imageUrl = orden.getUsuario().getFoto()
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


                            if(orden.getDetalle_orden().getIdventa()==object.getListaOrden_estado_general().get(0).getIdventa()){

                                orden.setLista_orden_general(object.getListaOrden_estado_general());

                                setDataRepartidor();

                                for(Orden_estado_general estado:object.getListaOrden_estado_general()){
                                    if(estado.getIdestadogeneral()==4){
                                        enable_button_confirmar=true;
                                        fragment_detalle_orden_PEDIDO_RECIBIDO.setVisibility(View.VISIBLE);
                                    }
                                }

                                setDataEstadoPedido();
                            }



                            //adapter_estado.addItemList(getContext(),object.getListaOrden_estado_general());





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

                            OrdenGeneralGson.cantidadOrden=-1;

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

                            if(orden.getDetalle_orden().getIdventa()==object.getIdventa()){

                                orden.setUsuario(object.getUsuario_general());
                                fragment_supervisar_orden_BUSCANDO_REPARTIDOR.setVisibility(View.GONE);
                                setDataRepartidor();

                            }



                            //orden.setUsuario(object.getUsuario_general());
                            //adapter.modifiedListWithPushActionWithRepartidorInformation(object.getIdventa(),object.getUsuario_general());

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


    private void clickToGps(){

        cardView_gps.setOnClickListener(v->{
        //    lsl

            NavController nav= Navigation.findNavController(v);

            DetalleOrdenFragmentDirections.ActionDetalleOrdenFragmentToFragmentSupervisarOrden action=DetalleOrdenFragmentDirections.actionDetalleOrdenFragmentToFragmentSupervisarOrden(orden);
            nav.navigate(action);

        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        double latOri= Double.valueOf(Cliente_Bi.getCliente().getMaps_coordenada_x().trim());
        double logOri=Double.valueOf(Cliente_Bi.getCliente().getMaps_coordenada_y().trim());


        LatLng locationOrigen = new LatLng(latOri,logOri );

        // Creating MarkerOptions
        MarkerOptions options2 = new MarkerOptions();

        // Setting the position of the marker
        options2.position(locationOrigen);
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.markergps));
        mMap.addMarker(options2).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationOrigen, 18));
    }


    private String convertTimestampToHour(Timestamp hora){

        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        String output = null;
        try{
            //Conversion of input String to date
            date= df.parse(hora.toString());
            //old date format to new date format
            output = outputformat.format(date);
            System.out.println(output);
        }catch(ParseException pe){
            pe.printStackTrace();
        }

        return output;
    }



    private boolean validarPermiso() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(getContext(),CALL_PHONE)== PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CALL_PHONE)) ){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CALL_PHONE},100);
        }

        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==1 && grantResults[0]== PERMISSION_GRANTED){

                Toast.makeText(getContext(),"Ahora si puedes llamar",Toast.LENGTH_LONG).show();

            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getActivity().getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{CALL_PHONE},100);
                }
            }
        });
        dialogo.show();
    }


}
