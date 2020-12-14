package com.example.yego.View.OrdenUI.DetallesUI;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Calificacion_Servicio;
import com.example.yego.Repository.Modelo.Calificacion_Usuario;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Gson.GsonOrden;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.ViewModel.Calificacion_ServicioViewModel;
import com.example.yego.ViewModel.Calificacion_UsuarioViewModel;
import com.hsalf.smileyrating.SmileyRating;

import static com.example.yego.View.LocationUI.ChooseWayLocationFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmarEntregaFragment extends Fragment {

   // private RatingBar  ratingBarRepartidor,ratingBarServicio;

    private TextView nombre_empresa;

    private Button confirmarCalificacion,inicio;

    private Boolean calificacion_servicio,calificacion_usuario;

   // private int rating_servicio,rating_delivery;


    private int puntaje_usuario=0,puntaje_servicio=0;

    private GsonOrden orden;

    private Calificacion_ServicioViewModel viewModel_Servicio;

    private Calificacion_UsuarioViewModel viewModel_Usuario;

    private NavController mNavController;

    private ImageView imageView_back;

    public ConfirmarEntregaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        puntaje_servicio=0;
        puntaje_usuario=0;

        calificacion_servicio=false;
        calificacion_usuario=false;

        viewModel_Servicio= new ViewModelProvider(this).get(Calificacion_ServicioViewModel.class);
        viewModel_Servicio.init();

        viewModel_Usuario= new ViewModelProvider(this).get(Calificacion_UsuarioViewModel.class);
        viewModel_Usuario.init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmar_entrega, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController= Navigation.findNavController(view);

        if(getArguments()!=null){

            ConfirmarEntregaFragmentArgs args=ConfirmarEntregaFragmentArgs.fromBundle(getArguments());
            orden=args.getOrden();

        }

        //responseCalificacion();

        initWidgets(view);

        setDataWidgets();

        buttonClick();

        declararServicioWidget(view);

        declararUsuarioWidget(view);
    }

    private void setDataWidgets() {

        nombre_empresa.setText("Gracias por tu compra en "+orden.getDetalle_orden().getNombre_empresa());
    }

    private void initWidgets(View view){
        confirmarCalificacion=view.findViewById(R.id.confirmarCalificacion);
        inicio=view.findViewById(R.id.inicio);
        nombre_empresa=view.findViewById(R.id.textView_nombre_usuario);

        imageView_back=view.findViewById(R.id.imageView_back);

        imageView_back.setOnClickListener(v->{

            sendCalificacion(0,0);

            deleteFragment();
        });

    }

    private  void declararUsuarioWidget(View view ) {



        SmileyRating smileyRating = view.findViewById(R.id.smile_rating_USUARIO);

        smileyRating.setTitle(SmileyRating.Type.GREAT, "Muy bueno");
        smileyRating.setTitle(SmileyRating.Type.GOOD, "Bueno");
        smileyRating.setTitle(SmileyRating.Type.OKAY, "Normal");
        smileyRating.setTitle(SmileyRating.Type.BAD, "Malo");
        smileyRating.setTitle(SmileyRating.Type.TERRIBLE, "Terrible");

        smileyRating.setRating(SmileyRating.Type.OKAY);


        smileyRating.setSmileySelectedListener(type -> {
            if (SmileyRating.Type.GREAT == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_usuario=5;
                calificacion_usuario=true;
            }

            if (SmileyRating.Type.GOOD == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_usuario=4;
                calificacion_usuario=true;
            }

            if (SmileyRating.Type.OKAY == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_usuario=3;
                calificacion_usuario=true;
            }

            if (SmileyRating.Type.BAD == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_usuario=2;
                calificacion_usuario=true;
            }

            if (SmileyRating.Type.TERRIBLE == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_usuario=1;
                calificacion_usuario=true;
            }
        });


    }

    private  void declararServicioWidget(View view ) {



        SmileyRating smileyRating = view.findViewById(R.id.smile_rating_SERVICIO);

        smileyRating.setTitle(SmileyRating.Type.GREAT, "Muy bueno");
        smileyRating.setTitle(SmileyRating.Type.GOOD, "Bueno");
        smileyRating.setTitle(SmileyRating.Type.OKAY, "Normal");
        smileyRating.setTitle(SmileyRating.Type.BAD, "Malo");
        smileyRating.setTitle(SmileyRating.Type.TERRIBLE, "Terrible");

        smileyRating.setRating(SmileyRating.Type.OKAY);


        smileyRating.setSmileySelectedListener(type -> {
            if (SmileyRating.Type.GREAT == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_servicio=5;
                calificacion_servicio=true;
            }

            if (SmileyRating.Type.GOOD == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_servicio=4;
                calificacion_servicio=true;
            }

            if (SmileyRating.Type.OKAY == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_servicio=3;
                calificacion_servicio=true;
            }

            if (SmileyRating.Type.BAD == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_servicio=2;
                calificacion_servicio=true;
            }

            if (SmileyRating.Type.TERRIBLE == type) {
                Log.i(TAG, "Wow, the user gave high rating");
                puntaje_servicio=1;
                calificacion_servicio=true;
            }
        });


    }


    private void buttonClick(){
        confirmarCalificacion.setOnClickListener( v->{

            sendCalificacion(puntaje_usuario,puntaje_servicio);

            deleteFragment();


        });

        inicio.setOnClickListener( v->{

            sendCalificacion(1,1);

            deleteFragment();
        });
    }



    private void sendCalificacion(float rating_delivery,float rating_servicio){

        /*
        *
        * ESTAMOS CALIFICANDO AL REPARTIDOR
        *
        * */
        Calificacion_Usuario calificacion_usuario= new Calificacion_Usuario();
        calificacion_usuario.setIdventa(orden.getDetalle_orden().getIdventa());
        calificacion_usuario.setIdusuario(orden.getUsuario().getIdusuariogeneral().intValue());
        calificacion_usuario.setCalificacion(rating_delivery);

        viewModel_Usuario.agregarCalificacion(calificacion_usuario);

        /*
         *
         * ESTAMOS CALIFICANDO EL SERVICIO OFRECIDO POR YEGOO
         *
         * */
        Calificacion_Servicio calificacion_servicio= new Calificacion_Servicio();
        calificacion_servicio.setIdventa(orden.getDetalle_orden().getIdventa());
        calificacion_servicio.setIdusuario(Cliente_Bi.getCliente().getIdusuario());
        calificacion_servicio.setCalificacion(rating_servicio);

        viewModel_Servicio.agregarCalificacion(calificacion_servicio);
    }

    private void deleteFragment(){

        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }

        mNavController.navigate(R.id.nav_inicio);
    }
}
