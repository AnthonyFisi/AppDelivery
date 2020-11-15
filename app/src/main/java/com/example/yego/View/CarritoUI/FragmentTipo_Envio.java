package com.example.yego.View.CarritoUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Horario;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.CarritoUI.Envio_empresa.ListaTipo_EnvioResultsAdapter;
import com.example.yego.View.HorarioActivity;
import com.example.yego.View.VentaActivity;
import com.example.yego.ViewModel.Tipo_EnvioViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentTipo_Envio extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";


    private Tipo_EnvioViewModel viewModel;
    private ListaTipo_EnvioResultsAdapter adapter;
    private float CostoDelivery;
    private Empresa empresa=new Empresa();

    private Button button_horario_hoy,button_horario_programado;

    List<Horario> listaHorario;

    private String fechaSeleccionada;

    private Venta venta;

    public FragmentTipo_Envio() {

    }

    public static FragmentTipo_Envio newInstance(float CostoDelivery,Empresa empresa,Venta venta) {
        FragmentTipo_Envio fragment = new FragmentTipo_Envio();
        fragment.reciveData(CostoDelivery,empresa,venta);
        return fragment;
    }

    private void reciveData(float CostoDelivery, Empresa empresa,Venta venta){
        this.CostoDelivery=CostoDelivery;
        this.empresa=empresa;
        this.venta=venta;
        System.out.println("RECIBIMOS DATA DE EMPRESA" + empresa.getNombredueno_empresa());
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaHorario=new ArrayList<>();
       /* adapter= new ListaTipo_EnvioResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(Tipo_EnvioViewModel.class);
        viewModel.init();
        viewModel.getTipo_EnvioaLiveData().observe(this, new Observer<GsonTipo_Envio>() {
            @Override
            public void onChanged(GsonTipo_Envio gsonTipo_envio) {
                if (gsonTipo_envio != null) {
                    adapter.setResults(gsonTipo_envio.getListaTipo_Envio(),FragmentTipo_Envio.this::itemClick,CostoDelivery);
                }
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_tipo__envio, container, false);
       /* viewModel.searchListTipo_Envio();
        RecyclerView recyclerView=view.findViewById(R.id.recyclerview_TIPO_ENVIO);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        return view;*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_horario_hoy=view.findViewById(R.id.button_horario_hoy);
        button_horario_programado=view.findViewById(R.id.button_horario_programado);


        button_horario_hoy.setOnClickListener( v->{
            seleccionarHorario();


            //DIRIGIRSE DIRECTAMENTE A LA VENTA
            //Intent intent= VentaActivity.newIntentVentaActivity(getContext(),empresa,venta);
            //startActivity(intent);

            /*Intent intent= VentaActivity.newIntentVentaActivity(getContext(),empresa,mTipo_envio,horario,fechaSeleccionada);
            startActivity(intent);*/
        });

        button_horario_programado.setOnClickListener( v->{

            //DIRIGIRSE A ELEGIR UN HORARIO
            Intent intent= HorarioActivity.newIntentHorarioActivity(getContext(),empresa,venta);
            startActivity(intent);

        });

    }

    /*

    @Override
    public void itemClick(Tipo_Envio tipo_envio) {
        System.out.println("RECIBIMOS DATA DE EMPRESA" + empresa.getNombredueno_empresa());
        Intent intent= HorarioActivity.newIntentHorarioActivity(getContext(),empresa,tipo_envio);
        startActivity(intent);
    }*/

    private void  seleccionarHorario(){

        Horario.getLista();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        Date currentTime = Calendar.getInstance().getTime();
        currentTime.toLocaleString();

        String[] parts = currentDateandTime.split("-");

        fechaSeleccionada=Integer.parseInt(parts[0])+"-"+Integer.parseInt(parts[1])+"-"+Integer.parseInt(parts[2]);

        for(Horario horario:Horario.mHorarioList){
            if(horario.getHorario_inicio()>=currentTime.getHours()){
                if(empresa.getHorario_inicio()<=horario.getHorario_inicio() && empresa.getHorario_fin()>=horario.getHorario_fin()){
                    listaHorario.add(horario);
                }
            }
        }


        venta.setIdhorario(listaHorario.get(0).getIdhorario());
        venta.setVenta_fechaentrega(fechaSeleccionada);


    }
}
