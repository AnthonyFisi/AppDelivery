package com.example.yego.View.VentaUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonTipoPago;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.HorarioActivity;
import com.example.yego.View.MethodPayUI.FragmentTypesPay;
import com.example.yego.View.MethodPayUI.ListaTypesPayResultsAdapter;
import com.example.yego.ViewModel.TipoPagoViewModel;

public class MetodoPagoActivity extends AppCompatActivity  implements  ListaTypesPayResultsAdapter.ClickTipoPago{


    private TipoPagoViewModel viewModel;
    private ListaTypesPayResultsAdapter adapter;

    private static final String  INTENT_EMPRESA_ACTIVITY="com.example.yego.View.CarritoUI.FramentTipo_Envio.Empresa";

    private static final String  INTENT_VENTA="com.example.yego.View.CarritoUI.FramentTipo_Envio.Tipo_Envio";


    private Venta mVenta= new Venta();

    private Empresa mEmpresa = new Empresa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        reciveDataIntent();

        loadData();

        RecyclerView recyclerView=findViewById(R.id.fragment_types_pay_RECYCLERVIEW);
        viewModel.searchListTipoPagoEnable();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        return super.onCreateView(name, context, attrs);
    }

    private void loadData(){
        adapter=new ListaTypesPayResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(TipoPagoViewModel.class);
        viewModel.init();
        viewModel.getGsonTipoPagoLiveData().observe(this, new Observer<GsonTipoPago>() {
            @Override
            public void onChanged(GsonTipoPago gsonTipoPago) {

                if(gsonTipoPago.getListaTipoPago()!=null){

                    for(TipoPago tipoPago:gsonTipoPago.getListaTipoPago()){
                        System.out.println(tipoPago.getTipopago_nombre());
                    }
                    adapter.setResults(gsonTipoPago.getListaTipoPago(), MetodoPagoActivity.this);
                    System.out.println("ENTRAMOS fragment recive data");

                }else{
                    System.out.println("ENTRAMOS FRAGMENT AND DOR NECIVE DATA");

                }
            }
        });
    }

    @Override
    public void clickPago(TipoPago tipoPago) {
        mVenta.setIdtipopago(tipoPago.getIdtipopago());
    }



    private void reciveDataIntent(){
        if(getIntent().getSerializableExtra(INTENT_VENTA) !=null){
            mVenta=(Venta) getIntent().getSerializableExtra(INTENT_VENTA);
        }
        if(getIntent().getSerializableExtra(INTENT_EMPRESA_ACTIVITY) !=null){
            mEmpresa=(Empresa) getIntent().getSerializableExtra(INTENT_EMPRESA_ACTIVITY);
        }
    }

    public static Intent newIntentMetodoPagoActivity(Context packageContext, Empresa empresa, Venta venta){
        Intent intent =new Intent(packageContext, MetodoPagoActivity.class);
        intent.putExtra(INTENT_EMPRESA_ACTIVITY,empresa);
        intent.putExtra(INTENT_VENTA,venta);
        return intent;
    }
}
