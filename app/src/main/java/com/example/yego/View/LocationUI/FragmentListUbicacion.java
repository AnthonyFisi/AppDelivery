package com.example.yego.View.LocationUI;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.ViewModel.UbicacionViewModel;

import java.util.ArrayList;
import java.util.List;


public class FragmentListUbicacion extends Fragment{


    private UbicacionViewModel viewModel;
    private ListUbicacionResultsAdapter adapter;
    private Ubicacion ubicacionHabilitado=new Ubicacion();
    private Button fragment_ubicacion_AGREGAR_DIRECCION;
    private TextView texto_ubicacion_NOMBRE;


    public FragmentListUbicacion() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter= new ListUbicacionResultsAdapter();
        viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);
        viewModel.init();
        viewModel.getUbicacionLiveData().observe(this, new Observer<GsonUbicacion>() {
            @Override
            public void onChanged(GsonUbicacion gsonUbicacion) {
                if(gsonUbicacion !=null){
                    List<Ubicacion> lista=new ArrayList<>();
                    System.out.println("Llegan los datos ");
                    for(Ubicacion ubicacion:gsonUbicacion.getListaUbicacion()){
                        if(ubicacion.isUbicacion_estado()){
                            ubicacionHabilitado=ubicacion;
                            System.out.println("UBICACION HABILITADO"+ubicacionHabilitado.getUbicacion_nombre());
                        }else{
                            System.out.println("Entran a lista ADD");

                            lista.add(ubicacion);
                        }
                    }

                   // adapter.setResults(lista,FragmentListUbicacion.this);
                }else{
                    System.out.println("NO RETORNARON DATOS ");

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_list_ubicacion, container, false);
        //view = inflater.inflate(R.layout.fragment_list_ubicacion, container, false);


        viewModel.searchListUbicacion(Cliente_Bi.getCliente().getIdusuario());
        RecyclerView recyclerView=view.findViewById(R.id.RecyclerView_LISTA_UBICACION);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        fragment_ubicacion_AGREGAR_DIRECCION=view.findViewById(R.id.fragment_ubicacion_AGREGAR_DIRECCION);
        texto_ubicacion_NOMBRE=view.findViewById(R.id.texto_ubicacion_NOMBRE);

        System.out.println("UBICACION HABILITADO LLEGO "+ubicacionHabilitado.getUbicacion_nombre());

        texto_ubicacion_NOMBRE.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());


        fragment_ubicacion_AGREGAR_DIRECCION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
/*
    @Override
    public void itemUbicacionClick(Ubicacion ubicacion) {
        UbicacionViewModel viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);
        viewModel.init();
        viewModel.updateEstadoUbicacion(Ubicacion.ubicacionEnable.getIdubicacion(),ubicacion.getIdubicacion());
        texto_ubicacion_NOMBRE.setText(ubicacion.getUbicacion_nombre());

    }

    @Override
    public void itemDeleteClick(Ubicacion ubicacion) {
        UbicacionViewModel viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);
        viewModel.init();
        viewModel.deleteUbicacion(ubicacion.getIdubicacion());
    }*/
}
