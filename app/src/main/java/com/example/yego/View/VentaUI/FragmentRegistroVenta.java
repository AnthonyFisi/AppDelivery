package com.example.yego.View.VentaUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yego.Login.LoadingDialog;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Horario;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.Utils.ComnetarioViewModel;
import com.example.yego.View.MethodPayActivity;
import com.example.yego.View.MethodPayUI.FragmentTypesPay;
import com.example.yego.View.ProductDetailUI.FragmentComentarioDialog;
import com.example.yego.View.ProductDetailUI.FragmentProductoDetail;
import com.example.yego.View.VentaActivity;
import com.example.yego.ViewModel.VentaViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentRegistroVenta extends Fragment {

    private final int METHOD_PAY_ACTIVITY = 1;


    private TextView registro_venta_TIPO_PAGO,registro_venta_UBICACION,registro_venta_COMENTARIOS,
                        registro_venta_SUB_TOTAL,registro_venta_COSTO_DELIVERY,registro_venta_COSTO_TOTAL,
            nombre_empresa,direccion_empresa,registro_venta_COMENTARIOS_UBICACION,registro_venta_TIEMPO_APROXIMADO,
            registro_venta_FECHA_ENTREGA,registro_venta_CELULAR,registro_venta_CELULAR_ACTUAL;

    private LinearLayout registro_venta_EDITAR_TIPO_PAGO,registro_venta_EDITAR_COMENTARIO,anadir_COMENTARIO_UBICACION,registro_venta_EDITAR_CELULAR;
    private Button registro_venta_REGISTRAR;
    private Ubicacion ubicacion;
    private  String comentarioUbicacion;
    private  String comentarioCelular;
    private  String comentarioPedido;

    private ImageView check_comentario_ubicacion,check_comentario_pedido,check_comentario_celular;


    private Venta venta;

    private String costoDelivery;

    private float totalPedido=0;


    private Empresa mEmpresa;

    private  TipoPago tipoPagoDefault;

    private Toolbar mToolbar;

    private ComnetarioViewModel mComentarioViewModel;

    private String nombre_horario;

    private ImageView imageView_method_pay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        venta= new Venta();
        ubicacion=Ubicacion.ubicacionEnable;

        tipoPagoDefault=new TipoPago(1,"Efectivo",true,"");

        mComentarioViewModel= new ViewModelProvider(this).get(ComnetarioViewModel.class);

        comentarioCelular="";
        comentarioPedido="";
        comentarioUbicacion="";


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro_venta, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //DECLARAR WIDGETS
        declararWidgets(view);


        //EDITAR COMENTARIO
        editarComentario();

        //EDITAR TIPO DE PAGO
        editarTipoPago();

        //REGISTRAR VENTA
        registrarVenta();

        editarComentarioUbicacion();


        //responseRegisterVenta();


        observerChange();

        clickAgregarCelular();

    }

    private void declararWidgets(View view){
        registro_venta_TIPO_PAGO=view.findViewById(R.id.registro_venta_TIPO_PAGO);
        registro_venta_UBICACION=view.findViewById(R.id.registro_venta_UBICACION);
        registro_venta_COMENTARIOS=view.findViewById(R.id.registro_venta_COMENTARIOS);
        registro_venta_SUB_TOTAL=view.findViewById(R.id.registro_venta_SUB_TOTAL);
        registro_venta_COSTO_DELIVERY=view.findViewById(R.id.registro_venta_COSTO_DELIVERY);
        registro_venta_COSTO_TOTAL=view.findViewById(R.id.registro_venta_COSTO_TOTAL);

        registro_venta_EDITAR_TIPO_PAGO=view.findViewById(R.id.registro_venta_EDITAR_TIPO_PAGO);
        registro_venta_EDITAR_COMENTARIO=view.findViewById(R.id.registro_venta_EDITAR_COMENTARIO);
        anadir_COMENTARIO_UBICACION=view.findViewById(R.id.anadir_COMENTARIO_UBICACION);

        registro_venta_REGISTRAR=view.findViewById(R.id.registro_venta_REGISTRAR);
        nombre_empresa=view.findViewById(R.id.nombre_empresa);
        direccion_empresa=view.findViewById(R.id.direccion_empresa);
        mToolbar=view.findViewById(R.id.toolbar3);
        registro_venta_COMENTARIOS_UBICACION=view.findViewById(R.id.registro_venta_COMENTARIOS_UBICACION);

        registro_venta_TIEMPO_APROXIMADO=view.findViewById(R.id.registro_venta_TIEMPO_APROXIMADO);

        registro_venta_FECHA_ENTREGA=view.findViewById(R.id.registro_venta_FECHA_ENTREGA);

        registro_venta_CELULAR=view.findViewById(R.id.registro_venta_CELULAR);

        registro_venta_EDITAR_CELULAR=view.findViewById(R.id.registro_venta_EDITAR_CELULAR);

        registro_venta_CELULAR_ACTUAL=view.findViewById(R.id.registro_venta_CELULAR_ACTUAL);

        check_comentario_ubicacion=view.findViewById(R.id.check_comentario_ubicacion);
        check_comentario_celular=view.findViewById(R.id.check_comentario_celular);
        check_comentario_pedido=view.findViewById(R.id.check_comentario_pedido);

        check_comentario_ubicacion.setVisibility(View.GONE);
        check_comentario_celular.setVisibility(View.GONE);
        check_comentario_pedido.setVisibility(View.GONE);

        imageView_method_pay=view.findViewById(R.id.imageView_method_pay);
    }

    private void registrarVenta(){
        registro_venta_REGISTRAR.setOnClickListener(view -> {


            List<Double> coordenada_empresa=new ArrayList<>();
            coordenada_empresa.add(Double.valueOf(mEmpresa.getMaps_coordenada_x()));
            coordenada_empresa.add(Double.valueOf(mEmpresa.getMaps_coordenada_y()));

            venta.setEmpresa_posistion(coordenada_empresa);

            List<Double> coordenada_usuario=new ArrayList<>();
            coordenada_usuario.add(Double.valueOf(Ubicacion.ubicacionEnable.getMaps_coordenada_x().trim()));
            coordenada_usuario.add(Double.valueOf(Ubicacion.ubicacionEnable.getMaps_coordenada_y().trim()));

            venta.setUsuario_position(coordenada_usuario);

            venta.setIdtipopago(tipoPagoDefault.getIdtipopago());
            venta.setIdubicacion(ubicacion.getIdubicacion());
            venta.setComentario(comentarioPedido);
            venta.setIdUsuario(Cliente_Bi.getCliente().getIdusuario());

            venta.setIdEmpresa(mEmpresa.getIdempresa());
            venta.setIdestado_pago(2);
            venta.setIdtipo_envio(venta.getIdtipo_envio());
            venta.setVenta_costodelivery(mEmpresa.getCosto_delivery());

            float costo_total=mEmpresa.getCosto_delivery()+venta.getVenta_costopedido();
            venta.setVenta_costototal(costo_total);

            venta.setIdtipo_envio(1);

            venta.setMesa(false);

            Intent intent= LoadVentaActivity.newIntentLoadVentaActivity(getContext(),mEmpresa,venta);
            startActivity(intent);

        });
    }


    private void editarTipoPago()
    {
        registro_venta_EDITAR_TIPO_PAGO.setOnClickListener(view -> {
           // Intent intent= MethodPayActivity.newInstanceMethodPay(getContext(),mEmpresa,venta);
            //startActivityForResult(intent,METHOD_PAY_ACTIVITY);
            FragmentTypesPay fragment=FragmentTypesPay.newInstance(venta,mEmpresa);
            fragment.show(getChildFragmentManager(),FragmentTypesPay.TAG);
        });
    }



    private void editarComentarioUbicacion(){
        anadir_COMENTARIO_UBICACION.setOnClickListener(v->{
          /*  FragmentComentarioBottomSheet fragment=FragmentComentarioBottomSheet.newInstance(2,"Comentario de ubicacion",comentario,"Ejemplo:Casa de dos pisos color verde");
            fragment.show(getChildFragmentManager(),FragmentComentarioBottomSheet.TAG);
*/

            FragmentManager fm = getChildFragmentManager();
            FragmentComentarioVentaDialog alertDialog = FragmentComentarioVentaDialog.newInstance(comentarioUbicacion,"Comentario Ubicacion","Ejemplo:Casa de dos pisos color verde",1);
            alertDialog.show(fm, "fragment_alert");
        });
    }

    private void clickAgregarCelular(){
        registro_venta_EDITAR_CELULAR.setOnClickListener(v->{
            FragmentManager fm = getChildFragmentManager();
            FragmentComentarioVentaDialog alertDialog = FragmentComentarioVentaDialog.newInstance(comentarioCelular,"Agregar celular","Ejemplo:999 999 999",2);
            alertDialog.show(fm, "fragment_alert");
        });
    }

    private void editarComentario(){
        registro_venta_EDITAR_COMENTARIO.setOnClickListener(view -> {
           /* FragmentComentarioBottomSheet fragment=FragmentComentarioBottomSheet.newInstance(1,"Comentario de pedido",comentario,"Ejemplo:Agregar mas papas al pollo");
            fragment.show(getChildFragmentManager(),FragmentComentarioBottomSheet.TAG);*/

            FragmentManager fm = getChildFragmentManager();
            FragmentComentarioVentaDialog alertDialog = FragmentComentarioVentaDialog.newInstance(comentarioPedido,"Comentario pedido","Ejemplo:Agregar mas papas al pollo",3);
            alertDialog.show(fm, "fragment_alert");
        });
    }


    public void setPassData(Empresa empresa, Venta venta,String nombre_horario){
        this.mEmpresa=empresa;
        this.venta=venta;
        this.nombre_horario=nombre_horario;
        setDataWidget();

    }

     private void setDataWidget(){


        if(venta.getIdtipo_envio()==1){
            registro_venta_TIEMPO_APROXIMADO.setText(mEmpresa.getTiempo_aproximado_entrega());
        }else{
            registro_venta_TIEMPO_APROXIMADO.setText("0 min");
        }

         ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
         mToolbar.setNavigationOnClickListener(v-> getActivity().finish());


         registro_venta_CELULAR_ACTUAL.setText(Cliente_Bi.getCliente().getCelular());
         //DATA UBICACION
       // String ubicacion_nombre=Ubicacion.ubicacionEnable.getMaps_detalle()+" , "+Ubicacion.ubicacionEnable.getMaps_distrito();
        registro_venta_UBICACION.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());

        //DATA SUBTOTAL
        String subototal= String.valueOf(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(mEmpresa.getIdempresa()));
        registro_venta_SUB_TOTAL.setText(subototal);

        //COSTO DELIVERY

        if(venta.getIdtipo_envio()==1){
            costoDelivery=String.valueOf(mEmpresa.getCosto_delivery());
            registro_venta_COSTO_DELIVERY.setText(costoDelivery);
        }else{
            costoDelivery=String.valueOf(0);
            registro_venta_COSTO_DELIVERY.setText(costoDelivery);
        }

        //TOTAL PRODUCTO
         totalPedido=Float.valueOf(subototal) + Float.valueOf(costoDelivery);

        registro_venta_COSTO_TOTAL.setText(String.valueOf(totalPedido));

        //DATA COMENTARIO
        registro_venta_COMENTARIOS.setText(comentarioPedido);

        //DATA TIPO PAGO
        registro_venta_TIPO_PAGO.setText(tipoPagoDefault.getTipopago_nombre());


        nombre_empresa.setText(mEmpresa.getNombre_empresa());

        direccion_empresa.setText(mEmpresa.getDireccion_empresa());


         try {

             registro_venta_FECHA_ENTREGA.setText(fecha());

         } catch (ParseException e) {
             e.printStackTrace();
         }


    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == METHOD_PAY_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

                tipoPagoDefault=(TipoPago) data.getSerializableExtra("data");

                registro_venta_TIPO_PAGO.setText(tipoPagoDefault.getTipopago_nombre());

                venta.setIdtipopago(tipoPagoDefault.getIdtipopago());

            }
        }


    }

    private  void observerChange(){
        mComentarioViewModel.getComentario().observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence s) {

            }
        });
    }

    public void resetDataComentario(String comentario){

        if(comentario.length()>0){
            registro_venta_COMENTARIOS.setText(comentario);
            check_comentario_pedido.setVisibility(View.VISIBLE);

            venta.setComentario(comentario);
            comentarioPedido=comentario;

        }else {
            registro_venta_COMENTARIOS.setHint("Ejm : Poner mas salsa al plato");
            check_comentario_pedido.setVisibility(View.GONE);
            comentarioPedido=comentario;

        }


    }

    public void resetDataComentarioUbicacion(String comentario){

        if(comentario.length()>0){
            registro_venta_COMENTARIOS_UBICACION.setText(comentario);
            check_comentario_ubicacion.setVisibility(View.VISIBLE);
            comentarioUbicacion=comentario;
            //venta.setComentario(comentario);
        }else {
            registro_venta_COMENTARIOS_UBICACION.setHint("Ejm : Poner mas salsa al plato");
            check_comentario_ubicacion.setVisibility(View.GONE);
            comentarioUbicacion="";

        }
    }

    public void resetDataMetodoPago(TipoPago tipoPago) {

        registro_venta_TIPO_PAGO.setText(tipoPago.getTipopago_nombre());
        venta.setIdtipo_envio(tipoPago.getIdtipopago());

        if (tipoPago.getTipopago_url()!= null) {
            String imageUrl =tipoPago.getTipopago_url()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .skipMemoryCache(true)
                    .into(imageView_method_pay);
        }

    }

    private String fecha() throws ParseException {

        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

       // Date date = formatter.parse(venta.getVenta_fechaentrega());

        String fechaShow="";

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d = null;
        try {

            d = formatter.parse(venta.getVenta_fechaentrega()+" 00:00:00.00");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Timestamp timestamp = Timestamp.valueOf(formatter.format(d));
       // Timestamp timestamp = Timestamp.valueOf(venta.getVenta_fechaentrega());


        String day = (new SimpleDateFormat("EEEE")).format(timestamp); // "Tuesday"

        String month = (new SimpleDateFormat("MMMM")).format(timestamp); // "April"

        String year = (new SimpleDateFormat("yyyy")).format(timestamp); // "2010"


        int numberDay=timestamp.getDate();

        if(nombre_horario.length()==0){
            nombre_horario=" ahora";
        }

       return day+" "+numberDay+" de "+month+","+year+"  - "+nombre_horario;
    }

    public void updateTextViewCelular(String celular){
        if(celular.length()>0){
            registro_venta_CELULAR.setText(celular);
            comentarioCelular=celular;
            check_comentario_celular.setVisibility(View.VISIBLE);

        }else{
            registro_venta_CELULAR.setHint("Ejm: 999 999 999 ");
            check_comentario_celular.setVisibility(View.GONE);
            comentarioCelular="";

        }

    }
}
