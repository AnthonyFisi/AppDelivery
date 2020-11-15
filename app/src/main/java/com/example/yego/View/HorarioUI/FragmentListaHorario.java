package com.example.yego.View.HorarioUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Empresa;
import com.example.yego.Repository.Modelo.Gson.GsonHorario;
import com.example.yego.Repository.Modelo.Horario;
import com.example.yego.Repository.Modelo.Tipo_Envio;
import com.example.yego.Repository.Modelo.Venta;
import com.example.yego.View.VentaActivity;
import com.example.yego.ViewModel.HorarioViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentListaHorario extends Fragment implements HorarioListResultsAdapter.ClickHorario {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private HorarioViewModel viewModel;
    private HorarioListResultsAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private DatePickerTimeline datePickerTimeline;
    private String fechaSeleccionada;
    private List<Horario> lista =new ArrayList<>();

    private Empresa mEmpresa= new Empresa();
   private Venta mVenta= new Venta();
    private int DayOfWeek;

    private TextView activity_horario_NOMBRE_EMPRESA;




    public FragmentListaHorario() {
    }

    public static FragmentListaHorario newInstance(String param1, String param2) {
        FragmentListaHorario fragment = new FragmentListaHorario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new HorarioListResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(HorarioViewModel.class);
        viewModel.init();
        viewModel.getHorarioLiveData().observe(this, new Observer<GsonHorario>() {
            @Override
            public void onChanged(GsonHorario gsonHorario) {
                if(gsonHorario !=null){

                    System.out.println(gsonHorario.getListaHorario().size()+"CANTIDAD");

                    List<Horario> listaHorario=new ArrayList<>();
                    lista=gsonHorario.getListaHorario();

                    Date currentTime = Calendar.getInstance().getTime();
                    currentTime.toLocaleString();

                    for(Horario horario:gsonHorario.getListaHorario()){
                        if(horario.getHorario_inicio()>=currentTime.getHours()){
                            listaHorario.add(horario);
                        }
                    }

                    List<Horario> nuevaLista= new ArrayList<>();

                    for(Horario horario:listaHorario){

                        if(mEmpresa.getHorario_inicio()<=horario.getHorario_inicio() && mEmpresa.getHorario_fin()>=horario.getHorario_fin()){
                            nuevaLista.add(horario);
                        }
                    }

                    adapter.setResults(nuevaLista, FragmentListaHorario.this);
                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_lista_horario, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_lista_horario);
        RecyclerView recyclerView=view.findViewById(R.id.lista_horario_RecyclerView);
        viewModel.searchListahorario(mShimmerViewContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        setUpTime(view);

        Date[] dates = {Calendar.getInstance().getTime()};
        datePickerTimeline.deactivateDates(dates);

        declararWidgets(view);
       // setDataWidget();

        return view;
    }

    public void declararWidgets(View view){
        activity_horario_NOMBRE_EMPRESA=view.findViewById(R.id.activity_horario_NOMBRE_EMPRESA);


    }

    private void setDataWidget(){
        activity_horario_NOMBRE_EMPRESA.setText(mEmpresa.getNombre_empresa());
    }


    private void setUpTime(View view){
        datePickerTimeline = view.findViewById(R.id.datePickerTimeline);
        Date date= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        String[] parts = currentDateandTime.split("-");


        datePickerTimeline.setInitialDate(Integer.parseInt(parts[0]),(Integer.parseInt(parts[1])-1),Integer.parseInt(parts[2]));
        fechaSeleccionada=Integer.parseInt(parts[0])+"-"+Integer.parseInt(parts[1])+"-"+Integer.parseInt(parts[2]);



// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {

                fechaSeleccionada=year+"-"+(month+1)+"-"+day;

                System.out.println("ENTRAMIS ACA");

                if(day!=date.getDate()){

                    System.out.println("HOY" +lista.size());


                    List<Horario> nuevaLista= new ArrayList<>();

                    for(Horario horario:lista){

                        if(mEmpresa.getHorario_inicio()<=horario.getHorario_inicio() && mEmpresa.getHorario_fin()>=horario.getHorario_fin()){

                            nuevaLista.add(horario);

                        }
                    }
                    System.out.println("LISTA"+nuevaLista.size());


                    adapter.setResults(nuevaLista, FragmentListaHorario.this);
                }else{

                    System.out.println("OTRO DIA"+lista.size());

                    List<Horario> listaHorario=new ArrayList<>();
                    Date currentTime = Calendar.getInstance().getTime();
                    currentTime.toLocaleString();


                    for(Horario horario:lista){
                        if(horario.getHorario_inicio()>=currentTime.getHours()){
                            listaHorario.add(horario);
                        }
                    }

                    List<Horario> nuevaLista= new ArrayList<>();

                    for(Horario horario:listaHorario){

                        if(mEmpresa.getHorario_inicio()<=horario.getHorario_inicio() && mEmpresa.getHorario_fin()>=horario.getHorario_fin()){
                            nuevaLista.add(horario);
                        }
                    }

                    System.out.println("LISTA"+nuevaLista.size());



                    adapter.setResults(nuevaLista, FragmentListaHorario.this);
                }
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });
        Date currentTime = Calendar.getInstance().getTime();
        currentTime.toLocaleString();

    }



    @Override
    public void clickItem(Horario horario) {
        String data=fechaSeleccionada+" "+ horario.getIdhorario()+ "/ "+mEmpresa.getNombredueno_empresa() + " / ";
        mVenta.setIdhorario(horario.getIdhorario());
        mVenta.setVenta_fechaentrega(fechaSeleccionada);

        //DIRIGIRSE DIRECTAMENTE A LA VENTA
        Intent intent= VentaActivity.newIntentVentaActivity(getContext(),mEmpresa,mVenta,horario.getHorario_nombre());
        startActivity(intent);


    }

    public void setPassData(Empresa empresa, Venta venta){
        this.mEmpresa=empresa;
        this.mVenta=venta;
        setDataWidget();
    }

}
