package com.example.yego.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.Login.GoogleMapsOficial;
import com.example.yego.Login.LoadingDialog;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.LocationUI.ChooseWayLocationFragment;
import com.example.yego.View.LocationUI.ListUbicacionResultsAdapter;
import com.example.yego.ViewModel.UbicacionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActionBottomDialogFragment  extends Fragment implements  ListUbicacionResultsAdapter.UbicacionClick{

    public static final String TAG = "ActionBottomDialog";

    private UbicacionViewModel viewModel;
    private ListUbicacionResultsAdapter adapter;
    private Button fragment_ubicacion_AGREGAR_DIRECCION;
    private TextView texto_ubicacion_NOMBRE;

    private BackToInicio mBackToInicio;
    private ImageView ic_back;

    LoadingDialog mLoadingDialog;

    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
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
                    for(Ubicacion ubicacion:gsonUbicacion.getListaUbicacion()){
                        if(!ubicacion.isUbicacion_estado()){
                            lista.add(ubicacion);
                        }
                    }
                    adapter.setResults(lista, ActionBottomDialogFragment.this);
                }
            }
        });

        mLoadingDialog= new LoadingDialog(getActivity());
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.bottom_sheet, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel.searchListUbicacion(Cliente_Bi.getCliente().getIdusuario());
        RecyclerView recyclerView=view.findViewById(R.id.RecyclerView_LISTA_UBICACION);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        fragment_ubicacion_AGREGAR_DIRECCION=view.findViewById(R.id.fragment_ubicacion_AGREGAR_DIRECCION);
        texto_ubicacion_NOMBRE=view.findViewById(R.id.texto_ubicacion_NOMBRE);


        texto_ubicacion_NOMBRE.setText(Ubicacion.ubicacionEnable.getUbicacion_nombre());

        //AGREGAR UBICACION
        clickAgregarUbicacion();


        ic_back=view.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(v->{
            backToInicio();
        });


    }




    private void clickAgregarUbicacion(){
        fragment_ubicacion_AGREGAR_DIRECCION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChooseWayLocationFragment addPhotoBottomDialogFragment =
                    ChooseWayLocationFragment.newInstance();
            addPhotoBottomDialogFragment.show(getChildFragmentManager(),
                    ChooseWayLocationFragment.TAG);


            }
        });

    }







    @Override
    public void itemUbicacionClick(Ubicacion ubicacion,int position) {
        mLoadingDialog.startLoadingDialog();

        UbicacionViewModel viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);

        viewModel.init();

        viewModel.updateEstadoUbicacion(Ubicacion.ubicacionEnable.getIdubicacion(),ubicacion.getIdubicacion());


        viewModel.getUbicacionLiveData().observe(this, new Observer<GsonUbicacion>() {
            @Override
            public void onChanged(GsonUbicacion gsonUbicacion) {
                mLoadingDialog.dismissDialog();
                if(gsonUbicacion!=null){

                    adapter.changeItem(position);

                    texto_ubicacion_NOMBRE.setText(ubicacion.getUbicacion_nombre());

                    Toast.makeText(getContext(),"Actualizacion exitosa",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Lo sentimos vuelva a interlo",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void itemDeleteClick(Ubicacion ubicacion) {
        mLoadingDialog.startLoadingDialog();
        UbicacionViewModel viewModel = ViewModelProviders.of(this).get(UbicacionViewModel.class);
        viewModel.init();
        viewModel.deleteUbicacion(ubicacion.getIdubicacion());
        viewModel.getUbicacionLiveData().observe(this, new Observer<GsonUbicacion>() {
            @Override
            public void onChanged(GsonUbicacion gsonUbicacion) {

                mLoadingDialog.dismissDialog();

                if(gsonUbicacion!=null){
                    //adapter.deleteItem(ubicacion);
                    Toast.makeText(getContext(),"Eliminacion exitosa",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Lo sentimos vuelva a intentarlo",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    private void backToInicio() {
        mBackToInicio.back();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mBackToInicio = (ActionBottomDialogFragment.BackToInicio) context;
    }

    public interface BackToInicio{
        void  back();
    }

}
