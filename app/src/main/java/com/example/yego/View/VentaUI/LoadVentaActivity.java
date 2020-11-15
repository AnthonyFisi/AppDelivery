package com.example.yego.View.VentaUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.OrdenGeneralGson;
import com.example.yego.Repository.Modelo.ProductoJOINregistroPedidoJOINpedido;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.VentaActivity;
import com.example.yego.ViewModel.VentaViewModel;

public class LoadVentaActivity extends AppCompatActivity {

    private LinearLayout cargando_venta,error_venta;

    private VentaViewModel viewModel;

    private static final String  VENTA="com.example.yego.View.HorarioUI.FragmentListaHorario.Venta";

    private static final String  EMPRESA="com.example.yego.View.HorarioUI.FragmentListaHorario.Empresa";

    private Venta mVenta;

    private CardView reintentar_venta;


    private Empresa mEmpresa=new Empresa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_venta);
        cargando_venta=findViewById(R.id.cargando_venta);
        error_venta=findViewById(R.id.error_venta);
        reintentar_venta=findViewById(R.id.reintentar_venta);


        viewModel = ViewModelProviders.of(LoadVentaActivity.this).get(VentaViewModel.class);
        viewModel.init();

        reciveDataIntent();

        reintentar_venta.setOnClickListener(v->{

            viewModel.registarVentaAlternative(mVenta);

        });


        responseRegisterVenta();

        viewModel.registarVentaAlternative(mVenta);


    }

    private void responseRegisterVenta(){
        viewModel.getVentaLiveData().observe(LoadVentaActivity.this, venta -> {
            cargando_venta.setVisibility(View.GONE);
            if (venta != null) {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                if(venta.isRepsuesta_registro_venta()){
                    ProductoJOINregistroPedidoJOINpedido.removeProductosByEmpresa(mEmpresa.getIdempresa());

                    OrdenGeneralGson.cantidadOrden=+1;

                    Intent intent= ResultVentActivity.newIntentResultActivity(LoadVentaActivity.this,mEmpresa,venta);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //ActivityCompat.finishAffinity(getActivity());
                }


            }else{

                error_venta.setVisibility(View.VISIBLE);

            }
        });

    }


    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(EMPRESA) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(EMPRESA);
        }

        if(getIntent().getSerializableExtra(VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(VENTA);
        }

    }



    public static Intent newIntentLoadVentaActivity(Context packageContext, Empresa empresa, Venta venta){
        Intent intent =new Intent(packageContext, LoadVentaActivity.class);
        intent.putExtra(EMPRESA,empresa);
        intent.putExtra(VENTA,venta);
        return intent;
    }
}
