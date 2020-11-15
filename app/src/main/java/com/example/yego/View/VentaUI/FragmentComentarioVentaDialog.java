package com.example.yego.View.VentaUI;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yego.R;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;


public class FragmentComentarioVentaDialog extends AppCompatDialogFragment  {


    public static final String TAG = FragmentComentarioVentaDialog.class.getSimpleName();

    private static final String ARG_VALUE = "ARG_VALUE";
    private static final String ARG_TITUTLODIALOG = "ARG_TITULODIALOG";
    private static final String ARG_HINT = "ARG_HINT";
    private static final String ARG_CODE ="ARH_CODE" ;

    private EditText textInputFirstName;
    private TextView titulo_dialogframent;
    private FormDialogListener1 listener;

    private CardView button_cancelar_comentario,button_agregar_comentario;

    public static FragmentComentarioVentaDialog newInstance(String value,String tituloDialog,String hint,int code) {
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, value);
        args.putString(ARG_TITUTLODIALOG,tituloDialog);
        args.putString(ARG_HINT, hint);
        args.putInt(ARG_CODE, code);

        FragmentComentarioVentaDialog frag = new FragmentComentarioVentaDialog();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentComentarioVentaDialog.FormDialogListener1) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FormDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_comentarios_dialog, null);

        setupContent(content);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(content);



        AlertDialog alertDialog = builder.create();
        //asegura que se muestre el teclado
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
       /* Button positiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    returnValues();
                    getDialog().dismiss();

            }
        });*/
    }


    private void returnValues() {
            listener.update(textInputFirstName.getText().toString(),getArguments().getInt(ARG_CODE));
    }

    private void setupContent(View content) {
        textInputFirstName = content.findViewById(R.id.textInputFirstName);
        textInputFirstName.setText(getArguments().getString(ARG_VALUE));
        textInputFirstName.setSelection(getArguments().getString(ARG_VALUE).length());

        textInputFirstName.setHint(getArguments().getString(ARG_HINT));

        titulo_dialogframent=content.findViewById(R.id.titulo_dialogframent);
        titulo_dialogframent.setText(getArguments().getString(ARG_TITUTLODIALOG));

        button_cancelar_comentario=content.findViewById(R.id.button_cancelar_comentario);
        button_agregar_comentario=content.findViewById(R.id.button_agregar_comentario);

        button_agregar_comentario.setOnClickListener(v->{
            returnValues();
            dismiss();
        });


        button_cancelar_comentario.setOnClickListener(v->{
            dismiss();
        });


        textInputFirstName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    returnValues();
                    dismiss();
                    return true;
                }
                return false;
            }
        });


    }



    public interface FormDialogListener1{
        void update(String firstname,int code);

    }



}
