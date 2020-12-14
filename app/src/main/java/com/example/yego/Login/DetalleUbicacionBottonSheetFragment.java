package com.example.yego.Login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonUbicacion;
import com.example.yego.Repository.Modelo.JwtResponse;
import com.example.yego.Repository.Modelo.Ubicacion;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Utils.ActionBottomDialogFragment;
import com.example.yego.View.InicioActivity;
import com.example.yego.ViewModel.Cliente_BiViewModel;
import com.example.yego.ViewModel.UbicacionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;


public class DetalleUbicacionBottonSheetFragment extends BottomSheetDialogFragment {
    public static final String TAG = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_UBICACION = "ubicacion";

    private static final String ROUTE = "route";
    // TODO: Rename and change types of parameters
    private Ubicacion mUbicacion;

    private Button button_agregar_ubicacion;

    private EditText ubicacion_DETALLE;

    private UbicacionViewModel viewModel;

    private boolean route;

    private LinearLayout linearlayout_ACTUALIZAR_OK,progress_bar,linearlayout_detalle,linearlayout_REGISTRO_UBICACION;

    private TextView direccion_actualizada;

    private CardView insert_direccion;

    private BackToInicio mBackToInicio;

    private Toolbar mToolbar;


    public static DetalleUbicacionBottonSheetFragment newInstance(Ubicacion ubicacion,Boolean route) {
        DetalleUbicacionBottonSheetFragment fragment = new DetalleUbicacionBottonSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UBICACION, ubicacion);
        args.putBoolean(ROUTE,route);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUbicacion =(Ubicacion) getArguments().getSerializable(ARGS_UBICACION);
            route=getArguments().getBoolean(ROUTE);
        }
        viewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);
        viewModel.init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_ubicacion_botton_sheet, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        declararWidgets(view);

        registrarUbicacion();

        responseData();

        clickClose();

        textChangeListener();

        ImageView ic_back = view.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(v->{
            backToInicio();
        });

    }


    public void declararWidgets(View view){
        button_agregar_ubicacion=view.findViewById(R.id.button_agregar_ubicacion);
        TextView ubicacion_NOMBRE = view.findViewById(R.id.ubicacion_NOMBRE);
        ubicacion_DETALLE=view.findViewById(R.id.ubicacion_DETALLE);


        if(mUbicacion!=null) {

            ubicacion_NOMBRE.setText(mUbicacion.getUbicacion_nombre());

        }
        direccion_actualizada=view.findViewById(R.id.direccion_actualizada);

        insert_direccion=view.findViewById(R.id.insert_direccion);

        linearlayout_ACTUALIZAR_OK=view.findViewById(R.id.linearlayout_ACTUALIZAR_OK);

        linearlayout_REGISTRO_UBICACION=view.findViewById(R.id.linearlayout_REGISTRO_UBICACION);

        progress_bar=view.findViewById(R.id.progress_bar);

        linearlayout_detalle=view.findViewById(R.id.linearlayout_detalle);

    }

    private void responseData() {

        viewModel.getUbicaionLiveDataConfirmation().observe(this, new Observer<Ubicacion>() {
            @Override
            public void onChanged(Ubicacion ubicacion) {
                if(ubicacion!=null){

                    Ubicacion.ubicacionEnable=ubicacion;

                    if(route){


                        JwtResponse jwtResponse=SessionPrefs.get(getContext()).data();
                        jwtResponse.setIdubicacion(1);
                        SessionPrefs.get(getContext()).saveJwtResponse(jwtResponse);


                       /* Intent intent=AfterLoginActivity.newIntentLocationActivity(getContext(),jwtResponse.getId().intValue());
                        startActivity(intent);
                        getActivity().finish();*/

                       getDataCLiente_Bi(jwtResponse.getId().intValue());


                    }else{

                        progress_bar.setVisibility(View.GONE);

                        linearlayout_REGISTRO_UBICACION.setVisibility(View.GONE);

                        linearlayout_ACTUALIZAR_OK.setVisibility(View.VISIBLE);

                        direccion_actualizada.setText(ubicacion.getUbicacion_nombre());

                    }



                }else{
                    progress_bar.setVisibility(View.GONE);

                    button_agregar_ubicacion.setEnabled(true);

                    Toast.makeText(getContext(),"INTENTARLO  NUEVAMENTE",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void registrarUbicacion(){


        button_agregar_ubicacion.setOnClickListener(view -> {


     /*       if(ubicacion_DETALLE.getText().length()> 5){

                progress_bar.setVisibility(View.VISIBLE);
               // loadingDialog.startLoadingDialog();
*/
            progress_bar.setVisibility(View.VISIBLE);

            button_agregar_ubicacion.setEnabled(false);


                if(route){

                    mUbicacion.setUbicacion_comentarios(ubicacion_DETALLE.getText().toString());


                    viewModel.registrarFirstTimeUbicacio(mUbicacion);

                }else{

                    viewModel.registarUbicacion(mUbicacion,Ubicacion.ubicacionEnable.getIdubicacion());

                }

/*

            }else{

                if(ubicacion_DETALLE.length() < 5 ){
                    ubicacion_DETALLE.setError("Ingresar un breve detalle porfavor");
                }



            }*/


        });
    }


    private void getDataCLiente_Bi(int idusuario){

        Cliente_BiViewModel viewModelCliente = new ViewModelProvider(this).get(Cliente_BiViewModel.class);
        viewModelCliente.init();
        viewModelCliente.findInformacionClienteBi(idusuario);

        viewModelCliente.getCliente_biLiveData().observe(this, new Observer<Cliente_Bi>() {
            @Override
            public void onChanged(Cliente_Bi cliente_bi) {

                //loadingDialog.dismissDialog();

                progress_bar.setVisibility(View.GONE);

                if(cliente_bi!=null){

                    Ubicacion.ubicacionEnable=new Ubicacion(cliente_bi.getIdubicacion(),cliente_bi.getUbicacion_nombre(),cliente_bi.getUbicacion_comentarios(),cliente_bi.getIdusuario(),true,false,"",cliente_bi.getMaps_detalle(),cliente_bi.getMaps_coordenada_x(),cliente_bi.getMaps_coordenada_y());

                    // Usuario.idUsuario=cliente_bi.getIdusuario();


                    Cliente_Bi.registrarCliente_bi(cliente_bi);


                    //IR AL HOME
                    Intent intent= InicioActivity.newIntentInicioActivity(getContext(),false);
                    startActivity(intent);
                    requireActivity().finish();


                }else{

                    button_agregar_ubicacion.setEnabled(true);

                    // progress_bar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Experimentamos problemas en nuestros servicios", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void clickClose(){
        insert_direccion.setOnClickListener(v->{
           // backToInicio();
            Intent intent= new Intent(getContext(), InicioActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


    }


    private void textChangeListener(){
        ubicacion_DETALLE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0){

                    linearlayout_detalle.setBackground(getResources().getDrawable(R.drawable.border_editext_able));

                }else {
                    linearlayout_detalle.setBackground(getResources().getDrawable(R.drawable.border_editext_enable));

                }
            }
        });

        ubicacion_DETALLE.setOnClickListener(v->{
            linearlayout_detalle.setBackground(getResources().getDrawable(R.drawable.border_editext_able));
        });

    }



    private void backToInicio() {
        mBackToInicio.back();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mBackToInicio = (DetalleUbicacionBottonSheetFragment.BackToInicio) context;
    }

    public interface BackToInicio{
        void  back();
    }

}
