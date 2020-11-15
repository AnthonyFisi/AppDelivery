package com.example.yego.View.ui.Perfil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.yego.Login.LoadingDialog;
import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.Utils.ActionBottomDialogFragment;
import com.example.yego.ViewModel.UsuarioInfoViewModel;
import com.example.yego.ViewModel.UsuarioViewModel;
import com.google.api.Distribution;

import org.jetbrains.annotations.NotNull;

public class PerfilFragment extends Fragment {


    private TextView  perfil_NOMBRE,perfil_APELLIDO,perfil_UBICACION_ACTUAL;
    private EditText perfil_CORREO_ELECTRONICO,perfil_CELULAR;
    private TextView  perfil_CAMBIAR_CELULAR;
    private ImageView perfil_IMAGEN;
    private ImageView ic_back;
    private BackToInicio mBackToInicio;

    private UsuarioViewModel viewModel;

    private CardView cardView_change,cardView_cancelar;

    private LinearLayout linearlayout_progressbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
        viewModel.init();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_perfil, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        declararWidgets(view);

        setDataWidget();

        clickCambiarCelular(view);

        ic_back=view.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(v->{
            backToInicio();
        });

        responseDataUpdate();


    }

    private void declararWidgets(View view){

        perfil_NOMBRE=view.findViewById(R.id.perfil_NOMBRE);
        perfil_APELLIDO=view.findViewById(R.id.perfil_APELLIDO);
        perfil_CORREO_ELECTRONICO=view.findViewById(R.id.perfil_CORREO_ELECTRONICO);
        perfil_CELULAR=view.findViewById(R.id.perfil_CELULAR);
        perfil_UBICACION_ACTUAL=view.findViewById(R.id.perfil_UBICACION_ACTUAL);
        perfil_IMAGEN=view.findViewById(R.id.perfil_IMAGEN);
        linearlayout_progressbar=view.findViewById(R.id.linearlayout_progressbar);

        cardView_change=view.findViewById(R.id.cardView_change);

        cardView_cancelar=view.findViewById(R.id.cardView_cancelar);

        //perfil_CAMBIAR_CORREO=view.findViewById(R.id.perfil_CAMBIAR_CORREO);
        perfil_CAMBIAR_CELULAR=view.findViewById(R.id.perfil_CAMBIAR_CELULAR);

    }

    private void setDataWidget(){

        if (Cliente_Bi.getCliente().getFoto()!= null) {
            String imageUrl = Cliente_Bi.getCliente().getFoto()
                    .replace("http://", "https://");

            Glide.with(this)
                    .load(imageUrl)
                    .into(perfil_IMAGEN);
        }



        perfil_NOMBRE.setText(Cliente_Bi.getCliente().getNombre());
        perfil_APELLIDO.setText("");
        perfil_CORREO_ELECTRONICO.setText(Cliente_Bi.getCliente().getCorreo());
        perfil_CELULAR.setText(Cliente_Bi.getCliente().getCelular());
        perfil_UBICACION_ACTUAL.setText(Cliente_Bi.getCliente().getUbicacion_nombre());

        perfil_CORREO_ELECTRONICO.setEnabled(false);
        perfil_CELULAR.setEnabled(false);


    }


    private void clickCambiarCelular(View view){

        perfil_CAMBIAR_CELULAR.setOnClickListener(v->{

            FragmentManager fm = getChildFragmentManager();
            FragmentPhoneDialog alertDialog = FragmentPhoneDialog.newInstance(Cliente_Bi.getCliente().getCelular());
            alertDialog.show(fm, "fragment_alert");

            perfil_CELULAR.setEnabled(true);
            perfil_CELULAR.setCursorVisible(true);
            perfil_CELULAR.setFocusable(true);
           // perfil_CELULAR.setTextCursorDrawable(getResources().getDrawable(R.color.mainColor));

            cardView_change.setVisibility(View.VISIBLE);
            cardView_cancelar.setVisibility(View.VISIBLE);

            perfil_CAMBIAR_CELULAR.setVisibility(View.GONE);


            perfil_CELULAR.requestFocus();
            perfil_CELULAR.setFocusableInTouchMode(true);

            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(perfil_CELULAR, InputMethodManager.SHOW_FORCED);


        });


        cardView_change.setOnClickListener(v->{

            if(perfil_CELULAR.getText().toString().length()==9 && String.valueOf(perfil_CELULAR.getText().toString().charAt(0)).equals(String.valueOf(9))){
              //  view.getWindow().setSoftInputMode(
                        //WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                linearlayout_progressbar.setVisibility(View.VISIBLE);
                viewModel.updateCelular(Cliente_Bi.getCliente().getIdusuario(),perfil_CELULAR.getText().toString());
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }else{
                Toast.makeText(getContext(), "Ingresar numero correcto", Toast.LENGTH_LONG).show();
            }

          //  linearlayout_progressbar.setVisibility(View.VISIBLE);
        });

        cardView_cancelar.setOnClickListener(v->{
            perfil_CAMBIAR_CELULAR.setVisibility(View.VISIBLE);
            cardView_cancelar.setVisibility(View.GONE);
            cardView_change.setVisibility(View.GONE);

            perfil_CELULAR.setFocusable(false);
            perfil_CELULAR.setEnabled(false);
            perfil_CELULAR.setCursorVisible(false);

            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });


    }



    private void backToInicio() {
        mBackToInicio.back();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mBackToInicio = (PerfilFragment.BackToInicio) context;
    }

    public interface BackToInicio{
        void  back();
    }


    private void responseDataUpdate(){
        viewModel.getUsuarioLiveData().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                linearlayout_progressbar.setVisibility(View.GONE);
                cardView_cancelar.setVisibility(View.GONE);
                cardView_change.setVisibility(View.GONE);
                perfil_CAMBIAR_CELULAR.setVisibility(View.VISIBLE);
                perfil_CELULAR.setFocusable(false);
                perfil_CELULAR.setEnabled(false);
                perfil_CELULAR.setCursorVisible(false);


                if(usuario!=null){
                   perfil_CELULAR.setText(usuario.getCelular());
                    Cliente_Bi.getCliente().setCelular(usuario.getCelular());
                    Toast.makeText(getContext(),"Actualizacion exitosa",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getContext(),"Lo sentimos presentamos problemas",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}