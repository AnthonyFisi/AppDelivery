package com.example.yego.View.VentaUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yego.R;
import com.example.yego.Utils.ComnetarioViewModel;
import com.example.yego.View.ProductDetailUI.FragmentComentarioDialog;
import com.example.yego.View.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class FragmentComentarioBottomSheet  extends BottomSheetDialogFragment {

    public static final String TAG ="" ;
    private static final String COMENTARIO_VENTA = "comentario_venta";
    private static final String HINT ="hint" ;
    private static final String TITUTLO_COMENTARIO = "titulo_comentario";
    private static final String TIPO_COMENTARIO = "tipo_comentario";
    TextView textInputFirstName;
    Button button_comentario;
    //Comentario listener;
    private FormDialogListener listener;
    private ImageButton close_comentario;
    private String comentario,hint;
    private TextView titutlo_comentario;
    private String titulo_comentario;
    private int tipo_comentario;


    public static FragmentComentarioBottomSheet newInstance(int tipo_comentario,String titulo_comentario,String comentario,String hint){
        FragmentComentarioBottomSheet fragment=new FragmentComentarioBottomSheet();
        Bundle bundle=new Bundle();
        bundle.putString(COMENTARIO_VENTA,comentario);
        bundle.putString(HINT,hint);
        bundle.putString(TITUTLO_COMENTARIO,titulo_comentario);
        bundle.putInt(TIPO_COMENTARIO,tipo_comentario);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            comentario=getArguments().getString(COMENTARIO_VENTA);
            hint=getArguments().getString(HINT);
            titulo_comentario=getArguments().getString(TITUTLO_COMENTARIO);
            tipo_comentario=getArguments().getInt(TIPO_COMENTARIO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comentario_bottomsheet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_comentario=view.findViewById(R.id.button_comentario);
        textInputFirstName=view.findViewById(R.id.textInputFirstName);
        close_comentario=view.findViewById(R.id.close_comentario);
        titutlo_comentario=view.findViewById(R.id.titulo_comentario);

        titutlo_comentario.setText(titulo_comentario);

        if(comentario.length()>0){
            textInputFirstName.setText(comentario);
        }else {
            textInputFirstName.setHint(hint);
        }

        button_comentario.setOnClickListener(v->{
            if(tipo_comentario==1){
                returnValues(textInputFirstName.getText().toString());
            }
            if(tipo_comentario==2){
                returnValuesComentarioUbicacion(textInputFirstName.getText().toString());
            }
            dismiss();
        });

        close_comentario.setOnClickListener(v->{
            if(tipo_comentario==1){
                returnValues(comentario);
            }
            if(tipo_comentario==2){
                returnValuesComentarioUbicacion(comentario);
            }
            dismiss();
        });


    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentComentarioBottomSheet.FormDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FormDialogListener");
        }
    }


    public interface FormDialogListener{
        void update2(String comentario);
        void updateComentarioUbicacion(String comentario);

    }

    private void returnValues(String comentario) {
        listener.update2(comentario);
    }
    private void returnValuesComentarioUbicacion(String comentario) {
        listener.updateComentarioUbicacion(comentario);
    }
}
