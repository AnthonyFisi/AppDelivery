package com.example.yego.View.ui.Perfil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yego.R;
import com.example.yego.Repository.Modelo.Cliente_Bi;
import com.example.yego.Repository.Modelo.Usuario;
import com.example.yego.View.VentaUI.FragmentComentarioVentaDialog;
import com.example.yego.ViewModel.UsuarioViewModel;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class FragmentPhoneDialog extends AppCompatDialogFragment  {


    public static final String TAG = FragmentPhoneDialog.class.getSimpleName();

    private static final String ARG_VALUE = "ARG_VALUE";


    private UsuarioViewModel viewModel;

    private EditText textInputFirstName;

    private TextView titulo_dialogframent,textView_phone_number;

    private CardView button_cancelar_comentario,button_agregar_comentario;

    private LinearLayout linearlayout_change_phone,linearlayout_ok,linearlayout_loading;

    private AlertDialog alertDialog;

    private FormDialogListener1 listener;

    public static FragmentPhoneDialog newInstance(String value) {
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, value);
        FragmentPhoneDialog frag = new FragmentPhoneDialog();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider(this).get(UsuarioViewModel.class);
        viewModel.init();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_comentarios_dialog, null);

        responseDataUpdate();
        setupContent(content);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content);
              /*  .setNegativeButton(getString(R.string.cancel), null)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.save), null)
    .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            returnValues();
          }
        });*/


       alertDialog = builder.create();
        //asegura que se muestre el teclado
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }




    private void setupContent(View content) {
        textInputFirstName = content.findViewById(R.id.textInputFirstName);
        textInputFirstName.setText(getArguments().getString(ARG_VALUE));
        textInputFirstName.setSelection(getArguments().getString(ARG_VALUE).length());

        textView_phone_number=content.findViewById(R.id.textView_phone_number);
        linearlayout_change_phone=content.findViewById(R.id.linearlayout_change_phone);
        linearlayout_ok=content.findViewById(R.id.linearlayout_ok);
        linearlayout_loading=content.findViewById(R.id.linearlayout_loading);

        titulo_dialogframent=content.findViewById(R.id.titulo_dialogframent);

        button_cancelar_comentario=content.findViewById(R.id.button_cancelar_comentario);
        button_agregar_comentario=content.findViewById(R.id.button_agregar_comentario);

        button_agregar_comentario.setOnClickListener(v->{

            if(textInputFirstName.getText().toString().length()==9 && String.valueOf(textInputFirstName.getText().toString().charAt(0)).equals(String.valueOf(9))){
                alertDialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                linearlayout_loading.setVisibility(View.VISIBLE);
                linearlayout_change_phone.setVisibility(View.GONE);
                viewModel.updateCelular(Cliente_Bi.getCliente().getIdusuario(),textInputFirstName.getText().toString());

            }else{
                Toast.makeText(getContext(), "Ingresar numero correcto", Toast.LENGTH_LONG).show();
            }


        });


        button_cancelar_comentario.setOnClickListener(v->{
            Cliente_Bi.getCliente().setCelular(textInputFirstName.getText().toString());
            dismiss();
        });


        textInputFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    alertDialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    return true;
                }
                return false;
            }
        });


    }



    private void responseDataUpdate(){
        viewModel.getUsuarioLiveData().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                linearlayout_loading.setVisibility(View.GONE);
                if(usuario!=null){
                    linearlayout_ok.setVisibility(View.VISIBLE);
                    textView_phone_number.setText(usuario.getCelular());
                    Cliente_Bi.getCliente().setCelular(usuario.getCelular());
                    returnValues();
                    //Toast.makeText(getContext(),"Actualizacion exitosa",Toast.LENGTH_LONG).show();

                }else {
                    linearlayout_change_phone.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Lo sentimos presentamos problemas",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void returnValues() {
        listener.update();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentPhoneDialog.FormDialogListener1) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FormDialogListener");
        }
    }

    public interface FormDialogListener1{
        void update();

    }

}
