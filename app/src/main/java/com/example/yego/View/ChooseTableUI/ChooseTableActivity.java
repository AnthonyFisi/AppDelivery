package com.example.yego.View.ChooseTableUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.InicioActivity;
import com.example.yego.ViewModel.VentaViewModel;

public class ChooseTableActivity extends AppCompatActivity {

    private static final String  VENTA="com.example.yego.View.HorarioUI.FragmentListaHorario.Venta";

    private static final String  EMPRESA="com.example.yego.View.HorarioUI.FragmentListaHorario.Empresa";

    private Empresa mEmpresa;

    private Venta mVenta;

    private VentaViewModel viewModel;

    private CardView item_product_inicio_disminuir_producto,item_product_inicio_incrementar_producto,confirmar_pedido;

    private TextView item_product_inicio_cantidad_producto,text_enviar_pedido;

    private ProgressBar progressBar3;

    private int numeroMesa;

    private TipoPago tipoPagoDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        numeroMesa=1;
        tipoPagoDefault=new TipoPago(1,"Efectivo",true,"");

        Toolbar toolbar=findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v->{onBackPressed();});


        reciveDataIntent();

        declararWidgets();

        loadDataVenta();


        setDataWidget();

        clickButton();

        System.out.println(mVenta.getVenta_costopedido()+"/"+mVenta.getDescuento_mesa());


       // setOnClickSecondScreen();
    }


    public static Intent newIntentChooseTableActivity(Context packageContext, Empresa empresa,Venta venta){
        Intent intent =new Intent(packageContext,ChooseTableActivity.class);
        intent.putExtra(EMPRESA,empresa);
        intent.putExtra(VENTA,venta);
        return intent;
    }


    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(EMPRESA) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(EMPRESA);
        }

        if(getIntent().getSerializableExtra(VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(VENTA);
        }


    }

    private void declararWidgets(){

        item_product_inicio_disminuir_producto=findViewById(R.id.item_product_inicio_disminuir_producto);

        item_product_inicio_incrementar_producto=findViewById(R.id.item_product_inicio_incrementar_producto);

        item_product_inicio_cantidad_producto=findViewById(R.id.item_product_inicio_cantidad_producto);

        text_enviar_pedido=findViewById(R.id.text_enviar_pedido);

        confirmar_pedido=findViewById(R.id.confirmar_pedido);


        progressBar3=findViewById(R.id.progressBar3);



    }

    private void setDataWidget(){

        item_product_inicio_cantidad_producto.setText(String.valueOf(numeroMesa));


    }

    private void clickButton(){
        item_product_inicio_disminuir_producto.setOnClickListener(v->{

            if(numeroMesa!=0){
                numeroMesa-=1;
            }
            item_product_inicio_cantidad_producto.setText(String.valueOf(numeroMesa));


        });

        item_product_inicio_incrementar_producto.setOnClickListener(v->{
            numeroMesa+=1;
            item_product_inicio_cantidad_producto.setText(String.valueOf(numeroMesa));

        });

        confirmar_pedido.setOnClickListener(v->{
            progressBar3.setVisibility(View.VISIBLE);
            text_enviar_pedido.setText("Creando pedido");


            mVenta.setVenta_costopedido(ProductoJOINregistroPedidoJOINpedido.totalCostoByEmpresa(mEmpresa.getIdempresa()));

            mVenta.setNumeromesa(numeroMesa);
            mVenta.setIdtipopago(tipoPagoDefault.getIdtipopago());
            mVenta.setIdubicacion(Ubicacion.ubicacionEnable.getIdubicacion());
            mVenta.setComentario("");
            mVenta.setIdUsuario(Cliente_Bi.getCliente().getIdusuario());
            mVenta.setIdEmpresa(mEmpresa.getIdempresa());
            mVenta.setIdestado_pago(1);
            mVenta.setVenta_costodelivery(0);
            mVenta.setMesa(true);
            viewModel.registrarVentaMesa(mVenta);


        });
    }

    private void loadDataVenta(){
        viewModel = new ViewModelProvider(this).get(VentaViewModel.class);
        viewModel.init();
        viewModel.getVentaLiveData().observe(this, new Observer<Venta>() {
            @Override
            public void onChanged(Venta venta) {

                progressBar3.setVisibility(View.GONE);


                if(venta!=null){

                    if(venta.isRepsuesta_registro_venta()){


                        ProductoJOINregistroPedidoJOINpedido.removeProductosByEmpresa(mEmpresa.getIdempresa());

                        Intent intent=SuccesSellActivity.newIntentSuccesSellActivity(ChooseTableActivity.this,mEmpresa);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);



                    }else {
                        text_enviar_pedido.setText("Enviar pedido");
                        Toast.makeText(ChooseTableActivity.this,"Lo sentimos ocurrio un problema,volver a intentarlo",Toast.LENGTH_SHORT).show();
                    }
                }else {

                    text_enviar_pedido.setText("Enviar pedido");
                    Toast.makeText(ChooseTableActivity.this,"Lo sentimos ocurrio un problema,volver a intentarlo",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }




}
