package com.example.yego.View.CarritoUI.Envio_empresa;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Envio_empresa;
import com.example.yego.Repository.Modelo.Gson.Envio_empresaGson;
import com.example.yego.Repository.Modelo.Horario;
import com.example.yego.Repository.Modelo.TipoPago;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.CarritoUI.FragmentTipo_Envio;
import com.example.yego.View.ChooseTableUI.ChooseTableActivity;
import com.example.yego.View.HorarioActivity;
import com.example.yego.View.MethodPayUI.ListaTypesPayResultsAdapter;
import com.example.yego.View.VentaActivity;
import com.example.yego.ViewModel.Envio_empresaViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Envio_empresaFragment extends BottomSheetDialogFragment /*implements ListaTipo_EnvioResultsAdapter.ItemClickTipo_Envio */{

    public static final String TAG = "";
    private static final String EMPRESA = "empresa";
    private static final String VENTA = "venta";
    private Envio_empresaViewModel viewModel;

    private ListaTipo_EnvioResultsAdapter adapter;

    private RecyclerView recyclerView_tipo_envio;
    private Empresa empresa;
    private Venta venta;

    private CardView cardView_programar;

    private Button button_now;

    public Envio_empresaFragment() {
        // Required empty public constructor


    }

    public static Envio_empresaFragment newInstance(Empresa empresa, Venta venta){
        Envio_empresaFragment fragment=new Envio_empresaFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable(EMPRESA,empresa);
        bundle.putSerializable(VENTA,venta);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            empresa=(Empresa) getArguments().getSerializable(EMPRESA);

            venta=(Venta) getArguments().getSerializable(VENTA);
        }

/*
        adapter= new ListaTipo_EnvioResultsAdapter();
        viewModel= new ViewModelProvider(this).get(Envio_empresaViewModel.class);
        viewModel.init();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println(venta.getVenta_costopedido()+"/"+venta.getDescuento_mesa());

        recyclerView_tipo_envio=view.findViewById(R.id.recyclerView_tipo_envio);
        recyclerView_tipo_envio.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        viewModel.listaFindByIdempresa(empresa.getIdempresa());
       // loadData();

        cardView_programar=view.findViewById(R.id.cardView_programar);

        button_now=view.findViewById(R.id.button_now);

        clickButton();
    }

   /* private void loadData(){
        viewModel.getEnvio_empresaGsonLiveData().observe(this, new Observer<Envio_empresaGson>() {
            @Override
            public void onChanged(Envio_empresaGson envio_empresaGson) {
                if(envio_empresaGson!=null){

                    adapter.setResults(envio_empresaGson.getListaEnvioEmpresa(),Envio_empresaFragment.this);
                    recyclerView_tipo_envio.setAdapter(adapter);

                }else{
                    Toast.makeText(getContext(),"Intentarlo otra vez",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    private void clickButton(){

        cardView_programar.setOnClickListener(v->{
            Intent intent= HorarioActivity.newIntentHorarioActivity(getContext(),empresa,venta);
            startActivity(intent);
        });


        button_now.setOnClickListener(v->{
            seleccionarHorario();
            //DIRIGIRSE DIRECTAMENTE A LA VENTA
            Intent intent= VentaActivity.newIntentVentaActivity(getContext(),empresa,venta,"");
            startActivity(intent);
        });
    }

    /*
    @Override
    public void itemClick(Envio_empresa tipo_envio) {

        venta.setIdtipo_envio(tipo_envio.getIdtipoenvio());

        if(tipo_envio.getIdtipoenvio()==3){
            seleccionarHorario();
            Intent intent= ChooseTableActivity.newIntentChooseTableActivity(getContext(),empresa,venta);
            startActivity(intent);

        }else{
            Intent intent= HorarioActivity.newIntentHorarioActivity(getContext(),empresa,venta);
            startActivity(intent);

        }


    }*/


    private void  seleccionarHorario(){

        Horario.getLista();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        Date currentTime = Calendar.getInstance().getTime();
        currentTime.toLocaleString();

        String[] parts = currentDateandTime.split("-");

        String replaceMonth="";

        if(parts[1].length()==1){
            replaceMonth="0"+parts[1];
        }else {
            replaceMonth=parts[1];
        }

        String fechaSeleccionada = Integer.parseInt(parts[0]) + "-" + Integer.parseInt(replaceMonth) + "-" + Integer.parseInt(parts[2]);

        for(Horario horario:Horario.mHorarioList){

            Timestamp time=new Timestamp(System.currentTimeMillis());

            //String[] tiempo=horario.getHorario_nombre().split("- ");

            String replaceInicio="";
            String replaceFin="";

            if(String.valueOf(horario.getHorario_inicio()).length()==1){
                replaceInicio="0"+horario.getHorario_inicio();
            }else {
              replaceInicio=String.valueOf( horario.getHorario_inicio());
            }
            if(String.valueOf(horario.getHorario_fin()).length()==1){
                replaceFin="0"+horario.getHorario_fin();
            }else {
                replaceFin=String.valueOf( horario.getHorario_fin());
            }


            String inputInicio = fechaSeleccionada+" "+replaceInicio+":00:00.00";
            String inputFin = fechaSeleccionada+" "+replaceFin+":00:00.00";


            System.out.println(inputFin+ " / "+ inputInicio);


            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date d = null,d1=null;
            try {
                d = formatter.parse(inputInicio);
                d1 = formatter.parse(inputFin);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            inputInicio = formatter.format(d);
            inputFin = formatter.format(d1);


            Timestamp horarioInicio=Timestamp.valueOf(inputInicio);

            Timestamp horarioFin=Timestamp.valueOf(inputFin);



            if(time.after(horarioInicio) && time.before(horarioFin)) {
                venta.setIdhorario(horario.getIdhorario());
                System.out.println("idhorario"+horario.getIdhorario()+" / "+horarioInicio+"-"+horarioFin);
            }

            /*if(horario.getHorario_inicio()>=currentTime.getHours()){
                if(empresa.getHorario_inicio()<=horario.getHorario_inicio() && empresa.getHorario_fin()>=horario.getHorario_fin()){
                    listaHorario.add(horario);
                }
            }*/
        }


        venta.setVenta_fechaentrega(fechaSeleccionada);


    }




}
